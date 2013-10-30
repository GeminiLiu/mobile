//通过封装SWFUpload实现的上传下载组件
var UltraSWFUpload;
/**
 * id:组件标识 uploadAction：上传action queryAction：查询下载action downloadAction
 * ：下载action attachmentGroupId：附件组id savePath：上传/下载路径 uploadable：是否可上传
 * downloadable： 是否可下载 uploadBtnable：上传按钮是否可用 btnImageUrl：选择附件按钮图片url flashUrl
 * ：SWFUpload.swf url fileTypes：上传文件类型 fileTypesDescription：文件类型描述
 * isMultiUpload：是否批量上传 isMultiDownload：是否批量下载 isAutoUpload：是否自动上传
 * progressIsVisible:进度条是否显示
 * imagePath:样式图片路径 createDirByDate：是否根据日期新建文件夹 returnValue：自定义返回值格式
 */
if (UltraSWFUpload === undefined) 
{
	UltraSWFUpload = function(id, uploadAction, queryAction, downloadAction,
			attachmentGroupId, savePath, uploadable, downloadable, uploadBtnable,
			btnImageUrl, flashUrl, fileTypes, fileTypesDescription,
			isMultiUpload, isMultiDownload, isAutoUpload,
			progressIsVisible, imagePath, isEdit, deleteAction, 
			createDirByDate, attachmentUser, returnValue)
	{
		//初始化UltraSWFUpload对象的属性
		this.preInitUltraSWFUpload(id, uploadAction, queryAction, downloadAction,
			attachmentGroupId, savePath, uploadable, downloadable, uploadBtnable,
			btnImageUrl, flashUrl, fileTypes, fileTypesDescription,
			isMultiUpload, isMultiDownload, isAutoUpload,
			progressIsVisible, imagePath, isEdit, deleteAction, 
			createDirByDate, attachmentUser, returnValue);
		//如果可以上传附件，就将UltraSWFUpload对象的属性设置到setting集合中
		if (uploadable==true)
			this.initUltraSWFUpload();
		//获得可下载列表
		if(downloadable==true)
			this.postInitUltraSWFUpload();
	}
}

UltraSWFUpload.prototype.preInitUltraSWFUpload = function(id, uploadAction, queryAction, downloadAction,
			attachmentGroupId, savePath, uploadable, downloadable, uploadBtnable,
			btnImageUrl, flashUrl, fileTypes, fileTypesDescription,
			isMultiUpload, isMultiDownload, isAutoUpload,
			progressIsVisible, imagePath, isEdit, deleteAction, 
			createDirByDate, attachmentUser, returnValue) {
	/*---初始化ultraswfupload对象的属性 BEGIN---*/
	this.uploaderId = id;
	this.uploadAction = uploadAction;
	this.queryAction = queryAction;
	this.downloadAction = downloadAction;
	this.attachmentGroupId = attachmentGroupId;
	//this.savePath = savePath.replace(/\\/g,"\\\\");//不编码直接传到后台时，需要将\转成\\
	this.savePath = savePath;
	this.uploadable = uploadable;
	this.downloadable = downloadable;
	this.uploadBtnable = uploadBtnable;
	this.btnImageUrl = btnImageUrl;
	this.flashUrl = flashUrl;
	this.fileTypes = fileTypes;
	this.fileTypesDescription = fileTypesDescription;
	this.isMultiUpload = isMultiUpload;
	this.isMultiDownload = isMultiDownload;
	this.isAutoUpload = isAutoUpload;
	this.progressIsVisible = progressIsVisible;
	this.imagePath = imagePath;
	this.isEdit = isEdit;
	this.deleteAction = deleteAction;
	this.createDirByDate = createDirByDate;
	this.attachmentUser = attachmentUser;
	this.returnValue = returnValue;
	/*---初始化ultraswfupload对象的属性 END---*/
	//下载列表数组
	this.batchDownArray = new Array();
	//把自身实例保存到数组中
	this.uploaderName = "UltraSWFUpload_" + UltraSWFUpload.movieCount++;
	UltraSWFUpload.instances[this.uploaderName] = this;
	/*-- 初始化界面 --*/
	this.view();
};

