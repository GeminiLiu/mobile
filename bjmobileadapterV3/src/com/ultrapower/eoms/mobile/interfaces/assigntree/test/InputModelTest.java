package com.ultrapower.eoms.mobile.interfaces.assigntree.test;

import com.ultrapower.eoms.mobile.interfaces.assigntree.model.AssignTreeInputModel;
import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;

import junit.framework.TestCase;

public class InputModelTest extends TestCase
{

	public void testBuildModel()
	{
		//创建期望对象
		AssignTreeInputModel expectedModel = new AssignTreeInputModel();
		expectedModel.setUserName("Demo");
		expectedModel.setShowCorp(2);
		expectedModel.setShowCenter(2);
		expectedModel.setShowStation(1);
		expectedModel.setShowTeam(1);
		expectedModel.setShowPerson(1);
		expectedModel.setMultiSelect(1);
		expectedModel.setSelectObjs("team,station,center");
		expectedModel.setCityID("shenyang");
		expectedModel.setSpecialtyID("basestation");
		
		//创建实际对象
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo>");
				inputXml.append("<showCorp>2</showCorp>");
				inputXml.append("<showCenter>2</showCenter>");
				inputXml.append("<showStation>1</showStation>");
				inputXml.append("<showTeam>1</showTeam>");
				inputXml.append("<showPerson>1</showPerson>");
				inputXml.append("<multi>1</multi>");
				inputXml.append("<selectObjs>team,station,center</selectObjs>");
				inputXml.append("<cityID>shenyang</cityID>");
				inputXml.append("<specialtyID>basestation</specialtyID>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		AssignTreeInputModel actualModel = new AssignTreeInputModel();
		try
		{
			actualModel.buildModel(inputXml.toString());
		}
		catch (InvalidXmlFormatException e)
		{
			fail(e.getMessage());
		}
		
		//测试
		assertEquals(expectedModel.getUserName(), actualModel.getUserName());
		assertEquals(expectedModel.getShowCorp(), actualModel.getShowCorp());
		assertEquals(expectedModel.getShowCenter(), actualModel.getShowCenter());
		assertEquals(expectedModel.getShowStation(), actualModel.getShowStation());
		assertEquals(expectedModel.getShowPerson(), actualModel.getShowPerson());
		assertEquals(expectedModel.getMultiSelect(), actualModel.getMultiSelect());
		assertEquals(expectedModel.getSelectObjs(), actualModel.getSelectObjs());
		assertEquals(expectedModel.getCityID(), actualModel.getCityID());
		assertEquals(expectedModel.getSpecialtyID(), actualModel.getSpecialtyID());
	}

}
