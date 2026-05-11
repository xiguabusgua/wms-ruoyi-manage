package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.CustomerAddressBo;
import com.ruoyi.wms.domain.entity.CustomerAddress;
import com.ruoyi.wms.domain.vo.CustomerAddressVo;
import com.ruoyi.wms.mapper.CustomerAddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 客户收货地址Service业务层处理
 *
 * @author zcc
 */
@RequiredArgsConstructor
@Service
public class CustomerAddressService extends ServiceImpl<CustomerAddressMapper, CustomerAddress> {

    private final CustomerAddressMapper customerAddressMapper;

    /**
     * 查询客户收货地址
     */
    public CustomerAddressVo queryById(Long id) {
        return customerAddressMapper.selectVoById(id);
    }

    /**
     * 分页查询客户收货地址列表
     */
    public TableDataInfo<CustomerAddressVo> queryPageList(CustomerAddressBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CustomerAddress> lqw = buildQueryWrapper(bo);
        Page<CustomerAddressVo> result = customerAddressMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询客户收货地址列表
     */
    public List<CustomerAddressVo> queryList(CustomerAddressBo bo) {
        LambdaQueryWrapper<CustomerAddress> lqw = buildQueryWrapper(bo);
        return customerAddressMapper.selectVoList(lqw);
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<CustomerAddress> buildQueryWrapper(CustomerAddressBo bo) {
        LambdaQueryWrapper<CustomerAddress> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCustomerId() != null, CustomerAddress::getCustomerId, bo.getCustomerId());
        lqw.like(StringUtils.isNotBlank(bo.getContactPerson()), CustomerAddress::getContactPerson, bo.getContactPerson());
        lqw.eq(StringUtils.isNotBlank(bo.getIsDefault()), CustomerAddress::getIsDefault, bo.getIsDefault());
        return lqw;
    }

    /**
     * 新增客户收货地址
     */
    public void insertByBo(CustomerAddressBo bo) {
        CustomerAddress add = MapstructUtils.convert(bo, CustomerAddress.class);
        customerAddressMapper.insert(add);
    }

    /**
     * 修改客户收货地址
     */
    public void updateByBo(CustomerAddressBo bo) {
        CustomerAddress update = MapstructUtils.convert(bo, CustomerAddress.class);
        customerAddressMapper.updateById(update);
    }

    /**
     * 批量删除客户收货地址
     */
    public void deleteByIds(Collection<Long> ids) {
        customerAddressMapper.deleteBatchIds(ids);
    }

    /**
     * 批量保存客户收货地址
     */
    public void saveAddresses(List<CustomerAddress> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        saveOrUpdateBatch(list);
    }

    /**
     * 根据客户ID查询收货地址列表
     */
    public List<CustomerAddressVo> queryByCustomerId(Long customerId) {
        if (customerId == null) {
            return Collections.emptyList();
        }
        CustomerAddressBo bo = new CustomerAddressBo();
        bo.setCustomerId(customerId);
        List<CustomerAddressVo> list = queryList(bo);
        return CollUtil.isEmpty(list) ? Collections.emptyList() : list;
    }

    /**
     * 根据客户ID删除所有关联收货地址
     */
    public void deleteByCustomerId(@jakarta.validation.constraints.NotNull Long customerId) {
        LambdaQueryWrapper<CustomerAddress> lqw = Wrappers.lambdaQuery();
        lqw.eq(CustomerAddress::getCustomerId, customerId);
        customerAddressMapper.delete(lqw);
    }

}