UltraSWFUpload.prototype.initUltraSWFUpload = function() {
	try {
		// 初始化SWFUpload
		/*---构造swfupload的setting参数（JSON格式）BEGIN---*/
		this.settings = {};
		this.settings.post_params = {};
		this.settings.custom_settings = {};
		/*---构造swfupload的setting参数（JSON格式）END---*/
		// post_params
		this.settings.post_params["uploader"] = "UltraSWFUpload";
		this.settings.post_params["attachmentGroupId"] = this.attachmentGroupId;
		this.settings.post_params["fileTypes"] = this.fileTypes;
		this.settings.post_params["attachmentUser"] = this.attachmentUser;
		this.settings.post_params["createDirByDate"] = this.createDirByDate;
		this.settings.post_params["returnValue"] = this.returnValue;
		// File Upload Settings
		this.settings["upload_url"] = this.uploadAction;
		this.settings["button_image_url"] = this.btnImageUrl;
		this.settings["flash_url"] = this.flashUrl;
		this.settings["button_placeholder_id"] = this.uploaderId + UltraSWFUpload.SPAN_BTN;
		this.settings.post_params["savePath"] = this.savePath;
		this.settings["file_post_name"] = "file";
		this.settings["file_size_limit"] = "100MB";
		this.settings["file_types"] = this.fileTypes;
		this.settings["file_types_description"] = this.fileTypesDescription;
		this.settings["file_upload_limit"] = "0";

		// upload event settings
		this.settings["file_queue_error_handler"] = fileQueueError;
		this.settings["file_dialog_complete_handler"] = fileDialogComplete;
		this.settings["file_queued_handler"] = fileQueued;
		this.settings["upload_progress_handler"] = uploadProgress;
		this.settings["upload_error_handler"] = uploadError;
		this.settings["upload_success_handler"] = uploadSuccess;
		this.settings["upload_complete_handler"] = uploadComplete;
		
		//button settings
		this.settings["button_width"] = "52";
		this.settings["button_height"] = "18";
		this.settings["button_text"] = '<span class="button">选择附件 <span class="buttonSmall">(10 MB Max)</span></span>';
		this.settings["button_text_style"] = '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }';
		this.settings["button_text_top_padding"] = "0";
		this.settings["button_text_left_padding"] = "0";
		this.settings["button_window_mode"] = SWFUpload.WINDOW_MODE.TRANSPARENT;
		this.settings["button_cursor"] = SWFUpload.CURSOR.HAND;
		if (false == this.isMultiUpload) {
			this.settings["file_queue_limit"] = "1";
			this.settings["button_action"] = SWFUpload.BUTTON_ACTION.SELECT_FILE;
		}
		
		//debug setting
		this.settings["debug"] = false;
		
		/*---传递程序员控制程序的自定义参数到swfupload对象当中，这些参数可以根据需要传递到事件处理脚本当中 BEGIN---*/
		this.settings.custom_settings["upload_target"] = this.uploaderId
				+ UltraSWFUpload.DIV_UP_PROGRESS;// 进度条
		this.settings.custom_settings["fileUploadTable"] = this.uploaderId
				+ UltraSWFUpload.TABLE_UP;       // 上传列表
		this.settings.custom_settings["currentSWFUpload"] = this.uploaderId;// 当前上传器
		this.settings.custom_settings["currentBtnCancel"] = this.uploaderId
				+ UltraSWFUpload.BTN_CANCEL_ALL; // 取消按钮
		this.settings.custom_settings["afterUploadSuccess"] = this.uploaderId
				+ ".afterUploadSuccess";         // 上传成功后的回调方法
		this.settings.custom_settings["afterUploadComplete"] = this.uploaderId
				+ ".afterUploadComplete";        // 上传完成后的回调方法
		this.settings.custom_settings["isUpdateDownloadTable"] = this
				.getDownloadable();              // 上传成功后是否更新下载列表
		this.settings.custom_settings["getUpdateDownTable"] = this.uploaderId
				+ ".getAndInitDownTable()";      // 更新下载列表方法
		this.settings.custom_settings["progressIsVisible"] = this.progressIsVisible;   // 更新下载列表方法
		// =========新增功能
		this.settings.custom_settings["isAutoUpload"] = this.isAutoUpload;             // 是否自动提交上传
		this.settings.custom_settings["imagePath"] = this.imagePath;              // 图片路径
		/*---传递程序员控制程序的自定义参数到swfupload对象当中，这些参数可以根据需要传递到事件处理脚本当中 END---*/
		//构造上传下载对象
		this.uploader = new SWFUpload(this.settings);
	} catch (ex) {
		alert(ex);
	}
};

