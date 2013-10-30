<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="getWorkflowTemplateXml.do" method="post" enctype="multipart/form-data">
		<label for="baseSchema">工单标识*</label>：
		<input name="baseSchema" id="baseSchema" type="text" value="WF:EL_TTM_TTH" />
		<label for="versionName">versionName</label>:
		<input name="versionName" id="versionName" type="tezt" value="" />
		<input type="submit" value="提　交" />
	</form>
</body>
</html>