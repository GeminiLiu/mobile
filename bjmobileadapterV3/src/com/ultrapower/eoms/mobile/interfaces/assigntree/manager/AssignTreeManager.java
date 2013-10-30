package com.ultrapower.eoms.mobile.interfaces.assigntree.manager;

//import com.ultrapower.eoms.common.core.util.StringUtils;
//import com.ultrapower.eoms.common.portal.model.UserSession;
//import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import cn.com.ultrapower.ultrawf.share.FormatString;

import com.ultrapower.eoms.mobile.interfaces.assigntree.model.AssignTreeInputModel;
import com.ultrapower.eoms.mobile.service.InterfaceService;
//import com.ultrapower.mams.common.tree.model.DwTreeParameter;
//import com.ultrapower.mams.common.tree.service.DwCorpOrgService;
import com.ultrapower.eoms.mobile.util.ParseInterfaceTree;

public class AssignTreeManager implements InterfaceService
{
//	private PortalManagerService portalManagerService;

//	private DwCorpOrgService dwCorpOrgService;
	
	public String call(String xml, String fileList)
	{
		AssignTreeInputModel input = new AssignTreeInputModel();
		String outputXml;

		try
		{
			input.buildModel(xml);
			outputXml = handle(input);
			outputXml = outputXml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
			outputXml = outputXml.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
		}
		catch (Exception e)
		{
			outputXml = "<opDetail><baseInfo><isLegal>0</isLegal></baseInfo></opDetail>";
			e.printStackTrace();
		}
		return outputXml;
	}

	private String handle(AssignTreeInputModel inputModel)
	{
		ParseInterfaceTree m_ParseInterfaceTree = new ParseInterfaceTree();
		String xml = m_ParseInterfaceTree.getXML("schema", inputModel.getUserName(), inputModel.getCityID(), "0", "2",inputModel);
		
		//		UserSession userSession = portalManagerService.login(inputModel.getUserName(), "", false);
		
//		DwTreeParameter para = new DwTreeParameter();
//		para.setIsNesting(1);
//		para.setIsShowCorp(inputModel.getShowCorp() < 1 ? 0 : inputModel.getShowCorp());
//		para.setIsShowCenter(inputModel.getShowCenter() < 1 ? 0 : inputModel.getShowCenter());
//		para.setIsShowStation(inputModel.getShowStation() < 1 ? 0 : inputModel.getShowStation());
//		para.setIsShowTeam(inputModel.getShowTeam() < 1 ? 0 : inputModel.getShowTeam());
//		para.setIsShowPerson(inputModel.getShowPerson() < 1 ? 0 : inputModel.getShowPerson());
//		
//		if(inputModel.getShowCorp() == 1) para.setCorpId(userSession.getDwCompanyId());
//		if(inputModel.getShowCenter() == 1) para.setCenterId(userSession.getDwCenterId());
//		
//		para.setCityCheck("0");//多选时是否可以选择地市  1 可以选择 0 不可以
//		para.setSpecialtyCheck("0");//多选时是否可以选择地市  1 可以选择 0 不可以
//		para.setCorpCheck("0");
//		para.setCenterCheck("0");
//		para.setStationCheck("0");
//		para.setTeamCheck("0");
//		para.setPersonCheck("0");
//		String[] values=inputModel.getSelectObjs().split(",");
//		for(String s:values)
//		{
//			s = FormatString.CheckNullString(s);
//			if(s.equals("city")) para.setCityCheck("1");
//			else if(s.equals("specialty")) para.setSpecialtyCheck("1");
//			else if(s.equals("corp")) para.setCorpCheck("1");
//			else if(s.equals("center")) para.setCenterCheck("1");
//			else if(s.equals("station")) para.setStationCheck("1");
//			else if(s.equals("team")) para.setTeamCheck("1");
//			else if(s.equals("person")) para.setPersonCheck("1");
//		}
//		
//		if(inputModel.getCityID() != null && !inputModel.getCityID().equals(""))
//		{
//			para.setCityId(inputModel.getCityID());
//		}
//		if(inputModel.getSpecialtyID() != null && !inputModel.getSpecialtyID().equals(""))
//		{
//			para.setSpecialtyId(inputModel.getSpecialtyID());
//		}
//		
		String output = xml;//dwCorpOrgService.getSpecialtyXml("", para);
		
		return output;
	}
}
