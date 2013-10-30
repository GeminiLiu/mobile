package com.ultrapower.eoms.common.core.component.rquery.core;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ultrapower.eoms.common.core.util.NumberUtils;

import org.jdom.Element;

import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.tag.select.core.Select;

public class QueryCondition {
	
	private String sqlName="";
	private Element sqlqueryElement;
	private int line=0;
	public QueryCondition(String sqlName)
	{
		this.sqlName=sqlName;
		
		Object obj=null;
		if(StartUp.sqlmapElement!=null)
		{
			obj=StartUp.sqlmapElement.get(sqlName);
			if(obj!=null)
				sqlqueryElement=(Element)obj;
		}
	}
	
	/**
	 * 获取查询条件的html字符串,变量数据从request或valuesmap中取
	 * @param request  
	 * @param valuesMap
	 * @return
	 */
	public String getCondition(HttpServletRequest request,HashMap valuesMap)
	{
		
		String result="";
		String lableprecent="11";
		String textprecent="22";
		int cols=0;
		
		StringBuffer stringBuffer=new StringBuffer();
		StringBuffer stringHidden=new StringBuffer();
		stringHidden.append("<div style='display:none;'>");
		List lstAction=null;
		if(this.sqlqueryElement!=null)
		{
			lstAction=this.sqlqueryElement.getChildren("condition");
		}
		int lstLen=0;
		if(lstAction!=null)
		{
			lstLen=lstAction.size();
			if(lstLen>0)
			{
				lstLen=0;
				Element ele=(Element)lstAction.get(0);
				String strCols=StringUtils.checkNullString(ele.getAttributeValue("cols"));
				cols=NumberUtils.formatToInt(strCols);
				lableprecent=StringUtils.checkNullString(ele.getAttributeValue("labprecent"));
				textprecent=StringUtils.checkNullString(ele.getAttributeValue("textprecent"));
				lstAction=ele.getChildren();
				if(lstAction!=null)
					lstLen=lstAction.size();
			}
		}
		Element element;
		
		if(cols<=0)
			cols=3;

		line=0;
		stringBuffer.append("<table  class=\"serachdivTable\">");
/*		stringBuffer.append("<tr>");
		for(int j=0;j<cols;j++)
		{
			if(j%2==0)
				stringBuffer.append("<td style=\"width:"+lableprecent+"%\">");
			else
				stringBuffer.append("<td style=\"width:"+textprecent+"%\">");
			stringBuffer.append("</td>");
		}
		stringBuffer.append("</tr>");
		
*/		
		String name;
		String isnewline;
		for(int i=0;i<lstLen;i++)
		{
			if(line%cols==0)
				stringBuffer.append("<tr>");
			line++;
			
			element=(Element)lstAction.get(i);
			name=element.getName();
			isnewline=StringUtils.checkNullString(element.getAttributeValue("isnewline"));
			if(name.equals("date"))
			{
				stringBuffer.append(getDate(element,request,valuesMap,lableprecent,textprecent));
			}
			else if(name.equals("select"))
			{
				stringBuffer.append(getSelect(element,request,valuesMap,lableprecent,textprecent));
			}
			else if(name.equals("dialog"))
			{
				stringBuffer.append(getDialog(element,request,valuesMap,lableprecent,textprecent));
			}
			else if(name.equals("hidden"))
			{
				stringHidden.append(getHidden(element,request,valuesMap,lableprecent,textprecent));
			}
			else
			{
				stringBuffer.append(getText(element,request,valuesMap,lableprecent,textprecent));
			}
			if(isnewline.equals("1"))
			{
				//stringBuffer.append("</tr>");
				this.line=0;
			}		
			
			if(line==0 || line%cols==0||i==(lstLen-1))
				stringBuffer.append("</tr>");
			
		}
		stringBuffer.append("</table>");
		stringHidden.append("</div>");
		stringBuffer.append(stringHidden.toString());
		return stringBuffer.toString();
	}
	
	
	private String getText(Element element,HttpServletRequest request,HashMap valuesMap,String lableprecent,String textprecent)
	{
		StringBuffer stringBuffer=new StringBuffer();
		if(element==null)
			return "";
		String id=StringUtils.checkNullString(element.getAttributeValue("id"));
		String name=StringUtils.checkNullString(element.getAttributeValue("name"));
		String displayName=StringUtils.checkNullString(element.getAttributeValue("displayname"));
		String value="";
		if(request!=null)
			value=StringUtils.checkNullString(request.getParameter(name));
		value=tranferQuot(value);
		String text=Internation.language(displayName);
		if(!text.equals(""))
			displayName=text;
		String colspan=StringUtils.checkNullString(element.getAttributeValue("colspan"));
		String rowspan=StringUtils.checkNullString(element.getAttributeValue("rowspan"));
		String onclick=StringUtils.checkNullString(element.getAttributeValue("onclick"));
		//String isnewline=StringUtils.checkNullString(element.getAttributeValue("isnewline"));
		stringBuffer.append("<td class=\"serachdivTableTd\" style='width:"+lableprecent+"%'>");
		
		stringBuffer.append(displayName);
		stringBuffer.append("：");
		stringBuffer.append("</td>");
		stringBuffer.append("<td style='width:"+textprecent+"%'");
		if(!colspan.equals(""))
		{
			stringBuffer.append(" colspan=\""+colspan+"\"");
		}
		if(!rowspan.equals(""))
		{
			stringBuffer.append(" rowspan=\""+rowspan+"\"");
		}
		stringBuffer.append(">");
		stringBuffer.append("<input type=\"text\"");
		if(!id.equals(""))
		{
			stringBuffer.append(" id=\""+id+"\"");
		}
		stringBuffer.append(" name=\""+name+"\"");
		stringBuffer.append(" class=\"textInput\"");
		stringBuffer.append(" value=\"");
		stringBuffer.append(value);
		stringBuffer.append("\" "); 
		if(!onclick.equals(""))
		{
			stringBuffer.append(" onclick=\"");
			stringBuffer.append(onclick);
			stringBuffer.append("\" ");
		}
		stringBuffer.append("/>");
		stringBuffer.append("</td>");
//		if(isnewline.equals("1"))
//		{
//			//stringBuffer.append("</tr>");
//			this.line=0;
//		}
		return stringBuffer.toString();
	}
	
	
	private String getDate(Element element,HttpServletRequest request,HashMap valuesMap,String lableprecent,String textprecent)
	{
		StringBuffer stringBuffer=new StringBuffer();
		if(element==null)
			return "";
		String name=StringUtils.checkNullString(element.getAttributeValue("name"));
		String displayName=StringUtils.checkNullString(element.getAttributeValue("displayname"));
		String value="";
		if(request!=null)
			value=StringUtils.checkNullString(request.getParameter(name));
		String text=Internation.language(displayName);
		if(!text.equals(""))
			displayName=text;
		String colspan=StringUtils.checkNullString(element.getAttributeValue("colspan"));
		String rowspan=StringUtils.checkNullString(element.getAttributeValue("rowspan"));
		//String isnewline=StringUtils.checkNullString(element.getAttributeValue("isnewline"));
		stringBuffer.append("<td class=\"serachdivTableTd\" style='width:"+lableprecent+"%'>");
		
		stringBuffer.append(displayName);
		stringBuffer.append("：");
		stringBuffer.append("</td>");
		stringBuffer.append("<td style='width:"+textprecent+"%'");
		if(!colspan.equals(""))
		{
			stringBuffer.append(" colspan=\""+colspan+"\"");
		}
		if(!rowspan.equals(""))
		{
			stringBuffer.append(" rowspan=\""+rowspan+"\"");
		}
		stringBuffer.append(">");
		stringBuffer.append("<input type=\"text\"");
		stringBuffer.append(" name=\""+name+"\"");
		stringBuffer.append(" class=\"textInput\"");
		stringBuffer.append(" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});\"");
		stringBuffer.append(" value=\"");
		stringBuffer.append(value);
		stringBuffer.append("\" "); 
		stringBuffer.append("/>");
		stringBuffer.append("</td>");
/*		if(isnewline.equals("1"))
		{
			//stringBuffer.append("</tr>");
			this.line=0;
		}*/
		return stringBuffer.toString();
	}
	
