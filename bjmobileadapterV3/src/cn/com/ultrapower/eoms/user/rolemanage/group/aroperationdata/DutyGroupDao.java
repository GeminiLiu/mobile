package cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.rolemanage.group.bean.GroupInfo;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GroupFind;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GroupSynchronization;


/**
 * <p>Description:封装组信息的提交逻辑<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-18
 */
public class DutyGroupDao extends HttpServlet {
	
	private String groupName;
	private String groupFullname;
	private String groupParentid;
	private String groupType;
	private String groupOrderBy;
	private String groupPhone;
	private String groupFax;
	private String groupStatus;
	private String groupCompanyid;
	private String groupCompanytype;
	private String groupDesc;
	private String c1;	
	private String groupDnId;	
	
	/**
	 * Constructor of the object.
	 */
	public DutyGroupDao() {
		groupName        = "";
		groupFullname    = "";
		groupParentid    = "";
		groupType        = "";
		groupOrderBy	 = "";
		groupPhone 		 = "";
		groupFax         = "";
		groupStatus      = "";
		groupCompanyid   = "";
		groupCompanytype = "";
		groupDesc        = "";
		c1				 = "";
		groupDnId		 = "";
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
		
		GroupInfo groupInfo = new GroupInfo();
		
		groupName        = request.getParameter("groupName");
		groupFullname    = request.getParameter("groupFullname");
		groupParentid    = request.getParameter("groupParentid");
		groupType        = request.getParameter("groupType");
		groupOrderBy	 = request.getParameter("groupOrderBy");
		groupPhone       = request.getParameter("groupPhone");
		groupFax         = request.getParameter("groupFax");
		groupStatus      = request.getParameter("groupStatus");
		groupCompanyid   = request.getParameter("groupCompanyid");
		groupCompanytype = request.getParameter("groupCompanytype");
		groupDesc        = request.getParameter("groupDesc");
		groupDnId		 = request.getParameter("groupDnId");
		
		
		if(groupFullname == null||groupFullname.equals(""))
		{
			groupInfo.setGroupFullname("");
		}
		else
		{
			groupInfo.setGroupFullname(groupFullname);
		}
		
		if(groupParentid == null||groupParentid.equals(""))
		{
			response.sendRedirect("../roles/dutymanagergroupadd.jsp?error=001");
		}
		else
		{
			groupInfo.setGroupParentid(groupParentid);
		}
		
		if(groupType == null||groupType.equals(""))
		{
			groupInfo.setGroupType("");
		}
		else
		{
			groupInfo.setGroupType(groupType);
		}
		
		if(groupOrderBy == null||groupOrderBy.equals(""))
		{
			groupInfo.setGroupOrderBy("");
		}
		else
		{
			groupInfo.setGroupOrderBy(groupOrderBy);
		}
		
		if(groupPhone == null||groupPhone.equals(""))
		{
			groupInfo.setGroupPhone("");
		}
		else
		{
			groupInfo.setGroupPhone(groupPhone);
		}
		
		if(groupFax == null||groupFax.equals(""))
		{
			groupInfo.setGroupFax("");
		}
		else
		{
			groupInfo.setGroupFax(groupFax);
		}
		
		if(groupStatus == null||groupStatus.equals(""))
		{
			groupInfo.setGroupStatus("");
		}
		else
		{
			groupInfo.setGroupStatus(groupStatus);
		}
		
		if(groupCompanyid == null||groupCompanyid.equals(""))
		{
			groupInfo.setGroupCompanyid("");
		}
		else
		{
			groupInfo.setGroupCompanyid(groupCompanyid);
		}
		
		if(groupCompanytype == null||groupCompanytype.equals(""))
		{
			groupInfo.setGroupCompanytype("");
		}
		else
		{
			groupInfo.setGroupCompanytype(groupCompanytype);
		}
		
		if(groupDesc == null||groupDesc.equals(""))
		{
			groupInfo.setGroupDesc("");
		}
		else
		{
			groupInfo.setGroupDesc(groupDesc);
		}
		
		//判断添加还是修改
		c1					= request.getParameter("c1");
		Group group			= new Group();
		GroupFind groupFind	= new GroupFind();
		
		try
		{
			if(c1 == null||c1.equals(""))
			{
				if(groupName == null||groupName.equals(""))
				{
					groupInfo.setGroupName("");
				}
				else
				{
					//如果相同节点下有相同组名称
					if(groupFind.isDuplicate(groupName,groupParentid))
					{
						response.sendRedirect("../roles/dutymanagergroupadd.jsp?error=002");
					}
					//否则
					else
					{
						groupInfo.setGroupName(groupName);
						if(groupDnId == null||groupDnId.equals(""))
						{
							groupInfo.setGroupDnId("");
						}
						else
						{
							groupInfo.setGroupDnId(groupDnId);
						}
						
						group.insertGroup(groupInfo);
						response.sendRedirect("../roles/dutymanagergroupadd.jsp?targetflag=yes");
					}	
				}
			}
			else
			{
				//如果相同节点下有相同组名称
				if(groupFind.isDuplicate(c1,groupName,groupParentid))
				{
					response.sendRedirect("../roles/dutymanagergroupedit.jsp?id="+c1+"&error=001");
				}
				//否则
				else
				{
					groupInfo.setGroupName(groupName);
					if(groupDnId == null||groupDnId.equals(""))
					{
						groupInfo.setGroupDnId("");
					}
					else
					{
						groupInfo.setGroupDnId(groupDnId+c1+";");
					}
					
					int intId = (groupFind.findModify(c1).getC630000030()).intValue();
					groupInfo.setGroupIntId(intId);
					
					//验证是否需要同步其子节点的相应信息的条件
					String oldParentId	= groupFind.findModify(c1).getC630000037();
					String newParentId	= groupInfo.getGroupParentid();
					String oldGroupType	= groupFind.findModify(c1).getC630000021();
					String newGroupType	= groupInfo.getGroupType();
					
					if(!oldParentId.equals(newParentId)||!oldGroupType.equals(newGroupType))
					{
						//同步相应组信息和用户信息
						GroupSynchronization groupSynchronization = new GroupSynchronization();
						groupSynchronization.modifySynchronization (c1,groupInfo);
						//修改组信息
						group.modifyGroup(groupInfo,c1);
						response.sendRedirect("../roles/groupshow.jsp");
					}
					else
					{
						group.modifyGroup(groupInfo,c1);
						response.sendRedirect("../roles/groupshow.jsp");
					}
				}
			}				
		}
		catch(Exception e)
		{
			e.printStackTrace();
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

