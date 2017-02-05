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
	function closeChooseStorageUIForMaterialBill() {
		closeUI('chooseStorageUIForMaterialBill');
	}

	//打开选择仓库窗口
	function openChooseStorageUIForMaterialBill() {
		createModalDialog("chooseStorageUIForMaterialBill",
				"openChooseStorageUI.do?opType=add", "选择仓库", 500, 500);
		openUI('chooseStorageUIForMaterialBill');
	}

	//关闭选择仓位窗口
	function closeChoosePositionUIForMaterialBill() {
		closeUI('choosePositionUIForMaterialBill');
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

	//用在点击查询按钮的时候
	function queryMaterialInventoryPagesForSearch() {
		queryMaterialInventorys();
	}

	//查询
	function queryMaterialInventorys() {
		var params = {
			POS_ID : getTextBoxValue('posIdTextInput'),
			STORE_ID : getTextBoxValue('storageIdTextInput'),
			keyWord : getTextBoxValue('keyWordForMaterialInventoryTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForMaterialInventory')
		};
		query(params, 'queryMaterialInventorysPage.do',
				successFunctionForQueryMaterialInventorys);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryMaterialInventorys(result) {
		dataGridLoadData('datagridForMaterialInventory', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput(
						'keyWordForMaterialInventoryTextInput',
						queryMaterialInventoryPagesForSearch);
				initDataGridForMaterialInventory();
				initDataGridForMaterialBillDetail();
			});

	//初始化列表元素
	function initDataGridForMaterialInventory() {
		$('#datagridForMaterialInventory')
				.datagrid(
						{
							url : 'queryMaterialInventorysPage.do',
							idField : 'BASE_TOOL_ID',
							rownumbers : true,
							toolbar : '#toolbarForMaterialInventory',
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
										title : '工器具名称',
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
										field : 'INVENT_AMOUNT',
										title : '数量',
										width : 100,
									} ] ],
							onBeforeLoad : function(param) {
								param.POS_ID = getTextBoxValue('posIdTextInput');
								param.STORE_ID = getTextBoxValue('storageIdTextInput');
								param.keyWord = getTextBoxValue('keyWordForMaterialInventoryTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForMaterialBillDetail() {
		$('#datagridForMaterialBillDetail').datagrid({
			idField : 'DETAIL_ID',
			rownumbers : true,
			toolbar : '#toolbarForMaterialBillDetail',
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
		var rowData = getRowDataOfSelfDataGrid('datagridForMaterialInventory',
				rowIndex);
		setTextBoxText('baseToolNameTextInput', rowData.BASE_TOOL_NAME);
		queryMaterialBillDetailsForPage(rowData);
		$('#materialInventoryListUI').panel('collapse');
		$('#materialBillDetailUI').panel('expand');
	}

	//根据模板id查询明细
	function queryMaterialBillDetailsForPage(rowData) {
		var params = {
			BASE_TOOL_ID : rowData.BASE_TOOL_ID,
			STORE_ID : rowData.STORE_ID,
			POS_ID : rowData.POS_ID,
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForMaterialBillDetail')
		};
		query(params, 'queryMaterialBillDetailsForPage.do',
				successFunctionForQueryMaterialBillDetails);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryMaterialBillDetails(result) {
		dataGridLoadData('datagridForMaterialBillDetail', result);
	}

	//列表界面
	function toList() {
		$('#materialInventoryListUI').panel('expand');
		$('#materialBillDetailUI').panel('collapse');
		rowIndexOfDataGrid = 0;
		setTextBoxText('baseToolNameTextInput', '');
		//清空明细列表
		dataGridLoadData('datagridForMaterialBillDetail', {
			total : 0,
			rows : []
		});
	}
</script>
</head>
<body>
	<div id="materialInventoryListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<div class="easyui-layout"
			data-options="fit:true,border:false">
			<div data-options="fit:true,border:false,region:'center'">
				<table id="datagridForMaterialInventory"
					class="easyui-datagrid">
				</table>
				<div id="toolbarForMaterialInventory">
					<table style="width: 100%">
						<tr>
							<td><a href="#" class="easyui-linkbutton"
								iconCls="icon-reload" plain="true"
								onclick="refreshDataGrid('datagridForMaterialInventory')">刷新</a>
							</td>
							<td align="center">
								<div style="display: none">
									<input id="storageIdTextInput"
										class="easyui-textbox" />
								</div> 仓库：<a href="#" id="storageNameBtn"
								class="easyui-linkbutton" style="width: 200px;"
								onclick="openChooseStorageUIForMaterialBill()">
									选择仓库</a>
							</td>
							<td align="center">
								<div style="display: none">
									<input id="posIdTextInput" class="easyui-textbox" />
								</div> 仓位：<a href="#" id="posNameBtn"
								class="easyui-linkbutton" style="width: 200px;"
								onclick="openChoosePositionUIForMaterialBill()">
									选择仓位</a>
							</td>
							<td align="right"><input
								id="keyWordForMaterialInventoryTextInput"
								class="easyui-textbox"
								data-options="prompt:'工器具',validType:'length[0,50]'"
								style="width: 200px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryMaterialInventoryPagesForSearch()">查询</a>
						</tr>
					</table>
				</div>
			</div>
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
					iconCls="icon-arrow_left" onclick="toList()">返回</a>
				工器具名称：<input id="baseToolNameTextInput"
					class="easyui-textbox" data-options="disabled:true"
					style="width: 200px">
			</div>
		</div>
	</div>
</body>
</html>