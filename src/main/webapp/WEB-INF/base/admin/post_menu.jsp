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
	//为职位配置菜单权限
	function addMenusToPost() {
		var selectedPost = $('#datagridOfPost').datagrid('getSelected');
		if (selectedPost == null) {
			alert("请选择岗位");
		} else {
			var rowDatas = $('#treeOfUnSelectedMenus').tree('getChecked');
			if (rowDatas.length == 0) {
				alert('请选择待选菜单权限');
			} else {
				var ids = '';
				for (var i = 0; i < rowDatas.length; i++) {
					var item = rowDatas[i];
					if (item.view_menu_up_inner_code != 'undefined') {
						ids += item.view_menu_up_inner_code + ',';
					}
				}
				ids = ids.substring(0, ids.length - 1);
				var params = {
					MENU_IDS : ids,
					POST_ID : selectedPost.POST_ID
				};
				save2(params, "addMenusToPost.do",
						successFunctionForAddMenusToPost,
						errorFunctionForOption, false);
			}
		}
	}

	//成功配置职位菜单权限
	function successFunctionForAddMenusToPost() {
		var selectedPost = $('#datagridOfPost').datagrid('getSelected');
		querySelectedMenusForTree(selectedPost.POST_ID);
	}

	//删除
	function delMenusToPost() {
		var selectedPost = $('#datagridOfPost').datagrid('getSelected');
		if (selectedPost == null) {
			alert("请选择岗位");
		} else {
			var rowDatas = $('#treeOfSelectedMenus').tree('getChecked');
			if (rowDatas.length == 0) {
				alert('请选择已选菜单权限');
			} else {
				var ids = '';
				for (var i = 0; i < rowDatas.length; i++) {
					var item = rowDatas[i];
					if (item.id != 'undefined') {
						ids += item.id + ',';
					}
				}
				ids = ids.substring(0, ids.length - 1);
				var params = {
					MENU_IDS : ids,
					POST_ID : selectedPost.POST_ID
				};
				save2(params, "delMenusToPost.do",
						successFunctionForDelMenusToPost,
						errorFunctionForOption, false);
			}
		}
	}

	//成功删除职位菜单权限
	function successFunctionForDelMenusToPost() {
		successFunctionForAddMenusToPost();
	}

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

	//查询已选菜单权限
	function querySelectedMenusForTree(postId) {
		var params = {
			POST_ID : postId,
		};
		ajaxFunction(params, 'querySelectedMenusForTree.do',
				successFunctionForQuerySelectedMenus, errorFunctionForQuery,
				false);
	}

	//成功查询已选菜单权限
	function successFunctionForQuerySelectedMenus(result, haveTree) {
		$('#treeOfSelectedMenus').tree('loadData', result);
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initDataGridOfPost();
		initDeptTree();
		initUnSelectedMenuTree();
		initSelectedMenuTree();
	});

	//初始化部门树
	function initDeptTree() {
		$('#deptTree').tree({
			url : 'queryDeptTree.do',
			onClick : function(node) {
				queryPostForPage(node.dept_inner_code); // 在用户点击的时候提示
				$('#treeOfSelectedMenus').tree('loadData', {
					total : 0,
					rows : []
				});
			},
			onLoadError : function(arguments) {
				eval(errorCodeForQuery);
			}
		});
	}

	//初始化待选择菜单权限树
	function initUnSelectedMenuTree() {
		$('#treeOfUnSelectedMenus').tree(
				{
					url : 'queryUnSelectedMenusForTree.do',
					checkbox : true,
					checkOnSelect : true,
					onClick : function(node) {
						var selectedPost = $('#datagridOfPost').datagrid(
								'getSelected');
						if (selectedPost == null) {
							alert('请选择岗位');
						}
						if (node.checked == true) {
							$(this).tree('uncheck', node.target);
						} else {
							$(this).tree('check', node.target);
						}
					},
					onLoadError : function(arguments) {
						eval(errorCodeForQuery);
					}
				});
	}

	//初始化已选择菜单权限树
	function initSelectedMenuTree() {
		$('#treeOfSelectedMenus').tree(
				{
					checkbox : true,
					checkOnSelect : true,
					onClick : function(node) {
						var selectedPost = $('#datagridOfPost').datagrid(
								'getSelected');
						if (selectedPost == null) {
							alert('请选择岗位');
						}
						if (node.checked == true) {
							$(this).tree('uncheck', node.target);
						} else {
							$(this).tree('check', node.target);
						}
					},
					onLoadError : function(arguments) {
						eval(errorCodeForQuery);
					}
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
				querySelectedMenusForTree(rowData.POST_ID);
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
			<div class="easyui-panel" data-options="fit:true"
				title="第三步：选择相关菜单权限">
				<div class="easyui-layout" data-options="fit:true">
					<div region="west" title="待选菜单权限" collapsible="false"
						style="width: 45%; height: 100%;">
						<ul id="treeOfUnSelectedMenus" class="easyui-tree" method="get"
							animate="true" lines="true"></ul>
					</div>
					<div region="center" style="width: 10%; height: 100%;">
						<a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-arrow-right',size:'large',iconAlign:'top'"
							style="width: 100%; height: 50%;" onclick="addMenusToPost()">选择</a>
						<a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-arrow-left',size:'large',iconAlign:'top'"
							style="width: 100%; height: 50%;" onclick="delMenusToPost()">取消选择</a>
					</div>
					<div region="east" collapsible="false" title="已选菜单权限"
						style="width: 45%; height: 100%;">
						<ul id="treeOfSelectedMenus" class="easyui-tree" method="get"
							animate="true" lines="true"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>