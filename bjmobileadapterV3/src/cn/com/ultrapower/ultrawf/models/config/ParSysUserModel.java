package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParSysUserModel {

//	属性设置区域--Begin--
	private  String User_ID;
	private  String User_LoginName;
	private  String User_PassWord;
	private  String User_FullName;
	private  String User_CreateUser;
	private  String User_Position;
	private  String User_IsManager;
	private  String User_Type;
	private  String User_Mobie;
	private  String User_Phone;
	private  String User_Fax;
	private  String User_Mail;
	private  String User_Status;
	private  String User_CPID;
	private  String User_CPType;
	private  String User_DPID;
	private  String User_LicenseType;
	private  String User_OrderBy;
	private  String User_IntID;
	private  String User_BelongGroupID;
//	1 User_ID 
	public String getUser_ID()
	{
	   return User_ID;
	}
	public void   setUser_ID(String p_User_ID)
	{
	   User_ID=p_User_ID;
	}
//	630000001 User_LoginName 
	public String getUser_LoginName()
	{
	   return User_LoginName;
	}
	public void   setUser_LoginName(String p_User_LoginName)
	{
	   User_LoginName=p_User_LoginName;
	}
//	630000002 User_PassWord 
	public String getUser_PassWord()
	{
	   return User_PassWord;
	}
	public void   setUser_PassWord(String p_User_PassWord)
	{
	   User_PassWord=p_User_PassWord;
	}
//	630000003 User_FullName 
	public String getUser_FullName()
	{
	   return User_FullName;
	}
	public void   setUser_FullName(String p_User_FullName)
	{
	   User_FullName=p_User_FullName;
	}
//	630000004 User_CreateUser 
	public String getUser_CreateUser()
	{
	   return User_CreateUser;
	}
	public void   setUser_CreateUser(String p_User_CreateUser)
	{
	   User_CreateUser=p_User_CreateUser;
	}
//	630000005 User_Position 
	public String getUser_Position()
	{
	   return User_Position;
	}
	public void   setUser_Position(String p_User_Position)
	{
	   User_Position=p_User_Position;
	}
//	630000006 User_IsManager 
	public String getUser_IsManager()
	{
	   return User_IsManager;
	}
	public void   setUser_IsManager(String p_User_IsManager)
	{
	   User_IsManager=p_User_IsManager;
	}
//	630000007 User_Type 
	public String getUser_Type()
	{
	   return User_Type;
	}
	public void   setUser_Type(String p_User_Type)
	{
	   User_Type=p_User_Type;
	}
//	630000008 User_Mobie 
	public String getUser_Mobie()
	{
	   return User_Mobie;
	}
	public void   setUser_Mobie(String p_User_Mobie)
	{
	   User_Mobie=p_User_Mobie;
	}
//	630000009 User_Phone 
	public String getUser_Phone()
	{
	   return User_Phone;
	}
	public void   setUser_Phone(String p_User_Phone)
	{
	   User_Phone=p_User_Phone;
	}
//	630000010 User_Fax 
	public String getUser_Fax()
	{
	   return User_Fax;
	}
	public void   setUser_Fax(String p_User_Fax)
	{
	   User_Fax=p_User_Fax;
	}
//	630000011 User_Mail 
	public String getUser_Mail()
	{
	   return User_Mail;
	}
	public void   setUser_Mail(String p_User_Mail)
	{
	   User_Mail=p_User_Mail;
	}
//	630000012 User_Status 
	public String getUser_Status()
	{
	   return User_Status;
	}
	public void   setUser_Status(String p_User_Status)
	{
	   User_Status=p_User_Status;
	}
//	630000013 User_CPID 
	public String getUser_CPID()
	{
	   return User_CPID;
	}
	public void   setUser_CPID(String p_User_CPID)
	{
	   User_CPID=p_User_CPID;
	}
//	630000014 User_CPType 
	public String getUser_CPType()
	{
	   return User_CPType;
	}
	public void   setUser_CPType(String p_User_CPType)
	{
	   User_CPType=p_User_CPType;
	}
//	630000015 User_DPID 
	public String getUser_DPID()
	{
	   return User_DPID;
	}
	public void   setUser_DPID(String p_User_DPID)
	{
	   User_DPID=p_User_DPID;
	}
//	630000016 User_LicenseType 
	public String getUser_LicenseType()
	{
	   return User_LicenseType;
	}
	public void   setUser_LicenseType(String p_User_LicenseType)
	{
	   User_LicenseType=p_User_LicenseType;
	}
//	630000017 User_OrderBy 
	public String getUser_OrderBy()
	{
	   return User_OrderBy;
	}
	public void   setUser_OrderBy(String p_User_OrderBy)
	{
	   User_OrderBy=p_User_OrderBy;
	}
//	630000029 User_IntID 
	public String getUser_IntID()
	{
	   return User_IntID;
	}
	public void   setUser_IntID(String p_User_IntID)
	{
	   User_IntID=p_User_IntID;
	}
//	630000036 User_BelongGroupID 
	public String getUser_BelongGroupID()
	{
	   if(User_BelongGroupID==null)
		   User_BelongGroupID="";
	   return User_BelongGroupID;
	}
	public void   setUser_BelongGroupID(String p_User_BelongGroupID)
	{
	   User_BelongGroupID=p_User_BelongGroupID;
	}
//	属性设置区域--End--
	
	public String GetWhereSql()
	{
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C1",getUser_ID()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000001",getUser_LoginName()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000002",getUser_PassWord()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000003",getUser_FullName()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000004",getUser_CreateUser()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000005",getUser_Position()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000006",getUser_IsManager()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000007",getUser_Type()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000008",getUser_Mobie()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000009",getUser_Phone()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000010",getUser_Fax()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000011",getUser_Mail()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000012",getUser_Status()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000013",getUser_CPID()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000014",getUser_CPType()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000015",getUser_DPID()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000016",getUser_LicenseType()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000017",getUser_OrderBy()));
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000029",getUser_IntID()));
		String strBelongGroupid=getUser_BelongGroupID();
		if(!strBelongGroupid.trim().equals(""))
		{
			String[]strAry=strBelongGroupid.split(",");
			int lens=0;
			if(strAry!=null)
				lens=strAry.length;
			String strLongSql="";
			for(int i=0;i<lens;i++)
			{
				if(strLongSql.trim().equals(""))
					strLongSql="C630000036 like '%"+strAry[i]+";%'";
				else
					strLongSql+=" or C630000036 like '%"+strAry[i]+";%'";
			}
			if(!strLongSql.trim().equals(""))
			{
				strLongSql=" and ("+strLongSql+")";
				stringBuffer.append(strLongSql);
			}
		}
		stringBuffer.append(this.getExtendSql());
		//stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000036",getUser_BelongGroupID()));
		return stringBuffer.toString();
		
	}		
	
	private String OrderbyFiledNameString="";
	//排序类型 0升序　否则为降序
	private int OrderByType=0;
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString,int p_intOrderByType) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=p_intOrderByType;
	}	
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=0;
	}	
	
	//返回排序字符串

	public String getOrderbyFiledNameString() 
	{
		String strRe="";
		if(!OrderbyFiledNameString.trim().equals(""))
		{
			//如果升序
			if(OrderByType==0)
				strRe=" order by "+OrderbyFiledNameString;
			else
			{
				String[] strAry =OrderbyFiledNameString.split(",");
				
				for(int index=0;index<strAry.length;index++)
				{	if(strRe.trim().equals(""))
						strRe+= strAry[index]+" desc";
					else
						strRe+=","+ strAry[index]+" desc";
				}
				strRe=" order by "+strRe;
			}//if(OrderByType==0)
		}
		return (strRe);
	}	
	
	private String ExtendSql;
	/**
	 * 查询扩展得sql语句
	 * @return
	 */	
	public String getExtendSql() {
		if(ExtendSql==null)
			ExtendSql="";
		return ExtendSql;
	}
	public void setExtendSql(String extendSql) {
		ExtendSql = extendSql;
	}	
	
}