UltraSWFUpload.prototype.postInitUltraSWFUpload = function() {
	// 如果可下载，获取下载列表
	if (this.getDownloadable()) 
	{
		this.getAndInitDownTable();
	}
};

UltraSWFUpload.prototype.cancelUploadAll = function() {
	var infoTable = document.getElementById(this.getUploaderId()
			+ UltraSWFUpload.TABLE_UP);
	var rows = infoTable.rows;
	var ids = new Array();
	var row;
	if (rows == null) {
		return false;
	}
	for ( var i = 0; i < rows.length; i++) {
		ids[i] = rows[i].id;
	}
	for ( var i = 0; i < ids.length; i++) {
		deleteFile(ids[i], this.getUploaderId() + UltraSWFUpload.TABLE_UP, this
				.getUploaderId(), this.getUploaderId()
				+ UltraSWFUpload.BTN_CANCEL_ALL);
	}
	//
	document.getElementById(this.getUploaderId()
			+ UltraSWFUpload.BTN_CANCEL_ALL).disabled = "disabled";
};

UltraSWFUpload.prototype.afterUploadSuccess = function(serverData) {
	//	
};

UltraSWFUpload.prototype.afterUploadComplete = function(serverData) {
	//	
};

UltraSWFUpload.prototype.startUploadFile = function() {
	this.getUploader().startUpload();
};

UltraSWFUpload.prototype.cancelUpload = function(fileId, trigger_error_event) {
	this.getUploader().cancelUpload(fileId, trigger_error_event);
};
// 属性操作
UltraSWFUpload.prototype.setQueryAction = function(queryAction) {
	this.queryAction = queryAction;
};

UltraSWFUpload.prototype.getQueryAction = function() {
	return this.queryAction;
};

UltraSWFUpload.prototype.setDownloadAction = function(downloadAction) {
	this.downloadAction = downloadAction;
};

UltraSWFUpload.prototype.getDownloadAction = function() {
	return this.downloadAction;
};

UltraSWFUpload.prototype.setImagePath = function(imagePath) {
	this.imagePath = imagePath;
};

UltraSWFUpload.prototype.getImagePath = function() {
	return this.imagePath;
};

UltraSWFUpload.prototype.setAttachmentGroupId = function(attachmentGroupId) {
	this.attachmentGroupId = attachmentGroupId;
};

UltraSWFUpload.prototype.getAttachmentGroupId = function() {
	return this.attachmentGroupId;
};
UltraSWFUpload.prototype.getFileTypesDescription = function()
{
	return this.fileTypesDescription;
};
UltraSWFUpload.prototype.setIsMultiDownload = function(isMultiDownload) {
	this.isMultiDownload = isMultiDownload;
};

UltraSWFUpload.prototype.getIsMultiDownload = function() {
	return this.isMultiDownload;
};

UltraSWFUpload.prototype.setProgressIsVisible = function(progressIsVisible) {
	this.progressIsVisible = progressIsVisible;
};

UltraSWFUpload.prototype.getProgressIsVisible = function() {
	return this.progressIsVisible;
};

UltraSWFUpload.prototype.setIsAutoUpload = function(isAutoUpload) {
	this.isAutoUpload = isAutoUpload;
};

UltraSWFUpload.prototype.getIsAutoUpload = function() {
	return this.isAutoUpload;
};

