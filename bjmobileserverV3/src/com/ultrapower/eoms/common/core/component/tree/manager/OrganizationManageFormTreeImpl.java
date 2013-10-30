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

public class OrganizationManageFormTreeImpl implements DynamicDataService {

	private WorkService workService;
	
	
	public List<DtreeBean> getChildList(String parentId) {
		String organizationId = parentId.split("\\,")[1];
		List<DutyLogType> dutyLogTypeList = workService.getDutyLogTypeListByOrgId(organizationId);
		UserSession userSession = ActionContext.getUserSession();
		String loginName = userSession.getPid();
//		DutyCalendarBean calenBean = workService.getCurrDutyWork(organizationId,loginName,null);
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		DtreeBean dtreeBeanTemp;
		HashMap mapTemp;
		//值班日志
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.MANAGE_TREE+DutyConstants.FAULT_RECORD_ID+organizationId);
		dtreeBeanTemp.setText(DutyConstants.FAULT_RECORD_NAME);
//		if(calenBean!=null && !calenBean.getCalendarId().equals("")){
//		   mapTemp.put("url",DutyConstants.FAULT_RECORD_URL+calenBean.getCalendarId());
//		}
		mapTemp.put("url",DutyConstants.FAULT_RECORD_SEARCH_URL+organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);
		//已处理工单
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.MANAGE_TREE+DutyConstants.MY_DEALED_SHEET_ID+organizationId);
		dtreeBeanTemp.setText(DutyConstants.MY_DEALED_SHEET_NAME);
		mapTemp.put("url",DutyConstants.MY_DEALED_SHEET_URL+organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);
		//已处理作业计划
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.MANAGE_TREE+DutyConstants.DEALED_PLAN_EXEC_ID+organizationId);
		dtreeBeanTemp.setText(DutyConstants.DEALED_PLAN_EXEC_NAME);
		mapTemp.put("url",DutyConstants.DEALED_PLAN_EXEC_URL+organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);
		//交接班记录查询
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.MANAGE_TREE+DutyConstants.DUTY_WORK_ID+organizationId);
		dtreeBeanTemp.setText(DutyConstants.DUTY_WORK_NAME);
		mapTemp.put("url",DutyConstants.DUTY_WORK_URL+organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);
		//非周期作业计划执行情况
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.MANAGE_TREE+DutyConstants.PLAN_EXECUTE_SEARCH_ID+organizationId);
		dtreeBeanTemp.setText(DutyConstants.PLAN_EXECUTE_SEARCH_NAME);
		mapTemp.put("url",DutyConstants.PLAN_EXECUTE_SEARCH_URL+organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);
		
		//月周期作业计划执行情况
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.MANAGE_TREE+DutyConstants.MONTH_PLAN_EXECUTE_SEARCH_ID+organizationId);
		dtreeBeanTemp.setText(DutyConstants.MONTH_PLAN_EXECUTE_SEARCH_NAME);
		mapTemp.put("url",DutyConstants.MONTH_PLAN_EXECUTE_SEARCH_URL+organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);
		//替班记录查询
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.MANAGE_TREE+DutyConstants.DUTY_RALAY_ID+organizationId);
		dtreeBeanTemp.setText(DutyConstants.DUTY_RALAY_NAME);
		mapTemp.put("url",DutyConstants.DUTY_RALAY_URL+organizationId);
		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);

		//值班室表单
		if(dutyLogTypeList!=null && dutyLogTypeList.size()>0){
		    for(DutyLogType dlt : dutyLogTypeList ){
		    	DtreeBean dtreeBean = new DtreeBean();
		    	dtreeBean.setId(organizationId+DutyConstants.MANAGE_TREE+dlt.getPid());
				dtreeBean.setText(dlt.getName());
				HashMap map = new HashMap();
//				if(calenBean!=null && !calenBean.getCalendarId().equals("")){
//				    map.put("url",DutyConstants.DUTY_LOG_SEARCH_URL[0]+calenBean.getCalendarId()+DutyConstants.DUTY_LOG_URL[1]+dlt.getDutyTemplate().getTemplateid());
//				} else {
//					map.put("url",DutyConstants.DUTY_LOG_SEARCH_URL[0]+""+DutyConstants.DUTY_LOG_URL[1]+dlt.getDutyTemplate().getTemplateid());
//				}
				map.put("url",DutyConstants.DUTY_LOG_SEARCH_URL[0]+organizationId+DutyConstants.DUTY_LOG_URL[1]+dlt.getDutyTemplate().getTemplateid());
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
