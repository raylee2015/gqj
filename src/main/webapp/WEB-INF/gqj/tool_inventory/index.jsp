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
	//关闭选择仓库窗口
	function closeChooseStorageUIForToolBatch() {
		closeUI('chooseStorageUIForToolBatch');
	}

	//打开选择仓库窗口
	function openChooseStorageUIForToolBatch() {
		createModalDialog("chooseStorageUIForToolBatch",
				"openChooseStorageUI.do?opType=add", "选择仓库", 500, 500);
		openUI('chooseStorageUIForToolBatch');
	}

	//关闭选择仓位窗口
	function closeChoosePositionUIForToolBatch() {
		closeUI('choosePositionUIForToolBatch');
	}

	//打开选择仓位窗口
	function openChoosePositionUIForToolBatch(rowIndex, storeId) {
		var storeId = getTextBoxValue('storageIdTextInput');
		if (storeId == null || storeId == '') {
			alert("请选择仓库");
			return;
		}
		createModalDialog("choosePositionUIForToolBatch",
				"openChoosePositionUI.do?rowIndex=" + rowIndex + "&STORE_ID="
						+ storeId, "选择仓库", 500, 500);
		openUI('choosePositionUIForToolBatch');
	}

	//用在点击查询按钮的时候
	function queryToolInventoryPagesForSearch() {
		queryToolInventorys();
	}

	//查询
	function queryToolInventorys() {
		var params = {
			BASE_TOOL_TYPE_ID : getComboBoxValue('baseToolTypeComboBox'),
			MANUFACTURER_ID : getComboBoxValue('baseToolManufacturerComboBox'),
			BASE_TOOL_MODEL : getTextBoxValue('baseToolModelTextBox'),
			BASE_TOOL_SPEC : getTextBoxValue('baseToolSpecTextBox'),
			POS_ID : getTextBoxValue('posIdTextInput'),
			STORE_ID : getTextBoxValue('storageIdTextInput'),
			keyWord : getTextBoxValue('keyWordForToolInventoryTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForToolInventory')
		};
		query(params, 'queryToolInventorysPage.do',
				successFunctionForQueryToolInventorys);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryToolInventorys(result) {
		dataGridLoadData('datagridForToolInventory', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput(
						'keyWordForToolInventoryTextInput',
						queryToolInventoryPagesForSearch);
				registerKeyPressForTextInput('baseToolSpecTextBox',
						queryToolInventoryPagesForSearch);
				registerKeyPressForTextInput('baseToolModelTextBox',
						queryToolInventoryPagesForSearch);
				initDataGridForToolInventory();
				initDataGridForToolTrack();
			});

	//初始化列表元素
	function initDataGridForToolInventory() {
		$('#datagridForToolInventory')
				.datagrid(
						{
							url : 'queryToolInventorysPage.do?DATE_TYPE='
									+ getTextBoxValue('dateTypeTextInput'),
							idField : 'TOOL_ID',
							rownumbers : true,
							toolbar : '#toolbarForToolInventory',
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
													+ '\')" href="javascript:void(0)">查看批次明细</a>';
											return btn;
										}
									},
									{
										field : 'TOOL_CODE',
										title : '工器具编号',
										width : 100,
									},
									{
										field : 'BASE_TOOL_TYPE_NAME',
										title : '工器具类型',
										width : 100,
									},
									{
										field : 'BASE_TOOL_NAME',
										title : '工器具名称',
										width : 200,
									},
									{
										field : 'BASE_TOOL_MODEL',
										title : '型号',
										width : 100,
									},
									{
										field : 'BASE_TOOL_SPEC',
										title : '规格',
										width : 100,
									},
									{
										field : 'BASE_TOOL_MANUFACTURER_NAME',
										title : '厂家',
										width : 150,
									},
									{
										field : 'STORE_NAME',
										title : '仓库',
										width : 100,
									},
									{
										field : 'POS_NAME',
										title : '仓位',
										width : 150,
									},
									{
										field : 'TOOL_STATUS_NAME',
										title : '状态',
										width : 60,
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
								param.POS_ID = getTextBoxValue('posIdTextInput');
								param.STORE_ID = getTextBoxValue('storageIdTextInput');
								param.keyWord = getTextBoxValue('keyWordForToolInventoryTextInput');
								param.BASE_TOOL_TYPE_ID = getComboBoxValue('baseToolTypeComboBox');
								param.MANUFACTURER_ID = getComboBoxValue('baseToolManufacturerComboBox');
								param.BASE_TOOL_MODEL = getTextBoxValue('baseToolModelTextBox');
								param.BASE_TOOL_SPEC = getTextBoxValue('baseToolSpecTextBox');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForToolTrack() {
		$('#datagridForToolTrack').datagrid({
			idField : 'TRACK_ID',
			rownumbers : true,
			toolbar : '#toolbarForToolTrack',
			pageSize : 30,
			pageNumber : 1,
			pagination : true,
			checkOnSelect : false,
			fit : true,
			method : 'get',
			columns : [ [ {
				field : 'BATCH_CODE',
				title : '批次',
				width : 150,
			}, {
				field : 'BATCH_CONFIRM_TIME',
				title : '仓库',
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
		var rowData = getRowDataOfSelfDataGrid('datagridForToolInventory',
				rowIndex);
		setTextBoxText('baseToolNameTextInput', rowData.BASE_TOOL_NAME);
		queryToolTracksForPage(rowData);
		$('#toolInventoryListUI').panel('collapse');
		$('#toolTrackUI').panel('expand');
	}

	//根据模板id查询明细
	function queryToolTracksForPage(rowData) {
		var params = {
			TOOL_ID : rowData.TOOL_ID,
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForToolTrack')
		};
		query(params, 'queryToolTracksForPage.do',
				successFunctionForQueryToolTracks);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryToolTracks(result) {
		dataGridLoadData('datagridForToolTrack', result);
	}

	//列表界面
	function toList() {
		$('#toolInventoryListUI').panel('expand');
		$('#toolTrackUI').panel('collapse');
		rowIndexOfDataGrid = 0;
		setTextBoxText('baseToolNameTextInput', '');
		//清空明细列表
		dataGridLoadData('datagridForToolTrack', {
			total : 0,
			rows : []
		});
	}

	function refresh() {
		$('#baseToolTypeComboBox').combobox('clear');
		$('#baseToolManufacturerComboBox').combobox('clear');
		setTextBoxValue('baseToolModelTextBox', '');
		setTextBoxValue('baseToolSpecTextBox', '');
		setTextBoxValue('posIdTextInput', '');
		setTextBoxValue('storageIdTextInput', '');
		setTextBoxValue('keyWordForToolInventoryTextInput', '');
		$('#storageNameBtn').linkbutton({
			text : '选择仓库',
			width : 200
		});
		$('#posNameBtn').linkbutton({
			text : '选择仓位',
			width : 200
		});
		refreshDataGrid('datagridForToolInventory');
	}
</script>
</head>
<body>
	<div style="display: none">
		<input id="dateTypeTextInput" class="easyui-textbox"
			value="<%=request.getParameter("DATE_TYPE")%>" />
	</div>
	<div id="toolInventoryListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="fit:true,border:false,region:'center'">
				<table id="datagridForToolInventory" class="easyui-datagrid">
				</table>
				<div id="toolbarForToolInventory">
					<table style="width: 100%">
						<tr>
							<td><a href="#" class="easyui-linkbutton"
								iconCls="icon-reload" plain="true" onclick="refresh()">刷新</a></td>
							<td></td>
							<td align="right"><input
								id="keyWordForToolInventoryTextInput" class="easyui-textbox"
								data-options="prompt:'工器具',validType:'length[0,50]'"
								style="width: 200px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryToolInventoryPagesForSearch()">查询</a>
						</tr>
						<tr>
							<td align="left">
								<div style="display: none">
									<input id="storageIdTextInput" class="easyui-textbox" />
								</div> 仓库：<a href="#" id="storageNameBtn" class="easyui-linkbutton"
								style="width: 200px;"
								onclick="openChooseStorageUIForToolBatch()"> 选择仓库</a>
							</td>
							<td>
								<div style="display: none">
									<input id="posIdTextInput" class="easyui-textbox" />
								</div> 仓位：<a href="#" id="posNameBtn" class="easyui-linkbutton"
								style="width: 200px;"
								onclick="openChoosePositionUIForToolBatch()"> 选择仓位</a>
							</td>
							<td>类型: <input id="baseToolTypeComboBox"
								data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '工器具类型',
							url : 'queryBaseToolTypeDropList.do',
							onChange : function(newValue, oldValue){
								queryToolInventorys();
							}
							"
								class="easyui-combobox" style="width: 200px;"></td>
						</tr>
						<tr>
							<td>厂家: <input id="baseToolManufacturerComboBox"
								data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '厂家',
							url : 'queryBaseToolManufacturerDropList.do',
							onChange : function(newValue, oldValue){
								queryToolInventorys();
							}
							"
								class="easyui-combobox" style="width: 200px;"></td>
							<td>型号: <input id="baseToolModelTextBox"
								data-options="prompt : '型号'" class="easyui-textbox"
								style="width: 200px;"></td>
							<td>规格: <input id="baseToolSpecTextBox"
								data-options="prompt : '规格'" class="easyui-textbox"
								style="width: 200px;"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="toolTrackUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<table id="datagridForToolTrack" class="easyui-datagrid">
		</table>
		<div id="toolbarForToolTrack">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-arrow_left" onclick="toList()">返回</a> 工器具名称：<input
					id="baseToolNameTextInput" class="easyui-textbox"
					data-options="disabled:true" style="width: 200px">
			</div>
		</div>
	</div>
</body>
</html>