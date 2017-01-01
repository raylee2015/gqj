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
	//记录增加和修改的地址
	var url = "";

	// 保存数据
	function saveBaseTool() {
		var params = {
			BASE_TOOL_ID : getTextBoxValue('bastToolIdTextBox'),
			TOOL_DEMAND_ID : getTextBoxValue('baseToolDemandIdTextBox'),
			MAN_ID : getTextBoxValue('manufacturerIdTextBox'),
			BASE_TOOL_MODEL : getTextBoxValue('bastToolModelTextBox'),
			BASE_TOOL_SPEC : getTextBoxValue('bastToolSpecTextBox'),
			BASE_TOOL_REMARK : getTextBoxValue('bastToolRemarkTextBox')
		};
		save(params, url, successFunctionForSaveBaseTool);
	}

	function successFunctionForSaveBaseTool(result) {
		parent.successFunctionForOption(result);
		closeEditUIForBaseTool();
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput("keyWordForManufacturerTextInput",
						queryManufacturerPagesForSearch);
				registerKeyPressForTextInput("keyWordForToolDemandTextInput",
						queryToolDemandPagesForSearch);
				initManufacturerDataGrid();
				initToolDemandGrid();
				initBaseToolForm();
			});

	//初始化表单
	function initBaseToolForm() {
		var opType = getTextBoxValue('opType');
		var postId = getTextBoxValue('bastToolIdTextBox');
		if (opType == 'edit') {
			url = "updateBaseTool.do";
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = getRowDataOfParentDataGrid('datagridForBaseTool',
					rowIndex);
			$('#bastToolForm').form('load', rowData);
		} else if (opType == 'add') {
			url = "addNewBaseTool.do";
		}
	}

	//查询仓库
	function queryManufacturerForPage() {
		var params = {
			keyWord : getTextBoxValue('keyWordForManufacturerTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForManufacturer')
		};
		query(params, 'queryManufacturerPage.do',
				successFunctionForQueryManufacturer);
	}

	//当查询成功时需要执行的代码
	function successFunctionForQueryManufacturer(result) {
		dataGridLoadData('datagridForManufacturer', result);
	}

	//关闭表单版面，开放选择厂家版面
	function openChooseManufacturerPanel() {
		$('#chooseManufacturerPanel').panel('expand');
		$('#chooseToolTypePanel').panel('collapse');
		$('#chooseToolDemandPanel').panel('collapse');
		$('#formPanel').panel('collapse');
		parent.makeEditUIBigger();
	}

	//关闭表单版面，开放选择类型版面
	function openChooseToolTypePanel() {
		$('#chooseManufacturerPanel').panel('collapse');
		$('#chooseToolTypePanel').panel('expand');
		$('#chooseToolDemandPanel').panel('collapse');
		$('#formPanel').panel('collapse');
		parent.makeEditUIBigger();
	}

	//关闭表单版面，开放选择名称版面
	function openChooseToolDemandPanel() {
		$('#chooseManufacturerPanel').panel('collapse');
		$('#chooseToolTypePanel').panel('collapse');
		$('#chooseToolDemandPanel').panel('expand');
		$('#formPanel').panel('collapse');
		parent.makeEditUIBigger();
	}

	//关闭选择仓库版面，开放表单版面
	function openFormPanel() {
		$('#chooseManufacturerPanel').panel('collapse');
		$('#chooseToolTypePanel').panel('collapse');
		$('#chooseToolDemandPanel').panel('collapse');
		$('#formPanel').panel('expand');
		parent.makeEditUISmaller();
	}

	//初始化列表元素
	function initToolDemandGrid() {
		$('#datagridForToolDemand')
				.datagrid(
						{
							url : 'queryToolDemandsPage.do',
							idField : 'TYPE_ID',
							rownumbers : true,
							toolbar : '#toolbarForToolDemand',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : true,
							singleSelect : true,
							fit : true,
							method : 'get',
							columns : [ [ {
								field : 'ck',
								checkbox : true
							}, {
								field : 'TOOL_TYPE_NAME',
								title : '工器具类型',
								width : 100,
							}, {
								field : 'TOOL_NAME',
								title : '工器具名称',
								width : 150,
							} ] ],
							onBeforeLoad : function(param) {
								var typeId = getComboBoxValue('toolTypeComboBox');
								param.TOOL_TYPE_ID = typeId;
								param.keyWord = getTextBoxValue('keyWordForToolDemandTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//当下拉菜单变化时，触发的查询方法
	function toolTypeChange(newValue, oldValue) {
		queryToolDemandPagesForComboBox(newValue);
	}

	//用在点击查询按钮的时候
	function queryToolDemandPagesForSearch() {
		queryToolDemands('');
	}

	//用在点击查询按钮的时候
	function queryToolDemandPagesForComboBox(toolTypeId) {
		queryToolDemands(toolTypeId);
	}

	//查询
	function queryToolDemands(toolTypeId) {
		if (toolTypeId == '') {
			var typeId = getComboBoxValue('toolTypeComboBox');
			toolTypeId = typeId;
		}
		var params = {
			TOOL_TYPE_ID : toolTypeId,
			keyWord : getTextBoxValue('keyWordForToolDemandTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForToolDemand')
		};
		query(params, 'queryToolDemandsPage.do',
				successFunctionForQueryToolDemands);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryToolDemands(result) {
		dataGridLoadData('datagridForToolDemand', result);
	}

	//初始化列表元素
	function initManufacturerDataGrid() {
		$('#datagridForManufacturer')
				.datagrid(
						{
							url : 'queryManufacturersPage.do',
							idField : 'MAN_ID',
							rownumbers : true,
							toolbar : '#toolbarForManufacturer',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : true,
							singleSelect : true,
							fit : true,
							method : 'get',
							columns : [ [ {
								field : 'ck',
								checkbox : true
							}, {
								field : 'MAN_NAME',
								title : '厂家名称',
								width : 100,
							} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForManufacturerTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//用在点击查询按钮的时候
	function queryManufacturerPagesForSearch() {
		queryManufacturers();
	}

	//查询
	function queryManufacturers() {
		var params = {
			keyWord : getTextBoxValue('keyWordForManufacturerTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForManufacturer')
		};
		query(params, 'queryManufacturersPage.do',
				successFunctionForQueryManufacturers);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryManufacturers(result) {
		dataGridLoadData('datagridForManufacturer', result);
	}

	//选择仓库
	function chooseManufacturer() {
		var selectedItems = $('#datagridForManufacturer')
				.datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择厂家");
			return;
		} else {
			var selectedItem = selectedItems[0];
			setTextBoxValue('manufacturerNameTextBox', selectedItem.MAN_NAME);
			setTextBoxValue('manufacturerIdTextBox', selectedItem.MAN_ID);
			openFormPanel();
		}
	}

	//选择工器具名称
	function chooseToolDemand() {
		var selectedItems = $('#datagridForToolDemand').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择工器具");
			return;
		} else {
			var selectedItem = selectedItems[0];
			setTextBoxValue('baseToolDemandIdTextBox', selectedItem.TOOL_ID);
			setTextBoxValue('baseToolNameTextBox', selectedItem.TOOL_NAME);
			openFormPanel();
		}
	}

	//关闭编辑界面
	function closeEditUIForBaseTool() {
		parent.closeEditUIForBaseTool();
	}
</script>
</head>
<body>
	<div id="formPanel" class="easyui-panel"
		data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="fit:true,border:false,region:'north'">
				<form id="bastToolForm" method="post"
					style="width: 100%;">
					<div style="display: none">
						<input id="opType" class="easyui-textbox"
							value="<%=request.getParameter("opType")%>" /> <input
							id="bastToolIdTextBox" class="easyui-textbox"
							name="BASE_TOOL_ID" /> <input id="rowIndex"
							class="easyui-textbox"
							value="<%=request.getParameter("rowIndex")%>" /> <input
							id="baseToolDemandIdTextBox" class="easyui-textbox"
							name="TOOL_DEMAND_ID" /><input
							id="manufacturerIdTextBox" class="easyui-textbox"
							name="MAN_ID" />
					</div>
					<table style="width: 100%; padding: 10px">
						<tr>
							<td width="22%">工器具名称:</td>
							<td><input id="baseToolNameTextBox"
								name="BASE_TOOL_NAME" disabled
								class="easyui-textbox"
								style="width: 70%; height: 32px" /> <a href="#"
								class="easyui-linkbutton"
								style="width: 29%; height: 32px;"
								onclick="openChooseToolDemandPanel()"> 选择工器具</a></td>
						</tr>
						<tr>
							<td width="22%">厂家:</td>
							<td><input id="manufacturerNameTextBox"
								name="BASE_TOOL_MANUFACTURER_NAME" disabled
								class="easyui-textbox"
								style="width: 70%; height: 32px" /> <a href="#"
								class="easyui-linkbutton"
								style="width: 29%; height: 32px;"
								onclick="openChooseManufacturerPanel()"> 选择厂家</a></td>
						</tr>
						<tr>
							<td width="22%">工器具型号:</td>
							<td><input id="bastToolModelTextBox"
								name="BASE_TOOL_MODEL" class="easyui-textbox"
								data-options="prompt:'工器具型号',required:true,validType:'length[0,20]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
						<tr>
							<td width="22%">工器具参数:</td>
							<td><input id="bastToolSpecTextBox"
								name="BASE_TOOL_SPEC" class="easyui-textbox"
								data-options="prompt:'工器具参数',required:true,validType:'length[0,20]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
						<tr>
							<td width="22%">工器具备注:</td>
							<td><input id="bastToolRemarkTextBox"
								name="BASE_TOOL_REMARK" class="easyui-textbox"
								data-options="prompt:'工器具备注',required:true,validType:'length[0,20]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px">
				<a class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="saveBaseTool()">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel"
					href="javascript:void(0)"
					onclick="closeEditUIForBaseTool()">关闭</a>
			</div>
		</div>
	</div>
	<div id="chooseManufacturerPanel" class="easyui-panel"
		fit="true" border="false">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" border="false" style="height: 30px">
				<a href="#" class="easyui-linkbutton"
					onclick="openFormPanel()">返回</a> <a href="#"
					class="easyui-linkbutton"
					onclick="chooseManufacturer()">选择</a>
			</div>
			<div region="center" border="false">
				<table id="datagridForManufacturer"
					class="easyui-datagrid">
				</table>
				<div id="toolbarForManufacturer">
					<input id="keyWordForManufacturerTextInput"
						class="easyui-textbox"
						data-options="prompt:'仓库名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryManufacturerPagesForSearch()">查询</a>
				</div>
			</div>
		</div>
	</div>
	<div id="chooseToolDemandPanel" class="easyui-panel"
		fit="true" border="false">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" border="false" style="height: 30px">
				<a href="#" class="easyui-linkbutton"
					onclick="openFormPanel()">返回</a> <a href="#"
					class="easyui-linkbutton" onclick="chooseToolDemand()">选择</a>
			</div>
			<div region="center" border="false">
				<table id="datagridForToolDemand"
					class="easyui-datagrid">
				</table>
				<div id="toolbarForToolDemand">
					<table width="100%">
						<tr>
							<td><input id="keyWordForToolDemandTextInput"
								class="easyui-textbox"
								data-options="prompt:'工器具名称',validType:'length[0,50]'"
								style="width: 150px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryToolDemandPagesForSearch()">查询</a>
								工器具种类: <input id="toolTypeComboBox" name="TYPE_ID"
								data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '工器具类型',
							url : 'queryToolDemandTypeDropList.do',
							onChange : function(newValue, oldValue){
								toolTypeChange(newValue, oldValue);
							}
							"
								class="easyui-combobox" style="width: 150px;"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>