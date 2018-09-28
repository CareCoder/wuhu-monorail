package net.cdsunrise.wm.quality.service;

import net.cdsunrise.wm.quality.entity.GirderPier;
import net.cdsunrise.wm.quality.entity.PierCoordinateStandard;
import net.cdsunrise.wm.quality.entity.PierDisStandard;
import net.cdsunrise.wm.quality.vo.PierCoordinateStandardVo;
import net.cdsunrise.wm.quality.vo.PierDisStandardDeviationVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 * @author gechaoqing
 * 线性测量服务
 */
public interface LineMeasureService {
    /**
     * 标准数据导入
     * @param file 文件
     */
    void standardDataImport(MultipartFile file);

    /***
     * 根据梁号获取梁端数据
     * @param girderCode 梁号
     * @return 梁端列表数据
     */
    List<GirderPier> getPierByGirderCode(String girderCode);

    /***
     * 根据梁号获取梁端数据
     * @param girderCode 梁号
     * @return 梁端列表数据
     */
    List<GirderPier> getPierForInput(String girderCode);

    /***
     * 保存偏差值
     * @param pierDisStandardList 原偏差数据
     */
    void saveDeviation(List<PierDisStandardDeviationVo> pierDisStandardList);

    /***
     * 保存坐标偏差
     * @param coordinateStandard 坐标偏差数据
     */
    void saveCoorDeviation(PierCoordinateStandardVo coordinateStandard);
}
