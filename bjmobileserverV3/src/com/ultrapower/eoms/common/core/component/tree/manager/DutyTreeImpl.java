package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.tree.service.SelectTreeSrcDataService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultraduty.model.DutyGeneralKnowledge;
import com.ultrapower.eoms.ultraduty.model.DutyTemplate;

public class DutyTreeImpl implements SelectTreeSrcDataService {
	
	private IDao<DutyTemplate> dutyTemplateDao;
	private IDao<DutyGeneralKnowledge> theDutyGeneralKnowledgeDao;
	
	/**
	 * 取得表单/常识集合
	 * 
	 * @param par 0,表单;1,常识
	 */
	public Object getSourceDataObj(String par) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		if ("0".equals(par)) {
			List<DutyTemplate> list = dutyTemplateDao.find("from DutyTemplate t where t.actived = ?", "1");
			if(list != null && list.size() > 0){
				for(DutyTemplate dt : list){
					dataMap.put(dt.getPid(), dt.getName());
				}
			}
		} else if ("1".equals(par)) {
			List<DutyGeneralKnowledge> list = theDutyGeneralKnowledgeDao.getAll();
			if(list != null && list.size() > 0){
				for(DutyGeneralKnowledge dgk : list){
					dataMap.put(dgk.getPid(), dgk.getTitle());
				}
			}
		}
		
		return dataMap;
	}

	public void setDutyTemplateDao(IDao<DutyTemplate> dutyTemplateDao) {
		this.dutyTemplateDao = dutyTemplateDao;
	}

	public void setTheDutyGeneralKnowledgeDao(
			IDao<DutyGeneralKnowledge> theDutyGeneralKnowledgeDao) {
		this.theDutyGeneralKnowledgeDao = theDutyGeneralKnowledgeDao;
	}
}
