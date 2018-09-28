package net.cdsunrise.wm.schedule.repository;

import net.cdsunrise.wm.schedule.entity.ModelDataCol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/***
 * @author gechaoqing
 * 模型附加数据字段信息仓库
 */
public interface ModelDataColRepository extends JpaRepository<ModelDataCol,Long> {
    /***
     * 批量删除模型附加信息字段定义数据
     * @param ids ID列表
     */
    @Modifying
    @Query(value = "DELETE FROM ModelDataCol WHERE id IN ?1")
    void deleteIdIn(Long[] ids);
}
