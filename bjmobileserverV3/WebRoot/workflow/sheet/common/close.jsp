<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<script type="text/javascript">
  try{
//  	window.parent.history.back();  
//	window.parent.parent.flag = 2;
// 	window.parent.history.go(-1);  
	var cp = window.parent.parent.curPage;
	var pz = window.parent.parent.pageSize;
	var ts = window.parent.parent.totalSize;
  	window.parent.parent.tabChange('TabIDWaiting','${ctx}/sheet/waitingDealSheet.action?var_currentpage='+cp+'&pageSize='+pz+'&var_istranfer=1&totalSize='+ts);
  }catch(e){
  } 
  <%
	Object obj = request.getAttribute("msg");
	if(obj != null) {
	%>
		alert('<%=obj%>');
	<%
	}
%>
</script>
