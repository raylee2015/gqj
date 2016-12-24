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
	function closeEditUIForMaterialType() {
		closeEditUI('editUIForMaterialType')
	}

	//打开编辑窗口
	function openAddUIForMaterialType() {
		createModalDialog("editUIForMaterialType", "openEditUI.do?opType=add",
				"添加物资类型", 400, 120);
		openEditUI('editUIForMaterialType');
	}

	//打开编辑窗口
	function openEditUIForMaterialType(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForMaterialType',
				rowIndex);
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForMaterialType", url, ("修改物资类型\""
				+ rowData.TYPE_NAME + "\"的信息"), 400, 120);
		openEditUI('editUIForMaterialType');
	}

	//删除
	function delMaterialTypes() {
		if (checkSelectedItems('datagridForMaterialType', '请选择物资类型')) {
			var typeIds = getIdsOfSelectedItems('datagridForMaterialType',
					'TYPE_ID');
			if (typeIds != null && typeIds != '') {
				var params = {
					TYPE_IDS : typeIds
				};
				showMessageBox(params, 'delMaterialTypes.do', '是否删除所选物资类型?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForMaterialType');
	}

	//用在点击查询按钮的时候
	function queryMaterialTypePagesForSearch() {
		queryMaterialTypes();
	}

	//查询
	function queryMaterialTypes() {
		var params = {
			keyWord : getTextBoxValue('keyWordForMaterialTypeTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForMaterialType')
		};
		query(params, 'queryMaterialTypesPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForMaterialType', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForMaterialTypeTextInput',
						queryMaterialTypePagesForSearch);
				initDataGridForMaterialType();
			});

	//初始化列表元素
	function initDataGridForMaterialType() {
		$('#datagridForMaterialType')
				.datagrid(
						{
							url : 'queryMaterialTypesPage.do',
							idField : 'TYPE_ID',
							rownumbers : true,
							toolbar : '#toolbarForMaterialType',
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
													+ ' onclick="openEditUIForMaterialType(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'TYPE_NAME',
										title : '物资类型名称',
										width : 150,
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
			<table id="datagridForMaterialType"
				class="easyui-datagrid">
			</table>
			<div id="toolbarForMaterialType">
				<div>
					<a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true"
						onclick="refreshDataGrid('datagridForMaterialType')">刷新</a>
					<a href="#" class="easyui-linkbutton"
						iconCls="icon-add" plain="true"
						onclick="openAddUIForMaterialType()">添加</a> <a
						href="#" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true"
						onclick="delMaterialTypes()">删除</a>
				</div>
				<div>
					<input id="keyWordForMaterialTypeTextInput"
						class="easyui-textbox"
						data-options="prompt:'物资类型名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryMaterialTypePagesForSearch()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>