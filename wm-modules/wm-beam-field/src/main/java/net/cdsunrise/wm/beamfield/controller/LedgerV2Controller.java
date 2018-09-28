package net.cdsunrise.wm.beamfield.controller;

import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.annotation.GetPath;
import net.cdsunrise.wm.base.web.annotation.ListPath;
import net.cdsunrise.wm.base.web.annotation.SavePath;
import net.cdsunrise.wm.beamfield.entity.LedgerV2;
import net.cdsunrise.wm.beamfield.service.LedgerV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Author: WangRui
 * Date: 2018/6/8
 * Describe:
 */
@RestController
@RequestMapping("/ledger2")
public class LedgerV2Controller {

    @Autowired
    private LedgerV2Service ledgerV2Service;

    @ApiOperation("新增")
    @SavePath
    public void save(LedgerV2 ledgerV2) {
        ledgerV2Service.save(ledgerV2);
    }

    @ApiOperation("查询单条")
    @GetPath
    public LedgerV2 get(Long id) {
        return ledgerV2Service.get(id);
    }

    @ApiOperation("分页")
    @ListPath
    public Pager<LedgerV2> list(PageCondition condition) {
        return ledgerV2Service.page(condition);
    }

    @ApiOperation("Excel导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        ledgerV2Service.export(response);
    }

}
