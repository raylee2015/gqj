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
	function closeEditUIForDictionary() {
		closeEditUI('editUIForDictionary')
	}

	//打开编辑窗口
	function openAddUIForDictionary() {
		createModalDialog("editUIForDictionary", "openEditUI.do?opType=add",
				"数据字典设置", 450, 300);
		openEditUI('editUIForDictionary');
	}

	//打开编辑窗口
	function openEditUIForDictionary(rowIndex) {
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForDictionary", url, "数据字典设置", 450, 300);
		openEditUI('editUIForDictionary');
	}

	//删除
	function delDictionarys() {
		if (checkSelectedItems('datagridForDictionary', '请选择数据字典')) {
			var dicIds = getIdsOfSelectedItems('datagridForDictionary',
					'DIC_ID');
			if (dicIds != null && dicIds != '') {
				var params = {
					DIC_IDS : dicIds
				};
				showMessageBox(params, 'delDictionarys.do', '是否删除所选数据字典?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForDictionary');
	}

	//用在点击查询按钮的时候
	function queryDictionaryPagesForSearch() {
		queryDictionarys('');
	}

	//用在点击树的时候
	function queryDictionaryPagesForTree(menuId) {
		queryDictionarys(menuId);
	}

	//查询
	function queryDictionarys(menuId) {
		var params = {
			MENU_ID : menuId,
			keyWord : $('#keyWordForDictionaryTextInput').textbox('getValue'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForDictionary')
		};
		query(params, 'queryDictionariesPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForDictionary', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				initDictionaryDataGrid();
				initMenuTree();
				registerKeyPressForTextInput('keyWordForDictionaryTextInput',
						queryDictionaryPagesForSearch);
			});

	//初始化树
	function initMenuTree() {
		$('#menuTree').tree({
			url : 'queryMenuTree.do',
			onClick : function(node) {
				queryDictionaryPagesForTree(node.id); // 在用户点击的时候提示
			},
			onLoadError : function(arguments) {
				errorFunctionForQuery();
			}
		});
	}

	//初始化列表元素
	function initDictionaryDataGrid() {
		$('#datagridForDictionary')
				.datagrid(
						{
							url : 'queryDictionariesPage.do',
							idField : 'DIC_ID',
							rownumbers : true,
							toolbar : '#toolbarForDictionary',
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
													+ ' onclick="openEditUIForDictionary(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'DIC_CODE',
										title : '字典代码',
										width : 100,
									}, {
										field : 'DIC_NAME',
										title : '字典名称',
										width : 100,
									}, {
										field : 'DIC_VALUE',
										title : '字典值',
										width : 100,
									}, {
										field : 'DIC_LABEL',
										title : '字典含义',
										width : 100,
									}, {
										field : 'DIC_SORT',
										title : '排序号',
										width : 100,
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
		<div region="west" collapsible="false" style="width: 200px;">
			<ul id="menuTree" class="easyui-tree" method="get" animate="true"
				lines="true"></ul>
		</div>
		<div region="center">
			<table id="datagridForDictionary" class="easyui-datagrid">
			</table>
			<div id="toolbarForDictionary">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refreshDataGrid('datagridForDictionary')">刷新</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true" onclick="openAddUIForDictionary()">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delDictionarys()">删除</a>
				</div>
				<div>
					<input id="keyWordForDictionaryTextInput" class="easyui-textbox"
						data-options="prompt:'字典名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryDictionaryPagesForSearch()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>