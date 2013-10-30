// JavaScript Document
function check_browser() {
   Opera = (navigator.userAgent.indexOf("Opera",0) != -1)?1:0;
   MSIE = (navigator.userAgent.indexOf("Microsoft",0) != -1)?1:0;
   FX = (navigator.userAgent.indexOf("Mozilla",0) != -1)?1:0;
   if ( Opera ) brow_type = "Opera";
   else if ( FX )brow_type = "Firefox";
   else if ( MSIE )brow_type = "MSIE";
   return brow_type;
}
function getWindowHeight() {
	var windowHeight = 0;
	if (typeof(window.innerHeight) == 'number') {
		windowHeight = window.innerHeight;
	}
	else {
		if (document.documentElement && document.documentElement.clientHeight) {
			windowHeight = document.documentElement.clientHeight;
		}
		else {
			if (document.body && document.body.clientHeight) {
				windowHeight = document.body.clientHeight;
			}
		}
	}
	return windowHeight;
}
function getWindowWidth() {
	var windowWidth = 0;
	if (typeof(window.innerWidth) == 'number') {
		windowWidth = window.innerWidth;
	}
	else {
		if (document.documentElement && document.documentElement.clientWidth) {
			windowWidth = document.documentElement.clientWidth;
		}
		else {
			if (document.body && document.body.clientWidth) {
				windowWidth = document.body.clientWidth;
			}
		}
	}
	return windowWidth;
}

function setCenter(x,y) {
	if (document.getElementById) {
		var windowHeight = getWindowHeight();
		var windowWidth = getWindowWidth();
		var brow_type = check_browser(); 
		if (windowHeight > 0) {
			var centerElement = document.getElementById('center');
			if(centerElement != null){
				var centerHeight  = centerElement.offsetHeight;
				var centerWidth  = centerElement.offsetWidth;
				if (windowHeight - (88) >= 0) {
					if (brow_type == "MSIE") {						
						centerElement.style.height = (windowHeight - y) + 'px';
						centerElement.style.width = (windowWidth - x) + 'px';
					}
					else
					{
						centerElement.style.height = (windowHeight - y) + 'px';
						centerElement.style.width = (windowWidth - x) + 'px';
					}
				}
			}
		}
	}
}
function changeRow_color(obj) {
var Ptr=document.getElementById(obj).getElementsByTagName("tr");
	
		for (var i=1;i<Ptr.length+1;i++) 
		{ 
		if(i%2>0)
		 { Ptr[i-1].className = "t2";}
		else
		 {Ptr[i-1].className = "t1";}
		}
	for(var i=0;i<Ptr.length;i++) {
		Ptr[i].onmouseover=function(){
		this.tmpClass=this.className;
		this.className = "t3";    
		};
		Ptr[i].onmouseout=function(){
		this.className=this.tmpClass;
		};
	}
}
function changeRow_color_custom(obj, group) {
	var Ptr=document.getElementById(obj).getElementsByTagName("tr");
		
	for (var i=1;i<Ptr.length;i++) 
	{
		if(Math.floor((i-1)/group)%2>0)
			{Ptr[i].className = "t2";}
		else
			{Ptr[i].className = "t1";}
	}
	for(var i=1;i<Ptr.length;i++)
	{
		var si = 0;
		if((i%group) > 0)
		{
			si = (i%group) - 1;
		}
		else
		{
			si = group - 1;
		}
		//var si = group-1-(i%group);
		//var trarr = '';
		var trarr = new Array();
		for(var j = i-si; j <= group; j++)
		{
			//trarr += ', Ptr[' + j + ']';
			trarr.push(Ptr[j]);
		}
		
		//var mouseOverEvalStr = 'EventHandler.attachEvent(Ptr[i], EventType.mouseover, EventHandler.createEvent(mouseOverEvent' + trarr + '))';
		//eval(mouseOverEvalStr);
		EventHandler.attachEvent(Ptr[i], EventType.mouseover, EventHandler.createEvent(changeRow_color_custom_mouseOverEvent, Ptr[i], si, group-si-1));
		
		
		//var mouseOutEvalStr = 'EventHandler.attachEvent(Ptr[i], EventType.mouseout, EventHandler.createEvent(mouseOutEvent' + trarr + '))';
		//eval(mouseOutEvalStr);
		EventHandler.attachEvent(Ptr[i], EventType.mouseout, EventHandler.createEvent(changeRow_color_custom_mouseOutEvent, Ptr[i], si, group-si-1));
	}
}
function changeRow_color_custom_mouseOverEvent(trobj, s1, s2)
{
	trobj.tmpClass=trobj.className;
	trobj.className = "t3";
	var preObj = trobj.previousSibling;
	for(var i = 0; i < s1; i++)
	{
		preObj.tmpClass=preObj.className;
		preObj.className = "t3";
		preObj = preObj.previousSibling;
	}
	preObj = trobj.nextSibling;
	for(var i = 0; i < s2; i++)
	{
		preObj.tmpClass=preObj.className;
		preObj.className = "t3";
		preObj = preObj.nextSibling;
	}
}
function changeRow_color_custom_mouseOutEvent(trobj, s1, s2)
{
	trobj.className=trobj.tmpClass;
	var preObj = trobj.previousSibling;
	for(var i = 0; i < s1; i++)
	{
		preObj.className=preObj.tmpClass;
		preObj = preObj.previousSibling;
	}
	preObj = trobj.nextSibling;
	for(var i = 0; i < s2; i++)
	{
		preObj.className=preObj.tmpClass;
		preObj = preObj.nextSibling;
	}
}

