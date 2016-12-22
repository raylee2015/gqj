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
	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				initDataGridOfSelectedPosts();
				initDataGridOfUnSelectedPosts();
				registerKeyPressForTextInput(
						'keyWordForSelectedPostsTextInput',
						querySelectedPostsForPage);
				registerKeyPressForTextInput(
						'keyWordForUnSelectedPostsTextInput',
						queryUnSelectedPostsForPage);
				initPost();
				queryUnSelectedPostsForPage();
				querySelectedPostsForPage();
			});

	function initPost() {
		var rowIndex = getTextBoxValue('rowIndex');
		selectedUser = parent.$('#datagridForPost').datagrid('getData').rows[rowIndex];
	}

	//初始化岗位列表
	function initDataGridOfUnSelectedPosts() {
		$('#datagridOfUnSelectedPosts').datagrid({
			idField : 'POST_ID',
			columns : [ [ {
				field : 'ck',
				title : '操作',
				checkbox : true,
			}, {
				field : 'POST_NAME',
				title : '岗位名称',
				width : 100
			} ] ]
		});
	}

	//初始化岗位列表
	function initDataGridOfSelectedPosts() {
		$('#datagridOfSelectedPosts').datagrid({
			idField : 'POST_ID',
			columns : [ [ {
				field : 'ck',
				title : '操作',
				checkbox : true,
			}, {
				field : 'POST_NAME',
				title : '岗位名称',
				width : 100
			} ] ]
		});
	}

	//查询待选岗位
	function queryUnSelectedPostsForPage(userId) {
		if (userId == '' || userId == null) {
			userId = selectedUser.USER_ID;
		}
		var params = {
			KEY_WORD : getTextBoxValue('keyWordForUnSelectedPostsTextInput'),
			USER_ID : userId,
			page : 1,
			rows : getPageSizeOfDataGrid('datagridOfUnSelectedPosts')
		};
		query(params, 'queryUnSelectedPostsForPage.do',
				successFunctionForQueryUnSelectedPosts);
	}

	//成功查询待选岗位
	function successFunctionForQueryUnSelectedPosts(result) {
		dataGridLoadData('datagridOfUnSelectedPosts', result);
	}

	//查询已选岗位
	function querySelectedPostsForPage(userId) {
		if (userId == '' || userId == null) {
			userId = selectedUser.USER_ID;
		}
		var params = {
			KEY_WORD : getTextBoxValue('keyWordForSelectedPostsTextInput'),
			USER_ID : userId,
			page : 1,
			rows : getPageSizeOfDataGrid('datagridOfSelectedPosts')
		};
		save(params, 'querySelectedPostsForPage.do',
				successFunctionForQuerySelectedPosts);
	}

	//成功查询已选岗位
	function successFunctionForQuerySelectedPosts(result) {
		dataGridLoadData('datagridOfSelectedPosts', result);
	}

	var selectedUser = null;

	//为职位配置岗位
	function addPostsToUser() {
		var rowDatas = $('#datagridOfUnSelectedPosts')
				.datagrid('getSelections');
		if (rowDatas.length == 0) {
			alert('请选择待选岗位');
		} else {
			var ids = '';
			for (var i = 0; i < rowDatas.length; i++) {
				var item = rowDatas[i];
				if (item.POST_ID != 'undefined') {
					ids += item.POST_ID + ',';
				}
			}
			ids = ids.substring(0, ids.length - 1);
			var params = {
				POST_IDS : ids,
				USER_ID : selectedUser.USER_ID
			};
			save(params, "addPostsToPost.do", successFunctionForAddPostsToPost);
		}
	}

	//成功配置职位岗位
	function successFunctionForAddPostsToPost() {
		querySelectedPostsForPage(selectedUser.USER_ID);
		queryUnSelectedPostsForPage(selectedUser.USER_ID);
	}

	//删除
	function delPostsToUser() {
		var rowDatas = $('#datagridOfSelectedPosts').datagrid('getSelections');
		if (rowDatas.length == 0) {
			alert('请选择已选岗位');
		} else {
			var ids = '';
			for (var i = 0; i < rowDatas.length; i++) {
				var item = rowDatas[i];
				if (item.POST_ID != 'undefined') {
					ids += item.POST_ID + ',';
				}
			}
			ids = ids.substring(0, ids.length - 1);
			var params = {
				POST_IDS : ids,
				USER_ID : selectedUser.USER_ID
			};
			save(params, "delPostsToPost.do", successFunctionForDelPostsToPost);
			rowDatas = {};
		}
	}

	//成功删除职位岗位
	function successFunctionForDelPostsToPost() {
		successFunctionForAddPostsToPost();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div region="west" title="待选岗位" collapsible="false"
			style="width: 45%; height: 100%;">
			<div style="display: none">
				<input id="rowIndex" value="<%=request.getParameter("rowIndex")%>"
					class="easyui-textbox" />
			</div>
			<table id="datagridOfUnSelectedPosts" class="easyui-datagrid"
				toolbar="#toolbarForUnSelectedPosts" pagination="true" pageSize="30"
				pageNumber="1" method="get" fit="true">
			</table>
			<div id="toolbarForUnSelectedPosts">
				<div>
					<input id="keyWordForUnSelectedPostsTextInput"
						class="easyui-textbox"
						data-options="prompt:'待选岗位名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryUnSelectedPostsForPage('')">查询</a>
				</div>
			</div>
		</div>
		<div region="center" style="width: 10%; height: 100%;">
			<a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-arrow-right',size:'large',iconAlign:'top'"
				style="width: 100%; height: 50%;" onclick="addPostsToUser()">选择</a>
			<a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-arrow-left',size:'large',iconAlign:'top'"
				style="width: 100%; height: 50%;" onclick="delPostsToUser()">取消选择</a>
		</div>
		<div region="east" collapsible="false" title="已选岗位"
			style="width: 45%; height: 100%;">
			<table id="datagridOfSelectedPosts" class="easyui-datagrid"
				toolbar="#toolbarForSelectedPosts" checkOnSelect="true"
				pagination="true" pageSize="30" pageNumber="1" method="get"
				fit="true">
			</table>
			<div id="toolbarForSelectedPosts">
				<div>
					<input id="keyWordForSelectedPostsTextInput" class="easyui-textbox"
						data-options="prompt:'已选岗位名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="querySelectedPostsForPage('')">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>