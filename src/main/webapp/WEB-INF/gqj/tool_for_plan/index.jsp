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
	function closeEditUIForToolForPlan() {
		closeEditUI('editUIForToolForPlan')
	}

	//打开编辑窗口
	function openAddUIForToolForPlan() {
		createModalDialog("editUIForToolForPlan", "openEditUI.do?opType=add",
				"添加工器具", 500, 450);
		openEditUI('editUIForToolForPlan');
	}

	//打开编辑窗口
	function openEditUIForToolForPlan(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForToolForPlan',
				rowIndex);
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForToolForPlan", url, ("修改工器具\""
				+ rowData.TOOL_NAME + "\"的信息"), 500, 450);
		openEditUI('editUIForToolForPlan');
	}

	//删除
	function delToolForPlans() {
		if (checkSelectedItems('datagridForToolForPlan', '请选择工器具')) {
			var ids = getIdsOfSelectedItems('datagridForToolForPlan', 'TOOL_ID');
			if (ids != null && ids != '') {
				var params = {
					TOOL_IDS : ids
				};
				showMessageBox(params, 'delToolForPlans.do', '是否删除所选工器具?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForToolForPlan');
	}

	//用在点击查询按钮的时候
	function queryToolForPlanPagesForSearch() {
		queryToolForPlans();
	}

	//查询
	function queryToolForPlans() {
		var params = {
			keyWord : getTextBoxValue('keyWordForToolForPlanTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForToolForPlan')
		};
		query(params, 'queryToolForPlansPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForToolForPlan', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForToolForPlanTextInput',
						queryToolForPlanPagesForSearch);
				initDataGridForToolForPlan();
			});

	//初始化列表元素
	function initDataGridForToolForPlan() {
		$('#datagridForToolForPlan')
				.datagrid(
						{
							url : 'queryToolForPlansPage.do',
							idField : 'TOOL_ID',
							rownumbers : true,
							toolbar : '#toolbarForToolForPlan',
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
													+ ' onclick="openEditUIForToolForPlan(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'TOOL_TYPE_NAME',
										title : '工器具类型',
										width : 100,
									}, {
										field : 'TOOL_NAME',
										title : '工器具名称',
										width : 150,
									}, {
										field : 'TOOL_STANDARD_CONFIG_DEMAND',
										title : '工器具标准配置',
										width : 450,
									}, {
										field : 'TOOL_MODEL_DEMAND',
										title : '工器具型号',
										width : 450,
									}, {
										field : 'TOOL_UNIT_NAME',
										title : '工器具单位',
										width : 80,
									}, {
										field : 'TOOL_REMARK',
										title : '备注',
										width : 80,
									} ] ],
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}
</script>
</head>
<body>
	<!-- 列表页面 -->
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'">
			<table id="datagridForToolForPlan" class="easyui-datagrid">
			</table>
			<div id="toolbarForToolForPlan">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refreshDataGrid('datagridForToolForPlan')">刷新</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true" onclick="openAddUIForToolForPlan()">添加</a> <a
						href="#" class="easyui-linkbutton" iconCls="icon-remove"
						plain="true" onclick="delToolForPlans()">删除</a>
				</div>
				<div>
					<input id="keyWordForToolForPlanTextInput" class="easyui-textbox"
						data-options="prompt:'工器具名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryToolForPlanPagesForSearch()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>