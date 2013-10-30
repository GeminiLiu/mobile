function lookWorkSheetMore(worksheetmaintableID,worksheetmoreID,worksheetmoreimgID)
{
	if (document.getElementById(worksheetmoreID).style.display == "none")
	{
		document.getElementById(worksheetmaintableID).style.background="#0099CC";
		document.getElementById(worksheetmoreID).style.display="";
		document.getElementById(worksheetmoreimgID).src=jsCtx + "/workflow/sheet/images/lookmore.png";
	}
	else
	{
		document.getElementById(worksheetmaintableID).style.background="";
		document.getElementById(worksheetmoreID).style.display="none";
		document.getElementById(worksheetmoreimgID).src=jsCtx +"/workflow/sheet/images/lookno.png";
	}
}
function onMousemoveWorkSheet(worksheetmaintableID)
{
	document.getElementById(worksheetmaintableID).style.background="#0099CC";
}
function onMouseoutWorkSheet(worksheetmaintableID)
{
	document.getElementById(worksheetmaintableID).style.background="";
}

function nextPage() {
	tranferPage('next');
}

function prevPage() {
	tranferPage('previous');
}

function openWaitSheet(baseId, baseSchema, tplId, taskId, dealTime, acceptTime, taskName, itSysName) {
	taskName = encodeURI(taskName);
	window.location=jsCtx + '/sheetInfo/waitingSheetInfo.action?baseId='+baseId+'&baseSchema='+baseSchema+'&tplId='+tplId+'&itSysName='+itSysName+'&taskId='+taskId+'&dealTime='+dealTime+'&acceptTime='+acceptTime+'&taskName=' + encodeURI(taskName);
}

function openDealedSheet(baseId, baseSchema, tplId, taskId, dealTime, acceptTime, taskName, itSysName) {
	taskName = encodeURI(taskName);
	window.location=jsCtx + '/sheetInfo/dealedSheetInfo.action?baseId='+baseId+'&baseSchema='+baseSchema+'&tplId='+tplId+'&itSysName='+itSysName+'&taskId='+taskId+'&dealTime='+dealTime+'&acceptTime='+acceptTime+'&taskName=' + encodeURI(taskName);
}

function showOrhiddenDiv(selectFieldDiv) {
	if (document.getElementById(selectFieldDiv).style.display=="none") {
		document.getElementById(selectFieldDiv).style.display="";
	} else {
		document.getElementById(selectFieldDiv).style.display="none";
	}
}