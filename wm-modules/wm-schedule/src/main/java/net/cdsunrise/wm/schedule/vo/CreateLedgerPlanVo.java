package net.cdsunrise.wm.schedule.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/***
 * @author gechaoqing
 * 生成制梁计划实体
 */
@Data
public class CreateLedgerPlanVo {
    private Date completeDate;
    private String beamCode;
    private String workPoint;
    public CreateLedgerPlanVo(String beamCode){
        this.beamCode = beamCode;
    }
}
