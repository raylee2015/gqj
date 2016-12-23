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
	//关闭编辑窗口
	function closeEditUIForDept() {
		closeEditUI('editUIForDept')
	}

	//打开编辑窗口
	function openAddUIForDept() {
		createModalDialog("editUIForDept", "openEditUI.do?opType=add", "添加部门",
				400, 200);
		openEditUI('editUIForDept');
	}

	//打开编辑窗口
	function openEditUIForDept(rowIndex) {
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		var rowData = getRowDataOfSelfDataGrid('datagridForDept', rowIndex);
		createModalDialog("editUIForDept", url,
				("修改部门\"" + rowData.DEPT_NAME + "\"的信息"), 400, 200);
		openEditUI('editUIForDept');
	}

	//删除
	function delDepts() {
		if (checkSelectedItems('datagridForDept', '请选择部门')) {
			var deptIds = getIdsOfSelectedItems('datagridForDept',
					'DEPT_INNER_CODE');
			if (deptIds != null && deptIds != '') {
				var params = {
					DEPT_IDS : deptIds
				};
				showMessageBox(params, 'delDepts.do', '是否删除所选部门及其下属部门?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForDept');
		reloadTree("deptTree");
	}

	//更新级联数据
	function updateInnerData() {
		var params = {};
		showMessageBox(params, 'updateInnerData.do', '是否更新级联数据?',
				successFunctionForOption);
	}

	//用在点击查询按钮的时候
	function queryDeptPagesForSearch() {
		queryDepts('');
	}

	//用在点击树的时候
	function queryDeptPagesForTree(deptInnerCode) {
		queryDepts(deptInnerCode);
	}

	//查询
	function queryDepts(deptInnerCode) {
		var params = {
			deptInnerCode : deptInnerCode,
			keyWord : $('#keyWordForDeptTextInput').textbox('getValue'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForDept')
		};
		query(params, 'queryDeptsPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForDept', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				initDataGridForDept();
				initDeptTree();
				registerKeyPressForTextInput('keyWordForDeptTextInput',
						queryDeptPagesForSearch);
			});

	//初始化树
	function initDeptTree() {
		$('#deptTree').tree({
			url : 'queryDeptTree.do',
			onClick : function(node) {
				queryDeptPagesForTree(node.dept_inner_code); // 在部门点击的时候提示
			},
			onLoadError : function(arguments) {
				errorFunctionForQuery();
			}
		});
	}

	//初始化列表元素
	function initDataGridForDept() {
		$('#datagridForDept')
				.datagrid(
						{
							url : 'queryDeptsPage.do',
							idField : 'DEPT_ID',
							rownumbers : true,
							toolbar : '#toolbarForDept',
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
													+ ' onclick="openEditUIForDept(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'DEPT_NAME',
										title : '部门名称',
										width : 100,
									}, {
										field : 'UP_DEPT_NAME',
										title : '上级部门',
										width : 100,
									}, {
										field : 'DEPT_INNER_NAME',
										title : '部门级联',
										width : 250,
									}, {
										field : 'DEPT_SORT',
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
		<div region="west" collapsible="false" style="width: 200px;">
			<ul id="deptTree" class="easyui-tree" method="get" animate="true"
				lines="true"></ul>
		</div>
		<div region="center">
			<table id="datagridForDept" class="easyui-datagrid">
			</table>
			<div id="toolbarForDept">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refreshDataGrid('datagridForDept')">刷新</a> <a
						href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="openAddUIForDept()">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delDepts()">删除</a> <a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true" onclick="updateInnerData()">更新级联数据</a>
				</div>
				<div>
					<input id="keyWordForDeptTextInput" class="easyui-textbox"
						data-options="prompt:'部门名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryDeptPagesForSearch()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>