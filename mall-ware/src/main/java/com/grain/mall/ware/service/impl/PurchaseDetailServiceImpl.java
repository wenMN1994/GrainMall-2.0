package com.grain.mall.ware.service.impl;

import com.grain.common.utils.R;
import com.grain.mall.ware.feign.ProductFeignService;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grain.common.utils.PageUtils;
import com.grain.common.utils.Query;

import com.grain.mall.ware.dao.PurchaseDetailDao;
import com.grain.mall.ware.entity.PurchaseDetailEntity;
import com.grain.mall.ware.service.PurchaseDetailService;
import org.springframework.util.StringUtils;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Autowired
    ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        /**
         *    key: '华为',//检索关键字
         *    status: 0,//状态
         *    wareId: 1,//仓库id
         */
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            queryWrapper.and(wrapper->{
                wrapper.eq("purchase_id", key).or().eq("sku_id", key);
            });
        }

        String status = (String) params.get("status");
        if(!StringUtils.isEmpty(status)){
            queryWrapper.eq("status", status);
        }

        String wareId = (String) params.get("wareId");
        if(!StringUtils.isEmpty(wareId)){
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> listDetailByPurchaseId(Long id) {
        List<PurchaseDetailEntity> purchaseId = this.list(new QueryWrapper<PurchaseDetailEntity>().eq("purchase_id", id));

        return purchaseId;
    }

    @Override
    public void savePurchaseDetail(PurchaseDetailEntity purchaseDetail) {

        // TODO 统计SKU采购金额（业务逻辑有误，待修复）
        CalculateSkuPurchasePrice(purchaseDetail);

        this.save(purchaseDetail);
    }

    @Override
    public void updatePurchaseDetailById(PurchaseDetailEntity purchaseDetail) {
        CalculateSkuPurchasePrice(purchaseDetail);

        this.updateById(purchaseDetail);
    }

    private void CalculateSkuPurchasePrice(PurchaseDetailEntity purchaseDetail) {
        R info = productFeignService.info(purchaseDetail.getSkuId());
        Map<String,Object> data = (Map<String, Object>) info.get("skuInfo");

        if(info.getCode() == 0){
            BigDecimal skuPrice = new BigDecimal(data.get("price").toString());
            BigDecimal skuNum = new BigDecimal(purchaseDetail.getSkuNum().toString());
            purchaseDetail.setSkuPrice(skuPrice.multiply(skuNum));
        }
    }

}