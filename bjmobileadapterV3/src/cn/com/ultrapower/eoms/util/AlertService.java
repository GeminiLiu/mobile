package cn.com.ultrapower.eoms.util;

import org.hibernate.HibernateException;

import cn.com.ultrapower.eoms.user.userinterface.bean.SmsOrderMessageBean;
import cn.com.ultrapower.eoms.user.userinterface.cm.SmsOrderInsert;

/**
 * 
 * <p>
 * Description: 短信发送
 * </p>
 * <p>
 * Created on: 2007-4-6
 * </p>
 * 
 * @author <a href="mailto:jfox.allen@gmail.com">allen.ye</a>
 * 
 */
public class AlertService {

	private static AlertService instance = new AlertService();

	private SmsOrderInsert insert;

	private AlertService() {
		insert = new SmsOrderInsert();
	}

	public static AlertService getInstance() {
		return instance;
	}

	public boolean fireMessage(String userid, String content) {
		boolean flag = false;
		SmsOrderMessageBean msg = new SmsOrderMessageBean();
		msg.setSmsorder_Id(userid);
		msg.setSmsMessage(content);
		try {
			flag = insert.insertsmsmessage(msg);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean fireMessage(SmsOrderMessageBean bean) {
		boolean flag = false;
		int nums = insert.insertSms(bean);
		if (nums > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 年计划审批短信提醒
	 * 
	 * @param userid
	 * @return
	 */
	public boolean fireYearPlanAlert(String userid) {
		String message = "一条年作业计划于" + CalendarUtil.getCurrentDateTime24() + "创建,需要您审批";
		return fireMessage(userid, message);
	}

	/**
	 * 月计划审批短信提醒
	 * 
	 * @param userid
	 * @return
	 */
	public boolean fireMonthPlanAlert(String userid) {
		String message = "一条月作业计划于" + CalendarUtil.getCurrentDateTime24() + "创建,需要您审批";
		return fireMessage(userid, message);
	}

	/**
	 * 月执行计划审批短信提醒
	 * 
	 * @param userid
	 * @return
	 */
	public boolean fireExecutePlanAlert(String userid) {
		String message = "一条月度作业计划于" + CalendarUtil.getCurrentDateTime24() + "执行完毕,需要您审批";
		return fireMessage(userid, message);
	}

	/**
	 * 故障经验库审批短信提醒
	 * 
	 * @param userid
	 * @return
	 */
	public boolean fireTroubleExpAlert(String userid) {
		String message = "一条故障经验于" + CalendarUtil.getCurrentDateTime24() + "提交,需要您审批";
		return fireMessage(userid, message);
	}

	/**
	 * 网络优化经验库审批短信提醒
	 * 
	 * @param userid
	 * @return
	 */
	public boolean fireOptimizeExpAlert(String userid) {
		String message = "一条网络优化经验于" + CalendarUtil.getCurrentDateTime24() + "提交,需要您审批";
		return fireMessage(userid, message);
	}

	/**
	 * 通用经验库审批短信提醒
	 * 
	 * @param userid
	 * @return
	 */
	public boolean fireGeneralExpAlert(String userid) {
		String message = "一条通用经验于" + CalendarUtil.getCurrentDateTime24() + "提交,需要您审批";
		return fireMessage(userid, message);
	}

	/**
	 * 咨询经验库审批短信提醒
	 * 
	 * @param userid
	 * @return
	 */
	public boolean fireConsultationExpAlert(String userid) {
		String message = "一条咨询经验于" + CalendarUtil.getCurrentDateTime24() + "提交,需要您审批";
		return fireMessage(userid, message);
	}

	/**
	 * 申告经验库审批短信提醒
	 * 
	 * @param userid
	 * @return
	 */
	public boolean fireCriticismExpAlert(String userid) {
		String message = "一条申告经验于" + CalendarUtil.getCurrentDateTime24() + "提交,需要您审批";
		return fireMessage(userid, message);
	}

}
