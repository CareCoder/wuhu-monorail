package net.cdsunrise.wm.beamfield.controller;

import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.util.ExcelImport;
import net.cdsunrise.wm.base.web.annotation.*;
import net.cdsunrise.wm.beamfield.entity.LedgerBasicInformation;
import net.cdsunrise.wm.beamfield.service.LedgerBasicInformationService;
import net.cdsunrise.wm.beamfield.vo.LedgerBasicInformationVo;
import net.cdsunrise.wm.beamfield.vo.LedgerPlanV2VoV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
@RestController
@RequestMapping("/ledgerBasicInfo")
public class LedgerBasicInformationController {

    @Autowired
    private LedgerBasicInformationService ledgerBasicInformationService;

    @ApiOperation("新增/编辑")
    @SavePath
    public void save(LedgerBasicInformation ledgerBasicInformation) {
        ledgerBasicInformationService.save(ledgerBasicInformation);
    }

    @ApiOperation("删除")
    @DeletePath
    public void delete(String beamNumber) {
        ledgerBasicInformationService.delete(beamNumber);
    }

    @ApiOperation("查询单个")
    @GetPath
    public LedgerBasicInformation get(String beamNumber) {
        return ledgerBasicInformationService.get(beamNumber);
    }

    @ApiOperation("分页")
    @PagePath
    public Pager<LedgerBasicInformation> getPager(PageCondition condition) {
        return ledgerBasicInformationService.getPager(condition);
    }

    @ApiOperation("条件查询")
    @PostMapping("/selectList")
    public Pager<LedgerBasicInformation> selectList(LedgerBasicInformationVo ledgerBasicInformationVo, PageCondition condition) {
        return ledgerBasicInformationService.selectList(ledgerBasicInformationVo, condition);
    }

    @ApiOperation("Excel导入")
    @PostMapping(value = "/ledgerImport")
    public String ledgerImport(MultipartFile file) throws ParseException {
        try {
            List<LedgerBasicInformation> ledgers = ExcelImport.excelTransformationEntityList(LedgerBasicInformation.class, file.getInputStream(), file.getOriginalFilename(), 1, 1);
            ledgerBasicInformationService.SaveBatch(ledgers);
            return "导入成功。";
        } catch (IOException e) {
            e.printStackTrace();
            return "导入失败,请联系系统管理员。";
        }
    }

    @ApiOperation("根据墩号数组查询梁数据")
    @PostMapping("/selectByPierNumber")
    public List<LedgerBasicInformation> selectByPierNumber(LedgerPlanV2VoV2 list){
        return ledgerBasicInformationService.selectByPierNumber(list);
    }
}

