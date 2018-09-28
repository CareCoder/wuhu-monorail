package net.cdsunrise.wm.quality.service;

import net.cdsunrise.wm.quality.entity.BeamStandard;
import net.cdsunrise.wm.quality.vo.BeamDeviationsVo;
import net.cdsunrise.wm.quality.vo.BeamPierSupportDeviationsVo;
import net.cdsunrise.wm.quality.vo.BeamSectionDeviationsVo;
import net.cdsunrise.wm.quality.vo.BeamStandardQueryResultVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 * @author gechaoqing
 * 梁墩标准数据服务
 */
public interface BeamStandardService {
    /***
     * 梁墩标准数据导入
     * @param file 导入文件
     */
    void beamStandardImport(MultipartFile file);

    /***
     * 根据梁号获取标准数据
     * @param code 梁号
     * @return 标准数据
     */
    BeamStandard getByBeamCode(String code);

    /***
     * 保存梁综合参数偏差数据
     * @param beamDeviationsVo 偏差数据
     */
    void saveBeamDeviations(BeamDeviationsVo beamDeviationsVo);

    /***
     * 保存墩临时支撑偏差数据
     * @param deviationsVo 偏差数据
     */
    void savePierSupportDeviations(BeamPierSupportDeviationsVo deviationsVo);

    /***
     * 保存断面偏差数据
     * @param deviationsVo 偏差数据
     */
    void saveSectionDeviations(BeamSectionDeviationsVo deviationsVo);

    /**
     * 获取所有梁测量数据
     * @param currentPage 当前页数
     * @param pageSize 每页显示数量
     * @return 梁测量数据列表
     */
    BeamStandardQueryResultVo allBeamStandard(int currentPage,int pageSize);

    /***
     * 梁综合测量数据审核
     * @param beamCode 梁号
     * @param status 审核状态
     */
    void auditBeamDeviations(String beamCode,String status);
    /***
     * 梁墩测量数据审核
     * @param id id
     * @param status 审核状态
     */
    void auditPierSupportDeviations(Long id,String status);
    /***
     * 断面测量数据审核
     * @param intType 类型 1 梁端  2 梁间
     * @param id 断面ID
     * @param status 审核状态
     */
    void auditSectionDeviations(Integer intType,Long id,String status);
}
