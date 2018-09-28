package net.cdsunrise.wm.schedule.service;


import net.cdsunrise.wm.schedule.vo.ScheduleReportCollectionVo;
import net.cdsunrise.wm.schedule.vo.ScheduleReportRefModelVo;
import net.cdsunrise.wm.schedule.vo.ScheduleReportSubmitVo;
import net.cdsunrise.wm.schedule.vo.ScheduleReportVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 进度上报服务类
 * </p>
 *
 * @author cdsunrise.net / gechaoqing
 * @since 2018-02-07
 */
public interface ScheduleReportService {
    /***
     * 进度工程量上报
     * @param reportCollectionVo 上报数据
     */
	void report(ScheduleReportCollectionVo reportCollectionVo,MultipartFile file);

	/***
	 * 进度上报提交数据
	 * @param submitVo 上报数据
	 */
	void reportSubmit(ScheduleReportSubmitVo submitVo);
    /***
     * 上报完成
     * @param planId 进度ID
     */
    void completePlans(Long[] planId);

	/***
	 * 进度上报，获取工程量清单
	 * @param planId 进度任务ID
	 * @return 工程量清单
	 */
	List<ScheduleReportRefModelVo> getRefModelForReport(Long planId);

	/***
	 * 进度上报历史查询
	 * @param planId 进度计划任务ID
	 * @return 上报列表
	 */
	List<ScheduleReportVo> getReportHis(Long planId);

	/***
	 * 根据进度ID列表查询进度上报历史数据
	 * @param planIdList 进度ID列表
	 * @return 上报历史数据列表
	 */
	Map<Long,List<ScheduleReportVo>> getReportHis(List<Long> planIdList);

}
