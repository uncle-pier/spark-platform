package com.spark.platform.wx.shop.biz.marketing.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spark.platform.wx.shop.api.entity.marketing.ShopCoupon;
import com.spark.platform.wx.shop.api.vo.CouponCardVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 优惠券 Mapper 接口
 * </p>
 *
 * @author wangdingfeng
 * @since 2021-01-03
 */
public interface ShopCouponDao extends BaseMapper<ShopCoupon> {

    /**
     * 查询当前可用的优惠券
     * @return
     */
    @Select("SELECT * FROM shop_coupon WHERE status= 1 AND del_flag=0 AND end_time > NOW() LIMIT #{limit}")
    @ResultType(CouponCardVo.class)
    List<CouponCardVo> findUseVo(@Param("limit") Integer limit);

    /**
     * 更新数量
     * @param id
     * @return
     */
    @Update("UPDATE shop_coupon set last_total=last_total-1 WHERE id=#{id} AND last_total-1 > 0")
    boolean updateLastTotal(@Param("id")Integer id);


}
