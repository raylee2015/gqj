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
	// 关闭编辑窗口
	function closeEditUIForBaseTool() {
		closeEditUI('editUIForBaseTool')
	}

	// 编辑窗口变大
	function makeEditUIBigger() {
		$('#editUIForBaseTool').panel('resize', {
			height : 500
		});
		$('#editUIForBaseTool').window('center');
	}

	// 编辑窗口变小
	function makeEditUISmaller() {
		$('#editUIForBaseTool').panel('resize', {
			height : 160
		});
		$('#editUIForBaseTool').window('center');
	}

	//打开编辑窗口
	function openAddUIForBaseTool() {
		createModalDialog("editUIForBaseTool", "openEditUI.do?opType=add",
				"添加工器具基础", 400, 160);
		openEditUI('editUIForBaseTool');
	}

	//打开编辑窗口
	function openEditUIForBaseTool(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForBaseTool', rowIndex);
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForBaseTool", url, ("修改工器具基础\""
				+ rowData.POS_NAME + "\"的信息"), 400, 160);
		openEditUI('editUIForBaseTool');
	}

	//删除
	function delBaseTools() {
		if (checkSelectedItems('datagridForBaseTool', '请选择工器具基础')) {
			var posIds = getIdsOfSelectedItems('datagridForBaseTool', 'POS_ID');
			if (posIds != null && posIds != '') {
				var params = {
					POS_IDS : posIds
				};
				showMessageBox(params, 'delBaseTools.do', '是否删除所选工器具基础?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForBaseTool');
	}

	//用在点击查询按钮的时候
	function queryBaseToolPagesForSearch() {
		queryBaseTools();
	}

	//查询
	function queryBaseTools() {
		var params = {
			keyWord : getTextBoxValue('keyWordForBaseToolTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForBaseTool')
		};
		query(params, 'queryBaseToolsPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForBaseTool', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForBaseToolTextInput',
						queryBaseToolPagesForSearch);
				initDataGridForBaseTool();
			});

	//初始化列表元素
	function initDataGridForBaseTool() {
		$('#datagridForBaseTool')
				.datagrid(
						{
							url : 'queryBaseToolsPage.do',
							idField : 'POS_ID',
							rownumbers : true,
							toolbar : '#toolbarForBaseTool',
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
													+ ' onclick="openEditUIForBaseTool(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'POS_NAME',
										title : '工器具基础名称',
										width : 150,
									}, {
										field : 'STORE_NAME',
										title : '所属仓库',
										width : 150,
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
		<div data-options="region:'center'">
			<table id="datagridForBaseTool" class="easyui-datagrid">
			</table>
			<div id="toolbarForBaseTool">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refreshDataGrid('datagridForBaseTool')">刷新</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true" onclick="openAddUIForBaseTool()">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delBaseTools()">删除</a>
				</div>
				<div>
					<input id="keyWordForBaseToolTextInput" class="easyui-textbox"
						data-options="prompt:'工器具基础名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryBaseToolPagesForSearch()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>