<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8"/> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp"%>	
	<link href="${ctx}/workflow/sheet/css/sheetInfo.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/workflow/sheet/css/menu.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script LANGUAGE="JavaScript" type="text/javascript" src="${ctx}/workflow/sheet/js/calendar.js" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript" src="${ctx}/workflow/sheet/js/tree.js"  defer="defer"></script>
	<script type="text/javascript" src="${ctx}/workflow/sheet/js/util.js"  defer="defer"></script>
	<script type="text/javascript" src="${ctx}/workflow/sheet/js/actor.js"  defer="defer"></script>
	<script type="text/javascript" src="${ctx}/workflow/sheet/js/dealActor.js"  defer="defer"></script>
	<title>工单详情</title>
	
	<script type="text/javascript">
			var loading = "(加载中．．．．．．)";
			var dealButtonID = "dealButton";
			var dealdivID = "dealdiv";//处理页面table
			var dealInfoTitleID = "dealInfoTitle";//处理标题 "填写"
			var dealFieldDivID = "dealFieldDiv";//处理字段
			var buttonTr = "buttonTr";
			var dictNames = [];
			var dictObjs = [];
			var sel_tip = "请选择";
			
			
		//用Ajax去获得该操作需要填写的字段
		function getDealField() {
			var actionStr = getSelectedValue(dealButtonID);
			var bus = new AjaxBus();
			bus.onComplete = function(responseText, responseXml){
				var ary = responseText.split("__AA__XX__");
				
				document.getElementById(dealFieldDivID).innerHTML = ary[0];
				document.getElementById(buttonTr).style.display = '';
				loadingTitle(false);
				if(ary.length > 1 && ary[1] != '') {
					eval(ary[1]);
				}
			}
			actionStr = encodeURI(actionStr);
			var url = '${ctx}/sheetInfo/getEditFieldsByAction.action?itSysName=${itSysName}&baseId=${baseId}&baseSchema=${baseSchema}&tplId=${tplId}&taskId=${taskId}&actionStr=' + encodeURI(actionStr) +'&r='+Math.random();
			bus.callBackGet(url);
		}
		
		function loadingTitle(flag) {
			var s = "";
			if(flag) {
				s = loading;
			} 
			document.getElementById(dealInfoTitleID).innerHTML=getSelectedText(dealButtonID) + "填写" + s;
		}
		
		//控制点击操作按钮后，显示该操作的相关填写字段(web后台拼接的html调用)
		function showOrhiddenDiv(selectFieldDiv)
		{
			if (document.getElementById(selectFieldDiv).style.display=="none")
			{
				document.getElementById(selectFieldDiv).style.display="";
			}
			else
			{
				document.getElementById(selectFieldDiv).style.display="none";
			}
		}
		
		function delDealField() {
			document.getElementById(dealdivID).style.display="none";
			document.getElementById(dealFieldDivID).innerHTML = "";
			document.getElementById(buttonTr).style.display = "none";
			document.getElementById(dealInfoTitleID).innerHTML="填写";
		}
		
		//控制点击操作按钮后，显示该操作的相关填写字段
		function dealWorkSheet(buttonName) {
			if (buttonName == "") {
				nodealWorkSheet();
				return;
			}
			delDealField();
			document.getElementById(dealdivID).style.display="";
			loadingTitle(true);
			getDealField();
		}
		//控制点击操作的返回按钮后，隐藏该操作的相关填写字段
		function nodealWorkSheet() {
			delDealField();
			document.getElementById(dealButtonID).value="";
		}
		//操作的提交函数
		function dealCommit() {
			document.getElementById("actionStr").value = getSelectedValue(dealButtonID);
			if(validate()) {
				loadingTitle(true);
				disableButtion();
				document.getElementById("form").submit();
			}
		}
		
		function enableButtion() {
			document.getElementById("submitBtn").disabled=false;
			document.getElementById("returnBtn").disabled=false;
			document.getElementById("dealButton").disabled=false;
		}
		
		function disableButtion() {
			document.getElementById("submitBtn").disabled=true;
			document.getElementById("returnBtn").disabled=true;
			document.getElementById("dealButton").disabled=true;
		}
		
		//表单必填验证
		function validate() {
			var valObj = document.getElementById("valFields");
			var msg = "";
			if(valObj != null) {
				var fiedStr = valObj.value;
				if(fiedStr != "") {
					var ids = fiedStr.split(";");
					for(var i=0;i<ids.length;i++) {
						var id = ids[i];
						if(id != "") {
							var cname = document.getElementById(id + ".cname").value;
							var content = document.getElementById(id + ".content").value;
							if(content == "") {
								msg += cname + "\n";
							}
						}
					}
					if(msg.length > 0) {
						alert(msg + "字段不能为空！");
						return false;
					}
				}
			}
			return true;
		}
		
		function getSelected(selId) {
			var obj = document.getElementById(selId);
			var index = obj.selectedIndex; // 选中索引
			return obj.options[index];
		}
		
		function getSelectedValue(selId) {
			return getSelected(selId).value;
		}
		
		function getSelectedText(selId) {
			return getSelected(selId).text;
		}
		
		function getSelectedTitle(selId) {
			return getSelected(selId).title;
		}
		
		function clearSelect(selId) {
			var select =document.getElementById(selId);
			if(select) {
				var len=select.options.length;
				for(var i=len-1;i>-1;i--) {
					select.options.remove(i);
				}
				select.options.add(new Option(sel_tip, "")); 
			}
		}
		
		function addOption(selId, key, value) {
			var select =document.getElementById(selId);
			if(select) {
				select.options.add(new Option(value, key)); 
			}
		}
		
		//字典下拉框变更(web后台拼接的html调用)
		function changeSelect(dictName, thisId, nextId, clearIds) {
			clearSelect(nextId);
			if(clearIds) {
				var clears = clearIds.split(",");
				for(var i=0;i<clears.length;i++) {
					clearSelect(clears[i]);
				}
			}
			if(dictName) {
				var sel = document.getElementById(thisId);
				var index = sel.selectedIndex; // 选中索引
				var key = sel.options[index].value;
				for(var i=0;i<dictNames.length;i++) {
					if(dictNames[i] == dictName) {
						var di = findDict(dictObjs[i].dict, key);
						if(di) {
							for(var j=0;j<di.dict.length;j++) {
								var newKey = di.dict[j].key;
								var newValue = di.dict[j].value;
								addOption(nextId, newKey, newValue);
							}
						}
						break;
					}
				}
			}
		}
		
		//递归查找当前字典的子集节点
		function findDict(dict, key) {
			if(dict && key) {
				var thisKey = dict.key;
				var thisValue = dict.value;
				var thisDicts = dict.dicts;
				if(thisKey == key) {
					return thisDicts;
				}
				if(thisDicts) {
					var len = dict.dicts.dict.length;
					for(var i=0;i<len;i++) {
						var find = findDict(dict.dicts.dict[i], key);
						if(find) {
							return find;
						}
					}
				}
			}
			return null;
		}
		
		//拼接多级字典值字符串进指定文本域(web后台拼接的html调用)
		function joinDictStr(txtId, dictIds) {
			var str = "";
			if(dictIds) {
				var dicIds = dictIds.split(",");
				for(var i=0;i<dicIds.length;i++) {
					var s = getSelectedText(dicIds[i]);
					if(s && s != '' && s != sel_tip) {
						str += "." + s;
					}
				}
			}
			document.getElementById(txtId).value = str.length>0 ? str.substring(1): "";
		}
		
		//控制时间控件显示隐藏
		function showOrHidCalendar(divId, txtId) {
			if(WebCalendar) {
				var existDivId = WebCalendar.objExport.id +'_time_div';
				hiddenCalendar(existDivId, 0);
				if(existDivId != divId) {
					showCalendar(divId, txtId);
				}			
			} else {
				showCalendar(divId, txtId);			
			}
		}
		
