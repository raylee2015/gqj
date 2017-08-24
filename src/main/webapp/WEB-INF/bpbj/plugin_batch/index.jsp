<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String batchType = request.getParameter("BATCH_TYPE");
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
	function closeAddPlugInsUIForBatch() {
		closeUI('addPlugInsUIForBatch')
	}

	//打开选择部门窗口
	function openAddPlugInsUIForBatch(batchId) {
		var panelHeight = 0;
		var batchType = getTextBoxValue('batchTypeTextInput');
		if (batchType == 0) {
			panelWidth = 500;
			panelHeight = 400;
		} else if (batchType == 8 || batchType == 9) {
			panelWidth = 500;
			panelHeight = 200;
		} else if (batchType == 1 || batchType == 5 || batchType == 6) {
			panelWidth = 500;
			panelHeight = 280;
		} else {
			panelWidth = 500;
			panelHeight = 250;
		}
		var batchType = getTextBoxValue('batchTypeTextInput');
		var title = "";
		if (batchType == 0) {
			title = "扫描入库插件";
		} else if (batchType == 1) {
			title = "扫描出库插件";
		} else if (batchType == 2) {
			title = "扫描转仓插件";
		} else if (batchType == 3) {
			title = "扫描退仓插件";
		} else if (batchType == 4) {
			title = "扫描报废插件";
		} else if (batchType == 5) {
			title = "扫描外站借用插件";
		} else if (batchType == 6) {
			title = "扫描外站归还插件";
		} else if (batchType == 8) {
			title = "扫描使用本站插件";
		} else if (batchType == 9) {
			title = "扫描归还本站插件";
		}
		createModalDialog("addPlugInsUIForBatch",
				"openAddPlugInsUI.do?OP_TYPE=ADD&BATCH_ID=" + batchId
						+ "&BATCH_TYPE="
						+ getTextBoxValue('batchTypeTextInput'), title,
				panelWidth, panelHeight);
		openUI('addPlugInsUIForBatch');
	}

	//打开选择部门窗口
	function openAddPlugInsUIForExchangeBatch() {
		createModalDialog("addPlugInsUIForBatch",
				"openAddPlugInsUI.do?OP_TYPE=EXCHANGE_TO_CHECKIN&BATCH_TYPE="
						+ getTextBoxValue('batchTypeTextInput'), "转仓入库", 500,
				400);
		openUI('addPlugInsUIForBatch');
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
	function delPlugInAndTrack(plugInId, trackId, batchId) {
		var params = {
			TOOL_ID : plugInId,
			TRACK_ID : trackId,
			BATCH_ID : batchId
		};
		showMessageBox(params, 'delPlugInAndTrack.do', '是否删除所选插件?',
				successFunctionForOptionPlugInTrack);
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
	function successFunctionForOptionPlugInTrack(result) {
		showMessage(result.msg, result.msg);
		var rowData = getRowDataOfSelfDataGrid('datagridForBatch',
				rowIndexOfDataGrid);
		queryPlugInTracks(rowData.BATCH_ID);
	}

	//用在点击查询按钮的时候
	function queryBatchPagesForSearch() {
		queryBatchs();
	}

	//用在点击查询按钮的时候
	function queryPlugInTrackPagesForSearch() {
		var rowData = getRowDataOfSelfDataGrid('datagridForBatch',
				rowIndexOfDataGrid);
		queryPlugInTracks(rowData.BATCH_ID);
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
	function queryPlugInTracks(batchId) {
		var params = {
			BATCH_ID : batchId,
			keyWord : getTextBoxValue('keyWordForPlugInTrackTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForPlugInTrack')
		};
		query(params, 'queryPlugInTracksPage.do',
				successFunctionForQueryPlugInTracks);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryPlugInTracks(result) {
		dataGridLoadData('datagridForPlugInTrack', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForBatchTextInput',
						queryBatchPagesForSearch);
				registerKeyPressForTextInput('keyWordForPlugInTrackTextInput',
						queryPlugInTrackPagesForSearch);
				initDataGridForBatch();
				initDataGridForPlugInTrackDetail();
				initPlugInTrackPanel();
				var batchType = getTextBoxValue('batchTypeTextInput');

				var addPlugInBtnName = "";
				if (batchType == 0) {
					addPlugInBtnName = "扫描入库插件";
				} else if (batchType == 1) {
					addPlugInBtnName = "扫描出库插件";
				} else if (batchType == 2) {
					addPlugInBtnName = "扫描转仓插件";
				} else if (batchType == 3) {
					addPlugInBtnName = "扫描退仓插件";
				} else if (batchType == 4) {
					addPlugInBtnName = "扫描报废插件";
				} else if (batchType == 5) {
					addPlugInBtnName = "扫描外站借用插件";
				} else if (batchType == 6) {
					addPlugInBtnName = "扫描外站归还插件";
				} else if (batchType == 8) {
					addPlugInBtnName = "扫描使用本站插件";
				} else if (batchType == 9) {
					addPlugInBtnName = "扫描归还本站插件";
				}

				$('#addPlugInBtn').linkbutton({
					text : addPlugInBtnName
				});
			});

	//初始化明细界面
	function initPlugInTrackPanel() {
		$('#plugInTrackUI').panel({
			closed : true
		});
	}

	//初始化列表元素
	function initDataGridForBatch() {
		$('#datagridForBatch')
				.datagrid(
						{
							url : 'queryBatchsPage.do',
							idField : 'BATCH_ID',
							rownumbers : true,
							toolbar : '#plugInbarForBatch',
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
										field : 'addPlugInColumn',
										title : '再添插件',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var batchConfirmUserId = rowData.BATCH_CONFIRM_USER_ID;
											var btn = '';
											if (batchConfirmUserId == null) {
												btn = '<a class="easyui-linkbutton" '
														+ ' onclick="openAddPlugInsUIForBatch(\''
														+ rowData.BATCH_ID
														+ '\')" href="javascript:void(0)">再添插件</a>';
											}
											return btn;
										}
									},
									{
										field : 'op',
										title : '操作',
										align : 'center',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var batchConfirmUserId = rowData.BATCH_CONFIRM_USER_ID;
											var btn = '';
											if (batchConfirmUserId == null) {
												btn = '<a class="easyui-linkbutton" '
														+ ' onclick="toDetail(\''
														+ rowIndex
														+ '\')" href="javascript:void(0)">编辑明细</a>';
											} else {
												btn = '<a class="easyui-linkbutton" '
														+ ' onclick="toDetail(\''
														+ rowIndex
														+ '\')" href="javascript:void(0)">查看明细</a>';
											}
											return btn;
										}
									}, {
										field : 'BATCH_CODE',
										title : '批次号',
										width : 250,
									}, {
										field : 'BATCH_CREATE_USER_NAME',
										title : '创建人',
										width : 100,
									}, {
										field : 'BATCH_CREATE_TIME',
										title : '创建时间',
										width : 100,
									}, {
										field : 'BATCH_CONFIRM_USER_NAME',
										title : '确认人',
										width : 100,
									}, {
										field : 'BATCH_CONFIRM_TIME',
										title : '确认时间',
										width : 100,
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForBatchTextInput');
								param.BATCH_TYPE = getTextBoxValue('batchTypeTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForPlugInTrackDetail() {
		$('#datagridForPlugInTrack')
				.datagrid(
						{
							idField : 'TRACK_ID',
							rownumbers : true,
							toolbar : '#plugInbarForPlugInTrack',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : false,
							fit : true,
							method : 'get',
							columns : [ [
									{
										field : 'del',
										title : '删除',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var plugInStatus = rowData.TOOL_STATUS;
											var btn = '';
											btn = '<a class="del_btn_class" '
													+ ' onclick="delPlugInAndTrack(\''
													+ rowData.TOOL_ID
													+ '\',\''
													+ rowData.TRACK_ID
													+ '\',\''
													+ rowData.BATCH_ID
													+ '\')" href="javascript:void(0)">删除</a>';
											return btn;
										}

									}, {
										field : 'PLUGIN_CODE',
										title : '编号',
										width : 150
									}, {
										field : 'BASE_TOOL_NAME',
										title : '名称',
										width : 150,
									}, {
										field : 'MAN_NAME',
										title : '厂家',
										width : 150,
									}, {
										field : 'BASE_TOOL_MODEL',
										title : '装置型号',
										width : 150,
									}, {
										field : 'BASE_TOOL_SPEC',
										title : '具体参数',
										width : 150,
									}, {
										field : 'PLUGIN_MAN_DATE',
										title : '生产日期',
										width : 100,
									}, {
										field : 'STORE_NAME',
										title : '仓库',
										align : 'center',
										width : 120
									} , {
										field : 'POS_NAME',
										title : '仓位',
										align : 'center',
										width : 120
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForPlugInTrackTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//操作类型
	var opType = '';

	//记录新增或者修改的方法
	var url;

	var rowIndexOfDataGrid = 0;

	//编辑界面
	function toDetail(rowIndex) {
		rowIndexOfDataGrid = rowIndex;
		var rowData = getRowDataOfSelfDataGrid('datagridForBatch', rowIndex);
		queryPlugInTracks(rowData.BATCH_ID);
		$('#batchListUI').panel('close');
		$('#plugInTrackUI').panel('open');
		$('#plugInTrackUI').panel('maximize');
	}

	//列表界面
	function toList() {
		$('#batchListUI').panel('open');
		$('#plugInTrackUI').panel('close');
		$('#batchListUI').panel('maximize');

		rowIndexOfDataGrid = 0;
		dataGridLoadData('datagridForPlugInTrack', {
			total : 0,
			rows : []
		});

		queryBatchs();
	}
</script>
</head>
<body>
	<div style="display: none">
		<input id="batchTypeTextInput" class="easyui-textbox"
			value="<%=batchType%>" />
	</div>
	<div id="batchListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<table id="datagridForBatch" class="easyui-datagrid">
		</table>
		<div id="plugInbarForBatch">
			<table style="width: 100%">
				<tr>
					<td><a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true"
						onclick="refreshDataGrid('datagridForBatch')">刷新</a> <%
 	if ("0".equals(batchType)) {
 %> <a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true" onclick="openAddPlugInsUIForExchangeBatch()">转仓入库</a>
						<%
							}
						%> <a id="addPlugInBtn" href="#" class="easyui-linkbutton"
						iconCls="icon-add" plain="true"
						onclick="openAddPlugInsUIForBatch('')">扫描入库</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delBatchs()">删除</a><a href="#" class="easyui-linkbutton"
						iconCls="icon-application_go" plain="true"
						onclick="confirmBatchs()">确认</a></td>
					<td align="right"><input id="keyWordForBatchTextInput"
						class="easyui-textbox"
						data-options="prompt:'批次号',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryBatchPagesForSearch()">查询</a>
				</tr>
			</table>
		</div>
	</div>
	<div id="plugInTrackUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<table id="datagridForPlugInTrack" class="easyui-datagrid">
		</table>
		<div id="plugInbarForPlugInTrack">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-arrow_left" onclick="toList()">返回</a> <input
					id="keyWordForPlugInTrackTextInput" class="easyui-textbox"
					data-options="prompt:'插件编号',validType:'length[0,50]'"
					style="width: 200px"> <a href="#" class="easyui-linkbutton"
					iconCls="icon-search" onclick="queryPlugInTrackPagesForSearch()">查询</a>
			</div>
		</div>
	</div>
</body>
</html>