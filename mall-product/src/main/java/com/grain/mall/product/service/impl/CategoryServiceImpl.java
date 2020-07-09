package com.grain.mall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.grain.mall.product.service.CategoryBrandRelationService;
import com.grain.mall.product.vo.CategoryTwoVo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grain.common.utils.PageUtils;
import com.grain.common.utils.Query;

import com.grain.mall.product.dao.CategoryDao;
import com.grain.mall.product.entity.CategoryEntity;
import com.grain.mall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //2、组装成父子树形结构
        //2.1、找到所有的一级分类
        List<CategoryEntity> levelOneMenus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu) -> {
            menu.setChildren(getChildrens(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return levelOneMenus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 1、检查当前删除的分类，是否被别的地方引用
        // 逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联的数据
     *
     * @param category
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    @Override
    public List<CategoryEntity> getLevelOneCategorys() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return categoryEntities;
    }

    // TODO 产生堆外内存溢出：OutOfDirectMemoryError
    // SpringBoot 2.0以后默认使用lettuce作为操作redis的客户端，它使用netty进行网络通信。
    // lettuce 的bug导致netty堆外内存溢出  -Xmx300m netty如果没有指定堆外内存，默认使用-Xmx300m
    // 可以通过-Dio.netty.maxDirectMemory进行设置
    // 解决方案：不能使用-Dio.netty.maxDirectMemory进行设置
    // 1、升级lettuce客户端   2、切换使用jedis
    @Override
    public Map<String, List<CategoryTwoVo>> getCategoryJson() {
        // 给缓存中放json字符串，拿出的json字符串，还要逆转为能用的对象类型【序列化与反序列化】

        // 1、加入缓存逻辑，缓存中的数据是json字符串
        // JSON跨语言，跨平台兼容
        String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");

        /**
         * 1、空结果缓存：解决缓存穿透
         * 2、设置过期时间（加随机值）：解决缓存雪崩
         * 3、加锁：解决缓存击穿
         */
        if (StringUtils.isEmpty(catalogJSON)) {
            // 2、缓存中没有，查询数据库
            System.out.println("缓存不命中。。。查询数据库。。。");
            Map<String, List<CategoryTwoVo>> categoryJsonFromDb = getCategoryJsonFromDbWithRedissonLock();
            return categoryJsonFromDb;
        }

        System.out.println("缓存命中。。。直接返回。。。");
        Map<String, List<CategoryTwoVo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<CategoryTwoVo>>>() {
        });

        return result;
    }

    /**
     * 从数据库查询并封装分类数据
     * Redisson分布式锁
     *
     * @return
     */
    public Map<String, List<CategoryTwoVo>> getCategoryJsonFromDbWithRedissonLock() {

        // 1、锁的名字。锁的粒度
        RLock lock = redissonClient.getLock("CategoryJson-lock");
        lock.lock();

        Map<String, List<CategoryTwoVo>> dataFromDb = null;
        try {
            dataFromDb = getDataFromDb();
        } finally {
            lock.unlock();
        }

        return dataFromDb;

    }

    /**
     * 从数据库查询并封装分类数据
     * 分布式锁
     *
     * @return
     */
    public Map<String, List<CategoryTwoVo>> getCategoryJsonFromDbWithRedisLock() {

        // 1、占分布式锁，去redis占坑
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
        if (lock) {
            System.out.println("获取分布式锁成功。。。");
            // 加锁成功......执行业务
            Map<String, List<CategoryTwoVo>> dataFromDb = null;
            try {
                dataFromDb = getDataFromDb();
            } finally {
                // 获取值对比+对比成功=原子操作   lua脚本解锁
//                String lockValue = stringRedisTemplate.opsForValue().get("lock");
//                if(uuid.equals(lockValue)){
//                    stringRedisTemplate.delete("lock"); // 删除锁，相当于解锁
//                }
                // 删除锁(原子操作)
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                Long execute = stringRedisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
            }

            return dataFromDb;
        } else {
            System.out.println("获取分布式锁失败。。。");
            // 加锁失败.....重试
            // 休眠100毫秒重试
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getCategoryJsonFromDbWithRedisLock(); // 自旋的方式
        }

    }


    /**
     * 从数据库查询并封装分类数据
     * 本地锁
     *
     * @return
     */
    public Map<String, List<CategoryTwoVo>> getCategoryJsonFromDbWithLocalLock() {

        // 只要是通一把锁，就能锁住需要这个锁的所有线程
        // 1、synchronized (this) springboot中的所有的组件在容器中都是单例的
        // TODO 本地锁：synchronized，JUC（Lock），在分布式情况下，必须使用分布式锁
        synchronized (this) {
            // 得到锁以后，我们应该再去缓存中确定一次，如果没有才需要继续查询
            return getDataFromDb();
        }

    }

    private Map<String, List<CategoryTwoVo>> getDataFromDb() {
        // 得到锁以后，我们应该再去缓存中确定一次，如果没有才需要继续查询
        String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");

        if (!StringUtils.isEmpty(catalogJSON)) {
            Map<String, List<CategoryTwoVo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<CategoryTwoVo>>>() {
            });

            return result;
        }
        System.out.println("查询了数据库。。。。。");

        /**
         * 将数据库多次查询变为一次查询
         */
        List<CategoryEntity> selectList = baseMapper.selectList(null);

        // 查出所有一级分类
        List<CategoryEntity> levelOneCategorys = getParent_cid(selectList, 0L);

        //封装数据
        Map<String, List<CategoryTwoVo>> parent_cid = levelOneCategorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 每一个的一级分类，查到这个一级分类的二级分类
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());

            // 封装上面的结果
            List<CategoryTwoVo> categoryTwoVos = null;
            if (categoryEntities != null) {
                categoryTwoVos = categoryEntities.stream().map(levelTwo -> {
                    CategoryTwoVo categoryTwoVo = new CategoryTwoVo(v.getCatId().toString(), null, levelTwo.getCatId().toString(), levelTwo.getName());

                    // 找当前二级分类的三级分类封装成vo
                    List<CategoryEntity> levelThreeCategory = getParent_cid(selectList, levelTwo.getCatId());
                    if (levelThreeCategory != null) {
                        List<CategoryTwoVo.CategoryThreeVo> categoryThreeVos = levelThreeCategory.stream().map(levelThree -> {
                            CategoryTwoVo.CategoryThreeVo categoryThreeVo = new CategoryTwoVo.CategoryThreeVo(levelTwo.getCatId().toString(), levelThree.getCatId().toString(), levelThree.getName());

                            return categoryThreeVo;
                        }).collect(Collectors.toList());
                        categoryTwoVo.setCatalog3List(categoryThreeVos);
                    }

                    return categoryTwoVo;
                }).collect(Collectors.toList());
            }

            return categoryTwoVos;
        }));

        // 查到的数据再放入缓存，将对象转为json放在缓存中
        String jsonString = JSON.toJSONString(parent_cid);
        stringRedisTemplate.opsForValue().set("catalogJSON", jsonString, 1, TimeUnit.DAYS);

        return parent_cid;
    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parentCid) {
        List<CategoryEntity> collect = selectList.stream().filter(item -> item.getParentCid() == parentCid).collect(Collectors.toList());
        return collect;
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        // 1、收集当前节点id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

    // 递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map((categoryEntity) -> {
            // 1、找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            // 2、菜单的排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }
}