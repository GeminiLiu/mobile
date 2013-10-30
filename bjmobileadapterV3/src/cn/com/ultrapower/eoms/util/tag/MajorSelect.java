package cn.com.ultrapower.eoms.util.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.ultrapower.eoms.user.userinterface.GetConfigInterFace;
import cn.com.ultrapower.eoms.user.userinterface.bean.SysBaseItems;
import cn.com.ultrapower.eoms.user.userinterface.bean.SysBaseItemsFlag;

public class MajorSelect extends TagSupport {

	private String name;
	private String value;
	private boolean addPlease;

	public boolean isAddPlease() {
		return addPlease;
	}

	public void setAddPlease(boolean addPlease) {
		this.addPlease = addPlease;
	}

	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public int doStartTag() throws JspException {
		SysBaseItems SysBaseItems = new SysBaseItems();// 传进SysBaseItems的字段条件
		SysBaseItemsFlag SysBaseItemsFlag = new SysBaseItemsFlag();// 要显示的字段
		SysBaseItemsFlag.setBase_Item(true);// 要示设的字段设为真
		GetConfigInterFace getConfigInterFace = new GetConfigInterFace();
		List list = getConfigInterFace.GetSysBaseItems(SysBaseItems,
				SysBaseItemsFlag);
		JspWriter out = pageContext.getOut();
		try {
			out.print("<select name=\"");
			out.print(name);
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
					SysBaseItems sb = (SysBaseItems) list.get(t);
					out.print("\n<option value=\"");
					out.print(sb.getBase_Item());
					out.print("\"");
					if (sb.getBase_Item().equals(value)) {
						out.print(" selected ");
					}
					out.print(">");
					out.print(sb.getBase_Item());
					out.print("</option>");

				}
			}
			out.print("</select>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
