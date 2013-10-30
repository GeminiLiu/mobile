package cn.com.ultrapower.eoms.user.sla.bussiness;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import cn.com.ultrapower.eoms.user.sla.hibernate.dbmanage.GetActionType;
import cn.com.ultrapower.eoms.user.sla.hibernate.dbmanage.GetSlaMailInfoList;
import cn.com.ultrapower.eoms.user.sla.hibernate.po.SysActionpo;


/**
 * 日期 2006-12-4
 * @author xuquanxing
 * 对动作进行编辑操作时统一由该类来调用,
 */
public class ActionHandle extends HttpServlet {
	static final Logger logger = (Logger) Logger.getLogger(ActionHandle.class);
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		 doPost(req,res);
	}
    
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 该方法首先根据参数actionid，查询动作表获得该id对应的表明，以确定下一步查询id值时去那个表查找
	 */
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException
	{
		String actionid                       = req.getParameter("id");//�ö����id
		String actiontypeid                   = req.getParameter("actionid");//��øö�������Ķ�������
		logger.info("actiontypeid are null");
		List list                             = null;
		String objecttable                    = "";
		GetSlaMailInfoList getslamailinfolist = new GetSlaMailInfoList();
		GetActionType       getactiontype     = new GetActionType();
		try
		{
		    List actiontypelist = getactiontype.getActionTypeResult(actiontypeid);
		    
		    System.out.println(actiontypelist.size());
		    System.out.println(actiontypeid);
		    System.out.println(actionid);
		    if(!actiontypelist.isEmpty())
		    {
		       for(Iterator it=actiontypelist.iterator();it.hasNext();)
			   {
				   SysActionpo sysaction  = ( SysActionpo)it.next();
				   objecttable            = sysaction.getC600000010();
				   System.out.print(objecttable);
			   }
		    }
		   }catch(Exception e)
		   {
				logger.info("[101]ActionHandle.dopost"+e.getMessage());
		   }
		try
		{
			list = getslamailinfolist.getResult(objecttable,actionid);
			System.out.println("mailinfo: "+list.size());
			HttpSession session =req.getSession();
			session.setAttribute("action",list);
			if(objecttable.equals("SysSlaSmspo"))
			{
				res.sendRedirect("slasmsinfo.jsp");
			}
			if(objecttable.equals("SysSlaMailpo"))
			{
				res.sendRedirect("slamailinfo.jsp");
			}
			if(objecttable.equals("SysSlaWorkFlowManagepo"))
			{
				res.sendRedirect("slaupdateinfo.jsp");
			}
			
		}catch(Exception ex)
		{
			logger.info("[102]ActionHandle.Exception"+ex.getMessage());

			res.setContentType("text/html;charset=utf-8");
			PrintWriter out = res.getWriter();
			out.println("<HTML><HEAD><TITLE>Guestbook</TITLE></HEAD>");
		    out.println("<BODY>");
		    out.println("该动作没有动作类型信息，请查证后再操作");
		    out.println("</BODY>");
		    out.println("</HEAD>");
		    out.println("</HTML>");
		    out.flush();
		    logger.info("actiontypeid is null");
		    return;
		}
		
	}
}