/**
		function displayDiv() {
			var flag = document.all.divCenter.style.display;
			if (flag == 'none') {
				flag = '';
			} else {
				flag='none';
			}
			
			var oRect = document.getElementById("dealFieldDiv").getBoundingClientRect();   
			var x=oRect.left;
			var y=oRect.top;
			var h=oRect.bottom-y;
			var w=oRect.right - x;

			document.getElementById('divCenter').style.left = x +'px'; 
 				document.getElementById('divCenter').style.top = y+'px';
 				document.getElementById('divCenter').style.height = h+'px';
 				document.getElementById('divCenter').style.width = w+'px';
 				document.getElementById('divCenter').style.display=flag;
		}
		**/
		
       </script>
</head>

<body style="font-size:12px;padding:0px; margin:0px; background-color:#000000;">
<table class="worksheetPageTable" width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td style='height:30px;padding-left:5px' align="left" valign="middle"><span style="height:30px; font-weight:bolder">工单处理</span></td>
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
            <tr>
			    <td>
			        <table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td colspan="2" class="infotitle">工单操作处理</td>
			          </tr>
			          <tr>
			            <td class="worksheetFieldLabel">环节处理建议：</td>
			            <td class="worksheetFieldValue"><textarea class="worksheetFieldValueInputMoreline" readonly="readonly" disabled="disabled">${taskName}</textarea></td>
			          </tr>
			          <tr>
			            <td class="worksheetFieldLabel">环节受理时限：</td>
			            <td class="worksheetFieldValue"><textarea class="worksheetFieldValueInputOneline" readonly="readonly" disabled="disabled"><eoms:date value="${acceptTime}"/></textarea></td>
			          </tr>
			          <tr>
			            <td class="worksheetFieldLabel">环节处理时限：</td>
			            <td class="worksheetFieldValue"><textarea class="worksheetFieldValueInputOneline" readonly="readonly" disabled="disabled"><eoms:date value="${dealTime}"/></textarea></td>
			          </tr>
			          <tr>
			            <td class="worksheetFieldLabel" style="padding-left:2px;background-color:#222222;font-style:italic;text-decoration: underline;">*选择操作动作：</td>
			            <td class="worksheetFieldValue" style="background-color:#222222;padding-left:0px;">            	
			                <select name="dealButton" id="dealButton" onchange="dealWorkSheet(this.value)" class="worksheetdealButton">
			                  <option value="" selected="selected">请选择</option>
			                  <c:forEach items="${actions}" var="ac" varStatus="i">
			                  		<option value="${ac.actionId}@${ac.actionType}@${ac.actionName}@${ac.needActor}@${ac.singleFlag}@${ac.actorDesc}">${ac.actionName}</option>
			                  </c:forEach>
			                </select>
			            </td>
			          </tr>
			          <tr>
			            <td colspan="2">&nbsp;<iframe name="submitIframe" style="display:none"></iframe>
			            	<s:form action="/sheetInfo/save.action" id="form" target="submitIframe">
			            		<input type="hidden" name="baseId" value="${baseId}"/>
			            		<input type="hidden" name="baseSchema" value="${baseSchema}"/>
			            		<input type="hidden" name="itSysName" value="${itSysName}"/>
			            		<input type="hidden" name="actionStr" id="actionStr"/>
				            	<table width="100%" border="0" cellspacing="0" cellpadding="0"  id="dealdiv" style="display:none; border-style:ridge;border-width:3px; border-color:#999999;">
				                  <tr>
				                    <td colspan="2" id="dealInfoTitle" class="dealbuttoninfo" style="background-color:#666666; text-align:center">
				                        填写
				                    </td>
				                  </tr>
				                  <tr>
				                    <td colspan="2" id="dealFieldDiv" style="background-color:#444444; ">
				                    </td>
				                  </tr>                 
				                  <tr id="buttonTr" style="display:none">
				                    <td colspan="2" class="dealbuttoninfo" align="center" style="background-color:#666666; ">
				                    	<table width="10px" border="0" cellspacing="0" cellpadding="0">
				                  		<tr>
				                            <td style="width:50%; padding-right:5px">
				                            	<input name="btnsubmit" type="button" id="submitBtn" value="　提 交　" style="color:#333333" onclick="dealCommit();"/>
				                            </td>
				                            <td style="width:50%; padding-left:5px;">
				                            	<input name="btnreset" type="button"  id="returnBtn" value="　返 回　" style="color:#333333" onclick="nodealWorkSheet();"/>
				                            </td>
				                    	</tr>
				                      	</table> 
				                    </td>
				                  </tr>
				              	</table>
							</s:form>
			          </td>
			          </tr>           
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
								   <c:when test="${fn:length(field.content) <= 19}">
								   		<c:set var="css" value="worksheetFieldValueInputOneline"/>
								   </c:when>
								    <c:when test="${fn:length(field.content) < 30}">
								   		<c:set var="css" value="worksheetFieldValueInputTwoline"/>
								   </c:when>
								   <c:otherwise>
								   		<c:set var="css" value="worksheetFieldValueInputMoreline"/>
								   </c:otherwise>
								</c:choose>
								<!-- 
								<td class="worksheetFieldValue"><textarea class="${css}" readonly="readonly">${field.content}</textarea></td>
								 -->
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
