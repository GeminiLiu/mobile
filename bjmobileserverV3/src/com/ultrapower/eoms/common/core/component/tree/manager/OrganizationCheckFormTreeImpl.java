package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.constants.DutyConstants;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.service.DynamicDataService;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultraduty.model.DutyOrganization;
import com.ultrapower.eoms.ultraduty.service.OrganizationService;

public class OrganizationCheckFormTreeImpl implements DynamicDataService{

	private OrganizationService organizationService;
	/**
	 * 根据登陆用户查询所有值班室
	 */
	public List<DtreeBean> getChildList(String parentid) {
		UserSession userSession = ActionContext.getUserSession();
		List<DutyOrganization> orgList = organizationService.getQualityOrgByUserId(userSession.getPid());
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
	   
		if(orgList!=null && orgList.size()>0){
			for(DutyOrganization org : orgList){
				DtreeBean dtreeBean = new DtreeBean();
				dtreeBean.setId(org.getPid());
				dtreeBean.setText(org.getOrganizationname());
				HashMap map = new HashMap();
				map.put("url",DutyConstants.DUTY_CHECK_URL+org.getPid());
				
				dtreeBean.setUserdata(map);
				dtreeBean.setUserdata(map);
				dtreeList.add(dtreeBean);
			}
		}
		return dtreeList;
	}
	public OrganizationService getOrganizationService() {
		return organizationService;
	}
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

}
