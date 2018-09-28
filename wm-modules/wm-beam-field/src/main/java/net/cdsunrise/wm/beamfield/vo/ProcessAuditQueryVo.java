package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: WangRui
 * Date: 2018/6/7
 * Describe:
 */
@Data
public class ProcessAuditQueryVo {

    @ApiParam("工序审核步骤id")
    private Integer processExamineId;

    @ApiParam("工序审核时间")
    private Date processExamineTime;

    @ApiParam("工序id与对应的预计审核时间")
    private Map<Integer, Date> map;

    @ApiParam("可以填报的工序")
    private List<ProcessEditVo> list;
}
