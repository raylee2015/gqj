<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String batchType = request.getParameter("BATCH_TYPE");
	String opType = request.getParameter("OP_TYPE");
	String batchCode = "";
	if (request.getAttribute("BATCH_CODE") != null) {
		batchCode = request.getAttribute("BATCH_CODE").toString();
	}
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
	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				initDataGridForUser();
				registerKeyPressForTextInput('keyWordForUserTextInput',
						queryUserPagesForSearch);

				initDataGridForStorage();
				registerKeyPressForTextInput('keyWordForStorageTextInput',
						queryStoragePagesForSearch);

				initDataGridForPosition();
				registerKeyPressForTextInput('keyWordForPositionTextInput',
						queryPositionPagesForSearch);

				initDataGridForDept();
				registerKeyPressForTextInput('keyWordForDeptTextInput',
						queryDeptPagesForSearch);

				queryNewBatchCode();

				initDataGridForBaseTool();
				registerKeyPressForTextInput('keyWordForBaseToolTextInput',
						queryBaseToolPagesForSearch);
				registerKeyPressForTextInput('baseToolSpecTextBox',
						queryBaseToolPagesForSearch);
				registerKeyPressForTextInput('baseToolModelTextBox',
						queryBaseToolPagesForSearch);

				registerKeyPressForTextInput('toolCodeTextInput', saveBatch);

				var batchType = getTextBoxValue('batchTypeTextInput');
			});

	//查询批次编号
	function queryNewBatchCode() {
		var batchCode = getTextBoxValue('batchCodeTextInput');
		if (batchCode != null && batchCode != '') {
			return;
		}
		var params = {
			BATCH_TYPE : getTextBoxValue('batchTypeTextInput')
		};
		query(params, 'queryNewBatchCode.do', successFunctionForQueryBatchCode);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryBatchCode(result) {
		setTextBoxText('batchCodeTextInput', result);
		setTextBoxValue('batchCodeTextInput', result);
	}

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
		query(params, 'queryStoragesPage.do', successFunctionForQueryStorages);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryStorages(result) {
		dataGridLoadData('datagridForStorage', result);
	}

	//用在点击查询按钮的时候
	function queryUserPagesForSearch() {
		queryUsers();
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
				width : 150
			}, {
				field : 'DEPT_NAME',
				title : '所属部门',
				width : 150
			} ] ],
			onBeforeLoad : function(param) {
				param.keyWord = getTextBoxValue('keyWordForUserTextInput');
			},
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}

	function chooseStorage() {
		var selectedItems = $('#datagridForStorage').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择仓库");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个仓库");
			return;
		}
		setTextBoxText('storageIdTextInput', selectedItems[0].STORE_ID);
		setTextBoxValue('storageIdTextInput', selectedItems[0].STORE_ID);
		setTextBoxValue('storageNameTextInput', selectedItems[0].STORE_NAME);
		$('#storageNameBtn').linkbutton({
			text : selectedItems[0].STORE_NAME,
		});
		openFormPanel();
	}

	//用在点击查询按钮的时候
	function queryPositionPagesForSearch() {
		queryPositions();
	}

	//查询
	function queryPositions() {
		var params = {
			STORE_ID : getTextBoxValue('storageIdTextInput'),
			keyWord : getTextBoxValue('keyWordForPositionTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForPosition')
		};
		query(params, 'queryPositionsPage.do', successFunctionForQueryPositions);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryPositions(result) {
		dataGridLoadData('datagridForPosition', result);
	}

	//初始化列表元素
	function initDataGridForPosition() {
		$('#datagridForPosition').datagrid({
			idField : 'POS_ID',
			rownumbers : true,
			toolbar : '#toolbarForPosition',
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
				field : 'POS_NAME',
				title : '仓位名称',
				width : 150
			} ] ],
			onBeforeLoad : function(param) {
				param.keyWord = getTextBoxValue('keyWordForPositionTextInput');
			},
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}

	function choosePosition() {
		var selectedItems = $('#datagridForPosition').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择仓位");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个仓位");
			return;
		}
		setTextBoxText('positionIdTextInput', selectedItems[0].POS_ID);
		setTextBoxValue('positionIdTextInput', selectedItems[0].POS_ID);
		setTextBoxValue('positionNameTextInput', selectedItems[0].POS_NAME);
		$('#positionNameBtn').linkbutton({
			text : selectedItems[0].POS_NAME,
		});
		openFormPanel();
	}

	//用在点击查询按钮的时候
	function queryBaseToolPagesForSearch() {
		queryBaseTools();
	}

	//查询工器具基础信息
	function queryBaseTools() {
		var params = {
			BASE_TOOL_TYPE_ID : getComboBoxValue('baseToolTypeComboBox'),
			MANUFACTURER_ID : getComboBoxValue('baseToolManufacturerComboBox'),
			keyWord : getTextBoxValue('keyWordForBaseToolTextInput'),
			BASE_TOOL_MODEL : getTextBoxValue('baseToolModelTextBox'),
			BASE_TOOL_SPEC : getTextBoxValue('baseToolSpecTextBox'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForBaseTool')
		};
		query(params, 'queryBaseToolsPage.do', successFunctionForQueryBaseTools);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryBaseTools(result) {
		dataGridLoadData('datagridForBaseTool', result);
	}

	//初始化列表元素
	function initDataGridForBaseTool() {
		$('#datagridForBaseTool')
				.datagrid(
						{
							url : 'queryBaseToolsPage.do',
							idField : 'TOOL_ID',
							rownumbers : true,
							toolbar : '#toolbarForBaseTool',
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
								field : 'BASE_TOOL_TYPE_NAME',
								title : '工器具类型',
								width : 100
							}, {
								field : 'BASE_TOOL_NAME',
								title : '工器具名称',
								width : 150
							}, {
								field : 'BASE_TOOL_MANUFACTURER_NAME',
								title : '厂家名称',
								width : 150
							}, {
								field : 'BASE_TOOL_MODEL',
								title : '型号',
								width : 100
							}, {
								field : 'BASE_TOOL_SPEC',
								title : '规格',
								width : 100
							} ] ],
							onBeforeLoad : function(param) {
								param.keyWord = getTextBoxValue('keyWordForBaseToolTextInput');
								param.BASE_TOOL_TYPE_ID = getComboBoxValue('baseToolTypeComboBox');
								param.MANUFACTURER_ID = getComboBoxValue('baseToolManufacturerComboBox');
								param.BASE_TOOL_MODEL = getTextBoxValue('baseToolModelTextBox');
								param.BASE_TOOL_SPEC = getTextBoxValue('baseToolSpecTextBox');
							},
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	function chooseBaseTool() {
		var selectedItems = $('#datagridForBaseTool').datagrid('getChecked');
		setTextBoxValue('baseToolIdTextInput', selectedItems[0].BASE_TOOL_ID);
		setTextBoxValue('baseToolNameTextInput',
				selectedItems[0].BASE_TOOL_NAME);
		setTextBoxValue('baseToolTypeIdTextInput',
				selectedItems[0].BASE_TOOL_TYPE_ID);
		setTextBoxValue('baseToolTypeNameTextInput',
				selectedItems[0].BASE_TOOL_TYPE_NAME);
		setTextBoxValue('baseToolModelTextInput',
				selectedItems[0].BASE_TOOL_MODEL);
		setTextBoxValue('baseToolSpecTextInput',
				selectedItems[0].BASE_TOOL_SPEC);
		setTextBoxValue('baseToolManNameTextInput',
				selectedItems[0].BASE_TOOL_MANUFACTURER_NAME);
		$('#baseToolNameBtn').linkbutton({
			text : selectedItems[0].BASE_TOOL_NAME,
		});
		openFormPanel();
	}

	//打开表单版面
	function openFormPanel() {
		$('#formPanel').panel('expand');
		$('#chooseBaseToolPanel').panel('collapse');
		$('#choosePositionPanel').panel('collapse');
		$('#chooseStoragePanel').panel('collapse');
		$('#chooseDeptPanel').panel('collapse');
		$('#chooseUserPanel').panel('collapse');
	}

	//打开选择工器具类型版面
	function openChooseBaseToolPanel() {
		$('#formPanel').panel('collapse');
		$('#chooseBaseToolPanel').panel('expand');
		$('#choosePositionPanel').panel('collapse');
		$('#chooseStoragePanel').panel('collapse');
		$('#chooseDeptPanel').panel('collapse');
	}

	//打开选择仓位版面
	function openChoosePositionPanel() {
		var storeId = getTextBoxValue('storageIdTextInput');
		if (storeId == null || storeId == '') {
			alert("请先选择仓库");
			return;
		}
		queryPositions();
		$('#formPanel').panel('collapse');
		$('#chooseBaseToolPanel').panel('collapse');
		$('#choosePositionPanel').panel('expand');
		$('#chooseStoragePanel').panel('collapse');
		$('#chooseDeptPanel').panel('collapse');
		var batchType = getTextBoxValue('batchTypeTextInput');
		if (batchType == 6) {
			$('#chooseUserPanel').panel('collapse');
		}
	}

	//打开选择仓库版面
	function openChooseStoragePanel() {
		$('#formPanel').panel('collapse');
		$('#chooseBaseToolPanel').panel('collapse');
		$('#choosePositionPanel').panel('collapse');
		$('#chooseStoragePanel').panel('expand');
		$('#chooseDeptPanel').panel('collapse');
		var batchType = getTextBoxValue('batchTypeTextInput');
		if (batchType == 6) {
			$('#chooseUserPanel').panel('collapse');
		}
	}

	//打开选择仓库版面
	function openChooseUserPanel() {
		$('#formPanel').panel('collapse');
		$('#chooseBaseToolPanel').panel('collapse');
		$('#choosePositionPanel').panel('collapse');
		$('#chooseStoragePanel').panel('collapse');
		$('#chooseDeptPanel').panel('collapse');
		$('#chooseUserPanel').panel('expand');
	}

	//打开选择领用部门版面
	function openChooseDeptPanel() {
		$('#formPanel').panel('collapse');
		$('#chooseBaseToolPanel').panel('collapse');
		$('#choosePositionPanel').panel('collapse');
		$('#chooseStoragePanel').panel('collapse');
		$('#chooseDeptPanel').panel('expand');
	}

	// 保存数据
	function saveBatch() {
		var params = null;
		var batchCode = getTextBoxValue('batchCodeTextInput');
		var batchRemark = getTextBoxValue('batchRemarkTextInput');
		var batchType = getTextBoxValue('batchTypeTextInput');
		var opType = getTextBoxValue('opTypeTextInput');
		var toolCode = getTextBoxValue('toolCodeTextInput');
		var toolTestDateCircle = '';
		var toolRejectDate = '';
		var toolTestDate = '';
		var toolManufactureDate = '';
		var toolPurchaseDate = '';
		var baseToolManName = '';
		var baseToolName = '';
		var baseToolTypeId = '';
		var baseToolTypeName = '';
		var baseToolModel = '';
		var baseToolSpec = '';
		var baseToolId = '';
		var storeId = '';
		var positionId = '';
		var toolBox = '';
		var storageName = '';
		var positionName = '';
		if (opType != "EXCHANGE_TO_CHECKIN") {
			baseToolId = getTextBoxValue('baseToolIdTextInput');
			baseToolName = getTextBoxValue('baseToolNameTextInput');
			baseToolTypeId = getTextBoxValue('baseToolTypeIdTextInput');
			baseToolTypeName = getTextBoxValue('baseToolTypeNameTextInput');
			baseToolModel = getTextBoxValue('baseToolModelTextInput');
			baseToolSpec = getTextBoxValue('baseToolSpecTextInput');
			baseToolManName = getTextBoxValue('baseToolManNameTextInput');
			toolTestDateCircle = getTextBoxValue('toolTestDateCircleTextInput');
			toolRejectDate = getDateBoxValue('toolRejectDateBox');
			toolTestDate = getDateBoxValue('toolTestDateBox');
			toolManufactureDate = getDateBoxValue('toolManufactureDateBox');
			toolPurchaseDate = getDateBoxValue('toolPurchaseDateBox');
		}
		storeId = getTextBoxValue('storageIdTextInput');
		positionId = getTextBoxValue('positionIdTextInput');
		toolBox = getTextBoxValue('toolBoxTextInput');
		storageName = getTextBoxValue('storageNameTextInput');
		positionName = getTextBoxValue('positionNameTextInput');
		params = {
			BATCH_CODE : batchCode,
			BATCH_TYPE : batchType,
			STORE_ID : storeId,
			POS_ID : positionId,
			STORE_NAME : storageName,
			POS_NAME : positionName,
			BASE_TOOL_ID : baseToolId,
			BASE_TOOL_NAME : baseToolName,
			BASE_TOOL_MANUFACTURER_NAME : baseToolManName,
			BASE_TOOL_TYPE_ID : baseToolTypeId,
			BASE_TOOL_TYPE_NAME : baseToolTypeName,
			BASE_TOOL_MODEL : baseToolModel,
			BASE_TOOL_SPEC : baseToolSpec,
			BATCH_REMARK : batchRemark,
			TOOL_CODE : toolCode,
			TOOL_BOX : toolBox,
			TOOL_TEST_DATE : toolTestDate,
			TOOL_REJECT_DATE : toolRejectDate,
			TOOL_MANUFACTURE_DATE : toolManufactureDate,
			TOOL_PURCHASE_DATE : toolPurchaseDate,
			TOOL_TEST_DATE_CIRCLE : toolTestDateCircle
		};
		url = "addNewBatchsAndDetails.do";
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		if (result.success) {
			//更新提示
			var count = result.count;
			var toolCode = getTextBoxValue('toolCodeTextInput');
			var opText = "扫描入库";
			$("#tip1").empty();
			$("#tip2").empty();
			var tip1 = "<font color='#0000ff' >" + toolCode + " 已经" + opText
					+ "</font>";
			var tip2 = "<font >已经" + opText + count + "个工器具</font>";
			$("#tip1").delay(200).html(tip1);
			$("#tip2").delay(200).html(tip2);
		} else {
			alert(result.msg);
		}
	}

	//关闭编辑窗口
	function closeAddToolsUIForBatch() {
		parent.queryBatchs();
		parent.closeAddToolsUIForBatch();
	}

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

	function chooseDept() {
		var selectedItems = $('#datagridForDept').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择部门");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个部门");
			return;
		}
		setTextBoxText('deptIdTextInput', selectedItems[0].DEPT_ID);
		setTextBoxValue('deptIdTextInput', selectedItems[0].DEPT_ID);
		$('#deptNameBtn').linkbutton({
			text : selectedItems[0].DEPT_NAME,
		});
		openFormPanel();
	}

	function chooseUser() {
		var selectedItems = $('#datagridForUser').datagrid('getChecked');
		if (selectedItems.length == 0) {
			alert("请选择人员");
			return;
		} else if (selectedItems.length > 1) {
			alert("只能选择一个人员");
			return;
		}
		setTextBoxText('userIdTextInput', selectedItems[0].USER_ID);
		setTextBoxValue('userIdTextInput', selectedItems[0].USER_ID);
		$('#userNameBtn').linkbutton({
			text : selectedItems[0].USER_NAME,
		});
		openFormPanel();
	}
