/*工单查询列表的处理记录快速查看功能*/
function getProcessLog(baseid, schema, srcObj)
{
	var llDiv = Util.$('logList');
	var srcx = parseInt(event.x);
	var srcy = parseInt(event.y);
	if(llDiv.style.display == 'none')
	{
		var abus = new AjaxBus();
		abus.onComplete = function(responseText, responseXml)
		{
			llDiv.children[0].children[0].innerHTML = '';
			var logList = responseXml.documentElement;
			var logString = '<table border="0" cellpadding="0" cellspacing="0">';
			logString += '<tr>';
			logString += '<th width=110>' + llDivTitle1 + '</th>';
			logString += '<th>' + llDivTitle2 + '</th>';
			logString += '</tr>';
			if(logList.childNodes.length > 0)
			{
				for(var i = 0; i < logList.childNodes.length; i++)
				{
					logString += '<tr>';
					var log = logList.childNodes[i];
					logString += '<td>' + (Util.ie?log.childNodes[0].text:log.childNodes[0].textContent) + '</td>';
					logString += '<td><span>' + (Util.ie?log.childNodes[1].text:log.childNodes[1].textContent) + '</span>' + (Util.ie?log.childNodes[2].text:log.childNodes[2].textContent) + '</td>';
					logString += '</tr>';
				}
			}
			logString += '</table>';
			llDiv.children[0].children[0].innerHTML = logString;
			//srcObj.appendChild(llDiv);
			llDiv.style.left = (srcx - 400) + 'px';
			llDiv.style.display = '';
			if((srcy + parseInt(llDiv.clientHeight)) > (parseInt(Util.$('center').clientHeight) + 30))
			{
				llDiv.style.top = (srcy - parseInt(llDiv.clientHeight) + 15) + 'px';
				llDiv.children[1].children[0].style.display = 'none';
				llDiv.children[1].children[1].style.display = '';
			}
			else
			{
				llDiv.style.top = (srcy - 10) + 'px';
				llDiv.children[1].children[0].style.display = '';
				llDiv.children[1].children[1].style.display = 'none';
			}
		}
		abus.callBackPost('${ctx}/sheet/getProcessLogXML.action?baseId=' + baseid + '&baseSchema=' + schema, null);
	}
   }

function hideProcessLog()
{
	
	var srcnode = event.srcElement;
	//document.getElementsByName('workSheetTitle')[0].value = srcnode.tagName + "=" + srcnode.id + "=" + (srcnode.id == 'logList' || (srcnode.tagName == 'A' && srcnode.tagName == 'a'));
	var isIn = false;
	while(srcnode.tagName != 'BODY' && srcnode.tagName != 'body')
	{
		if(srcnode.id == 'logList' || srcnode.tagName == 'A' || srcnode.tagName == 'a')
		{
			isIn = true;
			break;
		}
		else
		{
			srcnode = srcnode.parentNode;
		}
	}
	
	if(isIn)
	{
		return;
	}
	else
	{
		var llDiv = Util.$('logList');
		llDiv.children[0].children[0].innerHTML = '';
		llDiv.style.display = 'none';
	}
}