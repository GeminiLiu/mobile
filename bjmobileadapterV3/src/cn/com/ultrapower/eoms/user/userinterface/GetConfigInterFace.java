package cn.com.ultrapower.eoms.user.userinterface;

import java.util.List;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.userinterface.bean.ConfigKPI;
import cn.com.ultrapower.eoms.user.userinterface.bean.ConfigKPIFlag;
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
import cn.com.ultrapower.eoms.user.userinterface.cm.ConfigInfo;

public class GetConfigInterFace {

	/**
	 * date 2007-1-17
	 * author shigang
	 * @param args
	 * @return void
	 */
    //专业配置表
	public List GetSysBaseItems(SysBaseItems sysBaseItems,SysBaseItemsFlag sysBaseItemsFlag){
		ConfigInfo ConfigInfo	=	new ConfigInfo();
		List SBI=ConfigInfo.ConfigSysBaseItems(sysBaseItems,sysBaseItemsFlag);
		return SBI;
	}
    //基站配置表
	public List GetSysBTSInfo(SysBTSInfo sysBTSInfo,SysBTSInfoFlag sysBTSInfoFlag){
		ConfigInfo ConfigInfo	=	new ConfigInfo();
		List SBI=ConfigInfo.Config_SysBTSInfo(sysBTSInfo,sysBTSInfoFlag);
		return SBI;
	}
	//硬件类型
	public List GetHardWare(HardWareType hardWareType,HardWareTypeFlag hardWareTypeFlag){
		ConfigInfo ConfigInfo	=	new ConfigInfo();
		List HWT=ConfigInfo.Config_HardWare(hardWareType,hardWareTypeFlag);
		return HWT;
	}
	//软件版本
	public List GetSoftWare(SoftWare softWare,SoftWareFalg softWareFlag){
		ConfigInfo ConfigInfo	=	new ConfigInfo();
		List SW=ConfigInfo.Config_SoftWare(softWare,softWareFlag);
		return SW;
	}
	//网元类型
	public List GetNetElement(NetElement netElement,NetElementFlag netElementFlag){
		ConfigInfo ConfigInfo	=	new ConfigInfo();
		List GNE=ConfigInfo.Config_NetElement(netElement,netElementFlag);
		return GNE;
	}
	//设备厂家
	public List GetSourceType(SourceType sourceType,SourceTypeFlag sourceTypeFlag){
		ConfigInfo ConfigInfo	=	new ConfigInfo();
		List STF=ConfigInfo.Config_SourceType(sourceType,sourceTypeFlag);
		return STF;
	}
	//KPI指标
	public List GetKPI(ConfigKPI configKPI,ConfigKPIFlag configKPIFlag){
		ConfigInfo ConfigInfo	=	new ConfigInfo();
		List CK=ConfigInfo.Config_KPI(configKPI,configKPIFlag);
		return CK;
	}
	public static void main(String[] args) {
		SysBaseItems SysBaseItems=new SysBaseItems();//传进SysBaseItems的字段条件
		SysBaseItemsFlag SysBaseItemsFlag=new SysBaseItemsFlag();//要显示的字段
		SysBaseItemsFlag.setBase_Item(true);//要示设的字段设为真
		SysBaseItemsFlag.setBase_Item_1(true);
		SysBaseItemsFlag.setBase_Item_2(true);
		SysBaseItemsFlag.setBase_Item_3(true);
		SysBaseItems.setBase_Item_2("1");//设当前字段 where 条件
		GetConfigInterFace GetConfigInterFace=new GetConfigInterFace();
		GetConfigInterFace.GetSysBaseItems(SysBaseItems,SysBaseItemsFlag);
	}
}
