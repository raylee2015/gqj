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
				initDataGridForDemandPlan();
				registerKeyPressForTextInput('keyWordForDemandPlanTextInput',
						queryDemandPlanPagesForSearch);
			});

	//用在点击查询按钮的时候
	function queryDemandPlanPagesForSearch() {
		queryDemandPlans();
	}

	//查询
	function queryDemandPlans() {
		var params = {
			keyWord : getTextBoxValue('keyWordForDemandPlanTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForDemandPlan')
		};
		query(params, 'queryDemandPlansPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForDemandPlan', result);
	}

	//初始化列表元素
	function initDataGridForDemandPlan() {
		$('#datagridForDemandPlan')
				.datagrid(
						{
							url : 'queryDemandPlansPage.do',
							idField : 'PLAN_ID',
							rownumbers : true,
							toolbar : '#toolbarForDemandPlan',
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
								field : 'PLAN_CODE',
								title : '计划名称',
								width : 250
							} , {
								field : 'PLAN_TYPE_NAME',
								title : '计划类型',
								width : 100
							} , {
								field : 'PLAN_CREATE_USER_NAME',
								title : '创建人',
								width : 100
							} , {
								field : 'PLAN_CREATE_DATE',
								title : '创建人',
								width : 100
							}] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForDemandPlanTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}
	//关闭编辑窗口
	function closeChooseDemandPlanUIForMaterialBill() {
		parent.closeChooseDemandPlanUIForMaterialBill();
	}

	function choose() {
		var selectedItems = $('#datagridForDemandPlan').datagrid(
				'getSelections');
		if (selectedItems.length == 0) {
			alert("请选择需求计划");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个需求计划");
			return;
		}
		parent.$('#planIdTextInput').textbox('setValue',
				selectedItems[0].PLAN_ID);
		parent.$('#planIdTextInput').textbox('setText',
				selectedItems[0].PLAN_ID);
		parent.$('#planNameBtn').linkbutton({
			text : selectedItems[0].PLAN_CODE,
			width : 200
		});
		closeChooseDemandPlanUIForMaterialBill();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<table id="datagridForDemandPlan" class="easyui-datagrid">
			</table>
			<div id="toolbarForDemandPlan">
				<table style="width: 100%">
					<tr>
						<td><a class="easyui-linkbutton"
							iconCls="icon-ok" href="javascript:void(0)"
							onclick="choose()">选择</a> <a
							class="easyui-linkbutton" iconCls="icon-cancel"
							href="javascript:void(0)"
							onclick="closeChooseDemandPlanUIForTemplate()">关闭</a></td>
						<td align="right"><input
							id="keyWordForDemandPlanTextInput"
							class="easyui-textbox"
							data-options="prompt:'需求计划名称',validType:'length[0,25]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryDemandPlanPagesForSearch()">查询</a>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>