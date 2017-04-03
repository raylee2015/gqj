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
				initDataGridForUser();
				registerKeyPressForTextInput('keyWordForUserTextInput',
						queryUserPagesForSearch);
			});

	//用在点击查询按钮的时候
	function queryUserPagesForSearch() {
		queryUsers();
	}

	//初始化列表元素
	function initDataGridForUser() {
		$('#datagridForUser').datagrid({
			url : 'queryUsersPage.do',
			idField : 'USER_ID',
			rownumbers : true,
			toolbar : '#toolbarForUser',
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
				field : 'USER_NAME',
				title : '人员名称',
				width : 100,
			}, {
				field : 'USER_DEPT_NAME',
				title : '部门名称',
				width : 100,
			} ] ],
			onBeforeLoad : function(param) {
				param.keyWord = getTextBoxValue('keyWordForUserTextInput');
			},
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}
	//关闭编辑窗口
	function closeChooseUserUIForDemandPlan() {
		parent.closeChooseUserForAnnualPlanUI();
	}

	function choose() {
		var selectedItems = $('#datagridForUser').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert('请选择人员');
			return;
		} else if (selectedItems.length > 1) {
			alert('只能够选择一个人员');
			return;
		}
		updateCreateUserForAnnualPlan(selectedItems[0].USER_ID);
	}

	var url = "updateCreateUserForAnnualPlan.do";

	// 保存数据
	function updateCreateUserForAnnualPlan(userId) {
		var params = {
			USER_ID : userId,
			PLAN_ID : getTextBoxValue('planIdTextInput')
		};
		save(params, url, successFunctionForSavePosition);
	}

	//回调函数，保存后调用
	function successFunctionForSavePosition(result) {
		parent.successFunctionForOption(result);
		closeChooseUserUIForDemandPlan();
	}

	//查询
	function queryUsers() {
		var params = {
			keyWord : getTextBoxValue('keyWordForUserTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForUser')
		};
		query(params, 'queryUsersPage.do', successFunctionForQueryUsers);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryUsers(result) {
		dataGridLoadData('datagridForUser', result);
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="north" fit="true" border="false">
			<div style="display: none">
				<input id="planIdTextInput" class="easyui-textbox"
					value="<%=request.getParameter("planId")%>" /> 
			</div>
			<table id="datagridForUser" class="easyui-datagrid">
			</table>
			<div id="toolbarForUser">
				<table style="width: 100%">
					<tr>
						<td><a class="easyui-linkbutton"
							iconCls="icon-ok" href="javascript:void(0)"
							onclick="choose()">选择</a> <a
							class="easyui-linkbutton" iconCls="icon-cancel"
							href="javascript:void(0)"
							onclick="closeChooseUserUIForDemandPlan()">关闭</a></td>
						<td align="right"><input
							id="keyWordForUserTextInput" class="easyui-textbox"
							data-options="prompt:'人员名称',validType:'length[0,25]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryUserPagesForSearch()">查询</a>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>