package net.cdsunrise.wm.schedule.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * @author gechaoqing
 * 进度导入实体
 */
@Data
public class SchedulePlanImportVo {
    private String name;
    private Date startDate;
    private Date completeDate;
    private Integer days;
    private String unit;
    private BigDecimal quantity;
    private String category;
    private Long workPointId;
    private Long parentId;
    private List<SchedulePlanImportVo> children=new ArrayList<>();
}
