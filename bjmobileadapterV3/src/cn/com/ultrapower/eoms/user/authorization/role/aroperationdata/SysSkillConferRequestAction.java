package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.eoms.user.authorization.bean.SysSkillConferBean;
import cn.com.ultrapower.eoms.user.comm.function.ConvertTimeToSecond;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class SysSkillConferRequestAction extends HttpServlet
{

	public void dogGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException 
	{
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException 
	{
		String id 					= Function.nullString(req.getParameter("id"));
		String skillcause 			= Function.nullString(req.getParameter("skillcause"));
		String skillcancelcause 	= Function.nullString(req.getParameter("skillcancelcause"));
		String skillstarttime		= Function.nullString(req.getParameter("skillstarttime"));
		String skillendtime			= Function.nullString(req.getParameter("skillendtime"));
		String skillcommissionid 	= Function.nullString(req.getParameter("skillcommissionid"));
		String skillcommissiontype	= Function.nullString(req.getParameter("skillcommissiontype"));
		String skillstatus			= Function.nullString(req.getParameter("skillstatus"));
		String skillroleid			= Function.nullString(req.getParameter("skillroleid"));
		String userid				= Function.nullString(req.getParameter("userid"));
		
		res.setContentType("text/html");
		PrintWriter out 	= res.getWriter();
		SysSkillConferBean skillconferinfo = new SysSkillConferBean();
		SysSkillConfer skillconfer = new SysSkillConfer();
		SysSkillConferSync skillsync = new SysSkillConferSync();
		boolean flag = false;
		skillconferinfo.setSkillconfer_CancelCause(skillcancelcause);
		skillconferinfo.setSkillconfer_Cause(skillcause);
		skillconferinfo.setSkillconfer_DealUserID(skillcommissionid);
		skillconferinfo.setSkillconfer_memo("");
		skillconferinfo.setSkillconfer_SkillID(skillroleid);
		skillconferinfo.setSkillconfer_Status(skillstatus);
		skillconferinfo.setSkillconfer_UserID(userid);
		

		
		if(id.equals(""))
		{
			long starttime 	= System.currentTimeMillis()/1000;
			skillconferinfo.setSkillconfer_StartTime(starttime);
			
			flag = skillconfer.skillconferInsert(skillconferinfo);
			//同步技能表
			skillsync.skillconfersync(skillroleid,skillcommissionid,skillcommissiontype);
		}
		else
		{
			
			
			if(skillstatus.equals("1"))
			{
				//同步技能表
				skillsync.skillconfersync(skillroleid,"","");
				long endtime	= System.currentTimeMillis()/1000;
				long sttime		= new Long(ConvertTimeToSecond.timeConvert(skillstarttime)).longValue();
				skillconferinfo.setSkillconfer_StartTime(sttime);
				skillconferinfo.setSkillconfer_EndTime(endtime);
			}else
			{
				long sttime		= new Long(ConvertTimeToSecond.timeConvert(skillstarttime)).longValue();
				skillconferinfo.setSkillconfer_StartTime(sttime);
			}
			flag = skillconfer.skillconferModify(id,skillconferinfo);
		}
		if(flag)
		{
			res.sendRedirect("../roles/rolecommissionshow.jsp");
		}
		else
		{
			out.println("失败！");
		}
		
	}

}
