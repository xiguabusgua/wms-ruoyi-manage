package com.ruoyi.wms.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.RouteStepBo;
import com.ruoyi.wms.domain.entity.RouteStep;
import com.ruoyi.wms.domain.vo.RouteStepVo;
import com.ruoyi.wms.mapper.RouteStepMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 工序步骤Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class RouteStepService extends ServiceImpl<RouteStepMapper, RouteStep> {

    private final RouteStepMapper routeStepMapper;

    /**
     * 查询工序步骤
     */
    public RouteStepVo queryById(Long id) {
        return routeStepMapper.selectVoById(id);
    }

    public TableDataInfo<RouteStepVo> queryPageList(RouteStepBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<RouteStep> lqw = buildQueryWrapper(bo);
        Page<RouteStepVo> result = routeStepMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<RouteStepVo> queryList(RouteStepBo bo) {
        LambdaQueryWrapper<RouteStep> lqw = buildQueryWrapper(bo);
        return routeStepMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<RouteStep> buildQueryWrapper(RouteStepBo bo) {
        LambdaQueryWrapper<RouteStep> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRouteId() != null, RouteStep::getRouteId, bo.getRouteId());
        lqw.eq(bo.getEquipmentId() != null, RouteStep::getEquipmentId, bo.getEquipmentId());
        lqw.orderByAsc(RouteStep::getStepNo);
        return lqw;
    }

    public void insertByBo(RouteStepBo bo) {
        RouteStep add = MapstructUtils.convert(bo, RouteStep.class);
        routeStepMapper.insert(add);
    }

    public void updateByBo(RouteStepBo bo) {
        RouteStep update = MapstructUtils.convert(bo, RouteStep.class);
        routeStepMapper.updateById(update);
    }

    public void deleteByIds(Collection<Long> ids) {
        routeStepMapper.deleteBatchIds(ids);
    }

    @Transactional
    public void saveSteps(List<RouteStep> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        saveOrUpdateBatch(list);
    }

    public List<RouteStepVo> queryByRouteId(Long routeId) {
        RouteStepBo bo = new RouteStepBo();
        bo.setRouteId(routeId);
        List<RouteStepVo> steps = queryList(bo);
        if (CollUtil.isEmpty(steps)) {
            return Collections.emptyList();
        }
        return steps;
    }

    /**
     * 根据工艺路线ID删除工序步骤
     */
    public void deleteByRouteId(@jakarta.validation.constraints.NotNull Long routeId) {
        LambdaQueryWrapper<RouteStep> lqw = Wrappers.lambdaQuery();
        lqw.eq(RouteStep::getRouteId, routeId);
        routeStepMapper.delete(lqw);
    }

}
