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
	//记录新增需求计划的url
	var url = 'addAnnualDemandPlan.do';

	// 保存数据
	function saveDept() {
		var deptNames = getTextBoxValue('deptNamesTextBox');
		if (deptNames == null || deptNames == '') {
			alert("请选择部门");
			return;
		}
		var params = {
			DEPT_IDS : getTextBoxValue('deptIdsTextBox'),
			DEPT_NAMES : getTextBoxValue('deptNamesTextBox'),
			PLAN_CODE : getTextBoxValue('planCodeTextBox'),
		};
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		parent.successFunctionForOption(result);
		closeChooseDeptForAnnualPlanUI();
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initChooseDeptPanel();
	});

	//关闭编辑窗口
	function closeChooseDeptForAnnualPlanUI() {
		parent.closeChooseDeptForAnnualPlanUI();
	}

	//打开表单版面
	function openFormPanel() {
		$('#formPanel').panel('open');
		$('#chooseDeptPanel').panel('close');
		parent.makeChooseDeptForAnnualPlanUISmaller();
	}

	//打开选择仓库版面
	function openChooseDeptPanel() {
		$('#formPanel').panel('close');
		$('#chooseDeptPanel').panel('open');
		parent.makeChooseDeptForAnnualPlanUIBigger();
	}

	//初始化选择仓库版面
	function initChooseDeptPanel() {
		$('#chooseDeptPanel').panel({
			closed : true
		});
	}

	//选择仓库
	function chooseDept() {
		var selectedItems = $('#deptTree').tree('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择部门");
			return;
		} else {
			var deptNames = '';
			var deptIds = '';
			for (var i = 0; i < selectedItems.length; i++) {
				deptNames += selectedItems[i].text + ",";
				deptIds += selectedItems[i].id + ",";
			}
			deptNames = deptNames.substring(0, deptNames.length - 1);
			deptIds = deptIds.substring(0, deptIds.length - 1);
			setTextBoxValue('deptNamesTextBox', deptNames);
			setTextBoxValue('deptIdsTextBox', deptIds);
			openFormPanel();
		}
	}
</script>
</head>
<body>
	<div id="formPanel" class="easyui-panel"
		data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" fit="true" border="false">
				<div style="display: none">
					<input id="deptIdsTextBox" class="easyui-textbox" />
				</div>
				<form id="deptForm" method="post" style="width: 100%;">
					<table style="width: 100%; padding: 10px">
						<tr>
							<td width="28%">年度需求计划名称:</td>
							<td><input id="planCodeTextBox"
								class="easyui-textbox"
								data-options="prompt:'年度需求计划名称',required:true,validType:'length[0,25]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
						<tr>
							<td width="28%">填报部门:</td>
							<td><input id="deptNamesTextBox" disabled
								class="easyui-textbox"
								style="width: 69%; height: 32px" /> <a href="#"
								class="easyui-linkbutton"
								style="width: 29%; height: 32px;"
								onclick="openChooseDeptPanel()"> 选择填报部门</a></td>
						</tr>
					</table>
				</form>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px">
				<a class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="saveDept()">保存</a> <a
					class="easyui-linkbutton" iconCls="icon-cancel"
					href="javascript:void(0)"
					onclick="closeChooseDeptForAnnualPlanUI()">关闭</a>
			</div>
		</div>
	</div>
	<div id="chooseDeptPanel" class="easyui-panel" fit="true"
		border="false">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" border="false" style="height: 30px">
				<a href="#" class="easyui-linkbutton"
					onclick="openFormPanel()">返回</a> <a href="#"
					class="easyui-linkbutton" onclick="chooseDept()">选择</a>
			</div>
			<div region="center" border="false">
				<ul id="deptTree" class="easyui-tree" method="get"
					data-options="url : 'queryDeptTree.do',checkbox:true"
					animate="true" lines="true"></ul>
			</div>
		</div>
	</div>
</body>
</html>