package com.ruoyi.wms.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.CustomerComplaintBo;
import com.ruoyi.wms.domain.entity.CustomerComplaint;
import com.ruoyi.wms.domain.vo.CustomerComplaintVo;
import com.ruoyi.wms.mapper.CustomerComplaintMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerComplaintService {

    private final CustomerComplaintMapper customerComplaintMapper;

    public CustomerComplaintVo queryById(Long id) {
        CustomerComplaintVo vo = customerComplaintMapper.selectVoById(id);
        Assert.notNull(vo, "客户投诉记录不存在");
        return vo;
    }

    public TableDataInfo<CustomerComplaintVo> queryPageList(CustomerComplaintBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CustomerComplaint> lqw = buildQueryWrapper(bo);
        Page<CustomerComplaintVo> result = customerComplaintMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<CustomerComplaintVo> queryList(CustomerComplaintBo bo) {
        LambdaQueryWrapper<CustomerComplaint> lqw = buildQueryWrapper(bo);
        return customerComplaintMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CustomerComplaint> buildQueryWrapper(CustomerComplaintBo bo) {
        LambdaQueryWrapper<CustomerComplaint> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getComplaintNo()), CustomerComplaint::getComplaintNo, bo.getComplaintNo());
        lqw.eq(bo.getCustomerId() != null, CustomerComplaint::getCustomerId, bo.getCustomerId());
        lqw.eq(bo.getHandleStatus() != null, CustomerComplaint::getHandleStatus, bo.getHandleStatus());
        lqw.like(StringUtils.isNotBlank(bo.getComplaintType()), CustomerComplaint::getComplaintType, bo.getComplaintType());
        lqw.eq(bo.getOrderId() != null, CustomerComplaint::getOrderId, bo.getOrderId());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    public Long insertByBo(CustomerComplaintBo bo) {
        validateComplaintNo(bo.getComplaintNo());
        CustomerComplaint add = MapstructUtils.convert(bo, CustomerComplaint.class);
        customerComplaintMapper.insert(add);
        return add.getId();
    }

    public void updateByBo(CustomerComplaintBo bo) {
        CustomerComplaint update = MapstructUtils.convert(bo, CustomerComplaint.class);
        customerComplaintMapper.updateById(update);
    }

    public void deleteById(Long id) {
        customerComplaintMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(id -> customerComplaintMapper.deleteById(id));
    }

    public void validateComplaintNo(String complaintNo) {
        LambdaQueryWrapper<CustomerComplaint> lqw = Wrappers.lambdaQuery();
        lqw.eq(CustomerComplaint::getComplaintNo, complaintNo);
        CustomerComplaint exist = customerComplaintMapper.selectOne(lqw);
        Assert.isNull(exist, "投诉编号重复，请手动修改");
    }

}
