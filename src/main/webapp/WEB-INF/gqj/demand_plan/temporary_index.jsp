<%@page import="com.base.admin.entity.User"%>
<%@page import="com.base.util.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String opType = request.getParameter("OP_TYPE");
	String planType = request.getParameter("PLAN_TYPE");
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
	href="<%=contextPath%>/css/base.css">
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
	function closeChooseToolDemandUIForDemandPlan() {
		closeUI('chooseToolDemandUIForDemandPlan')
	}

	//打开选择工器具窗口
	function openChooseToolDemandUIForDemandPlan() {
		createModalDialog("chooseToolDemandUIForDemandPlan",
				"openChooseToolDemandUI.do?opType=add", "添加工器具需求计划", 1000, 600);
		openUI('chooseToolDemandUIForDemandPlan');
	}

	//删除
	function delDemandPlans() {
		if (checkSelectedItems('datagridForDemandPlan', '请选择工器具需求计划')) {
			var ids = getIdsOfSelectedItems('datagridForDemandPlan', 'PLAN_ID');
			if (ids != null && ids != '') {
				var params = {
					PLAN_IDS : ids
				};
				showMessageBox(params, 'deleteDemandPlansAndDetails.do',
						'是否删除所选需求计划?', successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForDemandPlan');
	}

	//用在点击查询按钮的时候
	function queryDemandPlanPagesForSearch() {
		queryDemandPlans();
	}

	//查询
	function queryDemandPlans() {
		var params = {
			OP_TYPE : getTextBoxValue('opType'),
			PLAN_TYPE : 1,
			keyWord : getTextBoxValue('keyWordForDemandPlanTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForDemandPlan')
		};
		query(params, 'queryDemandPlansPage.do',
				successFunctionForQueryDemandPlans);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryDemandPlans(result) {
		dataGridLoadData('datagridForDemandPlan', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForDemandPlanTextInput',
						queryDemandPlanPagesForSearch);
				initDataGridForDemandPlan();
				initDataGridForDemandPlanDetail();
			});

	//初始化列表元素
	function initDataGridForDemandPlan() {
		$('#datagridForDemandPlan')
				.datagrid(
						{
							url : 'queryDemandPlansPage.do?PLAN_TYPE=1&OP_TYPE='
									+ getTextBoxValue('opType'),//查询临时计划
							idField : 'PLAN_ID',
							rownumbers : true,
							toolbar : '#toolbarForDemandPlan',
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
										field : 'edit',
										title : '操作',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var status = rowData.PLAN_STATUS;
											var btn = ''
											if (status == 0 || status == 3
													|| status == 5) {
												btn = '<a class="easyui-linkbutton" '
														+ ' onclick="toDetail(\''
														+ rowIndex
														+ '\')" href="javascript:void(0)">编辑</a>';
											} else {
												btn = '<a class="easyui-linkbutton" '
														+ ' onclick="toDetail(\''
														+ rowIndex
														+ '\')" href="javascript:void(0)">查看</a>';
											}
											return btn;
										}
									}, {
										field : 'PLAN_STATUS_NAME',
										title : '计划状态',
										width : 100,
									}, {
										field : 'PLAN_DEPT_NAME',
										title : '创建部门',
										width : 120,
									}, {
										field : 'PLAN_CODE',
										title : '计划名称',
										width : 300,
									}, {
										field : 'PLAN_CREATE_USER_NAME',
										title : '创建人',
										width : 100,
									}, {
										field : 'PLAN_CREATE_DATE',
										title : '创建日期',
										width : 100,
									}, {
										field : 'PLAN_REMARK',
										title : '备注',
										width : 100,
									} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForDemandPlanTextInput');
							},
							onLoadSuccess : function(data) {
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForDemandPlanDetail() {
		$('#datagridForDemandPlanDetail')
				.datagrid(
						{
							idField : 'DETAIL_ID',
							rownumbers : true,
							toolbar : '#toolbarForDemandPlanDetail',
							pagination : false,
							checkOnSelect : false,
							fit : true,
							singleSelect : true,
							method : 'get',
							columns : [ [
									{
										field : 'op',
										title : '操作',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = '<a class="easyui-linkbutton" '
													+ ' onclick="delRowData(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">删除</a>';
											return btn;
										}
									},
									{
										field : 'TOOL_TYPE_NAME',
										title : '工器具类型',
										width : 100,
									},
									{
										field : 'TOOL_NAME',
										title : '工器具名称',
										width : 150,
									},
									{
										field : 'TOOL_STANDARD_CONFIG_DEMAND',
										title : '工器具标准配置',
										width : 250,
									},
									{
										field : 'TOOL_MODEL_DEMAND',
										title : '工器具型号',
										width : 250,
									},
									{
										field : 'TOOL_UNIT_NAME',
										title : '工器具单位',
										width : 80,
									},
									{
										field : 'TOOL_AMOUNT',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var toolAmount = "";
											if (typeof (rowData.TOOL_AMOUNT) == 'undefined') {
												toolAmount = "";
											} else {
												toolAmount = rowData.TOOL_AMOUNT;
											}
											var textInput = '<input value="'
													+ toolAmount
													+ '" type="text" onchange="setToolAmount('
													+ rowIndex
													+ ',this.value)" />';
											return textInput;
										},
										title : '数量',
										width : 80,
									} ] ],
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//设置工器具数量
	function setToolAmount(rowIndex, newValue) {
		var rowData = getRowDataOfSelfDataGrid('datagridForDemandPlanDetail',
				rowIndex);
		rowData.TOOL_AMOUNT = newValue;
	}

	//删除行数据
	function delRowData(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForDemandPlanDetail',
				rowIndex);
		var data = $('#datagridForDemandPlanDetail').datagrid('getData').rows;
		for (var i = 0; i < data.length; i++) {
			if (rowData.TOOL_ID == data[i].TOOL_ID) {
				data.splice(i, 1);
				break;
			}
		}
		dataGridLoadData('datagridForDemandPlanDetail', data);
	}

	//操作类型
	var opType = '';

	//记录新增或者修改的方法
	var url;

	var rowIndexOfDataGrid = 0;

	//编辑界面
	function toDetail(rowIndex) {
		if (rowIndex != null) {
			opType = 'edit';
			rowIndexOfDataGrid = rowIndex;
			var rowData = getRowDataOfSelfDataGrid('datagridForDemandPlan',
					rowIndex);
			var planStatus = rowData.PLAN_STATUS;
			queryDemandPlanDetailsForList(rowData);
			setTextBoxValue('demandPlanCodeTextInput', rowData.PLAN_CODE);
			setTextBoxValue('demandPlanRemarkTextInput', rowData.PLAN_REMARK);
			if (planStatus == 1 || planStatus == 2 || planStatus == 4) {
				$('#addToolBtn').linkbutton('disable');
				$('#saveBtn').linkbutton('disable');
				$('#submitBtn').linkbutton('disable');
			} else {
				$('#addToolBtn').linkbutton('enable');
				$('#saveBtn').linkbutton('enable');
				$('#submitBtn').linkbutton('enable');
			}
		} else {
			setTextBoxValue('demandPlanCodeTextInput',
					getTextBoxValue('planCode'));
			opType = 'add';
		}

		$('#demandPlanListUI').panel('collapse');
		$('#demandPlanDetailUI').panel('expand');
	}

	//根据需求计划id查询明细
	function queryDemandPlanDetailsForList(rowData) {
		var params = {
			PLAN_ID : rowData.PLAN_ID,
		};
		query(params, 'queryDemandPlanDetailsForList.do',
				successFunctionForQueryDemandPlanDetails);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryDemandPlanDetails(result) {
		dataGridLoadData('datagridForDemandPlanDetail', result);
	}

	//列表界面
	function toList() {
		$('#demandPlanListUI').panel('expand');
		$('#demandPlanDetailUI').panel('collapse');
		rowIndexOfDataGrid = 0;
		setTextBoxValue('demandPlanCodeTextInput', '');
		//清空明细列表
		dataGridLoadData('datagridForDemandPlanDetail', {
			total : 0,
			rows : []
		});
	}

	// 保存数据
	function saveDemandPlan() {
		var rowData = null;
		var params = null;
		var demandPlanCode = getTextBoxValue('demandPlanCodeTextInput');
		var demandPlanRemark = getTextBoxValue('demandPlanRemarkTextInput');
		if (demandPlanCode == null || demandPlanCode == '') {
			alert('请填写需求计划名称');
			return;
		}
		var demandPlanDetails = $('#datagridForDemandPlanDetail').datagrid(
				'getData').rows;
		if (demandPlanDetails.length == 0) {
			alert('请选择工器具');
			return;
		}
		var toolIds = '';
		var toolAmounts = '';
		for (var i = 0; i < demandPlanDetails.length; i++) {
			if (demandPlanDetails[i].TOOL_AMOUNT != null) {
				toolIds += demandPlanDetails[i].TOOL_ID + ',';
				toolAmounts += demandPlanDetails[i].TOOL_AMOUNT + ',';
			}
		}
		toolIds = toolIds.substring(0, toolIds.length - 1);
		toolAmounts = toolAmounts.substring(0, toolAmounts.length - 1);

		if (opType == 'add') {
			params = {
				PLAN_ID : '',
				PLAN_TYPE : 1,//临时计划
				PLAN_CODE : demandPlanCode,
				TOOL_IDS : toolIds,
				TOOL_AMOUNTS : toolAmounts,
				PLAN_REMARK : demandPlanRemark
			};
			url = "addNewDemandPlansAndDetails.do";
		} else if (opType == 'edit') {
			rowData = getRowDataOfSelfDataGrid('datagridForDemandPlan',
					rowIndexOfDataGrid);
			params = {
				PLAN_ID : rowData.PLAN_ID,
				PLAN_TYPE : 1,//临时计划
				PLAN_CODE : demandPlanCode,
				TOOL_IDS : toolIds,
				TOOL_AMOUNTS : toolAmounts,
				PLAN_REMARK : demandPlanRemark
			};
			url = "updateDemandPlansAndDetails.do";
		}
		save(params, url, successFunctionForSave);
	}

	// 提交计划
	function submitDemandPlan() {
		var rowData = null;
		var params = null;
		var demandPlanCode = getTextBoxValue('demandPlanCodeTextInput');
		var demandPlanRemark = getTextBoxValue('demandPlanRemarkTextInput');
		if (demandPlanCode == null || demandPlanCode == '') {
			alert('请填写需求计划名称');
			return;
		}
		var demandPlanDetails = $('#datagridForDemandPlanDetail').datagrid(
				'getData').rows;
		if (demandPlanDetails.length == 0) {
			alert('请选择工器具');
			return;
		}
		var toolIds = '';
		var toolAmounts = '';
		for (var i = 0; i < demandPlanDetails.length; i++) {
			if (demandPlanDetails[i].TOOL_AMOUNT != null) {
				toolIds += demandPlanDetails[i].TOOL_ID + ',';
				toolAmounts += demandPlanDetails[i].TOOL_AMOUNT + ',';
			}
		}
		toolIds = toolIds.substring(0, toolIds.length - 1);
		toolAmounts = toolAmounts.substring(0, toolAmounts.length - 1);
		if (opType == 'add') {
			params = {
				PLAN_STATUS : 1,
				PLAN_ID : '',
				PLAN_TYPE : 1,//临时计划
				PLAN_CODE : demandPlanCode,
				TOOL_IDS : toolIds,
				TOOL_AMOUNTS : toolAmounts,
				PLAN_REMARK : demandPlanRemark,
			};
			url = "addNewDemandPlansAndDetails.do";
		} else if (opType == 'edit') {
			rowData = getRowDataOfSelfDataGrid('datagridForDemandPlan',
					rowIndexOfDataGrid);
			params = {
				PLAN_STATUS : 1,
				PLAN_ID : rowData.PLAN_ID,
				PLAN_TYPE : 1,//临时计划
				PLAN_CODE : demandPlanCode,
				TOOL_IDS : toolIds,
				TOOL_AMOUNTS : toolAmounts,
				PLAN_REMARK : demandPlanRemark,
			};
			url = "updateDemandPlansAndDetails.do";
		}
		showMessageBox(params, url, '是否提交所选需求计划?', successFunctionForSave);
	}

	// 通过计划
	function passDemandPlanByWorkGroup() {
		var ids = getRowDataOfSelfDataGrid('datagridForDemandPlan',
				rowIndexOfDataGrid).PLAN_ID;
		var params = {
			PLAN_IDS : ids,
			PLAN_STATUS : 2
		};
		showMessageBox(params, 'updateDemandPlanStatus.do', '是否通过所选需求计划?',
				successFunctionForSave);
	}

	// 不通过计划
	function unPassDemandPlanByWorkGroup() {
		var ids = getRowDataOfSelfDataGrid('datagridForDemandPlan',
				rowIndexOfDataGrid).PLAN_ID;
		var params = {
			PLAN_IDS : ids,
			PLAN_STATUS : 3
		};
		showMessageBox(params, 'updateDemandPlanStatus.do', '是否不通过所选需求计划?',
				successFunctionForSave);
	}

	// 通过计划
	function unPassDemandPlanByDept() {
		var ids = getRowDataOfSelfDataGrid('datagridForDemandPlan',
				rowIndexOfDataGrid).PLAN_ID;
		var params = {
			PLAN_IDS : ids,
			PLAN_STATUS : 5
		};
		showMessageBox(params, 'updateDemandPlanStatus.do', '是否通过所选需求计划?',
				successFunctionForSave);
	}

	// 不通过计划
	function passDemandPlanByDept() {
		var ids = getRowDataOfSelfDataGrid('datagridForDemandPlan',
				rowIndexOfDataGrid).PLAN_ID;
		var params = {
			PLAN_IDS : ids,
			PLAN_STATUS : 4
		};
		showMessageBox(params, 'updateDemandPlanStatus.do', '是否不通过所选需求计划?',
				successFunctionForSave);
	}

	// 提交计划
	function submitDemandPlans() {
		if (checkSelectedItems('datagridForDemandPlan', '请选择需求计划')) {
			var ids = getIdsOfSelectedItems('datagridForDemandPlan', 'PLAN_ID');
			if (ids != null && ids != '') {
				var params = {
					PLAN_IDS : ids,
					PLAN_STATUS : 1
				};
				showMessageBox(params, 'updateDemandPlanStatus.do',
						'是否提交所选需求计划?', successFunctionForSave);
			}
		}
	}

	// 通过计划
	function passDemandPlansByWorkGroup() {
		if (checkSelectedItems('datagridForDemandPlan', '请选择需求计划')) {
			var ids = getIdsOfSelectedItems('datagridForDemandPlan', 'PLAN_ID');
			if (ids != null && ids != '') {
				var params = {
					PLAN_IDS : ids,
					PLAN_STATUS : 2
				};
				showMessageBox(params, 'updateDemandPlanStatus.do',
						'是否通过所选需求计划?', successFunctionForSave);
			}
		}
	}

	// 不通过计划
	function unPassDemandPlansByWorkGroup() {
		if (checkSelectedItems('datagridForDemandPlan', '请选择需求计划')) {
			var ids = getIdsOfSelectedItems('datagridForDemandPlan', 'PLAN_ID');
			if (ids != null && ids != '') {
				var params = {
					PLAN_IDS : ids,
					PLAN_STATUS : 3
				};
				showMessageBox(params, 'updateDemandPlanStatus.do',
						'是否不通过所选需求计划?', successFunctionForSave);
			}
		}
	}

	// 通过计划
	function passDemandPlansByDept() {
		if (checkSelectedItems('datagridForDemandPlan', '请选择需求计划')) {
			var ids = getIdsOfSelectedItems('datagridForDemandPlan', 'PLAN_ID');
			if (ids != null && ids != '') {
				var params = {
					PLAN_IDS : ids,
					PLAN_STATUS : 4
				};
				showMessageBox(params, 'updateDemandPlanStatus.do',
						'是否通过所选需求计划?', successFunctionForSave);
			}
		}
	}

	// 不通过计划
	function unPassDemandPlansByDept() {
		if (checkSelectedItems('datagridForDemandPlan', '请选择需求计划')) {
			var ids = getIdsOfSelectedItems('datagridForDemandPlan', 'PLAN_ID');
			if (ids != null && ids != '') {
				var params = {
					PLAN_IDS : ids,
					PLAN_STATUS : 5
				};
				showMessageBox(params, 'updateDemandPlanStatus.do',
						'是否不通过所选需求计划?', successFunctionForSave);
			}
		}
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		successFunctionForOption(result);
		toList();
	}

	//导出计划
	function exportDemandPlans() {
		if (checkSelectedItems('datagridForDemandPlan', '请选择需求计划')) {
			var ids = getIdsOfSelectedItems('datagridForDemandPlan', 'PLAN_ID');
			if (ids != null && ids != '') {
				$.messager
						.confirm(
								'确认',
								"是否导出相关计划？",
								function(confirmOrNot) {
									if (confirmOrNot) {
										window.location.href = getTextBoxValue("contextPath")
												+ "/gqj/demand_plan/exportDemandPlans.do?PLAN_IDS="
												+ ids;
										reloadDataGrid('datagridForDemandPlan');
									}
								});
			}
		}
	}
</script>
</head>
<body>
	<div style="display: none">
		<input id="opType" class="easyui-textbox" value="<%=opType%>" /><input
			id="planType" class="easyui-textbox" value="<%=planType%>" /> <input
			id="planCode" class="easyui-textbox"
			value="<%=(DateUtil.getNow() + " "
							+ ((User) request.getSession()
									.getAttribute("user"))
											.getUserDeptName()
							+ "临时需求计划")%>" />
	</div>
	<div id="demandPlanListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="fit:true,border:false,region:'center'">
				<table id="datagridForDemandPlan" class="easyui-datagrid">
				</table>
				<div id="toolbarForDemandPlan">
					<table style="width: 100%">
						<tr>
							<td><a href="#" class="easyui-linkbutton"
								iconCls="icon-reload" plain="true"
								onclick="refreshDataGrid('datagridForDemandPlan')">刷新</a><a
								href="#" class="easyui-linkbutton" iconCls="icon-base-download"
								plain="true" onclick="exportDemandPlans()">导出计划</a> <%
 	if ("EDIT".equals(opType)) {
 %> <a href="#" class="easyui-linkbutton" iconCls="icon-add"
								plain="true" onclick="toDetail()">添加</a> <a href="#"
								class="easyui-linkbutton" iconCls="icon-remove" plain="true"
								onclick="delDemandPlans()">删除</a> <a href="#"
								class="easyui-linkbutton" iconCls="icon-edit" plain="true"
								onclick="submitDemandPlans()">提交</a> <%
 	} else if ("AUDIT_BY_WORK_GROUP".equals(opType)) {
 %> <a href="#" class="easyui-linkbutton" iconCls="icon-application_go"
								plain="true" onclick="passDemandPlansByWorkGroup()">通过</a> <a
								href="#" class="easyui-linkbutton" iconCls="icon-cross"
								plain="true" onclick="unPassDemandPlansByWorkGroup()">不通过</a> <%
 	} else if ("AUDIT_BY_DEPT".equals(opType)) {
 %> <a href="#" class="easyui-linkbutton" iconCls="icon-application_go"
								plain="true" onclick="passDemandPlansByDept()">通过</a> <a
								href="#" class="easyui-linkbutton" iconCls="icon-cross"
								plain="true" onclick="unPassDemandPlansByDept()">不通过</a> <%
 	}
 %></td>
							<td align="right"><input id="keyWordForDemandPlanTextInput"
								class="easyui-textbox"
								data-options="prompt:'需求计划名称',validType:'length[0,50]'"
								style="width: 200px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryDemandPlanPagesForSearch()">查询</a>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="demandPlanDetailUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<div style="display: none">
			<input id="demandPlanIdTextInput" class="easyui-textbox" /> <input
				id="demandPlanStatusTextInput" class="easyui-textbox" />
		</div>
		<table id="datagridForDemandPlanDetail" class="easyui-datagrid">
		</table>
		<div id="toolbarForDemandPlanDetail">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-arrow_left" onclick="toList()">返回</a>
				<%
					if ("EDIT".equals(opType)) {
				%>
				<a href="#" id="saveBtn" class="easyui-linkbutton" iconCls="icon-ok"
					plain="true" onclick="saveDemandPlan()">保存</a><a href="#"
					id="addToolBtn" class="easyui-linkbutton" iconCls="icon-add"
					plain="true" onclick="openChooseToolDemandUIForDemandPlan()">添加工器具</a>
				<a href="#" id="submitBtn" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="submitDemandPlan()">提交</a>
				<%
					} else if ("AUDIT_BY_WORK_GROUP".equals(opType)) {
				%><a href="#" class="easyui-linkbutton"
					iconCls="icon-application_go" plain="true"
					onclick="passDemandPlanByWorkGroup()">通过</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-cross" plain="true"
					onclick="unPassDemandPlanByWorkGroup()">不通过</a>
				<%
					} else if ("AUDIT_BY_DEPT".equals(opType)) {
				%><a href="#" class="easyui-linkbutton"
					iconCls="icon-application_go" plain="true"
					onclick="passDemandPlanByDept()">通过</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-cross" plain="true"
					onclick="unPassDemandPlanByDept()">不通过</a>
				<%
					}
				%>
			</div>
			<div>
				<table>
					<tr>
						<td>需求计划名称： <input id="demandPlanCodeTextInput"
							class="easyui-textbox"
							data-options="prompt:'需求计划名称',validType:'length[0,50]'"
							style="width: 300px"></td>
						<td>备注： <input id="demandPlanRemarkTextInput"
							class="easyui-textbox"
							data-options="prompt:'备注',validType:'length[0,50]'"
							style="width: 200px"></td>
					</tr>
				</table>


			</div>
		</div>
	</div>
</body>
</html>