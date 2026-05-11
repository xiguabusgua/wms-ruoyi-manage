package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.CustomerBo;
import com.ruoyi.wms.domain.entity.Customer;
import com.ruoyi.wms.domain.entity.ItemCategory;
import com.ruoyi.wms.domain.vo.CustomerVo;
import com.ruoyi.wms.mapper.CustomerMapper;
import com.ruoyi.wms.mapper.ItemCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 客户档案Service业务层处理
 *
 * @author zcc
 */
@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerContactService customerContactService;
    private final CustomerAddressService customerAddressService;
    private final ItemCategoryMapper itemCategoryMapper;

    /**
     * 查询客户详情（含联系人列表、地址列表、分类名称填充）
     */
    public CustomerVo queryById(Long id) {
        CustomerVo vo = customerMapper.selectVoById(id);
        if (vo == null) {
            return null;
        }
        fillCustomerDetail(vo);
        return vo;
    }

    /**
     * 分页查询客户列表
     */
    public TableDataInfo<CustomerVo> queryPageList(CustomerBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Customer> lqw = buildQueryWrapper(bo);
        Page<CustomerVo> result = customerMapper.selectVoPage(pageQuery.build(), lqw);
        fillCustomerCategoryName(result.getRecords());
        return TableDataInfo.build(result);
    }

    /**
     * 查询客户列表
     */
    public List<CustomerVo> queryList(CustomerBo bo) {
        LambdaQueryWrapper<Customer> lqw = buildQueryWrapper(bo);
        List<CustomerVo> list = customerMapper.selectVoList(lqw);
        fillCustomerCategoryName(list);
        return list;
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<Customer> buildQueryWrapper(CustomerBo bo) {
        LambdaQueryWrapper<Customer> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getCustomerCode()), Customer::getCustomerCode, bo.getCustomerCode());
        lqw.like(StringUtils.isNotBlank(bo.getCustomerName()), Customer::getCustomerName, bo.getCustomerName());
        lqw.eq(bo.getCustomerId() != null, Customer::getCustomerId, bo.getCustomerId());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), Customer::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增客户（含级联保存联系人和地址）
     */
    public void insertByBo(CustomerBo bo) {
        validateCustomerCode(bo);
        Customer add = MapstructUtils.convert(bo, Customer.class);
        customerMapper.insert(add);
        Long customerId = add.getId();
        cascadeSaveContactsAndAddresses(customerId, bo);
    }

    /**
     * 修改客户（含级联更新联系人和地址）
     */
    public void updateByBo(CustomerBo bo) {
        validateCustomerCode(bo);
        Customer update = MapstructUtils.convert(bo, Customer.class);
        customerMapper.updateById(update);
        Long customerId = update.getId();
        cascadeUpdateContactsAndAddresses(customerId, bo);
    }

    /**
     * 删除客户（先删除关联联系人和地址）
     */
    public void deleteById(Long id) {
        customerContactService.deleteByCustomerId(id);
        customerAddressService.deleteByCustomerId(id);
        customerMapper.deleteById(id);
    }

    /**
     * 批量删除客户
     */
    public void deleteByIds(Collection<Long> ids) {
        for (Long id : ids) {
            customerContactService.deleteByCustomerId(id);
            customerAddressService.deleteByCustomerId(id);
        }
        customerMapper.deleteBatchIds(ids);
    }

    /**
     * 校验客户编码唯一性
     */
    public void validateCustomerCode(CustomerBo bo) {
        if (StrUtil.isBlank(bo.getCustomerCode())) {
            return;
        }
        LambdaQueryWrapper<Customer> lqw = Wrappers.lambdaQuery();
        lqw.eq(Customer::getCustomerCode, bo.getCustomerCode());
        lqw.ne(bo.getId() != null, Customer::getId, bo.getId());
        if (customerMapper.selectCount(lqw) > 0) {
            throw new ServiceException("客户编码已存在");
        }
    }

    /**
     * 填充客户详情（联系人、地址列表）
     */
    private void fillCustomerDetail(CustomerVo vo) {
        List<com.ruoyi.wms.domain.vo.CustomerContactVo> contacts = customerContactService.queryByCustomerId(vo.getId());
        vo.setContacts(CollUtil.isEmpty(contacts) ? List.of() : contacts);
        List<com.ruoyi.wms.domain.vo.CustomerAddressVo> addresses = customerAddressService.queryByCustomerId(vo.getId());
        vo.setAddresses(CollUtil.isEmpty(addresses) ? List.of() : addresses);
        fillCustomerCategoryName(List.of(vo));
    }

    /**
     * 填充客户分类名称
     */
    private void fillCustomerCategoryName(List<CustomerVo> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        for (CustomerVo vo : list) {
            if (vo.getCustomerId() != null) {
                ItemCategory itemCategory = itemCategoryMapper.selectById(vo.getCustomerId());
                if (itemCategory != null) {
                    vo.setCustomerCategoryName(itemCategory.getCategoryName());
                }
            }
        }
    }

    /**
     * 级联保存联系人和地址
     */
    private void cascadeSaveContactsAndAddresses(Long customerId, CustomerBo bo) {
        if (CollUtil.isNotEmpty(bo.getContacts())) {
            for (com.ruoyi.wms.domain.bo.CustomerContactBo contactBo : bo.getContacts()) {
                contactBo.setCustomerId(customerId);
            }
            customerContactService.saveContacts(MapstructUtils.convert(bo.getContacts(), com.ruoyi.wms.domain.entity.CustomerContact.class));
        }
        if (CollUtil.isNotEmpty(bo.getAddresses())) {
            for (com.ruoyi.wms.domain.bo.CustomerAddressBo addressBo : bo.getAddresses()) {
                addressBo.setCustomerId(customerId);
            }
            customerAddressService.saveAddresses(MapstructUtils.convert(bo.getAddresses(), com.ruoyi.wms.domain.entity.CustomerAddress.class));
        }
    }

    /**
     * 级联更新联系人和地址（先删后插）
     */
    private void cascadeUpdateContactsAndAddresses(Long customerId, CustomerBo bo) {
        customerContactService.deleteByCustomerId(customerId);
        customerAddressService.deleteByCustomerId(customerId);
        cascadeSaveContactsAndAddresses(customerId, bo);
    }

}
