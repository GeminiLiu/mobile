package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.PeopleFieldFlag;
import cn.com.ultrapower.eoms.user.userinterface.bean.PeopleInfo;

/**
 * <p>Description:用户模块接口<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-24
 */
public class UserInfoInterface {
	
	static final Logger logger = (Logger) Logger.getLogger(UserInfoInterface.class);
	private String usertablename			= "";

	public UserInfoInterface()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			usertablename		= getTableProperty.GetFormName("RemedyTpeople");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	
	String TBLName				= "";

	/**
	 * <p>Description:根据传过来的参数selectQuality确定sql语句select
	 * 和传过来的参数whereQuality确定sql语句where条件返回一个List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-24
	 * @param selectQuality		0代表要查询的字段,1代表不查
	 * @param whereQuality
	 * @return List
	 */
	public List getUserInfo(PeopleFieldFlag selectQuality,PeopleInfo whereQuality)
	{	
		//根据传入的第一个参数确定sql语句的select条件
		StringBuffer selectString	= new StringBuffer();
		IDataBase dataBase = null;
		Statement stm = null;
		ResultSet rs = null;
		
		String select_User_Id				= Function.nullString(selectQuality.getUserId());
		String select_User_LoginName		= Function.nullString(selectQuality.getUserLoginname());
		String select_User_PassWord			= Function.nullString(selectQuality.getUserPassword());
		String select_User_FullName			= Function.nullString(selectQuality.getUserFullname());
		String select_User_CreateUser		= Function.nullString(selectQuality.getUserCreateuser());
		String select_User_Position			= Function.nullString(selectQuality.getUserPosition());
		String select_User_IsManager		= Function.nullString(selectQuality.getUserIsmanager());
		String select_User_Type				= Function.nullString(selectQuality.getUserType());
		String select_User_Mobie			= Function.nullString(selectQuality.getUserMobie());
		String select_User_Phone			= Function.nullString(selectQuality.getUserPhone());
		String select_User_Fax				= Function.nullString(selectQuality.getUserFax());
		String select_User_Mail				= Function.nullString(selectQuality.getUserMail());
		String select_User_Status			= Function.nullString(selectQuality.getUserStatus());
		String select_User_CPID				= Function.nullString(selectQuality.getUserCpid());
		String select_User_CPType			= Function.nullString(selectQuality.getUserCptype());
		String select_User_DPID				= Function.nullString(selectQuality.getUserDpid());
		String select_User_LicenseType		= Function.nullString(selectQuality.getUserLicensetype());
		String select_User_OrderBy			= Function.nullString(selectQuality.getUserOrderby());
		String select_User_BelongGroupID	= Function.nullString(selectQuality.getUserBelongGroupId());
		String select_User_IntID			= Function.nullString(selectQuality.getUserIntId());
		
		if(select_User_Id.equals("0"))
		{
			selectString.append(" C1 as userId,");
		}
		if(select_User_LoginName.equals("0"))
		{
			selectString.append(" C630000001 as userLoginName,");
		}
		if(select_User_PassWord.equals("0"))
		{
			selectString.append(" C630000002 as userPassword,");
		}
		if(select_User_FullName.equals("0"))
		{
			selectString.append(" C630000003 as userFullName,");
		}
		if(select_User_CreateUser.equals("0"))
		{
			selectString.append(" C630000004 as userCreateUser,");
		}
		if(select_User_Position.equals("0"))
		{
			selectString.append(" C630000005 as userPosition,");
		}
		if(select_User_IsManager.equals("0"))
		{
			selectString.append(" C630000006 as userIsManager,");
		}
		if(select_User_Type.equals("0"))
		{
			selectString.append(" C630000007 as userType,");
		}
		if(select_User_Mobie.equals("0"))
		{
			selectString.append(" C630000008 as userMobie,");
		}
		if(select_User_Phone.equals("0"))
		{
			selectString.append(" C630000009 as userPhone,");
		}
		if(select_User_Fax.equals("0"))
		{
			selectString.append(" C630000010 as userFax,");
		}
		if(select_User_Mail.equals("0"))
		{
			selectString.append(" C630000011 as userMail,");
		}
		if(select_User_Status.equals("0"))
		{
			selectString.append(" C630000012 as userStatus,");
		}
		if(select_User_CPID.equals("0"))
		{
			selectString.append(" C630000013 as userCPID,");
		}
		if(select_User_CPType.equals("0"))
		{
			selectString.append(" C630000014 as userCPType,");
		}
		if(select_User_DPID.equals("0"))
		{
			selectString.append(" C630000015 as userDPID,");
		}
		if(select_User_LicenseType.equals("0"))
		{
			selectString.append(" C630000016 as userLicenseType,");
		}
		if(select_User_OrderBy.equals("0"))
		{
			selectString.append(" C630000017 as userOrderBy,");
		}
		if(select_User_BelongGroupID.equals("0"))
		{
			selectString.append(" C630000036 as userBelongGroupID,");
		}
		if(select_User_IntID.equals("0"))
		{
			selectString.append(" C630000029 as userIntID,");
		}

		//根据传入的第二个参数确定where条件
		StringBuffer whereString	= new StringBuffer();
		
		String where_User_Id			= Function.nullString(whereQuality.getUserId());
		String where_User_LoginName		= Function.nullString(whereQuality.getUserLoginname());
		String where_User_PassWord		= Function.nullString(whereQuality.getUserPassword());
		String where_User_FullName		= Function.nullString(whereQuality.getUserFullname());
		String where_User_CreateUser	= Function.nullString(whereQuality.getUserCreateuser());
		String where_User_Position		= Function.nullString(whereQuality.getUserPosition());
		String where_User_IsManager		= Function.nullString(whereQuality.getUserIsmanager());
		String where_User_Type			= Function.nullString(whereQuality.getUserType());
		String where_User_Mobie			= Function.nullString(whereQuality.getUserMobie());
		String where_User_Phone			= Function.nullString(whereQuality.getUserPhone());
		String where_User_Fax			= Function.nullString(whereQuality.getUserFax());
		String where_User_Mail			= Function.nullString(whereQuality.getUserMail());
		String where_User_Status		= Function.nullString(whereQuality.getUserStatus());
		String where_User_CPID			= Function.nullString(whereQuality.getUserCpid());
		String where_User_CPType		= Function.nullString(whereQuality.getUserCptype());
		String where_User_DPID			= Function.nullString(whereQuality.getUserDpid());
		String where_User_LicenseType	= Function.nullString(whereQuality.getUserLicensetype());
		String where_User_OrderBy		= Function.nullString(whereQuality.getUserOrderby());
		String where_User_BelongGroupID	= Function.nullString(whereQuality.getUserBelongGroupId());
		String where_User_IntID			= String.valueOf(whereQuality.getUserIntId());
		
		if(where_User_Id!=null&&!where_User_Id.equals(""))
		{
			whereString.append(" and C1='"+where_User_Id+"'");
		}
		if(where_User_LoginName!=null&&!where_User_LoginName.equals(""))
		{
			whereString.append(" and C630000001='"+where_User_LoginName+"'");
		}
		if(where_User_PassWord!=null&&!where_User_PassWord.equals(""))
		{
			whereString.append(" and C630000002='"+where_User_PassWord+"'");
		}
		if(where_User_FullName!=null&&!where_User_FullName.equals(""))
		{
			whereString.append(" and C630000003='"+where_User_FullName+"'");
		}
		if(where_User_CreateUser!=null&&!where_User_CreateUser.equals(""))
		{
			whereString.append(" and C630000004='"+where_User_CreateUser+"'");
		}
		if(where_User_Position!=null&&!where_User_Position.equals(""))
		{
			whereString.append(" and C630000005='"+where_User_Position+"'");
		}
		if(where_User_IsManager!=null&&!where_User_IsManager.equals(""))
		{
			whereString.append(" and C630000006='"+where_User_IsManager+"'");
		}
		if(where_User_Type!=null&&!where_User_Type.equals(""))
		{
			whereString.append(" and C630000007='"+where_User_Type+"'");
		}
		if(where_User_Mobie!=null&&!where_User_Mobie.equals(""))
		{
			whereString.append(" and C630000008='"+where_User_Mobie+"'");
		}
		if(where_User_Phone!=null&&!where_User_Phone.equals(""))
		{
			whereString.append(" and C630000009='"+where_User_Phone+"'");
		}
		if(where_User_Fax!=null&&!where_User_Fax.equals(""))
		{
			whereString.append(" and C630000010='"+where_User_Fax+"'");
		}
		if(where_User_Mail!=null&&!where_User_Mail.equals(""))
		{
			whereString.append(" and C630000011='"+where_User_Mail+"'");
		}
		if(where_User_Status!=null&&!where_User_Status.equals(""))
		{
			whereString.append(" and C630000012='"+where_User_Status+"'");
		}
		if(where_User_CPID!=null&&!where_User_CPID.equals(""))
		{
			whereString.append(" and C630000013='"+where_User_CPID+"'");
		}
		if(where_User_CPType!=null&&!where_User_CPType.equals(""))
		{
			whereString.append(" and C630000014='"+where_User_CPType+"'");
		}
		if(where_User_DPID!=null&&!where_User_DPID.equals(""))
		{
			whereString.append(" and C630000015='"+where_User_DPID+"'");
		}
		if(where_User_LicenseType!=null&&!where_User_LicenseType.equals(""))
		{
			whereString.append(" and C630000016='"+where_User_LicenseType+"'");
		}
		if(where_User_OrderBy!=null&&!where_User_OrderBy.equals(""))
		{
			whereString.append(" and C630000017='"+where_User_OrderBy+"'");
		}
		if(where_User_BelongGroupID!=null&&!where_User_BelongGroupID.equals(""))
		{
			whereString.append(" and C630000036='"+where_User_BelongGroupID+"'");
		}
		if(!where_User_IntID.equals("0")&&!where_User_IntID.equals(""))
		{
			whereString.append(" and C630000029='"+where_User_IntID+"'");
		}

		//根据先前的两个参数确定最终的sql语句
		StringBuffer sqlString	= new StringBuffer();
		
		sqlString.append("select");
		sqlString.append(Function.splitString(selectString.toString(),","));
		sqlString.append(" from "+usertablename);
		sqlString.append(" where 1=1");
		sqlString.append(whereString.toString());
		
		try
		{
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			stm		= dataBase.GetStatement();
			//获得数据库查询结果集
			rs		= dataBase.executeResultSet(stm,sqlString.toString());
			//将ResultSet结果集作为参数通过方法getResultSetList得到最终定制的List
			List list			= getResultSetList(rs,sqlString.toString());
			stm.close();
			dataBase.closeConn();
			return list;
		}
		catch(Exception e)
		{
			logger.error("[500]UserInfo.getUserInfo() 根据传过来的参数selectQuality和whereQuality返回一个List"+e.getMessage());
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	
	/**
	 * <p>Description:根据传入的sql语句查询返回List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-25
	 * @param sqlString
	 * @return List
	 */
	public List getUserInfo(String sqlString)
	{
		IDataBase dataBase = null;
		Statement stm = null;
		ResultSet rs = null;
		
		try
		{
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			stm		= dataBase.GetStatement();
			//获得数据库查询结果集
			rs		= dataBase.executeResultSet(stm,sqlString);
			//将ResultSet结果集作为参数通过方法getResultSetList得到最终定制的List
			List list			= getResultSetList(rs,sqlString);
			stm.close();
			return list;
		}
		catch(Exception e)
		{
			logger.error("[501]UserInfo.getUserInfo() 根据传入的sql语句查询返回List"+e.getMessage());
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	
	/**
	 * <p>Description:通过结果集返回包含Bean的List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-25
	 * @param rs
	 * @return List
	 */
	public List getResultSetList(ResultSet rs,String str)
	{
		ArrayList list = new ArrayList();
		try
		{
			
			while(rs.next())
			{
				PeopleInfo peopleInfo = new PeopleInfo();
				
				if(str.indexOf("userId")!=-1)
				{
					peopleInfo.setUserId(rs.getString("userId"));
				}
				if(str.indexOf("userLoginName")!=-1)
				{
					peopleInfo.setUserLoginname(rs.getString("userLoginName"));
				}
				if(str.indexOf("userPassword")!=-1)
				{
					peopleInfo.setUserPassword(rs.getString("userPassword"));
				}
				if(str.indexOf("userFullName")!=-1)
				{
					peopleInfo.setUserFullname(rs.getString("userFullName"));
				}
				if(str.indexOf("userCreateUser")!=-1)
				{
					peopleInfo.setUserCreateuser(rs.getString("userCreateUser"));
				}
				if(str.indexOf("userPosition")!=-1)
				{
					peopleInfo.setUserPosition(rs.getString("userPosition"));
				}
				if(str.indexOf("userIsManager")!=-1)
				{
					peopleInfo.setUserIsmanager(rs.getString("userIsManager"));
				}
				if(str.indexOf("userType")!=-1)
				{
					peopleInfo.setUserType(rs.getString("userType"));
				}
				if(str.indexOf("userMobie")!=-1)
				{
					peopleInfo.setUserMobie(rs.getString("userMobie"));
				}
				if(str.indexOf("userPhone")!=-1)
				{
					peopleInfo.setUserPhone(rs.getString("userPhone"));
				}
				if(str.indexOf("userFax")!=-1)
				{
					peopleInfo.setUserFax(rs.getString("userFax"));
				}
				if(str.indexOf("userMail")!=-1)
				{
					peopleInfo.setUserMail(rs.getString("userMail"));
				}
				if(str.indexOf("userStatus")!=-1)
				{
					peopleInfo.setUserStatus(rs.getString("userStatus"));
				}
				if(str.indexOf("userCPID")!=-1)
				{
					peopleInfo.setUserCpid(rs.getString("userCPID"));
				}
				if(str.indexOf("userCPType")!=-1)
				{
					peopleInfo.setUserBelongGroupId(rs.getString("userCPType"));
				}
				if(str.indexOf("userDPID")!=-1)
				{
					peopleInfo.setUserDpid(rs.getString("userDPID"));
				}
				if(str.indexOf("userLicenseType")!=-1)
				{
					peopleInfo.setUserLicensetype(rs.getString("userLicenseType"));
				}
				if(str.indexOf("userOrderBy")!=-1)
				{
					peopleInfo.setUserOrderby(rs.getString("userOrderBy"));
				}
				if(str.indexOf("userBelongGroupID")!=-1)
				{
					peopleInfo.setUserBelongGroupId(rs.getString("userBelongGroupID"));
				}
				if(str.indexOf("userIntID")!=-1)
				{
					peopleInfo.setUserIntId(rs.getInt("userIntID"));
				}
				
				list.add(peopleInfo);
			}
			rs.close();
			return list;
		}
		catch(Exception e)
		{
			logger.error("[502]UserInfo.getResultSetList() 通过结果集返回包含Bean的List"+e.getMessage());
			return null;
		}
	}
	
	
	
	public static void main(String args[])
	{
		PeopleFieldFlag userflag = new PeopleFieldFlag();
		//想取哪个字段，把哪个字段设置为0
		userflag.setUserMobie("0");
		PeopleInfo userinfo = new PeopleInfo();
		//以下是查询条件。
		userinfo.setUserLoginname("maxuegang");
		
		UserInfoInterface userinterface = new UserInfoInterface();
		List list = userinterface.getUserInfo(userflag,userinfo);
		for(Iterator it = list.iterator();it.hasNext();)
		{
			PeopleInfo peopleinfo = (PeopleInfo)it.next();
			System.out.println("移动电话："+peopleinfo.getUserMobie());
		}
		
	}
}
