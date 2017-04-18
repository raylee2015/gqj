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

				initDataGridForDept();
				registerKeyPressForTextInput('keyWordForDeptTextInput',
						queryDeptPagesForSearch);

				queryNewBatchCode();

				registerKeyPressForTextInput('toolCodeTextInput', saveBatch);

				var batchType = getTextBoxValue('batchTypeTextInput');
				var saveBtnName = "";
				if (batchType == 1) {
					saveBtnName = "出库";
				} else if (batchType == 2) {
					saveBtnName = "转仓";
				} else if (batchType == 3) {
					saveBtnName = "退仓";
				} else if (batchType == 4) {
					saveBtnName = "报废";
				} else if (batchType == 5) {
					saveBtnName = "借用";
				} else if (batchType == 6) {
					saveBtnName = "归还";
				} else if (batchType == 8) {
					saveBtnName = "本站使用";
				} else if (batchType == 9) {
					saveBtnName = "本站归还";
				}

				$('#saveBtn').linkbutton({
					text : saveBtnName
				});
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

	//打开表单版面
	function openFormPanel() {
		$('#formPanel').panel('expand');
		$('#chooseDeptPanel').panel('collapse');
		$('#chooseUserPanel').panel('collapse');
	}

	//打开选择仓库版面
	function openChooseUserPanel() {
		$('#formPanel').panel('collapse');
		$('#chooseDeptPanel').panel('collapse');
		$('#chooseUserPanel').panel('expand');
	}

	//打开选择领用部门版面
	function openChooseDeptPanel() {
		$('#formPanel').panel('collapse');
		$('#chooseDeptPanel').panel('expand');
		$('#chooseUserPanel').panel('collapse');
	}

	// 保存数据
	function saveBatch() {
		var params = null;
		var batchCode = getTextBoxValue('batchCodeTextInput');
		var batchRemark = "";
		var batchType = getTextBoxValue('batchTypeTextInput');
		var opType = getTextBoxValue('opTypeTextInput');
		var toolCode = getTextBoxValue('toolCodeTextInput');

		var batchReturnUserId = '';
		if (batchType == 6) {
			batchReturnUserId = getTextBoxValue('userIdTextInput');
			batchRemark=getTextBoxValue('batchRemarkTextInput');
		}
		var batchTakeDeptId = '';
		if (batchType == 1 || batchType == 2|| batchType == 5) {
			batchTakeDeptId = getTextBoxValue('deptIdTextInput');
			batchRemark=getTextBoxValue('batchRemarkTextInput');
		}
		params = {
			BATCH_CODE : batchCode,
			BATCH_TYPE : batchType,
			BATCH_RETURN_USER_ID : batchReturnUserId,
			BATCH_REMARK : batchRemark,
			BATCH_TAKE_DEPT_ID : batchTakeDeptId,
			TOOL_CODE : toolCode
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
			var opText = "";
			var batchType = getTextBoxValue('batchTypeTextInput');
			if (batchType == 1) {
				opText = "扫描出库";
			} else if (batchType == 2) {
				opText = "扫描转仓";
			} else if (batchType == 3) {
				opText = "扫描退仓";
			} else if (batchType == 4) {
				opText = "扫描报废";
			} else if (batchType == 5) {
				opText = "扫描借用";
			} else if (batchType == 6) {
				opText = "扫描归还";
			}
			$("#tip1").empty();
			$("#tip2").empty();
			var tip1 = "<font color='#0000ff' >" + toolCode + " 已经" + opText
					+ "</font>";
			var tip2 = "<font >已经" + opText + count + "个工器具</font>";
			$("#tip1").delay(200).html(tip1);
			$("#tip2").delay(200).html(tip2);
			setTextBoxText("toolCodeTextInput", "");
			setTextBoxValue("toolCodeTextInput", "");
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
				id="deptIdTextInput" class="easyui-textbox" /> <input
				id="userIdTextInput" class="easyui-textbox" />
		</div>
		<table style="width: 100%">
			<tr>
				<td width="18%">批次编号:</td>
				<td><input id="batchCodeTextInput" value="<%=batchCode%>"
					class="easyui-textbox" data-options="required:true,disabled:true"
					style="width: 100%; height: 32px" /></td>
			</tr>
			<%
				if ("1".equals(batchType) || "5".equals(batchType)
						|| "2".equals(batchType)) {
			%>
			<tr>
				<td width="18%">选择领用部门:</td>
				<td><a href="#" id="deptNameBtn" class="easyui-linkbutton"
					data-options="required:true,prompt:'选择领用部门'"
					onclick="openChooseDeptPanel()" style="width: 100%; height: 32px">选择领用部门</a></td>
			</tr>
			<%
				}
				if ("6".equals(batchType)) {
			%>
			<tr>
				<td width="18%">选择归还人:</td>
				<td><a href="#" id="userNameBtn" class="easyui-linkbutton"
					data-options="required:true,prompt:'选择归还人'"
					onclick="openChooseUserPanel()" style="width: 100%; height: 32px">选择归还人</a></td>
			</tr>
			<%
				}
				if (!("8".equals(batchType) || "9".equals(batchType))) {
			%>
			<tr>
				<td width="18%">备注:</td>
				<td><input id="batchRemarkTextInput" class="easyui-textbox"
					data-options="validType:'length[0,200]'"
					style="width: 100%; height: 32px" /></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td width="18%">工器具编号:</td>
				<td><input id="toolCodeTextInput" class="easyui-textbox"
					data-options="required:true,validType:'length[0,50]'"
					style="width: 100%; height: 32px" /></td>
			</tr>
			<tr>
				<td width="100%" align="right" colspan="2"><a href="#"
					id="saveBtn" class="easyui-linkbutton" onclick="saveBatch()"
					style="height: 32px">入库</a><a href="#" id="closeBtn"
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