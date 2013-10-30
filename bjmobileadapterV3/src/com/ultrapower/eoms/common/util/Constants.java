package com.ultrapower.eoms.common.util;

public class Constants {

	/***************************************************************************
	 * 字典类型
	 **************************************************************************/

	public final static String DICTTYPE_STATUS = "0";
	public final static String DICTTYPE_DEFAULT = "1";

	/***************************************************************************
	 * 缓存
	 **************************************************************************/

	public final static String CACHE_DICT = "Dictionary";

	/***************************************************************************
	 * 字典类型常量
	 **************************************************************************/
	public final static Long DICT_TEMPLTE = 1L;

	public final static Long DICT_WORKSERIAL = 2L;

	public final static Long DICT_SYSTEM = 3L;

	public final static Long DICT_OTHER = 4L;

	/***************************************************************************
	 * 作业计划执行人的类型
	 **************************************************************************/
	public final static Long EXEC_PERSON = 1L;// 按人执行
	public final static Long EXEC_GROUP = 2L;// 按组执行

	/***************************************************************************
	 * 知识类别:是否有子节点
	 **************************************************************************/
	public static final String HAS_CHILD = "1";
	public static final String NO_CHILD = "2";

	/***************************************************************************
	 * 知识模板:此模板是否被应用
	 **************************************************************************/
	public static final String HAS_USED = "1";
	public static final String NO_USE = "2";

	public final static Long ZERO_FLAG = 0L;
	public final static Long ONE_FLAG = 1L;

	// 停用
	public final static Long OFF = 0L;

	// 新建
	public final static Long NEW = 10L;
	
//	资源线程自动更新间隔
	public final static int LogThreadWaitMinute = 1;
}
