<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="cn.com.ultrapower.interfaces.server.InterSwitchAlarm" %>
<%@page import="cn.com.ultrapower.eoms.processSheet.InterfaceCfg" %>
<%@page import="cn.com.ultrapower.interfaces.server.thread.AlarmProcess" %>
<%@page import="cn.com.ultrapower.ultrawf.control.process.BaseAction" %>
<%@page import="org.dom4j.Document" %>
<%
    String WEB_PATH = request.getContextPath();
    if(WEB_PATH==null){
  		WEB_PATH="";
    }
  	BaseAction.newAction();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宁夏移动电子运行维护系统-登陆页面</title>
<style> 

input {
	border:#919B9C solid 1px;
	font-size:12px;
	display:inherit;
	visibility:visible;
	height:20px;
	COLOR: #333333; 
	FONT-FAMILY: "宋体","黑体", "Verdana";
}
/*登陆界面文字样式--begin */	

.login{ color:#FFFFFF; 
        font-size:12px; 
		line-height:26px}	

/*登陆界面文字样式--end */	


</style>
<!-- <link href="css/Style.css" rel="stylesheet" type="text/css">-->
<script type="text/javascript">
    function checkform(){
      obj = document.loginform.username;
      var r = obj.value.match(/^^[A-Za-z0-9_-]+$/);//数字和字母-_中文匹配
      if(r==null){
			if(obj.value ==""||obj.value ==null){
				alert("请输入用户登录名！");
				obj.focus();
				return false;
			}else{
				alert("用户名输入格式不匹配！");
				obj.value='';
				obj.focus();
				return false;
			}
		}
      document.loginform.submit();
    }
    
    function reset(){
    	obj = document.loginform.username;
    	obj.value="";
    	obj.focus();
    	obj1 = document.loginform.password;
    	obj1.value="";
    }
  	
	window.onload = function(){
		document.loginform.username.focus();
	}
	document.onkeydown=function(){
		if(window.event.keyCode==13){
			checkform();
		}
	}
    </script> 
</head>

<body background="images/login_bg.jpg" leftmargin="0" topmargin="0" >
<FORM name="loginform" action="<%=WEB_PATH%>/common/jsp/loginVerify.jsp" method="post">
<BR>
<table border="0" cellpadding="0" width="65%" cellspacing="4" align="center">
	<tr>
		  <td style="color:#fff;font-size:13px;padding-left:15px" align="left"><b>坚持</b>“网络质量是通信企业生命线”的宗旨，确保网络运行安全畅通，出色完成奥运通信保障任务，
		  </td>
	</tr>
		<tr>
		  <td style="color:#fff;font-size:13px;padding-left:45px" align="left">维护优化工作由面向网络设备维护延展至设备维护与客户感知的网络质量保障并重，
		  </td>
	</tr>
	<tr>
		  <td style="color:#fff;font-size:13px;padding-left:65px" align="left">继续向“质量领先、运行高效、支撑有力”的网络工作目标迈进，
		  </td>
	</tr>
	<tr>
		  <td style="color:#fff;font-size:13px;padding-left:85px" align="left">讲大局、讲责任、讲服务，建设一流的运维管理体系，
		  </td>
	</tr>
	<tr>
		  <td style="color:#fff;font-size:13px;padding-left:105px" align="left">全方位实践主动监控、主动维护、主动服务的一流运维理念，持续提升网络服务能力
		  </td>
	</tr>
</table>
<BR>
<table border="0" cellpadding="0" width="100%" cellspacing="0" >
     <tr>
		  <td>
			<table align="center" style="width:640" border="0" cellpadding="0" cellspacing="0" >
		    <tr>
		      <td align="right" width="640" background="images/login.jpg" height=427>
						  <TABLE width="100%" cellpadding="0" cellspacing="0">
						  <TR height=110>
							<TD></TD>
							<TD></TD>
						  </TR>
						  <TR height="28">
								<td align="right" width="75%">
								    <input id="username" name="username" type="text" maxlength="15"></td>
								<td width="25%" ><span style="cursor:hand;padding-left:10px"><img src="images/login.png" onclick="checkform();"></span></td>
							</TR>
							<TR>
								<td align="right" width="75%"><input id="password" name="password" type="password"  maxlength="15"></td>
								<td width="25%"><span style="cursor:hand;padding-left:10px"><img src="images/cancle.png" onclick="reset()"></span></td>
							 </tr>
						  </table>
					  </td>
				  </tr>
			  </table>
	      </td>
     </tr>
</table>
</FORM>
</body>
</html>
