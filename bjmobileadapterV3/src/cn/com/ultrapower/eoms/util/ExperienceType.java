package cn.com.ultrapower.eoms.util;
/**
 * 本类定义本系统所使用的经验库知识类型
 * @author zhaoqi
 *
 */
public class ExperienceType {
	
	public  static  Long ACCIDENT = new Long(1);//故障
	public  static Long CONSULTATION = new Long(2);//咨询

	public static Long CRITIC = new Long(3);//申告
	public static Long OPTIMIZE = new Long(4);//网络优化
	public static Long UNIFORM = new Long(5);//通用

	public static String[] experienceName={"","故障","咨询","申告","网络优化","通用"};
}
