package net.cdsunrise.wm.riskmanagement.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijun
 * @date 2018-04-25.
 * @descritpion 等级分类统计
 */
@ApiModel("等级分类统计")
@Data
public class LevelClassificationSummaryBo {
    /**
     * 日期 年月
     */
    @ApiModelProperty("日期字符串  年月")
    private String yearMonthStr;
    /**
     * 风险等级 和数量
     */
    private Integer lv1 = 0;

    private Integer lv2 = 0;

    private Integer lv3 = 0;

    private Integer lv4 = 0;

    private Integer lv5 = 0;
}
