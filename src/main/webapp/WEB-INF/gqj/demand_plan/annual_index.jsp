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
	function closeChooseToolDemandUIForTemplate() {
		closeUI('chooseToolDemandUIForTemplate')
	}

	//打开选择工器具窗口
	function openChooseToolDemandUIForTemplate() {
		createModalDialog("chooseToolDemandUIForTemplate",
				"openChooseToolDemandUI.do?opType=add", "添加工器具", 1000, 600);
		openUI('chooseToolDemandUIForTemplate');
	}

	//删除
	function delTemplates() {
		if (checkSelectedItems('datagridForTemplate', '请选择工器具')) {
			var ids = getIdsOfSelectedItems('datagridForTemplate',
					'TEMPLATE_ID');
			if (ids != null && ids != '') {
				var params = {
					TEMPLATE_IDS : ids
				};
				showMessageBox(params, 'delTemplates.do', '是否删除所选工器具?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForTemplate');
	}

	//用在点击查询按钮的时候
	function queryTemplatePagesForSearch() {
		queryTemplates();
	}

	//查询
	function queryTemplates() {
		var params = {
			keyWord : getTextBoxValue('keyWordForTemplateTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForTemplate')
		};
		query(params, 'queryTemplatesPage.do', successFunctionForQueryTemplates);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryTemplates(result) {
		dataGridLoadData('datagridForTemplate', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForTemplateTextInput',
						queryTemplatePagesForSearch);
				initDataGridForTemplate();
				initDataGridForTemplateDetail();
			});

	//初始化列表元素
	function initDataGridForTemplate() {
		$('#datagridForTemplate')
				.datagrid(
						{
							url : 'queryTemplatesPage.do',
							idField : 'TEMPLATE_ID',
							rownumbers : true,
							toolbar : '#toolbarForTemplate',
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
													+ ' onclick="toDetail(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'TEMPLATE_NAME',
										title : '模板名称',
										width : 200,
									} ] ],
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForTemplateDetail() {
		$('#datagridForTemplateDetail')
				.datagrid(
						{
							idField : 'DETAIL_ID',
							rownumbers : true,
							toolbar : '#toolbarForTemplateDetail',
							pagination : false,
							checkOnSelect : false,
							fit : true,
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
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForTemplateTextInput');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//删除行数据
	function delRowData(rowIndex) {
		var rowData = getRowDataOfSelfDataGrid('datagridForTemplateDetail',
				rowIndex);
		var data = $('#datagridForTemplateDetail').datagrid('getData').rows;
		for (var i = 0; i < data.length; i++) {
			if (rowData.TOOL_ID == data[i].TOOL_ID) {
				data.splice(i, 1);
			}
		}
		dataGridLoadData('datagridForTemplateDetail', data);
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
			var rowData = getRowDataOfSelfDataGrid('datagridForTemplate',
					rowIndex);
			queryTemplateDetailsForList(rowData);
			setTextBoxValue('templateDetailTextInput', rowData.TEMPLATE_NAME);
		} else {
			opType = 'add';
		}
		$('#templateListUI').panel('collapse');
		$('#templateDetailUI').panel('expand');
	}

	//根据模板id查询明细
	function queryTemplateDetailsForList(rowData) {
		var params = {
			TEMPLATE_ID : rowData.TEMPLATE_ID,
		};
		query(params, 'queryTemplateDetailsForList.do',
				successFunctionForQueryTemplateDetails);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryTemplateDetails(result) {
		dataGridLoadData('datagridForTemplateDetail', result);
	}

	//列表界面
	function toList() {
		$('#templateListUI').panel('expand');
		$('#templateDetailUI').panel('collapse');
		rowIndexOfDataGrid = 0;
		setTextBoxValue('templateDetailTextInput', '');
		//清空明细列表
		dataGridLoadData('datagridForTemplateDetail', {
			total : 0,
			rows : []
		});
	}

	// 保存数据
	function saveTemplate() {
		var rowData = null;
		var params = null;
		var templateName = getTextBoxValue('templateDetailTextInput');
		if (templateName == null || templateName == '') {
			alert('请填写模板名称');
			return;
		}
		var templateDetails = $('#datagridForTemplateDetail').datagrid(
				'getData').rows;
		if (templateDetails.length == 0) {
			alert('请选中工器具');
			return;
		}
		var toolIds = '';
		for (var i = 0; i < templateDetails.length; i++) {
			toolIds += templateDetails[i].TOOL_ID + ',';
		}
		toolIds = toolIds.substring(0, toolIds.length - 1);

		if (opType == 'add') {
			params = {
				TEMPLATE_ID : '',
				TEMPLATE_NAME : templateName,
				TOOL_IDS : toolIds
			};
			url = "addNewTemplatesAndDetails.do";
		} else if (opType == 'edit') {
			rowData = getRowDataOfSelfDataGrid('datagridForTemplate',
					rowIndexOfDataGrid);
			params = {
				TEMPLATE_ID : rowData.TEMPLATE_ID,
				TEMPLATE_NAME : templateName,
				TOOL_IDS : toolIds
			};
			url = "updateTemplatesAndDetails.do";
		}
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave() {
		reloadDataGrid('datagridForTemplate');
		toList();
	}
</script>
</head>
<body>
	<div id="templateListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<div class="easyui-layout"
			data-options="fit:true,border:false">
			<div data-options="fit:true,border:false,region:'center'">
				<table id="datagridForTemplate" class="easyui-datagrid">
				</table>
				<div id="toolbarForTemplate">
					<table style="width: 100%">
						<tr>
							<td><a href="#" class="easyui-linkbutton"
								iconCls="icon-reload" plain="true"
								onclick="refreshDataGrid('datagridForTemplate')">刷新</a>
								<a href="#" class="easyui-linkbutton"
								iconCls="icon-add" plain="true" onclick="toDetail()">添加</a>
								<a href="#" class="easyui-linkbutton"
								iconCls="icon-remove" plain="true"
								onclick="delTemplates()">删除</a></td>
							<td align="right"><input
								id="keyWordForTemplateTextInput"
								class="easyui-textbox"
								data-options="prompt:'模板名称',validType:'length[0,50]'"
								style="width: 200px"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="queryTemplatePagesForSearch()">查询</a>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="templateDetailUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<table id="datagridForTemplateDetail"
			class="easyui-datagrid">
		</table>
		<div id="toolbarForTemplateDetail">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					onclick="toList()">返回</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-ok"
					plain="true" onclick="saveTemplate()">保存</a><a href="#"
					class="easyui-linkbutton" iconCls="icon-add"
					plain="true"
					onclick="openChooseToolDemandUIForTemplate()">添加工器具</a>
			</div>
			<div>
				模板名称： <input id="templateDetailTextInput"
					class="easyui-textbox"
					data-options="prompt:'模板名称',validType:'length[0,50]'"
					style="width: 200px">
			</div>
		</div>
	</div>
</body>
</html>