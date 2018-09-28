package net.cdsunrise.wm.prophase.service.Impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.prophase.entity.HDStatisticalData;
import net.cdsunrise.wm.prophase.entity.HouseDemolition;
import net.cdsunrise.wm.prophase.repository.HouseDemolitionRepository;
import net.cdsunrise.wm.prophase.service.HouseDemolitionService;
import net.cdsunrise.wm.prophase.vo.HouseDemolitionSaveVo;
import net.cdsunrise.wm.prophase.vo.ProphaseRefModelVo;
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
public class HouseDemolitionServiceImpl implements HouseDemolitionService {

    @Autowired
    private HouseDemolitionRepository houseDemolitionRepository;

    @Autowired

    private CommonDAO commonDAO;


    @Override
    public void save(HouseDemolitionSaveVo saveVo) {
        HouseDemolition entity = houseDemolitionRepository.save(saveVo.getHouseDemolition());
        Long id = entity.getId();
        if (saveVo != null && saveVo.getList() != null && saveVo.getList().size() > 0) {
            //保存关联模型数据
            houseDemolitionRepository.deleteRefModel(id);
            for (ProphaseRefModelVo temp : saveVo.getList()) {
                houseDemolitionRepository.insertRefModel(id, temp.getFid(), temp.getGuid(), temp.getModelId());
            }
        }
    }

    @Override
    public void saveBatch(List<HouseDemolition> houseDemolitionList) {
        houseDemolitionRepository.save(houseDemolitionList);
    }

    @Override
    public void delete(Long id) {
        houseDemolitionRepository.delete(id);
    }

    @Override
    public HouseDemolition findOne(Long id) {
        return houseDemolitionRepository.findOne(id);
    }

    @Override
    public Pager getPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(HouseDemolition.class).useNativeSql(false).setPageCondition(condition);
        Pager<HouseDemolition> pager = commonDAO.findPager(helper);
        return pager;
    }

    @Override
    public List<HouseDemolition> selectList(HouseDemolition houseDemolition) {
        return houseDemolitionRepository.findAll();
    }

    @Override
    public HDStatisticalData dataStat() {
        QueryHelper helper = new QueryHelper(
                "w1.houseTotalNumber," +
                        "w1.houseTotalArea," +
                        "w4.demolished," +
                        "w4.demolishedArea," +
                        "w3.demolishing," +
                        "w3.demolishingArea," +
                        "w2.notDemolished," +
                        "w2.notDemolishedArea",
                "(select count(*) as houseTotalNumber,sum(demolition_area) as houseTotalArea from wm_house_demolition where demolition_area>0 and demolition_status!=0) w1," +
                        "(select COUNT(*) as notDemolished,SUM(w.demolition_area) as notDemolishedArea from wm_house_demolition w where w.demolition_status = 1) w2," +
                        "(select COUNT(*) as demolishing,SUM(w.demolition_area) as demolishingArea from wm_house_demolition w where w.demolition_status = 2) w3," +
                        "(select COUNT(*) as demolished,SUM(w.demolition_area) as demolishedArea from wm_house_demolition w where w.demolition_status = 3) w4")
                .useNativeSql(true);
        List<HDStatisticalData> list = commonDAO.findList(helper, HDStatisticalData.class);
        if (!list.isEmpty()) {
            HDStatisticalData result = list.get(0);
            return result;
        } else {
            return null;
        }
    }
}
