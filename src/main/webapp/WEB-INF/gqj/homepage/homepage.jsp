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

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForToolTrackTextInput',
						queryToolTrackPagesForSearch);
				initDataGridForToolTrackDetail();
			});

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
										field : 'TOOL_CODE',
										title : '工器具编号',
										width : 100
									},
									{
										field : 'BASE_TOOL_TYPE_NAME',
										title : '工器具类型',
										width : 100
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
	<div id="toolTrackUI" class="easyui-panel" title="外借工器具列表"
		data-options="width:680,height:400">
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
					style="width: 200px"> <a href="#" class="easyui-linkbutton"
					iconCls="icon-search" onclick="queryToolTrackPagesForSearch()">查询</a>
			</div>
		</div>
	</div>
</body>
</html>