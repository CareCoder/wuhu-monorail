package net.cdsunrise.wm.system.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.system.entity.ProjectLine;
import net.cdsunrise.wm.system.entity.WorkPoint;
import net.cdsunrise.wm.system.repository.ProjectLineRepository;
import net.cdsunrise.wm.system.repository.WorkPointRepository;
import net.cdsunrise.wm.system.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/***
 * @author gechaoqing
 * 项目服务
 * 线路、工点数据服务
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private CommonDAO commonDAO;
    private ProjectLineRepository lineRepository;
    private WorkPointRepository workPointRepository;

    public ProjectServiceImpl(CommonDAO commonDAO
            , ProjectLineRepository lineRepository
            , WorkPointRepository workPointRepository){
        this.commonDAO = commonDAO;
        this.lineRepository = lineRepository;
        this.workPointRepository = workPointRepository;
    }

    @Override
    public Pager getLinePager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(ProjectLine.class, "l").useNativeSql(false).setPageCondition(condition);
        return commonDAO.findPager(helper);
    }

    @Override
    public Pager getWorkPointPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(WorkPoint.class, "l").useNativeSql(false).setPageCondition(condition);
        return commonDAO.findPager(helper);
    }

    @Override
    public void save(ProjectLine line) {
        if(line==null){
            throw new ServiceException("无可保存的线路数据");
        }
        if(StringUtils.isBlank(line.getName())){
            throw new ServiceException("线路名称不可为空");
        }
        lineRepository.save(line);
    }

    @Override
    public void save(WorkPoint workPoint) {
        if(workPoint==null){
            throw new ServiceException("无可保存的工点/区间数据");
        }
        if(StringUtils.isBlank(workPoint.getName())){
            throw new ServiceException("线路名称不可为空");
        }
        if(StringUtils.isBlank(workPoint.getType())){
            throw new ServiceException("没有类型数据，是工点还是区间？");
        }
        if(workPoint.getLineId()==null||workPoint.getLineId()==0){
            throw new ServiceException("工点/区间未知所属线路！");
        }
        workPointRepository.save(workPoint);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLine(Long lineId) {
        if(lineId==null||lineId==0){
            throw new ServiceException("无效数据");
        }
        workPointRepository.deleteByLineId(lineId);
        lineRepository.delete(lineId);
    }

    @Override
    public void deleteWorkPoint(Long workPointId) {
        if(workPointId==null||workPointId==0){
            throw new ServiceException("无效数据");
        }
        workPointRepository.delete(workPointId);
    }

    @Override
    public List<WorkPoint> getByLine(Long lineId) {
        return workPointRepository.findByLineId(lineId);
    }

    @Override
    public List<WorkPoint> getByLineAndType(Long lineId, String type) {
        return workPointRepository.findByLineIdAndType(lineId,type);
    }

    @Override
    public ProjectLine getLineById(Long id) {
        return lineRepository.findOne(id);
    }

    @Override
    public WorkPoint getWorkPointById(Long id) {
        return workPointRepository.findOne(id);
    }
}
