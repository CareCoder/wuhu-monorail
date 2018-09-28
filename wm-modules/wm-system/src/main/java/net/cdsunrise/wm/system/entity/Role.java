package net.cdsunrise.wm.system.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author : WangRui
 * Date : 2018/4/23
 * Describe :
 */
@Data
@Entity
@Table(name = "wm_role")
public class Role extends BaseEntity {

    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编号  格式 ROLE_*
     */
    private String code;

    @ManyToMany
    @JoinTable(
            name = "wm_role_right",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    private List<Resource> resources = new ArrayList<>();

    /**
     * 角色列表权限id
     */
    @ApiParam("角色列表权限id")
    private Long roleAuthorId;

}
