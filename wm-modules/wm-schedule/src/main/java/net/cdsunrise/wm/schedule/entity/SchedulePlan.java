package net.cdsunrise.wm.schedule.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/***
 * @author gechaoqing
 * 进度计划实体
 */
@Data
@Entity
@Table(name = "wm_schedule_plan")
public class SchedulePlan extends BaseEntity {
    public static final String CODE_SPLIT="-";
    @ApiParam("进度计划名称")
    @Column(name = "`name`")
    private String name;
    /***
     * 计划类别
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanCategory#MASTER 总计划
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanCategory#YEAR 年计划
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanCategory#MONTH 月计划
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanCategory#WEEK 周计划
     */
    @ApiParam("进度任务类别['MASTER','YEAR','MONTH','WEEK']")
    private String category;
    @ApiParam("进度任务开始日期，yyyy-MM-dd")
    private Date startDate;
    @ApiParam("进度任务完成日期，yyyy-MM-dd")
    private Date completeDate;
    @ApiParam("进度任务实际开始日期，不用传值")
    private Date actualStartDate;
    @ApiParam("进度任务实际完成日期，不用传值")
    private Date actualCompleteDate;
    @ApiParam("进度任务工期")
    private int timeLimit;
    @ApiParam("进度任务所属工点ID")
    private Long workPointId;

    /***
     * 工点名称临时字段
     */
    @Transient
    private String workPointName;
    @ApiParam("进度创建人ID[1]")
    private Long createUserId;
    @ApiParam("创建人所在单位ID")
    private Long companyId;
    @ApiParam("分配给部门ID")
    private Long deptId;
    /***
     * 部门名称临时字段
     */
    @Transient
    private String deptName;
    @ApiParam("上级任务ID")
    private Long parentId;
    /***
     * 计划状态
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanStatus#AUDITING 审核中
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanStatus#COMPLETE 已完成
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanStatus#PROCESSING 已开工(进行中)
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanStatus#NOT_START 未开工
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanStatus#DELETE 已删除
     *
     *
     */
    @ApiParam("进度状态，不用传值")
    @Column(name = "`status`")
    private String status;
    @ApiParam("进度任务编码，不用传值")
    @Column(name = "`code`")
    private String code;
    @ApiParam("进度任务完成百分比，不用传值")
    private BigDecimal completePercent;
    @ApiParam("进度任务描述")
    @Column(name = "`describe`")
    private String describe;
    /***
     * 完成审核状态
     * @see net.cdsunrise.wm.schedule.enums.ScheduleAuditStatus#PASS
     * @see net.cdsunrise.wm.schedule.enums.ScheduleAuditStatus#UN_PASS
     * @see net.cdsunrise.wm.schedule.enums.ScheduleAuditStatus#WAIT_AUDIT
     */
    @ApiParam("进度完成审核状态，不用传值")
    private String auditStatus;

    /***
     * 编制确认状态
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanConfirmStatus#OK
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanConfirmStatus#NOT_OK
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanConfirmStatus#NONE
     */
    @ApiParam("进度编制审核确认状态，不用传值")
    private String confirmStatus;

    @ApiParam("工程量单位")
    private String quantityUnit;
    @ApiParam("计划工程量")
    private BigDecimal quantity;

    @ApiParam("还可分配的工程量")
    @Transient
    private BigDecimal lastQuantity=BigDecimal.ZERO;

    @OneToMany(mappedBy = "plan",cascade = CascadeType.ALL)
    private List<SchedulePlanRefModel> refModelList;

    /***
     * 计划是否可编辑临时字段
     */
    @Transient
    private boolean editable=true;

    @OneToOne(
            mappedBy = "schedulePlan",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private SchedulePlanBackUp schedulePlanBackUp;

    public void setSchedulePlanBackUp(SchedulePlanBackUp schedulePlanBackUp){
        if (schedulePlanBackUp == null) {
            if (this.schedulePlanBackUp != null) {
                this.schedulePlanBackUp.setSchedulePlan(null);
            }
        }
        else {
            schedulePlanBackUp.setSchedulePlan(this);
        }
        this.schedulePlanBackUp = schedulePlanBackUp;
    }
    @Override
    public String toString() {
        return "SchedulePlan{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", startDate=" + startDate +
                ", completeDate=" + completeDate +
                ", actualStartDate=" + actualStartDate +
                ", actualCompleteDate=" + actualCompleteDate +
                ", timeLimit=" + timeLimit +
                ", workPointId=" + workPointId +
                ", createUserId=" + createUserId +
                ", companyId=" + companyId +
                ", parentId=" + parentId +
                ", status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", completePercent=" + completePercent +
                ", describe='" + describe + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", confirmStatus='" + confirmStatus + '\'' +
                ", quantityUnit='" + quantityUnit + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
