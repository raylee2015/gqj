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
	function saveUser() {
		var params = {
			USER_ID : getTextBoxValue('userIdTextBox'),
			USER_NAME : getTextBoxValue('userNameTextBox'),
			USER_CODE : getTextBoxValue('userCodeTextBox'),
			USER_PHONE : getTextBoxValue('userPhoneTextBox'),
			USER_SORT : getTextBoxValue('userSortTextBox'),
			USER_DEPT_ID : getComboTreeValue('deptTree'),
			USER_USE_FLAG : getComboBoxValue('userUseFlagComboBox'),
			USER_LOCK_FLAG : getComboBoxValue('userLockFlagComboBox')
		};
		save(params, url, successFunctionForSave)
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		parent.successFunctionForOption(result);
		closeEditUIForUser();
	}

	$(document).ready(function() {
		closeCache();
		initUserForm();
		initDeptTree();
	});

	//初始化表单
	function initUserForm() {
		var opType = getTextBoxValue('opType');
		if (opType == 'add') {
			url = 'addNewUser.do';
		} else if (opType == 'edit') {
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = parent.$('#datagridForUser').datagrid('getData').rows[rowIndex];
			$('#userForm').form('load', rowData);
			url = 'updateUser.do';
		}
	}

	//初始化树
	function initDeptTree() {
		var treeNodes = parent.$('#deptTree').tree('getRoots');
		$('#deptTree').combotree('loadData', treeNodes);
	}

	//关闭编辑窗口
	function closeEditUIForUser() {
		parent.closeEditUIForUser();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="fit:true,region:'north',border:false">
			<form id="userForm" method="post" style="width: 100%">
				<div style="display: none">
					<input id="userIdTextBox" name="USER_ID" class="easyui-textbox" />
					<input id="opType" value="<%=request.getParameter("opType")%>"
						class="easyui-textbox" /> <input id="rowIndex"
						value="<%=request.getParameter("rowIndex")%>"
						class="easyui-textbox" />
				</div>
				<table style="width: 100%; padding: 10px">
					<tr>
						<td width="20%;">所属部门:</td>
						<td><input id="deptTree" class="easyui-combotree"
							name="USER_DEPT_ID" data-options="required:true"
							style="width: 100%; height: 32px"></td>
					</tr>
					<tr>
						<td width="20%">用户名称:</td>
						<td><input id="userNameTextBox" name="USER_NAME"
							class="easyui-textbox"
							data-options="prompt:'用户名称',required:true,validType:'length[0,10]'"
							style="width: 100%; height: 32px"></td>
					</tr>
					<tr>
						<td width="20%">用户编号:</td>
						<td><input id="userCodeTextBox" name="USER_CODE"
							class="easyui-textbox"
							data-options="prompt:'用户编号',required:true,validType:'length[4,10]'"
							style="width: 100%; height: 32px;"></td>
					</tr>
					<tr>
						<td width="20%">手机号码:</td>
						<td><input id="userPhoneTextBox" name="USER_PHONE"
							type="text" class="easyui-numberbox"
							style="width: 100%; height: 32px;"
							data-options="precision:0,prompt:'手机号码',required:true,validType:'length[11,11]'" /></td>
					</tr>
					<tr>
						<td width="20%">是否在用:</td>
						<td><input id="userUseFlagComboBox" name="USER_USE_FLAG"
							data-options="valueField : 'ID',textField : 'TEXT',	require : true,	panelHeight : 'auto',prompt : '是否在用',url : 'queryUserUseFlagDropList.do'"
							class="easyui-combobox" style="width: 100%; height: 32px;"></td>
					</tr>
					<tr>
						<td width="20%">是否锁定:</td>
						<td><input id="userLockFlagComboBox" name="USER_LOCK_FLAG"
							data-options="valueField : 'ID',textField : 'TEXT',require : true,panelHeight : 'auto',	prompt : '是否被锁',url : 'queryUserLockFlagDropList.do'"
							class="easyui-combobox" style="width: 100%; height: 32px;"></td>
					</tr>
					<tr>
						<td width="20%">排序号:</td>
						<td><input id="userSortTextBox" name="USER_SORT" type="text"
							class="easyui-numberbox" style="width: 100%; height: 32px;"
							data-options="min:0,max:9999,precision:0,prompt:'排序号',required:true,validType:'length[0,4]'" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="south" border="false"
			style="text-align: right; height: 30px">
			<a class="easyui-linkbutton" iconCls="icon-ok"
				href="javascript:void(0)" onclick="saveUser()">保存</a> <a
				class="easyui-linkbutton" iconCls="icon-cancel"
				href="javascript:void(0)" onclick="closeEditUIForUser()">关闭</a>
		</div>
	</div>
</body>
</html>