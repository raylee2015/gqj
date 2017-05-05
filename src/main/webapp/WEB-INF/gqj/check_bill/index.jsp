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
	function closeScanUIForTool() {
		closeEditUI('scanUIForTool')
	}

	//打开编辑窗口
	function openScanUIForTool() {
		createModalDialog("scanUIForTool", "scanUIForTool.do", "扫描检验工器具", 400,
				180);
		openEditUI('scanUIForTool');
	}

	//删除
	function delCheckBills() {
		if (checkSelectedItems('datagridForCheckBill', '请选择检验结果')) {
			var ids = getIdsOfSelectedItems('datagridForCheckBill', 'BILL_ID');
			if (ids != null && ids != '') {
				var params = {
					BILL_IDS : ids
				};
				showMessageBox(params, 'delCheckBills.do', '是否删除所选检验结果?',
						successFunctionForOption);
			}
		}
	}
	
	//确认
	function confrimCheckBills() {
		if (checkSelectedItems('datagridForCheckBill', '请选择检验结果')) {
			var ids = getIdsOfSelectedItems('datagridForCheckBill', 'BILL_ID');
			if (ids != null && ids != '') {
				var params = {
					BILL_IDS : ids
				};
				showMessageBox(params, 'comfirmCheckBills.do', '是否完成所选检验结果?',
						successFunctionForOption);
			}
		}
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForCheckBill');
	}

	//用在点击查询按钮的时候
	function queryCheckBillPagesForSearch() {
		queryCheckBills();
	}

	//查询
	function queryCheckBills() {
		var params = {
			keyWord : getTextBoxValue('keyWordForCheckBillTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForCheckBill')
		};
		query(params, 'queryCheckBillsPage.do',
				successFunctionForQueryCheckBills);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryCheckBills(result) {
		dataGridLoadData('datagridForCheckBill', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				registerKeyPressForTextInput('keyWordForCheckBillTextInput',
						queryCheckBillPagesForSearch);
				registerKeyPressForTextInput(
						'keyWordForCheckBillDetailTextInput',
						queryCheckBillDetailsForPage);
				initDataGridForCheckBill();
				initDataGridForCheckBillDetail();
			});

	//初始化列表元素
	function initDataGridForCheckBill() {
		$('#datagridForCheckBill')
				.datagrid(
						{
							url : 'queryCheckBillsPage.do',
							idField : 'BILL_ID',
							rownumbers : true,
							toolbar : '#toolbarForCheckBill',
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
											var billStatus = rowData.BILL_STATUS;
											var btn = '';
											if (billStatus == '0') {
												btn = '<a class="easyui-linkbutton" '
														+ ' onclick="toDetail(\''
														+ rowIndex
														+ '\')" href="javascript:void(0)">编辑</a>';
											} else if (billStatus == '1') {
												btn = '<a class="easyui-linkbutton" '
														+ ' onclick="toDetail(\''
														+ rowIndex
														+ '\')" href="javascript:void(0)">查看</a>';
											}
											return btn;
										}
									}, {
										field : 'BILL_CODE',
										title : '检验批次',
										width : 250,
									}, {
										field : 'BILL_CREATE_USER_NAME',
										title : '创建人',
										width : 100,
									}, {
										field : 'BILL_CREATE_TIME',
										title : '创建时间',
										width : 100,
									}, {
										field : 'BILL_STATUS_NAME',
										title : '状态',
										width : 100,
									}, {
										field : 'BILL_REMARK',
										title : '备注',
										width : 100,
									} ] ],
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}

	//初始化列表元素
	function initDataGridForCheckBillDetail() {
		$('#datagridForCheckBillDetail').datagrid({
			idField : 'DETAIL_ID',
			rownumbers : true,
			toolbar : '#toolbarForCheckBillDetail',
			pagination : true,
			pageSize : 30,
			pageNumber : 1,
			checkOnSelect : false,
			fit : true,
			method : 'get',
			columns : [ [ {
				field : 'BASE_TOOL_STATUS_NAME',
				title : '状态',
				width : 100
			}, {
				field : 'BASE_TOOL_CODE',
				title : '工器具编码',
				width : 100
			}, {
				field : 'BASE_TOOL_NAME',
				title : '工器具名称',
				width : 150
			}, {
				field : 'MAN_NAME',
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
			}, {
				field : 'BASE_TOOL_NORMAL_FLAG_NAME',
				title : '是否正常',
				width : 100
			}, {
				field : 'BASE_TOOL_REMARK',
				title : '备注',
				width : 100
			} ] ],
			onLoadError : function() {
				errorFunctionForQuery();
			}
		});
	}

	//操作类型
	var opType = '';

	//记录新增或者修改的方法
	var url;

	var rowIndexOfDataGrid = 0;

	//编辑界面
	function toDetail(rowIndex) {
		if (rowIndex != null) {
			rowIndexOfDataGrid = rowIndex;
			var rowData = getRowDataOfSelfDataGrid('datagridForCheckBill',
					rowIndex);
			opType = 'edit';
			setTextBoxText("checkBillIdTextBox", rowData.BILL_ID);
			setTextBoxValue("checkBillIdTextBox", rowData.BILL_ID)
			queryCheckBillDetailsForPage();
			setTextBoxValue('checkBillCodeTextInput', rowData.BILL_CODE);
			setTextBoxValue('checkBillRemarkTextInput', rowData.BILL_REMARK);
		} else {
			opType = 'add';
		}
		$('#checkBillListUI').panel('collapse');
		$('#checkBillDetailUI').panel('expand');
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryCheckBillCode(result) {
		setTextBoxText('checkBillCodeTextInput', result);
		setTextBoxValue('checkBillCodeTextInput', result);
	}

	//根据出入库单据id查询明细
	function queryCheckBillDetailsForPage() {
		var billId = getTextBoxValue('checkBillIdTextBox');
		var params = {
			BASE_TOOL_NORMAL_FLAG : getComboBoxValue('baseToolNormalFlagComboBox'),
			BASE_TOOL_STATUS : getComboBoxValue('baseToolStatusComboBox'),
			BILL_ID : billId,
			keyWord : getTextBoxValue('keyWordForCheckBillDetailTextInput'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForCheckBillDetail')
		};
		query(params, 'queryCheckBillDetailsForPage.do',
				successFunctionForQueryCheckBillDetails);
	}

	//回调函数，查询成功后调用
	function successFunctionForQueryCheckBillDetails(result) {
		dataGridLoadData('datagridForCheckBillDetail', result);
	}

	//列表界面
	function toList() {
		$('#checkBillListUI').panel('expand');
		$('#checkBillDetailUI').panel('collapse');
		rowIndexOfDataGrid = 0;
		setTextBoxValue('checkBillCodeTextInput', '');
		setTextBoxValue('checkBillRemarkTextInput', '');
		setTextBoxValue('checkBillIdTextInput', '');
		//清空明细列表
		dataGridLoadData('datagridForCheckBillDetail', {
			total : 0,
			rows : []
		});
	}

	function addNewCheckBillsAndDetails() {
		showMessageBox(null, 'addNewCheckBillsAndDetails.do', '是否生成当月检验记录?',
				successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave() {
		reloadDataGrid('datagridForCheckBill');
	}
</script>
</head>
<body>

	<div id="checkBillListUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<!-- 列表页面 -->
		<table id="datagridForCheckBill" class="easyui-datagrid">
		</table>
		<div id="toolbarForCheckBill">
			<table style="width: 100%">
				<tr>
					<td><a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true"
						onclick="refreshDataGrid('datagridForCheckBill')">刷新</a> <a
						href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="addNewCheckBillsAndDetails()">生成当月检验记录</a> <a
						href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
						onclick="confrimCheckBills()">确认完成</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delCheckBills()">删除</a></td>
					<td align="right"><input id="keyWordForCheckBillTextInput"
						class="easyui-textbox"
						data-options="prompt:'批次号',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryCheckBillPagesForSearch()">查询</a>
				</tr>
			</table>
		</div>
	</div>
	<div id="checkBillDetailUI" class="easyui-panel"
		data-options="fit:true,border:false">
		<div style="display: none">
			<input id="checkBillIdTextBox" class="easyui-textbox" />
		</div>
		<table id="datagridForCheckBillDetail" class="easyui-datagrid">
		</table>
		<div id="toolbarForCheckBillDetail">
			<div>
				<a href="#" class="easyui-linkbutton" plain="true"
					iconCls="icon-arrow_left" onclick="toList()">返回</a> <a id="saveBtn"
					href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
					onclick="openScanUIForTool()">扫描检查</a>
			</div>
			<div>
				<table style="width: 100%">
					<tr>
						<td>批次号： <input id="checkBillCodeTextInput"
							class="easyui-textbox"
							data-options="prompt:'批次号',validType:'length[0,50]',disabled:true"
							style="width: 200px"></td>
						<td>是否已检验:<input id="baseToolStatusComboBox"
							data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '是否已检验',
							url : 'queryYesOrNoFlagDropList.do',
							onChange : function(newValue, oldValue){
								queryCheckBillDetailsForPage();
							}"
							class="easyui-combobox" style="width: 200;"></td>
						<td>是否正常:<input id="baseToolNormalFlagComboBox"
							data-options="valueField : 'ID',textField : 'TEXT',require : true,
							panelHeight : 'auto',	prompt : '是否正常',
							url : 'queryYesOrNoFlagDropList.do',
							onChange : function(newValue, oldValue){
								queryCheckBillDetailsForPage();
							}"
							class="easyui-combobox" style="width: 200;"></td>
						<td align="right"><input
							id="keyWordForCheckBillDetailTextInput" class="easyui-textbox"
							data-options="prompt:'工器具编码',validType:'length[0,50]'"
							style="width: 200px"> <a href="#"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="queryCheckBillDetailsForPage()">查询</a></td>
					</tr>
				</table>

			</div>
		</div>
	</div>
</body>
</html>