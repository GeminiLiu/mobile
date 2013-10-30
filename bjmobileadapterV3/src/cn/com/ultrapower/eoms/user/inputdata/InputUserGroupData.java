package cn.com.ultrapower.eoms.user.inputdata;

import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import cn.com.ultrapower.eoms.user.comm.function.*;
import cn.com.ultrapower.eoms.user.database.*;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata.Group;
import cn.com.ultrapower.eoms.user.rolemanage.group.bean.*;
import cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata.People;
import cn.com.ultrapower.eoms.user.rolemanage.people.bean.PeopleInfo;
public class InputUserGroupData
{

	public static void main(String[] args)
	{
		try
		{
			InputUserGroupData ReadExcelPoi1=new InputUserGroupData();
			String path=Function.getProjectPath();
			//ReadExcelPoi1.inputgroup(path);
			//ReadExcelPoi1.inputuser(path);
			
		}catch(Exception e)
		{
			
		}
	}
	public boolean inputuser(String path)
	{
		IDataBase dataBase = null;
		Statement stm		= null;
		ResultSet rs		= null;
		try
		{
			String strsql		= "";
			path=path+"test1.xls";
			
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(path));
			//获得Excel的Sheet页
			HSSFSheet sheet = workbook.getSheet("Sheet1");
			//也可以用getSheetAt(int index)按索引引用
			//在Excel文档中，第一章工作表的缺省索引是0
			//其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			//读取第一行
			int iRowCount = sheet.getPhysicalNumberOfRows();
			int iColCount = sheet.getRow(0).getPhysicalNumberOfCells();
			System.out.println("总行数："+iRowCount+"总列数"+iColCount);
			HSSFRow row;
			HSSFCell cell;
			String strtmp		= "";
			StringBuffer strbuf	= new StringBuffer();
			//			实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			//			获得数据库查询结果集
			stm		= dataBase.GetStatement();


		    String loginname="";
		    String password="";
		    String fullname="";
		    String CreateUser="";
		    String Position="";
		    String Type="";
		    String Mobie="";
		    String Phone="";
		    String Fax="";
		    String Mail="";
		    String Status="";
		    String CPname="";
		    String Cpid="";
		    String DPname="";
		    String LicenseType="";
		    
		    People people				= new People();
			for(int i=1;i<iRowCount;i++)
			{
				GroupInfo groupinfo=new GroupInfo();
				System.out.println("第"+i+"行");
				row 	= sheet.getRow(i);
				//登陆名
				cell 				= row.getCell((short)0);
				loginname			= getValue(cell);
				loginname.trim();
				//密码
				cell 				= row.getCell((short)1);
				password			= getValue(cell);
				password			= password.trim();
				//姓名
				cell 				= row.getCell((short)2);
				fullname			= getValue(cell);
				//创建人
				cell 				= row.getCell((short)3);
				CreateUser			= getValue(cell);
				//职位
				cell 				= row.getCell((short)4);
				Position			= getValue(cell);
				//类别
				cell 				= row.getCell((short)5);
				Type				= getValue(cell);
				//手机
				cell 				= row.getCell((short)6);
				Mobie				= getValue(cell);
				Mobie				=Mobie.trim();
				//电话
				cell 				= row.getCell((short)7);
				Phone				= getValue(cell);
				
				//传真
				cell 				= row.getCell((short)8);
				Fax					= getValue(cell);
				//邮件
				cell 				= row.getCell((short)9);
				Mail				= getValue(cell);
				//状态
				cell 				= row.getCell((short)10);
				Status				= getValue(cell);
				//单位
				cell 				= row.getCell((short)11);
				CPname				= getValue(cell);
				CPname				=String.valueOf(CPname).trim();
				//部门
				cell 				= row.getCell((short)12);
				DPname				= getValue(cell);
				DPname				=String.valueOf(DPname).trim();
				//licence类型
				cell 				= row.getCell((short)13);
				LicenseType			= getValue(cell);
				LicenseType			= String.valueOf(LicenseType).trim();
				PeopleInfo peopleInfo = new PeopleInfo();
				
				peopleInfo.setUserLoginname(loginname);
				peopleInfo.setUserPassword(password);
				peopleInfo.setUserCreateuser(CreateUser);
				peopleInfo.setUserFax(Fax);
				peopleInfo.setUserFullname(fullname.trim());
				peopleInfo.setUserIsmanager("0");
				peopleInfo.setUserLicensetype(LicenseType);
				peopleInfo.setUserMail(Mail);
				peopleInfo.setUserMobie(Mobie.trim());
				peopleInfo.setUserPhone(Phone.trim());
				peopleInfo.setUserPosition(Position.trim());
				peopleInfo.setUserStatus(Status.trim());
				peopleInfo.setUserType(Type.trim());
				peopleInfo.setUserBelongGroupId("");
				peopleInfo.setUserOrderby("99");
				peopleInfo.setUserCptype("");
//				单位id
				if(!CPname.equals("null")&&!CPname.equals("")&&!CPname.equals("0.0")&&!CPname.equals("0"))
				{
					Cpid=getCpid(CPname,stm);
					if(Cpid == null||Cpid.equals(""))
					{
						peopleInfo.setUserCpid("");
					}
					else
					{
						peopleInfo.setUserCpid(Cpid.trim());
					}
				}
				else
				{
					peopleInfo.setUserCpid("");
				}
//				获得部门id
				if(!String.valueOf(DPname).equals("null")&&!String.valueOf(DPname).equals("")&&!String.valueOf(DPname).equals("0")&&!String.valueOf(DPname).equals("0.0"))
				{
					ValueBean valueBean	= getdepartid(Cpid,DPname,stm);
					peopleInfo.setUserDpid(valueBean.getParentid().trim());
				}
				else
				{
					peopleInfo.setUserDpid("");
				}
				people.insertPeople(peopleInfo);
			}
//			Function.closeDataBaseSource(null,stm,dataBase);
			return true;
		}
		catch(Exception e)
		{
			e.getMessage();
			return false;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	public boolean inputgroup(String path)
	{
		IDataBase dataBase = null;
		Statement stm	   = null;
		ResultSet rs	   = null;
		try
		{
			String strsql		= "";
			path=path+"test.xls";
			
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(path));
			//获得Excel的Sheet页
			HSSFSheet sheet = workbook.getSheet("Sheet2");
			//也可以用getSheetAt(int index)按索引引用
			//在Excel文档中，第一章工作表的缺省索引是0
			//其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			//读取第一行
			int iRowCount = sheet.getPhysicalNumberOfRows();
			int iColCount = sheet.getRow(0).getPhysicalNumberOfCells();
			System.out.println("总行数："+iRowCount+"总列数"+iColCount);
			HSSFRow row;
			HSSFCell cell;
			String strtmp		= "";
			StringBuffer strbuf	= new StringBuffer();
			//			实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			//			获得数据库查询结果集
			stm		= dataBase.GetStatement();


		    String groupName;
		    String groupFullname;
		    String groupParentid;
		    String groupType;
		    String groupOrderBy;
		    String groupPhone;
		    String groupFax;
		    String groupStatus;
		    String groupCompanyid="0";
		    String groupCompanytype;
		    String groupDesc;
		    String groupDnId;
		    String groupparentname;
		    String groupCompanyname;
		    
		    Group group			= new Group();
			for(int i=1;i<iRowCount;i++)
			{
				GroupInfo groupinfo=new GroupInfo();
				System.out.println("第"+i+"行");
				row 	= sheet.getRow(i);
				//组名称
				cell 				= row.getCell((short)0);
				groupName			= getValue(cell);
				groupName=groupName.trim();
				//父组
				cell 				= row.getCell((short)1);
				groupparentname		= getValue(cell);
				groupparentname=groupparentname.trim();
				//组类型
				cell 				= row.getCell((short)2);
				groupType			= getValue(cell);
				//电话
				cell 				= row.getCell((short)3);
				groupPhone			= getValue(cell);
				//传真
				cell 				= row.getCell((short)4);
				groupFax			= getValue(cell);
				//状态
				cell 				= row.getCell((short)5);
				groupStatus			= getValue(cell);
				//单位id
				cell 				= row.getCell((short)6);
				groupCompanyname	= getValue(cell);
				groupCompanyname=groupCompanyname.trim();
				//单位类型
				cell 				= row.getCell((short)7);
				groupCompanytype	= getValue(cell);
				//描述
				cell 				= row.getCell((short)8);
				groupDesc			= getValue(cell);
				
				
				
				//设置组名称
				groupinfo.setGroupName(groupName.trim());

				//组类型
				if(groupType == null||groupType.equals(""))
				{
					groupinfo.setGroupType("");
				}
				else
				{
					groupinfo.setGroupType(groupType.trim());
				}
				
				//电话
				if(groupPhone == null||groupPhone.equals(""))
				{
					groupinfo.setGroupPhone("");
				}
				else
				{
					groupinfo.setGroupPhone(groupPhone.trim());
				}
				//传真
				if(groupFax == null||groupFax.equals(""))
				{
					groupinfo.setGroupFax("");
				}
				else
				{
					groupinfo.setGroupFax(groupFax.trim());
				}
				//状态
				if(groupStatus == null||groupStatus.equals(""))
				{
					groupinfo.setGroupStatus("");
				}
				else
				{
					groupinfo.setGroupStatus(groupStatus.trim());
				}
				
				//单位类型
				if(groupCompanytype == null||groupCompanytype.equals(""))
				{
					groupinfo.setGroupCompanytype("");
				}
				else
				{
					groupinfo.setGroupCompanytype(groupCompanytype);
				}
				//描述
				if(groupDesc == null||groupDesc.equals(""))
				{
					groupinfo.setGroupDesc("");
				}
				else
				{
					groupinfo.setGroupDesc(groupDesc.trim());
				}
				groupinfo.setGroupOrderBy("9999");
//				单位id
				if(!groupCompanyname.equals("")&&!groupCompanyname.equals("0.0")&&!groupCompanyname.equals("0"))
				{
					groupCompanyid=getCpid(groupCompanyname,stm);
					if(groupCompanyid == null||groupCompanyid.equals(""))
					{
						groupinfo.setGroupCompanyid("0");
					}
					else
					{
						groupinfo.setGroupCompanyid(groupCompanyid.trim());
					}
				}
				else
				{
					groupinfo.setGroupCompanyid("0");
				}
//				获得父id
				System.out.println(groupparentname+"dddddddddddd===============");
				if(!String.valueOf(groupparentname).equals("null")&&!String.valueOf(groupparentname).equals("0")&&!String.valueOf(groupparentname).equals("0.0"))
				{
					ValueBean valueBean	= getParentid(groupCompanyid,groupparentname,stm);
					groupinfo.setGroupParentid(valueBean.getParentid().trim());
					groupinfo.setGroupDnId(valueBean.getDnid().trim());
					System.out.println(valueBean.getDnid()+"dnidddd");
					groupFullname		= valueBean.getDnname()+"."+groupName;
					groupinfo.setGroupFullname(groupFullname.trim());
					System.out.println(groupFullname+"dnname11");
				}
				else
				{
					groupinfo.setGroupParentid("0");
					groupinfo.setGroupDnId("0;");
					System.out.println("0;dsdfdsf");
					groupinfo.setGroupFullname(groupName.trim());
					System.out.println(groupName+"dnname");
				}
				System.out.println(groupinfo.getGroupName());
				group.insertGroup(groupinfo);
			}
			
			return true;
		}
		catch(Exception e)
		{
			e.getMessage();
			return false;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	public String getCpid(String cpname,Statement stm)
	{
		ResultSet rs=null;
		GetFormTableName getFormTableName=new GetFormTableName();
		String RemedyTgroup=getFormTableName.GetFormName("RemedyTgroup");
		try
		{
			String strsql="select * from "+RemedyTgroup+" where C630000018='"+String.valueOf(cpname).trim()+"'";
			rs=stm.executeQuery(strsql);
			String value	= "";
			if(rs.next())
			{
				value=rs.getString("C1");
			}
			return value;
		}
		catch(Exception e)
		{
			e.getMessage();
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
			}
			catch(Exception ee)
			{
				ee.getMessage();
			}
			return null;
		}
		
	}
	public ValueBean getParentid(String cpname,String parentname,Statement stm)
	{
		ResultSet rs=null;
		try
		{
			GetFormTableName getFormTableName=new GetFormTableName();
			String RemedyTgroup=getFormTableName.GetFormName("RemedyTgroup");
			String strsql="select * from "+ RemedyTgroup +" where C630000018='"+ String.valueOf(parentname).trim() +"' and (C630000026='0' or C630000026='"+cpname+"')";
			rs=stm.executeQuery(strsql);
			ValueBean value	= new ValueBean();
			System.out.println(strsql);
			if(rs.next())
			{
				System.out.println(rs.getString("C1"));
				System.out.println(rs.getString("C630000037"));
				System.out.println(rs.getString("C630000019"));
				value.setParentid(rs.getString("C1"));
				value.setDnid(rs.getString("C630000037"));
				value.setDnname(rs.getString("C630000019"));
			}
			
			rs.close();
			return value;
		}
		catch(Exception e)
		{
			e.getMessage();
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
			}
			catch(Exception ee)
			{
				ee.getMessage();
			}
			
			return null;
		}
	}
	
