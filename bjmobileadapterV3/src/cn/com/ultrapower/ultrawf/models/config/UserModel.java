package cn.com.ultrapower.ultrawf.models.config;

import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.util.FormatString;

public class UserModel {
//  属性设置区域--Begin--
    private  String RequestID;
    private  String FullName;
    private  String LoginName;
    private  String PassWord;
    private  String EmailAddress;
    private  String GroupList;
    private  long LicenseType;
    private  String RoleList;
    
    private  String	Dep;
    private  String	DepID;
    private  String	Corp;
    private  String	CorpID;
    private  String	DN;
    private  String	DNID;
    private  String	CloseBaseSamenessGroup;
    
    public String getDep() {
		return Dep;
	}
	public void setDep(String dep) {
		Dep = dep;
	}
	public String getDepID() {
		return DepID;
	}
	public void setDepID(String depID) {
		DepID = depID;
	}
	public String getCorp() {
		return Corp;
	}
	public void setCorp(String corp) {
		Corp = corp;
	}
	public String getCorpID() {
		return CorpID;
	}
	public void setCorpID(String corpID) {
		CorpID = corpID;
	}
	public String getDN() {
		return DN;
	}
	public void setDN(String dn) {
		DN = dn;
	}
	public String getDNID() {
		return DNID;
	}
	public void setDNID(String dnid) {
		DNID = dnid;
	}
	public String getCloseBaseSamenessGroup() {
		return CloseBaseSamenessGroup;
	}
	public void setCloseBaseSamenessGroup(String closeBaseSamenessGroup) {
		CloseBaseSamenessGroup = closeBaseSamenessGroup;
	}
	public String getCloseBaseSamenessGroupID() {
		return CloseBaseSamenessGroupID;
	}
	public void setCloseBaseSamenessGroupID(String closeBaseSamenessGroupID) {
		CloseBaseSamenessGroupID = closeBaseSamenessGroupID;
	}

	private  String	CloseBaseSamenessGroupID;

    public UserModel(){}
//    1 RequestID 
    public String GetUserID()
    {
       return RequestID;
    }
    public void   SetUserID(String p_RequestID)
    {
       RequestID=p_RequestID;
    }
//    8 FullName 
    public String GetFullName()
    {
       return FullName;
    }
    public void   SetFullName(String p_FullName)
    {
       FullName=p_FullName;
    }
//    101 LoginName 
    public String GetLoginName()
    {
       return LoginName;
    }
    public void   SetLoginName(String p_LoginName)
    {
       LoginName=p_LoginName;
    }
//    102 Password 
    public String GetPassWord()
    {
       return PassWord;
    }
    public void   SetPassWord(String p_PassWord)
    {
       PassWord=p_PassWord;
    }
//    103 EmailAddress 
    public String GetEmailAddress()
    {
       return EmailAddress;
    }
    public void   SetEmailAddress(String p_EmailAddress)
    {
       EmailAddress=p_EmailAddress;
    }
//    104 GroupList 
    public String GetGroupList()
    {
       if(GroupList==null)
    	   return "";
       return GroupList;
    }
    public void   SetGroupList(String p_GroupList)
    {
       GroupList=p_GroupList;
    }

//    109 LicenseType 
    public long GetLicenseType()
    {
       return LicenseType;
    }
    public void   SetLicenseType(long p_LicenseType)
    {
       LicenseType=p_LicenseType;
    }

//    属性设置区域--End--

    


