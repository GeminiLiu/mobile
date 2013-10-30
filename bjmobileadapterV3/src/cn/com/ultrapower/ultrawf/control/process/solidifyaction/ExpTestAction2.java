package cn.com.ultrapower.ultrawf.control.process.solidifyaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.share.constants.ConstantsManager;
import cn.com.ultrapower.system.sqlquery.startup.Startup;
import cn.com.ultrapower.system.util.FormatTime;

public class ExpTestAction2 {

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
		BaseNewFields.add(new BaseFieldInfo("CCH_ComplainType","800020001","互联互通业务.互联互通业务.互联彩信",4));
		BaseNewFields.add(new BaseFieldInfo("BaseAcceptOutTime","700000017","11111111111111111",7));
		BaseNewFields.add(new BaseFieldInfo("BaseDealOutTime","700000018","11111111111111111",7));
		BaseNewFields.add(new BaseFieldInfo("BasePriority","700000015","一般",4));
		BaseNewFields.add(new BaseFieldInfo("CCH_ComplainTime","800020002","11111111111111111",7));
		BaseNewFields.add(new BaseFieldInfo("CCH_LinkMan","800020016","黄维",4));
		BaseNewFields.add(new BaseFieldInfo("CCH_LinkManTel","800020017","13900001111",4));
		BaseNewFields.add(new BaseFieldInfo("CCH_ComplainDesc","800020014","0",4));
		BaseNewFields.add(new BaseFieldInfo("CCH_IncidentDesc","800020020","0",4));

		Action_New ActionOp = new Action_New();
		
		istrue_new = ActionOp.do_Action("Demo","WF:EL_TTM_CCH",null,BaseNewFields,null);
		if (istrue_new == false)
		{
			System.out.println("新建失败！");
		}
		else
		{
			System.out.println("新建成功，工单ＩＤ为：！" + ActionOp.getM_BaseID());
		}

//		固定流程客户化动作（有匹配角色的）的列子
		if (istrue_new)
		{
			List<BaseFieldInfo> BaseExpFields_1 = new ArrayList<BaseFieldInfo>();
			//这个字段是需要在流程图中显示的操作说明，请一定要填写；
			BaseExpFields_1.add(new BaseFieldInfo("tmp_BaseUser_P_OpDealNext_Desc","700038200","接口测试-新建派发" + FormatTime.FormatLongDateToString(FormatTime.getCurrentDate()),4));
			
			//下面是客户化操作需要填写的字段
			BaseExpFields_1.add(new BaseFieldInfo("tmp_INC_T0DealDesc","800045501","接口测试-新建派发" + FormatTime.FormatLongDateToString(FormatTime.getCurrentDate()),4));
			BaseExpFields_1.add(new BaseFieldInfo("tmp_INC_T0_ToUserID","800045504","Demo",4));
			BaseExpFields_1.add(new BaseFieldInfo("tmp_INC_T0_ToUser","800045503","系统管理员",4));
			BaseExpFields_1.add(new BaseFieldInfo("tmp_INC_T0_ToGroupID","800045506","",4));
			BaseExpFields_1.add(new BaseFieldInfo("tmp_INC_T0_ToGroup","800045505","",4));
			Action_NextBaseCustom m_Action_NextBaseCustom = new Action_NextBaseCustom();		
			istrue_send = m_Action_NextBaseCustom.do_Action(
					"Demo", 
					"WF:EL_TTM_CCH", 
					ActionOp.getM_BaseID(), 
					null, 
					null, 
					"新建派发", 
					BaseExpFields_1, 
					null);
			if (istrue_send == false)
			{
				System.out.println("新建派发失败！");
			}
			else
			{
				System.out.println("新建派发成功，工单ＩＤ为：！" + ActionOp.getM_BaseID());
			}
		}
	    
		if (istrue_send)
		{
			List<BaseFieldInfo> BaseExpFields_2 = new ArrayList<BaseFieldInfo>();
			//这个字段是需要在流程图中显示的操作说明，请一定要填写；
			BaseExpFields_2.add(new BaseFieldInfo("CCH_T1Result","800021001","驳回处理",4));
			BaseExpFields_2.add(new BaseFieldInfo("tmp_CCH_GoTurnUpDesc","800045701","驳回处理",4));
			
			Action_NextBaseCustom m_Action_NextBaseCustom = new Action_NextBaseCustom();		
			istrue_finish = m_Action_NextBaseCustom.do_Action(
					"Demo", 
					"WF:EL_TTM_CCH", 
					ActionOp.getM_BaseID(), 
					null, 
					null, 
					"驳回处理", 
					BaseExpFields_2, 
					null);
			if (istrue_finish == false)
			{
				System.out.println("驳回处理失败！");
			}
			else
			{
				System.out.println("驳回处理成功，工单ＩＤ为：！" + ActionOp.getM_BaseID());
			}			
		}
		if (istrue_finish)
		{
			Action_Close m_Action_Close = new Action_Close();
			boolean d =  m_Action_Close.do_Action(
					"Demo", 
					"WF:EL_TTM_CCH", 
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
