<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/plugin/swfupload/import.jsp"%>
<html>
<head>
	<title>上传下载组件例子</title>
</head>
<body>
<%--
	以下是常用属性的解释，请选择使用。
	id:该上传组件的ID，可用通过id.startUploadFile()实现自己的上传按钮，必须指定；
	uploadable：是否可以上传(true|false)，默认为true；
	downable:是否可以下载(true|false)，默认为true；
	isMultiDownload:是否可以批量下载(true|false)，默认为true；
	uploadBtnIsVisible:系统上传下载按钮是否可见，如果您自定义了上传按钮，可以将系统上传按钮隐藏(true|false)，默认为true；
	isEdit:是否可以删除附件(true|false)，默认为true；
	isAutoUpload:是否自动上传附件(true|false)，默认为false；
	createDirByDate：是否根据月份在指定存储路径下创建附件存储子路径(true|false)，默认为false；
	fileTypes:上传文件类型，如"*.jpg;*.png"或者"*.*"，默认为"*.*"；
	progressIsVisible:上传进度条是否可见(true|false)，默认为true；
	attchmentGroupId:附件关系ID，具有相同关系ID的附件作为一组显示到下载列表中，如果不指定，则由系统生成uuid；
	returnValue：每个附件上传成功后，自定义从服务器端的返回信息，，无默认值；	//返回值格式：1-文件名；2-文件真实名；3-存储路径；4-文件大小；5-文件类型；6-关系ID；
																//比如您要返回文件名、存储路径、文件大小，您应该在标签中添加属性为returnValue="1,3,4"
																//返回值也将以这个顺序返回，如"卡农.mp3,D:\music\,3072KB"
	attachmentUser:附件上传人，默认值""。										
--%>
<atta:fileupload 
	id="myfileupload" 
	uploadable="true" 
	downable="true" 
	isMultiDownload="true" 
	isMultiUpload="true"
	uploadBtnIsVisible="true"
	isEdit="true" 
	isAutoUpload="false"
	createDirByDate="false" 
	fileTypes="*.*" 
	progressIsVisible="true"
	attchmentGroupId="rcl-02-upload" 
	returnValue="1,2,3,4,5" >
</atta:fileupload>
<input type="button" value="自定义上传按钮" onclick="submitfrm();" />
<script>
	function submitfrm()
	{
		//手动调用附件上传，myfileupload是标签中的id属性值
		myfileupload.startUploadFile();
	}
	myfileupload.afterUploadSuccess = function(serverData)
	{
		//上传成功后调用的函数，如果上传队列中有5个附件，那么该函数将会回调5次
		//alert(serverData);
	}
	myfileupload.afterUploadComplete = function( serverData )
	{
		//上传完成后调用的函数，不管上传队列中有多少个附件，该函数只会在所有附件上传完毕后回调1次
		//alert("afterUploadComplete-------------"+serverData);
	}
</script>
</body>
</html>
