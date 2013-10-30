package cn.com.ultrapower.eoms.user.inputdata;

import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import cn.com.ultrapower.eoms.user.comm.function.*;
import cn.com.ultrapower.eoms.user.database.*;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata.Group;
import cn.com.ultrapower.eoms.user.rolemanage.group.bean.*;
import cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata.People;
import cn.com.ultrapower.eoms.user.rolemanage.people.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.config.menu.bean.GrandActionConfigBean;
import cn.com.ultrapower.eoms.user.config.menu.aroperationdata. GrandActionInterface;

public class InputAction
{

	public static void main(String[] args)
	{
		try
		{
			InputAction inputdata=new InputAction();
			inputdata.inputaction();
		}catch(Exception e)
		{
			
		}
	}
	public void inputaction()
	{
		Statement stm		= null;
		String strsql		= "";
		ResultSet rs		= null;
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		try
		{
			//获得数据库查询结果集
			strsql="select t1.source_id from sourceconfig t1,sourceconfig t2 where t1.source_module=t2.source_id and t2.source_name='MYDESKTOP'";
			stm	= dataBase.GetStatement();
			System.out.println(strsql);
			rs  = stm.executeQuery(strsql);
			
			List list = new ArrayList();
			while(rs.next())
			{
				list=associatebean(String.valueOf(rs.getString("source_id")),list);
			}
			rs.close();
			System.out.println("dddd");
			insertdata(list);
		}
		catch(Exception e)
		{
			e.getMessage();
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		
	}
	public List associatebean(String sourceid,List list)
	{
		try
		{
			//新增 1
			//删除 2
			//交班 3
			//接班 4
			//应用 5
			//排班 6（其实导航条上没有这个权限）

			GrandActionConfigBean grandinfo = new GrandActionConfigBean();
			grandinfo.setDropDownConf_FieldID(sourceid);  //资源ID
			grandinfo.setDropDownConf_FieldValue("管理员管理");  //动作中文名称
			grandinfo.setDropDownConf_NumValue("1000");     //动作中文名对应的值
			grandinfo.setDropDownConf_OrderBy("1000");	//排序值
			
			GrandActionConfigBean grandinfo1 = new GrandActionConfigBean();
			grandinfo1.setDropDownConf_FieldID(sourceid);  //资源ID
			grandinfo1.setDropDownConf_FieldValue("删除");  //动作中文名称
			grandinfo1.setDropDownConf_NumValue("2");     //动作中文名对应的值
			grandinfo1.setDropDownConf_OrderBy("999");	//排序值
			
			GrandActionConfigBean grandinfo2 = new GrandActionConfigBean();
			grandinfo2.setDropDownConf_FieldID(sourceid);  //资源ID
			grandinfo2.setDropDownConf_FieldValue("交班");  //动作中文名称
			grandinfo2.setDropDownConf_NumValue("3");     //动作中文名对应的值
			grandinfo2.setDropDownConf_OrderBy("998");	//排序值
			
			GrandActionConfigBean grandinfo3 = new GrandActionConfigBean();
			grandinfo3.setDropDownConf_FieldID(sourceid);  //资源ID
			grandinfo3.setDropDownConf_FieldValue("接班");  //动作中文名称
			grandinfo3.setDropDownConf_NumValue("4");     //动作中文名对应的值
			grandinfo3.setDropDownConf_OrderBy("997");	//排序值
			
			GrandActionConfigBean grandinfo4 = new GrandActionConfigBean();
			grandinfo4.setDropDownConf_FieldID(sourceid);  //资源ID
			grandinfo4.setDropDownConf_FieldValue("应用");  //动作中文名称
			grandinfo4.setDropDownConf_NumValue("5");     //动作中文名对应的值
			grandinfo4.setDropDownConf_OrderBy("996");	//排序值
			
			GrandActionConfigBean grandinfo5 = new GrandActionConfigBean();
			grandinfo5.setDropDownConf_FieldID(sourceid);  //资源ID
			grandinfo5.setDropDownConf_FieldValue("排班");  //动作中文名称
			grandinfo5.setDropDownConf_NumValue("6");     //动作中文名对应的值
			grandinfo5.setDropDownConf_OrderBy("995");	//排序值
			
			list.add(grandinfo);
			list.add(grandinfo1);
			//list.add(grandinfo2);
			//list.add(grandinfo3);
			//list.add(grandinfo4);
			//list.add(grandinfo5);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return list;
	}
	public void insertdata(List list)
	{
		try
		{
			GrandActionInterface grandinterface = new GrandActionInterface();
			boolean bl = grandinterface.insertGrandActionInterface(list);
			if(bl)
			{
				System.out.println("OK");
			}
			else
			{
				System.out.println("Sorry");
			}
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}
}