	private String getSelect(Element element,HttpServletRequest request,HashMap valuesMap,String lableprecent,String textprecent)
	{
		StringBuffer stringBuffer=new StringBuffer();
		if(element==null)
			return "";
		String name=StringUtils.checkNullString(element.getAttributeValue("name"));
		String displayName=StringUtils.checkNullString(element.getAttributeValue("displayname"));
		String value="";
		if(request!=null)
			value=StringUtils.checkNullString(request.getParameter(name));
		String text=Internation.language(displayName);
		if(!text.equals(""))
			displayName=text;
		String colspan=StringUtils.checkNullString(element.getAttributeValue("colspan"));
		String rowspan=StringUtils.checkNullString(element.getAttributeValue("rowspan"));
		String dictype=StringUtils.checkNullString(element.getAttributeValue("dictype"));
		String childrenname=StringUtils.checkNullString(element.getAttributeValue("childrenname"));
		String sql=StringUtils.checkNullString(element.getChildText("sql"));
		String isnull=StringUtils.checkNullString(element.getChildText("isnull"));
		String childrenUrl=StringUtils.checkNullString(element.getAttributeValue("childrenUrl"));
		stringBuffer.append("<td class=\"serachdivTableTd\" style='width:"+lableprecent+"%'>");
		
		stringBuffer.append(displayName);
		stringBuffer.append("：");
		stringBuffer.append("</td>");
		stringBuffer.append("<td style='width:"+textprecent+"%'");
		if(!colspan.equals(""))
		{
			stringBuffer.append(" colspan=\""+colspan+"\"");
		}
		if(!rowspan.equals(""))
		{
			stringBuffer.append(" rowspan=\""+rowspan+"\"");
		}
		stringBuffer.append(">");
		
		Select select=new Select(name,"",childrenname,dictype,"",value,null,sql,isnull,childrenUrl);
		stringBuffer.append(select.getSelect(request));
		stringBuffer.append("</td>");

		return stringBuffer.toString();
	}	
	
