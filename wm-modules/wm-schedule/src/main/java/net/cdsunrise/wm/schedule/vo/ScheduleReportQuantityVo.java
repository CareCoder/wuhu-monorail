package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 进度上报提交的工程量数据
 */
@Data
public class ScheduleReportQuantityVo {

    /***
     * 工程量ID
     *
     */
    @ApiParam("工程量ID（模型ID）")
    private Long projectVolumeId;
    /***
     * 上报量
     */
    @ApiParam("上报工程量")
    private BigDecimal reportVolume;
}
