/**
 * 
 */
package cn.com.ultrapower.eoms.util;

/**
 * 定义经验的评定等级
 * @author wanghongbing
 *
 */
public class ValueLevel {
	public static int LEVEL1 = 10;//一般
	public static int LEVEL2 = 20;//好
	public static int LEVEL3 = 30;//非常好
	
	public static String getValueLevelName ( int _valueLevel ) {
		switch ( _valueLevel ) {
		case 10: return "一般";
		case 20: return "好";
		case 30: return "非常好";
		}
		return "未知状态";
	}
}
