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
	//记录新增或者修改的方法
	var url;

	// 保存数据
	function saveParam() {
		var params = {
			PARAM_ID : getTextBoxValue('paramIdTextBox'),
			PARAM_KEY : getTextBoxValue('paramKeyTextBox'),
			PARAM_VALUE : getTextBoxValue('paramValueTextBox'),
			PARAM_REMARK : getTextBoxValue('paramRemarkTextBox'),
			MENU_ID : getComboTreeValue('menuTree')
		};
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		parent.successFunctionForOption(result);
		closeEditUIForParam();
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initMenuTree();
		initParamForm();
	});

	//初始化表单
	function initParamForm() {
		var opType = getTextBoxValue('opType');
		if (opType == 'add') {
			url = 'addNewParam.do';
		} else if (opType == 'edit') {
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = getRowDataOfParentDataGrid('datagridForParam',
					rowIndex);
			$('#paramForm').form('load', rowData);
			url = 'updateParam.do';
		}
	}

	//初始化树
	function initMenuTree() {
		var treeNodes = parent.$('#menuTree').tree('getRoots');
		$('#menuTree').combotree('loadData', treeNodes);
	}

	//关闭编辑窗口
	function closeEditUIForParam() {
		parent.closeEditUIForParam();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<form id="paramForm" method="post" style="width: 100%;">
				<div style="display: none">
					<input id="paramIdTextBox" name="PARAM_ID" class="easyui-textbox" />
					<input id="opType" value="<%=request.getParameter("opType")%>"
						class="easyui-textbox" /> <input id="rowIndex"
						value="<%=request.getParameter("rowIndex")%>"
						class="easyui-textbox" />
				</div>
				<table style="width: 100%; padding: 10px">
					<tr>
						<td width="25%">所属系统:</td>
						<td><input id="menuTree" name="MENU_ID"
							class="easyui-combotree" data-options="required:true"
							style="width: 100%; height: 32px"></td>
					</tr>
					<tr>
						<td width="25%">参数键:</td>
						<td><input id="paramKeyTextBox" name="PARAM_KEY"
							class="easyui-textbox"
							data-options="prompt:'参数键',required:true,validType:'length[0,25]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
					<tr>
						<td width="25%">参数值:</td>
						<td><input id="paramValueTextBox" name="PARAM_VALUE"
							class="easyui-textbox"
							data-options="prompt:'参数值',required:true,validType:'length[0,25]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
					<tr>
						<td width="25%">备注:</td>
						<td><input id="paramRemarkTextBox" name="PARAM_REMARK"
							class="easyui-textbox"
							data-options="prompt:'备注',required:true,validType:'length[0,50]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="south" border="false"
			style="text-align: right; height: 30px">
			<a class="easyui-linkbutton" iconCls="icon-ok"
				href="javascript:void(0)" onclick="saveParam()">保存</a> <a
				class="easyui-linkbutton" iconCls="icon-cancel"
				href="javascript:void(0)" onclick="closeEditUIForParam()">关闭</a>
		</div>
	</div>
</body>
</html>