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
				initDataGridForStorage();
				registerKeyPressForTextInput('keyWordForStorageTextInput',
						queryStoragePagesForSearch);
			});

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

	//初始化列表元素
	function initDataGridForStorage() {
		$('#datagridForStorage').datagrid({
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
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'STORE_NAME',
				title : '仓库名称',
				width : 150
			} ] ],
			onBeforeLoad : function(param) {
				param.keyWord = getTextBoxValue('keyWordForStorageTextInput');
			},
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}
	//关闭编辑窗口
	function closeChooseStorageUIForMaterialBill() {
		parent.closeChooseStorageUIForMaterialBill();
	}

	function choose() {
		var selectedItems = $('#datagridForStorage').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择仓库");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个仓库");
			return;
		}
		parent.$('#storageIdTextInput').textbox('setValue',
				selectedItems[0].STORE_ID);
		parent.$('#storageIdTextInput').textbox('setText',
				selectedItems[0].STORE_ID);
		parent.$('#storageNameBtn').linkbutton({
			text : selectedItems[0].STORE_NAME,
			width : 200
		});
		closeChooseStorageUIForMaterialBill();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<table id="datagridForStorage" class="easyui-datagrid">
			</table>
			<div id="toolbarForStorage">
				<table style="width: 100%">
					<tr>
						<td><a class="easyui-linkbutton"
							iconCls="icon-ok" href="javascript:void(0)"
							onclick="choose()">选择</a> <a
							class="easyui-linkbutton" iconCls="icon-cancel"
							href="javascript:void(0)"
							onclick="closeChooseStorageUIForMaterialBill()">关闭</a></td>
						<td align="right"><input
							id="keyWordForStorageTextInput"
							class="easyui-textbox"
							data-options="prompt:'仓库名称',validType:'length[0,25]'"
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