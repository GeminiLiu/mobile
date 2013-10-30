package com.ultrapower.eoms.mobile.interfaces.initaction.test;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;
import com.ultrapower.eoms.mobile.interfaces.initaction.model.InitActionInputModel;

import junit.framework.TestCase;

public class InputModelTest extends TestCase
{
	public void testBuildModel()
	{
		//创建期望对象
		InitActionInputModel expectedModel = new InitActionInputModel();
		expectedModel.setUserName("Demo");
		expectedModel.setBaseID("000000000043767");
		expectedModel.setCategory("WF4:EL_TTM_CCH");
		expectedModel.setTaskID("504471450526332");
		expectedModel.setActionCode("NEXT");
		expectedModel.setActionID("800010001");
		
		//创建实际对象
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo>");
				inputXml.append("<baseId>000000000043767</baseId>");
				inputXml.append("<category>WF4:EL_TTM_CCH</category>");
				inputXml.append("<taskId>504471450526332</taskId>");
				inputXml.append("<actionId>800010001</actionId>");
				inputXml.append("<actionCode>NEXT</actionCode>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		InitActionInputModel actualModel = new InitActionInputModel();
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
		assertEquals(expectedModel.getBaseID(), actualModel.getBaseID());
		assertEquals(expectedModel.getCategory(), actualModel.getCategory());
		assertEquals(expectedModel.getTaskID(), actualModel.getTaskID());
		assertEquals(expectedModel.getActionID(), actualModel.getActionID());
		assertEquals(expectedModel.getActionCode(), actualModel.getActionCode());
	}

}
