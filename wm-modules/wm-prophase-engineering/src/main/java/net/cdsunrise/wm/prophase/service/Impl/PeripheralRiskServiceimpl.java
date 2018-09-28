package net.cdsunrise.wm.prophase.service.Impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.prophase.entity.PeripheralRisk;
import net.cdsunrise.wm.prophase.repository.HouseDemolitionRepository;
import net.cdsunrise.wm.prophase.repository.PeripheralRiskRepository;
import net.cdsunrise.wm.prophase.service.PeripheralRiskService;
import net.cdsunrise.wm.prophase.vo.PeripheralRiskSaveVo;
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
public class PeripheralRiskServiceimpl implements PeripheralRiskService {

    @Autowired
    private PeripheralRiskRepository peripheralRiskRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public void save(PeripheralRiskSaveVo saveVo) {
        PeripheralRisk entity = peripheralRiskRepository.save(saveVo.getPeripheralRisk());
        Long id = entity.getId();
        if (saveVo != null && saveVo.getList() != null && saveVo.getList().size() > 0) {
            //保存关联模型数据
            peripheralRiskRepository.deleteRefModel(id);
            for (ProphaseRefModelVo temp : saveVo.getList()) {
                peripheralRiskRepository.insertRefModel(id, temp.getFid(), temp.getGuid(), temp.getModelId());
            }
        }
    }

    @Override
    public void saveBatch(List<PeripheralRisk> peripheralRisk) {
        peripheralRiskRepository.save(peripheralRisk);
    }

    @Override
    public void delete(Long id) {
        peripheralRiskRepository.delete(id);
    }

    @Override
    public PeripheralRisk findOne(Long id) {
        return peripheralRiskRepository.findOne(id);
    }

    @Override
    public Pager getPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(PeripheralRisk.class).useNativeSql(false).setPageCondition(condition);
        Pager pager = commonDAO.findPager(helper);
        return pager;
    }

    @Override
    public List<PeripheralRisk> selectList(PeripheralRisk peripheralRisk) {
        return peripheralRiskRepository.findAll();
    }
}
