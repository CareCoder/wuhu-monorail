package net.cdsunrise.wm.system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :部门
 */
@Data
@Entity
@Table(name = "wm_dept")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Department extends BaseEntity {

    /**
     * 部门名称
     */
    private String name;

    /**
     * 所属单位ID
     */
    @ManyToOne
    @JoinColumn(name = "company_id")
    private JoinCompany company;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "wm_dept_ref_dept",
            //拥有方
            joinColumns = @JoinColumn(name = "parent_dept_id"),
            //被动方
            inverseJoinColumns = @JoinColumn(name = "dept_id")
    )
    private List<Department> childrenList;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "wm_dept_ref_dept",
            //拥有方
            joinColumns = @JoinColumn(name = "dept_id"),
            //被动方
            inverseJoinColumns = @JoinColumn(name = "parent_dept_id")
    )
    @JsonBackReference
    private List<Department> parentList;


    /**
     * 用户列表权限id
     */
    @ApiParam("用户列表权限id")
    private Long userAuthorId;

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
