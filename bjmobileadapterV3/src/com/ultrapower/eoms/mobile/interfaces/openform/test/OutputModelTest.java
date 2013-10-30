package com.ultrapower.eoms.mobile.interfaces.openform.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.mobile.interfaces.openform.model.OpenFormOutputModel;

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
				expectedXml.append("<actionOps>");
					expectedXml.append("<actionop id=\"800010001\" code=\"NEXT\">分派给代维公司</actionop>");
					expectedXml.append("<actionop id=\"ASSIGN\" code=\"ASSIGN\">内部分派</actionop>");
					expectedXml.append("<actionop id=\"SAVE\" code=\"SAVE\">签收</actionop>");
				expectedXml.append("</actionOps>");
			expectedXml.append("</baseInfo>");
			expectedXml.append("<recordInfo>");
				expectedXml.append("<field code=\"dealList\">黄维派发给徐发球;\n处理时限：2012-12-24 00:01:01;\n受理时限：2012-12-27 10:10:10</field>");
				expectedXml.append("<field code=\"baseSummary\">哈啊哈哈撒旦法</field>");
			expectedXml.append("</recordInfo>");
		expectedXml.append("</opDetail>");

		//创建实际对象
		OpenFormOutputModel outputModel = new OpenFormOutputModel();
		List<String[]> actoinOps = new ArrayList<String[]>();
		actoinOps.add(new String[] {"NEXT", "800010001", "分派给代维公司"});
		actoinOps.add(new String[] {"ASSIGN", "ASSIGN", "内部分派"});
		actoinOps.add(new String[] {"SAVE", "SAVE", "签收"});
		outputModel.setActions(actoinOps);
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("dealList", "黄维派发给徐发球;\n处理时限：2012-12-24 00:01:01;\n受理时限：2012-12-27 10:10:10");
		fields.put("baseSummary", "哈啊哈哈撒旦法");
		outputModel.setFields(fields);
		String actualXml = outputModel.buildXml();

		//测试
		assertEquals(expectedXml.toString(), actualXml);
	}
	
	public void testBuildExceptionXml()
	{
		assertEquals("<opDetail><baseInfo><isLegal>0</isLegal></baseInfo></opDetail>", OpenFormOutputModel.buildExceptionXml());
	}
}
