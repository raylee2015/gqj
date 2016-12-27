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
	function closeEditUIForMaterial() {
		closeEditUI('editUIForMaterial')
	}

	//打开编辑窗口
	function openAddUIForMaterial() {
		createModalDialog("editUIForMaterial", "openEditUI.do?opType=add",
				"添加工器具", 500, 450);
		openEditUI('editUIForMaterial');
	}

	//打开编辑窗口
	function openEditUIForMaterial(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForMaterial', rowIndex);
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForMaterial", url, ("修改工器具\""
				+ rowData.MAT_NAME + "\"的信息"), 500, 450);
		openEditUI('editUIForMaterial');
	}

	//删除
	function delMaterials() {
		if (checkSelectedItems('datagridForMaterial', '请选择工器具')) {
			var matIds = getIdsOfSelectedItems('datagridForMaterial', 'MAT_ID');
			if (matIds != null && matIds != '') {
				var params = {
					MAT_IDS : matIds
				};
				showMessageBox(params, 'delMaterials.do', '是否删除所选工器具?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForMaterial');
	}

	//用在点击查询按钮的时候
	function queryMaterialPagesForSearch() {
		queryMaterials();
	}

	//查询
	function queryMaterials() {
		var params = {
			keyWord : getTextBoxValue('keyWordForMaterialTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForMaterial')
		};
		query(params, 'queryMaterialsPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForMaterial', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForMaterialTextInput',
						queryMaterialPagesForSearch);
				initDataGridForMaterial();
			});

	//初始化列表元素
	function initDataGridForMaterial() {
		$('#datagridForMaterial')
				.datagrid(
						{
							url : 'queryMaterialsPage.do',
							idField : 'MAT_ID',
							rownumbers : true,
							toolbar : '#toolbarForMaterial',
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
													+ ' onclick="openEditUIForMaterial(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									},{
										field : 'TYPE_NAME',
										title : '工器具类型',
										width : 100,
									}, {
										field : 'MAT_NAME',
										title : '工器具名称',
										width : 150,
									}, {
										field : 'MAT_SPEC',
										title : '工器具标准配置',
										width : 450,
									}, {
										field : 'MAT_MODEL',
										title : '工器具型号',
										width : 450,
									}, {
										field : 'MAT_UNIT_NAME',
										title : '工器具单位',
										width : 80,
									},  {
										field : 'MAT_REMARK',
										title : '备注',
										width : 80,
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
			<table id="datagridForMaterial" class="easyui-datagrid">
			</table>
			<div id="toolbarForMaterial">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refreshDataGrid('datagridForMaterial')">刷新</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true" onclick="openAddUIForMaterial()">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delMaterials()">删除</a>
				</div>
				<div>
					<input id="keyWordForMaterialTextInput" class="easyui-textbox"
						data-options="prompt:'工器具名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryMaterialPagesForSearch()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>