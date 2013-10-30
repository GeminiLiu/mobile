package com.ultrapower.eoms.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

public class DicTag extends TagSupport 
{
	private String dictype;//字典类型
	private String value;//字典值
	private String isfullname;//是否显示全名

	public int doStartTag()
	{
		return EVAL_BODY_INCLUDE;
	}
	public int doEndTag() 
	{
		JspWriter out = pageContext.getOut();
		try 
		{
			DicManagerService dicManagerService  = (DicManagerService)WebApplicationManager.getBean("dicManagerService");
			String diname = "";
			if("true".equals(StringUtils.checkNullString(isfullname)))
			{
				DicItem di = dicManagerService.getDicItemByValue(dictype, value);
				if(di != null)
					diname = StringUtils.checkNullString(di.getDicfullname());
			}
			else
				diname = dicManagerService.getTextByValue(dictype, value);
			out.print(diname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	public String getDictype() {
		return dictype;
	}
	public void setDictype(String dictype) {
		this.dictype = dictype;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIsfullname() {
		return isfullname;
	}
	public void setIsfullname(String isfullname) {
		this.isfullname = isfullname;
	}
}
