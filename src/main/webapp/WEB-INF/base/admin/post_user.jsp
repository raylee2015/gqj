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
	//为职位配置人员
	function addUsersToPost() {
		var selectedPost = $('#datagridOfPost').datagrid('getSelected');
		if (selectedPost == null) {
			alert("请选择岗位");
		} else {
			var rowDatas = $('#datagridOfUnSelectedUsers').datagrid(
					'getSelections');
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
						successFunctionForAddUsersToPost,
						errorFunctionForOption, false);
			}
		}
	}

	//成功配置职位人员
	function successFunctionForAddUsersToPost() {
		var selectedPost = $('#datagridOfPost').datagrid('getSelected');
		querySelectedUsersForPage(selectedPost.POST_ID);
		queryUnSelectedUsersForPage(selectedPost.POST_ID);
	}

	//删除
	function delUsersToPost() {
		var selectedPost = $('#datagridOfPost').datagrid('getSelected');
		if (selectedPost == null) {
			alert("请选择岗位");
		} else {
			var rowDatas = $('#datagridOfSelectedUsers').datagrid(
					'getSelections');
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
						successFunctionForDelUsersToPost,
						errorFunctionForOption, false);
				rowDatas = {};
			}
		}
	}

	//成功删除职位人员
	function successFunctionForDelUsersToPost() {
		successFunctionForAddUsersToPost();
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

	//查询待选人员
	function queryUnSelectedUsersForPage(postId) {
		if (postId == '') {
			var selectedPost = $('#datagridOfPost').datagrid('getSelected');
			if (selectedPost == null) {
				alert("请选择岗位");
				return;
			} else {
				postId = selectedPost.POST_ID;
			}
		}
		var params = {
			KEY_WORD : getTextBoxValue('keyWordForUnSelectedUsersTextInput'),
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
		if (postId == '') {
			var selectedPost = $('#datagridOfPost').datagrid('getSelected');
			if (selectedPost == null) {
				alert("请选择岗位");
				return;
			} else {
				postId = selectedPost.POST_ID;
			}
		}
		var params = {
			KEY_WORD : getTextBoxValue('keyWordForSelectedUsersTextInput'),
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

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				initDataGridOfPost();
				initDeptTree();
				initDataGridOfSelectedUsers();
				initDataGridOfUnSelectedUsers();
				registerKeyPressForTextInput(
						'keyWordForSelectedUsersTextInput',
						querySelectedUsersForPage);
				registerKeyPressForTextInput(
						'keyWordForUnSelectedUsersTextInput',
						queryUnSelectedUsersForPage);
			});

	//初始化树
	function initDeptTree() {
		$('#deptTree').tree({
			url : 'queryDeptTree.do',
			onClick : function(node) {
				queryPostForPage(node.dept_inner_code); // 在用户点击的时候提示
				$('#datagridOfUnSelectedUsers').datagrid('loadData', {
					total : 0,
					rows : []
				});
				$('#datagridOfSelectedUsers').datagrid('loadData', {
					total : 0,
					rows : []
				});
				$('#datagridOfPost').datagrid('unselectAll');
				$('#datagridOfPost').datagrid('uncheckAll');
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
				width : 100
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
					style="width: 300px; height: 50%;">
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
							toolbar="#toolbarForUnSelectedUsers" pagination="true"
							pageSize="30" pageNumber="1" method="get" fit="true">
						</table>
						<div id="toolbarForUnSelectedUsers">
							<div>
								<input id="keyWordForUnSelectedUsersTextInput"
									class="easyui-textbox"
									data-options="prompt:'待选人员名称|部门名称',validType:'length[0,50]'"
									style="width: 200px"> <a href="#"
									class="easyui-linkbutton" iconCls="icon-search"
									onclick="queryUnSelectedUsersForPage('')">查询</a>
							</div>
						</div>
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
							toolbar="#toolbarForSelectedUsers" checkOnSelect="true"
							pagination="true" pageSize="30" pageNumber="1" method="get"
							fit="true">
						</table>
						<div id="toolbarForSelectedUsers">
							<div>
								<input id="keyWordForSelectedUsersTextInput"
									class="easyui-textbox"
									data-options="prompt:'已选人员名称|部门名称',validType:'length[0,50]'"
									style="width: 200px"> <a href="#"
									class="easyui-linkbutton" iconCls="icon-search"
									onclick="querySelectedUsersForPage('')">查询</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>