package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.userinterface.bean.UserAndSkillInfo;
public class UserAndSkillInfoAssociate {

	/**
	 * 日期 2007-3-1
	 * @author wangyanguang
	 * @param rs
	 * @return List
	 */
	public static List associateSkillAndUser(ResultSet rs)
	{
		List returnList = new ArrayList();
		String user_ID 					= "";
		String user_IntID 				= "";
		String user_LoginName 			= "";
		String user_FullName 			= "";
		try
		{
			if(rs!=null)
			{
				while(rs.next())
				{
					UserAndSkillInfo userskillinfo = new UserAndSkillInfo();
					user_ID 					= Function.nullString(rs.getString("userid"));
					user_IntID 					= Function.nullString(rs.getString("userintid")); 
					user_LoginName 				= Function.nullString(rs.getString("userloginname")); 
					user_FullName 				= Function.nullString(rs.getString("userfullname"));
					userskillinfo.setUser_FullName(user_FullName);
					userskillinfo.setUser_ID(user_ID);
					userskillinfo.setUser_IntID(user_IntID);
					userskillinfo.setUser_LoginName(user_LoginName);
					if(userskillinfo!=null)
					{
						returnList.add(userskillinfo);
					}
				}
			}
		}catch(Exception e)
		{
			System.out.println("EXception!");
			System.out.println(e.getMessage());
		}
		if(returnList!=null)
		{
			return returnList;
		}
		else
		{
			return null;
		}
	}
}
