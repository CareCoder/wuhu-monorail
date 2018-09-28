package net.cdsunrise.wm.quality.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/***
 * @author gechaoqing
 * 梁、墩支撑修改记录数据
 */
@Data
@Entity
@Table(name = "wm_beam_pier_support_standard_modify_his")
public class BeamPierSupportStandardModifyHis {

    @Id
    @ApiParam("主键ID")
    protected Long id;
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal centerDeviationVal;
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal heightDeviationVal;
    /***
     * 测量人ID
     */
    private Long modifyUserId;
    /***
     * 测量人姓名
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
    protected Date createTime;

}
