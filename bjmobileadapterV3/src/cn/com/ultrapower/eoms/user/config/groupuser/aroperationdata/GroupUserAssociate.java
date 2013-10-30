package cn.com.ultrapower.eoms.user.config.groupuser.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.BaseCatagorygrandpo;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.config.groupuser.bean.GroupUserInfo;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

/**
 * <p>Description:封装ArrayList对象,封装传给ArEdit的参数<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-20
 */
public class GroupUserAssociate {
	
	static final Logger logger = (Logger) Logger.getLogger(GroupUserAssociate.class);
	
	/**
	 * <p>Description:组成员信息把字段信息封装到一个bean对象内<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-20
	 * @param ID
	 * @param value
	 * @param flag
	 * @return ArInfo
	 */
	public static ArInfo setObject(String ID,String value,String flag){
    	try
    	{
    		ArInfo arGroupUserInfo = new ArInfo();
    		arGroupUserInfo.setFieldID(ID);
    		arGroupUserInfo.setValue(value);
    		arGroupUserInfo.setFlag(flag);
    		return arGroupUserInfo;
    	}
    	catch(Exception e)
    	{
    		logger.error("[460]GroupUserAssociate.setObject() 组成员信息把字段信息封装到bean对象内失败"+e.getMessage());
    		return null;
    	}
	}
	
	 /**
	  * <p>Description:组成员信息把字段信息转换成ArrayList对象<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-20
	 * @param groupUserInfo
	 * @return ArrayList
	 */
	public static ArrayList associateInsert(GroupUserInfo groupUserInfo){
			
			String temp_MGroup_GroupId	= groupUserInfo.getMgroupGroupId();
			String temp_MGroup_UserId	= groupUserInfo.getMgroupUserId();
				
			ArInfo MGroup_GroupId	= setObject("620000027",temp_MGroup_GroupId,"1");
			ArInfo MGroup_UserId	= setObject("620000028",temp_MGroup_UserId,"1");
			
			try
			{
				ArrayList backlist = new ArrayList();
				backlist.add(MGroup_GroupId);
				backlist.add(MGroup_UserId);
				return backlist;
			}
			catch(Exception e)
			{
				logger.error("[461]GroupUserAssociate.associateInsert() 组成员信息把字段信息转换成ArrayList对象失败"+e.getMessage());
				return null;
			}
		}
	
	/**
	  * <p>Description:人员工单处理同组归档配置把字段信息转换成ArrayList对象<p>
	 * @author fangqun
	 * @creattime 2008-5-4
	 * @param groupUserInfo
	 * @return ArrayList
	 */
	public static ArrayList associateInsert_UserCloseBaseGroup(SysPeoplepo peoplepo,SysGrouppo grouppo,BaseCatagorygrandpo basecatagorygrandpo){
			
			String temp_GroupId	           = Long.toString(grouppo.getC630000030());
			String temp_GroupName          = grouppo.getC630000018();
			String temp_UserLoginname	   = peoplepo.getC630000001();
			String temp_UserName	       = peoplepo.getC630000003();
			String temp_BaseCategoryName   = basecatagorygrandpo.getC650000001();
			String temp_BaseCategorySchama = basecatagorygrandpo.getC650000002();
				
			ArInfo GroupId	            = setObject("650000006",temp_GroupId,"1");
			ArInfo GroupName	        = setObject("650000005",temp_GroupName,"1");
			ArInfo UserLoginname	    = setObject("650000004",temp_UserLoginname,"1");
			ArInfo UserName	            = setObject("650000003",temp_UserName,"1");
			ArInfo BaseCategoryName	    = setObject("650000001",temp_BaseCategoryName,"1");
			ArInfo BaseCategorySchama	= setObject("650000002",temp_BaseCategorySchama,"1");
			
			try
			{
				ArrayList backlist = new ArrayList();
				backlist.add(GroupId);
				backlist.add(GroupName);
				backlist.add(UserLoginname);
				backlist.add(UserName);
				backlist.add(BaseCategoryName);
				backlist.add(BaseCategorySchama);
				return backlist;
			}
			catch(Exception e)
			{
				logger.error("[461]GroupUserAssociate.associateInsert() 组成员信息把字段信息转换成ArrayList对象失败"+e.getMessage());
				return null;
			}
		}
}
