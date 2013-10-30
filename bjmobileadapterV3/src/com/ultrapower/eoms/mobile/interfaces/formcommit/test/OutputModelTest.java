package com.ultrapower.eoms.mobile.interfaces.formcommit.test;

import com.ultrapower.eoms.mobile.interfaces.formcommit.model.FormCommitOutputModel;

import junit.framework.TestCase;

public class OutputModelTest extends TestCase
{

	public void testBuildXml()
	{
		//创建期望对象
		StringBuilder expectedXml = new StringBuilder();
		expectedXml.append("<opDetail>");
			expectedXml.append("<baseInfo>");
				expectedXml.append("<isLegal>1</isLegal>");
				expectedXml.append("<success>1</success>");
				expectedXml.append("<errorMessage></errorMessage>");
			expectedXml.append("</baseInfo>");
		expectedXml.append("</opDetail>");
		
		//创建实际对象
		FormCommitOutputModel outputModel = new FormCommitOutputModel();
		outputModel.setSuccess(1);
		String actualXml = outputModel.buildXml();
		
		//测试
		assertEquals(expectedXml.toString(), actualXml);
	}
	
	public void testBuildExceptionXml()
	{
		assertEquals("<opDetail><baseInfo><isLegal>0</isLegal><success>0</success><errorMessage>error</errorMessage></baseInfo></opDetail>", FormCommitOutputModel.buildExceptionXml("error"));
	}

}
