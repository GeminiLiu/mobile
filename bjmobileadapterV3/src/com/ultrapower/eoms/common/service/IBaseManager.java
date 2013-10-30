package com.ultrapower.eoms.common.service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ultrapower.eoms.common.support.Page;
import com.ultrapower.eoms.common.support.PageBean;
import com.ultrapower.eoms.common.support.PageLimit;


/**
 * @author QL
 * @version 创建时间：Jan 23, 2008 3:34:05 PM 类说明
 */
public interface IBaseManager<T> {


	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
	 * 
	 * @param id
	 *            对象主键
	 * @return 实体对象
	 */
	abstract T get(Serializable id);

	/**
	 * 获取全部对象
	 * 
	 * @return 符合条件的对象列表
	 */
	abstract List<T> getAll();

	/**
	 * 获取全部对象,带排序参数.
	 * 
	 * @param orderBy
	 *            排序条件
	 * @param isAsc
	 *            升降序
	 * @return 符合条件的对象列表
	 */
	abstract List<T> getAll(String orderBy, boolean isAsc);

	/**
	 * 保存对象.
	 */
	abstract void save(Object o);

	/**
	 * 删除对象.
	 */
	abstract void remove(Object o);

	/**
	 * 根据ID移除对象.
	 * 
	 */
	abstract void removeById(Serializable id);

	/**
	 * 刷新缓存
	 */
	abstract void flush();

	/**
	 * 清空缓存
	 */
	abstract void clear();

	/**
	 * 创建Query对象.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values
	 *            可变参数.
	 */
	abstract Query createQuery(String hql, Object... values);

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 * 
	 * @param hql
	 *            查询语句
	 * @param values
	 *            可变参数,见{@link #createQuery(String,Object...)}
	 */
	abstract List find(String hql, Object... values);

	/**
	 * 根据属性名和属性值查询对象
	 * 
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * 
	 * @return 符合条件的对象列表
	 */
	abstract List<T> findBy(String propertyName, Object value);

	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 * 
	 * @return 符合条件的对象列表
	 */
	abstract List<T> findBy(String propertyName, Object value, String orderBy,
			boolean isAsc);

	/**
	 * 根据属性名和属性值查询单个对象.
	 * 
	 * @return 符合条件的唯一对象 or null if not found
	 */
	abstract T findUniqueBy(String propertyName, Object value);

	/**
	 * 判断对象某些属性的值在数据库中唯一.
	 * 
	 * 
	 * @param 实体对象
	 * @param uniquePropertyNames
	 *            在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	abstract boolean isUnique(Object entity, String uniquePropertyNames);

	/**
	 * 判断对象是否已经持久化
	 * 
	 * @param id
	 */
	abstract boolean exists(Serializable id);

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	abstract String getIdName(Class clazz);

	/**
	 * 取得对象的主键值,辅助函数.
	 */
	abstract Serializable getId(Object entity) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException;

	
	/*
	 * 直接执行hql upadate语句的方法
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            可变参数
	 * 
	 * @return 返回操作记录数
	 */
	abstract int executeUpdate(String hql, Object... values);
	
	/**
	 * 获取hibernate本身的持久层访问模板
	 * 
	 * @return
	 */
	abstract HibernateTemplate getTemplate();
	
	
	/**
	 * 获取对象的集合从字符串
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	abstract<T> List<T> getListByIds(Class<T> entityClass,String ids);
	
	/**
	 * 分页查询使用的方法
	 * 
	 * @param pageBean
	 *            分页信息
	 * @param hql
	 *            查询语句
	 * @param values
	 *            查询参数
	 * @return 查询结果
	 */
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