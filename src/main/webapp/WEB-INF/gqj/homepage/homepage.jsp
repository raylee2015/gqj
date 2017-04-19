<%@page import="com.base.admin.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String userDeptCode = ((User) request.getSession().getAttribute("user")).getUserDeptCode();
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
	href="<%=contextPath%>/css/base.css">
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
	//关闭选择部门窗口
	function closeAddToolsUIForBatch() {
		closeUI('addToolsUIForBatch')
	}

	//打开选择部门窗口
	function openAddToolsUIForBatch() {
		createModalDialog("addToolsUIForBatch",
				"openAddToolsUI.do?BATCH_TYPE=6", "归还工器具", 500, 300);
		openUI('addToolsUIForBatch');
	}

	//用在点击查询按钮的时候
	function queryToolTrackPagesForSearch() {
		queryToolTracks();
	}

	//查询
	function queryToolTracks() {
		var params = {
			keyWord : getTextBoxValue('keyWordForToolTrackTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForToolTrack')
		};
		query(params, 'queryNeedReturnTools.do',
				successFunctionForQueryToolTracks);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryToolTracks(result) {
		dataGridLoadData('datagridForToolTrack', result);
	}

	//用在点击查询按钮的时候
	function queryTestToolPagesForSearch() {
		queryTestTools();
	}

	//查询
	function queryTestTools() {
		var params = {
			DATE_TYPE : 'OVER_TEST',
			keyWord : getTextBoxValue('keyWordForTestToolTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForTestTool')
		};
		query(params, 'queryNeedReturnTools.do',
				successFunctionForQueryTestTools);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryTestTools(result) {
		dataGridLoadData('datagridForTestTool', result);
	}
	
	//用在点击查询按钮的时候
	function queryRejectToolPagesForSearch() {
		queryRejectTools();
	}

	//查询
	function queryRejectTools() {
		var params = {
			DATE_TYPE : 'OVER_REJECT',
			keyWord : getTextBoxValue('keyWordForRejectToolTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForRejectTool')
		};
		query(params, 'queryNeedReturnTools.do',
				successFunctionForQueryRejectTools);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryRejectTools(result) {
		dataGridLoadData('datagridForRejectTool', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForToolTrackTextInput',
						queryToolTrackPagesForSearch);
				registerKeyPressForTextInput('keyWordForTestToolTextInput',
						queryTestToolPagesForSearch);
				registerKeyPressForTextInput('keyWordForRejectToolTextInput',
						queryRejectToolPagesForSearch);

				initDataGridForToolTrackDetail();
				initDataGridForTestTool();
				initDataGridForRejectTool()

				var userDeptCode = getTextBoxValue("userDeptCode");
				if (userDeptCode == 'GLRY') {
					$('#toolTrackUI').panel({
						closed : true
					});
					$('#tejectToolUI').panel({
						closed : true
					});
					$('#testToolUI').panel({
						closed : true
					});
					$('#welcomeUI').panel({
						closed : false
					});
				}
			});

	//初始化列表元素
	function initDataGridForTestTool() {
		$('#datagridForTestTool')
				.datagrid(
						{
							url : 'queryToolInventorysPage.do?DATE_TYPE=OVER_TEST',
							idField : 'TOOL_ID',
							rownumbers : true,
							toolbar : '#toolbarForTestTool',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : false,
							fit : true,
							method : 'get',
							columns : [ [
									{
										field : 'BASE_TOOL_NAME',
										title : '工器具名称',
										width : 150,
									},
									{
										field : 'BASE_TOOL_MANUFACTURER_NAME',
										title : '厂家',
										width : 150,
									},
									{
										field : 'TOOL_NEXT_TEST_DATE',
										title : '下次试验日期',
										width : 100,
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = rowData.TOOL_NEXT_TEST_DATE;
											if (rowData.NEED_TEST == 1) {
												btn = '<font color="#0000ff"> '
														+ rowData.TOOL_NEXT_TEST_DATE
														+ '</font>';
											}
											if (rowData.NEED_TEST == 2) {
												btn = '<font color="#ff0000"> '
														+ rowData.TOOL_NEXT_TEST_DATE
														+ '</font>';
											}
											return btn;
										}
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForTestToolTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForRejectTool() {
		$('#datagridForRejectTool')
				.datagrid(
						{
							url : 'queryToolInventorysPage.do?DATE_TYPE=OVER_REJECT',
							idField : 'TOOL_ID',
							rownumbers : true,
							toolbar : '#toolbarForRejectTool',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : false,
							fit : true,
							method : 'get',
							columns : [ [
									{
										field : 'BASE_TOOL_NAME',
										title : '工器具名称',
										width : 150,
									},
									{
										field : 'BASE_TOOL_MANUFACTURER_NAME',
										title : '厂家',
										width : 150,
									},
									{
										field : 'TOOL_REJECT_DATE',
										title : '报废日期',
										width : 100,
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = rowData.TOOL_REJECT_DATE;
											if (rowData.NEED_REJECT == 1) {
												btn = '<font color="#ff0000"> '
														+ rowData.TOOL_REJECT_DATE
														+ '</font>';
											}
											return btn;
										}
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForRejectToolTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForToolTrackDetail() {
		$('#datagridForToolTrack')
				.datagrid(
						{
							idField : 'TRACK_ID',
							rownumbers : true,
							toolbar : '#toolbarForToolTrack',
							url : 'queryNeedReturnTools.do',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : false,
							fit : true,
							method : 'get',
							columns : [ [
									{
										field : 'BASE_TOOL_TYPE_NAME',
										title : '工器具类型',
										width : 80
									},
									{
										field : 'BASE_TOOL_NAME',
										title : '工器具名称',
										width : 150
									},
									{
										field : 'TOOL_DEPT_NAME',
										title : '借用部门',
										width : 100
									},
									{
										field : 'TOOL_NEXT_TEST_DATE',
										title : '下次试验时间',
										width : 80,
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = rowData.TOOL_NEXT_TEST_DATE;
											if (rowData.NEED_TEST == 1) {
												btn = '<font color="#0000ff"> '
														+ rowData.TOOL_NEXT_TEST_DATE
														+ '</font>';
											}
											if (rowData.NEED_TEST == 2) {
												btn = '<font color="#ff0000"> '
														+ rowData.TOOL_NEXT_TEST_DATE
														+ '</font>';
											}
											return btn;
										}
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForToolTrackTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}
</script>
</head>
<body>
	<div style="display: none">
		<input id="userDeptCode" class="easyui-textbox"
			value="<%=userDeptCode%>" />
	</div>
	<div id="welcomeUI" class="easyui-panel"
		data-options="fit:true,closed:true">欢迎使用工器具管理系统</div>
	<table width="100%">
		<tr>
			<td width="33%"><div id="toolTrackUI" class="easyui-panel"
					title="外借工器具列表" data-options="height:400">
					<table id="datagridForToolTrack" class="easyui-datagrid">
					</table>

					<div id="toolbarForToolTrack">
						<div>
							<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
								plain="true" onclick="refreshDataGrid('datagridForToolTrack')">刷新</a>
							<a href="#" class="easyui-linkbutton" plain="true"
								iconCls="icon-add" onclick="openAddToolsUIForBatch()">归还</a> <input
								id="keyWordForToolTrackTextInput" class="easyui-textbox"
								data-options="prompt:'工器具编号',validType:'length[0,50]'"
								style="width: 200px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryToolTrackPagesForSearch()">查询</a>
						</div>
					</div>
				</div></td>
			<td width="33%"><div id="testToolUI" class="easyui-panel"
					title="到期试验工器具列表" data-options="height:400">
					<table id="datagridForTestTool" class="easyui-datagrid">
					</table>

					<div id="toolbarForTestTool">
						<div>
							<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
								plain="true" onclick="refreshDataGrid('datagridForTestTool')">刷新</a>
							<input id="keyWordForTestToolTextInput" class="easyui-textbox"
								data-options="prompt:'工器具编号',validType:'length[0,50]'"
								style="width: 200px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryToolTrackPagesForSearch()">查询</a>
						</div>
					</div>
				</div></td>
			<td width="33%"><div id="rejectToolUI" class="easyui-panel"
					title="到期报废工器具列表" data-options="height:400">
					<table id="datagridForRejectTool" class="easyui-datagrid">
					</table>

					<div id="toolbarForRejectTool">
						<div>
							<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
								plain="true" onclick="refreshDataGrid('datagridForRejectTool')">刷新</a>
							<input id="keyWordForRejectToolTextInput" class="easyui-textbox"
								data-options="prompt:'工器具编号',validType:'length[0,50]'"
								style="width: 200px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryToolTrackPagesForSearch()">查询</a>
						</div>
					</div>
				</div></td>
		</tr>
	</table>
</body>
</html>