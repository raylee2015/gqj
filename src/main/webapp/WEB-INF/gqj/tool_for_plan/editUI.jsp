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
	function saveToolForPlan() {
		var params = {
			TOOL_ID : getTextBoxValue('toolIdTextBox'),
			TOOL_NAME : getTextBoxValue('toolNameTextBox'),
			TOOL_STANDARD_CONFIG_DEMAND : getTextBoxValue('toolStandardConfigDemandTextBox'),
			TOOL_MODEL_DEMAND : getTextBoxValue('toolModelDemandTextBox'),
			TYPE_ID : getComboBoxValue('toolTypeComboBox'),
			TOOL_UNIT : getComboBoxValue('toolUnitComboBox'),
			TOOL_REMARK : getTextBoxValue('toolRemarkTextBox'),
		};
		save(params, url, successFunctionForSaveToolForPlan);
	}

	function successFunctionForSaveToolForPlan(result) {
		parent.successFunctionForOption(result);
		closeEditUIForToolForPlan();
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initToolForPlanForm();
	});

	//初始化表单
	function initToolForPlanForm() {
		var opType = getTextBoxValue('opType');
		var postId = getTextBoxValue('toolIdTextBox');
		if (opType == 'edit') {
			url = "updateToolForPlan.do";
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = getRowDataOfParentDataGrid('datagridForToolForPlan',
					rowIndex);
			$('#toolForPlanForm').form('load', rowData);
		} else if (opType == 'add') {
			url = "addNewToolForPlan.do";
		}
	}

	//关闭编辑界面
	function closeEditUIForToolForPlan() {
		parent.closeEditUIForToolForPlan();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="fit:true,border:false,region:'north'">
			<form id="toolForPlanForm" method="post" style="width: 100%;">
				<div style="display: none">
					<input id="opType" class="easyui-textbox"
						value="<%=request.getParameter("opType")%>" /> <input
						id="toolIdTextBox" class="easyui-textbox" name="TOOL_ID" /> <input
						id="rowIndex" class="easyui-textbox"
						value="<%=request.getParameter("rowIndex")%>" />
				</div>
				<table style="width: 100%; padding: 10px">
					<tr>
						<td width="25%">工器具种类:</td>
						<td><input id="toolTypeComboBox" name="TYPE_ID"
							data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '工器具类型',
							url : 'queryToolForPlanTypeDropList.do'"
							class="easyui-combobox" style="width: 100%; height: 32px;"></td>
					</tr>
					<tr>
						<td width="25%">工器具名称:</td>
						<td><input id="toolNameTextBox" name="TOOL_NAME"
							class="easyui-textbox"
							data-options="prompt:'工器具名称',required:true,validType:'length[0,20]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
					<tr>
						<td width="25%">工器具标准配置:</td>
						<td><input id="toolStandardConfigDemandTextBox"
							name="TOOL_STANDARD_CONFIG_DEMAND" class="easyui-textbox"
							data-options="prompt:'工器具标准配置',required:true,multiline:true,
							validType:'length[0,500]'"
							style="width: 100%; height: 100px" /></td>
					</tr>
					<tr>
						<td width="25%">工器具型号:</td>
						<td><input id="toolModelDemandTextBox" name="TOOL_MODEL_DEMAND"
							class="easyui-textbox"
							data-options="prompt:'工器具型号',required:true,multiline:true,
							validType:'length[0,500]'"
							style="width: 100%; height: 100px" /></td>
					</tr>
					<tr>
						<td width="25%">工器具单位:</td>
						<td><input id="toolUnitComboBox" name="TOOL_UNIT"
							data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '工器具单位',
							url : 'queryToolForPlanUnitDropList.do'"
							class="easyui-combobox" style="width: 100%; height: 32px;"></td>
					</tr>
					<tr>
						<td width="25%">工器具备注:</td>
						<td><input id="toolRemarkTextBox" name="TOOL_REMARK"
							class="easyui-textbox"
							data-options="prompt:'工器具备注',validType:'length[0,50]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="south" border="false"
			style="text-align: right; height: 30px">
			<a class="easyui-linkbutton" iconCls="icon-ok"
				href="javascript:void(0)" onclick="saveToolForPlan()">保存</a> <a
				class="easyui-linkbutton" iconCls="icon-cancel"
				href="javascript:void(0)" onclick="closeEditUIForToolForPlan()">关闭</a>
		</div>
	</div>
</body>
</html>