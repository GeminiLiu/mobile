package com.ultrapower.mobile.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.mobile.common.utils.ItSysConfigUtil;

public class TreeProxyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		id = StringUtils.isBlank(id) ? "0" : id;
		String rearchUserOrDep = req.getParameter("rearchUserOrDep");
		String contextPath = ItSysConfigUtil.getItSysAddress("eoms4");
		String serverPath = contextPath + "common/depuserTree.action?id="+id+"&isSelectType=2";
		if (StringUtils.isNotBlank(rearchUserOrDep)) {
			serverPath += "&rearchUserOrDep=" + URLEncoder.encode(rearchUserOrDep, "utf-8");
		}
		
		
		  HttpClient client = new HttpClient(); 
	      // 设置代理服务器地址和端口       

	      //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port); 
	      // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https 
	         HttpMethod method=new GetMethod(serverPath);
	         method.addRequestHeader("Content-type" , "text/html; charset=utf-8"); 
	      //使用POST方法
	      //HttpMethod method = new PostMethod(" http://java.sun.com");
	      client.executeMethod(method);

	      //打印服务器返回的状态
	       System.out.println(method.getStatusLine());
	      //打印返回的信息
	       String result = method.getResponseBodyAsString();
	      System.out.println(method.getResponseBodyAsString());
	      //释放连接
	      method.releaseConnection();
	      resp.getWriter().write(result);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
