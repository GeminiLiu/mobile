/**
 * 
 */
package cn.com.ultrapower.eoms.util;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

/**
 * 初始化
 * @author lijupeng
 *
 */
public class LoggerServlet extends HttpServlet{

	//public static Log logger = LogFactory.getLog(Logger.class);	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
		// 初始化Action资源
		try{
			String prefix =  getServletContext().getRealPath("/");
		    String file = getInitParameter("configfile");
		    // if the log4j-init-file is not set, then no point in trying
		    if(file != null) {
		      PropertyConfigurator.configure(prefix+file);
		      System.out.println("Init Log4j success!");
		    }
		}
		catch(Exception ioe){
			System.out.print("Load ActionRes is Error");
		}
	}

}