UltraSWFUpload.prototype.setIsMultiUploadd = function(isMultiUpload) {
	this.isMultiUpload = isMultiUpload;
};

UltraSWFUpload.prototype.getIsMultiUpload = function() {
	return this.isMultiUpload;
};
UltraSWFUpload.prototype.setIsEdit = function(isEdit)
{
	this.isEdit = isEdit;
}
UltraSWFUpload.prototype.getIsEdit = function()
{
	return this.isEdit;
}
UltraSWFUpload.prototype.getDeleteAction = function()
{
	return this.deleteAction;
}
UltraSWFUpload.prototype.setDeleteAction = function(deleteAction)
{
	this.deleteAction = deleteAction;
}
/**
 * 上传按钮是否显示可用
 */
UltraSWFUpload.prototype.setUploadBtnable = function(usable) {
	if (usable == false || usable == "false") {
		this.uploadBtnable = false;
	} else {
		this.uploadBtnable = true;
	}

};

UltraSWFUpload.prototype.getUploadBtnable = function() {
	return this.uploadBtnable;
};

/**
 * 是否显示下载列表
 */
UltraSWFUpload.prototype.setDownloadable = function(downloadable) {
	if (downloadable == true || downloadable == "true") {
		this.downloadable = true;
	} else {
		this.downloadable = false;
	}
};

UltraSWFUpload.prototype.getDownloadable = function() {
	return this.downloadable;
};

/**
 * jsp组件id，即uploader id
 */
UltraSWFUpload.prototype.getUploaderId = function() {
	return this.uploaderId;
};

UltraSWFUpload.prototype.getUploader = function() {
	return this.uploader;
};
/**
 * 设置文件上传器
 */
UltraSWFUpload.prototype.setUploader = function(uploader) {
	this.uploader = uploader;
};
/**
 * 设置上传Url
 */
UltraSWFUpload.prototype.setUploadURL = function(url) {
	this.uploadService = url;
	this.getUploader().settings["upload_url"] = url;
};

UltraSWFUpload.prototype.getUploadURL = function() {
	return this.uploadService;
};

UltraSWFUpload.prototype.setPostParams = function(paramsObject) {
	this.getUploader().setPostParams(paramsObject);
};

