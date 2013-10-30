package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.tree.service.SelectTreeSrcDataService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultraduty.model.DutyGroup;
import com.ultrapower.eoms.ultraplan.model.PlanCheckConfig;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

public class PlanCheckUserTreeImpl implements SelectTreeSrcDataService {
	
	private IDao<PlanCheckConfig> planCheckConfigDao;
	private UserManagerService userManagerService;
	private static final String FINDCHECKCONFIG = "from PlanCheckConfig planCheckConfig where " +
			" planCheckConfig.checkUser = ? and planCheckConfig.checkType = '2'";
	
	/**
	 * 或者质检人所能质检的原员
	 * 
	 * @param par 质检人ID
	 */
	public Object getSourceDataObj(String par) {
		Map<String, String> userMap = new HashMap<String, String>();
		List<PlanCheckConfig> list = planCheckConfigDao.find(FINDCHECKCONFIG, par);
		if(list != null && list.size() > 0){
			for(int i = 0 ; i < list.size();i++){
				
				String userName = userManagerService.getUserNameByID(list.get(i).getCommonUser());
				userMap.put(list.get(i).getCommonUser(), userName);
			}
		}
		return userMap;
	}


	public IDao<PlanCheckConfig> getPlanCheckConfigDao() {
		return planCheckConfigDao;
	}

	public void setPlanCheckConfigDao(IDao<PlanCheckConfig> planCheckConfigDao) {
		this.planCheckConfigDao = planCheckConfigDao;
	}


	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	

}
