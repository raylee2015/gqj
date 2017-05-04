<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String billType = request.getParameter("BILL_TYPE");
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
	// 关闭编辑窗口
	function closeScanUIForAccessory() {
		closeEditUI('scanUIForAccessory')
	}

	//打开编辑窗口
	function openScanUIForAccessory() {
		createModalDialog("scanUIForAccessory", "openScanUI.do",
				"扫描添加配件", 400, 120);
		openEditUI('scanUIForAccessory');
	}

	//关闭选择部门窗口
	function closeChooseAccessoryInventoryUIForAccessoryBill() {
		closeUI('chooseAccessoryInventoryUIForAccessoryBill')
	}

	//打开选择部门窗口
	function openChooseAccessoryInventoryUIForAccessoryBill() {
		createModalDialog("chooseAccessoryInventoryUIForAccessoryBill",
				"openChooseAccessoryInventoryUI.do", "选择配件", 1000, 600);
		openUI('chooseAccessoryInventoryUIForAccessoryBill');
	}

	//关闭选择部门窗口
	function closeChooseDeptUIForAccessoryBill() {
		closeUI('chooseDeptUIForAccessoryBill')
	}

	//打开选择部门窗口
	function openChooseDeptUIForAccessoryBill() {
		createModalDialog("chooseDeptUIForAccessoryBill",
				"openChooseDeptUI.do", "选择部门", 500, 500);
		openUI('chooseDeptUIForAccessoryBill');
	}

	//关闭选择需求计划窗口
	function closeChooseDemandPlanUIForAccessoryBill() {
		closeUI('chooseDemandPlanUIForAccessoryBill')
	}

	//打开选择需求计划窗口
	function openChooseDemandPlanUIForAccessoryBill() {
		createModalDialog("chooseDemandPlanUIForAccessoryBill",
				"openChooseDemandPlanUI.do", "选择需求计划", 700, 500);
		openUI('chooseDemandPlanUIForAccessoryBill');
	}

	//关闭选择配件窗口
	function closeChooseBaseToolUIForAccessoryBill() {
		closeUI('chooseBaseToolUIForAccessoryBill')
	}

	//打开选择配件窗口
	function openChooseBaseToolUIForAccessoryBill() {
		createModalDialog("chooseBaseToolUIForAccessoryBill",
				"openChooseBaseToolUI.do?opType=add", "选择配件", 800, 600);
		openUI('chooseBaseToolUIForAccessoryBill');
	}

	//关闭选择仓库窗口
	function closeChooseStorageUIForAccessoryBill() {
		closeUI('chooseStorageUIForAccessoryBill')
	}

	//打开选择仓库窗口
	function openChooseStorageUIForAccessoryBill() {
		createModalDialog("chooseStorageUIForAccessoryBill",
				"openChooseStorageUI.do?opType=add", "选择仓库", 500, 500);
		openUI('chooseStorageUIForAccessoryBill');
	}

	//关闭选择仓位窗口
	function closeChoosePositionUIForAccessoryBill() {
		closeUI('choosePositionUIForAccessoryBill')
	}

	//打开选择仓位窗口
	function openChoosePositionUIForAccessoryBill(rowIndex, storeId) {
		var storeId = getTextBoxValue('storageIdTextInput');
		if (storeId == null || storeId == '') {
			alert("请选择仓库");
			return;
		}
		createModalDialog("choosePositionUIForAccessoryBill",
				"openChoosePositionUI.do?rowIndex=" + rowIndex + "&STORE_ID="
						+ storeId, "选择仓库", 500, 500);
		openUI('choosePositionUIForAccessoryBill');
	}

	//删除
	function delAccessoryBills() {
		if (checkSelectedItems('datagridForAccessoryBill', '请选择批次')) {
			var ids = getIdsOfSelectedItems('datagridForAccessoryBill',
					'BILL_ID');
			if (ids != null && ids != '') {
				var params = {
					BILL_IDS : ids
				};
				showMessageBox(params, 'delAccessoryBills.do', '是否删除所选批次?',
						successFunctionForOption);
			}
		}
	}

	//确认
	function confirmAccessoryBills() {
		if (checkSelectedItems('datagridForAccessoryBill', '请选择批次')) {
			var ids = getIdsOfSelectedItems('datagridForAccessoryBill',
					'BILL_ID');
			if (ids != null && ids != '') {
				var params = {
					BILL_IDS : ids,
					BILL_TYPE : getTextBoxValue('billTypeTextInput')
				};
				showMessageBox(params, 'confirmAccessoryBills.do', '是否确认所选批次?',
						successFunctionForOption);
			}
		}
	}

	//领用
	function takeAccessoryBills() {
		if (checkSelectedItems('datagridForAccessoryBill', '请选择批次')) {
			var ids = getIdsOfSelectedItems('datagridForAccessoryBill',
					'BILL_ID');
			if (ids != null && ids != '') {
				var params = {
					BILL_IDS : ids
				};
				showMessageBox(params, 'takeAccessoryBills.do', '是否领用所选批次?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForAccessoryBill');
	}

	//用在点击查询按钮的时候
	function queryAccessoryBillPagesForSearch() {
		queryAccessoryBills();
	}

	//查询
	function queryAccessoryBills() {
		var params = {
			BILL_TYPE : getTextBoxValue('billTypeTextInput'),
			keyWord : getTextBoxValue('keyWordForAccessoryBillTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForAccessoryBill')
		};
		query(params, 'queryAccessoryBillsPage.do',
				successFunctionForQueryAccessoryBills);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryAccessoryBills(result) {
		dataGridLoadData('datagridForAccessoryBill', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput(
						'keyWordForAccessoryBillTextInput',
						queryAccessoryBillPagesForSearch);
				initDataGridForAccessoryBill();
				initDataGridForAccessoryBillDetail();
			});

	//初始化列表元素
	function initDataGridForAccessoryBill() {
		$('#datagridForAccessoryBill')
				.datagrid(
						{
							url : 'queryAccessoryBillsPage.do?BILL_TYPE='
									+ getTextBoxValue('billTypeTextInput'),
							idField : 'BILL_ID',
							rownumbers : true,
							toolbar : '#toolbarForAccessoryBill',
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
											var billConfirmUserId = rowData.BILL_CONFIRM_USER_ID;
											var btn = '';
											if (billConfirmUserId == null) {
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
							onLoadSuccess : function(data) {
								var billType = getTextBoxValue('billTypeTextInput');
								if (billType != 5) {
									if (data.rows.length > 0) {
										//循环判断操作为新增的不能选择
										for (var i = 0; i < data.rows.length; i++) {
											//根据operate让某些行不可选
											if (data.rows[i].BILL_CONFIRM_USER_ID != null) {
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
										$("#datagridForAccessoryBill")
												.datagrid('uncheckRow', i);
									}
								}
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForAccessoryBillDetail() {
		$('#datagridForAccessoryBillDetail')
				.datagrid(
						{
							idField : 'DETAIL_ID',
							rownumbers : true,
							toolbar : '#toolbarForAccessoryBillDetail',
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
										field : 'BASE_TOOL_CODE',
										title : '工器具编码',
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
										field : 'BASE_TOOL_REMARK',
										title : '备注',
										width : 150,
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
														+ ' onclick="openChoosePositionUIForAccessoryBill(\''
														+ rowIndex
														+ '\',\''
														+ getTextBoxValue('storageIdTextInput')
														+ '\')" href="javascript:void(0)">'
														+ rowData.POS_NAME
														+ '</a>';
											} else {
												btn = '<a class="choose_position_btn_class" width="100%" '
														+ ' onclick="openChoosePositionUIForAccessoryBill(\''
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
								param.keyWord = getTextBoxValue('keyWordForAccessoryBillTextInput');
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

	//设置配件数量
	function setToolAmount(rowIndex, newValue) {
		var rowData = getRowDataOfSelfDataGrid(
				'datagridForAccessoryBillDetail', rowIndex);
		if (getTextBoxValue('billTypeTextInput') == 1
				|| getTextBoxValue('billTypeTextInput') == 2) {
			if (newValue > rowData.INVENT_AMOUNT) {
				alert('输入数量大于现有库存，请重新输入')
			}
		}
		rowData.DETAIL_BILL_AMOUNT = newValue;
	}

	//删除行数据
	function delRowData(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid(
				'datagridForAccessoryBillDetail', rowIndex);
		var data = $('#datagridForAccessoryBillDetail').datagrid('getData').rows;
		for (var i = 0; i < data.length; i++) {
			if (rowData.BASE_TOOL_ID == data[i].BASE_TOOL_ID) {
				data.splice(i, 1);
				break;
			}
		}

		dataGridLoadData('datagridForAccessoryBillDetail', data);
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
			var rowData = getRowDataOfSelfDataGrid('datagridForAccessoryBill',
					rowIndex);
			var billConfirmUserId = rowData.BILL_CONFIRM_USER_ID;
			opType = 'edit';
			queryAccessoryBillDetailsForList(rowData);
			setTextBoxValue('accessoryBillCodeTextInput', rowData.BILL_CODE);
			setTextBoxValue('accessoryBillRemarkTextInput', rowData.BILL_REMARK);
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

			if (billConfirmUserId != null) {
				$('#saveBtn').linkbutton({
					disabled : true
				});
				$('#addToolsBtn').linkbutton({
					disabled : true
				});
			}
		} else {
			opType = 'add';
			queryNewAccessoryBillCode();
		}
		$('#accessoryBillListUI').panel('collapse');
		$('#accessoryBillDetailUI').panel('expand');
	}

	//根据出入库单据id查询明细
	function queryNewAccessoryBillCode() {
		var params = {
			BILL_TYPE : getTextBoxValue('billTypeTextInput')
		};
		query(params, 'queryNewAccessoryBillCode.do',
				successFunctionForQueryAccessoryBillCode);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryAccessoryBillCode(result) {
		setTextBoxText('accessoryBillCodeTextInput', result);
		setTextBoxValue('accessoryBillCodeTextInput', result);
	}

	//根据出入库单据id查询明细
	function queryAccessoryBillDetailsForList(rowData) {
		var params = {
			BILL_ID : rowData.BILL_ID,
		};
		query(params, 'queryAccessoryBillDetailsForList.do',
				successFunctionForQueryAccessoryBillDetails);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryAccessoryBillDetails(result) {
		dataGridLoadData('datagridForAccessoryBillDetail', result);
	}

	//列表界面
	function toList() {
		$('#accessoryBillListUI').panel('expand');
		$('#accessoryBillDetailUI').panel('collapse');
		rowIndexOfDataGrid = 0;
		setTextBoxValue('accessoryBillCodeTextInput', '');
		setTextBoxValue('accessoryBillRemarkTextInput', '');
		setTextBoxValue('storageIdTextInput', '');
		setTextBoxValue('planIdTextInput', '');
		//清空明细列表
		dataGridLoadData('datagridForAccessoryBillDetail', {
			total : 0,
			rows : []
		});
		$('#saveBtn').linkbutton({
			disabled : false
		});
		$('#addToolsBtn').linkbutton({
			disabled : false
		});
		$('#storageNameBtn').linkbutton({
			text : '选择仓库'
		});
		$('#planNameBtn').linkbutton({
			text : '选择需求计划 '
		});
	}

	// 保存数据
	function saveAccessoryBill() {
		var rowData = null;
		var params = null;
		var accessoryBillCode = getTextBoxValue('accessoryBillCodeTextInput');
		var accessoryBillRemark = getTextBoxValue('accessoryBillRemarkTextInput');
		var accessoryBillDetails = $('#datagridForAccessoryBillDetail')
				.datagrid('getData').rows;
		if (accessoryBillDetails.length == 0) {
			alert('请选中配件');
			return;
		}
		var baseToolIds = '';
		var baseToolPosIds = '';
		var baseToolAmountIds = '';
		for (var i = 0; i < accessoryBillDetails.length; i++) {
			if (accessoryBillDetails[i].POS_ID == null
					|| accessoryBillDetails[i].POS_ID == '') {
				alert('注意：第 ' + (i + 1) + ' 行请选择仓位');
				return;
			}
			baseToolIds += accessoryBillDetails[i].BASE_TOOL_ID + ',';
			baseToolPosIds += accessoryBillDetails[i].POS_ID + ',';
			baseToolAmountIds += accessoryBillDetails[i].DETAIL_BILL_AMOUNT
					+ ',';
		}
		baseToolIds = baseToolIds.substring(0, baseToolIds.length - 1);
		baseToolPosIds = baseToolPosIds.substring(0, baseToolPosIds.length - 1);
		baseToolAmountIds = baseToolAmountIds.substring(0,
				baseToolAmountIds.length - 1);
		//因为在入库的时候没有领用部门，因此需要特别处理
		var takeDeptId = "";
		if (getTextBoxValue('billTypeTextInput') == 1
				|| getTextBoxValue('billTypeTextInput') == 2) {
			takeDeptId = getTextBoxValue('deptIdTextInput');
		}
		if (opType == 'add') {
			params = {
				BILL_ID : '',
				BILL_TYPE : getTextBoxValue('billTypeTextInput'),
				STORE_ID : getTextBoxValue('storageIdTextInput'),
				BILL_TAKE_DEPT_ID : takeDeptId,
				BILL_CODE : accessoryBillCode,
				BILL_REMARK : accessoryBillRemark,
				BASE_TOOL_IDS : baseToolIds,
				BASE_TOOL_POS_IDS : baseToolPosIds,
				DETAIL_BILL_AMOUNTS : baseToolAmountIds
			};
			url = "addNewAccessoryBillsAndDetails.do";
		} else if (opType == 'edit') {
			rowData = getRowDataOfSelfDataGrid('datagridForAccessoryBill',
					rowIndexOfDataGrid);
			params = {
				BILL_ID : rowData.BILL_ID,
				PLAN_ID : planId,
				BILL_TYPE : getTextBoxValue('billTypeTextInput'),
				STORE_ID : getTextBoxValue('storageIdTextInput'),
				BILL_TAKE_DEPT_ID : takeDeptId,
				BILL_CODE : accessoryBillCode,
				BILL_REMARK : accessoryBillRemark,
				BASE_TOOL_IDS : baseToolIds,
				BASE_TOOL_POS_IDS : baseToolPosIds,
				DETAIL_BILL_AMOUNTS : baseToolAmountIds
			};
			url = "updateAccessoryBillsAndDetails.do";
		}
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave() {
		reloadDataGrid('datagridForAccessoryBill');
		toList();
	}

	//导出编号表格
	function exportTools() {
		var dataGrid = $('#datagridForAccessoryBill');
		var rowDatas = dataGrid.datagrid('getChecked');
		for (var i = 0; i < rowDatas.length; i++) {
			var item = rowDatas[i];
			if (typeof (item.BILL_TAKE_USER_ID) == 'undefined') {
				alert('所选批次位领用，请确认');
				return;
			}
		}
		if (checkSelectedItems('datagridForAccessoryBill', '请选择批次')) {
			var ids = getIdsOfSelectedItems('datagridForAccessoryBill',
					'BILL_ID');
			if (ids != null && ids != '') {
				$.messager
						.confirm(
								'确认',
								"是否导出所选批次？",
								function(confirmOrNot) {
									if (confirmOrNot) {
										window.location.href = getTextBoxValue("contextPath")
												+ "/gqj/accessory_bill/exportTools.do?BILL_IDS="
												+ ids;
										reloadDataGrid('datagridForAccessoryBill');
									}
								});
			}
		}
	}
</script>
</head>
<body>

	<div id="accessoryBillListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<table id="datagridForAccessoryBill" class="easyui-datagrid">
		</table>
		<div id="toolbarForAccessoryBill">
			<div style="display: none">
				<input id="billTypeTextInput" class="easyui-textbox"
					value="<%=request.getParameter("BILL_TYPE")%>" /> <input
					id="contextPath" class="easyui-textbox" value="<%=contextPath%>" />
			</div>
			<table style="width: 100%">
				<tr>
					<td><a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true"
						onclick="refreshDataGrid('datagridForAccessoryBill')">刷新</a> <%
 	if (!"5".equals(billType)) {
 %> <a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true" onclick="toDetail()">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delAccessoryBills()">删除</a><a href="#"
						class="easyui-linkbutton" iconCls="icon-application_go"
						plain="true" onclick="confirmAccessoryBills()">确认</a> <%
 	} else {
 %> <a href="#" class="easyui-linkbutton" iconCls="icon-application_go"
						plain="true" onclick="takeAccessoryBills()">领用</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-base-download"
						plain="true" onclick="exportTools()">导出配件列表</a> <%
 	}
 %></td>
					<td align="right"><input id="keyWordForAccessoryBillTextInput"
						class="easyui-textbox"
						data-options="prompt:'批次号',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryAccessoryBillPagesForSearch()">查询</a>
				</tr>
			</table>
		</div>
	</div>
	<div id="accessoryBillDetailUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<table id="datagridForAccessoryBillDetail" class="easyui-datagrid">
		</table>

		<div id="toolbarForAccessoryBillDetail">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-arrow_left" onclick="toList()">返回</a> <a id="saveBtn"
					href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
					onclick="saveAccessoryBill()">保存</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-add" plain="true"
					onclick="openScanUIForAccessory()">扫描配件</a>
				<%
					if ("0".equals(billType)) {
				%>
				<a id="addToolsBtn" href="#" class="easyui-linkbutton"
					iconCls="icon-add" plain="true"
					onclick="openChooseBaseToolUIForAccessoryBill()">添加配件</a>
				<%
					} else {
				%>
				<a id="addToolsBtn" href="#" class="easyui-linkbutton"
					iconCls="icon-add" plain="true"
					onclick="openChooseAccessoryInventoryUIForAccessoryBill()">添加配件</a>
				<%
					}
				%>
			</div>
			<div>
				<table>
					<tr>
						<td>批次号： <input id="accessoryBillCodeTextInput"
							class="easyui-textbox"
							data-options="prompt:'批次号',validType:'length[0,50]',disabled:true"
							style="width: 200px"></td>
						<td>
							<div style="display: none">
								<input id="storageIdTextInput" class="easyui-textbox" />
							</div> 仓库：<a href="#" id="storageNameBtn" class="easyui-linkbutton"
							style="width: 200px;"
							onclick="openChooseStorageUIForAccessoryBill()"> 选择仓库</a>
						</td>

						<%
							if ("1".equals(billType)) {
						%>
						<td>
							<div style="display: none">
								<input id="storageIdTextInput" class="easyui-textbox" />
							</div> 变电站：<a href="#" id="storageNameBtn" class="easyui-linkbutton"
							style="width: 200px;"
							onclick="openChooseStorageUIForAccessoryBill()"> 选择变电站</a>
						</td>
						<td>
							<div style="display: none">
								<input id="storageIdTextInput" class="easyui-textbox" />
							</div> 变电站区域间隔：<a href="#" id="storageNameBtn"
							class="easyui-linkbutton" style="width: 200px;"
							onclick="openChooseStorageUIForAccessoryBill()"> 选择变电站区域间隔</a>
						</td>
						<%
							}
						%>
						<td>备注： <input id="accessoryBillRemarkTextInput"
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