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
import com.ruoyi.wms.domain.bo.ProductionPlanBo;
import com.ruoyi.wms.domain.entity.ProductionPlan;
import com.ruoyi.wms.domain.vo.ProductionPlanVo;
import com.ruoyi.wms.mapper.ProductionPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * 生产计划Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class ProductionPlanService {

    private final ProductionPlanMapper productionPlanMapper;

    /**
     * 计划状态常量
     */
    public static class PlanStatus {
        /** 待排产 */
        public static final Integer PENDING = 0;
        /** 已排产 */
        public static final Integer SCHEDULED = 1;
        /** 生产中 */
        public static final Integer IN_PRODUCTION = 2;
        /** 已完成 */
        public static final Integer COMPLETED = 3;
        /** 已关闭 */
        public static final Integer CLOSED = 4;
    }

    /**
     * 查询生产计划
     */
    public ProductionPlanVo queryById(Long id) {
        return productionPlanMapper.selectVoById(id);
    }

    /**
     * 查询生产计划列表
     */
    public TableDataInfo<ProductionPlanVo> queryPageList(ProductionPlanBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ProductionPlan> lqw = buildQueryWrapper(bo);
        Page<ProductionPlanVo> result = productionPlanMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询生产计划列表
     */
    public java.util.List<ProductionPlanVo> queryList(ProductionPlanBo bo) {
        LambdaQueryWrapper<ProductionPlan> lqw = buildQueryWrapper(bo);
        return productionPlanMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ProductionPlan> buildQueryWrapper(ProductionPlanBo bo) {
        LambdaQueryWrapper<ProductionPlan> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPlanNo()), ProductionPlan::getPlanNo, bo.getPlanNo());
        lqw.eq(bo.getSalesOrderId() != null, ProductionPlan::getSalesOrderId, bo.getSalesOrderId());
        lqw.eq(bo.getProductId() != null, ProductionPlan::getProductId, bo.getProductId());
        lqw.eq(bo.getPriority() != null, ProductionPlan::getPriority, bo.getPriority());
        lqw.eq(bo.getPlanStatus() != null, ProductionPlan::getPlanStatus, bo.getPlanStatus());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增生产计划
     */
    @Transactional
    public Long insertByBo(ProductionPlanBo bo) {
        validatePlanNo(bo.getPlanNo());
        if (bo.getProducedQuantity() == null) {
            bo.setProducedQuantity(BigDecimal.ZERO);
        }
        if (bo.getPlanStatus() == null) {
            bo.setPlanStatus(PlanStatus.PENDING);
        }
        ProductionPlan add = MapstructUtils.convert(bo, ProductionPlan.class);
        productionPlanMapper.insert(add);
        return add.getId();
    }

    /**
     * 从销售订单生成生产计划
     */
    @Transactional
    public Long generateFromSalesOrder(Long salesOrderId, Long productId, BigDecimal planQuantity,
                                        LocalDate planStartDate, LocalDate planEndDate, Integer priority) {
        Assert.notNull(salesOrderId, "销售订单ID不能为空");
        Assert.notNull(productId, "物料ID不能为空");
        Assert.notNull(planQuantity, "计划数量不能为空");
        ProductionPlan plan = new ProductionPlan();
        plan.setPlanNo(generatePlanNo());
        plan.setSalesOrderId(salesOrderId);
        plan.setProductId(productId);
        plan.setPlanQuantity(planQuantity);
        plan.setProducedQuantity(BigDecimal.ZERO);
        plan.setPlanStartDate(planStartDate);
        plan.setPlanEndDate(planEndDate);
        plan.setPriority(priority != null ? priority : 2);
        plan.setPlanStatus(PlanStatus.PENDING);
        productionPlanMapper.insert(plan);
        return plan.getId();
    }

    /**
     * 生成计划编号
     */
    private String generatePlanNo() {
        return "PP" + System.currentTimeMillis();
    }

    /**
     * 修改生产计划
     */
    @Transactional
    public void updateByBo(ProductionPlanBo bo) {
        ProductionPlan update = MapstructUtils.convert(bo, ProductionPlan.class);
        productionPlanMapper.updateById(update);
    }

    /**
     * 状态流转：待排产 → 已排产
     */
    @Transactional
    public void schedule(Long id) {
        ProductionPlanVo vo = queryById(id);
        Assert.notNull(vo, "生产计划不存在");
        if (!PlanStatus.PENDING.equals(vo.getPlanStatus())) {
            throw new ServiceException("只有待排产状态的计划可以排产");
        }
        LambdaUpdateWrapper<ProductionPlan> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductionPlan::getId, id);
        wrapper.set(ProductionPlan::getPlanStatus, PlanStatus.SCHEDULED);
        productionPlanMapper.update(null, wrapper);
    }

    /**
     * 状态流转：已排产 → 生产中
     */
    @Transactional
    public void startProduction(Long id) {
        ProductionPlanVo vo = queryById(id);
        Assert.notNull(vo, "生产计划不存在");
        if (!PlanStatus.SCHEDULED.equals(vo.getPlanStatus())) {
            throw new ServiceException("只有已排产状态的计划可以开始生产");
        }
        LambdaUpdateWrapper<ProductionPlan> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductionPlan::getId, id);
        wrapper.set(ProductionPlan::getPlanStatus, PlanStatus.IN_PRODUCTION);
        productionPlanMapper.update(null, wrapper);
    }

    /**
     * 状态流转：生产中 → 已完成
     */
    @Transactional
    public void complete(Long id) {
        ProductionPlanVo vo = queryById(id);
        Assert.notNull(vo, "生产计划不存在");
        if (!PlanStatus.IN_PRODUCTION.equals(vo.getPlanStatus())) {
            throw new ServiceException("只有生产中状态的计划可以标记为已完成");
        }
        LambdaUpdateWrapper<ProductionPlan> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductionPlan::getId, id);
        wrapper.set(ProductionPlan::getPlanStatus, PlanStatus.COMPLETED);
        productionPlanMapper.update(null, wrapper);
    }

    /**
     * 状态流转：任意状态 → 已关闭（待排产/已排产/生产中均可关闭）
     */
    @Transactional
    public void close(Long id) {
        ProductionPlanVo vo = queryById(id);
        Assert.notNull(vo, "生产计划不存在");
        if (PlanStatus.COMPLETED.equals(vo.getPlanStatus()) || PlanStatus.CLOSED.equals(vo.getPlanStatus())) {
            throw new ServiceException("已完成或已关闭的计划无法再次关闭");
        }
        LambdaUpdateWrapper<ProductionPlan> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductionPlan::getId, id);
        wrapper.set(ProductionPlan::getPlanStatus, PlanStatus.CLOSED);
        productionPlanMapper.update(null, wrapper);
    }

    /**
     * 删除生产计划
     */
    public void deleteById(Long id) {
        validateBeforeDelete(id);
        productionPlanMapper.deleteById(id);
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(this::deleteById);
    }

    private void validateBeforeDelete(Long id) {
        ProductionPlanVo vo = queryById(id);
        Assert.notNull(vo, "生产计划不存在");
        Integer status = vo.getPlanStatus();
        if (PlanStatus.IN_PRODUCTION.equals(status)) {
            throw new ServiceException("删除失败", null, "生产计划【" + vo.getPlanNo() + "】正在生产中，无法删除！");
        }
        if (PlanStatus.COMPLETED.equals(status)) {
            throw new ServiceException("删除失败", null, "生产计划【" + vo.getPlanNo() + "】已完成，无法删除！");
        }
    }

    private void validatePlanNo(String planNo) {
        LambdaQueryWrapper<ProductionPlan> lqw = Wrappers.lambdaQuery();
        lqw.eq(ProductionPlan::getPlanNo, planNo);
        ProductionPlan exist = productionPlanMapper.selectOne(lqw);
        Assert.isNull(exist, "计划编号重复，请重新输入");
    }

}
