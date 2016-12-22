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
	//关闭编辑窗口
	function closeEditUIForPost() {
		closeEditUI('editUIForPost')
	}

	//打开编辑窗口
	function openAddUIForPost() {
		createModalDialog("editUIForPost", "openEditUI.do?opType=add", "岗位设置",
				400, 260);
		openEditUI('editUIForPost');
	}

	//打开编辑窗口
	function openEditUIForPost(rowIndex) {
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForPost", url, "岗位设置", 400, 260);
		openEditUI('editUIForPost');
	}

	//关闭选人窗口
	function closeChooseUserUIForPost() {
		closeEditUI('chooseUserUIForPost')
	}

	//打开选人窗口
	function openChooseUserUIForPost(rowIndex) {
		var url = "openChooseUserUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("chooseUserUIForPost", url, "匹配岗位人员", 1000, 600);
		openEditUI('chooseUserUIForPost');
	}

	//关闭选菜单窗口
	function closeChooseMenuUIForPost() {
		closeEditUI('chooseMenuUIForPost')
	}

	//打开选菜单窗口
	function openChooseMenuUIForPost(rowIndex) {
		var url = "openChooseMenuUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("chooseMenuUIForPost", url, "匹配岗位菜单权限", 1000, 600);
		openEditUI('chooseMenuUIForPost');
	}

	//删除
	function delPosts() {
		if (checkSelectedItems('datagridForPost', '请选择岗位')) {
			var postIds = getIdsOfSelectedItems('datagridForPost', 'POST_ID');
			if (postIds != null && postIds != '') {
				var params = {
					POST_IDS : postIds
				};
				showMessageBox(params, 'delPosts.do', '是否删除所选岗位?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForPost');
	}

	//用在点击查询按钮的时候
	function queryPostPagesForSearch() {
		queryPosts('');
	}

	//用在点击树的时候
	function queryPostPagesForTree(deptIds) {
		queryPosts(deptIds);
	}

	//查询
	function queryPosts(deptIds) {
		var params = {
			DEPT_IDS : deptIds,
			keyWord : $('#keyWordForPostTextInput').textbox('getValue'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForPost')
		};
		query(params, 'queryPostPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForPost', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				initDataGridForPost();
				initDeptTree();
				registerKeyPressForTextInput('keyWordForPostTextInput',
						queryPostPagesForSearch);
			});

	//初始化树
	function initDeptTree() {
		$('#deptTree').tree({
			url : 'queryDeptTree.do',
			onClick : function(node) {
				queryPostPagesForTree(node.dept_inner_code); // 在用户点击的时候提示
			},
			onLoadError : function(arguments) {
				errorFunctionForQuery()
			}
		});
	}

	//初始化列表元素
	function initDataGridForPost() {
		$('#datagridForPost')
				.datagrid(
						{
							url : 'queryPostPage.do',
							idField : 'POST_ID',
							rownumbers : true,
							toolbar : '#toolbarForPost',
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
													+ ' onclick="openEditUIForPost(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									},
									{
										field : 'CHOOSE_USER',
										title : '选择相关人员',
										width : 120,
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = '<a class="easyui-linkbutton" '
													+ 'onclick="openChooseUserUIForPost(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">选择相关人员</a>';
											return btn;
										}
									},
									{
										field : 'CHOOSE_MENU',
										title : '选择相关菜单权限',
										width : 140,
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = '<a class="easyui-linkbutton" '
													+ 'onclick="openChooseMenuUIForPost(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">选择相关菜单权限</a>';
											return btn;
										}
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
										title : '所属岗位',
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
</script>
</head>
<body>
	<!-- 列表页面 -->
	<div class="easyui-layout" data-options="fit:true">
		<div region="west" collapsible="false" style="width: 200px;">
			<ul id="deptTree" class="easyui-tree" method="get" animate="true"
				lines="true"></ul>
		</div>
		<div region="center">
			<table id="datagridForPost" class="easyui-datagrid">
			</table>
			<div id="toolbarForPost">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refreshDataGrid('datagridForPost')">刷新</a> <a
						href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="openAddUIForPost()">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delPosts()">删除</a>
				</div>
				<div>
					<input id="keyWordForPostTextInput" class="easyui-textbox"
						data-options="prompt:'岗位名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryPostPagesForSearch()">查询</a>
				</div>

			</div>
		</div>
	</div>
</body>
</html>