package net.cdsunrise.wm.hiddentrouble.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion 隐患排查分级统计
 */
@Data
@ApiModel("隐患排查分级信息")
public class LevelSummaryBo {
    @ApiModelProperty("日期字符串 yyyy-MM-dd")
    private String yearMonthStr;

    @ApiModelProperty(value = "一级隐患")
    private Integer lv1 = 0;

    @ApiModelProperty(value = "二级隐患")
    private Integer lv2 = 0;

    @ApiModelProperty(value = "三级隐患")
    private Integer lv3 = 0;

    private Integer lv4 = 0;

    private Integer lv5 = 0;
}
