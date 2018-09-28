package net.cdsunrise.wm.schedule.feign;

/***
 * @author gechaoqing
 * 梁相关客户端
 */

import net.cdsunrise.wm.schedule.vo.CreateLedgerPlanVo;
import net.cdsunrise.wm.schedule.vo.LedgerPiersVo;
import net.cdsunrise.wm.schedule.vo.LedgerPlanVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "wm-beam-field")
public interface BeamFeign {
    /***
     * 生成制梁计划
     * @param ledgerPlanVo 制梁计划参数
     */
    @PostMapping("/ledgerPlanV2/createLedgerPlanByPier")
    void createLedgerPlan(LedgerPlanVo ledgerPlanVo);

    /**
     * 获取梁基本信息
     * @param list
     * @return
     */
    @PostMapping("/ledgerBasicInfo/selectByPierNumber")
    List<LedgerPiersVo> fetchLedgerPiers(LedgerPlanVo list);

    /***
     * 生成制梁计划
     * @param createLedgerPlanVoList
     */
    @PostMapping("/ledgerPlanV2/createLedgerPlanByBNList")
    void createLedgerPlan(@RequestBody List<CreateLedgerPlanVo> createLedgerPlanVoList);
}
