package com.grain.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grain.common.utils.PageUtils;
import com.grain.mall.order.entity.OrderEntity;
import com.grain.mall.order.vo.OrderConfirmVo;
import com.grain.mall.order.vo.PayVo;
import com.grain.mall.order.vo.SubmitOrderResponseVo;
import com.grain.mall.order.vo.SubmitOrderVo;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-06-02 17:49:52
 */
public interface OrderService extends IService<OrderEntity> {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 订单确认页返回需要的数据
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    /**
     * 提交订单
     * @param vo
     * @return
     */
    SubmitOrderResponseVo submitOrder(SubmitOrderVo vo);

    /**
     * 根据订单号获取订单
     * @param orderSn
     * @return
     */
    OrderEntity getOrderByOrderSn(String orderSn);

    /**
     * 关闭订单
     * @param entity
     */
    void closeOrder(OrderEntity entity);

    /**
     * 获取当前订单的支付信息
     * @param orderSn
     * @return
     */
    PayVo getOrderPay(String orderSn);

    /**
     * 查询当前登录用户的所有订单列表
     * @param params
     * @return
     */
    PageUtils queryPageWithItem(Map<String, Object> params);
}

