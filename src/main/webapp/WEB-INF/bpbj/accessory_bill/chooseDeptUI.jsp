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
				initDataGridForDept();
				registerKeyPressForTextInput('keyWordForDeptTextInput',
						queryDeptPagesForSearch);
			});

	//用在点击查询按钮的时候
	function queryDeptPagesForSearch() {
		queryDepts();
	}

	//查询
	function queryDepts() {
		var params = {
			keyWord : getTextBoxValue('keyWordForDeptTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForDept')
		};
		query(params, 'queryDeptsPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForDept', result);
	}

	//初始化列表元素
	function initDataGridForDept() {
		$('#datagridForDept').datagrid({
			url : 'queryDeptsPage.do',
			idField : 'DEPT_ID',
			rownumbers : true,
			toolbar : '#toolbarForDept',
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
				field : 'DEPT_NAME',
				title : '部门名称',
				width : 250
			} ] ],
			onBeforeLoad : function(param) {
				param.keyWord = getTextBoxValue('keyWordForDeptTextInput');
			},
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}
	//关闭编辑窗口
	function closeChooseDeptUIForAccessoryBill() {
		parent.closeChooseDeptUIForAccessoryBill();
	}

	function choose() {
		var selectedItems = $('#datagridForDept').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择部门");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个部门");
			return;
		}
		parent.$('#deptIdTextInput').textbox('setValue',
				selectedItems[0].DEPT_ID);
		parent.$('#deptIdTextInput').textbox('setText',
				selectedItems[0].DEPT_ID);
		parent.$('#deptNameBtn').linkbutton({
			text : selectedItems[0].DEPT_NAME,
			width : 200
		});
		closeChooseDeptUIForAccessoryBill();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<table id="datagridForDept" class="easyui-datagrid">
			</table>
			<div id="toolbarForDept">
				<table style="width: 100%">
					<tr>
						<td><a class="easyui-linkbutton"
							iconCls="icon-ok" href="javascript:void(0)"
							onclick="choose()">选择</a> <a
							class="easyui-linkbutton" iconCls="icon-cancel"
							href="javascript:void(0)"
							onclick="closeChooseDeptUIForTemplate()">关闭</a></td>
						<td align="right"><input
							id="keyWordForDeptTextInput" class="easyui-textbox"
							data-options="prompt:'部门名称',validType:'length[0,25]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryDeptPagesForSearch()">查询</a>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>