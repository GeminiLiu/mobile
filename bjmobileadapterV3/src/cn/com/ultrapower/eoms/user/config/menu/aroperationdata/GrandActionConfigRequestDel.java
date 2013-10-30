package cn.com.ultrapower.eoms.user.config.menu.aroperationdata;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class GrandActionConfigRequestDel  extends HttpServlet {
		static final Logger logger = (Logger) Logger.getLogger(GrandActionConfigRequestDel.class);
		/**
		 * Constructor of the object.
		 */
		public GrandActionConfigRequestDel() {
			super();
		}

		/**
		 * Destruction of the servlet. <br>
		 */
		public void destroy() {
			super.destroy(); // Just puts "destroy" string in log
			// Put your code here
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
			try
			{
				String DelRequest[]=request.getParameterValues("delrecord");
				for(int i=0;i<DelRequest.length;i++){
					GrandActionConfig grandinfo=new GrandActionConfig();
					grandinfo.formFieldDelete(DelRequest[i]);
				}
				logger.info("删除成功");
				response.sendRedirect("../roles/grandactionconfigshow.jsp");
			}
			catch(Exception e)
			{
				logger.error("删除失败");
				logger.error("[-1] TreeDelDao servlet error:"+e.toString());
				response.sendRedirect("../roles/grandactionconfigshow.jsp");
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
			this.doGet(request,response);
		}

		/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occure
		 */
		public void init() throws ServletException {
			// Put your code here
		}

	}