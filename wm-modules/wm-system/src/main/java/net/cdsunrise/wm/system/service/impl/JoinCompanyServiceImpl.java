package net.cdsunrise.wm.system.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.system.entity.Department;
import net.cdsunrise.wm.system.entity.JoinCompany;
import net.cdsunrise.wm.system.repository.DepartmentRepository;
import net.cdsunrise.wm.system.repository.JoinCompanyRepository;
import net.cdsunrise.wm.system.service.JoinCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/26
 * Describe :
 */
@Service
public class JoinCompanyServiceImpl implements JoinCompanyService {

    @Autowired
    private JoinCompanyRepository joinCompanyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public JoinCompany findOne(Long id) {
        return joinCompanyRepository.findOne(id);
    }

    @Override
    public List<JoinCompany> findAll() {
        return joinCompanyRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        JoinCompany joinCompany = findOne(id);
        List<Department> departmentList = departmentRepository.findByCompany(joinCompany);
        for (Department department : departmentList){
            department.setCompany(null);
        }
        departmentRepository.save(departmentList);
        joinCompanyRepository.delete(id);
    }

    @Override
    public void save(JoinCompany joinCompany) {
        joinCompanyRepository.save(joinCompany);
    }

    @Override
    public Pager<JoinCompany> getPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(JoinCompany.class, "l").useNativeSql(false).setPageCondition(condition);
        Pager<JoinCompany> pager = commonDAO.findPager(helper);
        return pager;
    }
}
