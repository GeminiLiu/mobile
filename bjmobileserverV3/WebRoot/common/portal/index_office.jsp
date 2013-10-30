<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="com_t_title" /> </title>
		<script src="${ctx}/common/javascript/dropmenu.js" type="text/javascript"></script>
		<script src="${ctx}/common/style/office/js/index.js" type="text/javascript"></script>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<script type="text/javascript"
			src="${ctx}/common/plugin/jquery/ui/jquery.timers-1.1.2.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/mymenu.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/common/style/office/css/index.css" />
		<script type="text/javascript">
	
	var menuids=["suckertree1"] //Enter id(s) of SuckerTree UL menus, separated by commas
	function buildsubmenus()
	{
		for (var i=0; i<menuids.length; i++)
		{
		  var ultags=document.getElementById(menuids[i]).getElementsByTagName("ul")
		  if( ultags==null)
		  {
		  	break;
		  }
		  for (var t=0; t<ultags.length; t++)
		  {
		 	
			if(ultags[t].parentNode.getElementsByTagName("a")[0]==null)
			{
				continue;
			}
		 	ultags[t].parentNode.getElementsByTagName("a")[0].className="subfolderstyle"
		 	
		    ultags[t].parentNode.onmouseover=function()
		    {
		    	this.getElementsByTagName("ul")[0].style.display="block"
		    }
		    ultags[t].parentNode.onmouseout=function()
		    {
		   		this.getElementsByTagName("ul")[0].style.display="none"
		    }
		  }//for (var t=0; t<ultags.length; t++)
		}
		
	}
	
	if (window.addEventListener)
	window.addEventListener("load", buildsubmenus, false)
	else if (window.attachEvent)
	window.attachEvent("onload", buildsubmenus)
 
    //我的菜单 链接弹出 
    function openwindow(url,name,iWidth,iHeight)
	{
		var url; 
		var name; 
		var iWidth; 
		var iHeight; 
		var iTop = (window.screen.availHeight-30-iHeight)/2; 
		var iLeft = (window.screen.availWidth-10-iWidth)/2; 
		window.open('${ctx}/'+url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no');
	}
		
	/***************************公用代码************************************/
	function PageMenuActive(objName)
	{
	
		document.getElementById(activePageMenu).className = 'menu_onblur';
		document.getElementById(objName).className = 'menu_onfocus';
		activePageMenu = objName;
	}
	window.onload = function()
	{
		setCenter(0,67);
	}
	window.onresize = function() 
	{
		setCenter(0,67)
	}
	/**首页弹出start**/
	$(document).ready(function(){
		//每60秒采集一次数据
		$("#viewPage").everyTime(60000,function() {
                getTaskInfo();
				});
	});
	function getTaskInfo(){
		jQuery.post("${ctx}/ultratask/getTaskInfo.action" ,   //服务器页面地址
            {},
            function(data) {
              if(data!=""){
                  //构建页面显示：
                  //jQuery("#viewPage").html(data);
                  //$("#viewPage").slideDown();
                  test(data);
                  var viewPage=$(document.getElementById("taskInfo"));
                  $(viewPage).css("cursor", "pointer").click(function() {updateListInfo();});
                  //然后10秒后关闭显示页面 一次时间
	              $(viewPage).oneTime(10000,function(){$(viewPage).slideUp();});
               }
             }
		);  
	}
	function updateListInfo(){
		 jQuery.post("${ctx}/ultratask/readTaskNote.action",   //服务器页面地址
         {},
         function(data) {
        	 $(document.getElementById("taskInfo")).slideUp();
         });
	}
	function test(data){
		createDiv("taskInfo",$(document.body));
		divObj = $(document.getElementById("taskInfo"));
		divObj.css('display','none');
		divObj.css('position','absolute');
		divObj.css('width','250px');
		divObj.css('height','80');
		divObj.css('z-index','32001');
		divObj.css('top',document.body.clientHeight-340);
		divObj.css('left',document.body.clientWidth-250);
		divObj.css('background','#CCFFFF');
		divObj.css('padding','10px');
		divObj.css('font-size','12px');
		divObj.css('color','#0000FF');
		divObj.css('filter','Alpha(opacity=80)') ;
		divObj.css('opacity','.60') ;
		divObj.css('border','1px solid #0000FF') ;
		divObj.css('border-top','4px solid #0000FF') ;
		divObj.html(data);
        divObj.slideDown();
	}
	function createDiv(id,jobj){
		$(jobj.children().get(0)).after("<div id='"+id+"'></div>");
	}
	/**首页弹出end**/
	</script>

	</head>

	<body>
    <div id="viewPage">
	</div>
	<div id="taskInfo">
	</div>
    <div class="headerOffice">
      <div class="bannerOffice">
        <div class="banner_rightOffice" id="right">
        
          <div class="logoOffice">
          	<div class="systemfuctionOffice">
                <span><a href="index.action" target="_top">常规风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
                <span><a href="index_office.action" target="_top">Office风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
                <span><a href="index_rightmenu.action" target="_top">右侧菜单风格</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
                <span class="logon_user">欢迎您：${userSession.fullName}</span>
                <span class="help">系统帮助</span>
                <span class="exit"><a href="userLogout.action?logoutType=arsys" target="_top">安全退出</a></span>
            </div>
          </div>
          
        </div>
      </div>
      <div class="menubg">
      <div class="menu">
      <ul>
					<a href="${ctx}/common/portal/frame/portal.jsp" target="center">
						<li	id="td0" class="menu_onfocus" onclick="PageMenuActive('td0');"><span>首　　页</span></li>
					</a>
					<!--定义的Page头 开始-->
					<c:forEach items="${navigateList}" var="navigate" varStatus="status">						
						<c:set var="url" value="${ctx}/portal/content_frame.action?menuid=${navigate.id}"/>
						
						<li class="menu_line"></li>
						<a href="${url}" target="center">
							<li id="td${navigate.mark}" class="menu_onblur" onclick="PageMenuActive('td${navigate.mark}');" onmouseover="showQuickBar('quickBar_${navigate.mark}')" onmouseout="hideQuickBar()"><span>${navigate.text}</span></li>
						</a>
					</c:forEach>
					<!--定义的Page头 结束-->
      
      </ul>
      </div>
            
    </div>
    </div>    

			
		<div style="position:absolute;z-index:100;top:36px;right:10px;">
			<div class="suckerdiv">
				<ul id="suckertree1">
					<li>
						<div class="myMenu" onmouseover="this.className='myMenuHover'"	onmouseout="this.className='myMenu'"></div>
						<ul style="background:url(${ctx}/common/style/blue/images/index/mymenu_first_bg.png);width:192px;">

							<div class="mymenu_title">
								<span class="mymenutitle">我的菜单</span>
							</div>
							${myMenuHtml}
							<div class="mymenu_bottom"></div>
						</ul>
					</li>
					
				</ul>
			</div>
		</div>
		<%@ include file="quickbar/quickBar_sheet.jsp"%>
		<%@ include file="quickbar/quickBar_ultrasm.jsp"%>
		<%@ include file="quickbar/quickBar_duty.jsp" %>
		<%@ include file="quickbar/quickBar_plan.jsp" %>
		<%@ include file="quickbar/quickbar_docsManager.jsp" %>
		<%@ include file="quickbar/quickbar_taskbooking.jsp" %>
		<%@ include file="quickbar/quickBar_workflow.jsp" %>
		<%@ include file="quickbar/quickBar_repository.jsp" %>
		<script>
		window.activePageMenu = 'td0';PageMenuActive('td0');
		</script>
		<iframe src="${ctx}/common/portal/frame/portal.jsp" frameborder="0" width="100%" id="center" name="center" height="1px"></iframe>
		<div style="width: 100%;height: 100%; padding-top:200px;" align="center">
			<img src="${ctx}/common/style/blue/images/progress_nonmodal-circle.gif" width="32" height="32"/>
		</div>
	</body>
</html>
