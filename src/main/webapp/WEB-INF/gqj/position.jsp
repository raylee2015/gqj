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
	//记录新增或者修改的方法
	var url;

	//打开编辑窗口
	function openAddUI() {
		openAddDataUI1();
		setTextBoxValue('positionIdTextBox', -1);
		url = 'addNewPosition.do';
	}

	//打开编辑窗口
	function openEditUI(opType, rowIndex) {
		var rowData = $('#datagrid').datagrid('getData').rows[rowIndex];
		openEditDataUI1(rowData);
		url = 'updatePosition.do';
	}

	//删除
	function delPositions() {
		var ids = getIdsOfSelectedItems('POS_ID', '数据操作出现问题，请联系系统管理员');
		var params = {
			POS_IDS : ids
		};
		del(params, "请选择仓位", '是否删除所选仓位?', 'delPositions.do', true);
	}

	// 保存数据
	function savePosition() {
		var params = {
			POS_ID : getTextBoxValue('positionIdTextBox'),
			POS_NAME : getTextBoxValue('positionNameTextBox'),
			POS_SORT : getTextBoxValue('positionSortTextBox')
		};
		save1(params, url, true);
	}

	//查询
	function queryForPage() {
		var params = {
			keyWord : $('#keyWordTextInput').textbox('getValue'),
			page : 1,
			rows : $('#datagrid').datagrid('getPager').data("pagination").options.pageSize
		};
		query(params, 'queryPositionPage.do');
	}

	//页面加载完
	$(document).ready(
			function() {
				initDocument();
				initDataGrid();
				initChooseStoragePanel();
				initStorageDataGrid();
				registerKeyPressForTextInput("keyWordForStorageTextInput",
						queryStorageForPage);
			});

	//查询仓库
	function queryStorageForPage() {
		var params = {
			keyWord : $('#keyWordForStorageTextInput').textbox('getValue'),
			page : 1,
			rows : $('#datagridForStorage').datagrid('getPager').data(
					"pagination").options.pageSize
		};
		ajaxFunction(params, 'queryStoragePage.do',
				successFunctionForQueryStorage, errorFunctionForQuery, false);
	}

	// 当查询成功时需要执行的代码
	function successFunctionForQueryStorage(result, haveTree) {
		$('#datagridForStorage').datagrid('loadData', result);
	}

	//初始化列表元素
	function initDataGrid() {
		$('#datagrid').datagrid({
			url : 'queryPositionPage.do',
			idField : 'POS_ID',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'op',
				title : '操作',
				formatter : editColumnFormatter
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
				eval(errorCodeForQuery);
			}
		});
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

		$('#editUI').panel('resize', {
			height : 500
		});

		$('#editUI').panel('move', {
			top : 100
		});

		$('#editUI').panel('doLayout');
	}

	//关闭选择仓库版面，开放表单版面
	function openFormPanelAndCloseChooseStoragePanel() {
		$('#formPanel').panel('open');
		$('#chooseStoragePanel').panel('close');
		$('#editUI').panel('resize', {
			height : 190
		});

		$('#editUI').panel('move', {
			top : 200
		});

		$('#editUI').panel('doLayout');
	}

	//初始化列表元素
	function initStorageDataGrid() {
		$('#datagridForStorage').datagrid({
			url : 'queryStoragePage.do',
			idField : 'STORE_ID',
			columns : [ [ {
				field : 'ck',
				checkbox : true
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
	
	function chooseStorage() {
		
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
						iconCls="icon-remove" plain="true" onclick="delPositions()">删除</a>
				</div>
				<div>
					<input id="keyWordTextInput" class="easyui-textbox"
						data-options="prompt:'仓位名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryForPage()">查询</a>
				</div>

			</div>
		</div>
	</div>

	<!--  详细界面 -->
	<div id="editUI" class="easyui-window" title="添加仓位" closed="true"
		data-options="iconCls:'icon-save',shadow:false"
		style="width: 400px; height: 190px; padding: 5px;">
		<div id="formPanel" class="easyui-panel" fit="true" border="false">
			<div class="easyui-layout" data-options="fit:true">
				<div region="north" fit="true" border="false">
					<form id="form" method="post" style="width: 100%;">
						<div style="display: none">
							<input id="positionIdTextBox" name="POS_ID"
								class="easyui-textbox" />
						</div>
						<table width="100%">
							<tr>
								<td width="22%">所属仓库:</td>
								<td><a href="#" class="easyui-linkbutton"
									style="width: 100%; height: 32px;"
									onclick="openChooseStoragePanelAndCloseFormPanel()"></a></td>
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
						href="javascript:void(0)" onclick="closeEditUI()">关闭</a>
				</div>
			</div>
		</div>
		<div id="chooseStoragePanel" class="easyui-panel" fit="true"
			border="false">
			<div class="easyui-layout" data-options="fit:true">
				<div region="north" border="false" style="height: 30px">
					<a href="#" class="easyui-linkbutton"
						onclick="openFormPanelAndCloseChooseStoragePanel()">返回</a> <a
						href="#" class="easyui-linkbutton"
						onclick="chooseStorage()">选择</a>
				</div>
				<div region="center" border="false">
					<table id="datagridForStorage" class="easyui-datagrid"
						toolbar="#toolbarForStorage" rownumbers="true" pagination="true"
						pageSize="30" pageNumber="1" checkOnSelect="false" method="get"
						fit="true">
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
	</div>
</body>
</html>