package net.cdsunrise.wm.riskmanagement.vo;


import lombok.Data;
import net.cdsunrise.wm.riskmanagement.enums.InspectionTaskStatus;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelAttribute;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author lijun
 * @date 2018-04-23.
 * @descritpion
 */
@Data
public class InspectionTaskQueryVo {

    /**
     * 创建人ID
     */
    private Long creatorId;
    /**
     * 处理人ID
     */
    private Long handlerId;
    /**
     * 任务状态
     */
    private InspectionTaskStatus status;
    /**
     * 工点 分项工程名称
     */
    private String subProject;
    /**
     * 行的数量。主要用于APP
     */
    private Integer rowCount;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
