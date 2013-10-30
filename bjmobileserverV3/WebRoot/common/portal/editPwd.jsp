<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="com_t_title" /></title>
		<!-- <link href="${ctx}/common/style/blue/css/login.css" rel="stylesheet" type="text/css" /> -->
		<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,55);
		}
		window.onload = function() 
		{
			setCenter(0,55);
			if('${returnMessage}'!='')
			{
				alert('${returnMessage}');
				window.close();
			}
			if('${isLogin}' == 'true')
				document.login_form.oldPwd.focus();
			else
				document.login_form.newPwd.focus();
		}
		 if (window != top)
		 {
		 	 //alert(1);
		 	 top.location.href = location.href;
		 	  //window.history.go(-1);
		 }
		function err(){
		  document.getElementById("error").style.visibility= 'visible';
		  //document.getElementById("loginbox").style.paddingTop= '80px';
		  return;
		}
		function editPwd()
		{
			var new_pwd = document.getElementById('newPwd').value;
			var new_pwd_repeat = document.getElementById('reNewPwd').value;
			if(new_pwd == new_pwd_repeat)
			{
				document.forms[0].action = "${ctx}/portal/editPwd.action";
				document.forms[0].submit();
			}
			else
			{
				alert("<eoms:lable name='sm_lb_repeatPwdErr' />！");
			}
		}
		function backLogin()
		{
			document.getElementById('loginName').disabled = 'disabled';
			document.forms[0].action = "${ctx}/portal/login.action";
			document.forms[0].submit();
		}
	</script>
	</head>
	<body>
	<form name="login_form" action="${ctx}/portal/editPwd.action" method="post" id="Form1">
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2">萨班斯密码管理</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center" style="text-align: center; margin: 0 auto;">
				<div class="blank_tr"></div>
					<c:if test="${isLogin!='true'}">
					<br/><br/><br/>
					</c:if>
					<table style="width:400px">
						<tr><td><b>萨班斯密码管理（规则描述）</b></td></tr>
						<tr>
							<td align="left">
								<c:if test="${isLogin!='true'}">
								<br/>
								</c:if>
								1、密码长度应在6-30位之间，只能并且必须包含字母和数字，密码不能输入连续的相同字符；
								<br/>
								2、密码不能设置为最近6次使用过的密码；
								<br/>
								3、密码不能是个人信息相关；（如：用户登录名、手机、固定电话等）
								<br/>
								4、为了您的安全性，请在90天内务必修改自己的密码。
							</td>
						</tr>
					</table>
					<c:if test="${isLogin!='true'}">
					<br/><br/>
					</c:if>
					<c:choose>
						<c:when test="${msg!=null}">
							<font color=red>${msg}</font>
						</c:when>
						<c:otherwise>
							<br/>
						</c:otherwise>
					</c:choose>
					<table style="width:400px">
						<tr>
							<td align="right">用户登陆名：</td>
							<td align="left">${loginName}
							<input type="hidden" name="loginName" value="${loginName}"/>
							<input type="hidden" name="isLogin" value="${isLogin}"/>
							</td>
						</tr>
						<c:if test="${isLogin=='true'}">
						<tr>
							<td align="right">原密码：</td>
							<td align="left"><input type="password" name="oldPwd" style="width:150px"/></td>
						</tr>
						</c:if>
						<tr>
							<td align="right">新密码：</td>
							<td align="left"><input type="password" name="newPwd" style="width:150px"/></td>
						</tr>
						<tr>
							<td align="right">重复新密码：</td>
							<td align="left"><input type="password" name="reNewPwd" style="width:150px"/></td>
						</tr>
						<c:if test="${isLogin!='true'}">
						<tr>
							<td></td>
							<td>
								<input type="button" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" value="确认" onclick="editPwd();" />
								<input type="button" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"value="返回" onclick="backLogin();" />
							</td>
						</tr>
						</c:if>
					</table>
				<div class="blank_tr"></div>
			</div>
			<div class="add_bottom">
				<c:if test="${isLogin=='true'}">
					<input type="button" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" value="确认" onclick="editPwd();" />
					<input type="button" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"value="关闭" onclick="window.close();" />
				</c:if>
			</div>
		</div>
	</form>
	</body>
</html>
