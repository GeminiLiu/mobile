package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.config.entryid.aroperationdata.GetNewId;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceattributevalue;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;

/**
 * <p>Description:封装资源属性值信息的提交逻辑<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-30
 */
public class SourceAttValueAction extends HttpServlet {

	private String sourceattvalue_attid;
	private String sourceattvalue_value;
	private String sourceattvalue_id;
	private String source_id;
	private String sourceattvalueBelongrow;

	/**
	 * Constructor of the object.
	 */
	public SourceAttValueAction() {
		
		sourceattvalue_attid		= "";
		sourceattvalue_value		= "";
		sourceattvalue_id			= "";
		source_id					= "";
		sourceattvalueBelongrow		= "";
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//从form.properties中获得数据
		GetFormTableName tablename				= new GetFormTableName();
		String sourceattributevalue				= tablename.GetFormName("sourceattributevalue");
		String sourceattributevalue_belongrow	= tablename.GetFormName("sourceattvalue_belongrow");
		String sourceattributevalue_id			= tablename.GetFormName("sourceattvalue_id");
		
		Sourceattributevalue attributeValue = new Sourceattributevalue();
		source_id					= request.getParameter("sourceid");	
		
		//获得自增的字段ID
		GetNewId getid=new GetNewId();
		
		sourceattvalue_attid		= request.getParameter("sourceattvalue_attid");
		sourceattvalue_value		= request.getParameter("sourceattvalue_value").trim();
		SourceAttValueOp attValue	= new SourceAttValueOp();
		
		String temp_attid[] = sourceattvalue_attid.split(",");
		String temp_value[] = sourceattvalue_value.split(",");
		
		sourceattvalueBelongrow = request.getParameter("sourceattvalueBelongrow");
		sourceattvalue_id	= request.getParameter("sourceattvalue_id");
		//添加资源属性值
		if(sourceattvalueBelongrow==null||sourceattvalueBelongrow.equals(""))
		{
			attributeValue.setSourceattvalueBelongrow(getid.getnewid(sourceattributevalue,sourceattributevalue_belongrow));
			for(int i=0;i<temp_attid.length;i++)
			{	
				//如果属性值为空
				if(temp_value[i].equals("null"))
				{
					attributeValue.setSourceattvalueId(new Long(getid.getnewid(sourceattributevalue,sourceattributevalue_id)));
					attributeValue.setSourceattvalueAttid(new Long(temp_attid[i]));
					attributeValue.setSourceattvalueValue("");
				}
				//若属性值不为空
				else
				{
					attributeValue.setSourceattvalueId(new Long(getid.getnewid(sourceattributevalue,sourceattributevalue_id)));
					attributeValue.setSourceattvalueAttid(new Long(temp_attid[i]));
					
					//modify by wangyanguang 2007-3-20
					//当用户修改某一资源URL值时，判断值是不为‘&’如是则用'&amp;'代替，否则不变。
					String returnstr = Function.getReplaceString(temp_value[i]);
					
					attributeValue.setSourceattvalueValue(returnstr);
				}
				try
				{
					attValue.sourceAttValueInsert(attributeValue);
					response.sendRedirect("../roles/sourceattvalueshow.jsp?sourceid="+source_id);
				}
				catch(Exception e)
				{
					e.getMessage();
				}				
			}				
		}
		//修改资源属性值
		else
		{
			
			attributeValue.setSourceattvalueBelongrow(sourceattvalueBelongrow);
			String temp_id[] = sourceattvalue_id.split(",");
			try
			{
				for(int i=0;i<temp_attid.length;i++)
				{	
					//如果为新加资源属性
					if(temp_id[i]==null||temp_id[i].equals("0"))
					{	
						attributeValue.setSourceattvalueId(new Long(getid.getnewid(sourceattributevalue,sourceattributevalue_id)));
						attributeValue.setSourceattvalueAttid(new Long(temp_attid[i]));
						attributeValue.setSourceattvalueValue(Function.nullString(temp_value[i]));
						attValue.sourceAttValueInsert(attributeValue);
					}
					//若为已有资源属性
					else
					{	
						attributeValue.setSourceattvalueId(new Long(temp_id[i]));
						attributeValue.setSourceattvalueAttid(new Long(temp_attid[i]));
						
						//modify by wangyanguang 2007-3-20
						//当用户修改某一资源URL值时，判断值是不为‘&’如是则用'&amp;'代替，否则不变。
						String returnstr = Function.getReplaceString(temp_value[i]);
						attributeValue.setSourceattvalueValue(returnstr);
						attValue.sourceAttValueModify(attributeValue);
					}
				}
				response.sendRedirect("../roles/sourceattvalueshow.jsp?sourceid="+source_id);
			}
			catch(Exception e)
			{
				e.getMessage();
			}
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
