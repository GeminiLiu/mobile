package cn.com.ultrapower.eoms.util.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata.DwSysGroup;
import cn.com.ultrapower.eoms.user.rolemanage.group.bean.SysGroup;
import cn.com.ultrapower.eoms.util.StringUtil;

public class AgentCorporation extends TagSupport {

	private String name;

	private String value;

	private String style;

	private boolean addPlease;

	public boolean isAddPlease() {
		return addPlease;
	}

	public void setAddPlease(boolean addPlease) {
		this.addPlease = addPlease;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public int doStartTag() throws JspException {
		DwSysGroup dwSysGroup = new DwSysGroup();
		SysGroup sysGroup = new SysGroup();
		List list = dwSysGroup.select();
		JspWriter out = pageContext.getOut();
		try {
			out.print("<select name=\"");
			out.print(name);
			if (StringUtil.isNULL(style)) {
				out.print("\" style=\"" + style);
				out.print("\"");
			}
			out.print("\">");
			if(addPlease){
				out.print("\n<option value=\"");
				out.print("\"");
				out.print(">");
				out.print("请选择");
				out.print("</option>");
			}
			if (list != null && !list.isEmpty()) {
				for (int t = 0; t < list.size(); t++) {
					sysGroup = (SysGroup) list.get(t);
					out.print("\n<option value=\"");
					out.print(sysGroup.getName());
					out.print("\"");
					if (sysGroup.getName().equals(value)) {
						out.print(" selected ");
					}
					out.print(">");
					out.print(sysGroup.getName());
					out.print("</option>");

				}
			}
			out.print("</select>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
