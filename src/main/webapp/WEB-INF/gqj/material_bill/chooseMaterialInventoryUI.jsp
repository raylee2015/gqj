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
				initDataGridForMaterialInventory();
				registerKeyPressForTextInput(
						'keyWordForMaterialInventoryTextInput',
						queryMaterialInventoryPagesForSearch);
				registerKeyPressForTextInput('baseToolSpecTextBox',
						queryMaterialInventoryPagesForSearch);
				registerKeyPressForTextInput('baseToolModelTextBox',
						queryMaterialInventoryPagesForSearch);
			});

	//用在点击查询按钮的时候
	function queryMaterialInventoryPagesForSearch() {
		queryMaterialInventorys();
	}

	//查询
	function queryMaterialInventorys() {
		var params = {
			BASE_TOOL_TYPE_ID : getComboBoxValue('baseToolTypeComboBox'),
			MANUFACTURER_ID : getComboBoxValue('baseToolManufacturerComboBox'),
			keyWord : getTextBoxValue('keyWordForMaterialInventoryTextInput'),
			BASE_TOOL_MODEL : getTextBoxValue('baseToolModelTextBox'),
			BASE_TOOL_SPEC : getTextBoxValue('baseToolSpecTextBox'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForMaterialInventory')
		};
		query(params, 'queryMaterialInventorysPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForMaterialInventory', result);
	}

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
							}, {
								field : 'STORE_NAME',
								title : '仓库',
								width : 100
							}, {
								field : 'POS_NAME',
								title : '仓位',
								width : 100
							}, {
								field : 'INVENT_AMOUNT',
								title : '数量',
								width : 100
							} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForMaterialInventoryTextInput');
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
	//关闭编辑窗口
	function closeChooseMaterialInventoryUIForMaterialBill() {
		parent.closeChooseMaterialInventoryUIForMaterialBill();
	}

	function choose() {
		var selectedItems = $('#datagridForMaterialInventory').datagrid(
				'getSelections');
		var data = parent.$('#datagridForMaterialBillDetail').datagrid(
				'getData');
		if (data.rows.length == 0) {
			data = selectedItems;
		} else {
			//排重
			for (var i = 0; i < data.rows.length; i++) {
				for (var j = 0; j < selectedItems.length; j++) {
					if (selectedItems[j].BASE_TOOL_ID == data.rows[i].BASE_TOOL_ID) {
						selectedItems.splice(j, 1);
						break;
					}
				}
			}
			for (var j = 0; j < selectedItems.length; j++) {
				data.rows.push(selectedItems[j]);
			}
		}
		parent.$('#datagridForMaterialBillDetail').datagrid('loadData', data);
		closeChooseMaterialInventoryUIForMaterialBill();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<table id="datagridForMaterialInventory"
				class="easyui-datagrid">
			</table>
			<div id="toolbarForMaterialInventory">
				<table style="width: 100%">
					<tr>
						<td><a class="easyui-linkbutton"
							iconCls="icon-ok" href="javascript:void(0)"
							onclick="choose()">选择</a> <a
							class="easyui-linkbutton" iconCls="icon-cancel"
							href="javascript:void(0)"
							onclick="closeChooseMaterialInventoryUIForTemplate()">关闭</a></td>
						<td align="right"><input
							id="keyWordForMaterialInventoryTextInput"
							class="easyui-textbox"
							data-options="prompt:'工器具名称',validType:'length[0,25]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryMaterialInventoryPagesForSearch()">查询</a>
					</tr>
					<tr>
						<td>类型: <input id="baseToolTypeComboBox"
							data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '工器具类型',
							url : 'queryBaseToolTypeDropList.do',
							onChange : function(newValue, oldValue){
								queryMaterialInventorys();
							}
							"
							class="easyui-combobox" style="width: 200px;"></td>
						<td>厂家: <input id="baseToolManufacturerComboBox"
							data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '厂家',
							url : 'queryBaseToolManufacturerDropList.do',
							onChange : function(newValue, oldValue){
								queryMaterialInventorys();
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
	</div>
</body>
</html>