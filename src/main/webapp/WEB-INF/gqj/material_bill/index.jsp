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
	//关闭选择工器具窗口
	function closeChooseBaseToolUIForMaterialBill() {
		closeUI('chooseBaseToolUIForMaterialBill')
	}

	//打开选择工器具窗口
	function openChooseBaseToolUIForMaterialBill() {
		createModalDialog("chooseBaseToolUIForMaterialBill",
				"openChooseBaseToolUI.do?opType=add", "添加工器具", 800, 600);
		openUI('chooseBaseToolUIForMaterialBill');
	}

	//关闭选择仓库窗口
	function closeChooseStorageUIForMaterialBill() {
		closeUI('chooseStorageUIForMaterialBill')
	}

	//打开选择仓库窗口
	function openChooseStorageUIForMaterialBill() {
		createModalDialog("chooseStorageUIForMaterialBill",
				"openChooseStorageUI.do?opType=add", "选择仓库", 500, 500);
		openUI('chooseStorageUIForMaterialBill');
	}

	//关闭选择仓位窗口
	function closeChoosePositionUIForMaterialBill() {
		closeUI('choosePositionUIForMaterialBill')
	}

	//打开选择仓位窗口
	function openChoosePositionUIForMaterialBill(rowIndex, storeId) {
		var storeId = getTextBoxValue('storageIdTextInput');
		if (storeId == null || storeId == '') {
			alert("请选择仓库");
			return;
		}
		createModalDialog("choosePositionUIForMaterialBill",
				"openChoosePositionUI.do?rowIndex=" + rowIndex + "&STORE_ID="
						+ storeId, "选择仓库", 500, 500);
		openUI('choosePositionUIForMaterialBill');
	}

	//删除
	function delMaterialBills() {
		if (checkSelectedItems('datagridForMaterialBill', '请选择批次')) {
			var ids = getIdsOfSelectedItems('datagridForMaterialBill',
					'BILL_ID');
			if (ids != null && ids != '') {
				var params = {
					BILL_IDS : ids
				};
				showMessageBox(params, 'delMaterialBills.do', '是否删除所选批次?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForMaterialBill');
	}

	//用在点击查询按钮的时候
	function queryMaterialBillPagesForSearch() {
		queryMaterialBills();
	}

	//查询
	function queryMaterialBills() {
		var params = {
			keyWord : getTextBoxValue('keyWordForMaterialBillTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForMaterialBill')
		};
		query(params, 'queryMaterialBillsPage.do',
				successFunctionForQueryMaterialBills);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryMaterialBills(result) {
		dataGridLoadData('datagridForMaterialBill', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForMaterialBillTextInput',
						queryMaterialBillPagesForSearch);
				initDataGridForMaterialBill();
				initDataGridForMaterialBillDetail();
			});

	//初始化列表元素
	function initDataGridForMaterialBill() {
		$('#datagridForMaterialBill')
				.datagrid(
						{
							url : 'queryMaterialBillsPage.do?BILL_TYPE='
									+ getTextBoxValue('billTypeTextInput'),
							idField : 'BILL_ID',
							rownumbers : true,
							toolbar : '#toolbarForMaterialBill',
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
													+ ' onclick="toDetail(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'BILL_CODE',
										title : '批次号',
										width : 250,
									}, {
										field : 'BILL_CREATE_USER_NAME',
										title : '创建人',
										width : 100,
									}, {
										field : 'BILL_CREATE_TIME',
										title : '创建时间',
										width : 100,
									}, {
										field : 'BILL_CONFIRM_USER_NAME',
										title : '确认人',
										width : 100,
									}, {
										field : 'BILL_CONFIRM_TIME',
										title : '确认时间',
										width : 100,
									} ] ],
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForMaterialBillDetail() {
		$('#datagridForMaterialBillDetail')
				.datagrid(
						{
							idField : 'DETAIL_ID',
							rownumbers : true,
							toolbar : '#toolbarForMaterialBillDetail',
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
														+ ' onclick="openChoosePositionUIForMaterialBill(\''
														+ rowIndex
														+ '\',\''
														+ getTextBoxValue('storageIdTextInput')
														+ '\')" href="javascript:void(0)">'
														+ rowData.POS_NAME
														+ '</a>';
											} else {
												btn = '<a class="choose_position_btn_class" width="100%" '
														+ ' onclick="openChoosePositionUIForMaterialBill(\''
														+ rowIndex
														+ '\',\''
														+ getTextBoxValue('storageIdTextInput')
														+ '\')" href="javascript:void(0)">选择仓位</a>';
											}
											return btn;
										}
									},
									{
										field : 'DETAIL_BILL_AMOUNT',
										title : '数量',
										width : 150,
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var toolAmount = "";
											if (typeof (rowData.DETAIL_BILL_AMOUNT) == 'undefined') {
												toolAmount = "";
											} else {
												toolAmount = rowData.DETAIL_BILL_AMOUNT;
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
								param.keyWord = getTextBoxValue('keyWordForMaterialBillTextInput');
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
		var rowData = getRowDataOfSelfDataGrid('datagridForMaterialBillDetail',
				rowIndex);
		rowData.DETAIL_BILL_AMOUNT = newValue;
	}

	//删除行数据
	function delRowData(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForMaterialBillDetail',
				rowIndex);
		var data = $('#datagridForMaterialBillDetail').datagrid('getData').rows;
		for (var i = 0; i < data.length; i++) {
			if (rowData.BASE_TOOL_ID == data[i].BASE_TOOL_ID) {
				data.splice(i, 1);
				break;
			}
		}

		dataGridLoadData('datagridForMaterialBillDetail', data);
	}

	//操作类型
	var opType = '';

	//记录新增或者修改的方法
	var url;

	var rowIndexOfDataGrid = 0;

	//编辑界面
	function toDetail(rowIndex) {
		if (rowIndex != null) {
			opType = 'edit';
			rowIndexOfDataGrid = rowIndex;
			var rowData = getRowDataOfSelfDataGrid('datagridForMaterialBill',
					rowIndex);
			queryMaterialBillDetailsForList(rowData);
			setTextBoxValue('materialBillCodeTextInput', rowData.BILL_CODE);
			setTextBoxValue('materialBillRemarkTextInput', rowData.BILL_REMARK);
			setTextBoxValue('storageIdTextInput', rowData.STORE_ID);
			$('#storageNameBtn').linkbutton({
				text : rowData.STORE_NAME,
				width : 200
			});
		} else {
			opType = 'add';
			queryNewMaterialBillCode();
		}
		$('#materialBillListUI').panel('collapse');
		$('#materialBillDetailUI').panel('expand');
	}

	//根据出入库单据id查询明细
	function queryNewMaterialBillCode() {
		var params = {
			BILL_TYPE : getTextBoxValue('billTypeTextInput')
		};
		query(params, 'queryNewMaterialBillCode.do',
				successFunctionForQueryMaterialBillCode);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryMaterialBillCode(result) {
		setTextBoxText('materialBillCodeTextInput', result);
		setTextBoxValue('materialBillCodeTextInput', result);
	}

	//根据出入库单据id查询明细
	function queryMaterialBillDetailsForList(rowData) {
		var params = {
			BILL_ID : rowData.BILL_ID,
		};
		query(params, 'queryMaterialBillDetailsForList.do',
				successFunctionForQueryMaterialBillDetails);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryMaterialBillDetails(result) {
		dataGridLoadData('datagridForMaterialBillDetail', result);
	}

	//列表界面
	function toList() {
		$('#materialBillListUI').panel('expand');
		$('#materialBillDetailUI').panel('collapse');
		rowIndexOfDataGrid = 0;
		setTextBoxValue('materialBillCodeTextInput', '');
		//清空明细列表
		dataGridLoadData('datagridForMaterialBillDetail', {
			total : 0,
			rows : []
		});
	}

	// 保存数据
	function saveMaterialBill() {
		var rowData = null;
		var params = null;
		var materialBillCode = getTextBoxValue('materialBillCodeTextInput');
		var materialBillRemark = getTextBoxValue('materialBillRemarkTextInput');
		var materialBillDetails = $('#datagridForMaterialBillDetail').datagrid(
				'getData').rows;
		if (materialBillDetails.length == 0) {
			alert('请选中工器具');
			return;
		}
		var baseToolIds = '';
		var baseToolPosIds = '';
		var baseToolAmountIds = '';
		for (var i = 0; i < materialBillDetails.length; i++) {
			if (materialBillDetails[i].POS_ID == null
					|| materialBillDetails[i].POS_ID == '') {
				alert('注意：第 ' + (i + 1) + ' 行请选择仓位');
				return;
			}
			baseToolIds += materialBillDetails[i].BASE_TOOL_ID + ',';
			baseToolPosIds += materialBillDetails[i].POS_ID + ',';
			baseToolAmountIds += materialBillDetails[i].DETAIL_BILL_AMOUNT
					+ ',';
		}
		baseToolIds = baseToolIds.substring(0, baseToolIds.length - 1);
		baseToolPosIds = baseToolPosIds.substring(0, baseToolPosIds.length - 1);
		baseToolAmountIds = baseToolAmountIds.substring(0,
				baseToolAmountIds.length - 1);

		if (opType == 'add') {
			params = {
				BILL_ID : '',
				BILL_TYPE : getTextBoxValue('billTypeTextInput'),
				STORE_ID : getTextBoxValue('storageIdTextInput'),
				BILL_CODE : materialBillCode,
				BILL_REMARK : materialBillRemark,
				BASE_TOOL_IDS : baseToolIds,
				BASE_TOOL_POS_IDS : baseToolPosIds,
				DETAIL_BILL_AMOUNTS : baseToolAmountIds
			};
			url = "addNewMaterialBillsAndDetails.do";
		} else if (opType == 'edit') {
			rowData = getRowDataOfSelfDataGrid('datagridForMaterialBill',
					rowIndexOfDataGrid);
			params = {
				BILL_ID : rowData.BILL_ID,
				BILL_TYPE : getTextBoxValue('billTypeTextInput'),
				STORE_ID : getTextBoxValue('storageIdTextInput'),
				BILL_CODE : materialBillCode,
				BILL_REMARK : materialBillRemark,
				BASE_TOOL_IDS : baseToolIds,
				BASE_TOOL_POS_IDS : baseToolPosIds,
				DETAIL_BILL_AMOUNTS : baseToolAmountIds
			};
			url = "updateMaterialBillsAndDetails.do";
		}
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave() {
		reloadDataGrid('datagridForMaterialBill');
		toList();
	}
</script>
</head>
<body>
	<div id="materialBillListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<table id="datagridForMaterialBill"
			class="easyui-datagrid">
		</table>
		<div id="toolbarForMaterialBill">
			<div style="display: none">
				<input id="billTypeTextInput" class="easyui-textbox"
					value="<%=request.getParameter("BILL_TYPE")%>" />
			</div>
			<table style="width: 100%">
				<tr>
					<td><a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true"
						onclick="refreshDataGrid('datagridForMaterialBill')">刷新</a>
						<a href="#" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="toDetail()">添加</a>
						<a href="#" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true"
						onclick="delMaterialBills()">删除</a></td>
					<td align="right"><input
						id="keyWordForMaterialBillTextInput"
						class="easyui-textbox"
						data-options="prompt:'批次号',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryMaterialBillPagesForSearch()">查询</a>
				</tr>
			</table>
		</div>
	</div>
	<div id="materialBillDetailUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<table id="datagridForMaterialBillDetail"
			class="easyui-datagrid">
		</table>
		<div id="toolbarForMaterialBillDetail">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-arrow_left" onclick="toList()">返回</a> <a
					href="#" class="easyui-linkbutton" iconCls="icon-ok"
					plain="true" onclick="saveMaterialBill()">保存</a><a
					href="#" class="easyui-linkbutton" iconCls="icon-add"
					plain="true"
					onclick="openChooseBaseToolUIForMaterialBill()">添加工器具</a>
			</div>
			<div>
				<table>
					<tr>
						<td>批次号： <input id="materialBillCodeTextInput"
							class="easyui-textbox"
							data-options="prompt:'批次号',validType:'length[0,50]',disabled:true"
							style="width: 200px"></td>
						<td>
							<div style="display: none">
								<input id="storageIdTextInput"
									class="easyui-textbox" />
							</div> 仓库：<a href="#" id="storageNameBtn"
							class="easyui-linkbutton" style="width: 200px;"
							onclick="openChooseStorageUIForMaterialBill()">
								选择仓库</a>
						</td>
						<td>备注： <input id="materialBillRemarkTextInput"
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