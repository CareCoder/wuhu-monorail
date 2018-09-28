package net.cdsunrise.wm.schedule.repository;

import net.cdsunrise.wm.schedule.entity.DelayReason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 * @author gechaoqing
 * 进度延迟原因
 */
public interface DelayReasonRepository extends JpaRepository<DelayReason,Long> {
    /***
     * 根据父级ID获取子集
     * @param parentId 父级ID
     * @return 子集
     */
    List<DelayReason> findByParentId(Long parentId);

    /***
     * 获取根节点延迟因素
     * @return 延迟因素集合
     */
    List<DelayReason> findByParentIdIsNull();

    /***
     * 二级影响因素
     * @return 因素集合
     */
    List<DelayReason> findByParentIdIsNotNull();
}
