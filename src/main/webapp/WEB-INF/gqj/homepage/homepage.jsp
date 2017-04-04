<%@ page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=UTF-8">
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
<script type="text/javascript"
	src="<%=contextPath%>/js/base.js"></script>
<script type="text/javascript">
	//关闭选择部门窗口
	function closeAddToolsUIForBatch() {
		closeUI('addToolsUIForBatch')
	}

	//打开选择部门窗口
	function openAddToolsUIForBatch() {
		createModalDialog("addToolsUIForBatch",
				"openAddToolsUI.do?OP_TYPE=ADD&BATCH_ID=" + batchId
						+ "&BATCH_TYPE="
						+ getTextBoxValue('batchTypeTextInput'), title, 500,
				500);
		openUI('addToolsUIForBatch');
	}

	//打开选择部门窗口
	function openAddToolsUIForExchangeBatch() {
		createModalDialog("addToolsUIForBatch",
				"openAddToolsUI.do?OP_TYPE=EXCHANGE_TO_CHECKIN&BATCH_TYPE="
						+ getTextBoxValue('batchTypeTextInput'), "转仓入库", 500,
				400);
		openUI('addToolsUIForBatch');
	}

	//删除
	function delBatchs() {
		if (checkSelectedItems('datagridForBatch', '请选择批次')) {
			var ids = getIdsOfSelectedItems('datagridForBatch', 'BATCH_ID');
			if (ids != null && ids != '') {
				var params = {
					BATCH_IDS : ids
				};
				showMessageBox(params, 'delBatchs.do', '是否删除所选批次?',
						successFunctionForOption);
			}
		}
	}

	//删除
	function delToolAndTrack(toolId, trackId, batchId) {
		var params = {
			TOOL_ID : toolId,
			TRACK_ID : trackId,
			BATCH_ID : batchId
		};
		showMessageBox(params, 'delToolAndTrack.do', '是否删除所选工器具?',
				successFunctionForOptionToolTrack);
	}

	//确认
	function confirmBatchs() {
		if (checkSelectedItems('datagridForBatch', '请选择批次')) {
			var ids = getIdsOfSelectedItems('datagridForBatch', 'BATCH_ID');
			if (ids != null && ids != '') {
				var params = {
					BATCH_IDS : ids,
					BATCH_TYPE : getTextBoxValue('batchTypeTextInput')
				};
				showMessageBox(params, 'confirmBatchs.do', '是否确认所选批次?',
						successFunctionForOption);
			}
		}
	}

	//领用
	function takeBatchs() {
		if (checkSelectedItems('datagridForBatch', '请选择批次')) {
			var ids = getIdsOfSelectedItems('datagridForBatch', 'BATCH_ID');
			if (ids != null && ids != '') {
				var params = {
					BATCH_IDS : ids
				};
				showMessageBox(params, 'takeBatchs.do', '是否领用所选批次?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForBatch');
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOptionToolTrack(result) {
		showMessage(result.msg, result.msg);
		var rowData = getRowDataOfSelfDataGrid('datagridForBatch',
				rowIndexOfDataGrid);
		queryToolTracks(rowData.BATCH_ID);
	}

	//用在点击查询按钮的时候
	function queryBatchPagesForSearch() {
		queryBatchs();
	}

	//用在点击查询按钮的时候
	function queryToolTrackPagesForSearch() {
		var rowData = getRowDataOfSelfDataGrid('datagridForBatch',
				rowIndexOfDataGrid);
		queryToolTracks(rowData.BATCH_ID);
	}

	//查询
	function queryBatchs() {
		var params = {
			BATCH_TYPE : getTextBoxValue('batchTypeTextInput'),
			keyWord : getTextBoxValue('keyWordForBatchTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForBatch')
		};
		query(params, 'queryBatchsPage.do', successFunctionForQueryBatchs);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryBatchs(result) {
		dataGridLoadData('datagridForBatch', result);
	}

	//查询
	function queryToolTracks(batchId) {
		var params = {
			BATCH_ID : batchId,
			keyWord : getTextBoxValue('keyWordForToolTrackTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForToolTrack')
		};
		query(params, 'queryToolTracksPage.do',
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
										field : 'ck',
										checkbox : true
									},
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
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-add" onclick="">归还</a> <input
					id="keyWordForToolTrackTextInput"
					class="easyui-textbox"
					data-options="prompt:'工器具编号',validType:'length[0,50]'"
					style="width: 200px"> <a href="#"
					class="easyui-linkbutton" iconCls="icon-search"
					onclick="queryToolTrackPagesForSearch()">查询</a>
			</div>
		</div>
	</div>
</body>
</html>