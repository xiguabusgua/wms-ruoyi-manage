package com.ruoyi.wms.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.ProductionExceptionBo;
import com.ruoyi.wms.domain.entity.ProductionException;
import com.ruoyi.wms.domain.vo.ProductionExceptionVo;
import com.ruoyi.wms.mapper.ProductionExceptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 异常上报Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class ProductionExceptionService {

    private final ProductionExceptionMapper productionExceptionMapper;

    /**
     * 异常类型常量
     */
    public static class ExceptionType {
        /** 设备故障 */
        public static final Integer EQUIPMENT_FAILURE = 1;
        /** 缺料 */
        public static final Integer MATERIAL_SHORTAGE = 2;
        /** 质量 */
        public static final Integer QUALITY_ISSUE = 3;
        /** 其他 */
        public static final Integer OTHER = 4;
    }

    /**
     * 处理状态常量
     */
    public static class HandleStatus {
        /** 未处理 */
        public static final Integer PENDING = 0;
        /** 处理中 */
        public static final Integer PROCESSING = 1;
        /** 已解决 */
        public static final Integer RESOLVED = 2;
    }

    /**
     * 查询异常上报
     */
    public ProductionExceptionVo queryById(Long id) {
        return productionExceptionMapper.selectVoById(id);
    }

    /**
     * 查询异常上报列表
     */
    public TableDataInfo<ProductionExceptionVo> queryPageList(ProductionExceptionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ProductionException> lqw = buildQueryWrapper(bo);
        Page<ProductionExceptionVo> result = productionExceptionMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常上报列表
     */
    public java.util.List<ProductionExceptionVo> queryList(ProductionExceptionBo bo) {
        LambdaQueryWrapper<ProductionException> lqw = buildQueryWrapper(bo);
        return productionExceptionMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ProductionException> buildQueryWrapper(ProductionExceptionBo bo) {
        LambdaQueryWrapper<ProductionException> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getWorkOrderId() != null, ProductionException::getWorkOrderId, bo.getWorkOrderId());
        lqw.eq(bo.getExceptionType() != null, ProductionException::getExceptionType, bo.getExceptionType());
        lqw.eq(bo.getHandleStatus() != null, ProductionException::getHandleStatus, bo.getHandleStatus());
        lqw.like(StringUtils.isNotBlank(bo.getReportBy()), ProductionException::getReportBy, bo.getReportBy());
        lqw.like(StringUtils.isNotBlank(bo.getExceptionDesc()), ProductionException::getExceptionDesc, bo.getExceptionDesc());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增异常上报
     */
    @Transactional
    public Long insertByBo(ProductionExceptionBo bo) {
        if (bo.getReportTime() == null) {
            bo.setReportTime(LocalDateTime.now());
        }
        if (bo.getHandleStatus() == null) {
            bo.setHandleStatus(HandleStatus.PENDING);
        }
        ProductionException add = MapstructUtils.convert(bo, ProductionException.class);
        productionExceptionMapper.insert(add);
        return add.getId();
    }

    /**
     * 修改异常上报
     */
    @Transactional
    public void updateByBo(ProductionExceptionBo bo) {
        ProductionException update = MapstructUtils.convert(bo, ProductionException.class);
        productionExceptionMapper.updateById(update);
    }

    /**
     * 开始处理 - 未处理→处理中
     */
    @Transactional
    public void startHandle(Long id) {
        ProductionExceptionVo vo = queryById(id);
        Assert.notNull(vo, "异常记录不存在");
        if (!HandleStatus.PENDING.equals(vo.getHandleStatus())) {
            throw new ServiceException("只有未处理的异常可以开始处理");
        }
        LambdaUpdateWrapper<ProductionException> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductionException::getId, id);
        wrapper.set(ProductionException::getHandleStatus, HandleStatus.PROCESSING);
        productionExceptionMapper.update(null, wrapper);
    }

    /**
     * 解决 - 处理中→已解决
     */
    @Transactional
    public void resolve(Long id, String handleResult) {
        ProductionExceptionVo vo = queryById(id);
        Assert.notNull(vo, "异常记录不存在");
        if (!HandleStatus.PROCESSING.equals(vo.getHandleStatus())) {
            throw new ServiceException("只有处理中的异常可以标记为已解决");
        }
        LambdaUpdateWrapper<ProductionException> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductionException::getId, id);
        wrapper.set(ProductionException::getHandleStatus, HandleStatus.RESOLVED);
        wrapper.set(ProductionException::getHandleResult, handleResult);
        productionExceptionMapper.update(null, wrapper);
    }

    /**
     * 关闭/取消解决 - 处理中→未处理（回退）
     */
    @Transactional
    public void cancelHandle(Long id) {
        ProductionExceptionVo vo = queryById(id);
        Assert.notNull(vo, "异常记录不存在");
        if (!HandleStatus.PROCESSING.equals(vo.getHandleStatus())) {
            throw new ServiceException("只有处理中的异常可以取消处理");
        }
        LambdaUpdateWrapper<ProductionException> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductionException::getId, id);
        wrapper.set(ProductionException::getHandleStatus, HandleStatus.PENDING);
        productionExceptionMapper.update(null, wrapper);
    }

    /**
     * 删除异常上报
     */
    public void deleteById(Long id) {
        productionExceptionMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        productionExceptionMapper.deleteByIds(ids);
    }

}
