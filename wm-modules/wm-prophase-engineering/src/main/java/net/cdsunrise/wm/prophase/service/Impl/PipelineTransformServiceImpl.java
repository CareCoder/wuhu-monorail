package net.cdsunrise.wm.prophase.service.Impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.prophase.entity.PipelineTransform;
import net.cdsunrise.wm.prophase.repository.PipelineTransformRepository;
import net.cdsunrise.wm.prophase.service.PipelineTransformService;
import net.cdsunrise.wm.prophase.vo.PipelineTranSaveVo;
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
public class PipelineTransformServiceImpl implements PipelineTransformService {

    @Autowired
    private PipelineTransformRepository pipelineTransformRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public void save(PipelineTranSaveVo saveVo) {
        PipelineTransform entity = pipelineTransformRepository.save(saveVo.getPipelineTransform());
        Long id = entity.getId();
        if (saveVo != null && saveVo.getList() != null && saveVo.getList().size() > 0) {
            //保存关联模型数据
            pipelineTransformRepository.deleteRefModel(id);
            for (ProphaseRefModelVo temp : saveVo.getList()) {
                pipelineTransformRepository.insertRefModel(id, temp.getFid(), temp.getGuid(), temp.getModelId());
            }
        }
    }

    @Override
    public void saveBatch(List<PipelineTransform> pipelineTransform) {
        pipelineTransformRepository.save(pipelineTransform);
    }

    @Override
    public void delete(Long id) {
        pipelineTransformRepository.delete(id);
    }

    @Override
    public PipelineTransform findOne(Long id) {
        return pipelineTransformRepository.findOne(id);
    }

    @Override
    public Pager getPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(PipelineTransform.class).useNativeSql(false).setPageCondition(condition);
        Pager pager = commonDAO.findPager(helper);
        return pager;
    }

    @Override
    public List<PipelineTransform> selectList(PipelineTransform pipelineTransform) {
        return pipelineTransformRepository.findAll();
    }
}
