<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8"/> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<link href="${ctx}/workflow/sheet/css/sheetInfo.css" rel="stylesheet" type="text/css" />
	<title>工单详情</title>
	<script type="javascript">
	</script>
</head>

<body style="padding:0px; margin:0px; background-color:#000000;">
<table class="worksheetPageTable" width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td style='height:30px;padding-left:5px' align="left" valign="middle"><span style="height:30px; font-weight:bolder">工单详情</span></td>
		<td style='height:30px;padding-right:5px' align="right" valign="middle"><span style="height:30px;"><a href="javascript:history.back()">返回列表</a></span></td>
    </tr>
</table>
<table width="100%" class="maintable" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="worksheetFieldTd">${baseName}：${baseSn}<font style="font-style:italic;text-decoration: underline;">(${baseStatus})</font></td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
		    <td>
		        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td colspan="2" class="infotitle">工单基本信息</td>
		          </tr>
		          <c:forEach items="${bizFields}" var="field">
		          		<c:if test="${field.hidden == false && fn:length(field.cname) > 0}">
							<tr>
								<td class="worksheetFieldLabel">${field.cname}：</td>
								<c:choose>
								   <c:when test="${fn:length(field.content) < 19}">
								   		<c:set var="css" value="worksheetFieldValueInputOneline"/>
								   </c:when>
								    <c:when test="${fn:length(field.content) < 30}">
								   		<c:set var="css" value="worksheetFieldValueInputTwoline"/>
								   </c:when>
								   <c:otherwise>
								   		<c:set var="css" value="worksheetFieldValueInputMoreline"/>
								   </c:otherwise>
								</c:choose>
								<td class="worksheetFieldValue">${field.content}</td>
							</tr>
						</c:if>
					</c:forEach>
		        </table>
		    </td>
		  </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td  colspan="2" class="infotitle">工单处理记录</td>
          </tr>
			<tr>
				<td colspan="2" class="worksheetFieldValue">
					<c:forEach items="${dpLogFields}" var="field">
						${field.content}
					</c:forEach>
				</td>
			</tr>
        </table>
    </td>
  </tr>
</table>
</body>
</html>
