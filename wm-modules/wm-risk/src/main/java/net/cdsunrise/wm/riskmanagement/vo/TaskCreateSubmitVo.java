package net.cdsunrise.wm.riskmanagement.vo;

import lombok.Data;
import net.cdsunrise.wm.riskmanagement.enums.InspectionTaskStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Data
public class TaskCreateSubmitVo {
    /**
     * 标题
     */
    private String title;
    /**
     * 位置（BIM位置）
     */
    private String location;

    /**
     * （添加时指定）经办人ID
     */
    private Long handlerId;
    /**
     * 验收人ID
     */
    private Long acceptancePersonId;
    /**
     * 分项工程(工点)
     */
    private String workPointName;
    /**
     * 工点ID
     */
    private Long workPointId;
    /**
     * 上传的图片
     */
    private MultipartFile[] image;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 情况说明，备注
     */
    private String remark;
}
