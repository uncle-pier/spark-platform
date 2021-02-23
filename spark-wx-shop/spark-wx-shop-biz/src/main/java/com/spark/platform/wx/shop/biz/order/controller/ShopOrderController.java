package com.spark.platform.wx.shop.biz.order.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spark.platform.wx.shop.api.entity.order.ShopOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spark.platform.wx.shop.biz.order.service.ShopOrderService;
import com.spark.platform.common.base.support.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spark.platform.common.base.support.BaseController;

import java.util.Map;

/**
 * <p>
 * 订单管理 api访问层
 * </p>
 *
 * @author wangdingfeng
 * @since 2020-12-21
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单管理")
@RequiredArgsConstructor
public class ShopOrderController extends BaseController {

    private final ShopOrderService shopOrderService;

    @GetMapping("/page")
    @ApiOperation(value = "订单管理列表")
    public ApiResponse page(ShopOrder shopOrder, Page page) {
        return success(shopOrderService.findPage(page, shopOrder));
    }

    @PostMapping
    @ApiOperation(value = "保存订单管理信息")
    public ApiResponse save(@RequestBody ShopOrder shopOrder) {
        return success(shopOrderService.save(shopOrder));
    }

    @PutMapping
    @ApiOperation(value = "更新订单管理信息")
    public ApiResponse update(@RequestBody ShopOrder shopOrder) {
        return success(shopOrderService.updateById(shopOrder));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除订单管理")
    public ApiResponse delete(@PathVariable Long id) {
        return success(shopOrderService.removeById(id));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取订单管理信息")
    public ApiResponse<ShopOrder> getById(@PathVariable Integer id) {
        return success(shopOrderService.getOrder(id));
    }


    @PutMapping("/send")
    @ApiOperation(value = "订单发货")
    public ApiResponse send(@RequestParam Integer id, @RequestParam String shipperName,
                            @RequestParam String shipperCode, @RequestParam String logisticCode) {
        return success(shopOrderService.send(id, shipperName, shipperCode, logisticCode));
    }

    @PutMapping("/{id}/cancel")
    @ApiOperation(value = "取消订单")
    public ApiResponse cancel(@PathVariable Integer id) {
        return success(shopOrderService.cancel(id));
    }

    @GetMapping("/count")
    @ApiOperation(value = "计算每个状态下总数量")
    public ApiResponse<Map> count(@RequestParam String orderType){
        return success(shopOrderService.statusCount(orderType));
    }


}
