package net.cdsunrise.wm.system.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :岗位
 */
@Data
@Entity
@Table(name = "wm_position")
public class Position extends BaseEntity{

    /**
     * 名称
     */
    private String name;

    /**
     * 上级岗位ID
     */
    private Long parentId;

    /**
     * 所属部门ID
     */
    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    /***
     * BIM里模型视角定位
     */
    private String lookAtPos;

}
