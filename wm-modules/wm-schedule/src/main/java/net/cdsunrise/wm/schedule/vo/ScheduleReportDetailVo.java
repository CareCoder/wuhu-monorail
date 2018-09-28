package net.cdsunrise.wm.schedule.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author cdsunrise.net / gechaoqing
 * @since 2018-02-07
 * 进度上报历史记录
 */
@Data
public class ScheduleReportDetailVo implements Serializable {
	private Long reportId;
	/***
	 * 上报项名称
	 */
	private String reportItemName;
    /***
     * 上报量
     */
	private BigDecimal reportItemVolume;
    /***
     * 计划完成量
     */
	private BigDecimal planVolume;

}
