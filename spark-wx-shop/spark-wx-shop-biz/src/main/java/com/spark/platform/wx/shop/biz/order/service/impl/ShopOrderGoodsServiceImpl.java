package com.spark.platform.wx.shop.biz.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.spark.platform.wx.shop.api.entity.order.ShopOrderGoods;
import com.spark.platform.wx.shop.biz.order.dao.ShopOrderGoodsDao;
import com.spark.platform.wx.shop.biz.order.service.ShopOrderGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 下单商品详情 服务实现类
 * </p>
 *
 * @author wangdingfeng
 * @since 2020-12-21
 */
@Service
public class ShopOrderGoodsServiceImpl extends ServiceImpl<ShopOrderGoodsDao, ShopOrderGoods> implements ShopOrderGoodsService {

    @Override
    public List<ShopOrderGoods> findByOrderId(Integer orderId) {
        return super.list(Wrappers.<ShopOrderGoods>lambdaQuery().eq(ShopOrderGoods::getOrderId,orderId));
    }
}
