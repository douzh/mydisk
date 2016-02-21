package com.iteedu.base.dao;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.iteedu.stock.spider.StockSpider;

public abstract class AbstractBaseDao extends HibernateDaoSupport{
    // 打印日志类
    private static Log log=LogFactory.getLog(StockSpider.class);
    
    @Qualifier("dataSource")
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = new JdbcTemplate(dataSource);
        }
        return jdbcTemplate;
    }
    @Autowired
    public void setSessionFactory0(
            @Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
    public Serializable save(Object bean) {
        return this.getHibernateTemplate().save(bean);
    }

    public void update(Object bean) {
        this.getHibernateTemplate().update(bean);
    }

    public void saveOrUpdate(Object bean) {
        this.getHibernateTemplate().saveOrUpdate(bean);
    }

    public void saveOrUpdateAll(Collection<?> entities) {
        this.getHibernateTemplate().saveOrUpdateAll(entities);
    }

    public void deleteAll(Collection<?> entities) {
        this.getHibernateTemplate().deleteAll(entities);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> entityClass, Serializable id) {
        return (T) this.getHibernateTemplate().get(entityClass, id);
    }

    public List<?> getList(final String hql, Object[] params) {
        return getList(hql, params, 0);
    }

    public List<?> getList(final String hql, final Object[] params,
            final int maxcount) {
        List<?> find = getHibernateTemplate().executeFind(
            new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Query q = session.createQuery(hql);
                    if (maxcount != 0) {
                        q.setMaxResults(maxcount);
                    }
                    if (params != null) {
                        for (int i = 0; i < params.length; i++) {
                            q.setParameter(i, params[i]);
                        }
                    }
                    return q.list();
                }
            });
        if (find == null) {
            find = new ArrayList<Object>();
        }
        return find;
    }

    public Object getFirstResult(final String hql, Object[] params) {
        List<?> list = getList(hql, params, 1);
        if (list != null && list.size() != 0) {
            return list.iterator().next();
        }
        return null;
    }

    public void delete(Object bean) {
        this.getHibernateTemplate().delete(bean);
    }
}
