package net.cdsunrise.wm.schedule.controller;

import net.cdsunrise.wm.schedule.entity.Model;
import net.cdsunrise.wm.schedule.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/model")
public class ModelController {
    @Autowired
    private ModelService modelService;

    @GetMapping("/by/status/{modelStatus}")
    public List<Model> findByModelStatus(@PathVariable("modelStatus") Integer modelStatus){
        return modelService.findByModelStatus(modelStatus);
    }
}
