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

	//查询岗位
	function queryPostForPage(deptIds) {
		var params = {
			DEPT_IDS : deptIds,
			page : 1,
			rows : $('#datagridOfPost').datagrid('getPager').data("pagination").options.pageSize
		};
		ajaxFunction(params, 'queryPostPage.do', successFunctionForQueryPost,
				errorFunctionForQuery, false);
	}

	//岗位查询成功
	function successFunctionForQueryPost(result) {
		$('#datagridOfPost').datagrid('loadData', result);
	}

	//查询待选人员
	function queryUnSelectedUsersForPage(postId) {
		var params = {
			POST_ID : postId,
			page : 1,
			rows : $('#datagridOfUnSelectedUsers').datagrid('getPager').data(
					"pagination").options.pageSize
		};
		ajaxFunction(params, 'queryUnSelectedUsersForPage.do',
				successFunctionForQueryUnSelectedUsers, errorFunctionForQuery,
				false);
	}

	//成功查询待选人员
	function successFunctionForQueryUnSelectedUsers(result, haveTree) {
		$('#datagridOfUnSelectedUsers').datagrid('loadData', result);
	}

	//查询已选人员
	function querySelectedUsersForPage(postId) {
		var params = {
			POST_ID : postId,
			page : 1,
			rows : $('#datagridOfSelectedUsers').datagrid('getPager').data(
					"pagination").options.pageSize
		};
		ajaxFunction(params, 'querySelectedUsersForPage.do',
				successFunctionForQuerySelectedUsers, errorFunctionForQuery,
				false);
	}

	//成功查询已选人员
	function successFunctionForQuerySelectedUsers(result, haveTree) {
		$('#datagridOfSelectedUsers').datagrid('loadData', result);
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initDataGridOfPost();
		initDeptTree();
		initDataGridOfSelectedUsers();
		initDataGridOfUnSelectedUsers();
	});

	//初始化树
	function initDeptTree() {
		$('#deptTree').tree({
			url : 'queryDeptTree.do',
			onClick : function(node) {
				queryPostForPage(node.dept_inner_code); // 在用户点击的时候提示
			},
			onLoadError : function(arguments) {
				eval(errorCodeForQuery);
			}
		});
	}

	//初始化岗位列表
	function initDataGridOfUnSelectedUsers() {
		$('#datagridOfUnSelectedUsers').datagrid({
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
				title : '所属部门',
				width : 250
			} ] ]
		});
	}

	//初始化岗位列表
	function initDataGridOfSelectedUsers() {
		$('#datagridOfSelectedUsers').datagrid({
			idField : 'USER_ID',
			columns : [ [ {
				field : 'ck',
				title : '操作',
				checkbox : true,
			}, {
				field : 'USER_NAME',
				title : '用户名称',
				width : 250
			}, {
				field : 'USER_DEPT_NAME',
				title : '所属部门',
				width : 250
			} ] ]
		});
	}

	//初始化岗位列表
	function initDataGridOfPost() {
		$('#datagridOfPost').datagrid({
			idField : 'POST_ID',
			columns : [ [ {
				field : 'ck',
				title : '操作',
				checkbox : true,
			}, {
				field : 'POST_NAME',
				title : '岗位名称',
				width : 250
			} ] ],
			onSelect : function(rowIndex, rowData) {
				querySelectedUsersForPage(rowData.POST_ID);
				queryUnSelectedUsersForPage(rowData.POST_ID);
			}
		});
	}
</script>
</head>
<body>
	<!-- 列表页面 -->
	<div class="easyui-layout" data-options="fit:true">
		<div region="west" collapsible="false" style="width: 300px;">
			<div class="easyui-layout" data-options="fit:true">
				<div region="north" title="第一步：选择部门" collapsible="false"
					style="width: 300px; height: 40%;">
					<ul id="deptTree" class="easyui-tree" method="get" animate="true"
						lines="true"></ul>
				</div>
				<div region="south" collapsible="false" title="第二步：选择岗位"
					style="width: 300px; height: 50%;">
					<table id="datagridOfPost" class="easyui-datagrid"
						singleSelect="true" pagination="true" pageSize="30" pageNumber="1"
						method="get" fit="true">
					</table>
				</div>
			</div>

		</div>
		<div region="center">
			<div class="easyui-panel" data-options="fit:true" title="第三步：选择相关人员">
				<div class="easyui-layout" data-options="fit:true">
					<div region="west" title="待选人员" collapsible="false"
						style="width: 45%; height: 100%;">
						<table id="datagridOfUnSelectedUsers" class="easyui-datagrid"
							pagination="true" pageSize="30" pageNumber="1" method="get"
							fit="true">
						</table>
					</div>
					<div region="center" style="width: 10%; height: 100%;">
						<a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-arrow-right',size:'large',iconAlign:'top'"
							style="width: 100%; height: 50%;" onclick="refresh()">选择</a> <a
							href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-arrow-left',size:'large',iconAlign:'top'"
							style="width: 100%; height: 50%;" onclick="refresh()">取消选择</a>
					</div>
					<div region="east" collapsible="false" title="已选人员"
						style="width: 45%; height: 100%;">
						<table id="datagridOfSelectedUsers" class="easyui-datagrid"
							checkOnSelect="true" pagination="true" pageSize="30"
							pageNumber="1" method="get" fit="true">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>