/* ******************************************************** */
/* 下载列表 */
/* ******************************************************** */
UltraSWFUpload.prototype.getAndInitDownTable = function() {
	var isEdit = this.isEdit;
	var params = {
		attachmentGroupId : this.getAttachmentGroupId(),
		fileTypesDescription: this.getFileTypesDescription()
	};
	var uploaderId = this.getUploaderId();
	var tableId = uploaderId + UltraSWFUpload.TABLE_DWN;
	var uploaderSelf = this;
	var tempArray = new Array();
	$.post(this.getQueryAction(), params, function(tableData) {
		var downtable = document.getElementById(tableId);
		if(tableData != null && tableId!=undefined && tableData.length==0)
		{
			try 
			{  
	          eval(uploaderId +"deal(0)");
	        } catch(e){}  
			UltraSWFUpload.reSetTable(tableId);
			var cellInfo = downtable.insertRow().insertCell();
			cellInfo.colSpan = downtable.rows[0].cells.length;
			cellInfo.align = "center";
			cellInfo.innerHTML = "空附件列表!";
		}
		else
		{
			if (tableData != undefined) 
			{
			var rows = tableData.length;
			try 
			{  
	          eval(uploaderId +"deal("+rows+")");
	        } catch(e){}   
			UltraSWFUpload.reSetTable(tableId);
			for ( var i = 0; i < rows; i++) 
			{
				var row = downtable.insertRow();
				row.id = uploaderSelf.getUploaderId() + tableData[i]["attachmentId"];
				var col1;
				if(uploaderSelf.isMultiDownload==true)
				{
					col1 = row.insertCell();//复选框
				}
				var col2 = row.insertCell();//附件名称
				var col3 = row.insertCell();//上传人
				var col4 = row.insertCell();//上传时间
				var col5 = row.insertCell();//附件大小
				var col6;
				if(isEdit==true)
				{
					col6 = row.insertCell();//附件操作
				}
				
				// 批量下载时,添加复选框
				if (uploaderSelf.getIsMultiDownload()==true) 
				{
					var checkBox = document.createElement("input");
					checkBox.type = "checkbox";
					checkBox.value = tableData[i]["fileFileName"] + "@comm@"
							+ tableData[i]["fileNewName"] + "@comm@" + tableData[i]["savePath"];
					checkBox.onclick = function()
					{
						if(this.checked==true)//注意，这里的this代表当前复选框
						{
							UltraSWFUpload.checkIsAllChecked(true,uploaderSelf.uploaderName);
						}
						else
						{
							UltraSWFUpload.checkIsAllChecked(false,uploaderSelf.uploaderName);
						}
					}
					tempArray.push(checkBox);
					col1.appendChild(checkBox);
				} 
				col2.innerHTML = "<a href='javascript:"+uploaderSelf.getUploaderId()
										+".singleDownload("+(tempArray.length-1)+");'>"
										+tableData[i]["fileFileName"]
										+"</a>";
				col3.innerHTML = tableData[i]["attUser"];
				col4.innerHTML = tableData[i]["uploadTime"];
				col5.innerHTML = tableData[i]["attSize"];
				
				if(isEdit==true)
				{
					var col6html = "<a href='javascript:"+uploaderSelf.getUploaderId()
										+".delAttachment(\""+tableData[i]["attachmentId"]
										+"\");'><img src='"+uploaderSelf.getImagePath()
										+"images/upload/del.png' border='0' alt='删除附件' title='删除附件'/></a>";//删除附件
					col6.innerHTML = col6html;
				}
			}
			uploaderSelf.batchDownArray = tempArray;
		}
	}
	changeRow_color(tableId);
	}, 'json');
};

/**
单个下载
*/
UltraSWFUpload.prototype.singleDownload = function(checkPoi) {
	var valueStr =  (this.batchDownArray[checkPoi]).value;
	var valueArr = valueStr.split("@comm@");
	//var fileFileName = encodeURIComponent(valueArr[0]);
	//var fileNewName = encodeURIComponent(valueArr[1]);
	//var savePath = encodeURIComponent(valueArr[2]);
	var fileFileName = valueArr[0];
	var fileNewName = valueArr[1];
	var savePath = valueArr[2];
	var href = this.getDownloadAction()+"?fileFileName="+fileFileName+"&fileNewName="+fileNewName+"&savePath="+savePath;
	href = encodeURI(encodeURI(href));
	window.open(href);
}

/**
批量下载
*/
UltraSWFUpload.prototype.batchDownload = function() {
	var allfiles = this.batchDownArray;
	var fileNames = "";
	var fileNameSeparator = "@semi@";
	var hasDownFile = false;
	for ( var i = 0; i < allfiles.length; i++) {
		if (allfiles[i].checked == true) {
			hasDownFile = true;
			fileNames += allfiles[i].value;
			fileNames += fileNameSeparator;
		}
	}
	if (hasDownFile) {
		fileNames = fileNames.substring(0,fileNames.lastIndexOf("@semi@"));
		var downUrl = this.getDownloadAction();
		downUrl += "?isBatchDown=true&fileNewName=" + fileNames + "&savePath=" + this.savePath;
		downUrl = encodeURI(encodeURI(downUrl));
		window.open(downUrl);
		
	} else {
		alert("未选择下载文件,请确认!");
	}
};

UltraSWFUpload.prototype.delAttachment = function(attachmentId)
{
	var uploader = this;
	if(confirm("确认删除该附件！"))
	{
		var deleteUrl = this.getDeleteAction();
		$.post(deleteUrl,{attachmentId:attachmentId},function(result){
			if("true"==result)
			{
				alert("删除成功！");
				uploader.getAndInitDownTable();
			}
			else
			{
				alert("删除失败！");
				uploader.getAndInitDownTable();
			}
		});
	}
}

