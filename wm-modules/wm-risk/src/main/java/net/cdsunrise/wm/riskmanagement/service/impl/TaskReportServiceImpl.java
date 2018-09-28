package net.cdsunrise.wm.riskmanagement.service.impl;

import net.cdsunrise.wm.riskmanagement.bo.ImageResourceBo;
import net.cdsunrise.wm.riskmanagement.bo.TaskReportBo;
import net.cdsunrise.wm.riskmanagement.entity.ImageResource;
import net.cdsunrise.wm.riskmanagement.entity.TaskReport;
import net.cdsunrise.wm.riskmanagement.repository.TaskReportRepository;
import net.cdsunrise.wm.riskmanagement.service.ImageResourceService;
import net.cdsunrise.wm.riskmanagement.service.TaskReportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Service
public class TaskReportServiceImpl implements TaskReportService {
    @Autowired
    private TaskReportRepository taskReportRepository;
    @Value("${imageUrlPrefix}")
    private String imageUrlPrefix;
    @Value("${spring.application.name}")
    private String serverName;
    @Autowired
    private ImageResourceService imageResourceService;

    @Override
    public void save(TaskReport report) {
        taskReportRepository.save(report);
    }

    @Override
    public TaskReport findOne(Long id) {
        return taskReportRepository.findOne(id);
    }

    @Override
    public List<TaskReportBo> findBoByTaskId(Long taskId) {
        List<TaskReport> reports = taskReportRepository.findByTaskId(taskId);
        List<TaskReportBo> bos = new ArrayList<>();
        reports.forEach(r ->
                bos.add(convertToBo(r))
        );
        return bos;
    }

    @Override
    public void deleteByTaskId(Long taskId) {
        List<TaskReport> reports = taskReportRepository.findByTaskId(taskId);
        reports.forEach(r ->
                imageResourceService.deleteByReportId(r.getId())
        );
        taskReportRepository.deleteByTaskId(taskId);
    }

    private TaskReportBo convertToBo(TaskReport report) {
        TaskReportBo bo = new TaskReportBo();
        BeanUtils.copyProperties(report, bo);
        List<ImageResourceBo> imageResourceBos = new ArrayList<>();
        report.getReportImages().forEach(m -> {
            ImageResourceBo mbo = new ImageResourceBo();
            BeanUtils.copyProperties(m, mbo);
            mbo.setUrl("/" + serverName + imageUrlPrefix + m.getNewName());
            imageResourceBos.add(mbo);
        });
        return bo;
    }
}
