package net.cdsunrise.wm.prophase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;

/**
 * Author: WangRui
 * Date: 2018/5/21
 * Describe:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "wm_pipeline_transform_ref_model")
public class PipelineTransformRefModel extends BaseEntity {

    @ApiParam("管线改移数据ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pipeline_transform_id")
    @JsonIgnore
    private PipelineTransform pipelineTransform;

    @ApiParam("模型GUID")
    private String guid;

    @ApiParam("模型FID")
    private Long fid;

    @ApiParam("model  id")
    private Long modelId;
}
