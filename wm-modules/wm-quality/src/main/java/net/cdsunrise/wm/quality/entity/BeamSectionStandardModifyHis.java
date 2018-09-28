package net.cdsunrise.wm.quality.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/***
 * @author gechaoqing
 * 梁断面修改历史信息
 */
@Data
@Entity
@Table(name = "wm_beam_section_standard_modify_his")
public class BeamSectionStandardModifyHis {

    @Id
    @ApiParam("主键ID")
    protected Long id;

    /***
     * 断面类型
     * PIER - 梁端断面
     * BEAM - 梁间断面
     */
    private String type;

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

    /***
     * 顶面宽度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal topWidthDeviation;

    /***
     * 底面宽度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal bottomWidthDeviation;

    /***
     * 左侧高度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal leftHeightDeviation;

    /***
     * 右侧高度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal rightHeightDeviation;
}
