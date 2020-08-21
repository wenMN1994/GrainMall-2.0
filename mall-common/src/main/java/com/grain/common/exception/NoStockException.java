package com.grain.common.exception;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/8/20 22:19
 * @description：
 * @modified By：
 * @version: $
 */
public class NoStockException extends RuntimeException {

    private Long skuId;
    private String msg;

    public NoStockException(Long skuId) {
        super("商品id：" + skuId + "没有足够的库存了");
    }

    public NoStockException(String msg) {
        super("库存锁定失败，商品库存不足");
    }
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
