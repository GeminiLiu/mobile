package cn.com.ultrapower.ultrawf.models.config;

public class ParUserModel {

//  属性设置区域--Begin--
    private  String RequestID="";
    private  String FullName="";
    private  String LoginName="";
    private  String PassWord="";
    private  String EmailAddress="";
    private  String GroupList="";
    private  long LicenseType=999;
 
//    1 RequestID 
    public String GetRequestID()
    {
       return RequestID;
    }
    public void   SetRequestID(String p_RequestID)
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

	public String GetWhereSql()
	{
		StringBuffer stringBuffer=new StringBuffer();
		//1	RequestID
		if(!GetRequestID().equals(""))
			stringBuffer.append(" and C1='"+GetRequestID()+"'");
		//8	FullName
		if(!GetFullName().equals(""))
			stringBuffer.append(" and C8='"+GetFullName()+"'");		
		//101	LoginName
		if(!GetLoginName().equals(""))
			stringBuffer.append(" and C101='"+GetLoginName()+"'");		
		//103	EmailAddress
		if(!GetEmailAddress().equals(""))
			stringBuffer.append(" and C103='"+GetEmailAddress()+"'");			
		//104	GroupList
		if(!GetGroupList().equals(""))
			stringBuffer.append(" and C104='"+GetGroupList()+"'");			
		//109	LicenseType
		if(GetLicenseType()!=999)
			stringBuffer.append(" and C109='"+String.valueOf(GetLicenseType())+"'");	
		return stringBuffer.toString();
	}
}