UltraSWFUpload.reSetTable = function(tableId) {
	var table = document.getElementById(tableId);
	var rows = table.rows;
	if(rows!=null && rows.length>0)
	{
		for(var i=rows.length-1;i>0;i--)
		{
			table.deleteRow(i);
		}
	}
};

/*
全选或全不选复选框
*/
UltraSWFUpload.checkAllAttachment = function(checker,uploaderName)
{
	var currentuploader = UltraSWFUpload.instances[uploaderName];
	var allfiles = currentuploader.batchDownArray;
	for(var i=0;i<allfiles.length;i++)
	{
		allfiles[i].checked = checker.checked;
	}
}

/*
检查是否是全选
*/
UltraSWFUpload.checkIsAllChecked = function(ischeck,uploaderName)
{
	var currentuploader = UltraSWFUpload.instances[uploaderName];
	var allfiles = currentuploader.batchDownArray;
	var result = true;
	if(ischeck)
	{
		for(var i=0;i<allfiles.length;i++)
		{
			if(allfiles[i].checked==false)
			{
				result = false;
				break;
			}
		}
		if(result)
		{
			document.getElementById(currentuploader.uploaderId+"_checkAll").checked = true;
		}
	}
	else
	{
		document.getElementById(currentuploader.uploaderId+"_checkAll").checked = false;
	}
}

