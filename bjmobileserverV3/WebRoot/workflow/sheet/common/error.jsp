
<script type="text/javascript">
<%
	Object obj = request.getAttribute("error");
	if(obj != null) {
	%>
	//	parent.document.getElementById("loading").style.display = "none";
		parent.enableButtion();
		alert('<%=obj%>');
		window.parent.history.go(-1);  
	<%
	}
%>
</script>