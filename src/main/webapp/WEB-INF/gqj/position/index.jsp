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
	function closeEditUIForPosition() {
		closeEditUI('editUIForPosition')
	}

	// 编辑窗口变大
	function makeEditUIBigger() {
		$('#editUIForPosition').panel('resize', {
			height : 500
		});
		$('#editUIForPosition').window('center');
	}

	// 编辑窗口变小
	function makeEditUISmaller() {
		$('#editUIForPosition').panel('resize', {
			height : 160
		});
		$('#editUIForPosition').window('center');
	}

	//打开编辑窗口
	function openAddUIForPosition() {
		createModalDialog("editUIForPosition", "openEditUI.do?opType=add",
				"添加仓位", 400, 160);
		openEditUI('editUIForPosition');
	}

	//打开编辑窗口
	function openEditUIForPosition(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForPosition', rowIndex);
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForPosition", url, ("修改仓位\""
				+ rowData.POS_NAME + "\"的信息"), 400, 160);
		openEditUI('editUIForPosition');
	}

	//删除
	function delPositions() {
		if (checkSelectedItems('datagridForPosition', '请选择仓位')) {
			var posIds = getIdsOfSelectedItems('datagridForPosition', 'POS_ID');
			if (posIds != null && posIds != '') {
				var params = {
					POS_IDS : posIds
				};
				showMessageBox(params, 'delPositions.do', '是否删除所选仓位?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForPosition');
	}

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

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForPositionTextInput',
						queryPositionPagesForSearch);
				initDataGridForPosition();
			});

	//初始化列表元素
	function initDataGridForPosition() {
		$('#datagridForPosition')
				.datagrid(
						{
							url : 'queryPositionsPage.do',
							idField : 'POS_ID',
							rownumbers : true,
							toolbar : '#toolbarForPosition',
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
													+ ' onclick="openEditUIForPosition(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'POS_NAME',
										title : '仓位名称',
										width : 150,
									}, {
										field : 'STORE_NAME',
										title : '所属仓库',
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
			<table id="datagridForPosition" class="easyui-datagrid">
			</table>
			<div id="toolbarForPosition">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refreshDataGrid('datagridForPosition')">刷新</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true" onclick="openAddUIForPosition()">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delPositions()">删除</a>
				</div>
				<div>
					<input id="keyWordForPositionTextInput" class="easyui-textbox"
						data-options="prompt:'仓位名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryPositionPagesForSearch()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>