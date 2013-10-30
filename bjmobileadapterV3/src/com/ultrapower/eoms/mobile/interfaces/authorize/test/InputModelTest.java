package com.ultrapower.eoms.mobile.interfaces.authorize.test;

import com.ultrapower.eoms.mobile.interfaces.authorize.model.AuthorizeInputModel;
import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;

import junit.framework.TestCase;

public class InputModelTest extends TestCase
{

	public void testBuildModel()
	{
		//创建期望对象
		AuthorizeInputModel expectedModel = new AuthorizeInputModel();
		expectedModel.setUserName("Demo");
		expectedModel.setPassword("BjjDFuU0ges==");
		expectedModel.setMachineCode("AndroidPhone");
		expectedModel.setSimNum("13900000000");
		
		//创建实际对象
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<opDetail>");
			inputXml.append("<baseInfo>");
				inputXml.append("<userName>Demo</userName>");
				inputXml.append("<password>BjjDFuU0ges==</password>");
				inputXml.append("<machineCode>AndroidPhone</machineCode>");
				inputXml.append("<simNum>13900000000</simNum>");
			inputXml.append("</baseInfo>");
			inputXml.append("<recordInfo />");
		inputXml.append("</opDetail>");
		AuthorizeInputModel actualModel = new AuthorizeInputModel();
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
		assertEquals(expectedModel.getPassword(), actualModel.getPassword());
		assertEquals(expectedModel.getMachineCode(), actualModel.getMachineCode());
		assertEquals(expectedModel.getSimNum(), actualModel.getSimNum());
	}

}
