package net.cdsunrise.wm.schedule.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 * @author gechaoqing
 * 进度对比模拟
 */
@Data
public class SchedulePlanCompareMonitorVo {
    private String startDateStr;
    private String completeDateStr;
    /***
     * 0 - 正常
     * 1 - 提前
     * 2 - 延期
     */
    private int type;
    private List<RefModel> refModelList = new ArrayList<>();
    @Data
    public static class RefModel{
        private Long fid;
        private String guid;
        private Long modelId;
        public RefModel(Long modelId){
            this.modelId = modelId;
        }
    }
}
