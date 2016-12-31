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
				initDataGridForToolDemand();
				registerKeyPressForTextInput('keyWordForToolDemandTextInput',
						queryToolDemandPagesForSearch);
			});

	//用在点击查询按钮的时候
	function queryToolDemandPagesForSearch() {
		queryToolDemands();
	}

	//查询
	function queryToolDemands() {
		var params = {
			keyWord : getTextBoxValue('keyWordForToolDemandTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForToolDemand')
		};
		query(params, 'queryToolDemandsPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForToolDemand', result);
	}

	//初始化列表元素
	function initDataGridForToolDemand() {
		$('#datagridForToolDemand').datagrid({
			url : 'queryToolDemandsPage.do',
			idField : 'TOOL_ID',
			rownumbers : true,
			toolbar : '#toolbarForToolDemand',
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
				field : 'TOOL_TYPE_NAME',
				title : '工器具类型',
				width : 100,
			}, {
				field : 'TOOL_NAME',
				title : '工器具名称',
				width : 150,
			}, {
				field : 'TOOL_STANDARD_CONFIG_DEMAND',
				title : '工器具标准配置',
				width : 250,
			}, {
				field : 'TOOL_MODEL_DEMAND',
				title : '工器具型号',
				width : 250,
			}, {
				field : 'TOOL_UNIT_NAME',
				title : '工器具单位',
				width : 80,
			}, {
				field : 'TOOL_REMARK',
				title : '备注',
				width : 80,
			} ] ],
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}
	//关闭编辑窗口
	function closeChooseToolDemandUIForTemplate() {
		parent.closeChooseToolDemandUIForTemplate();
	}

	function choose() {
		var selectedItems = $('#datagridForToolDemand').datagrid(
				'getSelections');
		var data = {
			rows : selectedItems,
			total : selectedItems.length
		};
		parent.$('#datagridForTemplateDetail').datagrid('loadData', data);
		closeChooseToolDemandUIForTemplate();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<table id="datagridForToolDemand" class="easyui-datagrid">
			</table>
			<div id="toolbarForToolDemand">
				<div>
					<a class="easyui-linkbutton" iconCls="icon-ok"
						href="javascript:void(0)" onclick="choose()">选择</a> <a
						class="easyui-linkbutton" iconCls="icon-cancel"
						href="javascript:void(0)"
						onclick="closeChooseToolDemandUIForTemplate()">关闭</a>
					<input id="keyWordForToolDemandTextInput"
						class="easyui-textbox"
						data-options="prompt:'工器具名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryToolDemandPagesForSearch()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>