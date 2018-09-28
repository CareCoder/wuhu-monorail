package net.cdsunrise.wm.system.repository;

import net.cdsunrise.wm.system.entity.Department;
import net.cdsunrise.wm.system.entity.JoinCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :
 */
public interface DepartmentRepository extends JpaRepository<Department,Long> {

    List<Department> findByCompany(JoinCompany joinCompany);

    @Query(value = "select w1.* from wm_dept w1 RIGHT JOIN (SELECT dept_id as deptId from wm_dept_ref_dept where parent_dept_id in (8,9)) w2 on w1.id = w2.deptId",nativeQuery = true)
    List<Department> selectDepartmentByGLId();

    @Query(value = "select * from wm_dept where id in (select dept_id from wm_dept_ref_dept where parent_dept_id = :parentId)",nativeQuery = true)
    List<Department> findByParentId(@Param("parentId") long parentId);
}
