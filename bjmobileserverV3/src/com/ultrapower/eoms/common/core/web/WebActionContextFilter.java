package com.ultrapower.eoms.common.core.web;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ultrapower.accredit.util.GetUserUtil;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import com.ultrapower.eoms.ultrasm.model.ResourceUrl;
import com.ultrapower.eoms.ultrasm.service.PrivilegeManagerService;
import com.ultrapower.eoms.ultrasm.service.ResourceUrlManagerService;

/**
 * 访问web时加载的类
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-5-26
 */
public class WebActionContextFilter implements Filter
{

	public void destroy() 
	{ 

	}

	
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException
	{
//		HttpServletRequest request = (HttpServletRequest) arg0;
//		HttpServletResponse response=(HttpServletResponse)arg1;
//		
//		String currentURL=request.getRequestURI();
//		String targetURL = currentURL.substring(currentURL.indexOf("/", 1),currentURL.length()); //截取到当前文件名用于比较
//		int isar=targetURL.indexOf("arsys/forms");
//		if ("/portal/login.action".equals(targetURL)
//				|| "/portal/userLogout.action".equals(targetURL)
//				|| "/welcom.jsp".equals(targetURL)
//				|| "/portal/password.action".equals(targetURL)
//				|| "/portal/extentLogin.action".equals(targetURL)
//				|| "/portal/editPwd.action".equals(targetURL)
//				||isar>-1
//			)
//		{
			ActionContext.setContext(createActionContext(arg0,arg1,"","",null));
			arg2.doFilter(arg0, arg1);
//		}
//		else
//		{
//			Object objSession = request.getSession().getAttribute("userSession");
//			if(Constants.isSynch){//uip同步
//				UserSession userSession = null;
//				if(objSession==null){
//					String loginName = GetUserUtil.getUsername();
//					PortalManagerService portalManagerService = (PortalManagerService)WebApplicationManager.getBean("portalManagerService");
//					objSession = portalManagerService.login(loginName, "", false);
//					request.getSession().setAttribute("userSession", objSession);
//				}else{
//					userSession=(UserSession)objSession;
//				}
//				
//				boolean isAllow=true;
//				String resourcepid="";
//				String operatorid="";
//				SqlResult sqlResult=null;
//				Object obj=WebApplicationManager.getBean("resourceUrlManagerService");
//				if(obj!=null)
//				{
//					//根据url查询资源和操作
//					ResourceUrlManagerService resourceUrlManagerService=(ResourceUrlManagerService)obj;
//					ResourceUrl resourceUrl=resourceUrlManagerService.getResourceUrlByUrl(targetURL);
//					if(resourceUrl!=null)
//					{
//						resourcepid=resourceUrl.getResid();
//						operatorid=resourceUrl.getOpid();
//						obj=WebApplicationManager.getBean("privilegeManagerService");
//						if(obj!=null)
//						{
//							PrivilegeManagerService privilegeManagerService=(PrivilegeManagerService)obj;
//							isAllow=privilegeManagerService.isAllow(userSession.getPid(), resourcepid, operatorid);
//							sqlResult=privilegeManagerService.getPrivilegeSql(userSession.getPid(), resourcepid, operatorid);
//						}
//					}
//				}
//				ActionContext.setContext(createActionContext(arg0,arg1,resourcepid,operatorid,sqlResult));
//				RecordLog.printFluxStatLog(targetURL);
//				if(!isAllow)
//				{
//					String url=request.getContextPath()+"/common/core/privilegeError.jsp";
//					response.sendRedirect(url);
//				}
//				else
//					arg2.doFilter(arg0, arg1);
//			}else{
//				//Constants.IS_SSO 是否单点登录
//				if(objSession==null && Constants.IS_SSO==true)
//				{
//					Cookie[] cookies = request.getCookies();
//					if(cookies==null)
//					{
//						System.out.println("The cookie is null.");
//					}
//					else
//					{
//						for(Cookie cookie:cookies)
//						{
//							if(cookie.getName().equals(Constants.SSO_COOKIE_NAME))
//							{
//								String value = cookie.getValue();
//								String[] pri = value.split(Constants.SSO_USER_PWD_SEPARATOR);
//								if(pri.length>0)
//								{
//									CryptUtils crypt = CryptUtils.getInstance();
//									String loginName = pri[0];
//									String pwd = pri.length==2?crypt.decode(pri[1]):"";
//									PortalManagerService portalManagerService = (PortalManagerService)WebApplicationManager.getBean("portalManagerService");
//									objSession = portalManagerService.login(loginName, pwd, true);
//									request.getSession().setAttribute("userSession", objSession);
//								}
//								break;
//							}
//						}
//					}
//				} 
//				
//				if(objSession==null)
//				{
//					//跳转到登录页面
//					String url=request.getContextPath()+"/portal/login.action";
//					response.sendRedirect(url);
//				}
//				else if(objSession!=null && "/".equals(targetURL))
//				{
//					String url=request.getContextPath()+"/portal/index.action";
//					RecordLog.printFluxStatLog(url);
//					response.sendRedirect(url);
//				}else
//				{
//					UserSession userSession=(UserSession)objSession;
//					boolean isAllow=true;
//					String resourcepid="";
//					String operatorid="";
//					SqlResult sqlResult=null;
//					Object obj=WebApplicationManager.getBean("resourceUrlManagerService");
//					if(obj!=null)
//					{
//						//根据url查询资源和操作
//						ResourceUrlManagerService resourceUrlManagerService=(ResourceUrlManagerService)obj;
//						ResourceUrl resourceUrl=resourceUrlManagerService.getResourceUrlByUrl(targetURL);
//						if(resourceUrl!=null)
//						{
//							resourcepid=resourceUrl.getResid();
//							operatorid=resourceUrl.getOpid();
//							obj=WebApplicationManager.getBean("privilegeManagerService");
//							if(obj!=null)
//							{
//								PrivilegeManagerService privilegeManagerService=(PrivilegeManagerService)obj;
//								isAllow=privilegeManagerService.isAllow(userSession.getPid(), resourcepid, operatorid);
//								sqlResult=privilegeManagerService.getPrivilegeSql(userSession.getPid(), resourcepid, operatorid);
//							}
//						}
//					}
//					ActionContext.setContext(createActionContext(arg0,arg1,resourcepid,operatorid,sqlResult));
//					RecordLog.printFluxStatLog(targetURL);
//					//resourceUrlManagerService
//					if(!isAllow)
//					{
//						String url=request.getContextPath()+"/common/core/privilegeError.jsp";
//						response.sendRedirect(url);
//					}
//					else
//						arg2.doFilter(arg0, arg1);
//				}//if(objSession==null)
//			}
//		}
	}
	
	/**
	 * 保存线程数据
	 * @param arg0
	 * @param arg1
	 * @param resId 资源ID
	 * @param opId 操作ID
	 * @param sqlResult 数据权限对象
	 * @return
	 */
	private ActionContext createActionContext(ServletRequest arg0, ServletResponse arg1,String resId,String opId,SqlResult sqlResult)
	{
		ActionContext ctx;
	//	ActionContext oldContext = ActionContext.getContext();
/*		if(oldContext!=null)
		{
			ctx=new ActionContext(new HashMap<String, Object>(oldContext.getContextMap()));
			
		}else
		{*/
			HashMap hash=new HashMap();
			hash.put(ActionContext.HTTP_REQUEST, arg0);
			//int page=NumberUtils.formatToInt(arg0.getParameter(GridConstants.HIDDEN_CURRENT_PAGE));
			hash.put(ActionContext.HTTP_RESOURCE,resId);
			hash.put(ActionContext.HTTP_OPERATE,opId);
			if(sqlResult!=null)
				hash.put(ActionContext.HTTP_OPERATE_DATAPRIVILEGE, sqlResult);
			ctx=new ActionContext(hash);
		//}
		return ctx;
	}
	public void init(FilterConfig arg0) throws ServletException 
	{
	}
	
}
