package cn.com.ultrapower.eoms.util.excel;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.eoms.user.userinterface.GetConfigInterFace;
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
import cn.com.ultrapower.eoms.user.userinterface.bean.SysBaseItems;
import cn.com.ultrapower.eoms.user.userinterface.bean.SysBaseItemsFlag;

/**
 * @经验库导入的校验
 * @author wanghongbing
 * 
 */
public class ValidateField {
	//公共校验提示信息
	public final static int standardLength = 90;
	public final static int titleLength = 100;
	public final static int contentLength = 4000;
	public final static String InvalidLength = "超出规定长度";
	public final static String InvalidNull = "单元格值为空";
	public final static String InvalidEgg = "含有非法字符";
	public final static String InvalidUser = "数据库中没有此人";
	
	public final static String InvalidMajor = "不是经验库的标准专业";
	public final static String InvalidNetBulkType = "不是经验库的标准网元类型";
	public final static String InvalidInstrumentFactory = "不是经验库的标准设备厂家";
	public final static String InvalidSoftVersion = "不是经验库的标准软件版本";
	public final static String InvalidHardType = "不是故障经验库的标准硬件类型";
	public final static String InvalidKPIIndex = "不是经验库的标准KPI指标";
	
	//加载所有标准数据
	public static List majorList = new ArrayList();
	public static List netBulkTypeList = new ArrayList();
	public static List instrumentFactoryList = new ArrayList();
	public static List softVersionList = new ArrayList();
	public static List hardTypeList = new ArrayList();
	public static List kpiIndexList = new ArrayList();
//	static {
//		List list = new ArrayList();
//		GetConfigInterFace getConfigInterFace=new GetConfigInterFace();
//		//GetSysBase...:
//		//第一个参数表示查询的限制条件
//		//第二个参数表示查询的结果字段
//		//标准专业数据列表
//		SysBaseItems sysBaseItems=new SysBaseItems();
//		SysBaseItemsFlag sysBaseItemsFlag=new SysBaseItemsFlag();
//		sysBaseItemsFlag.setBase_Item(true); 
//		sysBaseItemsFlag.setBase_Item_1(true);
//		sysBaseItemsFlag.setBase_Item_2(true);
//		sysBaseItemsFlag.setBase_Item_3(true);
//		list = getConfigInterFace.GetSysBaseItems(sysBaseItems,sysBaseItemsFlag);
//		if(list!=null&&!list.isEmpty()){
//            for(int t=0;t<list.size();t++){
//		      SysBaseItems sb =(SysBaseItems)list.get(t);
//		      String sourceName = (String)sb.getBase_Item_1();
//		      majorList.add(sourceName.trim());
//            }
//		}
//		//标准网元类型数据列表
//		NetElement netElement=new NetElement();
//	    NetElementFlag netElementFlag=new NetElementFlag();
//	    netElementFlag.setConfig_NetElement(true);
//	    netElementFlag.setConfig_Speciality(true);
//		list=getConfigInterFace.GetNetElement(netElement,netElementFlag);
//		if(list!=null&&list.size()!=0){
//		    for(int t=0;t<list.size();t++){
//			      NetElement ne =(NetElement)list.get(t);
//			      String sourceName = (String)ne.getConfig_NetElement();
//			      netBulkTypeList.add(sourceName.trim());
//		    }
//		}
//		//标准设备厂家数据列表
//		SourceType sourceType = new SourceType();
//		SourceTypeFlag sourceTypeFlag = new SourceTypeFlag();
//		sourceTypeFlag.setConfig_SourceType(true);
//		sourceTypeFlag.setConfig_SourceName(true);
//		list = getConfigInterFace.GetSourceType(sourceType, sourceTypeFlag);
//		if (list != null && list.size() != 0) {
//			for (int t = 0; t < list.size(); t++) {
//				SourceType st = (SourceType) list.get(t);
//				String sourceName = (String) st.getConfig_SourceName();
//				instrumentFactoryList.add(sourceName.trim());
//			}
//		}
//		//标准软件版本数据列表
//		SoftWare SoftWare = new SoftWare();
//		SoftWareFalg SoftWareFalg = new SoftWareFalg();
//		SoftWareFalg.setConfig_NetElement(true);
//		SoftWareFalg.setConfig_Speciality(true);
//		SoftWareFalg.setConfig_SoftWare1(true);
//		SoftWareFalg.setConfig_SoftWare2(true);
//		List list4 = getConfigInterFace.GetSoftWare(SoftWare, SoftWareFalg);
//		if (list4 != null && list4.size() != 0) {
//			for (int t = 0; t < list4.size(); t++) {
//				SoftWare sw = (SoftWare) list4.get(t);
//				String softWareName = null;
//				//软件版本1
//				softWareName = (String) sw.getConfig_SoftWare1();
//				softVersionList.add(softWareName.trim());
//				//软件版本2
//				softWareName = (String) sw.getConfig_SoftWare2();
//				softVersionList.add(softWareName.trim());
//			}
//		}
//		//标准硬件类型数据列表
//		HardWareType hardWareType = new HardWareType();
//		HardWareTypeFlag hardWareTypeFlag = new HardWareTypeFlag();
//		hardWareTypeFlag.setConfig_EquipType1(true);
//		hardWareTypeFlag.setConfig_EquipType2(true);
//		hardWareTypeFlag.setConfig_EquipType3(true);
//		hardWareTypeFlag.setConfig_EquipType4(true);
//		hardWareTypeFlag.setConfig_NetElement(true);
//		list = getConfigInterFace.GetHardWare(hardWareType,hardWareTypeFlag);
//		if (list != null && list.size() != 0) {
//			for (int t = 0; t < list.size(); t++) {
//				HardWareType hw = (HardWareType) list.get(t);
//				String equipTypeName = null;
//				equipTypeName = (String) hw.getConfig_EquipType1();
//				hardTypeList.add(equipTypeName.trim());
//				equipTypeName = (String) hw.getConfig_EquipType2();
//				hardTypeList.add(equipTypeName.trim());
//				equipTypeName = (String) hw.getConfig_EquipType3();
//				hardTypeList.add(equipTypeName.trim());
//				equipTypeName = (String) hw.getConfig_EquipType4();
//				hardTypeList.add(equipTypeName.trim());
//			}
//		}
//		//标准KPI指标数据列表
//		ConfigKPI configKPI = new ConfigKPI();
//		ConfigKPIFlag configKPIFlag = new ConfigKPIFlag();
//		configKPIFlag.setConfig_KPICollection(true);
//		configKPIFlag.setConfig_KPISign(true);
//		list = getConfigInterFace.GetKPI(configKPI, configKPIFlag);;
//		if (list != null && !list.isEmpty()) {
//			for (int t = 0; t < list.size(); t++) {
//				ConfigKPI st = (ConfigKPI) list4.get(t);
//				String sourceName = null;
//				sourceName = (String) st.getConfig_KPICollection();
//				kpiIndexList.add(sourceName.trim());
//				sourceName = (String) st.getConfig_KPISign();
//				kpiIndexList.add(sourceName.trim());
//			}
//		}
//	}
	
