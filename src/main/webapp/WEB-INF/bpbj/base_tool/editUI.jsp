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
	href="<%=contextPath%>/jquery-easyui-1.5/demo/demo.css">
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/base.js"></script>
<script type="text/javascript">
	//记录增加和修改的地址
	var url = "";

	// 保存数据
	function saveBaseTool() {
		var baseToolCode = "";
		if (getTextBoxValue('baseToolTypeTextBox') == 1) {
			baseToolCode = getTextBoxValue('baseToolCodeTextBox');
		}
		var params = {
			BASE_TOOL_ID : getTextBoxValue('baseToolIdTextBox'),
			BASE_TOOL_CODE : baseToolCode,
			BASE_TOOL_NAME : getTextBoxValue('baseToolNameTextBox'),
			BASE_TOOL_TYPE : getTextBoxValue('baseToolTypeTextBox'),
			MAN_ID : getTextBoxValue('manufacturerIdTextBox'),
			BASE_TOOL_MODEL : getTextBoxValue('baseToolModelTextBox'),
			BASE_TOOL_SPEC : getTextBoxValue('baseToolSpecTextBox'),
			BASE_TOOL_REMARK : getTextBoxValue('baseToolRemarkTextBox'),
			BASE_TOOL_UNIT : getTextBoxValue('baseToolUnitTextBox'),
			BASE_TOOL_STATION : getTextBoxValue('baseToolStationTextBox')
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
				initManufacturerDataGrid();
				initBaseToolForm();
			});

	//初始化表单
	function initBaseToolForm() {
		var opType = getTextBoxValue('opType');
		if (opType == 'edit') {
			url = "updateBaseTool.do";
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = getRowDataOfParentDataGrid('datagridForBaseTool',
					rowIndex);
			$('#baseToolForm').form('load', rowData);
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
		$('#formPanel').panel('collapse');
		parent.makeEditUIBigger();
	}

	//关闭表单版面，开放选择类型版面
	function openChooseToolTypePanel() {
		$('#chooseManufacturerPanel').panel('collapse');
		$('#formPanel').panel('collapse');
		parent.makeEditUIBigger();
	}

	//关闭选择仓库版面，开放表单版面
	function openFormPanel() {
		$('#chooseManufacturerPanel').panel('collapse');
		$('#formPanel').panel('expand');
		parent.makeEditUISmaller();
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
				<form id="baseToolForm" method="post" style="width: 100%;">
					<div style="display: none">
						<input id="opType" class="easyui-textbox"
							value="<%=request.getParameter("opType")%>" /> <input
							id="rowIndex" class="easyui-textbox"
							value="<%=request.getParameter("rowIndex")%>" /><input
							id="baseToolTypeTextBox" class="easyui-textbox"
							value="<%=request.getParameter("BASE_TOOL_TYPE")%>" /> <input
							id="baseToolIdTextBox" class="easyui-textbox" name="BASE_TOOL_ID" />
						<input id="manufacturerIdTextBox" class="easyui-textbox"
							name="MAN_ID" />
					</div>
					<table style="width: 100%; padding: 10px">
						<%
							String baseToolType = request.getParameter("BASE_TOOL_TYPE");
							String title = "";
							if ("1".equals(baseToolType)) {
								title = "配件";
						%>
						<tr>
							<td width="22%"><%=title%>编码:</td>
							<td><input id="baseToolCodeTextBox"
								data-options="prompt:'<%=title%>编码',required:true,validType:'length[0,50]'"
								name="BASE_TOOL_CODE" class="easyui-textbox"
								style="width: 100%; height: 32px" /></td>
						</tr>
						<%
							} else {
								title = "插件";
							}
						%>
						<tr>
							<td width="22%"><%=title%>名称:</td>
							<td><input id="baseToolNameTextBox"
								data-options="prompt:'<%=title%>名称',required:true,validType:'length[0,50]'"
								name="BASE_TOOL_NAME" class="easyui-textbox"
								style="width: 100%; height: 32px" /></td>
						</tr>
						<tr>
							<td width="22%">厂家:</td>
							<td><input id="manufacturerNameTextBox"
								name="BASE_TOOL_MANUFACTURER_NAME" disabled
								class="easyui-textbox" style="width: 70%; height: 32px" /> <a
								href="#" class="easyui-linkbutton"
								style="width: 29%; height: 32px;"
								onclick="openChooseManufacturerPanel()"> 选择厂家</a></td>
						</tr>
						<tr>
							<td width="22%"><%=title%>型号:</td>
							<td><input id="baseToolModelTextBox" name="BASE_TOOL_MODEL"
								class="easyui-textbox"
								data-options="prompt:'<%=title%>型号',required:true,validType:'length[0,50]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
						<tr>
							<td width="22%"><%=title%>参数:</td>
							<td><input id="baseToolSpecTextBox" name="BASE_TOOL_SPEC"
								class="easyui-textbox"
								data-options="prompt:'<%=title%>参数',required:true,validType:'length[0,50]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
						<tr>
							<td width="22%"><%=title%>单位:</td>
							<td><input id="baseToolUnitTextBox" name="BASE_TOOL_UNIT"
								class="easyui-textbox"
								data-options="prompt:'<%=title%>单位',required:true,validType:'length[0,10]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
						<tr>
							<td width="22%"><%=title%>适用站点:</td>
							<td><input id="baseToolStationTextBox"
								name="BASE_TOOL_STATION" class="easyui-textbox"
								data-options="prompt:'<%=title%>适用站点',required:true,validType:'length[0,50]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
						<tr>
							<td width="22%"><%=title%>备注:</td>
							<td><input id="baseToolRemarkTextBox"
								name="BASE_TOOL_REMARK" class="easyui-textbox"
								data-options="prompt:'<%=title%>备注',required:true,validType:'length[0,20]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px">
				<a class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="saveBaseTool()">保存</a> <a
					class="easyui-linkbutton" iconCls="icon-cancel"
					href="javascript:void(0)" onclick="closeEditUIForBaseTool()">关闭</a>
			</div>
		</div>
	</div>
	<div id="chooseManufacturerPanel" class="easyui-panel" fit="true"
		border="false">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" border="false" style="height: 30px">
				<a href="#" class="easyui-linkbutton" onclick="openFormPanel()">返回</a>
				<a href="#" class="easyui-linkbutton" onclick="chooseManufacturer()">选择</a>
			</div>
			<div region="center" border="false">
				<table id="datagridForManufacturer" class="easyui-datagrid">
				</table>
				<div id="toolbarForManufacturer">
					<input id="keyWordForManufacturerTextInput" class="easyui-textbox"
						data-options="prompt:'仓库名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryManufacturerPagesForSearch()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>