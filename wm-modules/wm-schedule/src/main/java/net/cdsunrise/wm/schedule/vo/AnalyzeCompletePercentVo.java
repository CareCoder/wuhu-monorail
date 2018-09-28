package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/***
 * @author gechaoqing
 * 进度分析，完成百分比分析
 */
@Data
@ApiModel("进度百分比")
public class AnalyzeCompletePercentVo {
    @ApiModelProperty("进度名称集合")
    private List<String> taskNameList = new ArrayList<>();
    @ApiModelProperty("完成百分比集合")
    private List<BigDecimal> completePercentList = new ArrayList<>();
    @ApiModelProperty("未完成百分比集合")
    private List<BigDecimal> unCompletePercentList = new ArrayList<>();
}
