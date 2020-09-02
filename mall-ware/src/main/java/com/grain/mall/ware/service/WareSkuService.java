package com.grain.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grain.common.to.mq.OrderTo;
import com.grain.common.to.mq.StockLockedTo;
import com.grain.common.utils.PageUtils;
import com.grain.mall.ware.entity.WareSkuEntity;
import com.grain.mall.ware.vo.SkuHasStockVo;
import com.grain.mall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-06-02 17:53:21
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);


    void addStock(Long skuId, Long wareId, Integer skuNum);

    /**
     * 查询sku是否有库存
     * @param skuIds
     * @return
     */
    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    /**
     * 为某个订单锁定库存
     * @param vo
     * @return
     */
    Boolean orderLockStock(WareSkuLockVo vo);

    /**
     * 解锁库存
     * @param to
     */
    void unLockStock(StockLockedTo to);

    /**
     * 订单关闭解锁库存
     * @param orderTo
     */
    void unLockStock(OrderTo orderTo);
}

