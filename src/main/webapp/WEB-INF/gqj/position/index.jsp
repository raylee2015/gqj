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
	href="<%=contextPath%>/jquery-easyui-1.5/demo/demo.css">
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/base.js"></script>
<script type="text/javascript">
	// 关闭编辑窗口
	function closeEditUI() {
		$("#editUI").dialog('destroy');
		$("#editUI").remove();
	}

	// 编辑窗口变大
	function makeEditUIBigger() {
		$('#editUI').panel('resize', {
			height : 500
		});
		$('#editUI').window('center');
	}

	// 编辑窗口变小
	function makeEditUISmaller() {
		$('#editUI').panel('resize', {
			height : 160
		});
		$('#editUI').window('center');
	}

	//打开编辑窗口
	function openAddUI() {
		createModalDialog("editUI", "openEditUI.do?opType=add", "仓位设置", 400,
				160);
		$("#editUI").dialog('open');
	}

	//打开编辑窗口
	function openEditUI(rowIndex) {
		var rowData = $('#datagrid').datagrid('getData').rows[rowIndex];
		var url = "openEditUI.do?opType=edit&postId=" + rowData.POS_ID;
		createModalDialog("editUI", url, "仓位设置", 400, 160);
		$("#editUI").dialog('open');
	}

	//删除
	function delPositions() {
		var ids = getIdsOfSelectedItems('POS_ID', '数据操作出现问题，请联系系统管理员');
		var params = {
			POS_IDS : ids
		};
		del(params, "请选择仓位", '是否删除所选仓位?', 'delPositions.do', true);
	}

	//查询
	function queryForPage() {
		var params = {
			keyWord : $('#keyWordTextInput').textbox('getValue'),
			page : 1,
			rows : $('#datagrid').datagrid('getPager').data("pagination").options.pageSize
		};
		query(params, 'queryPositionPage.do');
	}

	//页面加载完
	$(document).ready(function() {
		initDocument();
		initDataGrid();
	});

	//初始化列表元素
	function initDataGrid() {
		$('#datagrid').datagrid({
			url : 'queryPositionPage.do',
			idField : 'POS_ID',
			rownumbers : true,
			toolbar : '#toolbar',
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
				field : 'op',
				title : '操作',
				formatter : editColumnFormatter
			}, {
				field : 'POS_NAME',
				title : '仓位名称',
				width : 150,
			}, {
				field : 'STORE_NAME',
				title : '所属仓库',
				width : 150,
			} ] ],
			onLoadError : function() {
				eval(errorCodeForQuery);
			}
		});
	}
</script>
</head>
<body>
	<!-- 列表页面 -->
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'">
			<table id="datagrid" class="easyui-datagrid">
			</table>
			<div id="toolbar">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refresh()">刷新</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="openAddUI()">添加</a> <a href="#" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="delPositions()">删除</a>
				</div>
				<div>
					<input id="keyWordTextInput" class="easyui-textbox"
						data-options="prompt:'仓位名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryForPage()">查询</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>