</script>
</head>
<body>
	<div id="formPanel" class="easyui-panel" data-options="fit:true">
		<div style="display: none">
			<input id="batchTypeTextInput" class="easyui-textbox"
				value="<%=request.getParameter("BATCH_TYPE")%>" /><input
				id="opTypeTextInput" class="easyui-textbox"
				value="<%=request.getParameter("OP_TYPE")%>" /> <input
				id="storageIdTextInput" class="easyui-textbox" /> <input
				id="storageNameTextInput" class="easyui-textbox" /> <input
				id="positionIdTextInput" class="easyui-textbox" /><input
				id="positionNameTextInput" class="easyui-textbox" /><input
				id="baseToolIdTextInput" class="easyui-textbox" /><input
				id="baseToolNameTextInput" class="easyui-textbox" /><input
				id="baseToolTypeIdTextInput" class="easyui-textbox" /><input
				id="baseToolTypeNameTextInput" class="easyui-textbox" /><input
				id="baseToolModelTextInput" class="easyui-textbox" /><input
				id="baseToolSpecTextInput" class="easyui-textbox" /><input
				id="baseToolManNameTextInput" class="easyui-textbox" /> <input
				id="userIdTextInput" class="easyui-textbox" />
		</div>
		<table style="width: 100%">
			<tr>
				<td width="18%">批次编号:</td>
				<td><input id="batchCodeTextInput" value="<%=batchCode%>"
					class="easyui-textbox" data-options="required:true,disabled:true"
					style="width: 100%; height: 32px" /></td>
			</tr>
			<tr>
				<td width="18%">选择仓库:</td>
				<td><a href="#" id="storageNameBtn" class="easyui-linkbutton"
					data-options="required:true,prompt:'选择仓库'"
					onclick="openChooseStoragePanel()"
					style="width: 100%; height: 32px">选择仓库</a></td>
			</tr>
			<tr>
				<td width="18%">选择仓位:</td>
				<td><a href="#" id="positionNameBtn" class="easyui-linkbutton"
					data-options="required:true,prompt:'选择仓位'"
					onclick="openChoosePositionPanel()"
					style="width: 100%; height: 32px">选择仓位</a></td>
			</tr>
			<tr>
				<td width="18%">箱号:</td>
				<td><input id="toolBoxTextInput" class="easyui-textbox"
					data-options="required:true,validType:'length[0,200]'"
					style="width: 100%; height: 32px" /></td>
			</tr>
			<%
				if (!"EXCHANGE_TO_CHECKIN".equals(opType)) {
			%>
			<tr>
				<td width="18%">工器具类型:</td>
				<td><a href="#" id="baseToolNameBtn" class="easyui-linkbutton"
					data-options="required:true,prompt:'选择工器具类型'"
					onclick="openChooseBaseToolPanel()"
					style="width: 100%; height: 32px">选择工器具类型</a></td>
			</tr>
			<tr>
				<td width="18%">出厂日期:</td>
				<td><input id="toolManufactureDateBox" class="easyui-datebox"
					data-options="required:true" style="width: 100%; height: 32px" /></td>
			</tr>
			<tr>
				<td width="18%">购买日期:</td>
				<td><input id="toolPurchaseDateBox" class="easyui-datebox"
					data-options="required:true" style="width: 100%; height: 32px" /></td>
			</tr>
			<tr>
				<td width="18%">初试日期:</td>
				<td><input id="toolTestDateBox" class="easyui-datebox"
					data-options="required:true" style="width: 100%; height: 32px" /></td>
			</tr>
			<tr>
				<td width="18%">报废日期:</td>
				<td><input id="toolRejectDateBox" class="easyui-datebox"
					data-options="required:true" style="width: 100%; height: 32px" /></td>
			</tr>
			<tr>
				<td width="18%">试验周期:</td>
				<td><input id="toolTestDateCircleTextInput"
					class="easyui-textbox" data-options="required:true"
					style="width: 96%; height: 32px" />月</td>
			</tr>
			<%
				}
			%>
			<tr>
				<td width="18%">备注:</td>
				<td><input id="batchRemarkTextInput" class="easyui-textbox"
					data-options="validType:'length[0,200]'"
					style="width: 100%; height: 32px" /></td>
			</tr>
			<tr>
				<td width="18%">工器具编号:</td>
				<td><input id="toolCodeTextInput" class="easyui-textbox"
					data-options="required:true,validType:'length[0,50]'"
					style="width: 100%; height: 32px" /></td>
			</tr>
			<tr>
				<td width="100%" align="right" colspan="2"><a href="#"
					id="saveBtn" class="easyui-linkbutton" onclick="saveBatch()"
					style="width: 60px; height: 32px">入库</a><a href="#" id="closeBtn"
					class="easyui-linkbutton" onclick="closeAddToolsUIForBatch()"
					style="width: 100px; height: 32px">关闭并刷新列表</a></td>
			</tr>
			<tr>
				<td width="100%" colspan="2"><hr /></td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="tip2"></div>
					<div id="tip1"></div>
				</td>
			</tr>
		</table>
	</div>
	<div id="chooseBaseToolPanel" class="easyui-panel"
		data-options="fit:true">
		<table id="datagridForBaseTool" class="easyui-datagrid">
		</table>
		<div id="toolbarForBaseTool">
			<table style="width: 100%">
				<tr>
					<td><a class="easyui-linkbutton" iconCls="icon-ok"
						href="javascript:void(0)" onclick="chooseBaseTool()">选择</a> <a
						class="easyui-linkbutton" iconCls="icon-cancel"
						href="javascript:void(0)" onclick="openFormPanel()">返回</a></td>
					<td align="right"><input id="keyWordForBaseToolTextInput"
						class="easyui-textbox"
						data-options="prompt:'工器具名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryBaseToolPagesForSearch()">查询</a>
				</tr>
				<tr>
					<td>类型: <input id="baseToolTypeComboBox"
						data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '工器具类型',
							url : 'queryBaseToolTypeDropList.do',
							onChange : function(newValue, oldValue){
								queryBaseTools();
							}
							"
						class="easyui-combobox" style="width: 200px;"></td>
					<td>厂家: <input id="baseToolManufacturerComboBox"
						data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '厂家',
							url : 'queryBaseToolManufacturerDropList.do',
							onChange : function(newValue, oldValue){
								queryBaseTools();
							}
							"
						class="easyui-combobox" style="width: 200px;"></td>
				</tr>
				<tr>
					<td>型号: <input id="baseToolModelTextBox"
						data-options="prompt : '型号'" class="easyui-textbox"
						style="width: 200px;"></td>
					<td>规格: <input id="baseToolSpecTextBox"
						data-options="prompt : '规格'" class="easyui-textbox"
						style="width: 200px;"></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="choosePositionPanel" class="easyui-panel"
		data-options="fit:true">
		<table id="datagridForPosition" class="easyui-datagrid">
		</table>
		<div id="toolbarForPosition">
			<table style="width: 100%">
				<tr>
					<td><a class="easyui-linkbutton" iconCls="icon-ok"
						href="javascript:void(0)" onclick="choosePosition()">选择</a> <a
						class="easyui-linkbutton" iconCls="icon-cancel"
						href="javascript:void(0)" onclick="openFormPanel()">返回</a></td>
					<td align="right"><input id="keyWordForPositionTextInput"
						class="easyui-textbox"
						data-options="prompt:'仓位名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryPositionPagesForSearch()">查询</a>
				</tr>
			</table>
		</div>
	</div>
	<div id="chooseStoragePanel" class="easyui-panel"
		data-options="fit:true">
		<table id="datagridForStorage" class="easyui-datagrid">
		</table>
		<div id="toolbarForStorage">
			<table style="width: 100%">
				<tr>
					<td><a class="easyui-linkbutton" iconCls="icon-ok"
						href="javascript:void(0)" onclick="chooseStorage()">选择</a> <a
						class="easyui-linkbutton" iconCls="icon-cancel"
						href="javascript:void(0)" onclick="openFormPanel()">返回</a></td>
					<td align="right"><input id="keyWordForStorageTextInput"
						class="easyui-textbox"
						data-options="prompt:'仓库名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryStoragePagesForSearch()">查询</a>
				</tr>
			</table>
		</div>
	</div>
	<div id="chooseUserPanel" class="easyui-panel" data-options="fit:true">
		<table id="datagridForUser" class="easyui-datagrid">
		</table>
		<div id="toolbarForUser">
			<table style="width: 100%">
				<tr>
					<td><a class="easyui-linkbutton" iconCls="icon-ok"
						href="javascript:void(0)" onclick="chooseUser()">选择</a> <a
						class="easyui-linkbutton" iconCls="icon-cancel"
						href="javascript:void(0)" onclick="openFormPanel()">返回</a></td>
					<td align="right"><input id="keyWordForUserTextInput"
						class="easyui-textbox"
						data-options="prompt:'人员名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryUserPagesForSearch()">查询</a>
				</tr>
			</table>
		</div>
	</div>
	<div id="chooseDeptPanel" class="easyui-panel" data-options="fit:true">
		<table id="datagridForDept" class="easyui-datagrid">
		</table>
		<div id="toolbarForDept">
			<table style="width: 100%">
				<tr>
					<td><a class="easyui-linkbutton" iconCls="icon-ok"
						href="javascript:void(0)" onclick="chooseDept()">选择</a> <a
						class="easyui-linkbutton" iconCls="icon-cancel"
						href="javascript:void(0)" onclick="openFormPanel()">返回</a></td>
					<td align="right"><input id="keyWordForDeptTextInput"
						class="easyui-textbox"
						data-options="prompt:'部门名称',validType:'length[0,25]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryDeptPagesForSearch()">查询</a>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>