package net.cdsunrise.wm.quality.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * @author gechaoqing
 * 梁标准信息修改记录
 */
@Data
@Entity
@Table(name = "wm_beam_standard_modify_his")
public class BeamStandardModifyHis {
    @Id
    private String beamCode;

    /***
     * 修改后的长度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal lengthDeviation;

    /***
     * 修改后的高度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal heightDeviation;

    /***
     * 修改人ID
     */
    private Long modifyUserId;
    /***
     * 修改人姓名
     */
    private String modifyUserName;

    /***
     * 修改记录审核状态
     * @see net.cdsunrise.wm.quality.enums.Status#OK 通过
     * @see net.cdsunrise.wm.quality.enums.Status#NOT_OK 不通过
     * @see net.cdsunrise.wm.quality.enums.Status#NONE 待审核
     */
    private String status;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    @ApiParam("创建时间")
    private Date createTime;

}
