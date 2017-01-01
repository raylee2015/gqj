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
	function closeEditUIForToolType() {
		closeEditUI('editUIForToolType')
	}

	//打开编辑窗口
	function openAddUIForToolType() {
		createModalDialog("editUIForToolType", "openEditUI.do?opType=add",
				"添加工器具类型", 400, 120);
		openEditUI('editUIForToolType');
	}

	//打开编辑窗口
	function openEditUIForToolType(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForToolType', rowIndex);
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForToolType", url, ("修改工器具类型\""
				+ rowData.TYPE_NAME + "\"的信息"), 400, 120);
		openEditUI('editUIForToolType');
	}

	//删除
	function delToolTypes() {
		if (checkSelectedItems('datagridForToolType', '请选择工器具类型')) {
			var typeIds = getIdsOfSelectedItems('datagridForToolType',
					'TYPE_ID');
			if (typeIds != null && typeIds != '') {
				var params = {
					TYPE_IDS : typeIds
				};
				showMessageBox(params, 'delToolTypes.do', '是否删除所选工器具类型?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForToolType');
	}

	//用在点击查询按钮的时候
	function queryToolTypePagesForSearch() {
		queryToolTypes();
	}

	//查询
	function queryToolTypes() {
		var params = {
			keyWord : getTextBoxValue('keyWordForToolTypeTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForToolType')
		};
		query(params, 'queryToolTypesPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForToolType', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForToolTypeTextInput',
						queryToolTypePagesForSearch);
				initDataGridForToolType();
			});

	//初始化列表元素
	function initDataGridForToolType() {
		$('#datagridForToolType')
				.datagrid(
						{
							url : 'queryToolTypesPage.do',
							idField : 'TYPE_ID',
							rownumbers : true,
							toolbar : '#toolbarForToolType',
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
													+ ' onclick="openEditUIForToolType(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'TYPE_NAME',
										title : '工器具类型名称',
										width : 150,
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForToolTypeTextInput');
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
			<table id="datagridForToolType" class="easyui-datagrid">
			</table>
			<div id="toolbarForToolType">
				<table width="100%">
					<tr>
						<td><a href="#" class="easyui-linkbutton"
							iconCls="icon-reload" plain="true"
							onclick="refreshDataGrid('datagridForToolType')">刷新</a>
							<a href="#" class="easyui-linkbutton"
							iconCls="icon-add" plain="true"
							onclick="openAddUIForToolType()">添加</a> <a href="#"
							class="easyui-linkbutton" iconCls="icon-remove"
							plain="true" onclick="delToolTypes()">删除</a></td>
						<td align="right"><input
							id="keyWordForToolTypeTextInput"
							class="easyui-textbox"
							data-options="prompt:'工器具类型名称',validType:'length[0,25]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryToolTypePagesForSearch()">查询</a>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>