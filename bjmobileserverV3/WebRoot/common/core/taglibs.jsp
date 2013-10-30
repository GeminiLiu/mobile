<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="eoms"%>
<%@ taglib uri="/WEB-INF/datagrid" prefix="dg"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	var jsCtx = "${ctx}";
</script>
<fmt:setBundle basename="i18n.Messages" var="i18Bundle"/>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript" src="${ctx}/common/javascript/datagrid.js"  defer="defer"></script>
<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"  defer="defer"></script>

