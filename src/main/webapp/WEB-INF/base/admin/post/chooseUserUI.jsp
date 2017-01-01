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
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/base.css">
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/base.js"></script>
<script type="text/javascript">
	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				initDataGridOfSelectedUsers();
				initDataGridOfUnSelectedUsers();
				registerKeyPressForTextInput(
						'keyWordForSelectedUsersTextInput',
						querySelectedUsersForPage);
				registerKeyPressForTextInput(
						'keyWordForUnSelectedUsersTextInput',
						queryUnSelectedUsersForPage);
				initPost();
				queryUnSelectedUsersForPage();
				querySelectedUsersForPage();
			});

	function initPost() {
		var rowIndex = getTextBoxValue('rowIndex');
		selectedPost = parent.$('#datagridForPost').datagrid('getData').rows[rowIndex];
	}

	//初始化岗位列表
	function initDataGridOfUnSelectedUsers() {
		$('#datagridOfUnSelectedUsers')
				.datagrid(
						{
							idField : 'USER_ID',
							columns : [ [ {
								field : 'ck',
								title : '操作',
								checkbox : true,
							}, {
								field : 'USER_NAME',
								title : '用户名称',
								width : 100
							}, {
								field : 'USER_DEPT_NAME',
								title : '所属岗位',
								width : 250
							} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForUnSelectedUsersTextInput');
							}
						});
	}

	//初始化岗位列表
	function initDataGridOfSelectedUsers() {
		$('#datagridOfSelectedUsers')
				.datagrid(
						{
							idField : 'USER_ID',
							columns : [ [ {
								field : 'ck',
								title : '操作',
								checkbox : true,
							}, {
								field : 'USER_NAME',
								title : '用户名称',
								width : 100
							}, {
								field : 'USER_DEPT_NAME',
								title : '所属岗位',
								width : 250
							} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForSelectedUsersTextInput');
							}
						});
	}

	//查询待选人员
	function queryUnSelectedUsersForPage(postId) {
		if (postId == '' || postId == null) {
			postId = selectedPost.POST_ID;
		}
		var params = {
			KEY_WORD : getTextBoxValue('keyWordForUnSelectedUsersTextInput'),
			POST_ID : postId,
			page : 1,
			rows : getPageSizeOfDataGrid('datagridOfUnSelectedUsers')
		};
		query(params, 'queryUnSelectedUsersForPage.do',
				successFunctionForQueryUnSelectedUsers);
	}

	//成功查询待选人员
	function successFunctionForQueryUnSelectedUsers(result) {
		dataGridLoadData('datagridOfUnSelectedUsers', result);
	}

	//查询已选人员
	function querySelectedUsersForPage(postId) {
		if (postId == '' || postId == null) {
			postId = selectedPost.POST_ID;
		}
		var params = {
			KEY_WORD : getTextBoxValue('keyWordForSelectedUsersTextInput'),
			POST_ID : postId,
			page : 1,
			rows : getPageSizeOfDataGrid('datagridOfSelectedUsers')
		};
		save(params, 'querySelectedUsersForPage.do',
				successFunctionForQuerySelectedUsers);
	}

	//成功查询已选人员
	function successFunctionForQuerySelectedUsers(result) {
		dataGridLoadData('datagridOfSelectedUsers', result);
	}

	var selectedPost = null;

	//为职位配置人员
	function addUsersToPost() {
		var rowDatas = $('#datagridOfUnSelectedUsers')
				.datagrid('getSelections');
		if (rowDatas.length == 0) {
			alert('请选择待选人员');
		} else {
			var ids = '';
			for (var i = 0; i < rowDatas.length; i++) {
				var item = rowDatas[i];
				if (item.USER_ID != 'undefined') {
					ids += item.USER_ID + ',';
				}
			}
			ids = ids.substring(0, ids.length - 1);
			var params = {
				USER_IDS : ids,
				POST_ID : selectedPost.POST_ID
			};
			save(params, "addUsersToPost.do", successFunctionForAddUsersToPost);
		}
	}

	//成功配置职位人员
	function successFunctionForAddUsersToPost() {
		querySelectedUsersForPage(selectedPost.POST_ID);
		queryUnSelectedUsersForPage(selectedPost.POST_ID);
	}

	//删除
	function delUsersToPost() {
		var rowDatas = $('#datagridOfSelectedUsers').datagrid('getSelections');
		if (rowDatas.length == 0) {
			alert('请选择已选人员');
		} else {
			var ids = '';
			for (var i = 0; i < rowDatas.length; i++) {
				var item = rowDatas[i];
				if (item.USER_ID != 'undefined') {
					ids += item.USER_ID + ',';
				}
			}
			ids = ids.substring(0, ids.length - 1);
			var params = {
				USER_IDS : ids,
				POST_ID : selectedPost.POST_ID
			};
			save(params, "delUsersToPost.do", successFunctionForDelUsersToPost);
			rowDatas = {};
		}
	}

	//成功删除职位人员
	function successFunctionForDelUsersToPost() {
		successFunctionForAddUsersToPost();
	}
</script>
</head>
<body>
	<div class="easyui-layout"
		data-options="fit:true,border:false">
		<div region="west" title="待选人员" collapsible="false"
			style="width: 45%; height: 100%;">
			<div style="display: none">
				<input id="rowIndex"
					value="<%=request.getParameter("rowIndex")%>"
					class="easyui-textbox" />
			</div>
			<table id="datagridOfUnSelectedUsers"
				class="easyui-datagrid"
				toolbar="#toolbarForUnSelectedUsers" pagination="true"
				pageSize="30" pageNumber="1" method="get" fit="true">
			</table>
			<div id="toolbarForUnSelectedUsers">
				<div>
					<input id="keyWordForUnSelectedUsersTextInput"
						class="easyui-textbox"
						data-options="prompt:'待选人员名称|岗位名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryUnSelectedUsersForPage('')">查询</a>
				</div>
			</div>
		</div>
		<div region="center" style="width: 10%; height: 100%;">
			<a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-arrow-right',size:'large',iconAlign:'top'"
				style="width: 100%; height: 50%;"
				onclick="addUsersToPost()">选择</a> <a href="#"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-arrow-left',size:'large',iconAlign:'top'"
				style="width: 100%; height: 50%;"
				onclick="delUsersToPost()">取消选择</a>
		</div>
		<div region="east" collapsible="false" title="已选人员"
			style="width: 45%; height: 100%;">
			<table id="datagridOfSelectedUsers"
				class="easyui-datagrid"
				toolbar="#toolbarForSelectedUsers" checkOnSelect="true"
				pagination="true" pageSize="30" pageNumber="1"
				method="get" fit="true">
			</table>
			<div id="toolbarForSelectedUsers">
				<div>
					<input id="keyWordForSelectedUsersTextInput"
						class="easyui-textbox"
						data-options="prompt:'已选人员名称|岗位名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="querySelectedUsersForPage('')">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>