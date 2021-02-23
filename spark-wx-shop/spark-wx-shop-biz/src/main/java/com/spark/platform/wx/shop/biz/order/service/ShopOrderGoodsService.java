package com.spark.platform.wx.shop.biz.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spark.platform.wx.shop.api.entity.order.ShopOrderGoods;

import java.util.List;

/**
 * <p>
 * 下单商品详情 服务类
 * </p>
 *
 * @author wangdingfeng
 * @since 2020-12-21
 */
public interface ShopOrderGoodsService extends IService<ShopOrderGoods> {
    /**
     * 根据订单ID 查询数据
     * @param orderId
     * @return
     */
    List<ShopOrderGoods> findByOrderId(Integer orderId);
}
