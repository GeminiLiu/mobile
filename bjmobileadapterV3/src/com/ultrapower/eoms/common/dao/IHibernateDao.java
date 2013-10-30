/**
 * Copyright (c) 2007 神州泰岳服务管理事业部应用组
 * All rights reserved.
 *
 * 文件名称: IHiberanteDao.java
 * 文件标示: IHiberanteDao.java
 * 摘   要:
 * 
 * 当前版本：
 * 作   者:Administrator
 * 完成日期:
 */
package com.ultrapower.eoms.common.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ultrapower.eoms.common.support.Page;
import com.ultrapower.eoms.common.support.PageBean;
import com.ultrapower.eoms.common.support.PageLimit;

public interface IHibernateDao {

	abstract <T> T get(Class<T> entityClass, Serializable id);

	abstract <T> List<T> getAll(Class<T> entityClass);

	abstract <T> List<T> getAll(Class<T> entityClass, String orderBy,
			boolean isAsc);

	abstract void save(Object o);

	abstract void remove(Object o);
	
	//新增加修改方法 20120809
	abstract void merge(Object o);

	abstract <T> void removeById(Class<T> entityClass, Serializable id);

	abstract void flush();

	abstract void clear();

	abstract Query createQuery(String hql, Object... values);

	abstract <T> Criteria createCriteria(Class<T> entityClass,
			Criterion... criterions);

	abstract <T> Criteria createCriteria(Class<T> entityClass, String orderBy,
			boolean isAsc, Criterion... criterions);

	abstract List find(String hql, Object... values);

	abstract <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value);

	abstract <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value, String orderBy, boolean isAsc);

	abstract <T> T findUniqueBy(Class<T> entityClass, String propertyName,
			Object value);

	abstract <T> boolean isUnique(Class<T> entityClass, Object entity,
			String uniquePropertyNames);

	abstract <T> boolean exists(Class<T> entityClass, Serializable id);

	abstract String getIdName(Class clazz);

	abstract <T> Serializable getId(Class<T> entityClass, Object entity)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException;

	abstract List pagedQuery(String hsql, PageLimit pageLimit, Object... values);

	abstract List pagedQuery(Query query, Query totalCountQuery,
			PageLimit pageLimit);

	abstract Page pagedQuery(String hql, int pageNo, int pageSize,
			Object... values);

	abstract Page pagedQuery(Query query, Query totalCountQuery, int pageNo,
			int pageSize);

	abstract int executeUpdate(String hql, Object... values);
	
	
	abstract HibernateTemplate getTemplate();
	
	public List pagedQuery(PageBean pageBean, String hql, Object... values);


	/**
	 * @param sql
	 *            原生SQL
	 * @param values
	 *            SQL中的参数
	 * @return 查询结果集
	 */
	List nativeQuery(String sql, Object... values);
	
	Session getHibernateSession();
}
