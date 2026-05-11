package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.SalesOrderDetailBo;
import com.ruoyi.wms.domain.entity.SalesOrderDetail;
import com.ruoyi.wms.domain.vo.SalesOrderDetailVo;
import com.ruoyi.wms.mapper.SalesOrderDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 销售订单明细Service业务层处理
 *
 * @author zcc
 */
@RequiredArgsConstructor
@Service
public class SalesOrderDetailService extends ServiceImpl<SalesOrderDetailMapper, SalesOrderDetail> {

    private final SalesOrderDetailMapper salesOrderDetailMapper;

    /**
     * 查询销售订单明细
     */
    public SalesOrderDetailVo queryById(Long id) {
        return salesOrderDetailMapper.selectVoById(id);
    }

    /**
     * 分页查询销售订单明细列表
     */
    public TableDataInfo<SalesOrderDetailVo> queryPageList(SalesOrderDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SalesOrderDetail> lqw = buildQueryWrapper(bo);
        Page<SalesOrderDetailVo> result = salesOrderDetailMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询销售订单明细列表
     */
    public List<SalesOrderDetailVo> queryList(SalesOrderDetailBo bo) {
        LambdaQueryWrapper<SalesOrderDetail> lqw = buildQueryWrapper(bo);
        return salesOrderDetailMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SalesOrderDetail> buildQueryWrapper(SalesOrderDetailBo bo) {
        LambdaQueryWrapper<SalesOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getOrderId() != null, SalesOrderDetail::getOrderId, bo.getOrderId());
        lqw.eq(bo.getSkuId() != null, SalesOrderDetail::getSkuId, bo.getSkuId());
        lqw.eq(bo.getQuantity() != null, SalesOrderDetail::getQuantity, bo.getQuantity());
        lqw.eq(bo.getUnitPrice() != null, SalesOrderDetail::getUnitPrice, bo.getUnitPrice());
        return lqw;
    }

    /**
     * 新增销售订单明细
     */
    public void insertByBo(SalesOrderDetailBo bo) {
        SalesOrderDetail add = MapstructUtils.convert(bo, SalesOrderDetail.class);
        salesOrderDetailMapper.insert(add);
    }

    /**
     * 修改销售订单明细
     */
    public void updateByBo(SalesOrderDetailBo bo) {
        SalesOrderDetail update = MapstructUtils.convert(bo, SalesOrderDetail.class);
        salesOrderDetailMapper.updateById(update);
    }

    /**
     * 批量删除销售订单明细
     */
    public void deleteByIds(Collection<Long> ids) {
        salesOrderDetailMapper.deleteBatchIds(ids);
    }

    /**
     * 保存明细列表（批量保存或更新）
     */
    @Transactional
    public void saveDetails(List<SalesOrderDetail> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        saveOrUpdateBatch(list);
    }

    /**
     * 根据销售订单ID查询明细列表
     */
    public List<SalesOrderDetailVo> queryByOrderId(Long orderId) {
        SalesOrderDetailBo bo = new SalesOrderDetailBo();
        bo.setOrderId(orderId);
        List<SalesOrderDetailVo> details = queryList(bo);
        if (CollUtil.isEmpty(details)) {
            return Collections.emptyList();
        }
        return details;
    }

    /**
     * 根据销售订单ID删除明细
     */
    public void deleteByOrderId(@jakarta.validation.constraints.NotNull Long orderId) {
        LambdaQueryWrapper<SalesOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(SalesOrderDetail::getOrderId, orderId);
        salesOrderDetailMapper.delete(lqw);
    }

}