	public ValueBean getdepartid(String cpname,String parentname,Statement stm)
	{
		ResultSet rs=null;
		try
		{
			GetFormTableName getFormTableName=new GetFormTableName();
			String RemedyTgroup=getFormTableName.GetFormName("RemedyTgroup");
			String strsql="select * from "+ RemedyTgroup +" where C630000018='"+ String.valueOf(parentname).trim() +"' and C630000026='"+cpname+"'";
			rs=stm.executeQuery(strsql);
			ValueBean value	= new ValueBean();
			System.out.println(strsql);
			if(rs.next())
			{
				System.out.println(rs.getString("C1"));
				System.out.println(rs.getString("C630000037"));
				System.out.println(rs.getString("C630000019"));
				value.setParentid(rs.getString("C1"));
				value.setDnid(rs.getString("C630000037"));
				value.setDnname(rs.getString("C630000019"));
			}
			
			rs.close();
			return value;
		}
		catch(Exception e)
		{
			e.getMessage();
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
			}
			catch(Exception ee)
			{
				ee.getMessage();
			}
			
			return null;
		}
	}
	public String getValue(HSSFCell cell)
	{
		try
		{
		String value	= "";
		if(cell!=null)
		{
			if(cell.getCellType()==cell.CELL_TYPE_NUMERIC)
			{
				value	= String.valueOf((long)cell.getNumericCellValue());
				value	= String.valueOf(value).trim();
			}
			if(cell.getCellType()==cell.CELL_TYPE_STRING)
			{
				value	= cell.getStringCellValue();
				value	= String.valueOf(value).trim();
			}
		}
		else
		{
			value="";
		}
		return value;
		}
		catch(Exception e)
		{
			e.getMessage();
			return "";
		}
	}
	
}