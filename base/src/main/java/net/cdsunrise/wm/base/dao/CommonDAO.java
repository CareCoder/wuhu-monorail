package net.cdsunrise.wm.base.dao;

import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;


/**
 * @author lijun
 * @date 2018-04-02.
 */
@EnableJpaRepositories
public interface CommonDAO {
    /**
     * 分页方法
     *
     * @param helper
     * @return
     */
    <T> Pager<T> findPager(QueryHelper helper);

    /**
     * 分页查询 返回指定的对象
     *
     * @param helper
     * @param tClass
     * @param <T>
     * @return
     */
    <T> Pager<T> findPager(QueryHelper helper, Class<T> tClass);

    /**
     * 列表查询
     *
     * @param helper
     * @return
     */
    <T> List<T> findList(QueryHelper helper);

    /**
     * 列表查询 返回指定对象
     *
     * @param helper
     * @param tClass
     * @param <T>
     * @return
     */
    <T> List<T> findList(QueryHelper helper, Class<T> tClass);
}
