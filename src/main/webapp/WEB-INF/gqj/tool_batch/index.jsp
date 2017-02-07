<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String batchType = request.getParameter("BATCH_TYPE");
	String deptType = request.getParameter("DEPT_TYPE");
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
		var panelHeight = 0;
		var batchType = getTextBoxValue('batchTypeTextInput');
		if (batchType == 0) {
			panelHeight = 600;
		} else {
			panelHeight = 500;
		}
		createModalDialog("addToolsUIForBatch", "openAddToolsUI.do?BATCH_TYPE="
				+ getTextBoxValue('batchTypeTextInput'), "扫描入库", 700,
				panelHeight);
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

	//用在点击查询按钮的时候
	function queryBatchPagesForSearch() {
		queryBatchs();
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

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForBatchTextInput',
						queryBatchPagesForSearch);
				initDataGridForBatch();
				initDataGridForBatchDetail();
				initBatchDetailPanel();
				var batchType = getTextBoxValue('batchTypeTextInput');
				if (batchType == 1 || batchType == 2 || batchType == 5) {
					$('#datagridForBatch').treegrid('showColumn',
							'BATCH_TAKE_DEPT_NAME');
					$('#datagridForBatch').treegrid('showColumn',
							'BATCH_TAKE_USER_NAME');
					$('#datagridForBatch').treegrid('showColumn',
							'BATCH_TAKE_TIME');
				}
			});

	//初始化明细界面
	function initBatchDetailPanel() {
		$('#batchDetailUI').panel({
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
										field : 'op',
										title : '操作',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var batchConfirmUserId = rowData.BATCH_CONFIRM_USER_ID;
											var btn = '';
											if (batchConfirmUserId == null) {
												btn = '<a class="easyui-linkbutton" '
														+ ' onclick="toDetail(\''
														+ rowIndex
														+ '\')" href="javascript:void(0)">编辑</a>';
											} else {
												btn = '<a class="easyui-linkbutton" '
														+ ' onclick="toDetail(\''
														+ rowIndex
														+ '\')" href="javascript:void(0)">查看</a>';
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
									} ] ],
							onLoadSuccess : function(data) {
								var batchType = getTextBoxValue('batchTypeTextInput');
								if (batchType != 5) {
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
	function initDataGridForBatchDetail() {
		$('#datagridForBatchDetail')
				.datagrid(
						{
							idField : 'DETAIL_ID',
							rownumbers : true,
							toolbar : '#toolbarForBatchDetail',
							pagination : false,
							checkOnSelect : false,
							fit : true,
							method : 'get',
							columns : [ [
									{
										field : 'op',
										title : '操作',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = '<a class="easyui-linkbutton" '
													+ ' onclick="delRowData(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">删除</a>';
											return btn;
										}
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
										field : 'BASE_TOOL_MANUFACTURER_NAME',
										title : '厂家名称',
										width : 150
									},
									{
										field : 'BASE_TOOL_MODEL',
										title : '型号',
										width : 100
									},
									{
										field : 'BASE_TOOL_SPEC',
										title : '规格',
										width : 100
									},
									{
										field : 'choosePosition',
										title : '选择仓位',
										align : 'center',
										width : 150,
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = '';
											if (rowData.POS_NAME != null) {
												btn = '<a class="choose_position_btn_class" width="100%" '
														+ ' onclick="openChoosePositionUIForBatch(\''
														+ rowIndex
														+ '\',\''
														+ getTextBoxValue('storageIdTextInput')
														+ '\')" href="javascript:void(0)">'
														+ rowData.POS_NAME
														+ '</a>';
											} else {
												btn = '<a class="choose_position_btn_class" width="100%" '
														+ ' onclick="openChoosePositionUIForBatch(\''
														+ rowIndex
														+ '\',\''
														+ getTextBoxValue('storageIdTextInput')
														+ '\')" href="javascript:void(0)">选择仓位</a>';
											}
											return btn;
										}
									},
									{
										field : 'DETAIL_BATCH_AMOUNT',
										title : '数量',
										width : 150,
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var toolAmount = "";
											if (typeof (rowData.DETAIL_BATCH_AMOUNT) == 'undefined') {
												toolAmount = "";
											} else {
												toolAmount = rowData.DETAIL_BATCH_AMOUNT;
											}
											var textInput = '<input value="'
													+ toolAmount
													+ '" type="text" onChange="setToolAmount('
													+ rowIndex
													+ ',this.value)" />';
											return textInput;
										}
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForBatchTextInput');
							},
							onLoadSuccess : function(param) {
								$(".choose_position_btn_class").linkbutton({
									plain : true,
									width : 145
								});
								$(".set_tool_amount_textinput_class").textbox({
									width : 145
								});
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//设置工器具数量
	function setToolAmount(rowIndex, newValue) {
		var rowData = getRowDataOfSelfDataGrid('datagridForBatchDetail',
				rowIndex);
		if (getTextBoxValue('batchTypeTextInput') == 1
				|| getTextBoxValue('batchTypeTextInput') == 2) {
			if (newValue > rowData.INVENT_AMOUNT) {
				alert('输入数量大于现有库存，请重新输入')
			}
		}
		rowData.DETAIL_BATCH_AMOUNT = newValue;
	}

	//删除行数据
	function delRowData(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForBatchDetail',
				rowIndex);
		var data = $('#datagridForBatchDetail').datagrid('getData').rows;
		for (var i = 0; i < data.length; i++) {
			if (rowData.BASE_TOOL_ID == data[i].BASE_TOOL_ID) {
				data.splice(i, 1);
				break;
			}
		}
		dataGridLoadData('datagridForBatchDetail', data);
	}

	//操作类型
	var opType = '';

	//记录新增或者修改的方法
	var url;

	var rowIndexOfDataGrid = 0;

	//编辑界面
	function toDetail(rowIndex) {
		if (rowIndex != null) {
			rowIndexOfDataGrid = rowIndex;
			var rowData = getRowDataOfSelfDataGrid('datagridForBatch', rowIndex);
			var batchConfirmUserId = rowData.BATCH_CONFIRM_USER_ID;
			opType = 'edit';
			queryBatchDetailsForList(rowData);
			setTextBoxValue('batchCodeTextInput', rowData.BATCH_CODE);
			setTextBoxValue('batchRemarkTextInput', rowData.BATCH_REMARK);
			setTextBoxValue('storageIdTextInput', rowData.STORE_ID);
			setTextBoxValue('planIdTextInput', rowData.PLAN_ID);
			$('#storageNameBtn').linkbutton({
				text : rowData.STORE_NAME,
				width : 200
			});
			$('#planNameBtn').linkbutton({
				text : rowData.PLAN_CODE,
				width : 200
			});

			if (batchConfirmUserId != null) {
				$('#saveBtn').linkbutton({
					disabled : true
				});
				$('#addToolsBtn').linkbutton({
					disabled : true
				});
			}
		} else {
			opType = 'add';
		}
		$('#batchListUI').panel('collapse');
		$('#batchDetailUI').panel('expand');
	}

	//根据出入库单据id查询明细
	function queryBatchDetailsForList(rowData) {
		var params = {
			BATCH_ID : rowData.BATCH_ID,
		};
		query(params, 'queryBatchDetailsForList.do',
				successFunctionForQueryBatchDetails);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryBatchDetails(result) {
		dataGridLoadData('datagridForBatchDetail', result);
	}

	//列表界面
	function toList() {
		$('#batchListUI').panel('expand');
		$('#batchDetailUI').panel('collapse');
		rowIndexOfDataGrid = 0;
		setTextBoxValue('batchCodeTextInput', '');
		setTextBoxValue('batchRemarkTextInput', '');
		//清空明细列表
		dataGridLoadData('datagridForBatchDetail', {
			total : 0,
			rows : []
		});
		$('#saveBtn').linkbutton({
			disabled : false
		});
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
 	if (!"7".equals(batchType)) {
 %> <a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true" onclick="openAddToolsUIForBatch()">扫描入库</a> <a
						href="#" class="easyui-linkbutton" iconCls="icon-remove"
						plain="true" onclick="delBatchs()">删除</a><a href="#"
						class="easyui-linkbutton" iconCls="icon-application_go"
						plain="true" onclick="confirmBatchs()">确认</a> <%
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
	<div id="batchDetailUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<table id="datagridForBatchDetail" class="easyui-datagrid">
		</table>

		<div id="toolbarForBatchDetail">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-arrow_left" onclick="toList()">返回</a>
			</div>
			<div>
				<table>
					<tr>
						<td>批次号： <input id="batchCodeTextInput"
							class="easyui-textbox"
							data-options="prompt:'批次号',validType:'length[0,50]',disabled:true"
							style="width: 200px"></td>
						<%
							if ("1".equals(batchType) || "2".equals(batchType)) {
						%>
						<td>
							<div style="display: none">
								<input id="deptIdTextInput" class="easyui-textbox" />
							</div> 领用部门：<a href="#" id="deptNameBtn" class="easyui-linkbutton"
							style="width: 200px;" onclick="openChooseDeptUIForBatch()">
								选择领用部门</a>
						</td>
						<%
							}
						%>
						<td>备注： <input id="batchRemarkTextInput"
							class="easyui-textbox"
							data-options="prompt:'备注',validType:'length[0,50]'"
							style="width: 200px"></td>
					</tr>
				</table>

			</div>
		</div>
	</div>
</body>
</html>