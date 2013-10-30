package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.SysSkillConferBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class SysSkillConferAssociate
{

	
	static final Logger logger = (Logger) Logger.getLogger(SysSkillConferAssociate.class);
	
	/**
	 * 根据参数，将参数组合成一个ArInfo Bean类对象。
	 * 日期 2007-1-13
	 * @author wangyanguang/王彦广 
	 * @param ID
	 * @param value
	 * @param flag
	 * @return ArInfo
	 */
	public static ArInfo setObject(String ID, String value, String flag)
	{
		try 
		{
			ArInfo arpeopleInfo = new ArInfo();
			arpeopleInfo.setFieldID(ID);
			arpeopleInfo.setValue(value);
			arpeopleInfo.setFlag(flag);
			return arpeopleInfo;
		}
		catch (Exception e) 
		{
			logger.info("767 SysSkillConferAssociate 类中 setObject方法组合成一个Bean信息时出现异常!");
			return null;
		}
	}
	
    /**
     *  根据参数 SysSkillConferBean 信息将Bean信息组合成AR能够识别格式以便做增、删、改、查操作。
     *  2007-1-13
     * @author wangyanguang/����� 
     * @param roleInfo
     * @return ArrayList
     */
    public static ArrayList associateCondition(SysSkillConferBean skillconferbeaninfo) 
    {
    	String Skillconfer_Cause 		= "";
    	String Skillconfer_CancelCause 	= "";
    	String Skillconfer_StartTime 	= "";
    	String Skillconfer_EndTime 		= "";
    	String Skillconfer_SkillID 		= "";
    	String Skillconfer_UserID 		= "";
    	String Skillconfer_Status 		= "";
    	String Skillconfer_DealUserID 	= "";
    	String Skillconfer_memo 		= "";
    	try
    	{
    		Skillconfer_Cause			= skillconferbeaninfo.getSkillconfer_Cause();
    		Skillconfer_CancelCause   	= skillconferbeaninfo.getSkillconfer_CancelCause();
    		Skillconfer_StartTime  		= String.valueOf(skillconferbeaninfo.getSkillconfer_StartTime());
    		Skillconfer_EndTime   		= String.valueOf(skillconferbeaninfo.getSkillconfer_EndTime());
    		Skillconfer_SkillID   		= skillconferbeaninfo.getSkillconfer_SkillID();
    		Skillconfer_UserID         	= skillconferbeaninfo.getSkillconfer_UserID();
    		Skillconfer_Status     		= skillconferbeaninfo.getSkillconfer_Status();
    		Skillconfer_DealUserID    	= skillconferbeaninfo.getSkillconfer_DealUserID();
    		Skillconfer_memo   			= skillconferbeaninfo.getSkillconfer_memo();
    	}
    	catch(Exception e)
    	{
    		logger.info("768 SysSkillConferAssociate 类中 associateCondition 方法从参数中取值时出现异常！");
    	}
    	
    	ArInfo causeinfo 				=  null ;
    	ArInfo cancelcauseinfo			=  null ;
    	ArInfo starttimeinfo  			=  null ;
    	ArInfo endtimeinfo				=  null ;
    	ArInfo skillidinfo				=  null ;
    	ArInfo useridinfo 				=  null ;
    	ArInfo statusinfo 				=  null ;
    	ArInfo dealuseridinfo 			=  null ;
    	ArInfo memoinfo 				=  null ;
    	try
    	{
    		causeinfo 					=  setObject("610000020", Skillconfer_Cause, "1");
    		cancelcauseinfo				=  setObject("610000021", Skillconfer_CancelCause, "1");
    		starttimeinfo  				=  setObject("610000022", Skillconfer_StartTime, "2");
    		endtimeinfo					=  setObject("610000023", Skillconfer_EndTime, "2");
    		skillidinfo					=  setObject("610000024", Skillconfer_SkillID, "1");
    		useridinfo 					=  setObject("610000025", Skillconfer_UserID, "1");
    		statusinfo 					=  setObject("610000026", Skillconfer_Status, "1");
    		dealuseridinfo 				=  setObject("610000027", Skillconfer_DealUserID, "1");
    		memoinfo 					=  setObject("610000028", Skillconfer_memo, "1");
    	}
    	catch(Exception e)
    	{
    		logger.info("769 SysSkillConferAssociate 类中 associateCondition 方法调用setObject方法时出现异常！");
    	}

        ArrayList returnList = new ArrayList();
        
        returnList.add(causeinfo);
        returnList.add(cancelcauseinfo);
        returnList.add(starttimeinfo);
        returnList.add(endtimeinfo);
        returnList.add(skillidinfo);
        returnList.add(useridinfo);
        returnList.add(statusinfo);
        returnList.add(dealuseridinfo);
        returnList.add(memoinfo);
        
        return returnList;
    }
}
