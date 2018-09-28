package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * @author gechaoqing
 * 进度分析，重要事件节点数据
 */
@Data
@ApiModel("重要事件节点/里程碑事件")
public class AnalyzeCalendarVo {
    @ApiModelProperty("事件日期")
    private String date;
    @ApiModelProperty("事件内容")
    private String value;
}
