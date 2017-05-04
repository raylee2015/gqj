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
	// 关闭编辑窗口
	function closeEditUIForBaseTool() {
		closeEditUI('editUIForBaseTool')
	}

	// 编辑窗口变大
	function makeEditUIBigger() {
		$('#editUIForBaseTool').panel('resize', {
			height : 500
		});
		$('#editUIForBaseTool').window('center');
	}

	// 编辑窗口变小
	function makeEditUISmaller() {
		$('#editUIForBaseTool').panel('resize', {
			height : 365
		});
		$('#editUIForBaseTool').window('center');
	}

	//打开编辑窗口
	function openAddUIForBaseTool() {
		createModalDialog("editUIForBaseTool",
				"openEditUI.do?opType=add&BASE_TOOL_TYPE="
						+ getTextBoxValue('baseToolTypeTextBox'), "添加基础信息",
				500, 365);
		openEditUI('editUIForBaseTool');
	}

	//打开编辑窗口
	function openEditUIForBaseTool(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForBaseTool', rowIndex);
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex
				+ '&BASE_TOOL_TYPE=' + getTextBoxValue('baseToolTypeTextBox');
		createModalDialog("editUIForBaseTool", url, ("修改\""
				+ rowData.BASE_TOOL_NAME + "\"的基础信息"), 500, 365);
		openEditUI('editUIForBaseTool');
	}

	//删除
	function delBaseTools() {
		if (checkSelectedItems('datagridForBaseTool', '请选择基础信息')) {
			var ids = getIdsOfSelectedItems('datagridForBaseTool',
					'BASE_TOOL_ID');
			if (ids != null && ids != '') {
				var params = {
					BASE_TOOL_IDS : ids
				};
				showMessageBox(params, 'delBaseTools.do', '是否删除所选基础信息?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForBaseTool');
	}

	//用在点击查询按钮的时候
	function queryBaseToolPagesForSearch() {
		queryBaseTools();
	}

	//查询
	function queryBaseTools() {
		var params = {
			BASE_TOOL_TYPE : getTextBoxValue('baseToolTypeTextBox'),
			keyWord : getTextBoxValue('keyWordForBaseToolTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForBaseTool')
		};
		query(params, 'queryBaseToolsPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForBaseTool', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForBaseToolTextInput',
						queryBaseToolPagesForSearch);
				initDataGridForBaseTool();
				var baseToolType = getTextBoxValue('baseToolTypeTextBox');
				//配件按件管理，需要显示条码
				if (baseToolType == '1') {
					$('#datagridForBaseTool').treegrid('showColumn',
							'BASE_TOOL_CODE');
				}
			});

	//初始化列表元素
	function initDataGridForBaseTool() {
		var baseToolType = getTextBoxValue('baseToolTypeTextBox');
		var baseToolTitle = "";
		if (baseToolType == '1') {
			baseToolTitle = "配件";
		} else {
			baseToolTitle = "插件";
		}
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
											var btn = '<a class="easyui-linkbutton" '
													+ ' onclick="openEditUIForBaseTool(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'BASE_TOOL_CODE',
										title : baseToolTitle + '编码',
										width : 150,
										hidden : true
									}, {
										field : 'BASE_TOOL_NAME',
										title : baseToolTitle + '名称',
										width : 150,
									}, {
										field : 'BASE_TOOL_MANUFACTURER_NAME',
										title : baseToolTitle + '厂家',
										width : 150,
									}, {
										field : 'BASE_TOOL_MODEL',
										title : baseToolTitle + '型号',
										width : 150,
									}, {
										field : 'BASE_TOOL_SPEC',
										title : baseToolTitle + '规格参数',
										width : 150,
									}, {
										field : 'BASE_TOOL_UNIT',
										title : baseToolTitle + '单位',
										width : 100,
									}, {
										field : 'BASE_TOOL_STATION',
										title : baseToolTitle + '适用站点',
										width : 100,
									}, {
										field : 'BASE_TOOL_REMARK',
										title : baseToolTitle + '备注',
										width : 150,
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForBaseToolTextInput');
								param.BASE_TOOL_TYPE = getTextBoxValue('baseToolTypeTextBox');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}
</script>
</head>
<body>
	<div style="display: none">
		<input id="baseToolTypeTextBox" class="easyui-textbox"
			value="<%=request.getParameter("BASE_TOOL_TYPE")%>" />
	</div>
	<!-- 列表页面 -->
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'">
			<table id="datagridForBaseTool" class="easyui-datagrid">
			</table>
			<div id="toolbarForBaseTool">
				<table style="width: 100%">
					<tr>
						<td><a href="#" class="easyui-linkbutton"
							iconCls="icon-reload" plain="true"
							onclick="refreshDataGrid('datagridForBaseTool')">刷新</a> <a
							href="#" class="easyui-linkbutton" iconCls="icon-add"
							plain="true" onclick="openAddUIForBaseTool()">添加</a> <a href="#"
							class="easyui-linkbutton" iconCls="icon-remove" plain="true"
							onclick="delBaseTools()">删除</a></td>
						<td align="right"><input id="keyWordForBaseToolTextInput"
							class="easyui-textbox"
							data-options="prompt:'名称',validType:'length[0,25]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryBaseToolPagesForSearch()">查询</a>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>