package net.cdsunrise.wm.schedule.vo;

import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/****
 * @author gechaoqing
 * 进度模拟实体
 */
@Data
public class SchedulePlanForMonitorVo {
    private Date startDate;
    private Date endDate;
    private String endDateStr;
    private String startDateStr;
    private List<Long> refModelList=new ArrayList<>();


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        if(startDate!=null){
            this.startDateStr = DateFormatUtils.format(startDate,"yyyy-MM-dd");
        }
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        if(endDate!=null){
            this.endDateStr = DateFormatUtils.format(endDate,"yyyy-MM-dd");
        }
    }


}
