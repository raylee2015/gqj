<%@ page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String opType = request.getParameter("OP_TYPE");
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
	//页面加载完
	$(document).ready(
			function() {
				closeCache();

				initDataGridForStorage();
				registerKeyPressForTextInput('keyWordForStorageTextInput',
						queryStoragePagesForSearch);

				initDataGridForPosition();
				registerKeyPressForTextInput('keyWordForPositionTextInput',
						queryPositionPagesForSearch);

				initDataGridForBaseTool();
				registerKeyPressForTextInput('keyWordForBaseToolTextInput',
						queryBaseToolPagesForSearch);
				registerKeyPressForTextInput('baseToolSpecTextBox',
						queryBaseToolPagesForSearch);
				registerKeyPressForTextInput('baseToolModelTextBox',
						queryBaseToolPagesForSearch);
				initToolForm();
			});

	//初始化表单
	function initToolForm() {
		var url = "updateManufacturer.do";
		var rowIndex = getTextBoxValue('rowIndex');
		var rowData = getRowDataOfParentDataGrid('datagridForToolInventory',
				rowIndex);
		setTextBoxText("toolIdTextInput", rowData.TOOL_ID);
		setTextBoxValue("toolIdTextInput", rowData.TOOL_ID);
		setTextBoxText("storageIdTextInput", rowData.STORE_ID);
		setTextBoxValue("storageIdTextInput", rowData.STORE_ID);
		setTextBoxText("positionIdTextInput", rowData.POS_ID);
		setTextBoxValue("positionIdTextInput", rowData.POS_ID);
		setTextBoxText("baseToolIdTextInput", rowData.BASE_TOOL_ID);
		setTextBoxValue("baseToolIdTextInput", rowData.BASE_TOOL_ID);
		setTextBoxText("toolCodeTextInput", rowData.TOOL_CODE);
		setTextBoxValue("toolCodeTextInput", rowData.TOOL_CODE);
		setTextBoxText("toolBoxTextInput", rowData.TOOL_BOX);
		setTextBoxValue("toolBoxTextInput", rowData.TOOL_BOX);
		setTextBoxText("toolRemarkTextInput", rowData.TOOL_REMARK);
		setTextBoxValue("toolRemarkTextInput", rowData.TOOL_REMARK);
		setTextBoxText("toolTestDateCircleTextInput",
				rowData.TOOL_TEST_DATE_CIRCLE);
		setTextBoxValue("toolTestDateCircleTextInput",
				rowData.TOOL_TEST_DATE_CIRCLE);

		$('#storageNameBtn').linkbutton({
			text : rowData.STORE_NAME
		});
		$('#positionNameBtn').linkbutton({
			text : rowData.POS_NAME
		});
		$('#baseToolNameBtn').linkbutton({
			text : rowData.BASE_TOOL_NAME
		});

		$('#toolManufactureDateBox').datebox('setValue',
				rowData.TOOL_MANUFACTURE_DATE);
		$('#toolPurchaseDateBox').datebox('setValue',
				rowData.TOOL_PURCHASE_DATE);
		$('#toolTestDateBox').datebox('setValue', rowData.TOOL_TEST_DATE);
		$('#toolRejectDateBox').datebox('setValue', rowData.TOOL_REJECT_DATE);

		$('#toolManufactureDateBox').datebox('setText',
				rowData.TOOL_MANUFACTURE_DATE);
		$('#toolPurchaseDateBox')
				.datebox('setText', rowData.TOOL_PURCHASE_DATE);
		$('#toolTestDateBox').datebox('setText', rowData.TOOL_TEST_DATE);
		$('#toolRejectDateBox').datebox('setText', rowData.TOOL_REJECT_DATE);
	}

	//用在点击查询按钮的时候
	function queryStoragePagesForSearch() {
		queryStorages();
	}

	//查询
	function queryStorages() {
		var params = {
			keyWord : getTextBoxValue('keyWordForStorageTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForStorage')
		};
		query(params, 'queryStoragesPage.do', successFunctionForQueryStorages);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryStorages(result) {
		dataGridLoadData('datagridForStorage', result);
	}

	//初始化列表元素
	function initDataGridForStorage() {
		$('#datagridForStorage').datagrid({
			url : 'queryStoragesPage.do',
			idField : 'STORE_ID',
			rownumbers : true,
			toolbar : '#toolbarForStorage',
			pagination : true,
			pageSize : 30,
			pageNumber : 1,
			checkOnSelect : false,
			fit : true,
			method : 'get',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'STORE_NAME',
				title : '仓库名称',
				width : 150
			} ] ],
			onBeforeLoad : function(param) {
				param.keyWord = getTextBoxValue('keyWordForStorageTextInput');
			},
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}

	function chooseStorage() {
		var selectedItems = $('#datagridForStorage').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择仓库");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个仓库");
			return;
		}
		setTextBoxText('storageIdTextInput', selectedItems[0].STORE_ID);
		setTextBoxValue('storageIdTextInput', selectedItems[0].STORE_ID);
		$('#storageNameBtn').linkbutton({
			text : selectedItems[0].STORE_NAME,
		});
		openFormPanel();
	}

	//用在点击查询按钮的时候
	function queryPositionPagesForSearch() {
		queryPositions();
	}

	//查询
	function queryPositions() {
		var params = {
			STORE_ID : getTextBoxValue('storageIdTextInput'),
			keyWord : getTextBoxValue('keyWordForPositionTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForPosition')
		};
		query(params, 'queryPositionsPage.do', successFunctionForQueryPositions);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryPositions(result) {
		dataGridLoadData('datagridForPosition', result);
	}

	//初始化列表元素
	function initDataGridForPosition() {
		$('#datagridForPosition').datagrid({
			idField : 'POS_ID',
			rownumbers : true,
			toolbar : '#toolbarForPosition',
			pagination : true,
			pageSize : 30,
			pageNumber : 1,
			checkOnSelect : false,
			fit : true,
			method : 'get',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'POS_NAME',
				title : '仓位名称',
				width : 150
			} ] ],
			onBeforeLoad : function(param) {
				param.keyWord = getTextBoxValue('keyWordForPositionTextInput');
			},
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}

	function choosePosition() {
		var selectedItems = $('#datagridForPosition').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择仓位");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个仓位");
			return;
		}
		setTextBoxText('positionIdTextInput', selectedItems[0].POS_ID);
		setTextBoxValue('positionIdTextInput', selectedItems[0].POS_ID);
		$('#positionNameBtn').linkbutton({
			text : selectedItems[0].POS_NAME,
		});
		openFormPanel();
	}

	//用在点击查询按钮的时候
	function queryBaseToolPagesForSearch() {
		queryBaseTools();
	}

	//查询工器具基础信息
	function queryBaseTools() {
		var params = {
			BASE_TOOL_TYPE_ID : getComboBoxValue('baseToolTypeComboBox'),
			MANUFACTURER_ID : getComboBoxValue('baseToolManufacturerComboBox'),
			keyWord : getTextBoxValue('keyWordForBaseToolTextInput'),
			BASE_TOOL_MODEL : getTextBoxValue('baseToolModelTextBox'),
			BASE_TOOL_SPEC : getTextBoxValue('baseToolSpecTextBox'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForBaseTool')
		};
		query(params, 'queryBaseToolsPage.do', successFunctionForQueryBaseTools);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryBaseTools(result) {
		dataGridLoadData('datagridForBaseTool', result);
	}

	//初始化列表元素
	function initDataGridForBaseTool() {
		$('#datagridForBaseTool')
				.datagrid(
						{
							url : 'queryBaseToolsPage.do',
							idField : 'TOOL_ID',
							rownumbers : true,
							toolbar : '#toolbarForBaseTool',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : false,
							fit : true,
							method : 'get',
							columns : [ [ {
								field : 'ck',
								checkbox : true
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
							} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForBaseToolTextInput');
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

	function chooseBaseTool() {
		var selectedItems = $('#datagridForBaseTool').datagrid('getChecked');
		setTextBoxValue('baseToolIdTextInput', selectedItems[0].BASE_TOOL_ID);
		$('#baseToolNameBtn').linkbutton({
			text : selectedItems[0].BASE_TOOL_NAME,
		});
		openFormPanel();
	}

	//打开表单版面
	function openFormPanel() {
		$('#formPanel').panel('expand');
		$('#chooseBaseToolPanel').panel('collapse');
		$('#choosePositionPanel').panel('collapse');
		$('#chooseStoragePanel').panel('collapse');
	}

	//打开选择工器具类型版面
	function openChooseBaseToolPanel() {
		$('#formPanel').panel('collapse');
		$('#chooseBaseToolPanel').panel('expand');
		$('#choosePositionPanel').panel('collapse');
		$('#chooseStoragePanel').panel('collapse');
	}

	//打开选择仓位版面
	function openChoosePositionPanel() {
		var storeId = getTextBoxValue('storageIdTextInput');
		if (storeId == null || storeId == '') {
			alert("请先选择仓库");
			return;
		}
		queryPositions();
		$('#formPanel').panel('collapse');
		$('#chooseBaseToolPanel').panel('collapse');
		$('#choosePositionPanel').panel('expand');
		$('#chooseStoragePanel').panel('collapse');
	}

	//打开选择仓库版面
	function openChooseStoragePanel() {
		$('#formPanel').panel('collapse');
		$('#chooseBaseToolPanel').panel('collapse');
		$('#choosePositionPanel').panel('collapse');
		$('#chooseStoragePanel').panel('expand');
	}

	// 保存数据
	function saveBatch() {
		var toolId = getTextBoxValue('toolIdTextInput');
		var toolCode = getTextBoxValue('toolCodeTextInput');
		var toolTestDateCircle = getTextBoxValue('toolTestDateCircleTextInput');
		var toolRejectDate = getDateBoxValue('toolRejectDateBox');
		var toolTestDate = getDateBoxValue('toolTestDateBox');
		var toolManufactureDate = getDateBoxValue('toolManufactureDateBox');
		var toolPurchaseDate = getDateBoxValue('toolPurchaseDateBox');
		var baseToolId = getTextBoxValue('baseToolIdTextInput');
		var storeId = getTextBoxValue('storageIdTextInput');
		var positionId = getTextBoxValue('positionIdTextInput');
		var toolBox = getTextBoxValue('toolBoxTextInput');
		var toolRemark = getTextBoxValue('toolRemarkTextInput');
		
		var params = {
			TOOL_ID : toolId,
			TOOL_CODE : toolCode,
			TOOL_TEST_DATE : toolTestDate,
			TOOL_REJECT_DATE : toolRejectDate,
			TOOL_MANUFACTURE_DATE : toolManufactureDate,
			TOOL_PURCHASE_DATE : toolPurchaseDate,
			TOOL_TEST_DATE_CIRCLE : toolTestDateCircle,
			BASE_TOOL_ID : baseToolId,
			STORE_ID : storeId,
			POS_ID : positionId,
			TOOL_BOX : toolBox,
			TOOL_REMARK : toolRemark
		};
		url = "updateTool.do";
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		parent.successFunctionForOption(result);
		closeEditToolUIForBatch();
	}

	//关闭编辑窗口
	function closeEditToolUIForBatch() {
		parent.refresh();
		parent.closeEditToolUIForBatch();
	}
</script>
</head>
<body>
	<div id="formPanel" class="easyui-panel"
		data-options="fit:true">
		<form id="toolForm" method="post" style="width: 100%;">
			<div style="display: none">
				<input id="opTypeTextInput" class="easyui-textbox"
					value="<%=request.getParameter("OP_TYPE")%>" /> <input
					id="toolIdTextInput" class="easyui-textbox"
					name="TOOL_ID" /> <input id="storageIdTextInput"
					class="easyui-textbox" name="STORE_ID" /> <input
					id="positionIdTextInput" class="easyui-textbox"
					name="POS_ID" /><input id="baseToolIdTextInput"
					class="easyui-textbox" name="BASE_TOOL_ID" /><input
					id="rowIndex" class="easyui-textbox"
					value="<%=request.getParameter("rowIndex")%>" />
			</div>
			<table style="width: 100%">
				<tr>
					<td width="18%">工器具编号:</td>
					<td><input id="toolCodeTextInput" name="TOOL_CODE"
						class="easyui-textbox"
						data-options="required:true,validType:'length[0,50]',disabled:true"
						style="width: 100%; height: 32px" /></td>
				</tr>
				<tr>
					<td width="18%">选择仓库:</td>
					<td><a href="#" id="storageNameBtn"
						name="STORE_NAME" class="easyui-linkbutton"
						data-options="required:true,prompt:'选择仓库'"
						onclick="openChooseStoragePanel()"
						style="width: 100%; height: 32px">选择仓库</a></td>
				</tr>
				<tr>
					<td width="18%">选择仓位:</td>
					<td><a href="#" id="positionNameBtn"
						name="POS_NAME" class="easyui-linkbutton"
						data-options="required:true,prompt:'选择仓位'"
						onclick="openChoosePositionPanel()"
						style="width: 100%; height: 32px">选择仓位</a></td>
				</tr>
				<tr>
					<td width="18%">箱号:</td>
					<td><input id="toolBoxTextInput" name="TOOL_BOX"
						class="easyui-textbox"
						data-options="required:true,validType:'length[0,200]'"
						style="width: 100%; height: 32px" /></td>
				</tr>
				<tr>
					<td width="18%">工器具类型:</td>
					<td><a href="#" id="baseToolNameBtn"
						class="easyui-linkbutton"
						data-options="required:true,prompt:'选择工器具类型'"
						onclick="openChooseBaseToolPanel()"
						style="width: 100%; height: 32px">选择工器具类型</a></td>
				</tr>
				<tr>
					<td width="18%">出厂日期:</td>
					<td><input id="toolManufactureDateBox"
						name="TOOL_MANUFACTURE_DATE" class="easyui-datebox"
						data-options="required:true"
						style="width: 100%; height: 32px" /></td>
				</tr>
				<tr>
					<td width="18%">购买日期:</td>
					<td><input id="toolPurchaseDateBox"
						name="TOOL_PURCHASE_DATE" class="easyui-datebox"
						data-options="required:true"
						style="width: 100%; height: 32px" /></td>
				</tr>
				<tr>
					<td width="18%">初试日期:</td>
					<td><input id="toolTestDateBox"
						name="TOOL_TEST_DATE" class="easyui-datebox"
						data-options="required:true"
						style="width: 100%; height: 32px" /></td>
				</tr>
				<tr>
					<td width="18%">报废日期:</td>
					<td><input id="toolRejectDateBox"
						name="TOOL_REJECT_DATE" class="easyui-datebox"
						data-options="required:true"
						style="width: 100%; height: 32px" /></td>
				</tr>
				<tr>
					<td width="18%">试验周期:</td>
					<td><input id="toolTestDateCircleTextInput"
						class="easyui-textbox" data-options="required:true"
						style="width: 96%; height: 32px" />月</td>
				</tr>
				<tr>
					<td width="18%">备注:</td>
					<td><input id="toolRemarkTextInput"
						name="TOOL_REMARK" class="easyui-textbox"
						data-options="validType:'length[0,200]'"
						style="width: 100%; height: 32px" /></td>
				</tr>
				<tr>
					<td width="100%" align="right" colspan="2"><a
						href="#" id="saveBtn" class="easyui-linkbutton"
						onclick="saveBatch()"
						style="width: 60px; height: 32px">保存</a><a href="#"
						id="closeBtn" class="easyui-linkbutton"
						onclick="closeEditToolUIForBatch()"
						style="width: 100px; height: 32px">关闭并刷新列表</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="chooseBaseToolPanel" class="easyui-panel"
		data-options="fit:true">
		<table id="datagridForBaseTool" class="easyui-datagrid">
		</table>
		<div id="toolbarForBaseTool">
			<table style="width: 100%">
				<tr>
					<td><a class="easyui-linkbutton" iconCls="icon-ok"
						href="javascript:void(0)" onclick="chooseBaseTool()">选择</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel"
						href="javascript:void(0)" onclick="openFormPanel()">返回</a></td>
					<td align="right"><input
						id="keyWordForBaseToolTextInput"
						class="easyui-textbox"
						data-options="prompt:'工器具名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryBaseToolPagesForSearch()">查询</a>
				</tr>
				<tr>
					<td>类型: <input id="baseToolTypeComboBox"
						data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '工器具类型',
							url : 'queryBaseToolTypeDropList.do',
							onChange : function(newValue, oldValue){
								queryBaseTools();
							}
							"
						class="easyui-combobox" style="width: 200px;"></td>
					<td>厂家: <input id="baseToolManufacturerComboBox"
						data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '厂家',
							url : 'queryBaseToolManufacturerDropList.do',
							onChange : function(newValue, oldValue){
								queryBaseTools();
							}
							"
						class="easyui-combobox" style="width: 200px;"></td>
				</tr>
				<tr>
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
	<div id="choosePositionPanel" class="easyui-panel"
		data-options="fit:true">
		<table id="datagridForPosition" class="easyui-datagrid">
		</table>
		<div id="toolbarForPosition">
			<table style="width: 100%">
				<tr>
					<td><a class="easyui-linkbutton" iconCls="icon-ok"
						href="javascript:void(0)" onclick="choosePosition()">选择</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel"
						href="javascript:void(0)" onclick="openFormPanel()">返回</a></td>
					<td align="right"><input
						id="keyWordForPositionTextInput"
						class="easyui-textbox"
						data-options="prompt:'仓位名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryPositionPagesForSearch()">查询</a>
				</tr>
			</table>
		</div>
	</div>
	<div id="chooseStoragePanel" class="easyui-panel"
		data-options="fit:true">
		<table id="datagridForStorage" class="easyui-datagrid">
		</table>
		<div id="toolbarForStorage">
			<table style="width: 100%">
				<tr>
					<td><a class="easyui-linkbutton" iconCls="icon-ok"
						href="javascript:void(0)" onclick="chooseStorage()">选择</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel"
						href="javascript:void(0)" onclick="openFormPanel()">返回</a></td>
					<td align="right"><input
						id="keyWordForStorageTextInput" class="easyui-textbox"
						data-options="prompt:'仓库名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryStoragePagesForSearch()">查询</a>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>