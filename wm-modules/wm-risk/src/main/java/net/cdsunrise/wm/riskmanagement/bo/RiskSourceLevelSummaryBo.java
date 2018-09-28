package net.cdsunrise.wm.riskmanagement.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@ApiModel("风险按月统计")
@Data
public class RiskSourceLevelSummaryBo {
    /**
     * 年月
     */
    @ApiModelProperty("年月字符串 yyyy-MM")
    private String yearMonthStr;
    /**
     * 一级风险数量
     */
    @ApiModelProperty("风险等级一级数量")
    private Integer lv1 = 0;

    @ApiModelProperty("风险等级二级数量")
    private Integer lv2 = 0;

    @ApiModelProperty("风险等级三级数量")
    private Integer lv3 = 0;

    @ApiModelProperty("风险等级四级数量")
    private Integer lv4 = 0;

    @ApiModelProperty("风险等级五级数量")
    private Integer lv5 = 0;
}
