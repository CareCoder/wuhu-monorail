package net.cdsunrise.wm.system.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.system.entity.Department;
import net.cdsunrise.wm.system.entity.Position;
import net.cdsunrise.wm.system.vo.DepartmentVo;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :
 */
public interface DepartmentService {
    void save(DepartmentVo departmentVo);

    void delete(Long id);

    DepartmentVo findOne(Long id);

    List<DepartmentVo> findAll();

    List<DepartmentVo> findByCID(Long id);

    Pager<DepartmentVo> getPager(PageCondition condition);

    List<Long> subordinateDepartment();

    List<Long> subordinateDepartmentByDeptId(Long id);

    List<Department> childrenDepartment();

    List<Department> allDepartmentTree();

    List<Department> selectDepartmentByGLId();

    List<Department> findByParentId(Long parentId);
}
