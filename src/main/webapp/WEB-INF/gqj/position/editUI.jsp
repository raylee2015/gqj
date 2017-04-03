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
	function savePosition() {
		var params = {
			POS_ID : getTextBoxValue('positionIdTextBox'),
			POS_NAME : getTextBoxValue('positionNameTextBox'),
			STORE_ID : getTextBoxValue('storageIdTextBox')
		};
		save(params, url, successFunctionForSavePosition);
	}

	function successFunctionForSavePosition(result) {
		parent.successFunctionForOption(result);
		closeEditUIForPosition();
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput("keyWordForStorageTextInput",
						queryStorageForPage);
				initChooseStoragePanel();
				initStorageDataGrid();
				initPositionForm();
			});

	//初始化表单
	function initPositionForm() {
		var opType = getTextBoxValue('opType');
		var postId = getTextBoxValue('positionIdTextBox');
		if (opType == 'edit') {
			url = "updatePosition.do";
			var rowIndex = getTextBoxValue('rowIndex');
			var rowData = getRowDataOfParentDataGrid('datagridForPosition',
					rowIndex);
			$('#positionForm').form('load', rowData);
		} else if (opType == 'add') {
			url = "addNewPosition.do";
		}
	}

	//查询仓库
	function queryStorageForPage() {
		var params = {
			keyWord : getTextBoxValue('keyWordForStorageTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForStorage')
		};
		query(params, 'queryStoragePage.do', successFunctionForQueryStorage);
	}

	//当查询成功时需要执行的代码
	function successFunctionForQueryStorage(result) {
		dataGridLoadData('datagridForStorage', result);
	}

	//初始化选择仓库版面
	function initChooseStoragePanel() {
		$('#chooseStoragePanel').panel({
			closed : true
		});
	}

	//关闭表单版面，开放选择仓库版面
	function openChooseStoragePanelAndCloseFormPanel() {
		$('#chooseStoragePanel').panel('open');
		$('#formPanel').panel('close');
		parent.makeEditUIBigger();
	}

	//关闭选择仓库版面，开放表单版面
	function openFormPanelAndCloseChooseStoragePanel() {
		$('#formPanel').panel('open');
		$('#chooseStoragePanel').panel('close');
		parent.makeEditUISmaller();
	}

	//初始化列表元素
	function initStorageDataGrid() {
		$('#datagridForStorage').datagrid({
			url : 'queryStoragePage.do',
			idField : 'STORE_ID',
			rownumbers : true,
			toolbar : '#toolbarForStorage',
			pagination : true,
			pageSize : 30,
			pageNumber : 1,
			checkOnSelect : false,
			fit : true,
			method : 'get',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'STORE_NAME',
				title : '仓库名称',
				width : 100,
			} ] ],
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}

	//选择仓库
	function chooseStorage() {
		var selectedItems = $('#datagridForStorage').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择仓库");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个仓库");
			return;
		} else {
			var selectedItem = selectedItems[0];
			setTextBoxValue('storeNameTextBox', selectedItem.STORE_NAME);
			setTextBoxValue('storageIdTextBox', selectedItem.STORE_ID);
			openFormPanelAndCloseChooseStoragePanel();
		}
	}

	//关闭编辑界面
	function closeEditUIForPosition() {
		parent.closeEditUIForPosition();
	}
</script>
</head>
<body>
	<div id="formPanel" class="easyui-panel"
		data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="fit:true,border:false,region:'north'">
				<form id="positionForm" method="post" style="width: 100%;">
					<div style="display: none">
						<input id="opType" class="easyui-textbox"
							value="<%=request.getParameter("opType")%>" /> <input
							id="positionIdTextBox" class="easyui-textbox" name="POS_ID"
							 /> <input id="rowIndex" class="easyui-textbox"
							value="<%=request.getParameter("rowIndex")%>" /> <input
							id="storageIdTextBox" class="easyui-textbox" name="STORE_ID" />
					</div>
					<table width="100%">
						<tr>
							<td width="22%">所属仓库:</td>
							<td><input id="storeNameTextBox" name="STORE_NAME" disabled
								class="easyui-textbox" style="width: 70%; height: 32px" /> <a
								href="#" class="easyui-linkbutton"
								style="width: 29%; height: 32px;"
								onclick="openChooseStoragePanelAndCloseFormPanel()"> 选择仓库</a></td>
						</tr>
						<tr>
							<td width="22%">仓位名称:</td>
							<td><input id="positionNameTextBox" name="POS_NAME"
								class="easyui-textbox"
								data-options="prompt:'仓位名称',required:true,validType:'length[0,20]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px">
				<a class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="savePosition()">保存</a> <a
					class="easyui-linkbutton" iconCls="icon-cancel"
					href="javascript:void(0)" onclick="closeEditUIForPosition()">关闭</a>
			</div>
		</div>
	</div>
	<div id="chooseStoragePanel" class="easyui-panel" fit="true"
		border="false">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" border="false" style="height: 30px">
				<a href="#" class="easyui-linkbutton"
					onclick="openFormPanelAndCloseChooseStoragePanel()">返回</a> <a
					href="#" class="easyui-linkbutton" onclick="chooseStorage()">选择</a>
			</div>
			<div region="center" border="false">
				<table id="datagridForStorage" class="easyui-datagrid">
				</table>
				<div id="toolbarForStorage">
					<input id="keyWordForStorageTextInput" class="easyui-textbox"
						data-options="prompt:'仓库名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryStorageForPage()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>