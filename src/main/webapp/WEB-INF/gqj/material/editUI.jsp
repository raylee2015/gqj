<%@ page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=UTF-8">
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
<script type="text/javascript"
	src="<%=contextPath%>/js/base.js"></script>
<script type="text/javascript">
	//记录增加和修改的地址
	var url = "";

	// 保存数据
	function saveMaterial() {
		var params = {
			MAT_ID : getTextBoxValue('materialIdTextBox'),
			MAT_NAME : getTextBoxValue('materialNameTextBox'),
		};
		save(params, url, successFunctionForSaveMaterial);
	}

	function successFunctionForSaveMaterial(result) {
		parent.successFunctionForOption(result);
		closeEditUIForMaterial();
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initMaterialForm();
	});

	//初始化表单
	function initMaterialForm() {
		var opType = getTextBoxValue('opType');
		var postId = getTextBoxValue('materialIdTextBox');
		if (opType == 'edit') {
			url = "updateMaterial.do";
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = getRowDataOfParentDataGrid('datagridForMaterial',
					rowIndex);
			$('#materialForm').form('load', rowData);
		} else if (opType == 'add') {
			url = "addNewMaterial.do";
		}
	}

	//关闭编辑界面
	function closeEditUIForMaterial() {
		parent.closeEditUIForMaterial();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="fit:true,border:false,region:'north'">
			<form id="materialForm" method="post"
				style="width: 100%;">
				<div style="display: none">
					<input id="opType" class="easyui-textbox"
						value="<%=request.getParameter("opType")%>" /> <input
						id="materialIdTextBox" class="easyui-textbox"
						name="MAT_ID" /> <input id="rowIndex"
						class="easyui-textbox"
						value="<%=request.getParameter("rowIndex")%>" />
				</div>
				<table width="100%">
					<tr>
						<td width="22%">物资名称:</td>
						<td><input id="materialNameTextBox"
							name="MAT_NAME" class="easyui-textbox"
							data-options="prompt:'物资名称',required:true,validType:'length[0,20]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
					<tr>
						<td width="20%">物资单位:</td>
						<td><input id="matUnitComboBox" name="MAT_UNIT"
							data-options="valueField : 'ID',textField : 'TEXT',require : true,panelHeight : 'auto',	prompt : '物资单位',url : 'queryMatUnitDropList.do'"
							class="easyui-combobox" style="width: 100%; height: 32px;"></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="south" border="false"
			style="text-align: right; height: 30px">
			<a class="easyui-linkbutton" iconCls="icon-ok"
				href="javascript:void(0)" onclick="saveMaterial()">保存</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel"
				href="javascript:void(0)"
				onclick="closeEditUIForMaterial()">关闭</a>
		</div>
	</div>
</body>
</html>