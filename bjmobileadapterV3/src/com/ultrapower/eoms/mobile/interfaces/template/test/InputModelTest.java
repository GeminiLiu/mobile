package com.ultrapower.eoms.mobile.interfaces.template.test;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;
import com.ultrapower.eoms.mobile.interfaces.template.model.TemplateInputModel;

import junit.framework.TestCase;

public class InputModelTest extends TestCase
{

	public void testBuildModel()
	{
		//创建期望对象
		TemplateInputModel expectedModel = new TemplateInputModel();
		expectedModel.setUserName("Demo");
		expectedModel.setCategory("workflow");
		expectedModel.setVersion("WF4:EL_AM_PS");
		
		//创建实际对象
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo>");
				inputXml.append("<category>workflow</category>");
				inputXml.append("<version>WF4:EL_AM_PS</version>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		TemplateInputModel actualModel = new TemplateInputModel();
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
		assertEquals(expectedModel.getCategory(), actualModel.getCategory());
		assertEquals(expectedModel.getVersion(), actualModel.getVersion());
		
	}

}
