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
	function closeAddToolsUIForBatch() {
		closeUI('addToolsUIForBatch')
	}

	//打开选择部门窗口
	function openAddToolsUIForBatch(batchId) {
		var panelHeight = 0;
		var batchType = getTextBoxValue('batchTypeTextInput');
		if (batchType == 0) {
			panelWidth = 700;
			panelHeight = 600;
		} else if (batchType == 8 || batchType == 9) {
			panelWidth = 500;
			panelHeight = 250;
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
			title = "扫描入库工器具";
		} else if (batchType == 1) {
			title = "扫描出库工器具";
		} else if (batchType == 2) {
			title = "扫描转仓工器具";
		} else if (batchType == 3) {
			title = "扫描退仓工器具";
		} else if (batchType == 4) {
			title = "扫描报废工器具";
		} else if (batchType == 5) {
			title = "扫描外站借用工器具";
		} else if (batchType == 6) {
			title = "扫描外站归还工器具";
		} else if (batchType == 8) {
			title = "扫描使用本站工器具";
		} else if (batchType == 9) {
			title = "扫描归还本站工器具";
		}
		createModalDialog("addToolsUIForBatch",
				"openAddToolsUI.do?OP_TYPE=ADD&BATCH_ID=" + batchId
						+ "&BATCH_TYPE="
						+ getTextBoxValue('batchTypeTextInput'), title,
				panelWidth, panelHeight);
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
				registerKeyPressForTextInput('keyWordForBatchTextInput',
						queryBatchPagesForSearch);
				registerKeyPressForTextInput('keyWordForToolTrackTextInput',
						queryToolTrackPagesForSearch);
				initDataGridForBatch();
				initDataGridForToolTrackDetail();
				initToolTrackPanel();
				var batchType = getTextBoxValue('batchTypeTextInput');
				if (batchType == 1 || batchType == 2 || batchType == 7
						|| batchType == 5) {
					$('#datagridForBatch').datagrid('showColumn',
							'BATCH_TAKE_DEPT_NAME');
					$('#datagridForBatch').datagrid('showColumn',
							'BATCH_TAKE_USER_NAME');
					$('#datagridForBatch').datagrid('showColumn',
							'BATCH_TAKE_TIME');
					if (batchType == 7) {
						$('#datagridForBatch').datagrid('hideColumn',
								'addToolColumn');
						$('#datagridForToolTrack')
								.datagrid('hideColumn', 'del');
					}
				}

				if (batchType == 6) {
					$('#datagridForBatch').datagrid('showColumn',
							'BATCH_RETURN_USER_NAME');
				}

				var addToolBtnName = "";
				if (batchType == 0) {
					addToolBtnName = "扫描入库工器具";
				} else if (batchType == 1) {
					addToolBtnName = "扫描出库工器具";
				} else if (batchType == 2) {
					addToolBtnName = "扫描转仓工器具";
				} else if (batchType == 3) {
					addToolBtnName = "扫描退仓工器具";
				} else if (batchType == 4) {
					addToolBtnName = "扫描报废工器具";
				} else if (batchType == 5) {
					addToolBtnName = "扫描外站借用工器具";
				} else if (batchType == 6) {
					addToolBtnName = "扫描外站归还工器具";
				} else if (batchType == 8) {
					addToolBtnName = "扫描使用本站工器具";
				} else if (batchType == 9) {
					addToolBtnName = "扫描归还本站工器具";
				}

				$('#addToolBtn').linkbutton({
					text : addToolBtnName
				});
			});

	//初始化明细界面
	function initToolTrackPanel() {
		$('#toolTrackUI').panel({
			closed : true
		});
	}

	//初始化列表元素
	function initDataGridForBatch() {
		$('#datagridForBatch')
				.datagrid(
						{
							url : 'queryBatchsPage.do?BATCH_TYPE='
									+ getTextBoxValue('batchTypeTextInput'),
							idField : 'BATCH_ID',
							rownumbers : true,
							toolbar : '#toolbarForBatch',
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
										field : 'addToolColumn',
										title : '再添工器具',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var batchConfirmUserId = rowData.BATCH_CONFIRM_USER_ID;
											var btn = '';
											if (batchConfirmUserId == null) {
												btn = '<a class="easyui-linkbutton" '
														+ ' onclick="openAddToolsUIForBatch(\''
														+ rowData.BATCH_ID
														+ '\')" href="javascript:void(0)">再添工器具</a>';
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
										field : 'BATCH_COUNT',
										title : '工器具数量',
										width : 100,
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
									}, {
										field : 'BATCH_TAKE_DEPT_NAME',
										title : '领用部门',
										width : 100,
										hidden : true
									}, {
										field : 'BATCH_TAKE_USER_NAME',
										title : '领用人',
										width : 100,
										hidden : true
									}, {
										field : 'BATCH_TAKE_TIME',
										title : '领用时间',
										width : 100,
										hidden : true
									}, {
										field : 'BATCH_RETURN_USER_NAME',
										title : '归还人',
										width : 100,
										hidden : true
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForBatchTextInput');
								param.BATCH_TYPE = getTextBoxValue('batchTypeTextInput');
							},
							onLoadSuccess : function(data) {
								var batchType = getTextBoxValue('batchTypeTextInput');
								if (batchType != 7) {
									if (data.rows.length > 0) {
										//循环判断操作为新增的不能选择
										for (var i = 0; i < data.rows.length; i++) {
											//根据operate让某些行不可选
											if (data.rows[i].BATCH_CONFIRM_USER_ID != null) {
												$("input[type='checkbox']")[i + 1].disabled = true;
												data.rows[i].disabled = true;
											}
										}
									}
								}
							},
							onCheckAll : function(rows) {
								for (var i = 0; i < rows.length; i++) {
									if (rows[i].disabled) {
										$("#datagridForBatch").datagrid(
												'uncheckRow', i);
									}
								}
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
											var toolStatus = rowData.TOOL_STATUS;
											var btn = '';
											if (toolStatus == 0
													|| toolStatus == 2
													|| toolStatus == 5
													|| toolStatus == 7) {
												btn = '<a class="del_btn_class" '
														+ ' onclick="delToolAndTrack(\''
														+ rowData.TOOL_ID
														+ '\',\''
														+ rowData.TRACK_ID
														+ '\',\''
														+ rowData.BATCH_ID
														+ '\')" href="javascript:void(0)"></a>';
											}
											return btn;
										}

									}, {
										field : 'TOOL_CODE',
										title : '工器具编号',
										width : 100
									}, {
										field : 'BASE_TOOL_TYPE_NAME',
										title : '工器具类型',
										width : 100
									}, {
										field : 'BASE_TOOL_NAME',
										title : '工器具名称',
										width : 150
									}, {
										field : 'BASE_TOOL_MANUFACTURER_NAME',
										title : '厂家名称',
										width : 150
									}, {
										field : 'BASE_TOOL_MODEL',
										title : '型号',
										width : 100
									}, {
										field : 'BASE_TOOL_SPEC',
										title : '规格',
										width : 100
									}, {
										field : 'STORE_NAME',
										title : '仓库',
										align : 'center',
										width : 150,
									}, {
										field : 'POS_NAME',
										title : '仓位',
										align : 'center',
										width : 150
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForToolTrackTextInput');
							},
							onLoadSuccess : function(param) {
								$(".del_btn_class").linkbutton({
									iconCls : 'icon-cross'
								});
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
		queryToolTracks(rowData.BATCH_ID);
		$('#batchListUI').panel('close');
		$('#toolTrackUI').panel('open');
		$('#toolTrackUI').panel('maximize');
	}

	//列表界面
	function toList() {
		$('#batchListUI').panel('open');
		$('#toolTrackUI').panel('close');
		$('#batchListUI').panel('maximize');

		rowIndexOfDataGrid = 0;
		//清空明细列表
		dataGridLoadData('datagridForToolTrack', {
			total : 0,
			rows : []
		});

		queryBatchs();
	}
</script>
</head>
<body>

	<div id="batchListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<table id="datagridForBatch" class="easyui-datagrid">
		</table>
		<div id="toolbarForBatch">
			<div style="display: none">
				<input id="batchTypeTextInput" class="easyui-textbox"
					value="<%=request.getParameter("BATCH_TYPE")%>" />
			</div>
			<table style="width: 100%">
				<tr>
					<td><a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true"
						onclick="refreshDataGrid('datagridForBatch')">刷新</a> <%
 	if ("0".equals(batchType)) {
 %> <!--  <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" -->
						<!-- 						onclick="openAddToolsUIForQuickBatch()">快速入库</a> --> <a
						href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="openAddToolsUIForExchangeBatch()">转仓入库</a> <%
 	}
 	if (!"7".equals(batchType)) {
 %> <a id="addToolBtn" href="#" class="easyui-linkbutton"
						iconCls="icon-add" plain="true"
						onclick="openAddToolsUIForBatch('')">扫描入库</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delBatchs()">删除</a><a href="#" class="easyui-linkbutton"
						iconCls="icon-application_go" plain="true"
						onclick="confirmBatchs()">确认</a> <%
 	} else {
 %> <a href="#" class="easyui-linkbutton" iconCls="icon-application_go"
						plain="true" onclick="takeBatchs()">领用</a> <%
 	}
 %></td>
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
	<div id="toolTrackUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<table id="datagridForToolTrack" class="easyui-datagrid">
		</table>

		<div id="toolbarForToolTrack">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-arrow_left" onclick="toList()">返回</a> <input
					id="keyWordForToolTrackTextInput" class="easyui-textbox"
					data-options="prompt:'工器具编号',validType:'length[0,50]'"
					style="width: 200px"> <a href="#" class="easyui-linkbutton"
					iconCls="icon-search" onclick="queryToolTrackPagesForSearch()">查询</a>
			</div>
		</div>
	</div>
</body>
</html>