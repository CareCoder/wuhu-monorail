package net.cdsunrise.wm.quality.service;

import net.cdsunrise.wm.quality.entity.Teamwork;
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
    void upload(MultipartFile file, Teamwork teamwork);

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
}
