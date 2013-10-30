<%@ page language="java" import="com.ultrapower.eoms.common.portal.model.UserSession" pageEncoding="UTF-8"%>
<!--标准浏览器显示-->
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <%@ include file="/common/core/taglibs.jsp"%>
	<title>电子运行维护管理系统（${userSession.fullName}）</title>
    <script type="text/javascript">
        function tabChange(pClickTabID,pClickTabURL)
        {
            var m_ClickTabID_Array = new Array("TabIDWaiting","TabIDDealed");
            for (var i=0;i<m_ClickTabID_Array.length;i++ )
            {
                m_ClickTabID = m_ClickTabID_Array[i];
                if (m_ClickTabID == pClickTabID)
                {
                    document.getElementById(m_ClickTabID + "_body").style.backgroundColor	="#CCCCCC"; 
                    document.getElementById(m_ClickTabID + "_Text").style.color	="#111111"; 
                }
                else
                {
                    document.getElementById(m_ClickTabID + "_body").style.backgroundColor	="#333333"; 
                    document.getElementById(m_ClickTabID + "_Text").style.color	="#999999"; 
                }
                document.getElementById("TabBodyIframe").src = pClickTabURL;
            }
    
        }
        var curPage = 0;
        var pageSize = 0; 
        var totalSize = 0;
    </script>
    
  </head>

<body style="padding:0px; margin:0px; background-color:#000000; overflow:hidden;">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>
        <table width="100%" height="61px" border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" frame="vsides">
          <tr>
            <td id="TabIDWaiting_body" style="background-color:#CCCCCC;height:61px;" onclick="tabChange('TabIDWaiting','${ctx}/sheet/waitingDealSheet.action')">
                <div id="TabIDWaiting_Title" style="text-align:center">
                    <img src="${ctx}/workflow/sheet/images/tabwaitp.png" style="width:32px; height:32px" alt="" />
                    <div id="TabIDWaiting_Text" style="font-size:13px; text-align:center; padding-top:3px; color:#111111">待办任务</div>
                </div>
            </td>
            <td id="TabIDDealed_body" style="background-color:#333333; height:61px;" onclick="tabChange('TabIDDealed','${ctx}/sheet/dealedSheet.action')">
                <div id="TabIDDealed_Title" style="text-align:center">
                    <img src="${ctx}/workflow/sheet/images/tabdealedp.png" style="width:32px; height:32px" alt="" />
                    <div id="TabIDDealed_Text" style="font-size:13px; text-align:center; padding-top:3px; color:#999999">已办任务</div>
                </div>
            </td>
            <td id="TabIDExit_body" style="background-color:#333333; height:61px;" onclick="window.location='${ctx}/portal/logout.action'">
                <div id="TabIDExit_Title" style="text-align:center">
                    <img src="${ctx}/workflow/sheet/images/exit.png" style="width:32px; height:32px" alt="" />
                    <div id="TabIDExit_Text" style="font-size:13px; text-align:center; padding-top:3px; color:#999999">退出系统</div>
                </div>
            </td>
          </tr>
        </table>
        </td>
      </tr>
      <tr>
        <td style="background-color:#CCCCCC; height:1px;"></td>
      </tr>
      <tr>
        <td style="height:100%; width:100%; text-align:center;">
                <iframe src="${ctx}/sheet/waitingDealSheet.action" style="height:100%; width:100%;" id="TabBodyIframe" style="border:none;" frameborder="0" allowtransparency="yes"  marginheight="0" marginwidth="0" align="middle" >    
                </iframe>            
        </td>
      </tr>
    </table>
</body>
</html>
<script type="text/javascript">
tabChange('TabIDWaiting','${ctx}/sheet/waitingDealSheet.action')
var winW, winH, TabBodyIframeH;
if(window.innerHeight) { // all except IE
	winW = window.innerWidth;
	winH = window.innerHeight;
} else if (document.documentElement 
&& document.documentElement.clientHeight) {
	// IE 6 Strict Mode
	winW = document.documentElement.clientWidth; 
	winH = document.documentElement.clientHeight;
} else if (document.body) { // other
	winW = document.body.clientWidth;
	winH = document.body.clientHeight;
}
TabBodyIframeH = parseInt(winH) - 63;
</script>
