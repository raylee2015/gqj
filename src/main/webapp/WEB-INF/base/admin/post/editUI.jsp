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
	//记录新增或者修改的方法
	var url;

	// 保存数据
	function savePost() {
		var params = {
			POST_ID : getTextBoxValue('postIdTextBox'),
			POST_NAME : getTextBoxValue('postNameTextBox'),
			POST_DESP : getTextBoxValue('postDespTextBox'),
			POST_SORT : getTextBoxValue('postSortTextBox'),
			DEPT_ID : getComboTreeValue('deptTree')
		};
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		parent.successFunctionForOption(result);
		closeEditUIForPost();
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initPostForm();
		initDeptTree();
	});

	//初始化表单
	function initPostForm() {
		var opType = getTextBoxValue('opType');
		if (opType == 'add') {
			url = 'addNewPost.do';
		} else if (opType == 'edit') {
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = getRowDataOfParentDataGrid('datagridForPost',
					rowIndex);
			$('#postForm').form('load', rowData);
			url = 'updatePost.do';
		}
	}

	//初始化树
	function initDeptTree() {
		var treeNodes = parent.$('#deptTree').tree('getRoots');
		$('#deptTree').combotree('loadData', treeNodes);
	}

	//关闭编辑窗口
	function closeEditUIForPost() {
		parent.closeEditUIForPost();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<form id="postForm" method="post" style="width: 100%;">
				<div style="display: none">
					<input id="postIdTextBox" name="POST_ID" class="easyui-textbox" />
					<input id="opType" value="<%=request.getParameter("opType")%>"
						class="easyui-textbox" /> <input id="rowIndex"
						value="<%=request.getParameter("rowIndex")%>"
						class="easyui-textbox" />
				</div>
				<table style="width: 100%; padding: 10px">
					<tr>
						<td width="25%">上级岗位:</td>
						<td><input id="deptTree" name="POST_DEPT_ID"
							class="easyui-combotree" data-options="required:true"
							style="width: 100%; height: 32px"></td>
					</tr>
					<tr>
						<td width="25%">岗位名称:</td>
						<td><input id="postNameTextBox" name="POST_NAME"
							class="easyui-textbox"
							data-options="prompt:'岗位名称',required:true,validType:'length[0,20]'"
							style="width: 100%; height: 32px" /></td>
					</tr>
					<tr>
						<td width="25%">岗位职能描述:</td>
						<td><input id="postDespTextBox" name="POST_DESP"
							class="easyui-textbox"
							data-options="prompt:'岗位职能描述',required:true,validType:'length[0,250]'"
							style="width: 100%; height: 60px" /></td>
					</tr>
					<tr>
						<td width="25%">排序号:</td>
						<td><input id="postSortTextBox" name="POST_SORT" type="text"
							class="easyui-numberbox" style="width: 100%; height: 32px"
							data-options="min:0,max:99,precision:0,prompt:'排序号',required:true,validType:'length[0,2]'" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div region="south" border="false"
			style="text-align: right; height: 30px">
			<a class="easyui-linkbutton" iconCls="icon-ok"
				href="javascript:void(0)" onclick="savePost()">保存</a> <a
				class="easyui-linkbutton" iconCls="icon-cancel"
				href="javascript:void(0)" onclick="closeEditUIForPost()">关闭</a>
		</div>
	</div>
</body>
</html>