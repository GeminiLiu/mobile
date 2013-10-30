package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.tree.service.SelectTreeSrcDataService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultraduty.model.DutyShift;

public class OrganizationShiftTreeImpl implements SelectTreeSrcDataService {
	
	private IDao<DutyShift> theDutyShiftDao;
	
	private static final String SEARCHSHIFT = "from DutyShift ds where ds.dutyOrganization.pid = ? and ds.hideflag <> 1";
	
	/**
	 * 取得值班室内所有班次集合
	 * 
	 * @param par 值班室ID
	 */
	public Object getSourceDataObj(String par) {
		Map<String, String> shiftMap = new LinkedHashMap<String, String>();
		List<DutyShift> list = theDutyShiftDao.find(SEARCHSHIFT, par);
		if(list != null && list.size() > 0){
			for(DutyShift shift : list){
				shiftMap.put(shift.getPid(), shift.getShiftname());
			}
		}
		return shiftMap;
	}

	public void setTheDutyShiftDao(IDao<DutyShift> theDutyShiftDao) {
		this.theDutyShiftDao = theDutyShiftDao;
	}
}
