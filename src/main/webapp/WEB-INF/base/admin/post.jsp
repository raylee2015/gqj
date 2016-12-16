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

	//打开编辑窗口
	function openAddUI() {
		openAddDataUI2(true);
		setTextBoxValue('postIdTextBox', -1);
		url = 'addNewPost.do';
	}

	//打开编辑窗口
	function openEditUI(opType, rowIndex) {
		var rowData = $('#datagrid').datagrid('getData').rows[rowIndex];
		openEditDataUI2(rowData, true, 'POST_DEPT_ID', 'POST_DEPT_NAME');
		url = 'updatePost.do';
	}

	//删除
	function delPosts() {
		var ids = getIdsOfSelectedItems('POST_ID', '数据出现问题，请联系系统管理员');
		var params = {
			POST_IDS : ids
		};
		del(params, "请选择岗位", '是否删除所选岗位?', 'delPosts.do', true);
	}

	// 保存数据
	function savePost() {
		var params = {
			POST_ID : getTextBoxValue('postIdTextBox'),
			POST_NAME : getTextBoxValue('postNameTextBox'),
			POST_DESP : getTextBoxValue('postDespTextBox'),
			POST_SORT : getTextBoxValue('postSortTextBox'),
			DEPT_ID : getComboTreeValue('comboTree')
		};
		save1(params, url, true);
	}

	//查询
	function queryForPage(deptIds) {
		var params = {
			DEPT_IDS : deptIds,
			keyWord : $('#keyWordTextInput').textbox('getValue'),
			page : 1,
			rows : $('#datagrid').datagrid('getPager').data("pagination").options.pageSize
		};
		query(params, 'queryPostPage.do');
	}

	//页面加载完
	$(document).ready(function() {
		initDocument();
		initDataGrid();
		initTree();
		initDataGridOfSelectedUsers();
		initDataGridOfUnSelectedUsers();
		initUnSelectedMenuTree();
		initSelectedMenuTree();
	});

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
				width : 100
			}, {
				field : 'USER_DEPT_NAME',
				title : '所属部门',
				width : 250
			} ] ]
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

	//初始化树
	function initTree() {
		$('#tree').tree({
			url : 'queryDeptTree.do',
			onClick : function(node) {
				queryForPage(node.dept_inner_code); // 在用户点击的时候提示
			},
			onLoadError : function(arguments) {
				eval(errorCodeForQuery);
			}
		});
	}

	//初始化列表元素
	function initDataGrid() {
		$('#datagrid').datagrid({
			url : 'queryPostPage.do',
			idField : 'POST_ID',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'op',
				title : '操作',
				formatter : editColumnFormatter
			}, {
				field : 'CHOOSE_USER',
				title : '选择相关人员',
				width : 120,
				formatter : chooseUserColumnFormatter
			}, {
				field : 'CHOOSE_MENU',
				title : '选择相关菜单权限',
				width : 140,
				formatter : chooseMenuColumnFormatter
			}, {
				field : 'POST_NAME',
				title : '岗位名称',
				width : 100,
			}, {
				field : 'POST_DESP',
				title : '岗位职能描述',
				width : 300,
			}, {
				field : 'POST_DEPT_NAME',
				title : '所属部门',
				width : 100,
			}, {
				field : 'POST_SORT',
				title : '排序号',
				width : 100,
			} ] ],
			onLoadError : function() {
				eval(errorCodeForQuery);
			}
		});
	}

	// 重写选人列，使得选人栏显示选人连接
	function chooseUserColumnFormatter(fieldValue, rowData, rowIndex) {
		var btn = '<a class="easyui-linkbutton" onclick="openChooseUserUI(\'edit\',\''
				+ rowIndex + '\')" href="javascript:void(0)">选择相关人员</a>';
		return btn;
	}

	// 重写选菜单列，使得选菜单栏显示选人连接
	function chooseMenuColumnFormatter(fieldValue, rowData, rowIndex) {
		var btn = '<a class="easyui-linkbutton" onclick="openChooseMenuUI(\'edit\',\''
				+ rowIndex + '\')" href="javascript:void(0)">选择相关菜单权限</a>';
		return btn;
	}

	// 打开选择人窗口
	function openChooseUserUI(opType, rowIndex) {
		var rowData = $('#datagrid').datagrid('getData').rows[rowIndex];
		var postId = rowData.POST_ID;
		selectedPost=rowData;
		querySelectedUsersForPage(postId);
		queryUnSelectedUsersForPage(postId);
		$('#chooseUserUI').window('open');
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
		$('#datagridOfUnSelectedUsers').datagrid('uncheckAll');
		$('#datagridOfUnSelectedUsers').datagrid('unselectAll');
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
		$('#datagridOfSelectedUsers').datagrid('uncheckAll');
		$('#datagridOfSelectedUsers').datagrid('unselectAll');
	}

	var selectedPost = null;

	// 打开选择菜单窗口
	function openChooseMenuUI(opType, rowIndex) {
		var rowData = $('#datagrid').datagrid('getData').rows[rowIndex];
		selectedPost = rowData;
		var postId = rowData.POST_ID;
		querySelectedMenusForTree(postId);
		$('#chooseMenuUI').window('open');
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
			save2(params, "addMenusToPost.do",
					successFunctionForAddMenusToPost, errorFunctionForOption,
					false);
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
			save2(params, "delMenusToPost.do",
					successFunctionForDelMenusToPost, errorFunctionForOption,
					false);
		}
	}

	//成功删除职位菜单权限
	function successFunctionForDelMenusToPost() {
		successFunctionForAddMenusToPost();
	}

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
			save2(params, "addUsersToPost.do",
					successFunctionForAddUsersToPost, errorFunctionForOption,
					false);
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
			save2(params, "delUsersToPost.do",
					successFunctionForDelUsersToPost, errorFunctionForOption,
					false);
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
	<!-- 列表页面 -->
	<div class="easyui-layout" data-options="fit:true">
		<div region="west" collapsible="false" style="width: 200px;">
			<ul id="tree" class="easyui-tree" method="get" animate="true"
				lines="true"></ul>
		</div>
		<div region="center">
			<table id="datagrid" class="easyui-datagrid" rownumbers="true"
				toolbar="#toolbar" pagination="true" pageSize="30" pageNumber="1"
				checkOnSelect="false" method="get" fit="true">
			</table>
			<div id="toolbar">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refresh()">刷新</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="openAddUI()">添加</a> <a href="#" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="delPosts()">删除</a>
				</div>
				<div>
					<input id="keyWordTextInput" class="easyui-textbox"
						data-options="prompt:'岗位名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryForPage('')">查询</a>
				</div>

			</div>
		</div>
	</div>

	<!--  详细界面 -->
	<div id="editUI" class="easyui-window" title="添加岗位" closed="true"
		data-options="iconCls:'icon-save'"
		style="width: 450px; height: 250px; padding: 5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" fit="true" border="false">
				<form id="form" method="post" style="width: 100%;">
					<div style="display: none">
						<input id="postIdTextBox" name="POST_ID" class="easyui-textbox" />
					</div>
					<table width="100%">
						<tr>
							<td width="25%">上级岗位:</td>
							<td><input id="comboTree" name="POST_DEPT_ID"
								class="easyui-combotree" data-options="required:true"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="25%">岗位名称:</td>
							<td><input id="postNameTextBox" name="POST_NAME"
								class="easyui-textbox"
								data-options="prompt:'岗位名称',required:true,validType:'length[0,10]'"
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
					href="javascript:void(0)" onclick="closeEditUI()">关闭</a>
			</div>
		</div>
	</div>

	<!--  选择相关人员界面 -->
	<div id="chooseUserUI" class="easyui-window" title="选择相关人员"
		closed="true" style="width: 1000px; height: 600px;">
		<div class="easyui-layout" data-options="fit:true,border:false">
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
					style="width: 100%; height: 50%;" onclick="addUsersToPost()">选择</a>
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-arrow-left',size:'large',iconAlign:'top'"
					style="width: 100%; height: 50%;" onclick="delUsersToPost()">取消选择</a>
			</div>
			<div region="east" collapsible="false" title="已选人员"
				style="width: 45%; height: 100%;">
				<table id="datagridOfSelectedUsers" class="easyui-datagrid"
					checkOnSelect="true" pagination="true" pageSize="30" pageNumber="1"
					method="get" fit="true">
				</table>
			</div>
		</div>
	</div>

	<!--  选择相关菜单权限界面 -->
	<div id="chooseMenuUI" class="easyui-window" title="选择相关菜单权限"
		closed="true" style="width: 1000px; height: 600px;">
		<div class="easyui-layout" data-options="fit:true,border:false">
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
</body>
</html>