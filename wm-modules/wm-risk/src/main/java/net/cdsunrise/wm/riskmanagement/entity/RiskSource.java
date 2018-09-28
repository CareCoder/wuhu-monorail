package net.cdsunrise.wm.riskmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lijun
 * @date 2018-04-19.
 * @descritpion 风险源
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "wm_risk_source")
public class RiskSource extends BaseEntity {
    /**
     * 工作活动
     */
    private String workAct;
    /**
     * 时间  年月支付串
     */
    @Column(length = 10)
    private String yearMonthStr;
    /**
     * 工点名称 通过 , 分割
     */
    private String workPointNames;
    /**
     * 危险源
     */
    private String source;
    /**
     * 现有控制措施
     */
    private String ctrlMeasure;
    /**
     * 暴露于风险中的人员
     */
    private String riskStaff;
    /**
     * 事故发生的可能性
     */
    private Integer happenPossibility;
    /**
     * 事故后果
     */
    private Integer adtConsequence;
    /**
     * 综合风险等级评定
     */
    private Integer comEvaluation;
}
