package net.cdsunrise.wm.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :资源
 */
@Data
@Entity
@Table(name = "wm_resource")
public class Resource extends BaseEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 资源类型（1菜单、2事件、3授权）
     */
    private Integer type;

    /**
     * 链接
     */
    private String path;

    /**
     * 请求方式
     */
    @Column(length = 10)
    private String method;

    /**
     * 父级资源ID
     */
    private Long parentId;

    /**
     * 图标名称
     */
    private String icon;

    /**
     * 数据授权
     */
    private Integer dataAuth;

    /**
     * 子菜单顺序
     */
    private Integer order;

    /**
     * 覆盖equals方法，原来的方法不包括父类属性的比较
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(name, resource.name) &&
                Objects.equals(type, resource.type) &&
                Objects.equals(path, resource.path) &&
                Objects.equals(parentId, resource.parentId)&&
                Objects.equals(id, resource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, path, parentId, id);
    }
}
