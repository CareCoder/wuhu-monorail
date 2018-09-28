package net.cdsunrise.wm.schedule.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cdsunrise.net / gechaoqing
 * @since 2018-02-07
 * 进度上报历史记录
 */
@Data
public class ScheduleReportVo implements Serializable {

	/***
	 * 上报日期
	 */
	private Date reportDate;

	/***
	 * 上报人
	 */
	private String reportUser;

	private String attachmentName;
	private String attachmentUrl;

	private String comment;

	private BigDecimal reportQuantity;
	private Long id;

	private List<ScheduleReportDetailVo> hisDetailVoList=new ArrayList<>();


}
