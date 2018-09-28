package net.cdsunrise.wm.riskmanagement.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author lijun
 * @date 2018-04-23.
 * @descritpion
 */
@Data
public class TaskReportSubmitVo {
    /**
     * 任务ID
     */
    @NotNull(message = "任务ID不能为空")
    @ApiParam(name = "任务ID")
    private Long taskId;

    @ApiParam("是否发现风险")
    private boolean existsRisk;

    @ApiParam("备注")
    private String remark;

    @ApiParam("上传的图片")
    private MultipartFile[] images;
}
