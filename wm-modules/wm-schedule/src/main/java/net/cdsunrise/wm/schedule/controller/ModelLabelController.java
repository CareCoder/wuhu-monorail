package net.cdsunrise.wm.schedule.controller;

import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.schedule.entity.ModelLabel;
import net.cdsunrise.wm.schedule.service.ModelLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author gechaoqing
 * 模型标签控制
 */
@RestController
@RequestMapping("/model-label")
public class ModelLabelController {
    @Autowired
    private ModelLabelService modelLabelService;

    @ApiOperation("保存模型标签")
    @PostMapping("/save")
    public void saveLabel(ModelLabel modelLabel){
        modelLabelService.save(modelLabel);
    }

    @ApiOperation("获取模型标签列表")
    @PostMapping("/list-all")
    public Map<Long,List<ModelLabel>> getAll(){
        return modelLabelService.getAll();
    }

    @ApiOperation("删除模型标签")
    @PostMapping("/del")
    public void delete(Long[] ids){
        modelLabelService.delete(ids);
    }

    @ApiOperation("根据模型ID获取模型标签")
    @GetMapping("/list/{modelId}")
    public List<ModelLabel> getByModelId(@PathVariable("modelId") Long modelId){
        return modelLabelService.getByModelId(modelId);
    }
}
