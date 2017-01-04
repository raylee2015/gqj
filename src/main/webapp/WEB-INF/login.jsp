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
<style type="text/css">
HTML, body {
	height: 100%;
}
</style>
</head>
<body style="margin: 0px; background: #f6f6f6">
	<table style="width: 100%; height: 100%;">
		<tr>
			<td align="center"><div
					style="margin-bottom: 15px; text-align: center; width: 300px; font-family: 微软雅黑;">
					<h1>
						<b style="color: rgb(14, 45, 95);">综合管理平台</b>
					</h1>
				</div>
				<div id="content" class="easyui-panel" title="登陆"
					style="width: 300px; height: 300px; padding: 10px;">
					<form action="<%=contextPath%>/login.do" method="post">
						<p></p>
						工 号:
						<p></p>
						<input id="userCode" name="userCode" type="text"
							style="height: 32px; width: 270px;" class="easyui-textbox" />
						<p></p>
						密 码:
						<p></p>
						<input id="userPassWord" name="userPassWord" type="password"
							style="height: 32px; width: 270px;" class="easyui-textbox" /><br>
						<br> <br> <input type="submit" value="登录"
							style="width: 270px; height: 32px" />
						<p></p>
					</form>
				</div></td>
		</tr>
	</table>
</body>
</html>