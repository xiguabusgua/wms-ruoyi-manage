package com.ruoyi.wms.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.bo.MoldCategoryBo;
import com.ruoyi.wms.domain.entity.MoldCategory;
import com.ruoyi.wms.domain.entity.MoldInfo;
import com.ruoyi.wms.domain.vo.MoldCategoryVo;
import com.ruoyi.wms.mapper.MoldCategoryMapper;
import com.ruoyi.wms.mapper.MoldInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MoldCategoryService {

    private final MoldCategoryMapper moldCategoryMapper;
    private final MoldInfoMapper moldInfoMapper;

    public MoldCategoryVo queryById(Long id) {
        return moldCategoryMapper.selectVoById(id);
    }

    public TableDataInfo<MoldCategoryVo> queryPageList(MoldCategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MoldCategory> lqw = buildQueryWrapper(bo);
        Page<MoldCategoryVo> result = moldCategoryMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public List<MoldCategoryVo> queryList(MoldCategoryBo bo) {
        LambdaQueryWrapper<MoldCategory> lqw = buildQueryWrapper(bo);
        lqw.orderByAsc(MoldCategory::getOrderNum);
        return moldCategoryMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MoldCategory> buildQueryWrapper(MoldCategoryBo bo) {
        LambdaQueryWrapper<MoldCategory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, MoldCategory::getParentId, bo.getParentId());
        lqw.like(StrUtil.isNotBlank(bo.getCategoryName()), MoldCategory::getCategoryName, bo.getCategoryName());
        lqw.eq(StrUtil.isNotBlank(bo.getStatus()), MoldCategory::getStatus, bo.getStatus());
        return lqw;
    }

    public void insertByBo(MoldCategoryBo bo) {
        validateCategoryName(bo);
        MoldCategory add = MapstructUtils.convert(bo, MoldCategory.class);
        if (add.getOrderNum() == null) {
            LambdaQueryWrapper<MoldCategory> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(bo.getParentId() != null, MoldCategory::getParentId, bo.getParentId());
            wrapper.or().isNull(MoldCategory::getParentId);
            wrapper.orderByDesc(MoldCategory::getOrderNum);
            wrapper.last("LIMIT 1");
            MoldCategory lastCat = moldCategoryMapper.selectOne(wrapper);
            add.setOrderNum(lastCat == null ? 0L : lastCat.getOrderNum() + 1);
        }
        moldCategoryMapper.insert(add);
    }

    public void updateByBo(MoldCategoryBo bo) {
        validateCategoryName(bo);
        MoldCategory update = MapstructUtils.convert(bo, MoldCategory.class);
        moldCategoryMapper.updateById(update);
    }

    private void validateCategoryName(MoldCategoryBo bo) {
        LambdaQueryWrapper<MoldCategory> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MoldCategory::getCategoryName, bo.getCategoryName());
        queryWrapper.ne(bo.getId() != null, MoldCategory::getId, bo.getId());
        Assert.isTrue(moldCategoryMapper.selectCount(queryWrapper) == 0, "分类名称已存在");
    }

    public void deleteByIds(List<Long> ids) {
        LambdaQueryWrapper<MoldCategory> childLqw = Wrappers.lambdaQuery();
        childLqw.in(MoldCategory::getParentId, ids);
        Assert.state(moldCategoryMapper.selectCount(childLqw) == 0, "删除失败！请先删除该分类下的子分类");
        LambdaQueryWrapper<MoldInfo> moldLqw = Wrappers.lambdaQuery();
        moldLqw.in(MoldInfo::getMoldType, ids);
        Assert.state(moldInfoMapper.selectCount(moldLqw) == 0, "删除失败！该分类已被模具使用");
        LambdaQueryWrapper<MoldCategory> deleteWrapper = Wrappers.lambdaQuery();
        deleteWrapper.in(MoldCategory::getId, ids);
        moldCategoryMapper.delete(deleteWrapper);
    }

    public List<MoldCategoryVo> buildTree(List<MoldCategoryVo> categories) {
        List<MoldCategoryVo> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        for (MoldCategoryVo cat : categories) {
            tempList.add(cat.getId());
        }
        for (MoldCategoryVo cat : categories) {
            if (!tempList.contains(cat.getParentId())) {
                recursionFn(categories, cat);
                returnList.add(cat);
            }
        }
        if (returnList.isEmpty()) {
            returnList = categories;
        }
        return returnList;
    }

    private void recursionFn(List<MoldCategoryVo> list, MoldCategoryVo t) {
        List<MoldCategoryVo> childList = getChildList(list, t);
        t.setChildren(childList);
        for (MoldCategoryVo child : childList) {
            if (hasChild(list, child)) {
                recursionFn(list, child);
            }
        }
    }

    private List<MoldCategoryVo> getChildList(List<MoldCategoryVo> list, MoldCategoryVo t) {
        List<MoldCategoryVo> result = new ArrayList<>();
        for (MoldCategoryVo n : list) {
            if (n.getParentId() != null && n.getParentId().longValue() == t.getId().longValue()) {
                result.add(n);
            }
        }
        return result;
    }

    private boolean hasChild(List<MoldCategoryVo> list, MoldCategoryVo t) {
        return getChildList(list, t).size() > 0;
    }

}
