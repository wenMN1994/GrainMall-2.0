package com.grain.mall.ware.service.impl;

import com.grain.common.exception.NoStockException;
import com.grain.common.utils.R;
import com.grain.mall.ware.feign.ProductFeignService;
import com.grain.mall.ware.vo.OrderItemVo;
import com.grain.mall.ware.vo.SkuHasStockVo;
import com.grain.mall.ware.vo.WareSkuLockVo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grain.common.utils.PageUtils;
import com.grain.common.utils.Query;

import com.grain.mall.ware.dao.WareSkuDao;
import com.grain.mall.ware.entity.WareSkuEntity;
import com.grain.mall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * @author DragonWen
 */
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    WareSkuDao wareSkuDao;

    @Autowired
    ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        /**
         * wareId: 123,//仓库id
         * skuId: 123//商品id
         */
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if(!StringUtils.isEmpty(skuId)){
            queryWrapper.eq("sku_id", skuId);
        }

        String wareId = (String) params.get("wareId");
        if(!StringUtils.isEmpty(wareId)){
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {

        //1、判断如果还没有这个库存记录新增
        List<WareSkuEntity> entities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if(entities == null || entities.size() == 0){
            WareSkuEntity skuEntity = new WareSkuEntity();
            skuEntity.setSkuId(skuId);
            skuEntity.setStock(skuNum);
            skuEntity.setWareId(wareId);
            skuEntity.setStockLocked(0);
            //TODO 远程查询sku的名字，如果失败，整个事务无需回滚
            //1、自己catch异常
            //TODO 还可以用什么办法让异常出现以后不回滚？高级
            try {
                R info = productFeignService.info(skuId);
                Map<String,Object> data = (Map<String, Object>) info.get("skuInfo");

                if(info.getCode() == 0){
                    skuEntity.setSkuName((String) data.get("skuName"));
                }
            }catch (Exception e){

            }

            wareSkuDao.insert(skuEntity);
        }else{
            wareSkuDao.addStock(skuId,wareId,skuNum);
        }
    }

    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> collect = skuIds.stream().map(skuId -> {
            SkuHasStockVo vo = new SkuHasStockVo();
            // 查询当前sku的总库存数
            Long count = baseMapper.getSkuStock(skuId);
            vo.setSkuId(skuId);
            vo.setHasStock(count == null?false:count>0);
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }

    @Transactional
    @Override
    public Boolean orderLockStock(WareSkuLockVo vo) {
        // 现实生产环境：按照下单的地址，找到一个就近的仓库，锁定库存
        // 1、找到每个商品在哪个仓库有库存
        List<OrderItemVo> locks = vo.getLocks();

        List<SkuWareHasStock> collect = locks.stream().map(item -> {
            SkuWareHasStock stock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            stock.setSkuId(skuId);
            stock.setNum(item.getCount());
            // 查询这个商品在哪里有库存
            List<Long> wareIds = wareSkuDao.listWareIdHasStock(skuId);
            stock.setWareId(wareIds);
            return stock;
        }).collect(Collectors.toList());

        // 2、锁定库存
        for (SkuWareHasStock hasStock : collect) {
            Boolean skuStocked = false;
            Long skuId = hasStock.getSkuId();
            Integer num = hasStock.getNum();
            List<Long> wareIds = hasStock.getWareId();
            if(wareIds == null || wareIds.size() == 0){
                throw new NoStockException(skuId);
            }
            for (Long wareId : wareIds) {
                // 成功返回1,否则就是0
                Long count = wareSkuDao.lockSkuStock(skuId,wareId,num);
                if(count == 1){
                    skuStocked = true;
                    break;
                }
            }
            if(skuStocked){
                // 当前商品所有仓库都没有锁住
                throw new NoStockException(skuId);
            }
        }

        // 3、全部都锁定成功
        return true;
    }

    @Data
    class SkuWareHasStock{
        private Long skuId;
        private Integer num;
        private List<Long> wareId;
    }
}