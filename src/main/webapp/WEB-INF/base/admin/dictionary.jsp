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
	//记录新增或者修改的方法
	var url;

	//打开编辑窗口
	function openAddUI() {
		openAddDataUI1();
		setTextBoxValue('dictionaryIdTextBox', -1);
		url = 'addNewDictionary.do';
	}

	//打开编辑窗口
	function openEditUI(opType, rowIndex) {
		var rowData = $('#datagrid').datagrid('getData').rows[rowIndex];
		openEditDataUI1(rowData);
		url = 'updateDictionary.do';
	}

	//删除
	function delDictionarys() {
		var ids = getIdsOfSelectedItems('DIC_INNER_CODE', 'DIC_IDS', '请先更新级联数据');
		var params = {
			DIC_IDS : ids
		};
		del(params, "请选择部门", '是否删除所选部门及其下属部门?', 'delDictionarys.do', true);
	}

	//更新级联数据
	function updateInnerData() {
		$.messager.confirm('确认', '是否更新级联数据?', function(r) {
			if (r) {
				$.post('updateInnerData.do', {}, function(result) {
					if (result.success) {
						$('#datagrid').datagrid('reload'); // reload the datagrid
						$('#tree').tree('reload'); // reload the tree
						showMessage('更新级联数据成功', result.msg);
					} else {
						showMessage('更新级联数据失败', result.msg);
					}
				}, 'json');
			}
		});
	}

	// 保存数据
	function saveDictionary() {
		var params = {
			DIC_ID : getTextBoxValue('dictionaryIdTextBox'),
			DIC_NAME : getTextBoxValue('dictionaryNameTextBox'),
			DIC_SORT : getTextBoxValue('dictionarySortTextBox'),
			UP_DIC_ID : getComboTreeValue('comboTree')
		};
		save1(params, url, true);
	}

	//查询
	function queryForPage(dictionaryInnerCode) {
		var params = {
			dictionaryInnerCode : dictionaryInnerCode,
			keyWord : $('#keyWordTextInput').textbox('getValue'),
			page : 1,
			rows : $('#datagrid').datagrid('getPager').data("pagination").options.pageSize
		};
		query(params, 'queryDictionaryPage.do');
	}

	//页面加载完
	$(document).ready(function() {
		initDocument();
		initDataGrid();
		initTree();
	});

	//初始化树
	function initTree() {
		$('#tree').tree({
			url : 'queryDictionaryTree.do',
			onClick : function(node) {
				queryForPage(node.dictionary_inner_code); // 在用户点击的时候提示
			},
			onLoadError : function(arguments) {
				eval(errorCodeForQuery);
			}
		});
	}

	//初始化列表元素
	function initDataGrid() {
		$('#datagrid').datagrid({
			url : 'queryDictionaryPage.do',
			idField : 'DIC_ID',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'op',
				title : '操作',
				formatter : editColumnFormatter
			}, {
				field : 'DIC_NAME',
				title : '部门名称',
				width : 100,
			}, {
				field : 'UP_DIC_NAME',
				title : '上级部门',
				width : 100,
			}, {
				field : 'DIC_INNER_NAME',
				title : '部门级联',
				width : 250,
			}, {
				field : 'DIC_SORT',
				title : '排序号',
				width : 100,
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
		<div region="west" collapsible="false" style="width: 200px;">
			<ul id="tree" class="easyui-tree" method="get" animate="true"
				lines="true"></ul>
		</div>
		<div region="center">
			<table id="datagrid" class="easyui-datagrid" rownumbers="true"
				toolbar="#toolbar" pagination="true" pageSize="30" pageNumber="1"
				checkOnSelect="false" method="get" fit="true">
			</table>
			<div id="toolbar">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refresh()">刷新</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="openAddUI()">添加</a> <a href="#" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="delDictionarys()">删除</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="updateInnerData()">更新级联数据</a>
				</div>
				<div>
					<input id="keyWordTextInput" class="easyui-textbox"
						data-options="prompt:'部门名称',validType:'length[0,50]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryForPage('')">查询</a>
				</div>

			</div>
		</div>
	</div>

	<!--  详细界面 -->
	<div id="editUI" class="easyui-window" title="添加部门" closed="true"
		data-options="iconCls:'icon-save'"
		style="width: 400px; height: 190px; padding: 5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" fit="true" border="false">
				<form id="form" method="post" style="width: 100%;">
					<div style="display: none">
						<input id="dictionaryIdTextBox" name="DIC_ID"
							class="easyui-textbox" />
					</div>
					<table width="100%">
						<tr>
							<td width="22%">上级部门:</td>
							<td><input id="comboTree" name="UP_DIC_ID"
								class="easyui-combotree" data-options="required:true"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="22%">部门名称:</td>
							<td><input id="dictionaryNameTextBox" name="DIC_NAME"
								class="easyui-textbox"
								data-options="prompt:'部门名称',required:true,validType:'length[3,10]'"
								style="width: 100%; height: 32px" /></td>
						</tr>
						<tr>
							<td width="22%">排序号:</td>
							<td><input id="dictionarySortTextBox" name="DIC_SORT"
								type="text" class="easyui-numberbox"
								style="width: 100%; height: 32px"
								data-options="min:0,max:99,precision:0,prompt:'排序号',required:true,validType:'length[0,2]'" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px">
				<a class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="saveDictionary()">保存</a> <a
					class="easyui-linkbutton" iconCls="icon-cancel"
					href="javascript:void(0)" onclick="closeEditUI()">关闭</a>
			</div>
		</div>
	</div>
</body>
</html>