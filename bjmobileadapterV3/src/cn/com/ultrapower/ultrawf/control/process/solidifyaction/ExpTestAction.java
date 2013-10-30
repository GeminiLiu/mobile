package cn.com.ultrapower.ultrawf.control.process.solidifyaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.share.constants.ConstantsManager;
import cn.com.ultrapower.system.sqlquery.startup.Startup;
import cn.com.ultrapower.system.util.FormatTime;

public class ExpTestAction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String m_FilePath = System.getProperty("user.dir") + File.separator	+ "WebRoot"	+ File.separator + "WEB-INF";
		ConstantsManager m_ConstantsManager = new ConstantsManager(m_FilePath);
		m_ConstantsManager.getConstantInstance();	
		
	    Startup startup=new Startup();
	    startup.init(System.getProperty("user.dir") + File.separator	+ "WebRoot");		
		
	    boolean istrue_new 	= false;
	    boolean istrue_send = false;
	    boolean istrue_finish = false;
//	    
	    //新建工单的列子
		List<BaseFieldInfo> BaseNewFields = new ArrayList<BaseFieldInfo>();
		BaseNewFields.add(new BaseFieldInfo("BaseSummary","700000011","接口测试" + FormatTime.FormatLongDateToString(FormatTime.getCurrentDate()),4));
		BaseNewFields.add(new BaseFieldInfo("BaseItems","700000014","业务平台.彩铃平台.彩铃平台",4));
		BaseNewFields.add(new BaseFieldInfo("BasePriority","700000015","一般",4));
		BaseNewFields.add(new BaseFieldInfo("BaseDescrption","700000019","哈哈哈！！！！！",4));
		BaseNewFields.add(new BaseFieldInfo("01","800020026","0",4));
		BaseNewFields.add(new BaseFieldInfo("02","800020022","0",4));
		BaseNewFields.add(new BaseFieldInfo("03","800020021","0",4));
		BaseNewFields.add(new BaseFieldInfo("04","800020020","0",4));
		BaseNewFields.add(new BaseFieldInfo("05","800020017","0",4));
		BaseNewFields.add(new BaseFieldInfo("06","800020015","0",4));
		BaseNewFields.add(new BaseFieldInfo("07","800020013","0",4));
		BaseNewFields.add(new BaseFieldInfo("08","800020012","0",4));
		BaseNewFields.add(new BaseFieldInfo("09","800020003","0",4));
		BaseNewFields.add(new BaseFieldInfo("10","800020002","0",4));
		BaseNewFields.add(new BaseFieldInfo("11","800020001","0",4));
		BaseNewFields.add(new BaseFieldInfo("12","700000018","0",4));
		BaseNewFields.add(new BaseFieldInfo("13","700000017","0",4));
		Action_New ActionOp = new Action_New();
		
		istrue_new = ActionOp.do_Action("Demo","WF:EL_TTM_TTH",null,BaseNewFields,null);
		if (istrue_new == false)
		{
			System.out.println("新建失败！");
		}
		else
		{
			System.out.println("新建成功，工单ＩＤ为：！" + ActionOp.getM_BaseID());
		}
		if (istrue_new)
		{
			Action_Close m_Action_Close = new Action_Close();
			boolean d =  m_Action_Close.do_Action(
					"Demo", 
					"WF:EL_TTM_TTH", 
					ActionOp.getM_BaseID(), 
					null,
					null,
					new ArrayList<BaseFieldInfo>(),
					null
					);
			if (d == false)
			{
				System.out.println("归档失败！");
			}
			else
			{
				System.out.println("归档成功，工单ＩＤ为：！" + ActionOp.getM_BaseID());
			}
		}
		//固定流程客户化动作（有匹配角色的）的列子
