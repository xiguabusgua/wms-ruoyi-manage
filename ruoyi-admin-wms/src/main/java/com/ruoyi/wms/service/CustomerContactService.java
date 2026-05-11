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
import com.ruoyi.wms.domain.bo.CustomerContactBo;
import com.ruoyi.wms.domain.entity.CustomerContact;
import com.ruoyi.wms.domain.vo.CustomerContactVo;
import com.ruoyi.wms.mapper.CustomerContactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 客户联系人Service业务层处理
 *
 * @author zcc
 */
@RequiredArgsConstructor
@Service
public class CustomerContactService extends ServiceImpl<CustomerContactMapper, CustomerContact> {

    private final CustomerContactMapper customerContactMapper;

    /**
     * 查询客户联系人
     */
    public CustomerContactVo queryById(Long id) {
        return customerContactMapper.selectVoById(id);
    }

    /**
     * 分页查询客户联系人列表
     */
    public TableDataInfo<CustomerContactVo> queryPageList(CustomerContactBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CustomerContact> lqw = buildQueryWrapper(bo);
        Page<CustomerContactVo> result = customerContactMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询客户联系人列表
     */
    public List<CustomerContactVo> queryList(CustomerContactBo bo) {
        LambdaQueryWrapper<CustomerContact> lqw = buildQueryWrapper(bo);
        return customerContactMapper.selectVoList(lqw);
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<CustomerContact> buildQueryWrapper(CustomerContactBo bo) {
        LambdaQueryWrapper<CustomerContact> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCustomerId() != null, CustomerContact::getCustomerId, bo.getCustomerId());
        lqw.like(StringUtils.isNotBlank(bo.getContactName()), CustomerContact::getContactName, bo.getContactName());
        lqw.eq(StringUtils.isNotBlank(bo.getMobile()), CustomerContact::getMobile, bo.getMobile());
        return lqw;
    }

    /**
     * 新增客户联系人
     */
    public void insertByBo(CustomerContactBo bo) {
        CustomerContact add = MapstructUtils.convert(bo, CustomerContact.class);
        customerContactMapper.insert(add);
    }

    /**
     * 修改客户联系人
     */
    public void updateByBo(CustomerContactBo bo) {
        CustomerContact update = MapstructUtils.convert(bo, CustomerContact.class);
        customerContactMapper.updateById(update);
    }

    /**
     * 批量删除客户联系人
     */
    public void deleteByIds(Collection<Long> ids) {
        customerContactMapper.deleteBatchIds(ids);
    }

    /**
     * 批量保存客户联系人
     */
    public void saveContacts(List<CustomerContact> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        saveOrUpdateBatch(list);
    }

    /**
     * 根据客户ID查询联系人列表
     */
    public List<CustomerContactVo> queryByCustomerId(Long customerId) {
        if (customerId == null) {
            return Collections.emptyList();
        }
        CustomerContactBo bo = new CustomerContactBo();
        bo.setCustomerId(customerId);
        List<CustomerContactVo> list = queryList(bo);
        return CollUtil.isEmpty(list) ? Collections.emptyList() : list;
    }

    /**
     * 根据客户ID删除所有关联联系人
     */
    public void deleteByCustomerId(@jakarta.validation.constraints.NotNull Long customerId) {
        LambdaQueryWrapper<CustomerContact> lqw = Wrappers.lambdaQuery();
        lqw.eq(CustomerContact::getCustomerId, customerId);
        customerContactMapper.delete(lqw);
    }

}