/**
 *初始化组件试图
*/
UltraSWFUpload.prototype.view = function()
{
	var uploaderView = "";
	var uploaderContainer = document.getElementById(this.uploaderId + UltraSWFUpload.DIV_CONTENT);//放组件的div
	if (uploaderContainer == null) 
	{
		alert("没有定义存放组件的DIV块，初始化组件视图失败！");
		return;
	}
	/*--生成菜单栏_START--*/
	if((this.uploadable==false) && this.isMultiDownload==false)
	{
		uploaderView += "<div style='display:none;' id='" + this.uploaderId + UltraSWFUpload.DIV_UP_COMPONENT + "' class='choose_files'>\n";
	}
	else
	{
		uploaderView += "<div id='" + this.uploaderId + UltraSWFUpload.DIV_UP_COMPONENT + "' class='choose_files'>\n";
	}
	if(this.uploadable==true || this.isMultiDownload==true)
		uploaderView += "<table>"
		                  +"<tr>";
	//选择文件按钮
	if(this.uploadable==true)
	{
		uploaderView += "<td class='chooseupload_button5' onmouseover=\"this.className='chooseupload_button5_hover'\""
			            +" onmouseout=\"this.className='chooseupload_button5'\">";
		uploaderView += "<span id='" + this.uploaderId + UltraSWFUpload.SPAN_BTN +"'></span>\n";
		uploaderView += "</td>"
	}
	//上传按钮
	if (this.uploadBtnable==true) 
	{
		uploaderView += "<td id='" + this.uploaderId + UltraSWFUpload.BTN_UPLOAD + "' \n"
				     + " onclick='"
				     + this.uploaderId
				     + ".startUploadFile();'\n"
				     + " class='edit_button5'"
				     + "\n onMouseOver='this.className=\"edit_button5_hover\"'\n"
				     + " onmouseout='this.className=\"edit_button5\"'>上传</td>\n";
	}
	//批量下载按钮
	if(this.isMultiDownload==true)
	{
		uploaderView += "<td id='" + this.uploaderId + UltraSWFUpload.BTN_BATCHDOWN + "' \n"
				     + " class='batchdown_button5'"
				     + " onClick='"+this.uploaderId
				     + ".batchDownload()'"
				     + "\n onMouseOver='this.className=\"batchdown_button5_hover\"'\n"
				     + " onmouseout='this.className=\"batchdown_button5\"'>批量下载</td>\n";
	}
	if(this.uploadable==true || this.isMultiDownload==true)
		uploaderView += "</tr></table>\n";
	uploaderView += "</div>\n";
	/*--生成菜单栏_END--*/
	/*--取消上传按钮_START_暂时没用这个按钮--*/
	uploaderView += "<input id='" + this.uploaderId + UltraSWFUpload.BTN_CANCEL_ALL
			     + "' type='hidden' value='取消全部'\n" + " onclick='" + this.uploaderId
			     + ".cancelUploadAll();' disabled='disabled'\n" + " class='"
			     + UltraSWFUpload.CSS_BTN_MOUSEOUT
			     + "' onMouseUp='this.className=\""
			     + UltraSWFUpload.CSS_BTN_MOUSEUP + "\"'\n"
			     + " onmousedown='this.className=\""
			     + UltraSWFUpload.CSS_BTN_MOUSEDOWN + "\"'\n"
			     + " onMouseOver='this.className=\""
			     + UltraSWFUpload.CSS_BTN_MOUSEOVER + "\"'\n"
			     + " onmouseout='this.className=\""
			     + UltraSWFUpload.CSS_BTN_MOUSEOUT + "\"'/>\n";
	/*--取消上传按钮_END--*/
	//隐藏文本框，存储组ID
	uploaderView += "<input id='" + this.uploaderId
			     + UltraSWFUpload.INPUT_HIDDEN_ID + "' type='hidden' value='"
			     + this.getAttachmentGroupId() + "'\n" + " name='"
			     + UltraSWFUpload.INPUT_HIDDEN_NAME + "'/>\n";
	//进度条DIV
	uploaderView += "<div style='background:white;' id='" + this.uploaderId + UltraSWFUpload.DIV_UP_PROGRESS + "'></div>";
	//上传列表DIV
	uploaderView += "<div style='background:white;' id='" + this.uploaderId + UltraSWFUpload.DIV_UP_THUMBNAILS + "'>\n";
	uploaderView += "<table id='" + this.uploaderId + UltraSWFUpload.TABLE_UP + "' class='tableborder' ";
	uploaderView += " style='display:none' ";
	uploaderView += ">";
	uploaderView += "<tr><th>上传队列</th></tr>";
	uploaderView += "</table>\n";
	uploaderView += "</div>\n";
	//下载列表DIV
	uploaderView += "<div id='" + this.uploaderId
			     + UltraSWFUpload.DIV_DWN_THUMBNAILS + "'>\n";
	uploaderView += "<table id='" + this.uploaderId + UltraSWFUpload.TABLE_DWN + "' class='tableborder'>\n";
	if (this.getDownloadable()==true)
	{
		if(this.isMultiDownload==true)
		{
			uploaderView +="<tr>"
						    +"<th width='25'>"
						      +"<input id='"+this.uploaderId+"_checkAll' type='checkbox'"
						      +" onclick='UltraSWFUpload.checkAllAttachment(this,\""+this.uploaderName+"\");'/>"
						    +"</th>"
					        +"<th>附件名称</th>"
					        +"<th width='60'>上传人</th>"
					        +"<th width='120'>上传时间</th>"
					        +"<th width='100'>附件大小</th>";
		}
		else
		{
			uploaderView +="<tr>"
			                +"<th>附件名称</th>"
			                +"<th width='60'>上传人</th>"
			                +"<th width='120'>上传时间</th>"
			                +"<th width='100'>附件大小</th>";
		}
		if(this.isEdit==true)
		{
			uploaderView += "<th width='60'>删除</th>";
		}
		uploaderView += "</tr>\n";
	}
	uploaderView += "</table>\n";
	uploaderView += "</div>\n";
	uploaderContainer.innerHTML += uploaderView;
	changeRow_color(this.uploaderId + UltraSWFUpload.TABLE_UP);
}

/* ******************************************************** */
/* 选择上传文件设置 */
/* ******************************************************** */
/**
 * 设置上传文件类型
 */
UltraSWFUpload.prototype.setFileTypes = function(types, description) {
	this.getUploader().setFileTypes(types, description);
};

/**
 * 设置上传文件大小
 */
UltraSWFUpload.prototype.setFileSizeLimit = function(fileSizeLimit) {
	this.getUploader().setFileSizeLimit(fileSizeLimit);
};

/**
 * 设置上传文件个数,0表示没限制
 */
