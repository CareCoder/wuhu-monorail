package net.cdsunrise.wm.riskmanagement.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.riskmanagement.bo.RiskSourceLevelSummaryBo;
import net.cdsunrise.wm.riskmanagement.entity.RiskSource;
import net.cdsunrise.wm.riskmanagement.service.RiskSourceService;
import net.cdsunrise.wm.riskmanagement.vo.RiskSourceQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2018-04-20.
 * @descritpion
 */
@Service
public class RiskSourceServiceImpl implements RiskSourceService {
    @Autowired
    private CommonDAO commonDAO;

    @Override
    public Pager<RiskSource> getPager(RiskSourceQueryVo queryVo, PageCondition condition) {
        QueryHelper helper = getQueryHelper(queryVo)
                .setPageCondition(condition);
        return commonDAO.findPager(helper);
    }

    @Override
    public List<RiskSource> getList(RiskSourceQueryVo queryVo) {
        QueryHelper helper = getQueryHelper(queryVo);
        return commonDAO.findList(helper);
    }

    @Override
    public List<RiskSourceLevelSummaryBo> getLevelSummary(String workPointName) {
        QueryHelper helper = new QueryHelper("year_month_str yearMonthStr, \n" +
                "sum(case when com_evaluation = 1 then 1 else 0 end) lv1,\n" +
                "sum(case when com_evaluation = 2 then 1 else 0 end) lv2,\n" +
                "sum(case when com_evaluation = 3 then 1 else 0 end) lv3,\n" +
                "sum(case when com_evaluation = 4 then 1 else 0 end) lv4,\n" +
                "sum(case when com_evaluation = 5 then 1 else 0 end) lv5", "wm_risk_source")
                .addCondition("work_point_names like ?", "%" + workPointName + "%")
                .addGroupBy("year_month_str")
                .addOrderProperty("year_month_str", false);
        List<RiskSourceLevelSummaryBo> bos = commonDAO.findList(helper, RiskSourceLevelSummaryBo.class);
        return bos;
    }

    @Override
    public Map<String, Integer> getLevelStatistics(String workPointName) {
        QueryHelper helper = new QueryHelper("ifnull(sum(case when com_evaluation = 1 then 1 else 0 end),0) lv1,\n" +
                "ifnull(sum(case when com_evaluation = 2 then 1 else 0 end),0) lv2,\n" +
                "ifnull(sum(case when com_evaluation = 3 then 1 else 0 end),0) lv3,\n" +
                "ifnull(sum(case when com_evaluation = 4 then 1 else 0 end),0) lv4,\n" +
                "ifnull(sum(case when com_evaluation = 5 then 1 else 0 end),0) lv5", "wm_risk_source ")
                .addCondition(StringUtils.isNotBlank(workPointName), "work_point_names like ?", "% " + workPointName + " % ");
        List<Map> list = commonDAO.findList(helper);
        Map<String, Integer> map = new HashMap<>(5);
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

    private QueryHelper getQueryHelper(RiskSourceQueryVo queryVo) {
        QueryHelper helper = new QueryHelper(RiskSource.class, "r")
                .addCondition(StringUtils.isNotBlank(queryVo.getWorkPointName()), "r.workPointNames LIKE ?", "%" + queryVo.getWorkPointName() + "%")
                .addCondition(queryVo.getYearMonthStr() != null, "r.yearMonthStr=?", queryVo.getYearMonthStr())
                .addOrderProperty("r.workAct", Boolean.FALSE)
                .useNativeSql(false);
        return helper;
    }
}
