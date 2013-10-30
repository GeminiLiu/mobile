package com.ultrapower.eoms.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;



import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.util.ReflectionUtils;

import com.ultrapower.eoms.common.dao.IHibernateDao;
import com.ultrapower.eoms.common.support.Page;
import com.ultrapower.eoms.common.support.PageBean;
import com.ultrapower.eoms.common.support.PageLimit;
import com.ultrapower.eoms.common.util.GenericsUtils;

/**
 * Hibernate Dao的泛型基类. 继承于Spring的<code>HibernateDaoSupport</code>,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 * 
 * @author andy
 */
@SuppressWarnings("unchecked")
public class HibernateDaoImpl extends HibernateDaoSupport implements
		IHibernateDao {

	
	public HibernateDaoImpl() {
		System.out.println("HibernateDaoImpl Initialze....");
	}

	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#get(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#getAll(java.lang.Class)
	 */
	public <T> List<T> getAll(Class<T> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#getAll(java.lang.Class,
	 *      java.lang.String, boolean)
	 */
	public <T> List<T> getAll(Class<T> entityClass, String orderBy,
			boolean isAsc) {

		Assert.hasText(orderBy);
		if (isAsc)
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(
							Order.asc(orderBy)));
		else
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(
							Order.desc(orderBy)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#save(java.lang.Object)
	 */
	public void save(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#remove(java.lang.Object)
	 */
	public void remove(Object o) {
		getHibernateTemplate().delete(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#removeById(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public <T> void removeById(Class<T> entityClass, Serializable id) {
		remove(get(entityClass, id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#flush()
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#clear()
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#createQuery(java.lang.String,
	 *      java.lang.Object)
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		if(null != values){
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#createCriteria(java.lang.Class,
	 *      org.hibernate.criterion.Criterion)
	 */
	public <T> Criteria createCriteria(Class<T> entityClass,
			Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#createCriteria(java.lang.Class,
	 *      java.lang.String, boolean, org.hibernate.criterion.Criterion)
	 */
	public <T> Criteria createCriteria(Class<T> entityClass, String orderBy,
			boolean isAsc, Criterion... criterions) {
		Assert.hasText(orderBy);

		Criteria criteria = createCriteria(entityClass, criterions);

		if (isAsc)
			criteria.addOrder(Order.asc(orderBy));
		else
			criteria.addOrder(Order.desc(orderBy));

		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#find(java.lang.String,
	 *      java.lang.Object)
	 */
	public List find(String hql, Object... values) {
		Assert.hasText(hql);
		return getHibernateTemplate().find(hql, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#findBy(java.lang.Class,
	 *      java.lang.String, java.lang.Object)
	 */
	public <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, Restrictions.eq(propertyName, value))
				.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernateDao#findBy(java.lang.Class,
	 *      java.lang.String, java.lang.Object, java.lang.String, boolean)
	 */
	public <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return createCriteria(entityClass, orderBy, isAsc,
				Restrictions.eq(propertyName, value)).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernate#findUniqueBy(java.lang.Class,
	 *      java.lang.String, java.lang.Object)
	 */
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernate#isUnique(java.lang.Class,
	 *      java.lang.Object, java.lang.String)
	 */
	public <T> boolean isUnique(Class<T> entityClass, Object entity,
			String uniquePropertyNames) {

		Assert.hasText(uniquePropertyNames);
		Criteria criteria = createCriteria(entityClass).setProjection(
				Projections.rowCount());
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// 循环加入唯一列
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(
						entity, name)));
			}

			// 以下代码为了如果是update的情况,排除entity自身.

			String idName = getIdName(entityClass);

			// 取得entity的主键值
			Serializable id = getId(entityClass, entity);

			// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
			if (id != null)
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return (Integer) criteria.uniqueResult() == 0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernate#exists(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public <T> boolean exists(Class<T> entityClass, Serializable id) {
		return getHibernateTemplate().get(entityClass, id) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernate#getIdName(java.lang.Class)
	 */
	public String getIdName(Class clazz) {
		Assert.notNull(clazz);
		ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
		Assert.notNull(meta, "Class " + clazz
				+ " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, clazz.getSimpleName()
				+ " has no identifier property define.");
		return idName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernate#getId(java.lang.Class,
	 *      java.lang.Object)
	 */
	public <T> Serializable getId(Class<T> entityClass, Object entity)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Assert.notNull(entity);
		return (Serializable) PropertyUtils.getProperty(entityClass,
				getIdName(entityClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernate#pagedQuery(java.lang.String,
	 *      com.ultrapower.bpm.common.base.support.PageLimit, java.lang.Object)
	 */
	public List pagedQuery(String hsql, PageLimit pageLimit, Object... values) {

		hsql = pageLimit.getEntity() != null ? GenericsUtils.queryAccession(
				hsql, pageLimit.getEntity(), pageLimit.getAliasMap(), pageLimit
						.getOperMap(), pageLimit.getActualMap()) : hsql;
		Page page = pagedQuery(hsql, pageLimit.getPageSize(), pageLimit
				.getCURRENT_ROWS_SIZE(), values);

		pageLimit.getLimit().setRowAttributes(
				new Long(page.getTotalCount()).intValue(),
				pageLimit.getCURRENT_ROWS_SIZE());

		return (List) page.getResult();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernate#pagedQuery(org.hibernate.Query,
	 *      org.hibernate.Query,
	 *      com.ultrapower.bpm.common.base.support.PageLimit)
	 */
	public List pagedQuery(Query query, Query totalCountQuery,
			PageLimit pageLimit) {

		Page page = pagedQuery(query, totalCountQuery, pageLimit.getPageSize(),
				pageLimit.getCURRENT_ROWS_SIZE());

		pageLimit.getLimit().setRowAttributes(
				new Long(page.getTotalCount()).intValue(),
				pageLimit.getCURRENT_ROWS_SIZE());

		return (List) page.getResult();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernate#pagedQuery(java.lang.String,
	 *      int, int, java.lang.Object)
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize,
			Object... values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		// Count查询
		String countQueryString = " select count (*) "
				+ GenericsUtils.removeSelect(hql);
		List countlist = getHibernateTemplate().find(countQueryString, values);
		long totalCount = 0L;
		if (countlist.size() > 0)
			totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();

		return new Page(startIndex, totalCount, pageSize, list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.eoms.common.dao.IHibernate#pagedQuery(org.hibernate.Query,
	 *      org.hibernate.Query, int, int)
	 */
	public Page pagedQuery(Query query, Query totalCountQuery, int pageNo,
			int pageSize) {

		Assert.hasText(query.getQueryString());
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		// Count查询
		List countlist = totalCountQuery.list();
		long totalCount = 0L;
		if (totalCountQuery.getQueryString().indexOf("group by") > 0)
			totalCount = countlist.size();
		else if (countlist.size() > 0)
			totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();

		return new Page(startIndex, totalCount, pageSize, list);
	}
	
	
	/**
	 * 分页查询使用的方法,叶昌伦增加
	 * 
	 * @param pageBean
	 *            分页信息
	 * @param hql
	 *            查询HQL语句
	 * @param values
	 *            查询的参数
	 * @return 当前页的数据
	 */
	public List pagedQuery(PageBean pageBean, String hql, Object... values) {
		Assert.notNull(pageBean);
		// Count查询
		String countQueryString = " select count (*) "
				+ GenericsUtils.removeSelect(hql);
		List countlist = getHibernateTemplate().find(countQueryString, values);
		long rowCount = 0;
		if (countQueryString.indexOf("group by") > 0) {
			rowCount = countlist.size();
		} else if (countlist.size() > 0) {
			rowCount = (Long) countlist.get(0);
		}
		pageBean.setRowCount(rowCount);
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(pageBean.getFirstResult())
				.setMaxResults(pageBean.getPageSize()).list();
		return list;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.ultrapower.eoms.common.dao.IHibernate#executeUpdate(java.lang.String, java.lang.Object[])
	 */
	public int executeUpdate(String hql, Object... values)
	{
		int count		= 0;
		Query query		= createQuery(hql,values);
		count			= query.executeUpdate();
		return count;
	}

    
    public List nativeQuery(final String sql, final Object... values) {
        Query nativeQuery = getSession().createSQLQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                nativeQuery.setParameter(i, values[i]);
            }
        }
        return nativeQuery.list();
    }
    
    public Session getHibernateSession(){
    	return this.getSession();
    }

	public void merge(Object o) {
		// TODO Auto-generated method stub
		getHibernateTemplate().merge(o);
		
	}
}
