package net.cdsunrise.wm.system.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.system.entity.Department;
import net.cdsunrise.wm.system.entity.JoinCompany;
import net.cdsunrise.wm.system.entity.Position;
import net.cdsunrise.wm.system.entity.User;
import net.cdsunrise.wm.system.repository.DepartmentRepository;
import net.cdsunrise.wm.system.repository.JoinCompanyRepository;
import net.cdsunrise.wm.system.repository.PositionRepository;
import net.cdsunrise.wm.system.repository.UserRepository;
import net.cdsunrise.wm.system.service.DepartmentService;
import net.cdsunrise.wm.system.vo.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JoinCompanyRepository joinCompanyRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(DepartmentVo departmentvo) {
        JoinCompany joinCompany = joinCompanyRepository.findOne(departmentvo.getCompanyId());
        Department department = new Department();
        if (departmentvo.getId() != null) {
            department = departmentRepository.findOne(departmentvo.getId());
        }
        BeanUtils.copyProperties(departmentvo, department);
        department.setCompany(joinCompany);
        departmentRepository.save(department);
    }

    @Override
    public void delete(Long id) {
        Department department = departmentRepository.findOne(id);
        List<Position> positionList = positionRepository.findByDepartment(department);
        for (Position position : positionList) {
            position.setDepartment(null);
        }
        positionRepository.save(positionList);
        departmentRepository.delete(id);
    }

    @Override
    public DepartmentVo findOne(Long id) {
        return convertToVo(departmentRepository.findOne(id));
    }

    @Override
    public List<DepartmentVo> findAll() {
        return listConvert(departmentRepository.findAll());
    }

    @Override
    public List<DepartmentVo> findByCID(Long id) {
        return listConvert(departmentRepository.findByCompany(joinCompanyRepository.findOne(id)));
    }

    @Override
    public Pager<DepartmentVo> getPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(Position.class, "l").useNativeSql(false).setPageCondition(condition);
        Pager<Department> pager = commonDAO.findPager(helper);
        Pager<DepartmentVo> p = new Pager<>(pager.getNumber(), pager.getPageSize(), pager.getTotalElements(), listConvert(pager.getContent()));
        return p;
    }

    /**
     * 根据登录用户的部门获取该部门下的所有下属部门id
     *
     * @return
     */
    @Override
    public List<Long> subordinateDepartment() {
        User user = userRepository.findOne(UserUtils.getUserId());
        List<Department> departmentList = user.getPosition().getDepartment().getChildrenList();
        List<Long> deptIdList = new ArrayList<>();
        return deptIdRecursion(departmentList, deptIdList);
    }

    @Override
    public List<Long> subordinateDepartmentByDeptId(Long id) {
        List<Department> departmentList =  departmentRepository.findOne(id).getChildrenList();
        List<Long> deptIdList = new ArrayList<>();
        return deptIdRecursion(departmentList, deptIdList);
    }

    @Override
    public List<Department> childrenDepartment() {
        User user = userRepository.findOne(UserUtils.getUserId());
        List<Department> departmentList = user.getPosition().getDepartment().getChildrenList();
        return departmentList;
    }

    /**
     * 部门id递归遍历
     *
     * @return
     */
    public List<Long> deptIdRecursion(List<Department> departmentList, List<Long> deptIdList) {
        if (departmentList.size() > 0) {
            for (Department department : departmentList) {
                if (deptIdList.contains(department.getId())) {
                    continue;
                }
                deptIdList.add(department.getId());
                List<Department> departments = department.getChildrenList();
                if (departments.size() > 0) {
                    deptIdRecursion(departments, deptIdList);
                }
            }
        }
        return deptIdList;
    }

    /**
     * 将Department List转换成DepartmentVo List
     *
     * @param departmentList
     * @return
     */
    private List<DepartmentVo> listConvert(List<Department> departmentList) {
        if (departmentList.size() > 0) {
            List<DepartmentVo> vos = new ArrayList<>();
            departmentList.forEach(department -> vos.add(convertToVo(department)));
            return vos;
        } else {
            return null;
        }
    }

    /**
     * 将Department转换成DepartmentVo
     *
     * @param department
     * @return
     */
    private DepartmentVo convertToVo(Department department) {
        if (department != null) {
            DepartmentVo vo = new DepartmentVo();
            BeanUtils.copyProperties(department, vo);
            vo.setCompanyId(department.getCompany().getId());
            vo.setCompanyName(department.getCompany().getName());
            return vo;
        } else {
            return null;
        }
    }

    @Override
    public List<Department> allDepartmentTree() {
        List<Department> all = new ArrayList<>();
        all.add(departmentRepository.findOne(1L));
        return all;
    }

    /**
     * 查询管理一部管理二部的下属部门
     * @return
     */
    @Override
    public List<Department> selectDepartmentByGLId() {
        return departmentRepository.selectDepartmentByGLId();
    }

    @Override
    public List<Department> findByParentId(Long parentId) {
        List<Department> all = new ArrayList<>();
        all.addAll(departmentRepository.findByParentId(parentId));
        return all;
    }
}
