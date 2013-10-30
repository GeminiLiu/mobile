package cn.com.ultrapower.eoms.util.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 河北的各个地市
 * @author LY
 *
 */
public class HeBeiCity extends TagSupport {


	private String name;
	private String value;

	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public int doStartTag() throws JspException {
		
		List list = new ArrayList();
		list.add("石家庄");
		list.add("保定");
		list.add("衡水");
		list.add("唐山");
		list.add("秦皇岛");
		list.add("沧州");
		list.add("邯郸");
		list.add("邢台");
		list.add("承德");
		list.add("张家口");
		list.add("廊坊");
		JspWriter out = pageContext.getOut();
		try {
			out.print("<select name=\"");
			out.print(name);
			out.print("\">");
			if (list != null && !list.isEmpty()) {
				for (int t = 0; t < list.size(); t++) {
					String sb = (String) list.get(t);
					out.print("\n<option value=\"");
					out.print(sb);
					out.print("\"");
					if (sb.equals(value)){
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

	

}
