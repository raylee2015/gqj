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
	//关闭编辑窗口
	function closeEditUIForStorage() {
		closeEditUI('editUIForStorage')
	}

	//打开编辑窗口
	function openAddUIForStorage() {
		createModalDialog("editUIForStorage", "openEditUI.do?opType=add",
				"添加仓库", 400, 120);
		openEditUI('editUIForStorage');
	}

	//打开编辑窗口
	function openEditUIForStorage(rowIndex) {
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		var rowData = getRowDataOfSelfDataGrid('datagridForStorage', rowIndex);
		createModalDialog("editUIForStorage", url, ("修改仓库\""
				+ rowData.STORE_NAME + "\"的信息"), 400, 120);
		openEditUI('editUIForStorage');
	}
	//删除
	function delStorages() {
		if (checkSelectedItems('datagridForStorage', '请选择仓库')) {
			var storeIds = getIdsOfSelectedItems('datagridForStorage',
					'STORE_ID');
			if (storeIds != null && storeIds != '') {
				var params = {
					STORE_IDS : storeIds
				};
				showMessageBox(params, 'delStorages.do', '是否删除所选仓库?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForStorage');
	}

	//用在点击查询按钮的时候
	function queryStoragePagesForSearch() {
		queryStorages();
	}

	//查询
	function queryStorages() {
		var params = {
			keyWord : getTextBoxValue('keyWordForStorageTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForStorage')
		};
		query(params, 'queryStoragesPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForStorage', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForStorageTextInput',
						queryStoragePagesForSearch);
				initDataGridForStorage();
			});

	//初始化列表元素
	function initDataGridForStorage() {
		$('#datagridForStorage')
				.datagrid(
						{
							url : 'queryStoragesPage.do',
							idField : 'STORE_ID',
							rownumbers : true,
							toolbar : '#toolbarForStorage',
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
													+ ' onclick="openEditUIForStorage(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'STORE_NAME',
										title : '仓库名称',
										width : 100,
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForStorageTextInput');
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
		<div region="center">
			<table id="datagridForStorage" class="easyui-datagrid">
			</table>
			<div id="toolbarForStorage">
				<table style="width: 100%">
					<tr>
						<td><a href="#" class="easyui-linkbutton"
							iconCls="icon-reload" plain="true"
							onclick="refreshDataGrid('datagridForStorage')">刷新</a>
							<a href="#" class="easyui-linkbutton"
							iconCls="icon-add" plain="true"
							onclick="openAddUIForStorage()">添加</a> <a href="#"
							class="easyui-linkbutton" iconCls="icon-remove"
							plain="true" onclick="delStorages()">删除</a></td>
						<td align="right"><input
							id="keyWordForStorageTextInput"
							class="easyui-textbox"
							data-options="prompt:'仓库名称',validType:'length[0,50]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryStoragePagesForSearch()">查询</a>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>