package net.cdsunrise.wm.prophase.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.prophase.entity.PipelineTransform;
import net.cdsunrise.wm.prophase.vo.PipelineTranSaveVo;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :
 */
public interface PipelineTransformService {

    void save(PipelineTranSaveVo pipelineTranSaveVo);

    void saveBatch(List<PipelineTransform> pipelineTransform);

    void delete(Long id);

    PipelineTransform findOne(Long id);

    Pager getPager(PageCondition condition);

    List<PipelineTransform> selectList(PipelineTransform pipelineTransform);
}
