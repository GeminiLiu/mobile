<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<%@ include file="/common/core/taglibs.jsp"%>
	<link href="${ctx}/workflow/sheet/css/sheetList.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/workflow/sheet/js/sheetList.js"  defer="defer"></script>
 	<title>待办任务列表</title>
</head>
	<body style="font-size:10px;padding:0px; margin:0px; background-color:#000000;">
		<div id="full" class="divfull">
		  	<dg:datagrid  items="${workSheets}" var="ws" action="${ctx}/sheet/waitingDealSheet.action">
		  		<dg:condition>
		  			<table width="100%" class="maintable" border="0" cellspacing="0">
		  				<tr>
		  					<td colspan="2" align="left" style="color: #000000; height25px; padding-left:5px;padding-bottom:5px;padding-top:10px; font-weight: bold; background-color: #AAAAAA;">
		  						请输入查询条件
							</td>
		  				</tr>
		  				<tr>
		  					<td class="worksheetFieldLabel" style="width: 25%;color:#000000;">工单类别:</td>
		  					<td class="worksheetFieldValue">
		  						<s:select cssClass="selectLineEnable" id='baseSchema' list="wfTypes" name="baseSchema"  headerKey="" headerValue="全部" listKey="baseSchema" listValue="baseName">
		  						</s:select>
		  					</td>
		  				</tr>
		  				<tr>
		  					<td colspan="2" align="center">
		  						<input type="button" value="查询" onclick="tranferFirst()"/>
		  						<input type="button" value="取消" onclick="document.getElementById('baseSchema').value='';showsearch();"/>
		  					</td>
		  				</tr>
		  			</table>
		 		</dg:condition>
				<dg:gridrow>
					<table width="100%" cellspacing="0" cellpadding="0" class="maintable" id="worksheetmaintable${rowindex}" onmousemove="onMousemoveWorkSheet('worksheetmaintable${rowindex}')" onmouseout="onMouseoutWorkSheet('worksheetmaintable${rowindex}')">
					  <tr>
					    <td>
					    <table width="100%" border="0" cellspacing="0" cellpadding="0">
					      <tr>
					        <td width="100%">
					            <table width="100%" border="0" cellspacing="0" cellpadding="0">
					              <tr>
					                <td class="worksheetLeftNum" onclick="openWaitSheet('${ws.baseId}', '${ws.baseSchema}', '${ws.tplId}', '${ws.taskId}', '${ws.dealTime}', '${ws.acceptTime}', '${ws.taskName}', '${itSysName}')">${rowindex}：</td>
					                <td class="worksheetFieldTd" onclick="openWaitSheet('${ws.baseId}', '${ws.baseSchema}', '${ws.tplId}', '${ws.taskId}', '${ws.dealTime}', '${ws.acceptTime}', '${ws.taskName}', '${itSysName}')">工单号：${ws.baseSn}&nbsp;&nbsp;<font style="font-style:italic;text-decoration: underline;">(${ws.baseStatus})</font></td>
					                <td class="worksheetLeftNum" align="center" valign="middle" onclick="lookWorkSheetMore('worksheetmaintable${rowindex}','worksheetmore${rowindex}','worksheetmoreimg${rowindex}');">
					            		<img src="${ctx}/workflow/sheet/images/lookno.png" alt="查看更多信息" title="查看更多信息" height="15px" id="worksheetmoreimg${rowindex}"/>
	                               	</td>
					              </tr>
					              <tr onclick="openWaitSheet('${ws.baseId}', '${ws.baseSchema}', '${ws.tplId}', '${ws.taskId}', '${ws.dealTime}', '${ws.acceptTime}', '${ws.taskName}', '${itSysName}')">
					                <td class="worksheetLeftNum" >　</td>
					                <td class="worksheetFieldTd">分派时间：<eoms:date value="${ws.assigneTime}"/></td>
					                <td class="worksheetLeftNum">　</td>
					              </tr>
					              <tr onclick="openWaitSheet('${ws.baseId}', '${ws.baseSchema}', '${ws.tplId}', '${ws.taskId}', '${ws.dealTime}', '${ws.acceptTime}', '${ws.taskName}', '${itSysName}')">
					                <td class="worksheetLeftNum">　</td>
					                <td class="worksheetFieldTd" title="${ws.title}">${ws.baseType}：
					                	<c:if test="${fn:length(ws.title) > 20}">
							        		${fn:substring(ws.title, 0, 20)}......
							        	</c:if>
							        	<c:if test="${fn:length(ws.title) <= 20}">
							        		${ws.title}
							        	</c:if>
					                </td>
					                <td class="worksheetLeftNum">　</td>
					              </tr>
					            </table>
					        </td>
					      </tr>
					      <tr id="worksheetmore${rowindex}" style="display:none" onclick="openWaitSheet('${ws.baseId}', '${ws.baseSchema}', '${ws.tplId}', '${ws.taskId}', '${ws.dealTime}', '${ws.acceptTime}', '${ws.taskName}', '${itSysName}')">
					        <td width="100%">
					            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="worksheetMore">
					              <tr>
					                <td class="worksheetLeftNum">　</td>
					                <td class="worksheetFieldTd">建单人：${ws.creator}</td>
					                <td class="worksheetLeftNum">　</td>
					              </tr>
					              <tr>
					                <td class="worksheetLeftNum">　</td>
					                <td class="worksheetFieldTd">建单时间：<eoms:date value="${ws.createTime}"/></td>
					                <td class="worksheetLeftNum">　</td>
					              </tr>
					            </table>
					        </td>
					      </tr>
					    </table>
					    </td>
					  </tr>
					</table>
					<hr color="#666666" style="padding:0px; margin:0px;"/>
				</dg:gridrow>
				<dg:lefttoolbar>
					<img src="${ctx}/workflow/sheet/images/search.png" onclick="showsearch()"/>
		 		</dg:lefttoolbar>
			</dg:datagrid>
			<c:if test="${fn:length(workSheets) <= 0 }">
				<span class="worksheetFieldTd" style="font-size: 20px">没有符合条件的记录！</span>
			</c:if>
		</div>
  </body>
</html>
<script type="text/javascript">
	try{
		parent.curPage = document.getElementById('var_currentpage').value;
		parent.pageSize = document.getElementById('pageSize').value;
		parent.totalSize = document.getElementById('var_totalrows').value;
		}catch(e){
  		} 
 	</script>
