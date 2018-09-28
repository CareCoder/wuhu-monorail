package net.cdsunrise.wm.quality.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.quality.entity.GirderPier;
import net.cdsunrise.wm.quality.entity.PierCoordinateStandard;
import net.cdsunrise.wm.quality.entity.PierDisStandard;
import net.cdsunrise.wm.quality.service.LineMeasureService;
import net.cdsunrise.wm.quality.vo.PierCoordinateStandardVo;
import net.cdsunrise.wm.quality.vo.PierDisStandardDeviationVo;
import net.cdsunrise.wm.quality.vo.PierDisStandardSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/***
 * @author gechaoqing
 * 线性测量
 */
@RestController
@Api("线性测量")
@RequestMapping("/line-measure")
public class LineMeasureController {
    @Autowired
    private LineMeasureService measureService;

    @ApiOperation("标准值导入")
    @PostMapping("/import")
    public void standardDataImport(MultipartFile file){
        measureService.standardDataImport(file);
    }

    @ApiOperation("根据梁号获取梁端数据")
    @GetMapping("/by-girder/{code}")
    public List<GirderPier> getByGirderCode(@PathVariable("code") String code){
        return measureService.getPierByGirderCode(code);
    }

    @ApiOperation("根据梁号获取梁端数据，包含相对断面数据")
    @GetMapping("/by-girder/{code}/width-inverse")
    public List<GirderPier> getByGirderCodeWithInverse(@PathVariable("code") String code){
        return measureService.getPierForInput(code);
    }

    @ApiOperation("保存计算平距/高差偏差值")
    @PostMapping("/save/deviation/dis")
    public void saveDeviation(@RequestBody PierDisStandardDeviationVo[] deviation){
        measureService.saveDeviation(Arrays.asList(deviation));
    }

    @ApiOperation("保存计算坐标偏差值")
    @PostMapping("/save/deviation/coor")
    public void saveCoorDeviation(@RequestBody PierCoordinateStandardVo coordinateStandard){
        measureService.saveCoorDeviation(coordinateStandard);
    }
}
