package net.cdsunrise.wm.prophase.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :管线改移
 */
@Data
@Entity
@Table(name = "wm_pipeline_transformation")
public class PipelineTransform extends BaseEntity {

    @ApiParam("管线种类")
    private String pipelineType;

    @ApiParam("与工程关系")
    private String relationship;

    @ApiParam("管材")
    private String pipe;

    @ApiParam("规格")
    private String specifications;

    @ApiParam("平面位置")
    private String planePosition;

    @ApiParam("处理原因")
    private String handleReason;

    @ApiParam("埋深")
    private String buriedDepth;

    @ApiParam("现在影响长度")
    private String influenceLength;

    @ApiParam("措施保护")
    private String measuresProtect;

    @ApiParam("悬吊保护")
    private String suspensionProtect;

    @ApiParam("拆除")
    private String dismantle;

    @ApiParam("改移长度")
    private String shiftLength;

    @ApiParam("永久改移长度")
    private String pertShiftLength;

    @ApiParam("产权单位")
    private String propertyCompany;

    @ApiParam("开始时间")
    private Date beginTime;

    @ApiParam("结束时间")
    private Date endTime;

    @ApiParam("负责单位")
    private String responsibleUnit;

    @ApiParam("位置信息")
    private String positionInformation;

    @ApiParam("备注")
    private String note;

    @OneToMany(mappedBy = "pipelineTransform",cascade = CascadeType.ALL)
    private List<PipelineTransformRefModel> refModelList;

    @Override
    public String toString() {
        return "PipelineTransform{" +
                "pipelineType='" + pipelineType + '\'' +
                ", relationship='" + relationship + '\'' +
                ", pipe='" + pipe + '\'' +
                ", specifications='" + specifications + '\'' +
                ", planePosition='" + planePosition + '\'' +
                ", handleReason='" + handleReason + '\'' +
                ", buriedDepth='" + buriedDepth + '\'' +
                ", influenceLength='" + influenceLength + '\'' +
                ", measuresProtect='" + measuresProtect + '\'' +
                ", suspensionProtect='" + suspensionProtect + '\'' +
                ", dismantle='" + dismantle + '\'' +
                ", shiftLength='" + shiftLength + '\'' +
                ", pertShiftLength='" + pertShiftLength + '\'' +
                ", propertyCompany='" + propertyCompany + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", responsibleUnit='" + responsibleUnit + '\'' +
                ", positionInformation='" + positionInformation + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
