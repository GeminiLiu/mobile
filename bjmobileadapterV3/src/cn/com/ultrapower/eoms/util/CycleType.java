/**
 * 
 */
package cn.com.ultrapower.eoms.util;

/**
 * 本类定义本系统所使用的周期
 * @author lijupeng
 *
 */
public class CycleType {
	
	public static Long YEAR = new Long(1);//年度周期
	public static Long HALF_YEAR = new Long(2);//半年度周期
	public static Long QUARTER = new Long(3);//季度周期
	public static Long MONTH = new Long(4);//月度周期
	public static Long HALF_MONTH = new Long(5);//半月度周期
	public static Long WEEK = new Long(6);//周周期
	public static Long DAY = new Long(7);//日周期
	public static String YEAR_STRING = YEAR.toString();
	public static String HALF_YEAR_STRING = HALF_YEAR.toString();//半年度周期

	public static String QUARTER_STRING = QUARTER.toString();//季度周期
	public static String MONTH_STRING = MONTH.toString();//月度周期
	public static String HALF_MONTH_STRING = HALF_MONTH.toString();//半月度周期

	public static String WEEK_STRING = WEEK.toString();//周周期

	public static String DAY_STRING = DAY.toString();//日周期
	
	public static String[] cycleName={"","年","半年","季度","月度","半月","周","日"};
	
	public static Long cycleID ( String _cycleName ) {
		if ( _cycleName.startsWith(CycleType.cycleName[1]) ) return new Long(1);
		if ( _cycleName.startsWith(CycleType.cycleName[2]) ) return new Long(2);
		if ( _cycleName.startsWith(CycleType.cycleName[3]) ) return new Long(3);
		if ( _cycleName.startsWith(CycleType.cycleName[4]) ) return new Long(4);
		if ( _cycleName.startsWith(CycleType.cycleName[5]) ) return new Long(5);
		if ( _cycleName.startsWith(CycleType.cycleName[6]) ) return new Long(6);
		if ( _cycleName.startsWith(CycleType.cycleName[7]) ) return new Long(7);
		return new Long(0);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
