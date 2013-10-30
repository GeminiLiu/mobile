package com.ultrapower.eoms.common.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.dao.IHibernateDao;
import com.ultrapower.eoms.common.service.IBaseManager;
import com.ultrapower.eoms.common.support.Page;
import com.ultrapower.eoms.common.support.PageBean;
import com.ultrapower.eoms.common.support.PageLimit;
import com.ultrapower.eoms.common.util.GenericsUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BaseManagerImpl<T> implements IBaseManager<T> {

	private Class<T> entityClass;// DAO所管理的Entity类型.
	protected IHibernateDao hibernateDao;
	protected transient final Log log = LogFactory.getLog(getClass());

	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	@SuppressWarnings("unchecked")
	public BaseManagerImpl() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	private Class<T> getEntityClass() {
		return entityClass;
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}

	public T get(Serializable id) {
		return hibernateDao.get(getEntityClass(), id);
	}

	public List<T> getAll() {
		return hibernateDao.getAll(getEntityClass());
	}

	public List<T> getAll(String orderBy, boolean isAsc) {
		return hibernateDao.getAll(getEntityClass(), orderBy, isAsc);
	}

	public void save(Object o) {
		hibernateDao.save(o);
	}

	public void remove(Object o) {
		hibernateDao.remove(o);
	}
	
	public void merge(Object o){
		hibernateDao.merge(o);
	}

	public void removeById(Serializable id) {
		hibernateDao.removeById(getEntityClass(), id);
	}

	public void flush() {
		hibernateDao.flush();
	}

	public void clear() {
		hibernateDao.clear();
	}

	public Query createQuery(String hql, Object... values) {
		return hibernateDao.createQuery(hql, values);
	}

	public List find(String hql, Object... values) {
		return hibernateDao.find(hql, values);
	}

	public List<T> findBy(String propertyName, Object value) {
		return hibernateDao.findBy(getEntityClass(), propertyName, value);
	}

	public List<T> findBy(String propertyName, Object value, String orderBy,
			boolean isAsc) {
		return hibernateDao.findBy(getEntityClass(), propertyName, value,
				orderBy, isAsc);
	}

	public T findUniqueBy(String propertyName, Object value) {
		return hibernateDao
				.findUniqueBy(getEntityClass(), propertyName, value);
	}

	public boolean isUnique(Object entity, String uniquePropertyNames) {
		return hibernateDao.isUnique(getEntityClass(), entity,
				uniquePropertyNames);
	}

	public boolean exists(Serializable id) {
		return hibernateDao.exists(getEntityClass(), id);
	}

	public String getIdName(Class clazz) {
		return hibernateDao.getIdName(clazz);
	}

	public Serializable getId(Object entity) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return hibernateDao.getId(getEntityClass(), entity);
	}

	public List pagedQuery(String hql, PageLimit pageLimit, Object... values) {
		return hibernateDao.pagedQuery(hql, pageLimit, values);
	}

	public List pagedQuery(Query query, Query totalCountQuery,
			PageLimit pageLimit) {
		return hibernateDao.pagedQuery(query, totalCountQuery, pageLimit);
	}

	public Page pagedQuery(String hql, int pageNo, int pageSize,
			Object... values) {
		return hibernateDao.pagedQuery(hql, pageNo, pageSize, values);
	}

	public Page pagedQuery(Query query, Query totalCountQuery, int pageNo,
			int pageSize) {
		return hibernateDao.pagedQuery(query, totalCountQuery, pageNo,
				pageSize);
	}

	public int executeUpdate(String hql, Object... values) {
		return hibernateDao.executeUpdate(hql, values);
	}

	public HibernateTemplate getTemplate() {
		return hibernateDao.getTemplate();
	}

	public <T> List<T> getListByIds(Class<T> entityClass, String ids) {
		ids = ids.replace("[", "").replace("]", "");
		List<T> list = new ArrayList<T>();
		for (String id : ids.split(",")) {
			list.add(hibernateDao.get(entityClass, id));
		}
		return list;
	}
	public <T> List<T> getListByIdsInt(Class<T> entityClass, String ids) {
		ids = ids.replace("[", "").replace("]", "");
		List<T> list = new ArrayList<T>();
		for (String id : ids.split(",")) {
			list.add(hibernateDao.get(entityClass,new Long(id)));
		}
		return list;
	}
	public List pagedQuery(PageBean pageBean, String hql, Object... values) {
		return hibernateDao.pagedQuery(pageBean, hql, values);
	}
	
	 public List nativeQuery(String sql, Object... values) {
		return hibernateDao.nativeQuery(sql, values);
	}



	public Session getHibernateSession() {
		return hibernateDao.getHibernateSession();
	}
	
}
