package net.cdsunrise.wm.schedule.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.annotation.PagePath;
import net.cdsunrise.wm.schedule.entity.Model;
import net.cdsunrise.wm.schedule.entity.ModelData;
import net.cdsunrise.wm.schedule.entity.ModelDataCol;
import net.cdsunrise.wm.schedule.service.ModelDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author gechaoqing
 * 模型数据控制层
 */
@Api("模型数据处理")
@RestController
@RequestMapping("/model-data")
public class ModelDataController {
    private ModelDataService modelDataService;
    public ModelDataController(ModelDataService modelDataService){
        this.modelDataService = modelDataService;
    }

    @ApiOperation("保存模型数据")
    @PostMapping("/save")
    public void saveModelData(ModelData modelData){
        modelDataService.save(modelData);
    }

    @ApiOperation("保存模型数据字段定义")
    @PostMapping("/extra-col/save")
    public void saveExtraCell(ModelDataCol extraCell){
        modelDataService.save(extraCell);
    }

    @ApiOperation("获取所有模型数据字段定义")
    @GetMapping("/extra-col/list")
    public List<ModelDataCol> getAllExtraCell(){
        return modelDataService.getAllExtraCells();
    }

    @ApiOperation("根据模型唯一标识获取模型数据列表")
    @PostMapping("/list/{uniqueId}")
    public List<ModelData> getModelData(@PathVariable("uniqueId") String uniqueId){
        return modelDataService.getModelDataByModelUniqueId(uniqueId);
    }

    @ApiOperation("模型分页数据")
    @PagePath
    public Pager<Model> getModelPage(PageCondition pageCondition){
        return modelDataService.getModelPage(pageCondition);
    }

    @ApiOperation("根据模型ID获取模型数据")
    @GetMapping("/by/{modelId}")
    public List<ModelData> getModelDataByModelId(@PathVariable("modelId") Long modelId){
        return modelDataService.getModelDataByModelId(modelId);
    }

    @ApiOperation("删除模型数据")
    @PostMapping("/delete")
    public void deleteModelDatas(Long[] ids){
        modelDataService.deleteModelData(ids);
    }
    @ApiOperation("删除模型附加数据字段定义")
    @PostMapping("/extra-cell/delete")
    public void deleteExtraCells(Long[] ids){
        modelDataService.deleteExtraCell(ids);
    }

    @ApiOperation("设置模型分类关联数据字段")
    @PostMapping("/category/set-col")
    public void setCategoryRefCol(Long categoryId,List<Long> colIds){
        modelDataService.setCategoryCols(categoryId,colIds);
    }
}
