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
	$(document).ready(function() {
		closeCache();
		initUnSelectedMenuTree();
		initSelectedMenuTree();
		initPost();
	});

	function initPost() {
		var rowIndex = getTextBoxValue('rowIndex');
		selectedPost = parent.$('#datagridForPost').datagrid('getData').rows[rowIndex];
	}

	//初始化待选择菜单权限树
	function initUnSelectedMenuTree() {
		$('#treeOfUnSelectedMenus').tree({
			url : 'queryUnSelectedMenusForTree.do',
			checkbox : true,
			checkOnSelect : true,
			onClick : function(node) {
				if (node.checked == true) {
					$(this).tree('uncheck', node.target);
				} else {
					$(this).tree('check', node.target);
				}
			},
			onLoadError : function(arguments) {
				errorFunctionForQuery();
			}
		});
	}

	//初始化已选择菜单权限树
	function initSelectedMenuTree() {
		$('#treeOfSelectedMenus').tree({
			checkbox : true,
			checkOnSelect : true,
			onClick : function(node) {
				if (node.checked == true) {
					$(this).tree('uncheck', node.target);
				} else {
					$(this).tree('check', node.target);
				}
			},
			onLoadError : function(arguments) {
				errorFunctionForQuery();
			}
		});
	}

	var selectedPost = null;

	//查询已选菜单权限
	function querySelectedMenusForTree(postId) {
		var params = {
			POST_ID : postId,
		};
		query(params, 'querySelectedMenusForTree.do',
				successFunctionForQuerySelectedMenus);
	}

	//成功查询已选菜单权限
	function successFunctionForQuerySelectedMenus(result, haveTree) {
		$('#treeOfSelectedMenus').tree('loadData', result);
		unCheckALL('treeOfUnSelectedMenus');
		unCheckALL('treeOfSelectedMenus');
	}

	//全部不选
	function unCheckALL(treeId) {
		var tree = eval('$(\'#' + treeId + '\')');
		var roots = tree.tree('getRoots');
		for (var i = 0; i < roots.length; i++) {
			var node = tree.tree('find', roots[i].id);
			tree.tree('uncheck', node.target);
		}
	}

	//为职位配置菜单权限
	function addMenusToPost() {
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
			save(params, "addMenusToPost.do", successFunctionForAddMenusToPost);
		}
	}

	//成功配置职位菜单权限
	function successFunctionForAddMenusToPost() {
		querySelectedMenusForTree(selectedPost.POST_ID);
	}

	//删除
	function delMenusToPost() {
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
			save(params, "delMenusToPost.do", successFunctionForDelMenusToPost);
		}
	}

	//成功删除职位菜单权限
	function successFunctionForDelMenusToPost() {
		successFunctionForAddMenusToPost();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div region="west" title="待选菜单权限" collapsible="false"
			style="width: 45%; height: 100%;">
			<div style="display: none">
				<input id="rowIndex" value="<%=request.getParameter("rowIndex")%>"
					class="easyui-textbox" />
			</div>
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
</body>
</html>