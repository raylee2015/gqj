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
	//关闭选岗位窗口
	function closeChoosePostUIForUser() {
		closeEditUI('choosePostUIForUser')
	}

	//打开选岗位窗口
	function openChoosePostUIForUser(rowIndex) {
		var url = "openChoosePostUI.do?opType=edit&rowIndex=" + rowIndex;
		var rowData = getRowDataOfSelfDataGrid('datagridForUser', rowIndex);
		createModalDialog("choosePostUIForUser", url, ("设置\""
				+ rowData.USER_NAME + "\"的岗位"), 1000, 600);
		openEditUI('choosePostUIForUser');
	}

	//关闭编辑窗口
	function closeEditUIForUser() {
		closeEditUI('editUIForUser')
	}

	//打开编辑窗口
	function openAddUIForUser() {
		createModalDialog("editUIForUser", "openEditUI.do?opType=add", "添加用户",
				400, 350);
		openEditUI('editUIForUser');
	}

	//打开编辑窗口
	function openEditUIForUser(rowIndex) {
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		var rowData = getRowDataOfSelfDataGrid('datagridForUser', rowIndex);
		createModalDialog("editUIForUser", url,
				("修改\"" + rowData.USER_NAME + "\"的用户资料"), 400, 350);
		openEditUI('editUIForUser');
	}

	//删除用户
	function delUsers() {
		if (checkSelectedItems('datagridForUser', '请选择用户')) {
			var userIds = getIdsOfSelectedItems('datagridForUser', 'USER_ID');
			var params = {
				USER_IDS : userIds
			};
			showMessageBox(params, 'delUsers.do', '是否删除所选用户?',
					successFunctionForOption)
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForUser');
	}

	//初始化用户密码
	function initUserPassWord() {
		if (checkSelectedItems('datagridForUser', '请选择用户')) {
			var ids = getIdsOfSelectedItems('datagridForUser', 'USER_ID');
			var params = {
				USER_IDS : ids
			};
			showMessageBox(params, 'initUsersPassWord.do', '是否初始化所选用户密码?',
					successFunctionForOption);
		}
	}

	//用在点击查询按钮的时候
	function queryUserPagesForSearch() {
		queryUsers('');
	}

	//用在点击树的时候
	function queryUserPagesForTree(userDeptId) {
		queryUsers(userDeptId);
	}

	//查询用户
	function queryUsers(userDeptId) {
		var params = {
			USER_DEPT_ID : userDeptId,
			keyWord : getTextBoxValue('keyWordForUserTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForUser')
		};
		query(params, 'queryUsersPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForUser', result);
	}

	//页面加载完，执行初始化
	$(document).ready(
			function() {
				closeCache();
				initDataGridForUser();
				initDeptTree();
				registerKeyPressForTextInput('keyWordForUserTextInput',
						queryUserPagesForSearch);
			});

	//初始化部门树
	function initDeptTree() {
		$('#deptTree').tree({
			url : 'queryDeptTree.do',
			onClick : function(node) {
				queryUserPagesForTree(node.id); // 在用户点击的时候提示
			},
			onLoadError : function(arguments) {
				errorFunctionForQuery();
			}
		});
	}

	//初始化用户列表元素
	function initDataGridForUser() {
		$('#datagridForUser')
				.datagrid(
						{
							url : 'queryUsersPage.do',
							idField : 'USER_ID',
							rownumbers : true,
							toolbar : '#toolbarForUser',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : false,
							fit : true,
							method : 'get',
							columns : [ [
									{
										field : 'ck',
										checkbox : true
									},
									{
										field : 'op',
										title : '操作',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = '<a class="easyui-linkbutton" '
													+ ' onclick="openEditUIForUser(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									},
									{
										field : 'CHOOSE_POST',
										title : '选择岗位',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = '<a class="easyui-linkbutton" '
													+ ' onclick="openChoosePostUIForUser(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">选择岗位</a>';
											return btn;
										}
									}, {
										field : 'USER_NAME',
										title : '用户名称',
										width : 100,
									}, {
										field : 'USER_CODE',
										title : '用户编号',
										width : 100,
									}, {
										field : 'USER_DEPT_NAME',
										title : '所属部门',
										width : 100,
									}, {
										field : 'USER_PHONE',
										title : '手机号码',
										width : 100,
									}, {
										field : 'USER_LOCK_FLAG_NAME',
										title : '是否锁定',
										width : 100,
									}, {
										field : 'USER_USE_FLAG_NAME',
										title : '是否在用',
										width : 100,
									}, {
										field : 'USER_SORT',
										title : '排序号',
										width : 100,
									} ] ],
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}
</script>
</head>
<body>
	<!-- 列表页面 -->
	<div class="easyui-layout" data-options="fit:true">
		<div region="west" ,collapsible="false" style="width: 200px;">
			<ul id="deptTree" class="easyui-tree" method="get" animate="true"
				lines="true"></ul>
		</div>
		<div region="center">
			<table id="datagridForUser" class="easyui-datagrid">
			</table>
			<div id="toolbarForUser">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refreshDataGrid('datagridForUser')">刷新</a> <a
						href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="openAddUIForUser()">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delUsers()">删除</a><a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true" onclick="initUserPassWord()">初始化密码</a>
				</div>
				<div>
					<input id="keyWordForUserTextInput" class="easyui-textbox"
						data-options="prompt:'用户名称',validType:'length[0,10]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryUserPagesForSearch()">查询</a>
				</div>

			</div>
		</div>
	</div>
</body>
</html>