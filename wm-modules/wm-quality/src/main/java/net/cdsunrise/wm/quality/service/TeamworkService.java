package net.cdsunrise.wm.quality.service;

import net.cdsunrise.wm.quality.entity.Teamwork;
import net.cdsunrise.wm.quality.entity.WorkPoint;
import net.cdsunrise.wm.quality.vo.TeamworkVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeamworkService {
    /**
     * 列表
     */
    List<TeamworkVo> list();

    /**
     * 上传
     */
    void upload(MultipartFile[] file, Teamwork teamwork);

    /**
     * 根据非空字段条件查询
     */
    List<TeamworkVo> query(Teamwork teamwork);

    /**
     * 提供压缩文件
     * @param ids id
     */
    ResponseEntity<byte[]> zip(Long[] ids);

    /**
     * 删除
     */
    void delete(Long[] id);

    /**
     * 更新
     */
    void update(Teamwork teamwork);

    /**
     * 根据workPointId 查询出所有 teamwork 然后下载
     * @param workPointId 工作站点
     * @return 压缩文件
     */
    ResponseEntity<byte[]> zip(Long workPointId);

    /**
     * 上传文件夹,直接生成一个workpoint下面的所有内容
     * @param files 所有文件
     * @param workPoint 工作站的实体
     */
    void uploadFloder(MultipartFile[] files, WorkPoint workPoint);

    /**
     * 批量发布
     * @param ids
     */
    void publishBatch(Integer status, Long[] ids);
}
