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
	function closeChooseStorageUIForPlugInBatch() {
		closeUI('chooseStorageUIForPlugInBatch');
	}

	//打开选择仓库窗口
	function openChooseStorageUIForPlugInBatch() {
		createModalDialog("chooseStorageUIForPlugInBatch",
				"openChooseStorageUI.do", "选择仓库", 500, 500);
		openUI('chooseStorageUIForPlugInBatch');
	}

	//关闭选择仓位窗口
	function closeChoosePositionUIForPlugInBatch() {
		closeUI('choosePositionUIForPlugInBatch');
	}

	//打开选择仓位窗口
	function openChoosePositionUIForPlugInBatch(rowIndex, storeId) {
		var storeId = getTextBoxValue('storageIdTextInput');
		if (storeId == null || storeId == '') {
			alert("请选择仓库");
			return;
		}
		createModalDialog("choosePositionUIForPlugInBatch",
				"openChoosePositionUI.do?rowIndex=" + rowIndex + "&STORE_ID="
						+ storeId, "选择仓库", 500, 500);
		openUI('choosePositionUIForPlugInBatch');
	}

	//用在点击查询按钮的时候
	function queryPlugInInventoryPagesForSearch() {
		queryPlugInInventorys();
	}

	//查询
	function queryPlugInInventorys() {
// 		alert(getComboBoxValue('baseToolManufacturerComboBox'))
// 		alert(getTextBoxValue('baseToolModelTextBox'))
// 		alert(getTextBoxValue('baseToolSpecTextBox'))
// 		alert(getTextBoxValue('posIdTextInput'))
// 		alert(getTextBoxValue('storageIdTextInput'))
// 		alert(getTextBoxValue('keyWordForPlugInInventoryTextInput'))
		var params = {
			MAN_ID : getComboBoxValue('baseToolManufacturerComboBox'),
			BASE_TOOL_MODEL : getTextBoxValue('baseToolModelTextBox'),
			BASE_TOOL_SPEC : getTextBoxValue('baseToolSpecTextBox'),
			POS_ID : getTextBoxValue('posIdTextInput'),
			STORE_ID : getTextBoxValue('storageIdTextInput'),
			keyWord : getTextBoxValue('keyWordForPlugInInventoryTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForPlugInInventory')
		};
		query(params, 'queryPlugInInventorysPage.do',
				successFunctionForQueryPlugInInventorys);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryPlugInInventorys(result) {
		dataGridLoadData('datagridForPlugInInventory', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput(
						'keyWordForPlugInInventoryTextInput',
						queryPlugInInventoryPagesForSearch);
				registerKeyPressForTextInput('baseToolSpecTextBox',
						queryPlugInInventoryPagesForSearch);
				registerKeyPressForTextInput('baseToolModelTextBox',
						queryPlugInInventoryPagesForSearch);
				initDataGridForPlugInInventory();
				initDataGridForPlugInTrack();
			});

	//初始化列表元素
	function initDataGridForPlugInInventory() {
		$('#datagridForPlugInInventory')
				.datagrid(
						{
							url : 'queryPlugInInventorysPage.do',
							idField : 'PLUGIN_ID',
							rownumbers : true,
							toolbar : '#plugInbarForPlugInInventory',
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
									}, {
										field : 'PLUGIN_CODE',
										title : '编码',
										width : 150,
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
										width : 100,
									}, {
										field : 'POS_NAME',
										title : '仓位',
										width : 100,
									}, {
										field : 'PLUGIN_STATUS_NAME',
										title : '状态',
										width : 100,
									} ] ],
							onBeforeLoad : function(param) {
								param.POS_ID = getTextBoxValue('posIdTextInput');
								param.STORE_ID = getTextBoxValue('storageIdTextInput');
								param.keyWord = getTextBoxValue('keyWordForPlugInInventoryTextInput');
								param.MAN_ID = getComboBoxValue('baseToolManufacturerComboBox');
								param.BASE_TOOL_MODEL = getTextBoxValue('baseToolModelTextBox');
								param.BASE_TOOL_SPEC = getTextBoxValue('baseToolSpecTextBox');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForPlugInTrack() {
		$('#datagridForPlugInTrack').datagrid({
			idField : 'TRACK_ID',
			rownumbers : true,
			toolbar : '#plugInbarForPlugInTrack',
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
				field : 'STORE_NAME',
				title : '仓库',
				width : 100,
			}, {
				field : 'POS_NAME',
				title : '仓位',
				width : 120,
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
		var rowData = getRowDataOfSelfDataGrid('datagridForPlugInInventory',
				rowIndex);
		setTextBoxText('baseToolNameTextInput', rowData.BASE_TOOL_NAME);
		queryPlugInTracksForPage(rowData);
		$('#plugInInventoryListUI').panel('collapse');
		$('#plugInTrackUI').panel('expand');
	}

	//根据模板id查询明细
	function queryPlugInTracksForPage(rowData) {
		var params = {
			PLUGIN_ID : rowData.PLUGIN_ID,
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForPlugInTrack')
		};
		query(params, 'queryPlugInTracksForPage.do',
				successFunctionForQueryPlugInTracks);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryPlugInTracks(result) {
		dataGridLoadData('datagridForPlugInTrack', result);
	}

	//列表界面
	function toList() {
		$('#plugInInventoryListUI').panel('expand');
		$('#plugInTrackUI').panel('collapse');
		rowIndexOfDataGrid = 0;
		setTextBoxText('baseToolNameTextInput', '');
		//清空明细列表
		dataGridLoadData('datagridForPlugInTrack', {
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
		setTextBoxValue('keyWordForPlugInInventoryTextInput', '');
		$('#storageNameBtn').linkbutton({
			text : '选择仓库',
			width : 200
		});
		$('#posNameBtn').linkbutton({
			text : '选择仓位',
			width : 200
		});
		refreshDataGrid('datagridForPlugInInventory');
	}
</script>
</head>
<body>
	<div id="plugInInventoryListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="fit:true,border:false,region:'center'">
				<table id="datagridForPlugInInventory" class="easyui-datagrid">
				</table>
				<div id="plugInbarForPlugInInventory">
					<table style="width: 100%">
						<tr>
							<td><a href="#" class="easyui-linkbutton"
								iconCls="icon-reload" plain="true" onclick="refresh()">刷新</a></td>
							<td></td>
							<td align="right"><input
								id="keyWordForPlugInInventoryTextInput" class="easyui-textbox"
								data-options="prompt:'插件编码',validType:'length[0,50]'"
								style="width: 200px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryPlugInInventoryPagesForSearch()">查询</a>
						</tr>
						<tr>
							<td align="left">
								<div style="display: none">
									<input id="storageIdTextInput" class="easyui-textbox" />
								</div> 仓库：<a href="#" id="storageNameBtn" class="easyui-linkbutton"
								style="width: 200px;"
								onclick="openChooseStorageUIForPlugInBatch()"> 选择仓库</a>
							</td>
							<td>
								<div style="display: none">
									<input id="posIdTextInput" class="easyui-textbox" />
								</div> 仓位：<a href="#" id="posNameBtn" class="easyui-linkbutton"
								style="width: 200px;"
								onclick="openChoosePositionUIForPlugInBatch()"> 选择仓位</a>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>厂家: <input id="baseToolManufacturerComboBox"
								data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '厂家',
							url : 'queryBasePlugInManufacturerDropList.do',
							onChange : function(newValue, oldValue){
								queryPlugInInventorys();
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
	<div id="plugInTrackUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<table id="datagridForPlugInTrack" class="easyui-datagrid">
		</table>
		<div id="plugInbarForPlugInTrack">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-arrow_left" onclick="toList()">返回</a> 插件名称：<input
					id="baseToolNameTextInput" class="easyui-textbox"
					data-options="disabled:true" style="width: 200px">
			</div>
		</div>
	</div>
</body>
</html>