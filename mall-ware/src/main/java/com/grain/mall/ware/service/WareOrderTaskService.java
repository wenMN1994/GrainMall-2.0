package com.grain.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grain.common.utils.PageUtils;
import com.grain.mall.ware.entity.WareOrderTaskEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-06-02 17:53:21
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据订单编号查询库存工作单
     * @param orderSn
     * @return
     */
    WareOrderTaskEntity getOrderTaskByOrderSn(String orderSn);
}

