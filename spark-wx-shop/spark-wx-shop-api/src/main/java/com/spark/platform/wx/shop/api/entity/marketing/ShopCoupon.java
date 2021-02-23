package com.spark.platform.wx.shop.api.entity.marketing;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.spark.platform.common.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 优惠券
 * </p>
 *
 * @author wangdingfeng
 * @since 2021-01-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ShopCoupon对象", description="优惠券")
public class ShopCoupon extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "优惠券类型 新人券 现金券 满减券 折扣券")
    private String couponType;

    @ApiModelProperty(value = "面额 优惠多少钱和打多少折")
    private BigDecimal denomination;

    @ApiModelProperty(value = "固定面额 即 大于多少开始优惠")
    private BigDecimal fixedDenomination;

    @ApiModelProperty(value = "开始时间 ")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "是否限量")
    private Boolean isLimited;

    @ApiModelProperty(value = "发放总数")
    private Integer total;

    @ApiModelProperty(value = "剩余总量")
    private Integer lastTotal;

    @ApiModelProperty(value = "状态 0 未开始 1进行中 2已结束")
    private Integer status;


}
