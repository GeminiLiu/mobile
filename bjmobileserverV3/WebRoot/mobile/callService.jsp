<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="call.action" method="post" enctype="multipart/form-data">
		<input name="serviceCode" id="serviceCode_L001" type="radio" value="L001" />
		<label for="serviceCode_L001">登录</label>
		<input name="serviceCode" id="serviceCode_L002" type="radio" value="L002" />
		<label for="serviceCode_L002">工单模版</label>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="serviceCode" id="serviceCode_G001" type="radio" value="G001" />
		<label for="serviceCode_G001">待办列表</label>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="serviceCode" id="serviceCode_G002" type="radio" value="G002" />
		<label for="serviceCode_G002">工单数据</label>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="serviceCode" id="serviceCode_G003" type="radio" value="G003" />
		<label for="serviceCode_G003">动作页面</label>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="serviceCode" id="serviceCode_G004" type="radio" value="G004" />
		<label for="serviceCode_G004">工单提交</label>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="serviceCode" id="serviceCode_G005" type="radio" value="G005" />
		<label for="serviceCode_G005">处理记录</label>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="serviceCode" id="serviceCode_G007" type="radio" value="G007" />
		<label for="serviceCode_G007">离线工单</label>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="serviceCode" id="serviceCode_G008" type="radio" value="G008" />
		<label for="serviceCode_G008">获取工单附件</label>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="serviceCode" id="serviceCode_T001" type="radio" value="T001" />
		<label for="serviceCode_T001">获取派发树</label>
		<br />
		<input name="serviceCode" id="serviceCode_PT001" type="radio" value="PT001" />
		<label for="serviceCode_PT001">代办任务列表</label>
		<input name="serviceCode" id="serviceCode_PT002" type="radio" value="PT002" />
		<label for="serviceCode_PT002">巡检内容</label>
		<input name="serviceCode" id="serviceCode_PT003" type="radio" value="PT003" />
		<label for="serviceCode_PT003">巡检内容提交</label>
		<input name="serviceCode" id="serviceCode_V001" type="radio" value="V001" />
		<label for="serviceCode_V001">手机版本</label>
		<input name="serviceCode" id="serviceCode_V002" type="radio" value="V002" />
		<label for="serviceCode_V002">手机应用</label>
		<br />
		<textarea name="inputXml" style="width:600px;" rows="20"></textarea>
		<br />
		图片：<input type="file" name="picFile" />
		<br />
		录音：<input type="file" name="recFile" />
		<br />
		<input type="submit" value="提　交" />
	</form>
</body>
</html>