function getPageMenu(menuName,divName)
{
	activePageMenu = menuName;
	activePageDiv = divName;
}

function PageMenuActive(objName,divName)
{
	document.getElementById(activePageMenu).className = 'tab_hide';
	document.getElementById(activePageDiv).style.display = 'none';
	document.getElementById(objName).className = 'tab_show';
	document.getElementById(divName).style.display = '';
	activePageMenu = objName;
	activePageDiv = divName;
}
function checkAll(){
			var chk = document.forms["form1"].elements["chkAll"];
			var inputObj =  document.forms["form1"].getElementsByTagName("input");
			for (i = 0; i < inputObj.length; i++) {
				var temp = inputObj[i];
				if(temp.type == "checkbox"){
					temp.checked = chk.checked;
				}
			}
		}
/*ban particular character in text field*/
function clearSpecialChar(event)
{
	//               [", <, >,  &, ']
	var specialArr = [34,60,62,38,39];//add the ascii value of particular character
	var srcCode = event.keyCode;
	if(srcCode==undefined)
	{
		srcCode = event.which;
	}
	var b = true;
	for(var i=0;i<specialArr.length;i++)
	{
		if(srcCode==specialArr[i])
		{
			b = false;
			alert('输入字符不能是下列字符之一：\n'+'\"  -英文双引号\n\'  -英文单引号\n<  -小于符号\n>  -大于符号\n&  -&符号');
			break;
		}
	}
	return b;
}
/*open window at the middle&center of screen*/
function openwindow(url,name,iWidth,iHeight)
{
	var url; 
	var name; 
	var iWidth; 
	var iHeight; 
	var iTop = (window.screen.availHeight-30-iHeight)/2; 
	var iLeft = (window.screen.availWidth-10-iWidth)/2; 
	window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=yes');
}
/*
 function: compare old_str and new_str, then output add_str and del_str
 example_1:	old_str=1,2,3,5;new_str=1,3,4,6;compareStr(old_str,new_str)=4,6;2,5
 notice: under any condition, ';' exists all the same!
*/
function compareStr(oldStr,newStr)
{
	if(oldStr==newStr)
	{
		return ';';
	}
	if(oldStr=='')
	{
		return newStr+';';
	}
	if(newStr=='')
	{
		return ';'+oldStr;
	}
	var oldArr = oldStr.split(',');
	var newArr = newStr.split(',');
	var dels = '';
	var adds = '';
	for(var i=0;i<oldArr.length;i++)
	{	
		var tag = 0;
		for(var j=0;j<newArr.length;j++)
		{
			if(oldArr[i] == newArr[j])
			{
				tag = 1;
				break;
			}
		}
		if(tag == 0)
		{
			dels = dels + oldArr[i] + ',';
		}
	}
	for(var i=0;i<newArr.length;i++)
	{	
		var tag = 0;
		for(var j=0;j<oldArr.length;j++)
		{
			if(newArr[i] == oldArr[j])
			{
				tag = 1;
				break;
			}
		}
		if(tag == 0)
		{
			adds = adds + newArr[i] + ',';
		}
	}
	if(dels=='' && adds!='')
	{
		return adds.substring(0,adds.lastIndexOf(',')) + ';';
	}
	else if(adds=='' && dels!='')
	{
		return ';' + dels.substring(0,dels.lastIndexOf(','));
	}
	else if(adds=='' && dels=='')
	{
		return ';';
	}
	else
	{
		return adds.substring(0,adds.lastIndexOf(',')) + ';' + dels.substring(0,dels.lastIndexOf(','));
	}
}