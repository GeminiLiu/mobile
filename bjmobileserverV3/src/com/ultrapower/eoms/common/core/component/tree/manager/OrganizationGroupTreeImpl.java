package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.tree.service.SelectTreeSrcDataService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultraduty.model.DutyGroup;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;

public class OrganizationGroupTreeImpl implements SelectTreeSrcDataService {
	
	private IDao<DutyGroup> theDutyGroupDao;
	private DepManagerService depManagerService;
	
	private static final String SEARCHGROUP = "from DutyGroup dg where dg.dutyOrganization.pid = ?";
	
	/**
	 * 取得值班室内所有值班组集合
	 * 
	 * @param par 值班室ID
	 */
	public Object getSourceDataObj(String par) {
		Map<String, String> groupMap = new LinkedHashMap<String, String>();
		List<DutyGroup> list = theDutyGroupDao.find(SEARCHGROUP, par);
		if(list != null && list.size() > 0){
			for(DutyGroup group : list){
				String groupId = group.getGroupid();
				String groupName = depManagerService.getDepNameByID(groupId);
				groupMap.put(groupId, groupName);
			}
		}
		return groupMap;
	}

	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}

	public void setTheDutyGroupDao(IDao<DutyGroup> theDutyGroupDao) {
		this.theDutyGroupDao = theDutyGroupDao;
	}
}
