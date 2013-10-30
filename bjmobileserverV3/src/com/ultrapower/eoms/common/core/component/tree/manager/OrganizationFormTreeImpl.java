package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.constants.DutyConstants;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.service.DynamicDataService;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultraduty.model.DutyCalendarBean;
import com.ultrapower.eoms.ultraduty.model.DutyLogType;
import com.ultrapower.eoms.ultraduty.service.WorkService;

public class OrganizationFormTreeImpl implements DynamicDataService {

	private WorkService workService;

	public List<DtreeBean> getChildList(String parentId) {
		String organizationId = parentId.split("\\,")[1];
		List<DutyLogType> dutyLogTypeList = workService
				.getDutyLogTypeListByOrgId(organizationId);
		UserSession userSession = ActionContext.getUserSession();
		String loginName = userSession.getPid();
		DutyCalendarBean calenBean = workService.getCurrDutyWork(
				organizationId, loginName, null);
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		List<String> userNameList = workService.getOrgUserNameByOrgId(organizationId);
		String userName = "";
		if(userNameList!=null ){
			for(String userNameTemp : userNameList){
			    userName+=userNameTemp+",";
			}
		}
		DtreeBean dtreeBeanTemp;
		HashMap mapTemp;
		// 值班日志
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.FORM_TREE+DutyConstants.FAULT_RECORD_ID + organizationId);
		dtreeBeanTemp.setText(DutyConstants.FAULT_RECORD_NAME);
		if (calenBean != null && !calenBean.getCalendarId().equals("")) {
			mapTemp.put("url", DutyConstants.FAULT_RECORD_URL
					+ calenBean.getCalendarId());
		} else {
			mapTemp.put("url", DutyConstants.FAULT_RECORD_URL);
		}
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);
		// 待处理工单
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.FORM_TREE+DutyConstants.WAITING_DEAL_SHEET_ID
				+ organizationId);
		dtreeBeanTemp.setText(DutyConstants.WAITING_DEAL_SHEET_NAME);

		mapTemp.put("url", DutyConstants.WAITING_DEAL_SHEET_URL[0]+"0"+DutyConstants.WAITING_DEAL_SHEET_URL[1]+loginName+DutyConstants.WAITING_DEAL_SHEET_URL[2]+organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);
		// 待处理作业计划
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.FORM_TREE+DutyConstants.PLAN_EXEC_ID + organizationId);
		dtreeBeanTemp.setText(DutyConstants.PLAN_EXEC_NAME);
		mapTemp.put("url", DutyConstants.PLAN_EXEC_URL + organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);

		// 我的排班
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.FORM_TREE+DutyConstants.MY_ORGANIZATION_CALENDAR_ID
				+ organizationId);
		dtreeBeanTemp.setText(DutyConstants.MY_ORGANIZATION_CALENDAR_NAME);
		mapTemp.put("url", DutyConstants.MY_ORGANIZATION_CALENDAR_URL+organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);
		
		
		// 值班室待处理工单
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.FORM_TREE+DutyConstants.DUTY_WAITING_DEAL_SHEET_ID
				+ organizationId);
		dtreeBeanTemp.setText(DutyConstants.DUTY_WAITING_DEAL_SHEET_NAME);

		mapTemp.put("url", DutyConstants.WAITING_DEAL_SHEET_URL[0]+"1"+DutyConstants.WAITING_DEAL_SHEET_URL[1]+userName+DutyConstants.WAITING_DEAL_SHEET_URL[2]+organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);

		// 值班室表单
		if (dutyLogTypeList != null && dutyLogTypeList.size() > 0) {
			for (DutyLogType dlt : dutyLogTypeList) {
				DtreeBean dtreeBean = new DtreeBean();
				dtreeBean.setId(organizationId+DutyConstants.FORM_TREE+dlt.getPid());
				dtreeBean.setText(dlt.getName());
				HashMap map = new HashMap();
				if (calenBean != null && !calenBean.getCalendarId().equals("")) {
					map.put("url", DutyConstants.DUTY_LOG_URL[0]
							+ calenBean.getCalendarId()
							+ DutyConstants.DUTY_LOG_URL[1]
							+ dlt.getDutyTemplate().getTemplateid());
				} else {
					map.put("url", DutyConstants.DUTY_LOG_URL[0]
  							+ ""
  							+ DutyConstants.DUTY_LOG_URL[1]
  							+ dlt.getDutyTemplate().getTemplateid());
				}
				dtreeBean.setUserdata(map);
				dtreeList.add(dtreeBean);
			}
		}
		return dtreeList;
	}

	public WorkService getWorkService() {
		return workService;
	}

	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

}
