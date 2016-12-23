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
	function saveDept() {
		var params = {
			DEPT_ID : getTextBoxValue('deptIdTextBox'),
			DEPT_NAME : getTextBoxValue('deptNameTextBox'),
			DEPT_SORT : getTextBoxValue('deptSortTextBox'),
			UP_DEPT_ID : getComboTreeValue('deptTree')
		};
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		parent.successFunctionForOption(result);
		closeEditUIForDept();
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initDeptForm();
		initDeptTree();
	});

	//初始化表单
	function initDeptForm() {
		var opType = getTextBoxValue('opType');
		if (opType == 'add') {
			url = 'addNewDept.do';
		} else if (opType == 'edit') {
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = getRowDataOfParentDataGrid('datagridForDept',
					rowIndex);
			$('#deptForm').form('load', rowData);
			url = 'updateDept.do';
		}
	}

	//初始化树
	function initDeptTree() {
		var treeNodes = parent.$('#deptTree').tree('getRoots');
		$('#deptTree').combotree('loadData', treeNodes);
	}

	//关闭编辑窗口
	function closeEditUIForDept() {
		parent.closeEditUIForDept();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<form id="deptForm" method="post" style="width: 100%;">
				<div style="display: none">
					<input id="deptIdTextBox" name="DEPT_ID" class="easyui-textbox" /><input
						id="opType" value="<%=request.getParameter("opType")%>"
						class="easyui-textbox" /> <input id="rowIndex"
						value="<%=request.getParameter("rowIndex")%>"
						class="easyui-textbox" />
				</div>
				<table style="width: 100%; padding: 10px">
					<tr>
						<td width="22%">上级部门:</td>
						<td><input id="deptTree" name="UP_DEPT_ID"
							class="easyui-combotree" data-options="required:true"
							style="width: 100%; height: 32px"></td>
					</tr>
					<tr>
						<td width="22%">部门名称:</td>
						<td><input id="deptNameTextBox" name="DEPT_NAME"
							class="easyui-textbox"
							data-options="prompt:'部门名称',required:true,validType:'length[0,25]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
					<tr>
						<td width="22%">排序号:</td>
						<td><input id="deptSortTextBox" name="DEPT_SORT" type="text"
							class="easyui-numberbox" style="width: 100%; height: 32px"
							data-options="min:0,max:99,precision:0,prompt:'排序号',required:true,validType:'length[0,2]'" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="south" border="false"
			style="text-align: right; height: 30px">
			<a class="easyui-linkbutton" iconCls="icon-ok"
				href="javascript:void(0)" onclick="saveDept()">保存</a> <a
				class="easyui-linkbutton" iconCls="icon-cancel"
				href="javascript:void(0)" onclick="closeEditUIForDept()">关闭</a>
		</div>
	</div>
</body>
</html>