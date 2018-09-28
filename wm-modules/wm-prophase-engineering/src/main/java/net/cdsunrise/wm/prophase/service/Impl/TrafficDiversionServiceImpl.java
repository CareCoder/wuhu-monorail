package net.cdsunrise.wm.prophase.service.Impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.prophase.entity.TrafficDiversion;
import net.cdsunrise.wm.prophase.repository.TrafficDiversionRepository;
import net.cdsunrise.wm.prophase.service.TrafficDiversionService;
import net.cdsunrise.wm.prophase.vo.LocationVo;
import net.cdsunrise.wm.prophase.vo.ProphaseRefModelVo;
import net.cdsunrise.wm.prophase.vo.TrafficDiversionSaveVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :
 */
@Service
@Transactional
public class TrafficDiversionServiceImpl implements TrafficDiversionService {

    @Autowired
    private TrafficDiversionRepository trafficDiversionRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public void save(TrafficDiversionSaveVo saveVo) {
        TrafficDiversion entity = trafficDiversionRepository.save(saveVo.getTrafficDiversion());
        Long id = entity.getId();
        if (saveVo != null && saveVo.getList() != null && saveVo.getList().size() > 0) {
            //保存关联模型数据
            trafficDiversionRepository.deleteRefModel(id);
            for (ProphaseRefModelVo temp : saveVo.getList()) {
                trafficDiversionRepository.insertRefModel(id, temp.getFid(), temp.getGuid(), temp.getModelId());
            }
        }
    }

    @Override
    public void saveBatch(List<TrafficDiversion> trafficDiversion) {
        trafficDiversionRepository.save(trafficDiversion);
    }

    @Override
    public void delete(Long id) {
        trafficDiversionRepository.delete(id);
    }

    @Override
    public TrafficDiversion findOne(Long id) {
        return trafficDiversionRepository.findOne(id);
    }

    @Override
    public Pager getPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(TrafficDiversion.class).useNativeSql(false).setPageCondition(condition);
        Pager<TrafficDiversion> pager = commonDAO.findPager(helper);
        return pager;
    }

    @Override
    public List<TrafficDiversion> selectList(TrafficDiversion trafficDiversion) {
        return trafficDiversionRepository.findAll();
    }

    @Override
    public List<LocationVo> allModelLocation() {
        QueryHelper helper = new QueryHelper("result.locationx as locationX,result.locationy as locationY,result.locationz as locationZ",
                "(select locationx,locationy,locationz from wm_traffic_diversion where locationx<>'' and locationy<>'' and locationz<>'') result")
                .useNativeSql(true);
        List<LocationVo> list = commonDAO.findList(helper, LocationVo.class);
        return list;
    }
}