	public UserModel(String str_LoginName) 
    {
		try
		{
			IDataBase m_dbConsole = GetDataBase.createDataBase();
			StringBuffer strSelect = new StringBuffer();
			RemedyDBOp RemedyDBOpObj = new RemedyDBOp();
			strSelect.append("SELECT ");
			strSelect.append("C1 as UserID,C101 as LoginName,C8 as FullName,C102 as PassWord,C104 as GroupList,C109 as LicenseType, C640000000 as RoleList");
			strSelect.append(" from " + RemedyDBOpObj.GetRemedyTableName("User"));
			strSelect.append(" where C101 ='"+str_LoginName+"'");
			Statement stm=m_dbConsole.GetStatement();
			ResultSet objRs = m_dbConsole.executeResultSet(stm,strSelect.toString());	
			if (objRs.next()) 
			{
				this.SetLoginName(objRs.getString("LoginName"));
				this.SetPassWord(objRs.getString("PassWord"));
				this.SetFullName(objRs.getString("FullName"));
				this.SetGroupList(objRs.getString("GroupList"));
				this.setRoleList(FormatString.CheckNullString(DataBaseOtherDeal.GetFieldClobValue(objRs, "RoleList")));
				this.SetLicenseType(objRs.getLong("LicenseType"));
				this.setCloseBaseSamenessGroup("");
				this.setCloseBaseSamenessGroupID("");
				
				StringBuffer strSelectSysUser = new StringBuffer();
				strSelectSysUser.append("Select ");
				strSelectSysUser.append(" C630000013 as User_CPID, C630000015 as User_DPID ");
				strSelectSysUser.append(" from " + RemedyDBOpObj.GetRemedyTableName("UltraProcess:SysUser"));
				strSelectSysUser.append(" where C630000001 = '"+str_LoginName+"' ");
				Statement stm_SysUser=m_dbConsole.GetStatement();
				ResultSet objRs_SysUser = m_dbConsole.executeResultSet(stm_SysUser,strSelectSysUser.toString());
				String str_User_CPID = "";
				String str_User_DPID = "";
				if (objRs_SysUser.next())
				{
					str_User_CPID = objRs_SysUser.getString("User_CPID");
					str_User_DPID = objRs_SysUser.getString("User_DPID");
				}
				objRs_SysUser.close();
				stm_SysUser.close();
				
				StringBuffer strSelectSysGroup_CP = new StringBuffer();
				strSelectSysGroup_CP.append("Select ");
				strSelectSysGroup_CP.append(" C630000018 as User_CPName, C630000030 as User_CPIntID ");
				strSelectSysGroup_CP.append(" from " + RemedyDBOpObj.GetRemedyTableName("UltraProcess:SysGroup"));
				strSelectSysGroup_CP.append(" where C1 = '"+str_User_CPID+"' ");
				Statement stm_SysGroup_CP=m_dbConsole.GetStatement();
				ResultSet objRs_SysGroup_CP = m_dbConsole.executeResultSet(stm_SysGroup_CP,strSelectSysGroup_CP.toString());	
				String str_User_CPName = "";
				if (objRs_SysGroup_CP.next())
				{
					str_User_CPName = objRs_SysGroup_CP.getString("User_CPName");
					str_User_CPID 	= objRs_SysGroup_CP.getString("User_CPIntID");
				}
				objRs_SysGroup_CP.close();
				stm_SysGroup_CP.close();				

				StringBuffer strSelectSysGroup_DP = new StringBuffer();
				strSelectSysGroup_DP.append("Select ");
				strSelectSysGroup_DP.append(" C630000018 as User_DPName, C630000030 as User_DPIntID ");
				strSelectSysGroup_DP.append(" from " + RemedyDBOpObj.GetRemedyTableName("UltraProcess:SysGroup"));
				strSelectSysGroup_DP.append(" where C1 = '"+str_User_DPID+"' ");
				Statement stm_SysGroup_DP=m_dbConsole.GetStatement();
				ResultSet objRs_SysGroup_DP = m_dbConsole.executeResultSet(stm_SysGroup_DP,strSelectSysGroup_DP.toString());	
				String str_User_DPName = "";
				if (objRs_SysGroup_DP.next())
				{
					str_User_DPName = objRs_SysGroup_DP.getString("User_DPName");
					str_User_DPID 	= objRs_SysGroup_DP.getString("User_DPIntID");
				}
				objRs_SysGroup_DP.close();
				stm_SysGroup_DP.close();	

				String str_User_DNID = str_User_CPID +  "." + str_User_DPID;
				String str_User_DN   = str_User_CPName +  "." + str_User_DPName;

				this.setCorp(str_User_CPName);
				this.setCorpID(str_User_CPID);
				this.setDep(str_User_DPName);
				this.setDepID(str_User_DPID);
				this.setDN(str_User_DN);
				this.setDNID(str_User_DNID);
				
			}
			else
			{
				throw new Exception("该用户不存在");
			}
			objRs.close();
			stm.close();
			m_dbConsole.closeConn();			
		}
		catch(Exception ex)
		{
			System.err.println("初始化人员失败："+ex.getMessage());
			ex.printStackTrace();			
		}
    }
	public String getRoleList()
	{
		return RoleList;
	}
	public void setRoleList(String roleList)
	{
		RoleList = roleList;
	}            
}
