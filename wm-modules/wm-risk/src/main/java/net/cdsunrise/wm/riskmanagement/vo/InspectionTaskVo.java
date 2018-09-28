package net.cdsunrise.wm.riskmanagement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.cdsunrise.wm.riskmanagement.bo.ImageResourceBo;
import net.cdsunrise.wm.riskmanagement.bo.TaskReportBo;
import net.cdsunrise.wm.riskmanagement.entity.RiskSource;
import net.cdsunrise.wm.riskmanagement.entity.TaskReport;
import net.cdsunrise.wm.riskmanagement.enums.InspectionTaskStatus;

import java.util.*;

/**
 * @author lijun
 * @date 2018-04-24.
 * @descritpion
 */
@Data
@ApiModel("风险巡视任务")
public class InspectionTaskVo {

    @ApiModelProperty("主键")
    private Long id;
    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;
    /**
     * 位置（BIM位置）
     */
    @ApiModelProperty("位置（BIM位置）")
    private String location;
    /**
     * （添加时指定）创建人ID
     */
    @ApiModelProperty("创建人ID")
    private Long creatorId;

    @ApiModelProperty("创建人名称")
    private String creatorName;
    /**
     * （添加时指定）经办人
     */
    @ApiModelProperty("经办人ID")
    private Long handlerId;

    @ApiModelProperty("经办人名称")
    private String handlerName;
    /**
     * 验收人
     */
    @ApiModelProperty("验收人ID")
    private Long acceptancePersonId;
    /**
     * 验收人
     */
    @ApiModelProperty("验收人名称")
    private String acceptancePersonName;
    /**
     * 分项工程(工点)
     */
    @ApiModelProperty("分项工程(工点)")
    private String subProject;

    @ApiModelProperty("上报数量")
    private Integer reportedQuantity;

    @ApiModelProperty("问题数量")
    private Integer problemQuantity;
    /**
     * 任务状态
     */
    @ApiModelProperty("任务状态")
    private InspectionTaskStatus status;
    /**
     * 截图位置
     */
    @ApiModelProperty("截图图片")
    private List<ImageResourceBo> screenImages = new ArrayList<>();
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date startDate;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date endDate;

    /**
     * 情况说明，备注
     */
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date modifyTime;

    @ApiModelProperty("上报信息")
    private List<TaskReportBo> reports = new ArrayList<>();
}
