package net.cdsunrise.wm.system.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author : WangRui
 * Date : 2018/4/23
 * Describe :
 */
@Data
@Entity
@Table(name = "wm_join_company")
public class JoinCompany extends BaseEntity {

    /**
     *单位名称
     */
    private String name;

    /**
     * 单位类型（1建设单位、2设计单位、3监理单位、4施工单位、5质量监督）
     */
    private Integer type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 单位所在地
     */
    private String address;

    /**
     * 单位负责人
     */
    private String leader;

    /**
     * 负责人联系电话
     */
    private String tel;
}
