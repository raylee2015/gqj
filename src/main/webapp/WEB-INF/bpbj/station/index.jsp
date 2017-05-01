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
	// 关闭编辑窗口
	function closeEditUIForStation() {
		closeEditUI('editUIForStation')
	}

	//打开编辑窗口
	function openAddUIForStation() {
		createModalDialog("editUIForStation", "openEditUI.do?opType=add",
				"添加变电站", 400, 120);
		openEditUI('editUIForStation');
	}

	//打开编辑窗口
	function openEditUIForStation(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForStation',
				rowIndex);
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForStation", url, ("修改变电站\""
				+ rowData.STATION_NAME + "\"的信息"), 400, 120);
		openEditUI('editUIForStation');
	}

	//删除
	function delStations() {
		if (checkSelectedItems('datagridForStation', '请选择变电站')) {
			var ids = getIdsOfSelectedItems('datagridForStation', 'STATION_ID');
			if (ids != null && ids != '') {
				var params = {
					STATION_IDS : ids
				};
				showMessageBox(params, 'delStations.do', '是否删除所选变电站?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForStation');
	}

	//用在点击查询按钮的时候
	function queryStationPagesForSearch() {
		queryStations();
	}

	//查询
	function queryStations() {
		var params = {
			keyWord : getTextBoxValue('keyWordForStationTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForStation')
		};
		query(params, 'queryStationsPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForStation', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForStationTextInput',
						queryStationPagesForSearch);
				initDataGridForStation();
			});

	//初始化列表元素
	function initDataGridForStation() {
		$('#datagridForStation')
				.datagrid(
						{
							url : 'queryStationsPage.do',
							idField : 'STATION_ID',
							rownumbers : true,
							toolbar : '#toolbarForStation',
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
													+ ' onclick="openEditUIForStation(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'STATION_NAME',
										title : '变电站名称',
										width : 150,
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForStationTextInput');
							},
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
			<table id="datagridForStation"
				class="easyui-datagrid">
			</table>
			<div id="toolbarForStation">
				<table style="width: 100%">
					<tr>
						<td><a href="#" class="easyui-linkbutton"
							iconCls="icon-reload" plain="true"
							onclick="refreshDataGrid('datagridForStation')">刷新</a>
							<a href="#" class="easyui-linkbutton"
							iconCls="icon-add" plain="true"
							onclick="openAddUIForStation()">添加</a> <a
							href="#" class="easyui-linkbutton"
							iconCls="icon-remove" plain="true"
							onclick="delStations()">删除</a></td>
						<td align="right"><input
							id="keyWordForStationTextInput"
							class="easyui-textbox"
							data-options="prompt:'变电站名称',validType:'length[0,25]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryStationPagesForSearch()">查询</a>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>