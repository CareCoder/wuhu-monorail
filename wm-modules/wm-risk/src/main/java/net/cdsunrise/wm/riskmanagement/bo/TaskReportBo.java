package net.cdsunrise.wm.riskmanagement.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Data
public class TaskReportBo {

    private Long id;

    @ApiModelProperty("是否存在风险")
    private boolean existsRisk;

    @ApiModelProperty("上报备注")
    private String remark;
    /**
     * 上报的图片
     */
    @ApiModelProperty("上报图片")
    private List<ImageResourceBo> reportImages = new ArrayList<>();
    /**
     * 验收方案
     */
    @ApiModelProperty("验收方案")
    private String acceptanceMeasure;
    /**
     * 验收时间
     */
    @ApiModelProperty("验收时间")
    private Date acceptanceTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date modifyTime;
}
