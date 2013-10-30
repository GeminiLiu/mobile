package cn.com.ultrapower.eoms.util.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.ultrapower.eoms.util.StringUtil;

public class GerneralSelect extends TagSupport {

	private String name;
	private String items;
	private String value;
	private boolean addPlease;
	private String style;
	private String readOnly;

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

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

		List list = new ArrayList();
		String[] item = items.split(",");
		list = Arrays.asList(item);

		JspWriter out = pageContext.getOut();
		try {
			out.print("<select name=\"");
			out.print(name);
			out.print("\"");
			if(StringUtil.isNULL(style)){
				out.print(" style=\"");
				out.print(style);
				out.print("\"");
			}
			out.print(" >");
			if(addPlease){
				out.print("\n<option value=\"");
				out.print("\"");
				out.print(">");
				out.print("请选择");
				out.print("</option>");
			}
			if (list != null && !list.isEmpty()) {
				for (int t = 0; t < list.size(); t++) {
					String sb = (String) list.get(t);
					out.print("\n<option value=\"");
					out.print(sb);
					out.print("\"");
					if (sb.equals(value)) {
						out.print(" selected ");
					}
					out.print(">");
					out.print(sb);
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

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

}
