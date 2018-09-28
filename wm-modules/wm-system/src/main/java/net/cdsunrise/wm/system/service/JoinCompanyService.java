package net.cdsunrise.wm.system.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.system.entity.JoinCompany;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/26
 * Describe :
 */
public interface JoinCompanyService {

    JoinCompany findOne(Long id);

    List<JoinCompany> findAll();

    void delete(Long id);

    void save(JoinCompany joinCompany);

    Pager<JoinCompany> getPager(PageCondition condition);
}
