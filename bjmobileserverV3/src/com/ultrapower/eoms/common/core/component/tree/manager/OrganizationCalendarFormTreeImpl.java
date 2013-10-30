package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.constants.DutyConstants;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.service.DynamicDataService;
import com.ultrapower.eoms.ultraduty.service.WorkService;

public class OrganizationCalendarFormTreeImpl implements DynamicDataService {

	private WorkService workService;
	
	
	public List<DtreeBean> getChildList(String parentId) {
		String organizationId = parentId.split("\\,")[1];
//		List<DutyLogType> dutyLogTypeList = workService.getDutyLogTypeListByOrgId(organizationId);
//		UserSession userSession = ActionContext.getUserSession();
//		String loginName = userSession.getPid();
//		DutyCalendarBean calenBean = workService.getCurrDutyWork(organizationId,loginName,null);
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		DtreeBean dtreeBeanTemp;
		HashMap mapTemp;
		//排班
		dtreeBeanTemp = new DtreeBean();
		mapTemp = new HashMap();
		dtreeBeanTemp.setId(DutyConstants.ORGANIZATION_CALENDAR_ID+organizationId);
		dtreeBeanTemp.setText(DutyConstants.ORGANIZATION_CALENDAR_NAME);
		mapTemp.put("url",DutyConstants.ORGANIZATION_CALENDAR_URL);
		dtreeBeanTemp.setUserdata(mapTemp);
//		dtreeBeanTemp.setUserdata(mapTemp);
		dtreeList.add(dtreeBeanTemp);

		return dtreeList;
	}

	public WorkService getWorkService() {
		return workService;
	}
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

}
