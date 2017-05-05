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
	//关闭选择仓库窗口
	function closeChooseStorageUIForAccessoryBill() {
		closeUI('chooseStorageUIForAccessoryBill');
	}

	//打开选择仓库窗口
	function openChooseStorageUIForAccessoryBill() {
		createModalDialog("chooseStorageUIForAccessoryBill",
				"openChooseStorageUI.do?opType=add", "选择仓库", 500, 500);
		openUI('chooseStorageUIForAccessoryBill');
	}

	//关闭选择仓位窗口
	function closeChoosePositionUIForAccessoryBill() {
		closeUI('choosePositionUIForAccessoryBill');
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

	//用在点击查询按钮的时候
	function queryAccessoryInventoryPagesForSearch() {
		queryAccessoryInventorys();
	}

	//查询
	function queryAccessoryInventorys() {
		var params = {
			MAN_ID : getComboBoxValue('baseToolManufacturerComboBox'),
			BASE_TOOL_MODEL : getTextBoxValue('baseToolModelTextBox'),
			BASE_TOOL_SPEC : getTextBoxValue('baseToolSpecTextBox'),
			BASE_TOOL_STATION : getTextBoxValue('baseToolStationTextBox'),
			POS_ID : getTextBoxValue('posIdTextInput'),
			STORE_ID : getTextBoxValue('storageIdTextInput'),
			keyWord : getTextBoxValue('keyWordForAccessoryInventoryTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForAccessoryInventory')
		};
		query(params, 'queryAccessoryInventorysPage.do',
				successFunctionForQueryAccessoryInventorys);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryAccessoryInventorys(result) {
		dataGridLoadData('datagridForAccessoryInventory', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput(
						'keyWordForAccessoryInventoryTextInput',
						queryAccessoryInventoryPagesForSearch);
				registerKeyPressForTextInput('baseToolSpecTextBox',
						queryAccessoryInventoryPagesForSearch);
				registerKeyPressForTextInput('baseToolModelTextBox',
						queryAccessoryInventoryPagesForSearch);
				registerKeyPressForTextInput('baseToolStationTextBox',
						queryAccessoryInventoryPagesForSearch);
				initDataGridForAccessoryInventory();
				initDataGridForAccessoryBillDetail();
			});

	//初始化列表元素
	function initDataGridForAccessoryInventory() {
		$('#datagridForAccessoryInventory')
				.datagrid(
						{
							url : 'queryAccessoryInventorysPage.do',
							idField : 'BASE_TOOL_ID',
							rownumbers : true,
							toolbar : '#toolbarForAccessoryInventory',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : false,
							fit : true,
							method : 'get',
							columns : [ [
									{
										field : 'op',
										title : '操作',
										align : 'center',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = '<a class="easyui-linkbutton" '
													+ ' onclick="toDetail(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">查看出入库明细</a>';
											return btn;
										}
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
										field : 'BASE_TOOL_UNIT',
										title : '单位',
										width : 100,
									}, {
										field : 'BASE_TOOL_STATION',
										title : '适用站',
										width : 150,
									}, {
										field : 'BASE_TOOL_REMARK',
										title : '备注',
										width : 150,
									}, {
										field : 'STORE_NAME',
										title : '仓库',
										width : 100,
									}, {
										field : 'POS_NAME',
										title : '仓位',
										width : 100,
									}, {
										field : 'INVENT_AMOUNT',
										title : '数量',
										width : 100,
									}, {
										field : 'DEPT_NAME',
										title : '所属部门',
										width : 100,
										hidden : true
									} ] ],
							onBeforeLoad : function(param) {
								param.POS_ID = getTextBoxValue('posIdTextInput');
								param.STORE_ID = getTextBoxValue('storageIdTextInput');
								param.keyWord = getTextBoxValue('keyWordForAccessoryInventoryTextInput');
								param.MAN_ID = getComboBoxValue('baseToolManufacturerComboBox');
								param.BASE_TOOL_MODEL = getTextBoxValue('baseToolModelTextBox');
								param.BASE_TOOL_SPEC = getTextBoxValue('baseToolSpecTextBox');
								param.BASE_TOOL_STATION = getTextBoxValue('baseToolStationTextBox');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForAccessoryBillDetail() {
		$('#datagridForAccessoryBillDetail').datagrid({
			idField : 'DETAIL_ID',
			rownumbers : true,
			toolbar : '#toolbarForAccessoryBillDetail',
			pageSize : 30,
			pageNumber : 1,
			pagination : true,
			checkOnSelect : false,
			fit : true,
			method : 'get',
			columns : [ [ {
				field : 'BILL_CODE',
				title : '批次',
				width : 200,
			}, {
				field : 'STORE_NAME',
				title : '仓库',
				width : 100,
			}, {
				field : 'POS_NAME',
				title : '仓位',
				width : 100,
			}, {
				field : 'DETAIL_BILL_AMOUNT',
				title : '数量',
				width : 100,
			} ] ],
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}

	var rowIndexOfDataGrid = 0;

	//编辑界面
	function toDetail(rowIndex) {
		rowIndexOfDataGrid = rowIndex;
		var rowData = getRowDataOfSelfDataGrid('datagridForAccessoryInventory',
				rowIndex);
		setTextBoxText('baseToolNameTextInput', rowData.BASE_TOOL_NAME);
		queryAccessoryBillDetailsForPage(rowData);
		$('#accessoryInventoryListUI').panel('collapse');
		$('#accessoryBillDetailUI').panel('expand');
	}

	//根据模板id查询明细
	function queryAccessoryBillDetailsForPage(rowData) {
		var params = {
			BASE_TOOL_ID : rowData.BASE_TOOL_ID,
			STORE_ID : rowData.STORE_ID,
			POS_ID : rowData.POS_ID,
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForAccessoryBillDetail')
		};
		query(params, 'queryAccessoryBillDetailsForPage.do',
				successFunctionForQueryAccessoryBillDetails);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryAccessoryBillDetails(result) {
		dataGridLoadData('datagridForAccessoryBillDetail', result);
	}

	//列表界面
	function toList() {
		$('#accessoryInventoryListUI').panel('expand');
		$('#accessoryBillDetailUI').panel('collapse');
		rowIndexOfDataGrid = 0;
		setTextBoxText('baseToolNameTextInput', '');
		//清空明细列表
		dataGridLoadData('datagridForAccessoryBillDetail', {
			total : 0,
			rows : []
		});
	}
</script>
</head>
<body>
	<div style="display: none">
		<input id="deptTypeTextInput" class="easyui-textbox"
			value="<%=request.getParameter("DEPT_TYPE")%>" />
	</div>
	<div id="accessoryInventoryListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<div class="easyui-layout"
			data-options="fit:true,border:false">
			<div data-options="fit:true,border:false,region:'center'">
				<table id="datagridForAccessoryInventory"
					class="easyui-datagrid">
				</table>
				<div id="toolbarForAccessoryInventory">
					<table style="width: 100%">
						<tr>
							<td><a href="#" class="easyui-linkbutton"
								iconCls="icon-reload" plain="true"
								onclick="refreshDataGrid('datagridForAccessoryInventory')">刷新</a>
							</td>
							<td></td>
							<td align="right"><input
								id="keyWordForAccessoryInventoryTextInput"
								class="easyui-textbox"
								data-options="prompt:'工器具',validType:'length[0,50]'"
								style="width: 200px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryAccessoryInventoryPagesForSearch()">查询</a>
						</tr>
						<tr>
							<td align="left">
								<div style="display: none">
									<input id="storageIdTextInput"
										class="easyui-textbox" />
								</div> 仓库：<a href="#" id="storageNameBtn"
								class="easyui-linkbutton" style="width: 200px;"
								onclick="openChooseStorageUIForAccessoryBill()">
									选择仓库</a>
							</td>
							<td>
								<div style="display: none">
									<input id="posIdTextInput" class="easyui-textbox" />
								</div> 仓位：<a href="#" id="posNameBtn"
								class="easyui-linkbutton" style="width: 200px;"
								onclick="openChoosePositionUIForAccessoryBill()">
									选择仓位</a>
							</td>
							<td>适用站: <input id="baseToolStationTextBox"
								data-options="prompt : '适用站'" class="easyui-textbox"
								style="width: 200px;"></td>
						</tr>
						<tr>
							<td>厂家: <input id="baseToolManufacturerComboBox"
								data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '厂家',
							url : 'queryBaseToolManufacturerDropList.do',
							onChange : function(newValue, oldValue){
								queryAccessoryInventorys();
							}
							"
								class="easyui-combobox" style="width: 200px;"></td>
							<td>型号: <input id="baseToolModelTextBox"
								data-options="prompt : '型号'" class="easyui-textbox"
								style="width: 200px;"></td>
							<td>参数: <input id="baseToolSpecTextBox"
								data-options="prompt : '规格'" class="easyui-textbox"
								style="width: 200px;"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="accessoryBillDetailUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<table id="datagridForAccessoryBillDetail"
			class="easyui-datagrid">
		</table>
		<div id="toolbarForAccessoryBillDetail">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-arrow_left" onclick="toList()">返回</a>
				工器具名称：<input id="baseToolNameTextInput"
					class="easyui-textbox" data-options="disabled:true"
					style="width: 200px">
			</div>
		</div>
	</div>
</body>
</html>