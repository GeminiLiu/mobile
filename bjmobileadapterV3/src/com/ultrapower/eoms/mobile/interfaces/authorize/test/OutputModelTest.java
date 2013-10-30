package com.ultrapower.eoms.mobile.interfaces.authorize.test;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ultrapower.eoms.mobile.interfaces.authorize.model.AuthorizeOutputModel;

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
			expectedXml.append("</baseInfo>");
			expectedXml.append("<recordInfo>");
				expectedXml.append("<category type=\"workflow\">");
					expectedXml.append("<ver type=\"WF4:EL_AM_PS\" text=\"发电保障工单\">WF4_EL_AM_PS_20130307</ver>");
					expectedXml.append("<ver type=\"WF4:EL_AM_TTH\" text=\"代维故障处理工单\">WF4_EL_AM_TTH_20130308</ver>");
					expectedXml.append("<ver type=\"WF4:EL_AM_AT\" text=\"代维任务工单\">WF4_EL_AM_AT_20130308</ver>");
				expectedXml.append("</category>");
			expectedXml.append("</recordInfo>");
		expectedXml.append("</opDetail>");
		
		//创建实际对象
		AuthorizeOutputModel outputModel = new AuthorizeOutputModel();
		Map<String, Map<String, String[]>> templates = new LinkedHashMap<String, Map<String,String[]>>();
		Map<String, String[]> vers = new LinkedHashMap<String, String[]>();
		vers.put("WF4:EL_AM_PS", new String[] {"发电保障工单", "WF4_EL_AM_PS_20130307"});
		vers.put("WF4:EL_AM_TTH", new String[] {"代维故障处理工单", "WF4_EL_AM_TTH_20130308"});
		vers.put("WF4:EL_AM_AT", new String[] {"代维任务工单", "WF4_EL_AM_AT_20130308"});
		templates.put("workflow", vers);
		outputModel.setLoginResult(1);
		outputModel.setTemplates(templates);
		String actualXml = outputModel.buildXml();

		//测试
		assertEquals(expectedXml.toString(), actualXml);
	}

	public void testBuildExceptionXml()
	{
		assertEquals("<opDetail><baseInfo><isLegal>0</isLegal></baseInfo></opDetail>", AuthorizeOutputModel.buildExceptionXml());
	}

}
