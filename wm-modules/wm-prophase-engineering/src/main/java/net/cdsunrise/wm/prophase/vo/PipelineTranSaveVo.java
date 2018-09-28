package net.cdsunrise.wm.prophase.vo;

import lombok.Data;
import net.cdsunrise.wm.prophase.entity.PipelineTransform;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/5/21
 * Describe:
 */
@Data
public class PipelineTranSaveVo {

    private PipelineTransform pipelineTransform;

    private List<ProphaseRefModelVo> list;

}
