package net.cdsunrise.wm.hiddentrouble.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.exception.DataBaseRollBackException;
import net.cdsunrise.wm.base.exception.ServiceErrorException;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.hiddentrouble.bo.*;
import net.cdsunrise.wm.hiddentrouble.client.UserClient;
import net.cdsunrise.wm.hiddentrouble.entity.HiddenTrouble;
import net.cdsunrise.wm.hiddentrouble.entity.ImageResource;
import net.cdsunrise.wm.hiddentrouble.enums.HiddenTroubleStatus;
import net.cdsunrise.wm.hiddentrouble.repository.HiddenTroubleRepository;
import net.cdsunrise.wm.hiddentrouble.service.HiddenTroubleService;
import net.cdsunrise.wm.hiddentrouble.service.ImageResourceService;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleAcceptanceVo;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleCreateVo;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleQueryVo;
import net.cdsunrise.wm.hiddentrouble.vo.HiddenTroubleReportVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
 */
@Transactional(rollbackFor = {DataBaseRollBackException.class})
@Service
public class HiddenTroubleServiceImpl implements HiddenTroubleService {
    @Value("${imageUrlPrefix}")
    private String imageUrlPrefix;
    @Autowired
    private HiddenTroubleRepository hiddenTroubleRepository;
    @Autowired
    private ImageResourceService imageResourceService;
    @Autowired
    private UserClient userClient;
    @Autowired
    private CommonDAO commonDAO;

    @Override
    public void create(HiddenTroubleCreateVo createVo) {
        HiddenTrouble hiddenTrouble = new HiddenTrouble();
        List<ImageResource> images = imageResourceService.upload(createVo.getImages());
        UserBo handler = userClient.get(createVo.getHandlerId());
        UserBo creator = userClient.get(UserUtils.getUserId());
        hiddenTrouble.setImages(images);
        hiddenTrouble.setLevel(createVo.getLevel());
        hiddenTrouble.setName(createVo.getName());
        hiddenTrouble.setHandlerId(handler.getId());
        hiddenTrouble.setHandlerName(handler.getRealName());
        hiddenTrouble.setWorkPointId(createVo.getWorkPointId());
        hiddenTrouble.setWorkPointName(createVo.getWorkPointName());
        hiddenTrouble.setAcceptancePersonId(createVo.getAcceptancePersonId());
        hiddenTrouble.setCreatorId(creator.getId());
        hiddenTrouble.setHandlerName(creator.getRealName());
        hiddenTrouble.setStatus(HiddenTroubleStatus.UNPROCESSED);
        hiddenTroubleRepository.save(hiddenTrouble);
    }

    @Override
    public void report(HiddenTroubleReportVo reportVo) {
        //判断当前用户是否是录入的处理人
        HiddenTrouble hiddenTrouble = hiddenTroubleRepository.findOne(reportVo.getId());
        if (UserUtils.getUserId() == hiddenTrouble.getHandlerId()) {
            throw new ServiceErrorException("当前用户不是录入的经办人！");
        }
        hiddenTrouble.setHandleTime(new Date());
        hiddenTrouble.setHandleScheme(reportVo.getScheme());
        hiddenTrouble.setStatus(HiddenTroubleStatus.PROCESSED);
        hiddenTroubleRepository.save(hiddenTrouble);
    }

    @Override
    public void acceptance(HiddenTroubleAcceptanceVo acceptanceVo) {
        HiddenTrouble hiddenTrouble = hiddenTroubleRepository.findOne(acceptanceVo.getId());
        if (UserUtils.getUserId().equals(hiddenTrouble.getAcceptancePersonId())) {
            throw new ServiceErrorException("当前用户不是录入的验收人！");
        }
        hiddenTrouble.setAcceptanceRemark(acceptanceVo.getRemark());
        hiddenTrouble.setStatus(HiddenTroubleStatus.ACCEPTED);
        hiddenTroubleRepository.save(hiddenTrouble);
    }

    @Override
    public StatusStatisticsBo getStatusStatistics(Long workPointId, Date startDate, Date endDate) {
        QueryHelper helper = new QueryHelper("ifnull(sum(case when t.status='UNPROCESSED' then 1 else 0 end), 0) unprocessed," +
                "ifnull(sum(case when t.status='PROCESSED' then 1 else 0 end), 0) processed," +
                "ifnull(sum(case when t.status='ACCEPTED' then 1 else 0 end), 0) accepted",
                "wm_hidden_trouble t")
                .addCondition(workPointId != null, "t.work_point_id=?", workPointId)
                .addCondition(startDate != null, "t.create_time>=?", startDate)
                .addCondition(endDate != null, "t.create_time<=?", endDate);
        List<StatusStatisticsBo> bos = commonDAO.findList(helper, StatusStatisticsBo.class);
        return bos.get(0);
    }

