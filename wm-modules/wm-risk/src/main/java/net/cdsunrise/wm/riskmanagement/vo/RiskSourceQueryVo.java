package net.cdsunrise.wm.riskmanagement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * @author lijun
 * @date 2018-04-20.
 * @descritpion
 */
@Data
@ApiModel("风险源查询对象")
public class RiskSourceQueryVo {

    @ApiModelProperty("工点名称")
    private String workPointName;

    @ApiModelProperty("时间年月 yyyy-MM")
    private String yearMonthStr;
}
