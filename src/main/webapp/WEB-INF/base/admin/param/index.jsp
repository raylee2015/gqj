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
	function closeEditUIForParam() {
		closeEditUI('editUIForParam')
	}

	//打开编辑窗口
	function openAddUIForParam() {
		createModalDialog("editUIForParam", "openEditUI.do?opType=add",
				"添加全局参数", 400, 230);
		openEditUI('editUIForParam');
	}

	//打开编辑窗口
	function openEditUIForParam(rowIndex) {
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		var rowData = getRowDataOfSelfDataGrid('datagridForParam', rowIndex);
		createModalDialog("editUIForParam", url, ("修改全局参数\""
				+ rowData.PARAM_KEY + "\"的信息"), 400, 230);
		openEditUI('editUIForParam');
	}

	//删除
	function delParams() {
		if (checkSelectedItems('datagridForParam', '请选择全局参数')) {
			var paramIds = getIdsOfSelectedItems('datagridForParam', 'PARAM_ID');
			if (paramIds != null && paramIds != '') {
				var params = {
					PARAM_IDS : paramIds
				};
				showMessageBox(params, 'delParams.do', '是否删除所选全局参数?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForParam');
	}

	//用在点击查询按钮的时候
	function queryParamPageForSearch() {
		queryParams('');
	}

	//用在点击树的时候
	function queryParamPageForTree(menuId) {
		queryParams(menuId);
	}

	//查询
	function queryParams(menuId) {
		var params = {
			MENU_ID : menuId,
			keyWord : $('#keyWordForParamTextInput').textbox('getValue'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForParam')
		};
		query(params, 'queryParamsPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForParam', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				initDataGridForParam();
				initMenuTree();
				registerKeyPressForTextInput('keyWordForParamTextInput',
						queryParamPageForSearch);
			});

	//初始化树
	function initMenuTree() {
		$('#menuTree').tree({
			url : 'queryMenuTree.do',
			onClick : function(node) {
				queryParamPageForTree(node.id); // 在用户点击的时候提示
			},
			onLoadError : function(arguments) {
				errorFunctionForQuery();
			}
		});
	}

	//初始化列表元素
	function initDataGridForParam() {
		$('#datagridForParam')
				.datagrid(
						{
							url : 'queryParamsPage.do',
							idField : 'PARAM_ID',
							rownumbers : true,
							toolbar : '#toolbarForParam',
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
													+ ' onclick="openEditUIForParam(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'PARAM_KEY',
										title : '参数键',
										width : 200,
									}, {
										field : 'PARAM_VALUE',
										title : '参数值',
										width : 100,
									}, {
										field : 'PARAM_REMARK',
										title : '备注',
										width : 100,
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForParamTextInput');
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
		<div region="west" collapsible="false"
			style="width: 200px;">
			<ul id="menuTree" class="easyui-tree" method="get"
				animate="true" lines="true"></ul>
		</div>
		<div region="center">
			<table id="datagridForParam" class="easyui-datagrid">
			</table>
			<div id="toolbarForParam">
				<table style="width: 100%">
					<tr>
						<td><a href="#" class="easyui-linkbutton"
							iconCls="icon-reload" plain="true"
							onclick="refreshDataGrid('datagridForParam')">刷新</a>
							<a href="#" class="easyui-linkbutton"
							iconCls="icon-add" plain="true"
							onclick="openAddUIForParam()">添加</a> <a href="#"
							class="easyui-linkbutton" iconCls="icon-remove"
							plain="true" onclick="delParams()">删除</a></td>
						<td align="right"><input
							id="keyWordForParamTextInput" class="easyui-textbox"
							data-options="prompt:'参数键',validType:'length[0,50]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryParamPageForSearch()">查询</a>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>