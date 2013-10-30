package com.ultrapower.eoms.mobile.interfaces.authorize.model;

import java.util.Map;

public class AuthorizeOutputModel
{
	private int loginResult;
	private String userName;
	private String dwSpecialtyId;
	private Map<String, Map<String, String[]>> templates;
	
	/**
	 * 将对象属性拼装为XML
	 * @return 拼装的XML
	 */
	public String buildXml()
	{
		StringBuilder xml = new StringBuilder();
		xml.append("<opDetail>");
			xml.append("<baseInfo>");
				xml.append("<isLegal>" + loginResult + "</isLegal>");
				if (userName != null && !"".equals(userName))
					xml.append("<userName>" + userName + "</userName>");
				if (dwSpecialtyId != null && !"".equals(dwSpecialtyId))
					xml.append("<dwSpecialtyId>" + dwSpecialtyId + "</dwSpecialtyId>");
			xml.append("</baseInfo>");
			xml.append("<recordInfo>");
			for(String type : templates.keySet())
			{
				xml.append("<category type=\"" + type + "\">");
				Map<String, String[]> vers = templates.get(type);
				for(String cate : vers.keySet())
				{
					xml.append("<ver type=\"" + cate + "\" text=\"" + vers.get(cate)[0] + "\">" + vers.get(cate)[1] + "</ver>");
				}
				xml.append("</category>");
			}
			xml.append("</recordInfo>");
		xml.append("</opDetail>");

		return xml.toString();
	}
	
	/**
	 * 异常时返回的XML
	 * @return 异常XML
	 */
	public static String buildExceptionXml()
	{
		return "<opDetail><baseInfo><isLegal>0</isLegal></baseInfo></opDetail>";
	}

	public Map<String, Map<String, String[]>> getTemplates()
	{
		return templates;
	}

	public void setTemplates(Map<String, Map<String, String[]>> templates)
	{
		this.templates = templates;
	}

	public int getLoginResult()
	{
		return loginResult;
	}

	public void setLoginResult(int loginResult)
	{
		this.loginResult = loginResult;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDwSpecialtyId() {
		return dwSpecialtyId;
	}

	public void setDwSpecialtyId(String dwSpecialtyId) {
		this.dwSpecialtyId = dwSpecialtyId;
	}
}
