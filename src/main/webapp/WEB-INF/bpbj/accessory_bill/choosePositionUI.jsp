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
	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				initDataGridForPosition();
				registerKeyPressForTextInput('keyWordForPositionTextInput',
						queryPositionPagesForSearch);
			});

	//用在点击查询按钮的时候
	function queryPositionPagesForSearch() {
		queryPositions();
	}

	//查询
	function queryPositions() {
		var params = {
			keyWord : getTextBoxValue('keyWordForPositionTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForPosition')
		};
		query(params, 'queryPositionsPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForPosition', result);
	}

	//初始化列表元素
	function initDataGridForPosition() {
		$('#datagridForPosition')
				.datagrid(
						{
							url : 'queryPositionsPage.do?STORE_ID='
									+ getTextBoxValue('storageIdTextBox'),
							idField : 'POS_ID',
							rownumbers : true,
							toolbar : '#toolbarForPosition',
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
								field : 'POS_NAME',
								title : '仓位名称',
								width : 150
							} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForPositionTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}
	//关闭编辑窗口
	function closeChoosePositionUIForMaterialBill() {
		parent.closeChoosePositionUIForMaterialBill();
	}

	function choose() {
		var selectedItems = $('#datagridForPosition').datagrid('getChecked');
		var data = parent.$('#datagridForMaterialBillDetail').datagrid(
				'getData');
		if (selectedItems.length == 0) {
			alert("请选择仓位");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个仓位");
			return;
		}
		var rowIndex = getTextBoxValue('rowIndex');
		for (var i = 0; i < data.rows.length; i++) {
			if (i == rowIndex) {
				data.rows[i].POS_NAME = selectedItems[0].POS_NAME;
				data.rows[i].POS_ID = selectedItems[0].POS_ID;
				break;
			}
		}
		parent.$('#datagridForMaterialBillDetail').datagrid('loadData', data);
		parent.$('#datagridForMaterialBillDetail').datagrid('unselectAll');
		closeChoosePositionUIForMaterialBill();
	}
</script>
</head>
<body>
	<div style="display: none">
		<input id="rowIndex" class="easyui-textbox"
			value="<%=request.getParameter("rowIndex")%>" /> <input
			id="storageIdTextBox" class="easyui-textbox"
			name="STORE_ID"
			value="<%=request.getParameter("STORE_ID")%>" />
	</div>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<table id="datagridForPosition" class="easyui-datagrid">
			</table>
			<div id="toolbarForPosition">
				<table style="width: 100%">
					<tr>
						<td><a class="easyui-linkbutton"
							iconCls="icon-ok" href="javascript:void(0)"
							onclick="choose()">选择</a> <a
							class="easyui-linkbutton" iconCls="icon-cancel"
							href="javascript:void(0)"
							onclick="closeChoosePositionUIForTemplate()">关闭</a></td>
						<td align="right"><input
							id="keyWordForPositionTextInput"
							class="easyui-textbox"
							data-options="prompt:'仓位名称',validType:'length[0,25]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryPositionPagesForSearch()">查询</a>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>