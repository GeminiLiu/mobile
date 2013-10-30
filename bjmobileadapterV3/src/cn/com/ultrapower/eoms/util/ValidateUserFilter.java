package cn.com.ultrapower.eoms.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.com.ultrapower.eoms.util.user.UserInformation;

/**
 * @author lijupeng
 * @CreatTime 2006-11-8
 */
public class ValidateUserFilter implements Filter {



    /**
     * The filter configuration object we are associated with.  If this value
     * is null, this filter instance is not currently configured.
     */
    protected FilterConfig filterConfig = null;



    // --------------------------------------------------------- Public Methods


    /**
     * Take this filter out of service.
     */
    public void destroy() {

         this.filterConfig = null;

    }


    /**
     * Select and set (if specified) the character encoding to be used to
     * interpret request parameters for this request.
     *
     * @param request The servlet request we are processing
     * @param result The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
	throws IOException, ServletException {
 
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
    	HttpServletResponse httpResonse = (HttpServletResponse)response;
        HttpSession httpSession = (HttpSession)httpRequest.getSession();
        UserInformation userInfo = (UserInformation)httpSession.getAttribute("userInfo");
        String WEB_PATH = httpRequest.getContextPath();
        if(WEB_PATH==null){
      	WEB_PATH="";
        }
        boolean validUser = false;
        try {
			if (userInfo == null) {
				Log.logger.error(ArLogModule.LOGIN + "错误!无效的用户登录信息！");
			} else {
				validUser = true;
			}
			
		} catch (Exception e) {
			Log.logger.fatal(ArLogModule.LOGIN+"严重错误，无法读取session！请稍候再试！");
		}
		if(validUser == false){
			httpResonse.sendRedirect(WEB_PATH+"/common/login.jsp");
		}
	    // Pass control on to the next filter
        chain.doFilter(request, response);

    }


    /**
     * Place this filter into service.
     *
     * @param filterConfig The filter configuration object
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    	
		//Log.logger.error(ArLogModule.LOGIN + "用户开始进入！");

	    this.filterConfig = filterConfig;

    }


    // ------------------------------------------------------ Protected Methods


 

}
