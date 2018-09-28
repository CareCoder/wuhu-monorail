package net.cdsunrise.wm.schedule.repository;


import net.cdsunrise.wm.schedule.entity.ModelCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/***
 * @author gechaoqing
 * 模型数据分类信息仓库
 */
public interface ModelCategoryRepository extends JpaRepository<ModelCategory,Long> {
    /***
     * 批量删除模型数据
     * @param ids ID列表
     */
    @Modifying
    @Query(value = "DELETE FROM ModelCategory WHERE id IN ?1")
    void deleteIdIn(Long[] ids);

    /***
     * 根据模型分类ID删除关联模型字段数据
     * @param categoryId 分类ID
     */
    @Modifying
    @Query(value = "DELETE FROM wm_model_category_ref_col WHERE category_id=?1",nativeQuery = true)
    void deleteRefColByCategoryId(Long categoryId);

    /***
     * 存入模型分类关联模型字段数据
     * @param categoryId 分类ID
     * @param colId 模型字段ID
     */
    @Modifying
    @Query(value = "INSERT INTO wm_model_category_ref_col(category_id,col_id) VALUES(?1,?2)",nativeQuery = true)
    void insertRefCol(Long categoryId,Long colId);

}
