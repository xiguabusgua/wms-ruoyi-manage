package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.exception.base.BaseException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.ProcessRouteBo;
import com.ruoyi.wms.domain.bo.RouteStepBo;
import com.ruoyi.wms.domain.entity.ProcessRoute;
import com.ruoyi.wms.domain.entity.RouteStep;
import com.ruoyi.wms.domain.vo.ProcessRouteVo;
import com.ruoyi.wms.domain.vo.RouteStepVo;
import com.ruoyi.wms.mapper.ProcessRouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 工艺路线主表Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class ProcessRouteService {

    private final ProcessRouteMapper processRouteMapper;
    private final RouteStepService routeStepService;

    /**
     * 查询工艺路线（含工序步骤）
     */
    public ProcessRouteVo queryById(Long id) {
        ProcessRouteVo vo = processRouteMapper.selectVoById(id);
        Assert.notNull(vo, "工艺路线不存在");
        vo.setSteps(routeStepService.queryByRouteId(id));
        return vo;
    }

    /**
     * 查询工艺路线列表
     */
    public TableDataInfo<ProcessRouteVo> queryPageList(ProcessRouteBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ProcessRoute> lqw = buildQueryWrapper(bo);
        Page<ProcessRouteVo> result = processRouteMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询工艺路线列表
     */
    public List<ProcessRouteVo> queryList(ProcessRouteBo bo) {
        LambdaQueryWrapper<ProcessRoute> lqw = buildQueryWrapper(bo);
        return processRouteMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ProcessRoute> buildQueryWrapper(ProcessRouteBo bo) {
        LambdaQueryWrapper<ProcessRoute> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getRouteCode()), ProcessRoute::getRouteCode, bo.getRouteCode());
        lqw.like(StringUtils.isNotBlank(bo.getRouteName()), ProcessRoute::getRouteName, bo.getRouteName());
        lqw.eq(bo.getProductId() != null, ProcessRoute::getProductId, bo.getProductId());
        lqw.eq(bo.getRouteStatus() != null, ProcessRoute::getRouteStatus, bo.getRouteStatus());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增工艺路线（草稿状态）
     */
    @Transactional
    public Long insertByBo(ProcessRouteBo bo) {
        validateRouteCode(bo.getRouteCode());
        if (bo.getRouteStatus() == null) {
            bo.setRouteStatus(0);
        }
        if (bo.getVersion() == null) {
            bo.setVersion("V1.0");
        }
        ProcessRoute add = MapstructUtils.convert(bo, ProcessRoute.class);
        processRouteMapper.insert(add);
        bo.setId(add.getId());
        saveSteps(add.getId(), bo.getSteps());
        return add.getId();
    }

    /**
     * 修改工艺路线
     */
    @Transactional
    public void updateByBo(ProcessRouteBo bo) {
        ProcessRoute update = MapstructUtils.convert(bo, ProcessRoute.class);
        processRouteMapper.updateById(update);
        handleStepChanges(bo.getId(), bo.getSteps());
        List<RouteStep> stepList = MapstructUtils.convert(bo.getSteps(), RouteStep.class);
        stepList.forEach(it -> it.setRouteId(bo.getId()));
        routeStepService.saveSteps(stepList);
    }

    private void handleStepChanges(Long routeId, List<RouteStepBo> steps) {
        if (steps == null) {
            return;
        }
        List<RouteStepVo> dbList = routeStepService.queryByRouteId(routeId);
        var ids = steps.stream()
            .filter(it -> it.getId() != null)
            .map(it -> it.getId())
            .toList();
        var delList = dbList.stream()
            .filter(it -> !ids.contains(it.getId()))
            .toList();
        if (CollUtil.isNotEmpty(delList)) {
            routeStepService.deleteByIds(delList.stream().map(RouteStepVo::getId).toList());
        }
    }

    /**
     * 启用工艺路线
     */
    @Transactional
    public void enable(Long id) {
        LambdaUpdateWrapper<ProcessRoute> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProcessRoute::getId, id);
        wrapper.ne(ProcessRoute::getRouteStatus, 1);
        wrapper.set(ProcessRoute::getRouteStatus, 1);
        processRouteMapper.update(null, wrapper);
    }

    /**
     * 停用工艺路线
     */
    @Transactional
    public void disable(Long id) {
        LambdaUpdateWrapper<ProcessRoute> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProcessRoute::getId, id);
        wrapper.eq(ProcessRoute::getRouteStatus, 1);
        wrapper.set(ProcessRoute::getRouteStatus, 2);
        processRouteMapper.update(null, wrapper);
    }

    /**
     * 删除工艺路线
     */
    @Transactional
    public void deleteById(Long id) {
        validateBeforeDelete(id);
        routeStepService.deleteByRouteId(id);
        processRouteMapper.deleteById(id);
    }

    private void validateBeforeDelete(Long id) {
        ProcessRouteVo vo = queryById(id);
        Assert.notNull(vo, "工艺路线不存在");
        Integer status = vo.getRouteStatus();
        if (status != null && status == 1) {
            throw new BaseException("工艺路线【" + vo.getRouteCode() + "】已启用，无法删除！");
        }
    }

    public void deleteByIds(Collection<Long> ids) {
        ids.forEach(this::deleteById);
    }

    public void validateRouteCode(String routeCode) {
        LambdaQueryWrapper<ProcessRoute> lqw = Wrappers.lambdaQuery();
        lqw.eq(ProcessRoute::getRouteCode, routeCode);
        ProcessRoute exist = processRouteMapper.selectOne(lqw);
        Assert.isNull(exist, "路线编码重复，请手动修改");
    }

    /**
     * 计算工艺路线总标准工时（分钟）
     */
    public int calculateTotalStandardTime(Long routeId) {
        List<RouteStepVo> steps = routeStepService.queryByRouteId(routeId);
        return steps.stream()
            .mapToInt(s -> s.getStandardTime() != null ? s.getStandardTime() : 0)
            .sum();
    }

    /**
     * 计算工艺路线总人工成本
     */
    public BigDecimal calculateTotalLaborCost(Long routeId) {
        List<RouteStepVo> steps = routeStepService.queryByRouteId(routeId);
        return steps.stream()
            .map(s -> s.getLaborCost() != null ? s.getLaborCost() : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void saveSteps(Long routeId, List<RouteStepBo> steps) {
        if (CollUtil.isEmpty(steps)) {
            return;
        }
        List<RouteStep> stepList = MapstructUtils.convert(steps, RouteStep.class);
        stepList.forEach(it -> it.setRouteId(routeId));
        routeStepService.saveSteps(stepList);
    }

}
