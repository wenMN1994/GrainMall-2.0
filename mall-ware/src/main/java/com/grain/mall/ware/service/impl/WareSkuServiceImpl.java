package com.grain.mall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.grain.common.exception.NoStockException;
import com.grain.common.to.mq.StockDetailTo;
import com.grain.common.to.mq.StockLockedTo;
import com.grain.common.utils.R;
import com.grain.mall.ware.entity.WareOrderTaskDetailEntity;
import com.grain.mall.ware.entity.WareOrderTaskEntity;
import com.grain.mall.ware.feign.OrderFeignService;
import com.grain.mall.ware.feign.ProductFeignService;
import com.grain.mall.ware.service.WareOrderTaskDetailService;
import com.grain.mall.ware.service.WareOrderTaskService;
import com.grain.mall.ware.vo.OrderItemVo;
import com.grain.mall.ware.vo.OrderVo;
import com.grain.mall.ware.vo.SkuHasStockVo;
import com.grain.mall.ware.vo.WareSkuLockVo;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    OrderFeignService orderFeignService;

    @Autowired
    WareOrderTaskService wareOrderTaskService;

    @Autowired
    WareOrderTaskDetailService wareOrderTaskDetailService;

    @Autowired
    RabbitTemplate rabbitTemplate;

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

    /**
     * 为某个订单锁定库存
     * @param vo
     * @return
     * 库存解锁场景
     * 1、下订单成功，订单过期没有支付系统自动取消或用户手动取消，都要解锁库存
     * 2、下订单成功，库存锁定成功，接下来的业务调用失败，到最后订单回滚
     *      之前锁定的库存就要解锁。
     *
     */
    @Transactional
    @Override
    public Boolean orderLockStock(WareSkuLockVo vo) {
        /**
         * 保存库存工作单的详情
         * 追溯
         */
        WareOrderTaskEntity taskEntity = new WareOrderTaskEntity();
        taskEntity.setOrderSn(vo.getOrderSn());
        wareOrderTaskService.save(taskEntity);

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
                    // TODO 告诉MQ库存锁定成功
                    WareOrderTaskDetailEntity wareOrderTaskDetailEntity = new WareOrderTaskDetailEntity(null, skuId, "", num, taskEntity.getId(), wareId, 1);
                    wareOrderTaskDetailService.save(wareOrderTaskDetailEntity);
                    StockLockedTo lockedTo = new StockLockedTo();
                    lockedTo.setId(taskEntity.getId());
                    StockDetailTo stockDetailTo = new StockDetailTo();
                    BeanUtils.copyProperties(wareOrderTaskDetailEntity,stockDetailTo);
                    lockedTo.setDetail(stockDetailTo);
                    rabbitTemplate.convertAndSend("stock-event-exchange","stock.locked",lockedTo);
                    break;
                }
            }
            if(!skuStocked){
                // 当前商品所有仓库都没有锁住
                throw new NoStockException(skuId);
            }
        }

        // 3、全部都锁定成功
        return true;
    }

    @Override
    public void unLockStock(StockLockedTo to) {
        StockDetailTo detail = to.getDetail();
        Long detailId = detail.getId();
        /**
         * 解锁
         * 1、查询数据库关于这个订单的锁定库存信息。
         * 有：证明库存锁定成功
         *      解锁：订单情况。
         *          1、没有这个订单，必须解锁
         *          2、有这个订单。不是解锁库存
         *              订单状态：已取消：解锁库存
         *              没取消：不能解锁
         * 没有：库存锁定失败了，库存回滚了。这种情况无需解锁
         */
        WareOrderTaskDetailEntity byId = wareOrderTaskDetailService.getById(detailId);
        if(byId != null){
            // 解锁
            Long id = to.getId();
            WareOrderTaskEntity taskEntity = wareOrderTaskService.getById(id);
            String orderSn = taskEntity.getOrderSn();
            R r = orderFeignService.getOrderStatus(orderSn);
            if(r.getCode() == 0){
                // 订单数据返回成功
                OrderVo data = r.getData(new TypeReference<OrderVo>() {
                });
                if(data == null || data.getStatus() == 4){
                    // 订单不存在或订单已经被取消了，才能解锁库存
                    if(byId.getLockStatus() == 1){
                        unLockStock(detail.getSkuId(),detail.getWareId(),detail.getSkuNum(),detailId);
                    }
                }
            }else {
                // 消息拒绝以后重新放回队列里面，让别人继续消费
                throw new RuntimeException("远程服务调用失败");
            }
        }else {
            // 无需解锁
        }
    }

    /**
     * 1、库存自动解锁
     *    下单成功，库存锁定成功，接下来的业务调用失败，导致订单回滚，之前锁定的库存就要自动解锁
     * 2、订单失败
     *     锁库存失败
     */
    public void unLockStock(Long skuId, Long wareId, Integer num, Long taskDetailId){
        // 库存解锁
        wareSkuDao.unLockStock(skuId,wareId,num);
        // 更新库存工作单的状态
        WareOrderTaskDetailEntity entity = new WareOrderTaskDetailEntity();
        entity.setId(taskDetailId);
        // 变为已解锁
        entity.setLockStatus(2);
        wareOrderTaskDetailService.updateById(entity);
    }

    @Data
    class SkuWareHasStock{
        private Long skuId;
        private Integer num;
        private List<Long> wareId;
    }
}