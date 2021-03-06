package net.cdsunrise.wm.base.dao;

import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lijun
 * @date 2018-04-02.
 */
@Repository
public class CommonDAOImpl implements CommonDAO {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DataSource dataSource;


    @Override
    public <T> Pager<T> findPager(QueryHelper helper) {
        Query contentQuery;
        Query countQuery;
        //如果使用原生SQL
        if (helper.isUseNativeSql()) {
            contentQuery = entityManager.createNativeQuery(helper.getContentQueryString());
            countQuery = entityManager.createNativeQuery(helper.getCountQueryString());
            contentQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else {
            contentQuery = entityManager.createQuery(helper.getContentQueryString());
            countQuery = entityManager.createQuery(helper.getCountQueryString());
        }
        for (int i = 0; i < helper.getParameters().size(); i++) {
            Object p = helper.getParameters().get(i);
            contentQuery.setParameter(i + 1, p);
            countQuery.setParameter(i + 1, p);
        }
        contentQuery.setFirstResult(helper.getCurrentPage() * helper.getPageSize());
        contentQuery.setMaxResults(helper.getPageSize());
        List list = contentQuery.getResultList();
        int count = 0;
        if (helper.isUseNativeSql()) {
            BigInteger v = (BigInteger) countQuery.getSingleResult();
            v.intValue();
        } else {
            Long v = (Long) countQuery.getSingleResult();
            count = v.intValue();
        }
        Pager pager = new Pager(helper.getCurrentPage(), helper.getPageSize(), count, list);
        return pager;
    }

    @Override
    public <T> Pager<T> findPager(QueryHelper helper, Class<T> tClass) {
        if (!helper.isUseNativeSql()) {
            return findPager(helper);
        } else {
            Query countQuery;
            countQuery = entityManager.createNativeQuery(helper.getCountQueryString());
            for (int i = 0; i < helper.getParameters().size(); i++) {
                Object p = helper.getParameters().get(i);
                countQuery.setParameter(i + 1, p);
            }
            BigInteger v = (BigInteger) countQuery.getSingleResult();
            String sql = helper.getContentQueryString() + " LIMIT ?, ?";
            helper.getParameters().add(helper.getCurrentPage() * helper.getPageSize());
            helper.getParameters().add(helper.getPageSize());
            List<T> resultList = execSql(sql, helper.getParameters(), tClass);
            Pager pager = new Pager(helper.getCurrentPage(), helper.getPageSize(), v.intValue(), resultList);
            return pager;
        }
    }

    @Override
    public <T> List<T> findList(QueryHelper helper) {
        Query contentQuery;
        //如果使用原生SQL
        if (helper.isUseNativeSql()) {
            contentQuery = entityManager.createNativeQuery(helper.getContentQueryString());
            contentQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else {
            contentQuery = entityManager.createQuery(helper.getContentQueryString());
        }
        for (int i = 0; i < helper.getParameters().size(); i++) {
            Object p = helper.getParameters().get(i);
            contentQuery.setParameter(i + 1, p);
        }
        return contentQuery.getResultList();
    }

    @Override
    public <T> List<T> findList(QueryHelper helper, Class<T> tClass) {
        if (!helper.isUseNativeSql()) {
            return findList(helper);
        } else {
            return execSql(helper.getContentQueryString(), helper.getParameters(), tClass);
        }
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param clazz  转换的对象
     * @param <T>
     * @return
     */
    private <T> List<T> execSql(String sql, List<Object> params, Class<T> clazz) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            return convertToEntity(rs, clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将结果集转换成对象
     *
     * @param rs
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> List<T> convertToEntity(ResultSet rs, Class<T> clazz) {
        List<T> resultList = new ArrayList<>();
        try {
            //结果集 中列的名称和类型的信息
            ResultSetMetaData rsm = rs.getMetaData();
            int colNumber = rsm.getColumnCount();
            Field[] fields = clazz.getDeclaredFields();
            while (rs.next()) {
                T entity = clazz.newInstance();
                for (int i = 1; i <= colNumber; i++) {
                    Object value = rs.getObject(i);
                    //匹配实体类中对应的属性
                    for (int j = 0; j < fields.length; j++) {
                        Field f = fields[j];
                        if (f.getName().equals(rsm.getColumnName(i))) {
                            boolean flag = f.isAccessible();
                            if (f.getType() == Integer.class || f.getType() == int.class) {
                                value = rs.getInt(i);
                            }
                            f.setAccessible(true);
                            f.set(entity, value);
                            f.setAccessible(flag);
                            break;
                        }
                    }

                }
                resultList.add(entity);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