	/**
	 * 检查长度
	 * @param str
	 * @param len
	 * @return
	 */
	public static boolean CheckLength(String str, int len){
		if (str.getBytes().length >= len){
			return false;
		}
		return true;
	}
	/**
	 * 检查是否为空
	 * @param str
	 * @return
	 */
	public static boolean CheckNull(String str){
		if (str.length()==0){
			return false;
		}
		return true;
	}
	/**
	 * 检查是否含有非法字符
	 * @param str
	 * @return
	 */
	public static boolean CheckEgg(String str){
		String strEgg = "~!@%^&*();'\"?><[]{}\\|/=+—“”‘";
		for (int i=0;i<str.length();i++){
			for (int j=0;j<strEgg.length();j++){
				if (str.charAt(i)==strEgg.charAt(j)){
					return false;
				}
			}							
		}
		return true;
	}
	/**
	 * 检查专业是否是标准的
	 * @param major
	 * @return
	 */
	public static boolean IsMajorValid(String major){
		return majorList.contains(major.trim());
	}
	/**
	 * 检查网元类型是否是标准的
	 * @param netBulkType
	 * @return
	 */
	public static boolean IsNetBulkTypeValid(String netBulkType){
		return netBulkTypeList.contains(netBulkType);
	}
	/**
	 * 检查设备厂家是否是标准的
	 * @param instrumentFactory
	 * @return
	 */
	public static boolean IsInstrumentFactoryValid(String instrumentFactory){
		return instrumentFactoryList.contains(instrumentFactory);
	}
	/**
	 * 检查软件版本是否是标准的
	 * @param softVersion
	 * @return
	 */
	public static boolean IsSoftVersionValid(String softVersion){
		return softVersionList.contains(softVersion);
	}
	/**
	 * 检查硬件类型是否是标准的
	 * @param hardType
	 * @return
	 */
	public static boolean IsHardTypeValid(String hardType){
		return hardTypeList.contains(hardType);
	}
	/**
	 * 检查KPI指标是否是标准的
	 * @param kpiIndex
	 * @return
	 */
	public static boolean IsKPIIndexValid(String kpiIndex){
		return kpiIndexList.contains(kpiIndex);
	}
}
