<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/jquery-easyui-1.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/jquery-easyui-1.5/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/base.css">
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/base.js"></script>
<script type="text/javascript">
	
</script>
<style>
</style>
</head>
<body style="text-align: center;">
	<div style="margin: 10% 40% auto; font-family: 微软雅黑">
		<div style="margin-bottom: 15px;">
			<h1>
				<b style="color: rgb(14, 45, 95);">综合管理平台</b>
			</h1>
		</div>
		<div id="content" class="easyui-panel" title="登陆"
			style="width: 280px; height: 270px; padding: 10px;">
			<form action="<%=contextPath%>/login.do" method="post">
				<p></p>
				工 号:
				<p></p>
				<input id="userCode" name="userCode" type="text"
					style="width: 250px; height: 32px" class="easyui-textbox" />
				<p></p>
				密 码:
				<p></p>
				<input id="userPassWord" name="userPassWord" type="password"
					style="width: 250px; height: 32px" class="easyui-textbox" /><br>
				<br> <br> <input type="submit" value="登录"
					style="width: 250px; height: 32px" />
				<p></p>
			</form>
		</div>
	</div>
</body>
</html>