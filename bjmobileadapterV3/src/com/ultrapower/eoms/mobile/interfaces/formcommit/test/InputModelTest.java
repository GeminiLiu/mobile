package com.ultrapower.eoms.mobile.interfaces.formcommit.test;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ultrapower.eoms.mobile.interfaces.exception.InvalidXmlFormatException;
import com.ultrapower.eoms.mobile.interfaces.formcommit.model.FormCommitInputModel;

import junit.framework.TestCase;

public class InputModelTest extends TestCase
{

	public void testBuildModel()
	{
		//创建期望对象
		FormCommitInputModel expectedModel = new FormCommitInputModel();
		expectedModel.setUserName("Demo");
		expectedModel.setBaseID("000000000043767");
		expectedModel.setCategory("WF4:EL_TTM_CCH");
		expectedModel.setTaskID("504471450526332");
		expectedModel.setActionCode("NEXT");
		expectedModel.setActionText("移交到二线");
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("INC_Finish_Phase", "二级处理完成");
		fields.put("INC_T2Result", "完成处理");
		expectedModel.setFields(fields);
		
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
				inputXml.append("<actionCode>NEXT</actionCode>");
				inputXml.append("<actionText>移交到二线</actionText>");
				inputXml.append("<field code=\"INC_Finish_Phase\">二级处理完成</field>");
				inputXml.append("<field code=\"INC_T2Result\">完成处理</field>");
			inputXml.append("</recordInfo>");
		inputXml.append("</opDetail>");
		FormCommitInputModel actualModel = new FormCommitInputModel();
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
		assertEquals(expectedModel.getActionCode(), actualModel.getActionCode());
		Map<String, String> expectedFields = expectedModel.getFields();
		Map<String, String> actualFields = actualModel.getFields();
		assertEquals(expectedFields.get("INC_Finish_Phase"), actualFields.get("INC_Finish_Phase"));
		assertEquals(expectedFields.get("INC_T2Result"), actualFields.get("INC_T2Result"));
	}

}