//		if (istrue_new)
//		{
//			List<BaseFieldInfo> BaseExpFields_1 = new ArrayList<BaseFieldInfo>();
//			//这个字段是需要在流程图中显示的操作说明，请一定要填写；
//			BaseExpFields_1.add(new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200","接口测试-新建派发" + FormatTime.FormatLongDateToString(FormatTime.getCurrentDate()),4));
//			
//			//下面是客户化操作需要填写的字段
//			BaseExpFields_1.add(new BaseFieldInfo("tmp_INC_T0DealDesc","800045501","接口测试-新建派发" + FormatTime.FormatLongDateToString(FormatTime.getCurrentDate()),4));
//			Action_NextCustomHavaMatchRole m_Action_NextCustomHavaMatchRole = new Action_NextCustomHavaMatchRole();		
//			istrue_send = m_Action_NextCustomHavaMatchRole.do_Action(
//					"Demo", 
//					"WF:EL_TTM_TTH", 
//					ActionOp.getM_BaseID(), 
//					null, 
//					null, 
//					"新建派发", 
//					"dp_2",
//					new BaseFieldInfo("tmp_INC_T0_ToUserID","800045504","",4),
//					new BaseFieldInfo("tmp_INC_T0_ToUser","800045503","",4),
//					new BaseFieldInfo("tmp_INC_T0_ToGroupID","800045506","",4),
//					new BaseFieldInfo("tmp_INC_T0_ToGroup","800045505","",4),
//					BaseExpFields_1, 
//					null);
//			if (istrue_send == false)
//			{
//				System.out.println("新建派发失败！");
//			}
//			else
//			{
//				System.out.println("新建派发成功，工单ＩＤ为：！" + ActionOp.getM_BaseID());
//			}
//		}
	    
//		if (istrue_send)
//		{
//			List<BaseFieldInfo> BaseExpFields_2 = new ArrayList<BaseFieldInfo>();
//			//这个字段是需要在流程图中显示的操作说明，请一定要填写；
//			BaseExpFields_2.add(new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200","接口测试-T1完成" + FormatTime.FormatLongDateToString(FormatTime.getCurrentDate()),4));
//			
//			//下面是客户化操作需要填写的字段
//			BaseExpFields_2.add(new BaseFieldInfo("tmp_INC_DealResult","800045001","完成1",4));
//			BaseExpFields_2.add(new BaseFieldInfo("INC_DealResult","800030001","完成2",4));
//			BaseExpFields_2.add(new BaseFieldInfo("INC_T1Result","800021001","完成处理",4));
//			
//			Action_NextBaseCustom m_Action_NextBaseCustom = new Action_NextBaseCustom();		
//			istrue_finish = m_Action_NextBaseCustom.do_Action(
//					"Demo", 
//					"WF:EL_TTM_TTH", 
//					ActionOp.getM_BaseID(), 
//					null, 
//					null, 
//					"完成处理", 
//					BaseExpFields_2, 
//					null);
//			if (istrue_finish == false)
//			{
//				System.out.println("完成处理失败！");
//			}
//			else
//			{
//				System.out.println("完成处理成功，工单ＩＤ为：！" + ActionOp.getM_BaseID());
//			}			
//		}
		
//		if (istrue_finish)
//		{
//			List<BaseFieldInfo> BaseExpFields_2 = new ArrayList<BaseFieldInfo>();
//			//这个字段是需要在流程图中显示的操作说明，请一定要填写；
//			BaseExpFields_2.add(new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200","接口测试-归档" + FormatTime.FormatLongDateToString(FormatTime.getCurrentDate()),4));
//			
//			//下面是客户化操作需要填写的字段
//			BaseExpFields_2.add(new BaseFieldInfo("tmp_P_OpNext_Close_CloseOpSatisfaction","800045404","满意",4));
//			BaseExpFields_2.add(new BaseFieldInfo("tmp_INC_EndDealDesc","800045403","归档说明",4));
//			
//			//用于归档环节操作时的流程走向的判断字段
//			BaseExpFields_2.add(new BaseFieldInfo("INC_CloseAuditingDealResult","800021003","归档工单",4));
//			
//			Action_NextBaseCustom m_Action_NextBaseCustom = new Action_NextBaseCustom();		
//			istrue_finish = m_Action_NextBaseCustom.do_Action(
//					"Demo", 
//					"WF:EL_TTM_TTH", 
//					ActionOp.getM_BaseID(), 
//					null, 
//					null, 
//					"归档工单", 
//					BaseExpFields_2, 
//					null);
//			if (istrue_finish == false)
//			{
//				System.out.println("归档工单失败！");
//			}
//			else
//			{
//				System.out.println("归档工单成功，工单ＩＤ为：！" + ActionOp.getM_BaseID());
//			}		
//		}
	}

}
