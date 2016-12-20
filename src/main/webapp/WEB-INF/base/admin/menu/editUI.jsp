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
	function saveMenu() {
		var params = {
			MENU_ID : getTextBoxValue('menuIdTextBox'),
			MENU_NAME : getTextBoxValue('menuNameTextBox'),
			MENU_LEVEL : getComboBoxValue('menuLevelComboBox'),
			MENU_URL : getTextBoxValue('menuURLTextBox'),
			MENU_SORT : getTextBoxValue('menuSortTextBox'),
			UP_MENU_ID : getComboTreeValue('menuTree'),
			MENU_EXT_CODE : getTextBoxValue('menuExtCodeTextBox')
		};
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		parent.successFunctionForOption(result);
		closeEditUIForMenu();
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initMenuTree();
		initMenuForm();
		//initMenuLevelComboBox();
	});

	//初始化表单
	function initMenuForm() {
		var opType = getTextBoxValue('opType');
		if (opType == 'add') {
			url = 'addNewMenu.do';
		} else if (opType == 'edit') {
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = parent.$('#datagridForMenu').datagrid('getData').rows[rowIndex];
			$('#menuForm').form('load', rowData);
			url = 'updateMenu.do';
		}
	}

	//初始化树
	function initMenuTree() {
		var treeNodes = parent.$('#menuTree').tree('getRoots');
		$('#menuTree').combotree('loadData', treeNodes);
	}

	//关闭编辑窗口
	function closeEditUIForMenu() {
		parent.closeEditUIForMenu();
	}

	function initMenuLevelComboBox() {
		$('#menuLevelComboBox').combobox({
			onChange : menuLevelChange
		});
	}

	function menuLevelChange(newValue, oldValue) {
		if (newValue == 0 || newValue == 1 || newValue == 2) {
			$('#menuURLTextBox').textbox('disable');
			$('#menuExtCodeTextBox').textbox('disable');
			$('#menuURLTextBox').textbox('setText', '-');
			$('#menuExtCodeTextBox').textbox('setText', '-');
			$('#menuURLTextBox').textbox('setValue', '-');
			$('#menuExtCodeTextBox').textbox('setValue', '-');
		} else if (newValue == 3) {
			$('#menuURLTextBox').textbox('enable');
			$('#menuExtCodeTextBox').textbox('disable');
			$('#menuURLTextBox').textbox('setText', '');
			$('#menuExtCodeTextBox').textbox('setText', '-');
			$('#menuURLTextBox').textbox('setValue', '');
			$('#menuExtCodeTextBox').textbox('setValue', '-');
		} else if (newValue == 4) {
			$('#menuURLTextBox').textbox('disable');
			$('#menuExtCodeTextBox').textbox('enable');
			$('#menuURLTextBox').textbox('setText', '-');
			$('#menuExtCodeTextBox').textbox('setText', '');
			$('#menuURLTextBox').textbox('setValue', '-');
			$('#menuExtCodeTextBox').textbox('setValue', '');
		}
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<form id="menuForm" method="post" style="width: 100%;">
				<div style="display: none">
					<input id="menuIdTextBox" name="MENU_ID" class="easyui-textbox" /><input
						id="opType" value="<%=request.getParameter("opType")%>"
						class="easyui-textbox" /> <input id="rowIndex"
						value="<%=request.getParameter("rowIndex")%>"
						class="easyui-textbox" />
				</div>
				<table style="width: 100%; padding: 10px">
					<tr>
						<td width="30%">上级菜单:</td>
						<td><input id="menuTree" class="easyui-combotree"
							name="UP_MENU_ID" data-options="required:true"
							style="width: 100%; height: 32px"></td>
					</tr>
					<tr>
						<td width="30%">菜单名称:</td>
						<td><input id="menuNameTextBox" name="MENU_NAME"
							class="easyui-textbox"
							data-options="prompt:'菜单名称',required:true,validType:'length[0,25]'"
							style="width: 100%; height: 32px"></td>
					</tr>
					<tr>
						<td width="30%">菜单级别:</td>
						<td><input id="menuLevelComboBox" name="MENU_LEVEL"
							data-options="valueField : 'ID',textField : 'TEXT',require : true,panelHeight : 'auto',prompt : '菜单级别',url : 'queryMenuLevelDropList.do',
							onChange : function(newValue, oldValue){
								menuLevelChange(newValue, oldValue);
							}
							"
							class="easyui-combobox" style="width: 100%; height: 32px"></td>
					</tr>
					<tr>
						<td width="30%">菜单链接:</td>
						<td><input id="menuURLTextBox" name="MENU_URL"
							class="easyui-textbox"
							data-options="prompt:'菜单链接',required:true,validType:'length[0,50]'"
							style="width: 100%; height: 32px"></td>
					</tr>
					<tr>
						<td width="30%">排序号:</td>
						<td><input id="menuSortTextBox" name="MENU_SORT"
							class="easyui-numberbox" style="width: 100%; height: 32px"
							data-options="min:0,max:99,precision:0,prompt:'排序号',required:true,validType:'length[0,2]'" /></td>
					</tr>
					<tr>
						<td width="30%">扩展权限代码:</td>
						<td><input id="menuExtCodeTextBox" name="MENU_EXT_CODE"
							type="text" class="easyui-textbox"
							style="width: 100%; height: 32px"
							data-options="prompt:'扩展权限代码',required:true,validType:'length[0,25]'" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="south" border="false"
			style="text-align: right; height: 30px">
			<a class="easyui-linkbutton" iconCls="icon-ok"
				href="javascript:void(0)" onclick="saveMenu()">保存</a> <a
				class="easyui-linkbutton" iconCls="icon-cancel"
				href="javascript:void(0)" onclick="closeEditUIForMenu()">关闭</a>
		</div>
	</div>
</body>
</html>