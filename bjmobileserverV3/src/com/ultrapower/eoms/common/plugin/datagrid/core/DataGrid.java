package com.ultrapower.eoms.common.plugin.datagrid.core;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.jdom.Element;

import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.plugin.datagrid.grid.Limit;
import com.ultrapower.eoms.common.plugin.datagrid.util.RequestUtils;

public class DataGrid {

	private String name;
	private PageContext pageContext;
	private String leftToolAre;//工具条左侧区域
	private String conditionAre;//查询条件区域
	private String tablerow;//行数据
	private String gridtitle;//
	private String title;//标题
	private String ititle;//国际化的标题
	private String action;
	private String tquery;
	HttpServletRequest request;
	private String sqlName;
	public String getTquery() {
		return tquery;
	}

	public void setTquery(String tquery) {
		this.tquery = tquery;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public DataGrid(PageContext p_pageContext,String sqlName)
	{
		this.sqlName=sqlName;
		pageContext=p_pageContext;
	}

	/**
	 * 输出DataGrid视图
	 */
	public void dataView()
	{

		JspWriter out = pageContext.getOut();
		request=(HttpServletRequest)pageContext.getRequest();
		//System.out.println(request.getParameter("workSheetTitle2"));
		//System.out.println(request.getParameter("workSheetTitle"));
		String sorfield=StringUtils.checkNullString(request.getParameter(GridConstants.HIDDEN_SORTFIELD));
		String sorttype=StringUtils.checkNullString(request.getParameter(GridConstants.HIDDEN_SORTTYPE));
		String id = StringUtils.checkNullString(request.getParameter(GridConstants.HIDDEN_MENUID));
		if(sorttype.equals(""))
		{
			sorttype="0";//0升序 
		}
		Limit limit=RequestUtils.getWebLimit(request);
		int totalpage=0;
		if(limit!=null)
			totalpage=limit.getTotalPage();
		try 
		{
			//DataGridFnc.getJSFunction(out);

			if(action!=null && !action.equals(""))
				out.println("<form method='post' action='"+action+"'>");
			else
				out.println("<form method='post'>");

			getTitle(out);
			out.println("<div class='content'>"); 
			getToolBoor(out,limit);
			getConditionAre(out);
			out.println("	<div class='scroll_div' id='center'>"); //<!--滚动条div-->

			
			out.println("	<table id='tab' width='100%'>");
			if(gridtitle!=null)
				out.println(this.gridtitle);
			if(tablerow!=null)
				out.println(tablerow);
			out.println("	</table>");
			//out.println("<div style=\"padding-left:30px;padding-top:30px;\"/>"); 
			//out.println("</div>");//out.println("<div class='scroll_div' id='center'>");
			out.println("	</div>");

			out.println("<div >");
			out.println("<input id='"+GridConstants.HIDDEN_TOTAL_PAGES+"' name='"+GridConstants.HIDDEN_TOTAL_PAGES+"' type='hidden' value='"+totalpage+"'/> ");
			out.println("<input id='"+GridConstants.HIDDEN_CHECKBOX_SELECTVALUES+"' name='"+GridConstants.HIDDEN_CHECKBOX_SELECTVALUES+"' type='hidden' value=''/> ");
			out.println("<input id='"+GridConstants.HIDDEN_ISTRANFER+"' name='"+GridConstants.HIDDEN_ISTRANFER+"' type='hidden' value=''/> ");
			out.println("<input id='"+GridConstants.HIDDEN_SORTFIELD+"' name='"+GridConstants.HIDDEN_SORTFIELD+"' type='hidden' value='"+sorfield+"'/> ");
			out.println("<input id='"+GridConstants.HIDDEN_SORTTYPE+"' name='"+GridConstants.HIDDEN_SORTTYPE+"' type='hidden' value='"+sorttype+"'/> ");
			out.println("<input id='"+GridConstants.HIDDEN_MENUID+"' name='"+GridConstants.HIDDEN_MENUID+"' type='hidden' value='"+id+"'/> ");
			out.println("</div>");
			out.println("</div>");
			out.println("</form>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setGridtitle(String gridtitle)
	{
		this.gridtitle=gridtitle;
	}
	public void setTablerow(String tablerow)
	{
		this.tablerow=tablerow;
	}
	public void setLeftToolAre(String leftToolAre)
	{
		this.leftToolAre=leftToolAre;
	}
	public void setConditionAre(String conditionAre)
	{
		this.conditionAre=conditionAre;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getItitle() {
		return ititle;
	}

	public void setItitle(String ititle) {
		this.ititle = ititle;
	}

	private void getTitle(JspWriter out) throws IOException
	{
		
		if(title!=null && !"".equals(title))
		{
			out.println("<div class='title_right'>");
			out.println("	<div class='title_left'>");
			out.println("		<span class='title_bg'>");
			out.println("			<span class='title_icon2'>"+title+"</span>");
			out.println("			</span>");
			getTQueryHtml(out);
			out.println("		<span class='title_xieline'>");
			out.println("		</span>");
			out.println("	</div>");
			out.println("</div>");
		}
		else if(!"".equals(StringUtils.checkNullString(ititle)))
		{
			out.println("<div class='title_right'>");
			out.println("	<div class='title_left'>");
			out.println("		<span class='title_bg'>");
			out.println("			<span class='title_icon2'>");
			out.println(Internation.language(ititle));
			out.println("			</span>");
			out.println("		</span>");
			getTQueryHtml(out);
			out.println("		<span class='title_xieline'>");
			out.println("		</span>");
			out.println("	</div>");
			out.println("</div>");
		}
	}
	
	private void getTQueryHtml(JspWriter out)  throws IOException
	{
		
		out.println("<span style=\"float:right;\">");
		if(this.tquery!=null &&"true".equals(tquery))
		{
			String tName=StringUtils.checkNullString(request.getParameter(GridConstants.TQUERY_NAME));
			//String value=StringUtils.checkNullString(request.getParameter(tName));
			List lstCondition=this.getConditionElement();
			int lstLen=0;
			if(lstCondition!=null)
				lstLen=lstCondition.size();
			
			String value=StringUtils.checkNullString(request.getParameter(GridConstants.TQUERY_VALUE));
			
			out.println("<select name='"+GridConstants.TQUERY_NAME+"'> style=\"margin-top: 0px;\"");
			Element element;
			String istquery;
			for(int i=0;i<lstLen;i++)
			{
				element=(Element)lstCondition.get(i);
				String name=StringUtils.checkNullString(element.getAttributeValue("name"));
				istquery=StringUtils.checkNullString(element.getAttributeValue("tquery"));
				if(istquery.equals("true"))
				{
					String displayName=StringUtils.checkNullString(element.getAttributeValue("displayname"));
					String text=Internation.language(displayName);
					if(!text.equals(""))
						displayName=text;
					
					
					if(tName.equals(name))
					{
						out.println("<option value='"+name+"' selected > ");	
					}
					else
					{
						out.println("<option value='"+name+"'> ");
					}
					out.println(text);
					out.println("</option>");
				}
			}
			out.println("</select>");
			out.println("<input name='"+GridConstants.TQUERY_VALUE+"' style='width:200px' value='"+value+"' />" +
					"<input type='submit' value='搜索' onclick='tquerysubmit();' class='searchButton' onmouseover=\"this.className='searchButton_hover'\" onmouseout=\"this.className='searchButton'\"  />"
					+ "<input name='searchB' type='button' value='高级搜索' class='searchadv_button'  onclick='showsearch()' onmouseover=\"this.className='searchadv_button_hover'\" onmouseout=\"this.className='searchadv_button'\"/>"
					);
		}				
		out.println("</span>");
	}
	private List getConditionElement()
	{
		Element sqlqueryElement=null;
		if(StartUp.sqlmapElement!=null)
		{
			Object obj=StartUp.sqlmapElement.get(sqlName);
			if(obj!=null)
				sqlqueryElement=(Element)obj;
		}
		
		List lstCondition=null;
		if(sqlqueryElement!=null)
		{
			lstCondition=sqlqueryElement.getChildren("condition");
			Element ele=(Element)lstCondition.get(0);
			lstCondition=ele.getChildren();
				
		}
		return lstCondition;
	}
	
	private void getConditionAre(JspWriter out) throws IOException
	{
		if(conditionAre!=null && !conditionAre.equals(""))
		{
			if(name!=null)
			{
				//out.println("<div id='"+name+"_serachdiv' class='serachdiv' style='display:none;z-index:1;position:absolute;top:35px;left:0px;background-color:#CCCCCC;width:100%;'>");
				out.println("<div id='"+name+"_serachdiv' class='serachdiv' style='display:none;background-color:#CCCCCC;width:100%;'>");
			}
			else
			{
				out.println("<div id='serachdiv' class='serachdiv' style='display:none;background-color:#CCCCCC;width:100%;'>");
			}
//			out.println("	<div class='type_condition'><span>请输入查询条件</span></div>");
			out.println(conditionAre);
			out.println("</div>");
		}

	}
	/**
	 * 工具条
	 * @param out
	 * @param limit
	 * @throws IOException
	 */
	private void getToolBoor(JspWriter out,Limit limit)throws IOException
	{
		request=(HttpServletRequest)pageContext.getRequest();
		String contextPath = request.getContextPath();
		int number=0;
		if(leftToolAre==null)//如果没有lefttoobar工具栏标签
			return ;
		out.println("<input type='hidden' id='"+GridConstants.HIDDEN_PAGES_SIZE+"' name='"+GridConstants.HIDDEN_PAGES_SIZE+"' value='"+limit.getPageSize()+"'/>" );
		out.println("<table style='font-size: 12px; background-color:#999999; border:0px; color: #FFFFFF; padding-top:3px;' width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		out.println("	<tr>");
		out.println("		<td style='vertical-align:middle;' align=\"left\">");
		out.println("			<img src='"+contextPath+"/workflow/sheet/images/refresh.png' alt=\"刷新\" style=\"cursor:hand;\" onclick=\"location.reload();\"/>");
		out.print(leftToolAre);
		out.println("		</td>");
		out.println("		<td style='vertical-align:middle;' align=\"right\">");
		out.println("			<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		out.println("				<tr>");
		out.println("					<td style='vertical-align:middle;' align=\"right\"><NOBR>");
		out.println("						<img src='"+contextPath+"/workflow/sheet/images/left1.png' alt='第一页' title='第一页' style='cursor:hand;' onclick=\"return tranferPage('frist')\"/>");
		out.println("						<img src='"+contextPath+"/workflow/sheet/images/left.png' alt='上一页' title='上一页' style='cursor:hand;' onclick=\"return tranferPage('previous')\"/>");
		out.println("					</NOBR></td>");
		out.println("					<td style='vertical-align:middle;width:10px;' align=\"right\"><NOBR>");
	    number=0;
	    int currentpage=0;
	    int pagesize=0;
	    int totalrows=0;
	    if(limit!=null)
		{
			number=limit.getTotalPage();
			currentpage=limit.getPage();
			pagesize=limit.getPageSize();
			totalrows=limit.getTotalRows();
		}
		out.println("						<input type='hidden' id='"+GridConstants.HIDDEN_CURRENT_PAGE+"' name='"+GridConstants.HIDDEN_CURRENT_PAGE+"' value='"+currentpage+"'/>" );
		out.println("						" + currentpage+"/"+number + "页 共"+totalrows+"条"+"");
		out.println("					</NOBR></td>");
		out.println("					<td style='vertical-align:middle;' align=\"right\"><NOBR>");
		out.println("						<img src='"+contextPath+"/workflow/sheet/images/right.png' alt='下一页' title='下一页' style='cursor:hand;' onclick=\"return tranferPage('next')\"/>");
		out.println("						<img src='"+contextPath+"/workflow/sheet/images/right1.png' alt='最后一页' title='最后一页' style='cursor:hand;' onclick=\"return tranferPage('last')\"/>");
		out.println("			</NOBR></td></tr></table>");
		out.println("</td></tr></table>");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
