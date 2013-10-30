package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import cn.com.ultrapower.system.util.*;
	
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesUserGroupRelBean;
import cn.com.ultrapower.eoms.util.user.UserInformation;

public class PeopleRolesRightsTraAction extends HttpServlet {


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

		doPost(request, response);
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

		String tuser 		= "";
		String flag         = "";
		UserInformation userInfo = (UserInformation) request.getSession().getAttribute("userInfo");
		if(null!=userInfo){
		  tuser = userInfo.getUserLoginName();
		}
		String tran_type     = request.getParameter("ttype");          //移交方式 3种和分别对应的值：1 移交后，移交方的权限清空 ，2 移交方的权限临时赋给接收方，3 移交方的权限永久赋给接收方
		String acc_type      = request.getParameter("atype");          //接收方式 2种和分别对应的值：1 追加，2 覆盖
		String traloginname  = request.getParameter("t_loginname");    //移交方
		String accloginname  = request.getParameter("a_loginname");    //接收方
		String accgroupid    = request.getParameter("accmidcode");     //接收方所属组id
		String tra_roleids   = request.getParameter("troleid");        //逐项移交时传入的roleids
		String tra_detailids = request.getParameter("tdetailroleid");  //逐项移交时传入的细分角色id
		String agroupid      = "";
	
		if(accgroupid!=null && !"".equals(accgroupid)){
		   for(int i=0;i<15-accgroupid.length();i++){
			   agroupid = agroupid+"0"; 
		   }
		   agroupid = agroupid+accgroupid;
		}
		PeopleRolesRightsTraImpl a_peoplerolesrightstra = new PeopleRolesRightsTraImpl();
		List<String> a_roleids   = new ArrayList();
		List<String> t_roleids   = new ArrayList();
		List<String> t_detailids = new ArrayList();
		List<String> a_detailids = new ArrayList();
		//接收方所拥有的角色
		String a_roleid = "";
		PeopleRolesRightsTraImpl peoplerolesrightstra = new PeopleRolesRightsTraImpl();
		List a_list = peoplerolesrightstra.getRolesByPeople(tuser, accloginname);
		Iterator a_it = a_list.iterator();
		while(a_it.hasNext()){
			Object[] a_obj = (Object[])a_it.next();
			a_roleid       = FormatString.CheckNullString((String)a_obj[4]);
			a_roleids.add(a_roleid);
		}
		//接收方所拥有的细分角色
		a_detailids = peoplerolesrightstra.getUserDetailRolesId(accloginname);
		
		//移交方所拥有的角色
		String[] troleids = tra_roleids.split(";");
		if(!tra_roleids.equals("") && !tra_roleids.equals("null") && troleids.length>0){
			for(int i=0;i<troleids.length;i++){
				t_roleids.add(troleids[i]);
			}
		}
		//移交方所拥有的细分角色
		String[] tdetailids = tra_detailids.split(";");
		if(!tra_detailids.equals("") && !tra_detailids.equals("null") && tdetailids.length>0){
			for(int i=0;i<tdetailids.length;i++){
				t_detailids.add(tdetailids[i]);
			}
		}
	   	flag = peoplerolesrightstra.insertInToRolesUserGroupRel(t_roleids,a_roleids,accloginname,traloginname,tuser,agroupid,tran_type,t_detailids,a_detailids,acc_type);
	   	if(flag.equals("")){
	   	   flag = "success";
	   	}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(flag);
		out.flush();
		out.close();
		
	}

}
