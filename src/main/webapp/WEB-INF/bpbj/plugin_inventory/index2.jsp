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

	//用在点击查询按钮的时候
	function queryPlugInInventoryPagesForSearch() {
		queryPlugInInventorys();
	}

	//查询
	function queryPlugInInventorys() {
		var params = {
			BASE_TOOL_MODEL : getTextBoxValue('baseToolModelTextBox'),
			BASE_TOOL_SPEC : getTextBoxValue('baseToolSpecTextBox'),
			keyWord : getTextBoxValue('keyWordForPlugInInventoryTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForPlugInInventory')
		};
		query(params, 'queryPlugInInventorysPageByBaseTool.do',
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
			});

	//初始化列表元素
	function initDataGridForPlugInInventory() {
		$('#datagridForPlugInInventory')
				.datagrid(
						{
							url : 'queryPlugInInventorysPageByBaseTool.do',
							idField : 'BASE_TOOL_ID',
							rownumbers : true,
							toolbar : '#toolbarForPlugInInventory',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : false,
							fit : true,
							method : 'get',
							columns : [ [
									 {
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
										field : 'BASE_TOOL_AMOUNT',
										title : '数量',
										width : 100,
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForPlugInInventoryTextInput');
								param.BASE_TOOL_MODEL = getTextBoxValue('baseToolModelTextBox');
								param.BASE_TOOL_SPEC = getTextBoxValue('baseToolSpecTextBox');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

</script>
</head>
<body>
	<div id="accessoryInventoryListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<div class="easyui-layout"
			data-options="fit:true,border:false">
			<div data-options="fit:true,border:false,region:'center'">
				<table id="datagridForPlugInInventory"
					class="easyui-datagrid">
				</table>
				<div id="toolbarForPlugInInventory">
					<table style="width: 100%">
						<tr>
							<td><a href="#" class="easyui-linkbutton"
								iconCls="icon-reload" plain="true"
								onclick="refreshDataGrid('datagridForPlugInInventory')">刷新</a>
							</td>
							<td></td>
							<td align="right"><input
								id="keyWordForPlugInInventoryTextInput"
								class="easyui-textbox"
								data-options="prompt:'工器具',validType:'length[0,50]'"
								style="width: 200px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryPlugInInventoryPagesForSearch()">查询</a>
						</tr>
						<tr>
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
</body>
</html>