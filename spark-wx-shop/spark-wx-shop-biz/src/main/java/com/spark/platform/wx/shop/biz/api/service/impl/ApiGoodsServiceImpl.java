package com.spark.platform.wx.shop.biz.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spark.platform.common.base.enums.DelFlagEnum;
import com.spark.platform.common.base.exception.BusinessException;
import com.spark.platform.wx.shop.api.dto.ShopGoodsQueryDTO;
import com.spark.platform.wx.shop.api.entity.goods.ShopGoods;
import com.spark.platform.wx.shop.api.entity.goods.ShopGoodsComment;
import com.spark.platform.wx.shop.api.entity.goods.ShopGoodsGallery;
import com.spark.platform.wx.shop.api.entity.goods.ShopGoodsParam;
import com.spark.platform.wx.shop.api.enums.ShopGoodsActivityEnum;
import com.spark.platform.wx.shop.api.enums.ShopGoodsStatusEnum;
import com.spark.platform.wx.shop.api.vo.GoodsCardVo;
import com.spark.platform.wx.shop.api.vo.GoodsCategoryVo;
import com.spark.platform.wx.shop.api.vo.GoodsDetailVo;
import com.spark.platform.wx.shop.biz.api.service.ApiGoodsService;
import com.spark.platform.wx.shop.biz.goods.service.ShopGoodsCategoryService;
import com.spark.platform.wx.shop.biz.goods.service.ShopGoodsCommentService;
import com.spark.platform.wx.shop.biz.goods.service.ShopGoodsService;
import com.spark.platform.wx.shop.biz.marketing.service.ShopPinkGoodsService;
import com.spark.platform.wx.shop.biz.marketing.service.ShopSeckillGoodsService;
import com.spark.platform.wx.shop.biz.user.service.ShopUserCollectService;
import com.spark.platform.wx.shop.biz.user.service.ShopUserFootprintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: spark-platform
 * @Package: com.spark.platform.wx.shop.biz.api.service.impl
 * @ClassName: ApiGoodsServiceImpl
 * @Author: wangdingfeng
 * @Description:
 * @Date: 2020/12/23 13:17
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ApiGoodsServiceImpl implements ApiGoodsService {

    private final ShopGoodsService shopGoodsService;
    private final ShopGoodsCommentService shopGoodsCommentService;
    private final ShopUserFootprintService shopUserFootprintService;
    private final ShopGoodsCategoryService shopGoodsCategoryService;
    private final ShopPinkGoodsService shopPinkGoodsService;
    private final ShopSeckillGoodsService shopSeckillGoodsService;
    private final ShopUserCollectService shopUserCollectService;

    @Override
    public IPage<List<GoodsCardVo>> list(ShopGoodsQueryDTO shopGoodsQueryDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(shopGoodsQueryDTO.getTitle()), "title", shopGoodsQueryDTO.getTitle())
                .likeRight(StringUtils.isNotBlank(shopGoodsQueryDTO.getCategoryIds()), "category_ids", shopGoodsQueryDTO.getCategoryIds())
                .eq(StringUtils.isNotBlank(shopGoodsQueryDTO.getIsNew()), "is_new", shopGoodsQueryDTO.getIsNew())
                .eq(StringUtils.isNotBlank(shopGoodsQueryDTO.getIsHot()), "is_hot", shopGoodsQueryDTO.getIsHot())
                .eq(true,"status",ShopGoodsStatusEnum.PUBLISH.getStatus())
                .eq(true,"del_flag", DelFlagEnum.NORMAL.getValue())
                .like(StringUtils.isNotBlank(shopGoodsQueryDTO.getKeywords()),"keywords",shopGoodsQueryDTO.getKeywords())
                .orderBy(StringUtils.isNotBlank(shopGoodsQueryDTO.getOrderBy()), shopGoodsQueryDTO.isAsc(), shopGoodsQueryDTO.getOrderBy());
        return shopGoodsService.pageCard(new Page(shopGoodsQueryDTO.getCurrent(), shopGoodsQueryDTO.getSize()), queryWrapper);
    }

    @Override
    public List<GoodsCardVo> related(Integer goodsId) {
        log.info("【请求开始】商品详情页面“大家都在看”推荐商品,请求参数:goodsId:{}", goodsId);
        // 查询商品信息
        ShopGoods shopGoods = shopGoodsService.getById(goodsId);
        if (null == shopGoods || !ShopGoodsStatusEnum.PUBLISH.getStatus().equals(shopGoods.getStatus())) {
            throw new BusinessException("查询不到当前商品或者当前商品已下架！");
        }
        // 推荐算法 只推荐当前类目的商品 销量前十的商品
        IPage page = shopGoodsService.pageCard(new Page(0L, 10L),
                Wrappers.<ShopGoods>lambdaQuery().eq(ShopGoods::getCategoryIds, shopGoods.getCategoryIds()).orderByDesc(ShopGoods::getSaleNum));
        log.info("【请求结束】商品详情页面“大家都在看”推荐商品,响应结果:查询的商品数量:{}", page.getTotal());
        return page.getRecords();
    }

    @Override
    public GoodsDetailVo detail(Integer userId, Integer goodsId) {
        log.info("【请求开始】商品详情页面,请求参数:goodsId:{},userId:{}", goodsId, userId);
        GoodsDetailVo goodsDetail = new GoodsDetailVo();
        if (null != userId) {
            // 如果有当前登录用户则记录用户的浏览记录
            shopUserFootprintService.saveFootprint(userId,goodsId);
            // 查询是否收藏了此商品
            goodsDetail.setIsCollect(shopUserCollectService.getCollect(userId,goodsId));
        }
        // 查询商品信息
        ShopGoods shopGoods = shopGoodsService.getShopGoods(goodsId);
        if (null == shopGoods || !ShopGoodsStatusEnum.PUBLISH.getStatus().equals(shopGoods.getStatus())) {
            throw new BusinessException("查询不到当前商品或者当前商品已下架！");
        }
        BeanUtil.copyProperties(shopGoods, goodsDetail);
        goodsDetail.setGoodsId(shopGoods.getId());
        if(ShopGoodsActivityEnum.PINK.getStatus().equals(shopGoods.getActivity())){
            // 当前商品参团中
            goodsDetail.setPinkGoods(shopPinkGoodsService.getByGoodIds(shopGoods.getId()));
        }else if(ShopGoodsActivityEnum.SECKILL.getStatus().equals(shopGoods.getActivity())){
            // 当前商品秒杀中
            goodsDetail.setSeckillGoods(shopSeckillGoodsService.getByGoodIds(shopGoods.getId()));
        }
        // 获取规格
        goodsDetail.setGoodsAttrs(shopGoods.getShopGoodsAttrs());
        // 获取价格库存
        goodsDetail.setGoodsSkus(shopGoods.getShopGoodsSkus());
        // 获取主图
        goodsDetail.setPicList(shopGoods.getShopGoodsGalleries().stream().map(ShopGoodsGallery::getUrl).collect(Collectors.toList()));
        // 获取 产品属性
        goodsDetail.setGoodsParams(shopGoods.getShopGoodsParams().stream().collect(Collectors.toMap(ShopGoodsParam::getParamName,ShopGoodsParam::getParamValue)));
        log.info("【请求结束】,获取商品结果，当前商品标题：{}", shopGoods.getTitle());
        return goodsDetail;
    }

    @Override
    public List<GoodsCategoryVo> categoryTree(Integer level) {
        // 默认查询两个层级
        level = null == level ? 2 : level;
        return shopGoodsCategoryService.treeVo(level);
    }

    @Override
    public IPage<ShopGoodsComment> pageGoodsComment(Long size, Long current, Integer goodsId) {
        ShopGoodsComment shopGoodsComment = new ShopGoodsComment();
        shopGoodsComment.setGoodsId(goodsId);
        return shopGoodsCommentService.listPage(new Page(size,current),shopGoodsComment);
    }
}
