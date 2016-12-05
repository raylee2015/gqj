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
	//记录新增或者修改的方法
	var url;

	//打开编辑窗口
	function openAddUI() {
		openAddDataUI2(true);
		setTextBoxValue('userIdTextBox', -1);
		url = 'addNewUser.do';
	}

	//打开编辑窗口
	function openEditUI(opType, rowIndex) {
		var rowData = $('#datagrid').datagrid('getData').rows[rowIndex];
		openEditDataUI2(rowData, true, 'USER_DEPT_ID', 'USER_DEPT_NAME');
		url = 'updateUser.do';
	}

	//删除
	function delUsers() {
		var ids = getIdsOfSelectedItems('USER_ID', 'USER_IDS', '');
		var params = {
			USER_IDS : ids
		};
		del(params, "请选择用户", '是否删除所选用户?', 'delUsers.do', true);
	}

	// 保存数据
	function saveUser() {
		var params = {
			USER_ID : getTextBoxValue('userIdTextBox'),
			USER_NAME : getTextBoxValue('userNameTextBox'),
			USER_CODE : getTextBoxValue('userCodeTextBox'),
			USER_PHONE : getTextBoxValue('userPhoneTextBox'),
			USER_SORT : getTextBoxValue('userSortTextBox'),
			USER_DEPT_ID : getComboTreeValue('comboTree')
		};
		save1(params, url, true);
	}

	//初始化用户密码
	function initUserPassWord() {
		var ids = getIdsOfSelectedItems('USER_ID', 'USER_IDS', '');
		var params = {
			USER_IDS : ids
		};
		shwoConfirm(params, "请选择用户", '是否初始化所选用户密码?', 'initUserPassWord.do', true);
	}

	//查询
	function queryForPage(userDeptId) {
		var params = {
			USER_DEPT_ID : userDeptId,
			keyWord : $('#keyWordTextInput').textbox('getValue'),
			page : 1,
			rows : $('#datagrid').datagrid('getPager').data("pagination").options.pageSize
		};
		query(params, 'queryUserPage.do');
	}

	//页面加载完
	$(document).ready(function() {
		initDocument();
		initDataGrid();
		initTree();
	});

	//初始化树
	function initTree() {
		$('#tree').tree({
			url : 'queryDeptTree.do',
			onClick : function(node) {
				queryForPage(node.id); // 在用户点击的时候提示
			},
			onLoadError : function(arguments) {
				eval(errorCodeForQuery);
			}
		});
	}

	//初始化列表元素
	function initDataGrid() {
		$('#datagrid').datagrid({
			url : 'queryUserPage.do',
			idField : 'USER_ID',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'op',
				title : '操作',
				formatter : editColumnFormatter
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
				field : 'USER_SORT',
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
		<div region="west" ,collapsible="false" style="width: 200px;">
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
						iconCls="icon-remove" plain="true" onclick="delUsers()">删除</a><a
						href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="initUserPassWord()">初始化密码</a>
				</div>
				<div>
					<input id="keyWordTextInput" class="easyui-textbox"
						data-options="prompt:'用户名称',validType:'length[0,10]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryForPage('')">查询</a>
				</div>

			</div>
		</div>
	</div>

	<!--  详细界面 -->
	<div id="editUI" class="easyui-window" title="添加用户" closed="true"
		data-options="iconCls:'icon-save'"
		style="width: 400px; height: 260px; padding: 5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" fit="true" border="false">
				<form id="form" method="post" style="width: 100%;">
					<div style="display: none">
						<input id="userIdTextBox" name="USER_ID" class="easyui-textbox" />
					</div>
					<table width="100%">
						<tr>
							<td width="22%">所属部门:</td>
							<td><input id="comboTree" class="easyui-combotree"
								name="USER_DEPT_ID" data-options="required:true"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="22%">用户名称:</td>
							<td><input id="userNameTextBox" name="USER_NAME"
								class="easyui-textbox"
								data-options="prompt:'用户名称',required:true,validType:'length[3,10]'"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="22%">用户编号:</td>
							<td><input id="userCodeTextBox" name="USER_CODE"
								class="easyui-textbox"
								data-options="prompt:'用户编号',required:true,validType:'length[3,10]'"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="22%">手机号码:</td>
							<td><input id="userPhoneTextBox" name="USER_PHONE"
								type="text" class="easyui-numberbox"
								style="width: 100%; height: 32px"
								data-options="precision:0,prompt:'手机号码',required:true,validType:'length[11,11]'" /></td>
						</tr>
						<tr>
							<td width="22%">排序号:</td>
							<td><input id="userSortTextBox" name="USER_SORT" type="text"
								class="easyui-numberbox" style="width: 100%; height: 32px"
								data-options="min:0,max:99,precision:0,prompt:'排序号',required:true,validType:'length[0,2]'" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px">
				<a class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="saveUser()">保存</a> <a
					class="easyui-linkbutton" iconCls="icon-cancel"
					href="javascript:void(0)" onclick="closeEditUI()">关闭</a>
			</div>
		</div>
	</div>
</body>
</html>