	private String getDialog(Element element,HttpServletRequest request,HashMap valuesMap,String lableprecent,String textprecent)
	{
		StringBuffer stringBuffer=new StringBuffer();
		if(element==null)
			return "";
		String id=StringUtils.checkNullString(element.getAttributeValue("id"));
		String name=StringUtils.checkNullString(element.getAttributeValue("name"));
		String displayName=StringUtils.checkNullString(element.getAttributeValue("displayname"));
		String onclick=StringUtils.checkNullString(element.getAttributeValue("onclick"));
		String value="";
		if(request!=null)
			value=StringUtils.checkNullString(request.getParameter(name));
		
		value=tranferQuot(value);
		String text=Internation.language(displayName);
		if(!text.equals(""))
			displayName=text;
		String colspan=StringUtils.checkNullString(element.getAttributeValue("colspan"));
		String rowspan=StringUtils.checkNullString(element.getAttributeValue("rowspan"));
		//String isnewline=StringUtils.checkNullString(element.getAttributeValue("isnewline"));
		stringBuffer.append("<td class=\"serachdivTableTd\" style='width:"+lableprecent+"%'>");
		
		stringBuffer.append(displayName);
		stringBuffer.append("：");
		stringBuffer.append("</td>");
		stringBuffer.append("<td style='width:"+textprecent+"%'");
		if(!colspan.equals(""))
		{
			stringBuffer.append(" colspan=\""+colspan+"\"");
		}
		if(!rowspan.equals(""))
		{
			stringBuffer.append(" rowspan=\""+rowspan+"\"");
		}
		stringBuffer.append(">");
		stringBuffer.append("<input type=\"text\"");
		if(!id.equals(""))
		{
			stringBuffer.append(" id=\""+id+"\"");
		}		
		stringBuffer.append(" name=\""+name+"\"");
		stringBuffer.append(" class=\"textInput\" readonly='true'");
		stringBuffer.append(" value=\"");
		stringBuffer.append(value);
		
		stringBuffer.append("\" "); 
		if(!onclick.equals(""))
		{
			stringBuffer.append(" onclick=\"");
			stringBuffer.append(onclick);
			stringBuffer.append("\" ");
		}
		stringBuffer.append("/>");
		/*
		stringBuffer.append("<li class=\"page_choose_button\" onmouseover=\"this.className='page_choose_button_hover'\"");
		stringBuffer.append("onmouseout=\"this.className='page_choose_button'\"/>选择</li>");
		*/
		//stringBuffer.append("<li class='page_search_button' id='com_btn_search' onmouseover=\"this.className='page_search_button_hover'\" onmouseout=\"this.className='page_search_button'\" onclick=\"showsearch()\">搜索</li>");

		
		stringBuffer.append("</td>");
//		if(isnewline.equals("1"))
//		{
//			//stringBuffer.append("</tr>");
//			this.line=0;
//		}
		return stringBuffer.toString();
	}		
	
	private String getHidden(Element element,HttpServletRequest request,HashMap valuesMap,String lableprecent,String textprecent)
	{
		StringBuffer stringBuffer=new StringBuffer();
		if(element==null)
			return "";
		String value="";
		String name=StringUtils.checkNullString(element.getAttributeValue("name"));
		if(request!=null)
			value=StringUtils.checkNullString(request.getParameter(name));
		value=tranferQuot(value);
		stringBuffer.append("<input type='hidden' ");
		stringBuffer.append(" name='"+name+"'");
		stringBuffer.append(" value=\"");
		stringBuffer.append(value);
		stringBuffer.append("\" "); 		
		stringBuffer.append("/>");
		return stringBuffer.toString();
	}		
	
	/**
	 * 将字符串中"号转义&quto;
	 * @param value
	 * @return
	 */
	private  String tranferQuot(String value)
	{
		String result=value;
		if(result!=null)
			result=result.replaceAll("\"", "&quot;");
		return result;
	}
	
	public static void main(String[] args)
	{
	//	System.out.println(tranferQuot("aaaa\"dd\""));
		
		/*
		String path="E:\\WorkSpace2\\eoms4\\WebRoot\\sqlconfig";
		StartUp.loadFile(path);
		QueryCondition queryCondition=new QueryCondition("SQL_QuerySimple_001");
		String result=queryCondition.getCondition(null,null);
		System.out.println(result);
		*/
	}

}
