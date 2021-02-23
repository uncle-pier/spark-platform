package com.spark.platform.wx.shop.api.entity.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员购物车
 * </p>
 *
 * @author wangdingfeng
 * @since 2020-12-18
 */
@Data
@Accessors(chain = true)
@TableName("shop_user_cart")
@ApiModel(value="ShopUserCart对象", description="会员购物车")
public class ShopUserCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员ID")
    @NotNull(message = "会员不能为空")
    private Integer userId;

    @ApiModelProperty(value = "商品ID")
    @NotNull(message = "商品ID不能为空")
    private Integer goodsId;

    @ApiModelProperty(value = "规格搭配")
    @NotNull(message = "规格搭配不能为空")
    private String attrValIds;

    @ApiModelProperty(value = "规格搭配翻译")
    private String attrVals;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic(value = "0",delval = "1")
    @ApiModelProperty(value = "系统状态")
    private Integer delFlag;

    @ApiModelProperty(value = "商品标题")
    private transient String goodsTitle;
    @ApiModelProperty(value = "商品图片")
    private transient String homePic;
    @ApiModelProperty(value = "商品价格")
    private transient BigDecimal price;


}
