package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.tree.service.SelectTreeSrcDataService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultraduty.model.DutyOrganization;

public class OrganizationMapImpl implements SelectTreeSrcDataService {
	
	private IDao<DutyOrganization> theDutyOrganizationDao;;
	
	/**
	 * 取得岗位类型集合
	 * 
	 * @param par 值班室状态 0：停用，1：启用，2:全部
	 */
	public Object getSourceDataObj(String par) {
		Map<String, String> orgMap = new LinkedHashMap<String, String>();
		StringBuffer sbf = new StringBuffer();
		sbf.append("from DutyOrganization dutyOrg where 1 = 1");
		if ("0".equals(par) || "1".endsWith(par)) {
			sbf.append(" and dutyOrg.state = '").append(par).append("'");
		}
		sbf.append(" order by dutyOrg.pid");
		List<DutyOrganization> list = theDutyOrganizationDao.find(sbf.toString());
		if(list != null && list.size() > 0){
			for(DutyOrganization dutyOrg : list){
				orgMap.put(dutyOrg.getPid(), dutyOrg.getOrganizationname());
			}
		}
		return orgMap;
	}

	public void setTheDutyOrganizationDao(
			IDao<DutyOrganization> theDutyOrganizationDao) {
		this.theDutyOrganizationDao = theDutyOrganizationDao;
	}

}
