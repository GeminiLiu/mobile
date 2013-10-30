package com.ultrapower.eoms.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

 
/**
 * Generics的util类.
 * 
 * @author andy
 */
public class GenericsUtils {

	private static final Log log = LogFactory.getLog(GenericsUtils.class);

	private GenericsUtils() {
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public templateManager extends
	 * GenricManager<Book>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenricManager<Book>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	public static Class getSuperClassGenricType(Class clazz, int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			log.warn(clazz.getSimpleName()
					+ "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log
					.warn(clazz.getSimpleName()
							+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	public static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 获取hql中的order by 子句
	 * 
	 * @param hql
	 * @return
	 */
	public static String ordersfromHql(String hql) {

		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("order by");

		if (beginPos > 0)
			return hql.substring(beginPos);
		return "";

	}

	/**
	 * 根据正则截取HQL
	 * 
	 * @param hql
	 * @param regular
	 * @return
	 */
	public static String removeInHql(String hql, String regular) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile(regular, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	public static String removeOrders(String hql) {
		return removeInHql(hql, "order\\s*by[\\w|\\W|\\s|\\S]*");
	}

	/**
	 * 去除hql的where子句，用于pagedQuery
	 * 
	 * @param hql
	 * @return
	 */
	public static String removeWheres(String hql) {
		return removeInHql(hql, "where[\\w|\\W|\\s|\\S]*");
	}

	/**
	 * 通过实体域对象
	 * 
	 * @param hql
	 * @param entity
	 * @param assembleColumn
	 * @return
	 */
	public static String queryAccession(String hql, Object entity,
			Map<String, String> aliasMap, Map<String, String> operMap,
			Map<String, String> actualMap) {

		StringBuffer buffer = new StringBuffer();
		Field[] fields = BeanUtils.getDeclaredFields(entity.getClass());
		for (Field field : fields) {
			try {
				Object fieldValue = BeanUtils.forceGetProperty(entity, field
						.getName());
				if (aliasMap.get(field.getName()) != null && fieldValue != null
						&& !fieldValue.equals("")) {// 如果变量值非空
					String oper = fieldValue instanceof String ? " like "
							: " = ";
					String fieldColumn = field.getName();

					fieldValue = fieldValue instanceof String ? "'%"
							+ fieldValue + "%'" : fieldValue;
					oper = operMap.get(field.getName()) == null ? oper
							: operMap.get(field.getName());
					fieldColumn = actualMap.get(field.getName()) == null ? fieldColumn
							: actualMap.get(field.getName());

					buffer.append(" and ");
					buffer.append(aliasMap.get(field.getName()) + "."
							+ fieldColumn + oper + fieldValue);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return removeOrders(hql)
				+ (hql.indexOf("where") > 0 ? buffer.toString() : buffer
						.toString().replaceFirst("and", "where"))
				+ ordersfromHql(hql);

	}

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			log.error("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (byte anEncodedPassword : encodedPassword) {
			if ((anEncodedPassword & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(anEncodedPassword & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * Encode a string using Base64 encoding. Used when storing passwords as
	 * cookies.
	 * 
	 * This is weak encoding in that anyone can use the decodeString routine to
	 * reverse the encoding.
	 * 
	 * @param str
	 *            the string to encode
	 * @return the encoded string
	 */
	public static String encodeString(String str) {
		Base64 encoder = new Base64();
		return String.valueOf(encoder.encode(str.getBytes())).trim();
	}

	/**
	 * Decode a string using Base64 encoding.
	 * 
	 * @param str
	 *            the string to decode
	 * @return the decoded string
	 */
	public static String decodeString(String str) {
		Base64 dec = new Base64();
		try {
			return String.valueOf(dec.decode(str));
		} catch (DecoderException de) {
			throw new RuntimeException(de.getMessage(), de.getCause());
		}
	}
	
	/**
	 * @author liangyang
	 * 判断字符串是否为NULL或者为空字符串
	 * @param str
	 * @return 如果是null或者空则返回false，反之，返回true
	 */
	public static boolean isNull(String str){
		if(null != str && !"".equals(str)){
			return true;
		}
		return false;
	}
	
	public final static String getStatusLabel(Long obj) {

		if (Constants.ONE_FLAG.equals(obj)) {
			return "关闭";
		}
		if (Constants.ZERO_FLAG.equals(obj)) {
			return "启用";
		}
		return "启用";
	}
	
	public final static String getTemplateStatus(Long obj) {

		if (Constants.OFF.equals(obj)) {
			return "停用";
		}
		if (Constants.NEW.equals(obj)) {
			return "启用";
		}
		return "启用";
	}
	
	public final static String getDictLabel(Long obj) {
		if (Constants.DICT_TEMPLTE.equals(obj)) {
			return "模板";
		}
		if (Constants.DICT_WORKSERIAL.equals(obj)) {
			return "班次";
		}
		if (Constants.DICT_SYSTEM.equals(obj)) {
			return "系统";
		}
		if (Constants.DICT_OTHER.equals(obj)) {
			return "其他";
		}		
		return "";
	}
	
	public final static String[] getDictALL() {
		String[] dictArray= new String[4];
		dictArray[0] = Constants.DICT_TEMPLTE.toString();
		dictArray[1] = Constants.DICT_SYSTEM.toString();
		dictArray[2] = Constants.DICT_WORKSERIAL.toString();
		dictArray[3] = Constants.DICT_OTHER.toString();
		return dictArray;
	}
}
