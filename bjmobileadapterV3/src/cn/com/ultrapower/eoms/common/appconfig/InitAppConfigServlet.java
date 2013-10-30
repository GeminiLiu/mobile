package cn.com.ultrapower.eoms.common.appconfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * 读取应用部分配置参数
 * @author wanghongbing
 *
 */
public class InitAppConfigServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//配置文件信息
		String file = config.getInitParameter("configPath");
		String filePath = this.getServletContext().getRealPath(file);

		//文件路径初始化
		ParametersManager parametersManager = new ParametersManager(filePath);
		parametersManager.readParameters();
	}
}
