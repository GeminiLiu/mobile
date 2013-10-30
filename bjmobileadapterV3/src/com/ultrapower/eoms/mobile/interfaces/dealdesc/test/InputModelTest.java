package com.ultrapower.eoms.mobile.interfaces.dealdesc.test;

import com.ultrapower.eoms.mobile.interfaces.dealdesc.model.DealDescInputModel;
import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;

import junit.framework.TestCase;

public class InputModelTest extends TestCase
{

	public void testBuildModel()
	{
		//创建期望对象
		DealDescInputModel expectedModel = new DealDescInputModel();
		expectedModel.setUserName("Demo");
		expectedModel.setBaseID("000000000043767");
		expectedModel.setCategory("WF4:EL_TTM_CCH");
		
		//创建实际对象
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo>");
				inputXml.append("<baseId>000000000043767</baseId>");
				inputXml.append("<category>WF4:EL_TTM_CCH</category>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		DealDescInputModel actualModel = new DealDescInputModel();
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
	}

}
