package cn.com.ultrapower.eoms.util.remedy;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
/**
 * 读取工单初始化数据
 * @author lijupeng
 *
 */

public class InitRemedyFormConstantsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//配置文件信息
		String file = config.getInitParameter("configPath");
		String filePath = this.getServletContext().getRealPath(file);

		//文件路径初始化
		ConstantsManager constantsManager = new ConstantsManager(filePath);
		constantsManager.getConstantInstance();
	}
}
