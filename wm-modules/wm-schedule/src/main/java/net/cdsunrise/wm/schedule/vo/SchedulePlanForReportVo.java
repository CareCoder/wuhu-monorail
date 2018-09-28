package net.cdsunrise.wm.schedule.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/***
 * @author gechaoqing
 * 进度上报信息展示
 */
@Data
public class SchedulePlanForReportVo {
    private Long planId;
    private Date planCompleteDate;
    private BigDecimal planQuantity;
    private BigDecimal completeQuantity;
    private String workPointName;
    private String planName;
    private String quantityUnit;
}
