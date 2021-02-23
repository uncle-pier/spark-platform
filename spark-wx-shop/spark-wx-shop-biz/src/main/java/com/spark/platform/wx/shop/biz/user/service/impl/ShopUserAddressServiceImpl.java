package com.spark.platform.wx.shop.biz.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spark.platform.common.base.exception.BusinessException;
import com.spark.platform.wx.shop.api.entity.user.ShopUserAddress;
import com.spark.platform.wx.shop.biz.user.dao.ShopUserAddressDao;
import com.spark.platform.wx.shop.biz.user.service.ShopUserAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author wangdingfeng
 * @since 2020-12-10
 */
@Service
@Slf4j
public class ShopUserAddressServiceImpl extends ServiceImpl<ShopUserAddressDao, ShopUserAddress> implements ShopUserAddressService {

    @Override
    public List<ShopUserAddress> findAddress(Integer userId, Boolean isDefault) {
        return super.list(Wrappers.<ShopUserAddress>lambdaQuery()
                .eq(ShopUserAddress::getUserId,userId).eq(null != isDefault,ShopUserAddress::getIsDefault,isDefault).orderByDesc(ShopUserAddress::getIsDefault));
    }

    @Override
    public boolean deleteAddress(Integer userId, Integer id) {
        log.info("【地址信息=>删除】,用户:{}",userId);
        // 只允许当前用户删除自己的地址
        int count = super.count(Wrappers.<ShopUserAddress>lambdaQuery().eq(ShopUserAddress::getUserId,userId).eq(ShopUserAddress::getId,id));
        if(count == 0){
            throw new BusinessException("只允许删除自己的地址信息！");
        }
        return super.removeById(id);
    }

    @Override
    public boolean submitAddress(ShopUserAddress userAddress) {
        if(userAddress.getIsDefault()){
            // 如果是默认地址 修改其他的地址为费默认
            ShopUserAddress updateDate = new ShopUserAddress();
            updateDate.setIsDefault(false);
            super.update(updateDate,Wrappers.<ShopUserAddress>lambdaQuery().eq(ShopUserAddress::getUserId,userAddress.getUserId()));
        }
        return super.saveOrUpdate(userAddress);
    }
}
