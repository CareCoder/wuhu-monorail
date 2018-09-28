package net.cdsunrise.wm.quality.controller;

import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.quality.entity.BeamStandard;
import net.cdsunrise.wm.quality.service.BeamStandardService;
import net.cdsunrise.wm.quality.vo.BeamDeviationsVo;
import net.cdsunrise.wm.quality.vo.BeamPierSupportDeviationsVo;
import net.cdsunrise.wm.quality.vo.BeamSectionDeviationsVo;
import net.cdsunrise.wm.quality.vo.BeamStandardQueryResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/***
 * @author gechaoqing
 * 梁墩线性测量控制
 */
@RestController
@RequestMapping("/beam-pier-measure")
public class BeamPierMeasureController {
    @Autowired
    private BeamStandardService standardService;
    @ApiOperation("标准值导入")
    @PostMapping("/import")
    public void importStandardData(MultipartFile multipartFile){
        standardService.beamStandardImport(multipartFile);
    }

    @ApiOperation("根据梁号获取标准数据")
    @GetMapping("/by-code/{code}/width-inverse")
    public BeamStandard getStandardByBeamCode(@PathVariable("code") String code){
        return standardService.getByBeamCode(code);
    }

    @ApiOperation("保存梁综合参数偏差数据")
    @PostMapping("/save/deviation/beam")
    public void saveBeamDeviation(@RequestBody BeamDeviationsVo deviationsVo){
        standardService.saveBeamDeviations(deviationsVo);
    }

    @ApiOperation("审核梁综合参数偏差数据")
    @PostMapping("/audit/deviation/beam")
    public void auditBeamDeviationModify(String beamCode,String status){
        standardService.auditBeamDeviations(beamCode,status);
    }

    @ApiOperation("保存断面偏差数据")
    @PostMapping("/save/deviation/section")
    public void saveBeamSectionDeviation(@RequestBody BeamSectionDeviationsVo sectionDeviationsVo){
        standardService.saveSectionDeviations(sectionDeviationsVo);
    }

    @ApiOperation("审核断面偏差数据")
    @PostMapping("/audit/deviation/section")
    public void auditBeamSectionModify(Integer intType,Long id,String status){
        standardService.auditSectionDeviations(intType,id,status);
    }

    @ApiOperation("保存临时支撑偏差数据")
    @PostMapping("/save/deviation/support")
    public void saveBeamSupportDeviation(@RequestBody BeamPierSupportDeviationsVo supportDeviationsVo){
        standardService.savePierSupportDeviations(supportDeviationsVo);
    }

    @ApiOperation("审核临时支撑偏差数据")
    @PostMapping("/audit/deviation/support")
    public void auditBeamSupportModify(Long id,String status){
        standardService.auditPierSupportDeviations(id,status);
    }


    @ApiOperation("查询所有梁测量数据")
    @GetMapping("/query/beam/all")
    public BeamStandardQueryResultVo getAllStandard(int currentPage,int pageSize){
        return standardService.allBeamStandard(currentPage,pageSize);
    }
}
