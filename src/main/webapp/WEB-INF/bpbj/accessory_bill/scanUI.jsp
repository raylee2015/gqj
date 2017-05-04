<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/jquery-easyui-1.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/jquery-easyui-1.5/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/jquery-easyui-1.5/demo/demo.css">
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/base.js"></script>
<script type="text/javascript">
	//记录增加和修改的地址
	var url = "";

	// 保存数据
	function add() {
		setTimeout(addAccessory, 300);
	}

	// 保存数据
	function addAccessory() {
		var params = {
			BASE_TOOL_CODE : getTextBoxValue('baseToolCodeTextBox'),
		};
		save(params, "queryBaseToolForObject.do",
				successFunctionForSaveAccessory);
	}

	function successFunctionForSaveAccessory(result) {
		var data = parent.$('#datagridForAccessoryBillDetail').datagrid(
				'getData');
		if (result == null) {
			alert("没有该物资，请查证");
		} else {
			for (var i = 0; i < data.rows.length; i++) {
				if (result.BASE_TOOL_ID == data.rows[i].BASE_TOOL_ID) {
					result.data_is_have = true;
					if (data.rows[i].DETAIL_BILL_AMOUNT == null) {
						data.rows[i].DETAIL_BILL_AMOUNT = 1;
					} else {
						data.rows[i].DETAIL_BILL_AMOUNT += 1;
					}
					break;
				}
			}
			if (result.data_is_have == null) {
				result.DETAIL_BILL_AMOUNT = 0;
				setTextBoxText('baseToolCodeTextBox', '');
				setTextBoxValue('baseToolCodeTextBox', '');
				data.rows.push(result);
			}
		}
		parent.$('#datagridForAccessoryBillDetail').datagrid('loadData', data);
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		registerKeyPressForTextInput('baseToolCodeTextBox', add);
	});

	//关闭编辑界面
	function closeScanUIForAccessory() {
		parent.closeScanUIForAccessory();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="fit:true,border:false,region:'north'">
			<form id="accessoryForm" method="post" style="width: 100%;">
				<table width="100%">
					<tr>
						<td width="22%">配件编码:</td>
						<td><input id="baseToolCodeTextBox" name="BASE_TOOL_CODE"
							class="easyui-textbox"
							data-options="prompt:'配件编码',required:true,validType:'length[0,20]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="south" border="false"
			style="text-align: right; height: 30px">
			<a class="easyui-linkbutton" iconCls="icon-ok"
				href="javascript:void(0)" onclick="addAccessory()">添加</a> <a
				class="easyui-linkbutton" iconCls="icon-cancel"
				href="javascript:void(0)" onclick="closeScanUIForAccessory()">关闭</a>
		</div>
	</div>
</body>
</html>