UltraSWFUpload.prototype.setFileUploadLimit = function(fileUploadLimit) {
	this.getUploader().setFileUploadLimit(fileUploadLimit);
};

/**
 * 设置上传文件数组名称
 */
UltraSWFUpload.prototype.setFilePostName = function(filePostName) {
	this.getUploader().setFilePostName(filePostName);
};
/* ******************************************************** */
/* 选择上传文件的按钮 */
/* ******************************************************** */

/**
 * 设置选择上传文件按钮图片url 按钮图片中需要包括按钮的4个状态，从上到下依次是normal, hover, down/click, disabled.
 * 可以参照官方demo中的图片
 */
UltraSWFUpload.prototype.setButtonImageURL = function(btnImageUrl) {
	this.btnImageUrl = btnImageUrl;
	this.getUploader().setButtonImageURL(btnImageUrl);
};
/**
 * 设置选择上传文件按钮显示的内容,以html的形式,例如： '<span class="button">选择图片 <span
 * class="buttonSmall">(10 MB Max)</span></span>'
 */

UltraSWFUpload.prototype.setButtonText = function(html) {
	this.getUploader().setButtonText(html);
};
/**
 * 设置选择上传文件按钮显示的内容样式,例如： '.button { font-family: Helvetica, Arial, sans-serif;
 * font-size: 12pt; } .buttonSmall { font-size: 10pt; }'
 */

UltraSWFUpload.prototype.setButtonTextStyle = function(css) {
	this.getUploader().setButtonTextStyle(css);
};

UltraSWFUpload.prototype.setButtonTextPadding = function(left, top) {
	this.getUploader().setButtonTextPadding(left, top);
};

/* ******************** */
/* 静态常量 */
/* ******************** */
UltraSWFUpload.instances = {};
UltraSWFUpload.movieCount = 0;
// div
UltraSWFUpload.DIV_CONTENT = "UploaderContainer";
UltraSWFUpload.DIV_UP_COMPONENT = "UploaderComponent";
UltraSWFUpload.DIV_DWN_THUMBNAILS = "DownloadThumbnails";
UltraSWFUpload.DIV_UP_THUMBNAILS = "Uploadthumbnails";
UltraSWFUpload.DIV_UP_OVER = "OverUploadDiv";
UltraSWFUpload.DIV_UP_PROGRESS = "UploadProgressContainer";// 进度条div
//from
UltraSWFUpload.SWFUPLOAD_FORM ="_SWFUploaderForm";
// span
UltraSWFUpload.SPAN_BTN = "SpanButtonPlaceholder";//
// button
UltraSWFUpload.BTN_UPLOAD = "btnUpload";
UltraSWFUpload.BTN_BATCHDOWN = "btnBatchDown";
UltraSWFUpload.BTN_CANCEL_ALL = "btnCancel";
// table
UltraSWFUpload.TABLE_DWN = "DownloadTable";
UltraSWFUpload.TABLE_UP = "UploadTable";
UltraSWFUpload.TABLE_UP_INIT = "Init";
UltraSWFUpload.TABLE_OVER = "UpOverTable";
UltraSWFUpload.TABLE_OVER_TD = "UpOverTableTD";
UltraSWFUpload.TABLE_OVER_FINISH = "UpOverTableFinish";
// hidden input
UltraSWFUpload.INPUT_HIDDEN_ID = "AttachmentGroupId";
UltraSWFUpload.INPUT_HIDDEN_NAME = "AttachmentGroupIdName";
// css
UltraSWFUpload.CSS_TABLE = "ultra_upload_table";
UltraSWFUpload.CSS_COMPONENT = "ultra_upload_component";
UltraSWFUpload.CSS_BTN_MOUSEOUT = "ultra_btn_mouseout";
UltraSWFUpload.CSS_BTN_MOUSEOVER = "ultra_btn_mouseover";
UltraSWFUpload.CSS_BTN_MOUSEDOWN = "ultra_btn_mousedown";
UltraSWFUpload.CSS_BTN_MOUSEUP = "ultra_btn_mouseup";
