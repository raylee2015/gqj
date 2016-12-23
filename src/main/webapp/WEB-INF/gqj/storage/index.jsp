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
	//关闭编辑窗口
	function closeEditUIForStorage() {
		closeEditUI('editUIForStorage')
	}

	//打开编辑窗口
	function openAddUIForStorage() {
		createModalDialog("editUIForStorage", "openEditUI.do?opType=add",
				"仓库设置", 400, 300);
		openEditUI('editUIForStorage');
	}

	//打开编辑窗口
	function openEditUIForStorage(rowIndex) {
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForStorage", url, "仓库设置", 400, 300);
		openEditUI('editUIForStorage');
	}
	//删除
	function delStorages() {
		if (checkSelectedItems('datagridForStorage', '请选择仓库')) {
			var storeIds = getIdsOfSelectedItems('datagridForStorage',
					'MENU_INNER_CODE');
			if (storeIds != null && storeIds != '') {
				var params = {
					STORE_IDS : storeIds
				};
				showMessageBox(params, 'delStorages.do', '是否删除所选仓库?',
						successFunctionForOption);
			}
		}
	}
	
	//查询
	function queryForPage() {
		var params = {
			keyWord : $('#keyWordTextInput').textbox('getValue'),
			page : 1,
			rows : $('#datagrid').datagrid('getPager').data("pagination").options.pageSize
		};
		query(params, 'queryStoragePage.do');
	}

	//页面加载完
	$(document).ready(function() {
		initDocument();
		initDataGrid();
	});

	//初始化列表元素
	function initDataGrid() {
		$('#datagrid').datagrid({
			url : 'queryStoragePage.do',
			idField : 'STORE_ID',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'op',
				title : '操作',
				formatter : editColumnFormatter
			}, {
				field : 'STORE_NAME',
				title : '仓库名称',
				width : 100,
			} ] ],
			onLoadError : function() {
				eval(errorCodeForQuery);
			}
		});
	}
</script>
</head>
<body>
	<!-- 列表页面 -->
	<div class="easyui-layout" data-options="fit:true">
		<div region="center">
			<table id="datagrid" class="easyui-datagrid" rownumbers="true"
				toolbar="#toolbar" pagination="true" pageSize="30" pageNumber="1"
				checkOnSelect="false" method="get" fit="true">
			</table>
			<div id="toolbar">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refresh()">刷新</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="openAddUI()">添加</a> <a href="#" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="delStorages()">删除</a>
				</div>
				<div>
					<input id="keyWordTextInput" class="easyui-textbox"
						data-options="prompt:'仓库名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryForPage()">查询</a>
				</div>
			</div>
		</div>
	</div>

	<!--  详细界面 -->
	<div id="editUI" class="easyui-window" title="添加仓库" closed="true"
		data-options="iconCls:'icon-save'"
		style="width: 400px; height: 120px; padding: 5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" fit="true" border="false">
				<form id="form" method="post" style="width: 100%;">
					<div style="display: none">
						<input id="storageIdTextBox" name="STORE_ID"
							class="easyui-textbox" />
					</div>
					<table width="100%">
						<tr>
							<td width="22%">仓库名称:</td>
							<td><input id="storageNameTextBox" name="STORE_NAME"
								class="easyui-textbox"
								data-options="prompt:'仓库名称',required:true,validType:'length[3,10]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px">
				<a class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="saveStorage()">保存</a> <a
					class="easyui-linkbutton" iconCls="icon-cancel"
					href="javascript:void(0)" onclick="closeEditUI()">关闭</a>
			</div>
		</div>
	</div>
</body>
</html>