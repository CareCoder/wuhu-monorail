package net.cdsunrise.wm.schedule.vo;


import lombok.Data;

import java.util.Date;
import java.util.List;

/***
 * @author gechaoqing
 * 生成制梁计划
 */
@Data
public class LedgerPlanVo {
    private String workPoint;
    private Date useTime;
    private List<String> pierList;
}
