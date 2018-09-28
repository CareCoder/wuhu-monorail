package net.cdsunrise.wm.riskmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lijun
 * @date 2018-04-20.
 * @descritpion 条件验收
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "wm_conditional_acceptance")
public class ConditionalAcceptance extends BaseEntity {
    /**
     * 名称
     */
    private String name;
    /**
     * 时间
     */
    private String date;
    /**
     * 状态
     */
    private String status;
    /**
     * 线路
     */
    private String route;
    /**
     * 区间
     */
    private String area;
}
