package cn.com.ultrapower.eoms.user.userinterface;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.userinterface.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.userinterface.cm.PersonalOperation;

/**
 * <p>Description:用户信息修改接口<p>
 * @author wangwenzhuo
 * @CreatTime 2006-12-4
 */
public class PersonalModifyInterface {

	static final Logger logger	= (Logger) Logger.getLogger(PersonalModifyInterface.class);
	
	public boolean ModifyPeopleInfo(PeopleInfo peopleInfo)
	{
		try
		{
			PersonalOperation personalOperation = new PersonalOperation();
			return personalOperation.modifyPeople(peopleInfo,peopleInfo.getUserId());
		}
		catch(Exception e)
		{
			logger.error("[499]PersonalModifyInterface.ModifyPeopleInfo() 用户信息修改失败"+e.getMessage());
			return false;
		}
	}
	
}
