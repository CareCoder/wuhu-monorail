package net.cdsunrise.wm.schedule.vo;

import lombok.Data;

/***
 * @author gechaoqing
 * 进度计划编制，保存关联模型数据
 *
 */
@Data
public class SchedulePlanRefModelVo {
    public SchedulePlanRefModelVo(){}
    public SchedulePlanRefModelVo(Long fid,String guid){
        this.fid = fid;
        this.guid = guid;
    }
    public SchedulePlanRefModelVo(Long modelId){
        this.modelId = modelId;
    }
    private Long modelId;
    private String pierCode;
    private Long fid;
    private String guid;
}
