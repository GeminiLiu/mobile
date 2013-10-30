package cn.com.ultrapower.eoms.util;
/**
 * 定义用户的一些操作的数字.
 * @author LY
 * 2006-12-15
 */
public interface Action {
	String INSERT = "1";//新增
	String DELETE = "2";//删除
	String CHECKIN = "3";//交班
	String CHECKOUT = "4";//接班
	String APPLY = "5";//应用
	String DUTYARRANGE = "6"; //排班
	String VALIDATE = "7";//审批
	String IMPORT = "8";//导入
	String EXPORT = "9";//导出
	String ADDENDUM = "10";//追加
	String UPDATE = "11";//修改
}
