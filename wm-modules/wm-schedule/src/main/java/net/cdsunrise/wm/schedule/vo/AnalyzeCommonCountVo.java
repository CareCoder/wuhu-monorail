package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;


/***
 * @author gechaoqing
 * 通用统计信息
 */
@Data
@ApiModel("通用统计信息实体")
public class AnalyzeCommonCountVo {
    @ApiModelProperty("统计名称")
    private String name;
    @ApiModelProperty("统计结果")
    private AtomicInteger count;
}