    @Override
    public List<LevelSummaryBo> getLevelSummary(Long workPointId, Date startDate, Date endDate) {
        QueryHelper helper = new QueryHelper("date_format(h.create_time, '%Y-%m') yearMonthStr,\n" +
                "sum(case when h.`level` = 1 then 1 else 0 end) lv1,\n" +
                "sum(case when h.`level` = 2 then 1 else 0 end) lv2,\n" +
                "sum(case when h.`level` = 3 then 1 else 0 end) lv3,\n" +
                "sum(case when h.`level` = 4 then 1 else 0 end) lv4,\n" +
                "sum(case when h.`level` = 5 then 1 else 0 end) lv5"
                , "wm_hidden_trouble h")
                .addCondition(workPointId != null, "h.work_point_id=?", workPointId)
                .addCondition(null != startDate, "h.create_time>=?", startDate)
                .addCondition(null != endDate, "h.create_time<=?", endDate)
                .addOrderProperty("yearMonthStr", Boolean.FALSE)
                .addGroupBy("yearMonthStr");
        List<LevelSummaryBo> list = commonDAO.findList(helper, LevelSummaryBo.class);

        List<LevelSummaryBo> bos = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
        while (startTime.getYear() <= endTime.getYear() && startTime.getMonth().getValue() <= endTime.getMonth().getValue()) {
            //当前循环的年月
            boolean has = false;
            String nym = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            if (list != null && !list.isEmpty()) {
                for (LevelSummaryBo b : list) {
                    if (b.getYearMonthStr().equals(nym)) {
                        bos.add(b);
                        has = true;
                        break;
                    }
                }
            }
            if (!has) {
                LevelSummaryBo b = new LevelSummaryBo();
                b.setYearMonthStr(nym);
                bos.add(b);
            }
            //加一个月
            startTime = startTime.plusMonths(1);
        }
        return bos;
    }

    @Override
    public Pager<HiddenTroubleBo> getPager(HiddenTroubleQueryVo queryVo, PageCondition condition) {
        QueryHelper helper = getListHelper(queryVo)
                .setPageCondition(condition);
        Pager<HiddenTrouble> p = commonDAO.findPager(helper);
        return new Pager<>(p.getNumber(), p.getPageSize(), p.getTotalElements(), convertToBos(p.getContent()));
    }

    @Override
    public HiddenTroubleBo get(Long id) {
        HiddenTrouble entity = hiddenTroubleRepository.findOne(id);
        return convertToBo(entity);
    }

    @Override
    public Map<String, Integer> getLevelStatistics(Long workPointId, Date startDate, Date endDate) {
        QueryHelper helper = new QueryHelper("ifnull(sum(case when h.`level` = 1 then 1 else 0 end),0) lv1,\n" +
                "ifnull(sum(case when h.`level` = 2 then 1 else 0 end),0) lv2,\n" +
                "ifnull(sum(case when h.`level` = 3 then 1 else 0 end),0) lv3,\n" +
                "ifnull(sum(case when h.`level` = 4 then 1 else 0 end),0) lv4,\n" +
                "ifnull(sum(case when h.`level` = 5 then 1 else 0 end),0) lv5", "wm_hidden_trouble h ")
                .addCondition(startDate != null, "h.create_time>=?", startDate)
                .addCondition(endDate != null, "h.create_time<=?", endDate)
                .addCondition(workPointId != null, "h.work_point_id=?", workPointId);
        List<Map> list = commonDAO.findList(helper);
        Map<String, Integer> map = new HashMap<>(3);
        map.put("lv1", 0);
        map.put("lv2", 0);
        map.put("lv3", 0);
        map.put("lv4", 0);
        map.put("lv5", 0);
        if (list != null && list.size() > 0) {
            list.get(0).forEach((k, v) -> {
                map.put(k.toString(), ((BigDecimal) v).intValue());
            });
        }
        return map;
    }

    private List<HiddenTroubleBo> convertToBos(List<HiddenTrouble> hiddenTroubles) {
        List<HiddenTroubleBo> bos = new ArrayList<>();
        hiddenTroubles.forEach(o ->
                bos.add(convertToBo(o))
        );
        return bos;
    }

    private HiddenTroubleBo convertToBo(HiddenTrouble hiddenTrouble) {
        HiddenTroubleBo bo = new HiddenTroubleBo();
        if (hiddenTrouble == null) {
            return bo;
        }
        BeanUtils.copyProperties(hiddenTrouble, bo);
        List<ImageResourceBo> imageResourceBos = new ArrayList<>();
        if (!hiddenTrouble.getImages().isEmpty()) {
            for (ImageResource m : hiddenTrouble.getImages()) {
                ImageResourceBo imageBo = new ImageResourceBo();
                imageBo.setId(m.getId());
                imageBo.setNewName(m.getNewName());
                imageBo.setOriginalName(m.getOriginalName());
                imageBo.setUrl(imageUrlPrefix + m.getNewName());
                imageResourceBos.add(imageBo);
            }
        }
        bo.setImages(imageResourceBos);
        return bo;
    }

    /**
     * 列表查询helper
     *
     * @param queryVo
     * @return
     */
    private QueryHelper getListHelper(HiddenTroubleQueryVo queryVo) {
        QueryHelper helper = new QueryHelper(HiddenTrouble.class, "t")
                .addCondition(queryVo.getLevel() != null, "t.level=?", queryVo.getLevel())
                .addCondition(queryVo.getStatus() != null, "t.status=?", queryVo.getStatus())
                .addCondition(StringUtils.isNotBlank(queryVo.getHandlerName()), "t.handlerName like ?", "%" + queryVo.getHandlerName() + "%")
                .addCondition(queryVo.getHandleStartDate() != null, "t.handleTime>=?", queryVo.getHandleStartDate())
                .addCondition(queryVo.getHandleEndDate() != null, "t.handleTime<=?", queryVo.getHandleEndDate())
                .addOrderProperty("t.createTime", false)
                .useNativeSql(false);
        return helper;
    }

}
