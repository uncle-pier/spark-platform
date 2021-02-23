package com.spark.platform.wx.shop.biz.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spark.platform.wx.shop.api.entity.order.ShopOrderRefund;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spark.platform.wx.shop.biz.order.service.ShopOrderRefundService;
import com.spark.platform.common.base.support.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spark.platform.common.base.support.BaseController;

/**
 * <p>
 * 退款管理 api访问层
 * </p>
 *
 * @author wangdingfeng
 * @since 2021-01-07
 */
@RestController
@RequestMapping("/order/refund")
@Api(tags = "退款管理")
@RequiredArgsConstructor
public class ShopOrderRefundController extends BaseController {

    private final ShopOrderRefundService shopOrderRefundService;

    @GetMapping("/page")
    @ApiOperation(value = "退款管理列表")
    public ApiResponse page(ShopOrderRefund shopOrderRefund, Page page) {
        return success(shopOrderRefundService.findPage(page, shopOrderRefund));
    }

    @PostMapping
    @ApiOperation(value = "保存退款")
    public ApiResponse refund(@RequestBody ShopOrderRefund shopOrderRefund) {
        return success(shopOrderRefundService.save(shopOrderRefund));
    }

    @PutMapping("/confirm")
    @ApiOperation(value = "确认退款信息")
    public ApiResponse confirm(@RequestParam Integer id,@RequestParam boolean isAgree,@RequestParam String refusedReason) {
        return success(shopOrderRefundService.refund(id,isAgree,refusedReason));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除退款管理")
    public ApiResponse delete(@PathVariable Long id) {
        return success(shopOrderRefundService.removeById(id));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取退款管理信息")
    public ApiResponse getById(@PathVariable Long id) {
        return success(shopOrderRefundService.getById(id));
    }

}
