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
	function closeEditUIForZone() {
		closeEditUI('editUIForZone')
	}

	//打开编辑窗口
	function openAddUIForZone() {
		createModalDialog("editUIForZone", "openEditUI.do?opType=add",
				"添加变电站", 400, 120);
		openEditUI('editUIForZone');
	}

	//打开编辑窗口
	function openEditUIForZone(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForZone',
				rowIndex);
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForZone", url, ("修改变电站间隔\""
				+ rowData.ZONE_NAME + "\"的信息"), 400, 200);
		openEditUI('editUIForZone');
	}

	//删除
	function delZones() {
		if (checkSelectedItems('datagridForZone', '请选择变电站间隔')) {
			var ids = getIdsOfSelectedItems('datagridForZone', 'ZONE_ID');
			if (ids != null && ids != '') {
				var params = {
					ZONE_IDS : ids
				};
				showMessageBox(params, 'delZones.do', '是否删除所选变电站间隔?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForZone');
	}

	//用在点击查询按钮的时候
	function queryZonePagesForSearch() {
		queryZones();
	}

	//查询
	function queryZones() {
		var params = {
			keyWord : getTextBoxValue('keyWordForZoneTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForZone')
		};
		query(params, 'queryZonesPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForZone', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForZoneTextInput',
						queryZonePagesForSearch);
				initDataGridForZone();
			});

	//初始化列表元素
	function initDataGridForZone() {
		$('#datagridForZone')
				.datagrid(
						{
							url : 'queryZonesPage.do',
							idField : 'ZONE_ID',
							rownumbers : true,
							toolbar : '#toolbarForZone',
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
													+ ' onclick="openEditUIForZone(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'STATION_NAME',
										title : '变电站名称',
										width : 150,
									}, {
										field : 'ZONE_TYPE_NAME',
										title : '区域类型',
										width : 150,
									}, {
										field : 'ZONE_NAME',
										title : '区域名称',
										width : 150,
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForZoneTextInput');
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
			<table id="datagridForZone"
				class="easyui-datagrid">
			</table>
			<div id="toolbarForZone">
				<table style="width: 100%">
					<tr>
						<td><a href="#" class="easyui-linkbutton"
							iconCls="icon-reload" plain="true"
							onclick="refreshDataGrid('datagridForZone')">刷新</a>
							<a href="#" class="easyui-linkbutton"
							iconCls="icon-add" plain="true"
							onclick="openAddUIForZone()">添加</a> <a
							href="#" class="easyui-linkbutton"
							iconCls="icon-remove" plain="true"
							onclick="delZones()">删除</a></td>
						<td align="right"><input
							id="keyWordForZoneTextInput"
							class="easyui-textbox"
							data-options="prompt:'变电站名称|间隔名称',validType:'length[0,25]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryZonePagesForSearch()">查询</a>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>