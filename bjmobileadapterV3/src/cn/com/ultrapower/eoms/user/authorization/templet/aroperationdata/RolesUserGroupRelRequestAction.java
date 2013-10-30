package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesUserGroupRelBean;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage.SkillAndSendSync;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class RolesUserGroupRelRequestAction extends HttpServlet
{


	public void dogGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
    	String RoleRel_UserID 	= "";
//    	String RoleRel_RoleID 	= "";
    	String RoleRel_OrderBy 	= "";
    	String RoleRel_Desc 	= "";
    	String RoleRel_memo 	= "";
    	String RoleRel_memo1 	= "";
    	
    	boolean flag 					= false;
		res.setContentType("text/html");
		PrintWriter out 				= res.getWriter();
		
		RolesUserGroupRel rolesusergrouprel 			= new RolesUserGroupRel();
		RolesUserGroupRelBean rolesusergrouprelinfo 	= new RolesUserGroupRelBean();
		
		RoleRel_UserID 			= Function.nullString(req.getParameter("userid"));
		
		String roleidvalue[]       = req.getParameterValues("roleid");

//		RoleRel_RoleID 			= Function.nullString(req.getParameter("roleid"));
		RoleRel_OrderBy 		= Function.nullString(req.getParameter("orderby"));
		RoleRel_Desc 			= Function.nullString(req.getParameter("desc")); 
		RoleRel_memo 			= Function.nullString(req.getParameter("memo")); 
		RoleRel_memo1 			= Function.nullString(req.getParameter("memo1"));
		
		String peopleid         = req.getParameter("peopleid");
		String groupid         = req.getParameter("groupid");
		
		String rolesname = Function.nullString(req.getParameter("rolesname2"));		
		String username = Function.nullString(req.getParameter("username"));
		String groupname = Function.nullString(req.getParameter("groupname"));
		
		String rolesnameUTF = java.net.URLEncoder.encode(rolesname,"utf-8");
		String usernameUTF = java.net.URLEncoder.encode(username,"utf-8");
		String groupnameUTF = java.net.URLEncoder.encode(groupname,"utf-8");

		
		rolesusergrouprelinfo.setRoleRel_Desc(RoleRel_Desc);
		rolesusergrouprelinfo.setRoleRel_memo(RoleRel_memo);
		rolesusergrouprelinfo.setRoleRel_memo1(RoleRel_memo1);
		rolesusergrouprelinfo.setRoleRel_OrderBy(RoleRel_OrderBy);
		//婢舵牕鐪板顏嗗箚娑撻缚顬戦懝鐫瓺閻ㄥ嫭鏆熺紒鍕剁礉閸愬懎鐪板顏嗗箚娑撹櫣鏁ら幋铚傜瑢缂佸嫮娈戦弫鎵矋闂�鍨�?绗�??
		List listsql = new ArrayList();
		boolean bool = false;//鏍囩ず鏄惁瀛樺湪閲嶅璁板綍 
		boolean bool2 = true;//鏍囩ず鏄惁宸茶瀹氶噸瀹氬悜鐨剈rl
		for(int i=0;i<roleidvalue.length;i++)
		{
			rolesusergrouprelinfo.setRoleRel_RoleID(roleidvalue[i]);
			String[] str = RoleRel_UserID.split(",");
			for (int j = 0; j < str.length; j++) 
			{
				String[] tmpinfo = str[j].split(";");
				StringBuffer sql = new StringBuffer();
				bool = false;
				
				if(tmpinfo[1].equals("1"))
				{
					rolesusergrouprelinfo.setRoleRel_UserID("");
					rolesusergrouprelinfo.setRoleRel_GroupID(tmpinfo[0]);
					sql.append(" select rolesManagepo.c660000001 from RolesManagepo rolesManagepo, RolesUserGroupRelpo rolesUserGroupRelpo where rolesUserGroupRelpo.c660000027='"+ tmpinfo[0] +"'");
					sql.append(" and rolesUserGroupRelpo.c660000026 is null");
					sql.append(" and rolesUserGroupRelpo.c660000028='"+ roleidvalue[i] +"' and rolesUserGroupRelpo.c660000028=rolesManagepo.c1");
				}
				else
				{
					rolesusergrouprelinfo.setRoleRel_UserID(tmpinfo[0]);
					rolesusergrouprelinfo.setRoleRel_GroupID(tmpinfo[1]);
					sql.append(" select rolesManagepo.c660000001 from RolesManagepo rolesManagepo, RolesUserGroupRelpo rolesUserGroupRelpo where rolesUserGroupRelpo.c660000027='"+ tmpinfo[1] +"'");
					sql.append(" and rolesUserGroupRelpo.c660000026='"+tmpinfo[0]+"'");
					sql.append(" and rolesUserGroupRelpo.c660000028='"+ roleidvalue[i] +"' and rolesUserGroupRelpo.c660000028=rolesManagepo.c1");
				}
				try {
					listsql = HibernateDAO.queryObject(sql.toString());
					if(listsql.size()>0){
						bool = true;
						if(roleidvalue.length==1 && str.length==1){
							if(!rolesname.equals("") || rolesname == null)
							{							
								res.sendRedirect("../roles/rolesusergroupreladd_rolesmanage.jsp?error=001&rolesname="+rolesnameUTF+"");
							}
							else if(!username.equals("") || username == null)
							{							
								res.sendRedirect("../roles/rolesusergroupreladd_people.jsp?error=001&userFullname="+usernameUTF+"&peopleid='"+peopleid+"'"+"&groupid='"+groupid+"'");
							}
							else if(!groupname.equals("") || groupname == null)
							{							
								res.sendRedirect("../roles/rolesusergroupreladd_group.jsp?error=001&groupname="+groupnameUTF+"&intId='"+groupid+"'"+"&name='"+groupnameUTF+"'");
							}
							else{						
								res.sendRedirect("../roles/rolesusergroupreladd.jsp?error=001");
							}
							bool2 = false;
						}else{
							continue;
						}
						
					}else if(bool == false){
						flag = rolesusergrouprel.rolesUserGroupRelInsert(rolesusergrouprelinfo);
					}
				}catch (HibernateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}								
			}
		}
		//if(flag){
		if(bool2){
			String contextPath = req.getContextPath();
			res.sendRedirect(contextPath+"/roles/rolesusergroupreladd.jsp?targetflag=yes");		
		}		
	}

}
