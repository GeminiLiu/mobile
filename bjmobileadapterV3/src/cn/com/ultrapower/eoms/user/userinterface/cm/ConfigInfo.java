package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.ConfigKPI;
import cn.com.ultrapower.eoms.user.userinterface.bean.ConfigKPIFlag;
import cn.com.ultrapower.eoms.user.userinterface.bean.GroupAndUserInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.HardWareType;
import cn.com.ultrapower.eoms.user.userinterface.bean.HardWareTypeFlag;
import cn.com.ultrapower.eoms.user.userinterface.bean.NetElement;
import cn.com.ultrapower.eoms.user.userinterface.bean.NetElementFlag;
import cn.com.ultrapower.eoms.user.userinterface.bean.SoftWare;
import cn.com.ultrapower.eoms.user.userinterface.bean.SoftWareFalg;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceType;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceTypeFlag;
import cn.com.ultrapower.eoms.user.userinterface.bean.SysBTSInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SysBTSInfoFlag;
import cn.com.ultrapower.eoms.user.userinterface.bean.SysBaseItems;
import cn.com.ultrapower.eoms.user.userinterface.bean.SysBaseItemsFlag;

public class ConfigInfo {

	/**
	 * date 2007-1-17
	 * author shigang
	 * @param args
	 * @return void
	 */
	public String Config_SysBaseItemsSql(SysBaseItems sysBaseItems,SysBaseItemsFlag sysBaseItemsFlag){
		
		StringBuffer sqlwhere 	=	new StringBuffer();
		StringBuffer sql		=	new StringBuffer();
		StringBuffer sqlselect	=	new StringBuffer();
		//sqlselect=null;
		if (sysBaseItems.getBase_Item_1()!=null&&!sysBaseItems.getBase_Item_1().equals("")){
			sqlwhere.append(" and c650000001='"+sysBaseItems.getBase_Item_1()+"'");
		}

		
		GetFormTableName getFormTableName	= new GetFormTableName();
		String  TsysBaseItems				= getFormTableName.GetFormName("RemedyTSysBaseItems");
		sql.append("select distinct");
		
		if (sysBaseItemsFlag.isBase_Item_1()==true){
			if (sqlselect.length()==0){
				sqlselect.append(" t.c650000001");
			}else{
				sqlselect.append(" ,t.c650000001");
			}
		}
		sql.append(sqlselect.toString());
		sql.append(" from "+TsysBaseItems+" t");
		sql.append(" where 1=1").append(sqlwhere.toString());
		sql.append(" order by c650000001");
		
		return sql.toString();
		
	}
	public List ConfigSysBaseItems(SysBaseItems sysBaseItems,SysBaseItemsFlag sysBaseItemsFlag){
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		String Base_Item_1 			= "";
//		二级专业
		String Base_Item_2 			= "";
//		三级专业
		String Base_Item_3 			= "";
//		专业id
		String Base_Item_ID 		= "";
//		专业全称
		String Base_Item 			= "";
//		专业代码
		String Base_Item_Code		= "";
//		状态
		String Base_Item_State		= "";
		try
		{
			ConfigInfo ConfigInfo	=	new ConfigInfo();
			ArrayList BaseItemsList =   new ArrayList();
			String 	   Sql			=	ConfigInfo.Config_SysBaseItemsSql(sysBaseItems,sysBaseItemsFlag);
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,Sql);
			while(rs.next())
			{
				if (sysBaseItemsFlag.isBase_Item_1()==true){
					Base_Item_1		= rs.getString("c650000001");
				}
				
				
				
				SysBaseItems SysBaseItems = new SysBaseItems();
				SysBaseItems.setBase_Item_1(Base_Item_1);
				
				BaseItemsList.add(SysBaseItems);
			}
			return BaseItemsList;
		}
		catch(Exception e)
		{
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	//基站配置表
	public String Config_SysBTSInfoSql(SysBTSInfo sysBTSInfo,SysBTSInfoFlag sysBTSInfoFlag){
		
		StringBuffer sqlwhere 	=	new StringBuffer();
		StringBuffer sql		=	new StringBuffer();
		StringBuffer sqlselect	= 	new StringBuffer();
		if (sysBTSInfo.getBTSCode()!=null&&!sysBTSInfo.getBTSCode().equals("")){
			sqlwhere.append(" and c802800001='"+sysBTSInfo.getBTSCode()+"'");
		}
		if (sysBTSInfo.getBTSName()!=null&&!sysBTSInfo.getBTSName().equals("")){
			sqlwhere.append(" and c802800002='"+sysBTSInfo.getBTSName()+"'");
		}
		if (sysBTSInfo.getBTSLevel()!=null&&!sysBTSInfo.getBTSLevel().equals("")){
			sqlwhere.append(" and c802800003='"+sysBTSInfo.getBTSLevel()+"'");
		}
		if (sysBTSInfo.getBTSAreaNO()!=null&&!sysBTSInfo.getBTSAreaNO().equals("")){
			sqlwhere.append(" and c802800009='"+sysBTSInfo.getBTSAreaNO()+"'");
		}
		if (sysBTSInfo.getBTSIsELectricControl()!=null&&!sysBTSInfo.getBTSIsELectricControl().equals("")){
			sqlwhere.append(" and c802800007='"+sysBTSInfo.getBTSIsELectricControl());
		}
		if (sysBTSInfo.getBTSState()!=null&&!sysBTSInfo.getBTSState().equals("")){
			sqlwhere.append(" and c802800010='"+sysBTSInfo.getBTSState()+"'");
		}
		if (sysBTSInfo.getBTSTown()!=null&&!sysBTSInfo.getBTSTown().equals("")){
			sqlwhere.append(" and c802800004='"+sysBTSInfo.getBTSTown()+"'");
		}
		if (sysBTSInfo.getBTSNetWorkElement()!=null&&!sysBTSInfo.getBTSNetWorkElement().equals("")){
			sqlwhere.append(" and c802800005='"+sysBTSInfo.getBTSNetWorkElement()+"'");
		}
		if (sysBTSInfo.getBTSConfig()!=null&&!sysBTSInfo.getBTSConfig().equals("")){
			sqlwhere.append(" and c802800006='"+sysBTSInfo.getBTSConfig()+"'");
		}
		if (sysBTSInfo.getBTSDescription()!=null&&!sysBTSInfo.getBTSDescription().equals("")){
			sqlwhere.append(" and c802800008='"+sysBTSInfo.getBTSDescription()+"'");
		}
		
		
		GetFormTableName getFormTableName	= new GetFormTableName();
		String  TSysBTSInfo					= getFormTableName.GetFormName("RemedyTSysBTSInfo");
		sql.append("select distinct ");
		String strorderby	= "";
		if (sysBTSInfoFlag.isBTSTown()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c802800004");
				strorderby	= " order by t.c802800004";
			}else{
				sqlselect.append(" ,t.c802800004");
				strorderby	= strorderby+",t.c802800004";
			}
		}
		if (sysBTSInfoFlag.isBTSCode()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c802800001");
				strorderby	= " order by t.c802800001";
			}else{
				sqlselect.append(" ,t.c802800001");
				strorderby	= strorderby+",t.c802800001";
			}
		}
		if (sysBTSInfoFlag.isBTSName()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c802800002");
			}else{
				sqlselect.append(" ,t.c802800002");
			}
		}
		if (sysBTSInfoFlag.isBTSLevel()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c802800003");
			}else{
				sqlselect.append(" ,t.c802800003");
			}
		}
		if (sysBTSInfoFlag.isBTSAreaNO()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c802800009");
			}else{
				sqlselect.append(" ,t.c802800009");
			}
		}
		if (sysBTSInfoFlag.isBTSIsELectricControl()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c802800007");
			}else{
				sqlselect.append(" ,t.c802800007");
			}
		}
		if (sysBTSInfoFlag.isBTSState()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c802800010");
			}else{
				sqlselect.append(" ,t.c802800010");
			}
		}
		
		if (sysBTSInfoFlag.isBTSNetWorkElement()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c802800005");
			}else{
				sqlselect.append(" ,t.c802800005");
			}
		}
		if (sysBTSInfoFlag.isBTSConfig()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c802800006");
			}else{
				sqlselect.append(" ,t.c802800006");
			}
		}
		if (sysBTSInfoFlag.isBTSDescription()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c802800008");
			}else{
				sqlselect.append(" ,t.c802800008");
			}
		}
		sql.append(sqlselect.toString());
		sql.append(" from "+TSysBTSInfo+" t");
		//sql.append("( select * from "+TSysBTSInfo+" tmpt where 1=1 "+ sqlwhere.toString() +" order by tmpt.c802800004,tmpt.c802800001) t");
		sql.append(" where 1=1").append(sqlwhere.toString());
		sql.append(" "+strorderby);
		System.out.println("获得btsSQL语句"+sql.toString());
		return sql.toString();
	}
	public List Config_SysBTSInfo(SysBTSInfo sysBTSInfo,SysBTSInfoFlag sysBTSInfoFlag){
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;

		String BTSCode 						= "";
		String BTSName 						= "";
		String BTSLevel 					= "";
		String BTSAreaNO 					= "";
		String BTSIsELectricControl 		= "";
		String BTSState 					= "";
		String BTSTown 						= "";
		String BTSNetWorkElement 			= "";
		String BTSConfig 					= "";
		String BTSDescription 				= "";

		  
		try
		{
			ConfigInfo ConfigInfo	=	new ConfigInfo();
			ArrayList  BTSInfoList	=	new ArrayList();
			String 	   Sql			=	ConfigInfo.Config_SysBTSInfoSql(sysBTSInfo,sysBTSInfoFlag);
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,Sql);
			
			while(rs.next())
			{
				if (sysBTSInfoFlag.isBTSCode()==true){
					BTSCode						= rs.getString("c802800001");
				}
				if (sysBTSInfoFlag.isBTSName()==true){
					BTSName						= rs.getString("c802800002");
				}
				if (sysBTSInfoFlag.isBTSLevel()==true){
					BTSLevel					= rs.getString("c802800003");
				}
				if (sysBTSInfoFlag.isBTSAreaNO()==true){
					BTSAreaNO					= rs.getString("c802800009");
				}
				if (sysBTSInfoFlag.isBTSIsELectricControl()==true){
					BTSIsELectricControl		= rs.getString("c802800007");
				}
				if (sysBTSInfoFlag.isBTSState()==true){
					BTSState  					= rs.getString("c802800010");
				}
				if (sysBTSInfoFlag.isBTSTown()==true){
					BTSTown 					= rs.getString("c802800004");
				}
				if (sysBTSInfoFlag.isBTSNetWorkElement()==true){
					BTSNetWorkElement 			= rs.getString("c802800005");
				}
				if (sysBTSInfoFlag.isBTSConfig()==true){
					BTSConfig	 				= rs.getString("c802800006");
				}
				if (sysBTSInfoFlag.isBTSDescription()==true){
					BTSDescription	 			= rs.getString("c802800008");
				}
				SysBTSInfo SysBTSInfo = new SysBTSInfo();
				SysBTSInfo.setBTSCode(BTSCode);
				SysBTSInfo.setBTSName(BTSName);
				SysBTSInfo.setBTSLevel(BTSLevel);
				SysBTSInfo.setBTSIsELectricControl(BTSIsELectricControl);
				SysBTSInfo.setBTSState(BTSState);
				SysBTSInfo.setBTSTown(BTSTown);
				SysBTSInfo.setBTSNetWorkElement(BTSNetWorkElement);
				SysBTSInfo.setBTSConfig(BTSConfig);
				SysBTSInfo.setBTSDescription(BTSDescription);
				SysBTSInfo.setBTSAreaNO(BTSAreaNO);
				BTSInfoList.add(SysBTSInfo);
				
			}
			return BTSInfoList;
		}
		catch(Exception e)
		{
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
//	硬件类型
	public String Config_HardWareSql(HardWareType hardWareType,HardWareTypeFlag hardWareTypeFlag){
		
		StringBuffer sqlwhere 	=	new StringBuffer();
		StringBuffer sql		=	new StringBuffer();
		StringBuffer sqlselect	= 	new StringBuffer();
		if (hardWareType.getConfig_Speciality()!=null&&!hardWareType.getConfig_Speciality().equals("")){
			sqlwhere.append(" and c680000600='"+hardWareType.getConfig_Speciality()+"'");
		}
		if (hardWareType.getConfig_NetElement()!=null&&!hardWareType.getConfig_NetElement().equals("")){
			sqlwhere.append(" and c680000601='"+hardWareType.getConfig_NetElement()+"'");
		}
		if (hardWareType.getConfig_EquipCompany()!=null&&!hardWareType.getConfig_EquipCompany().equals("")){
			sqlwhere.append(" and c680000602='"+hardWareType.getConfig_EquipCompany()+"'");
		}
		if (hardWareType.getConfig_EquipType1()!=null&&!hardWareType.getConfig_EquipType1().equals("")){
			sqlwhere.append(" and c680000603='"+hardWareType.getConfig_EquipType1()+"'");
		}
		if (hardWareType.getConfig_EquipType2()!=null&&!hardWareType.getConfig_EquipType2().equals("")){
			sqlwhere.append(" and c680000604='"+hardWareType.getConfig_EquipType2()+"'");
		}
		if (hardWareType.getConfig_EquipType3()!=null&&!hardWareType.getConfig_EquipType3().equals("")){
			sqlwhere.append(" and c680000605='"+hardWareType.getConfig_EquipType3()+"'");
		}
		if (hardWareType.getConfig_EquipType4()!=null&&!hardWareType.getConfig_EquipType4().equals("")){
			sqlwhere.append(" and c680000606='"+hardWareType.getConfig_EquipType4()+"'");
		}
		
		GetFormTableName getFormTableName	= new GetFormTableName();
		String  THardWare					= getFormTableName.GetFormName("RemedyTHardWare");
		
		if (hardWareTypeFlag.isConfig_Speciality()==true){
			if (sqlselect.length()==0){
				sqlselect.append(" t.c680000600");
			}else{
				sqlselect.append(" ,t.c680000600");
			}	
		}
		
		if (hardWareTypeFlag.isConfig_NetElement()==true){
			if(sqlselect.length()==0){
			sqlselect.append(" t.c680000601");
			}else{
				sqlselect.append(" ,t.c680000601");
			}
		}
		if (hardWareTypeFlag.isConfig_EquipCompany()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000602");
			}else{
				sqlselect.append(" ,t.c680000602");
			}
		}
		if (hardWareTypeFlag.isConfig_EquipType1()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000603");
			}else{
				sqlselect.append(" ,t.c680000603");
			}
		}
		if (hardWareTypeFlag.isConfig_EquipType2()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000604");
			}else{
				sqlselect.append(" ,t.c680000604");
			}
		}
		if (hardWareTypeFlag.isConfig_EquipType3()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000605");
			}else{
				sqlselect.append(" ,t.c680000605");
			}
		}
		if (hardWareTypeFlag.isConfig_EquipType4()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000606");
			}else{
				sqlselect.append(" ,t.c680000606");
			}
		}
		sql.append("select distinct");
		sql.append(sqlselect.toString());
		sql.append(" from "+THardWare+" t");
		sql.append(" where 1=1").append(sqlwhere.toString());
		
		return sql.toString();
		
	}
	public List Config_HardWare(HardWareType hardWareType,HardWareTypeFlag hardWareTypeFlag){
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;

	  String Config_Speciality 	= "";
	  String Config_NetElement 	= "";
	  String Config_EquipCompany= "";
	  String Config_EquipType1 	= "";
	  String Config_EquipType2 	= "";
	  String Config_EquipType3 	= "";
	  String Config_EquipType4 	= "";

		  
		try
		{
			ConfigInfo ConfigInfo		=	new ConfigInfo();
			ArrayList  hardWareTypeList =	new ArrayList();
			String 	   Sql				=	ConfigInfo.Config_HardWareSql(hardWareType,hardWareTypeFlag);
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,Sql);
			while(rs.next())
			{
				if (hardWareTypeFlag.isConfig_Speciality()==true){
					Config_Speciality					= rs.getString("c680000600");
				}
				if (hardWareTypeFlag.isConfig_NetElement()==true){
					Config_NetElement					= rs.getString("c680000601");
				}
				if (hardWareTypeFlag.isConfig_EquipCompany()==true){
					Config_EquipCompany					= rs.getString("c680000602");
				}
				if (hardWareTypeFlag.isConfig_EquipType1()==true){
					Config_EquipType1					= rs.getString("c680000603");
				}
				if (hardWareTypeFlag.isConfig_EquipType2()==true){
					Config_EquipType2					= rs.getString("c680000604");
				}
				if (hardWareTypeFlag.isConfig_EquipType3()==true){
					Config_EquipType3					= rs.getString("c680000605");
				}
				if (hardWareTypeFlag.isConfig_EquipType4()==true){
					Config_EquipType4					= rs.getString("c680000606");
				}
				
				HardWareType HardWareType = new HardWareType();
				HardWareType.setConfig_Speciality(Config_Speciality);
				HardWareType.setConfig_NetElement(Config_NetElement);
				HardWareType.setConfig_EquipCompany(Config_EquipCompany);
				HardWareType.setConfig_EquipType1(Config_EquipType1);
				HardWareType.setConfig_EquipType2(Config_EquipType2);
				HardWareType.setConfig_EquipType3(Config_EquipType3);
				HardWareType.setConfig_EquipType4(Config_EquipType4);
				
				hardWareTypeList.add(HardWareType);
			}
			
		
			
			return hardWareTypeList;
		}
		catch(Exception e)
		{
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
//	软件版本
	public String Config_SoftWareSql(SoftWare softWare,SoftWareFalg softWareFlag){
		
		StringBuffer sqlwhere 	=	new StringBuffer();
		StringBuffer sql		=	new StringBuffer();
		StringBuffer sqlselect	=	new StringBuffer();
	
		if (softWare.getConfig_Speciality()!=null&&!softWare.getConfig_Speciality().equals("")){
			sqlwhere.append(" and c680000400='"+softWare.getConfig_Speciality()+"'");
		}
		if (softWare.getConfig_NetElement()!=null&&!softWare.getConfig_NetElement().equals("")){
			sqlwhere.append(" and c680000401='"+softWare.getConfig_NetElement()+"'");
		}
		if (softWare.getConfig_EquipCompany()!=null&&!softWare.getConfig_EquipCompany().equals("")){
			sqlwhere.append(" and c680000402='"+softWare.getConfig_EquipCompany()+"'");
		}
		if (softWare.getConfig_SoftWare1()!=null&&!softWare.getConfig_SoftWare1().equals("")){
			sqlwhere.append(" and c680000403='"+softWare.getConfig_SoftWare1()+"'");
		}
		if (softWare.getConfig_SoftWare2()!=null&&!softWare.getConfig_SoftWare2().equals("")){
			sqlwhere.append(" and c680000404='"+softWare.getConfig_SoftWare2()+"'");
		}
		
		GetFormTableName getFormTableName		= new GetFormTableName();
		String  RemedyTSoftWare					= getFormTableName.GetFormName("RemedyTSoftWare");
		if (softWareFlag.isConfig_Speciality()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000400");
			}else{
				sqlselect.append(" ,t.c680000400");
			}
		}
		if (softWareFlag.isConfig_NetElement()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000401");
			}else{
				sqlselect.append(" ,t.c680000401");
			}
		}
		if (softWareFlag.isConfig_EquipCompany()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000402");
			}else{
				sqlselect.append(" ,t.c680000402");
			}
		}
		if (softWareFlag.isConfig_SoftWare1()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000403");
			}else{
				sqlselect.append(" ,t.c680000403");
			}
		}
		if (softWareFlag.isConfig_SoftWare2()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000404");
			}else{
				sqlselect.append(" ,t.c680000404");
			}
		}
		sql.append("select distinct");
		sql.append(sqlselect.toString());
		
		sql.append(" from "+RemedyTSoftWare+" t");
		sql.append(" where 1=1").append(sqlwhere.toString());
		
		return sql.toString();
		
	}
	public List Config_SoftWare(SoftWare softWare,SoftWareFalg softWareFlag){
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;

		 
		 String Config_Speciality 	= "";
		 String Config_NetElement 	= "";
		 String Config_EquipCompany = "";
		 String Config_SoftWare1 	= "";
		 String Config_SoftWare2 	= "";
		
		try
		{
			ConfigInfo ConfigInfo	=	new ConfigInfo();
			ArrayList  softWareList = 	new ArrayList();
			String 	   Sql			=	ConfigInfo.Config_SoftWareSql(softWare,softWareFlag);
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,Sql);
			while(rs.next())
			{
				if (softWareFlag.isConfig_Speciality()==true){
					Config_Speciality					= rs.getString("c680000400");
				}
				if (softWareFlag.isConfig_NetElement()==true){
					Config_NetElement					= rs.getString("c680000401");
				}
				if (softWareFlag.isConfig_EquipCompany()==true){
					Config_EquipCompany					= rs.getString("c680000402");
				}
				if (softWareFlag.isConfig_SoftWare1()==true){
					Config_SoftWare1					= rs.getString("c680000403");
				}
				if (softWareFlag.isConfig_SoftWare2()==true){
					Config_SoftWare2					= rs.getString("c680000404");
				}
				
				SoftWare SoftWare = new SoftWare();
				SoftWare.setConfig_Speciality(Config_Speciality);
				SoftWare.setConfig_NetElement(Config_NetElement);
				SoftWare.setConfig_EquipCompany(Config_EquipCompany);
				SoftWare.setConfig_SoftWare1(Config_SoftWare1);
				SoftWare.setConfig_SoftWare2(Config_SoftWare2);
				softWareList.add(SoftWare);
			}
			
			return softWareList;
		}
		catch(Exception e)
		{
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
//	网元类型
	public String Config_NetElementSql(NetElement netElement,NetElementFlag netElementFlag){
		
		StringBuffer sqlwhere 	=	new StringBuffer();
		StringBuffer sql		=	new StringBuffer();
		StringBuffer sqlselect=  new StringBuffer();
		
		if (netElement.getConfig_Speciality()!=null&&!netElement.getConfig_Speciality().equals("")){
			sqlwhere.append(" and c680000000='"+netElement.getConfig_Speciality()+"'");
		}
		if (netElement.getConfig_NetElement()!=null&&!netElement.getConfig_NetElement().equals("")){
			sqlwhere.append(" and c680000001='"+netElement.getConfig_NetElement()+"'");
		}
		
		GetFormTableName getFormTableName		= new GetFormTableName();
		String  RemedyTNetElement					= getFormTableName.GetFormName("RemedyTNetElement");
		
		if (netElementFlag.isConfig_Speciality()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000000");
			}else{
				sqlselect.append(" ,t.c680000000");
			}
		}
		if (netElementFlag.isConfig_NetElement()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000001");
			}else{
				sqlselect.append(" ,t.c680000001");
			}
		}
		sql.append("select distinct");
		sql.append(sqlselect.toString());
		sql.append(" from "+RemedyTNetElement+" t");
		sql.append(" where 1=1").append(sqlwhere.toString());
		
		return sql.toString();
		
	}
	public List Config_NetElement(NetElement netElement,NetElementFlag netElementFlag){
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		String Config_Speciality 	= "";
		String Config_NetElement 	= "";
		try
		{
			ConfigInfo ConfigInfo	=	new ConfigInfo();
			ArrayList  netElementList=  new ArrayList();
			String 	   Sql			=	ConfigInfo.Config_NetElementSql(netElement,netElementFlag);
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,Sql);
			while(rs.next())
			{
				if (netElementFlag.isConfig_Speciality()==true){
					Config_Speciality					= rs.getString("c680000000");
				}
				if (netElementFlag.isConfig_NetElement()==true){
					Config_NetElement					= rs.getString("c680000001");
				}
			
				NetElement NetElement = new NetElement();
				NetElement.setConfig_Speciality(Config_Speciality);
				NetElement.setConfig_NetElement(Config_NetElement);
				
				netElementList.add(NetElement);
			}
			
			
			return netElementList;
		}
		catch(Exception e)
		{
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
//	设备厂家
	public String Config_SourceTypeSql(SourceType sourceType,SourceTypeFlag sourceTypeFlag){
		
		StringBuffer sqlwhere 	=	new StringBuffer();
		StringBuffer sql		=	new StringBuffer();
		StringBuffer sqlselect  =	new StringBuffer();
	
		
		GetFormTableName getFormTableName		= new GetFormTableName();
		String  RemedyTSourceType					= getFormTableName.GetFormName("RemedyTSourceType");
		
		
		sql.append("select c650000001");
		
		sql.append(" from "+RemedyTSourceType+" t");
		sql.append(" where 1=1");
		
		sql.append(" order by c1");
		
		return sql.toString();
		
	}
	public List Config_SourceType(SourceType sourceType,SourceTypeFlag sourceTypeFlag){
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;

		String Config_SourceType 	= "";
		String Config_SourceName 	= "";
		
		try
		{
			ConfigInfo ConfigInfo	=	new ConfigInfo();
			ArrayList sourceTypeList=   new ArrayList();
			String 	   Sql			=	ConfigInfo.Config_SourceTypeSql(sourceType,sourceTypeFlag);
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,Sql);
			while(rs.next())
			{
//				if (sourceTypeFlag.isConfig_SourceType()==true){
//					Config_SourceType					= rs.getString("c680000200");
//				}
				if (sourceTypeFlag.isConfig_SourceName()==true){
					Config_SourceName					= rs.getString("c650000001");
				}
			
				SourceType SourceType = new SourceType();
//				SourceType.setConfig_SourceType(Config_SourceType);
				SourceType.setConfig_SourceName(Config_SourceName);
				sourceTypeList.add(SourceType);
			}
			
			return sourceTypeList;
		}
		catch(Exception e)
		{
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
//	KPI指标
	public String Config_KPISql(ConfigKPI kpi,ConfigKPIFlag kpiFlag){
		
		StringBuffer sqlwhere 	=	new StringBuffer();
		StringBuffer sql		=	new StringBuffer();
		StringBuffer sqlselect  =   new StringBuffer();
		if (kpi.getConfig_KPICollection()!=null&&!kpi.getConfig_KPICollection().equals("")){
			sqlwhere.append(" and c680000800='"+kpi.getConfig_KPICollection()+"'");
		}
		if (kpi.getConfig_KPISign()!=null&&!kpi.getConfig_KPISign().equals("")){
			sqlwhere.append(" and c680000801='"+kpi.getConfig_KPISign()+"'");
		}
		
		GetFormTableName getFormTableName		= new GetFormTableName();
		String  RemedyTKPI					= getFormTableName.GetFormName("RemedyTKPI");
		
		if (kpiFlag.isConfig_KPICollection()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000800");
			}else{
				sqlselect.append(" ,t.c680000800");
			}
		}
		if (kpiFlag.isConfig_KPISign()==true){
			if(sqlselect.length()==0){
				sqlselect.append(" t.c680000801");
			}else{
				sqlselect.append(" ,t.c680000801");
			}
		}
		sql.append("select distinct");
		sql.append(sqlselect.toString());
		
		sql.append(" from "+RemedyTKPI+" t");
		sql.append(" where 1=1").append(sqlwhere.toString());
		return sql.toString();
		
	}
	public List Config_KPI(ConfigKPI kpi,ConfigKPIFlag kpiFlag){
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		 
		String Config_KPICollection 	= "";
		String Config_KPISign 			= "";
		
		try
		{
			ConfigInfo ConfigInfo	=	new ConfigInfo();
			ArrayList  kpiList		=   new ArrayList();
			String 	   Sql			=	ConfigInfo.Config_KPISql(kpi,kpiFlag);
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,Sql);
			while(rs.next())
			{
				if (kpiFlag.isConfig_KPISign()==true){
//					Config_KPICollection			= rs.getString("c680000800");
					
					Config_KPISign					= rs.getString("c680000801");
				}
				if (kpiFlag.isConfig_KPICollection()==true){
//					Config_KPISign					= rs.getString("c680000801");
					Config_KPICollection			= rs.getString("c680000800");
				}
				
				ConfigKPI ConfigKPI = new ConfigKPI();
				ConfigKPI.setConfig_KPICollection(Config_KPICollection);
				ConfigKPI.setConfig_KPISign(Config_KPISign);
				kpiList.add(ConfigKPI);
			}
			
			return kpiList;
		}
		catch(Exception e)
		{
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
