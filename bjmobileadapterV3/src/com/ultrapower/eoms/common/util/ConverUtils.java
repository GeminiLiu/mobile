package com.ultrapower.eoms.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConverUtils {
	
	private static final Log log = LogFactory.getLog(ConverUtils.class);
	
	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenricManager<Book>
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
	 * 对象是否在字符串之间(以逗号分割)
	 * @param obj
	 * @param strs
	 * @return
	 */
	
	public static boolean objInStr(String obj,String strs){
		String[] strArray = strs.split(",");
		for(String str:strArray){
			if(str.equals(obj))
				return true;	
		}
		
		return false;
		
	}
	
	/**
	 * 对象是否在字符串之间(以逗号分割)
	 * @param obj
	 * @param strs
	 * @return
	 */
	
	public static boolean objInStr(Long obj,String strs){
		String[] strArray = strs.split(",");
		for(String str:strArray){
			if(str.equals(String.valueOf(obj)))
				return true;	
		}
		
		return false;
		
	}

	
	/**
	 * 集合转换成数组
	 * 
	 * @param list
	 * @return object[]
	 */

	@SuppressWarnings("unchecked")
	public static Object[] arrayFromList(List list) {

		Object[] objects = new Object[list.size()];

		for (int i = 0; i < list.size(); i++) {
			objects[i] = list.get(i);
		}
		return objects;
	}
	/**
	 * 将通用对象s转换为long类型，如果字符穿为空或null，返回0；
	 * @param s
	 * @return
	 */
	public static long Obj2long(Object s) {
	    long i = 0;

	    String str = "";
	    try {
	      str = s.toString();
	      i = Long.parseLong(str);
	    }
	    catch (Exception e) {
	      i = 0;
	    }
	    return i;
	}
	
	public static Long Obj2Long(Object s) {
	    Long i;
	    String str = "";  
	    try {
	      str = s.toString();
	      i = new Long(str);
	    }
	    catch (Exception e) {
	      i = new Long("0");
	    }
	    return i;
	}

}
