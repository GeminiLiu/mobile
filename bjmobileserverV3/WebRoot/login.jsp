<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>电子运行维护管理系统</title>
    <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8"/> 
	<style type="text/css">
		body{
			padding-top:50px;
			margin:0px;
			background-color:#000000;
			color:#FFFFFF;
			font-family:Arial,Helvetica,sans-serif;
		}
		.divmain {
			height:100%;
			width:100%;
		}
		.titlearea {
			font-weight:bold;
			font-size: 24px;
			height:30px;
			vertical-align:middle;
		}
		.texttdarea {
			height:40px;
		}
		.buttonarea {
			height:45px;
			text-align:center;
		}
	</style>
	<script type="text/javascript">
		<%
			Object obj = request.getAttribute("msg");
			if(obj != null) {
			%>
				alert('<%=obj%>');
			<%
			}
		%>
		
	</script>
  </head>
  
  <body onload="readInfoFromCookie();">
  		<div class="divmain" id="divmain">
  			<form action="${pageContext.request.contextPath}/portal/login.action" method="post" id="form" onsubmit="writeInfoToCookie()">
  				<input type="hidden" name="itSysName" id="itSysName" value="ultrawf"/>
  				<table style="width: 100%" >
		   			<tr>
                    <td style="text-align:right; width:50%;">
                        <img src="${pageContext.request.contextPath}/workflow/sheet/images/toptitle.png" alt="" width="35px" height="35px"/>
                    </td>
                    <td class="titlearea">
                    	<nobr>
                        电子运行维护管理系统
                        </nobr>
                    </td>
                    <td style="text-align:right; width:50%;">
                        &nbsp;
                    </td>
                    </tr>
                </table>
  				<table style="width: 100%" >
		   			<tr>
		   				<td style=" font-style:italic; font-weight:bold" >
                        	请登录：
                            <hr/>
                        </td>
		   			</tr>
		   			<tr>
		   				<td class="texttdarea" ><nobr>
                        	　用户登录名：
                            <input type="text" style="width:160px;" name="userName" id="userName"/>
                        	</nobr>
                        </td>
		   			</tr>
		   			<tr>
		   				<td  class="texttdarea"><nobr>
                        	用户登录密码：
                            <input type="password" style="width:160px;" name="password" id="password"/>
                            </nobr>
                        </td>
		   			</tr>
		   			<tr>
		   				<td class="buttonarea">
                            <input name="btnsubmit" type="submit" value="　登 录　" style="color:#333333"/>
                            <input name="btnreset" type="reset"  value="　取 消　" style="color:#333333" onclick="window.close()"/>
                        </td>
		   			</tr>
		   		</table>
  			</form>
  		</div>
  </body> 
</html>
<script type="text/javascript">
function readInfoFromCookie() {
	var userNameC = getCookie('msusername');
	var passowordC = getCookie('mspassword');
	document.getElementById('userName').value = userNameC;
	document.getElementById('password').value = passowordC;
}
function writeInfoToCookie() {
	var userName = document.getElementById('userName').value;
	var password = document.getElementById('password').value;
	setCookie('msusername',userName,365);
	setCookie('mspassword',password,365);
}

function setCookie(c_name,value,expiredays)
{
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	if (expiredays==null)
	{
		document.cookie = c_name+ "=" + escape(value)+ "";
	}
	else
	{
		document.cookie = c_name+ "=" + escape(value)+ ";expires=" + exdate.toGMTString();
	}
}
function getCookie(c_name)
{
	if (document.cookie.length>0)
	{
		c_start=document.cookie.indexOf(c_name + "=");
		if (c_start!=-1)
		{ 
			c_start=c_start + c_name.length+1;
			c_end=document.cookie.indexOf(";",c_start);
			if (c_end==-1) c_end=document.cookie.length;
			return unescape(document.cookie.substring(c_start,c_end));
		} 
	}
	return "";
}
</script>
