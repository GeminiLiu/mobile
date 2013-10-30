package com.ultrapower.eoms.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.common.core.util.TimeUtils;

public class DateTag extends TagSupport{
	
	public long value;

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
	public int doEndTag() 
	{
		
		JspWriter out = pageContext.getOut();
		try 
		{
			String strText="";
			if(value>0)
			{
				strText=TimeUtils.formatIntToDateString(value);
			}
			out.print(strText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
