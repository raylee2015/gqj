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
	function saveDictionary() {
		var params = {
			DIC_ID : getTextBoxValue('dictionaryIdTextBox'),
			DIC_CODE : getTextBoxValue('dictionaryCodeTextBox'),
			DIC_NAME : getTextBoxValue('dictionaryNameTextBox'),
			DIC_VALUE : getTextBoxValue('dictionaryValueTextBox'),
			DIC_LABEL : getTextBoxValue('dictionaryLabelTextBox'),
			DIC_SORT : getTextBoxValue('dictionarySortTextBox'),
			MENU_ID : getComboTreeValue('menuTree')
		};
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		parent.successFunctionForOption(result);
		closeEditUIForDictionary();
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initDictionaryForm();
		initMenuTree();
	});

	//初始化表单
	function initDictionaryForm() {
		var opType = getTextBoxValue('opType');
		if (opType == 'add') {
			url = 'addNewDictionary.do';
		} else if (opType == 'edit') {
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = getRowDataOfParentDataGrid('datagridForDictionary',
					rowIndex);
			$('#dictionaryForm').form('load', rowData);
			url = 'updateDictionary.do';
		}
	}

	//初始化树
	function initMenuTree() {
		var treeNodes = parent.$('#menuTree').tree('getRoots');
		$('#menuTree').combotree('loadData', treeNodes);
	}

	//关闭编辑窗口
	function closeEditUIForDictionary() {
		parent.closeEditUIForDictionary();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<form id="dictionaryForm" method="post" style="width: 100%;">
				<div style="display: none">
					<input id="dictionaryIdTextBox" name="DIC_ID"
						class="easyui-textbox" /><input id="opType"
						value="<%=request.getParameter("opType")%>" class="easyui-textbox" />
					<input id="rowIndex" value="<%=request.getParameter("rowIndex")%>"
						class="easyui-textbox" />
				</div>
				<table style="width: 100%; padding: 10px">
					<tr>
						<td width="22%">所属系统:</td>
						<td><input id="menuTree" name="MENU_ID"
							class="easyui-combotree" data-options="required:true"
							style="width: 100%; height: 32px"></td>
					</tr>
					<tr>
						<td width="22%">字典代号:</td>
						<td><input id="dictionaryCodeTextBox" name="DIC_CODE"
							class="easyui-textbox"
							data-options="prompt:'字典代号',required:true,validType:'length[0,25]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
					<tr>
						<td width="22%">字典名称:</td>
						<td><input id="dictionaryNameTextBox" name="DIC_NAME"
							class="easyui-textbox"
							data-options="prompt:'字典名称',required:true,validType:'length[0,25]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
					<tr>
						<td width="22%">字典值:</td>
						<td><input id="dictionaryValueTextBox" name="DIC_VALUE"
							class="easyui-textbox"
							data-options="prompt:'字典值',required:true,validType:'length[0,25]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
					<tr>
						<td width="22%">字典含义:</td>
						<td><input id="dictionaryLabelTextBox" name="DIC_LABEL"
							class="easyui-textbox"
							data-options="prompt:'字典含义',required:true,validType:'length[0,25]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
					<tr>
						<td width="22%">排序号:</td>
						<td><input id="dictionarySortTextBox" name="DIC_SORT"
							type="text" class="easyui-numberbox"
							style="width: 100%; height: 32px"
							data-options="min:0,max:99,precision:0,prompt:'排序号',required:true,validType:'length[0,2]'" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="south" border="false"
			style="text-align: right; height: 30px">
			<a class="easyui-linkbutton" iconCls="icon-ok"
				href="javascript:void(0)" onclick="saveDictionary()">保存</a> <a
				class="easyui-linkbutton" iconCls="icon-cancel"
				href="javascript:void(0)" onclick="closeEditUIForDictionary()">关闭</a>
		</div>
	</div>
</body>
</html>