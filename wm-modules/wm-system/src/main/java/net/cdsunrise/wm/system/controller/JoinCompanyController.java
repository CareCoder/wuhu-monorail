package net.cdsunrise.wm.system.controller;

import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.annotation.DeletePath;
import net.cdsunrise.wm.base.web.annotation.ListPath;
import net.cdsunrise.wm.base.web.annotation.PagePath;
import net.cdsunrise.wm.system.entity.JoinCompany;
import net.cdsunrise.wm.system.service.JoinCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/26
 * Describe :
 */
@RestController
@RequestMapping("/joinCompany")
public class JoinCompanyController {

    @Autowired
    private JoinCompanyService joinCompanyService;

    @ApiOperation("新增/修改")
    @PostMapping
    public void save(JoinCompany joinCompany) {
        joinCompanyService.save(joinCompany);
    }

    @ApiOperation("删除")
    @DeletePath
    public void delete(Long id) {
        joinCompanyService.delete(id);
    }

    @ApiOperation("单条查询")
    @GetMapping
    public JoinCompany get(Long id) {
        return joinCompanyService.findOne(id);
    }

    @ApiOperation("查询所有参建单位")
    @ListPath
    public List<JoinCompany> findAll() {
        return joinCompanyService.findAll();
    }

    @ApiOperation(value = "分页")
    @PagePath
    public Pager<JoinCompany> getPager(PageCondition condition) {
        return joinCompanyService.getPager(condition);
    }

}
