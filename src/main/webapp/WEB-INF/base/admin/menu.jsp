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
		openAddDataUI2(true);
		setTextBoxValue('menuIdTextBox', -1);
		url = 'addNewMenu.do';
		$('#menuURLTextBox').textbox('enable');
		$('#menuExtCodeTextBox').textbox('enable');
	}

	//打开编辑窗口
	function openEditUI(opType, rowIndex) {
		var rowData = $('#datagrid').datagrid('getData').rows[rowIndex];
		openEditDataUI2(rowData, true, 'UP_MENU_ID', 'UP_MENU_NAME');
		url = 'updateMenu.do';
		$('#menuURLTextBox').textbox('enable');
		$('#menuExtCodeTextBox').textbox('enable');
	}

	//删除
	function delMenus() {
		var ids = getIdsOfSelectedItems('MENU_INNER_CODE', '数据出现问题，请联系系统管理员');
		var params = {
			MENU_IDS : ids
		};
		del(params, "请选择菜单", '是否删除所选菜单?', 'delMenus.do', true);
	}

	//更新级联数据
	function updataInnerData() {
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
	function saveMenu() {
		var params = {
			MENU_ID : getTextBoxValue('menuIdTextBox'),
			MENU_NAME : getTextBoxValue('menuNameTextBox'),
			MENU_LEVEL : getComboBoxValue('menuLevelComboBox'),
			MENU_URL : getTextBoxValue('menuURLTextBox'),
			MENU_SORT : getTextBoxValue('menuSortTextBox'),
			UP_MENU_ID : getComboTreeValue('comboTree'),
			MENU_EXT_CODE : getTextBoxValue('menuExtCodeTextBox')
		};
		save1(params, url, true);
	}

	//更新级联数据
	function updateInnerData() {
		var params = {};
		shwoConfirm(params, '是否更新级联数据?', 'updateInnerData.do', true);
	}

	//查询
	function queryForPage(menuInnerCode) {
		var params = {
			menuInnerCode : menuInnerCode,
			keyWord : $('#keyWordTextInput').textbox('getValue'),
			page : 1,
			rows : $('#datagrid').datagrid('getPager').data("pagination").options.pageSize
		};
		query(params, 'queryMenuPage.do');
	}

	//页面加载完
	$(document).ready(function() {
		initDocument();
		initDataGrid();
		initTree();
		initComboBox();
	});

	//初始化树
	function initTree() {
		$('#tree').tree({
			url : 'queryMenuTree.do',
			onClick : function(node) {
				if (typeof (node.menu_inner_code) == 'undefined') {
					alert('请关联菜单');
				} else {
					queryForPage(node.menu_inner_code); // 在菜单点击的时候提示
				}
			},
			onLoadError : function(arguments) {
				eval(errorCodeForQuery);
			}
		});
	}

	function initComboBox() {
		$('#menuLevelComboBox').combobox({
			valueField : 'ID',
			textField : 'TEXT',
			require : true,
			panelHeight : 'auto',
			prompt : '菜单级别',
			url : 'queryMenuLevelDropList.do',
			onChange : function(newValue, oldValue) {
				if (newValue == 0 || newValue == 1 || newValue == 2) {
					$('#menuURLTextBox').textbox('disable');
					$('#menuExtCodeTextBox').textbox('disable');
					$('#menuURLTextBox').textbox('setText', '-');
					$('#menuExtCodeTextBox').textbox('setText', '-');
					$('#menuURLTextBox').textbox('setValue', '-');
					$('#menuExtCodeTextBox').textbox('setValue', '-');
				} else if (newValue == 3) {
					$('#menuURLTextBox').textbox('enable');
					$('#menuExtCodeTextBox').textbox('disable');
					$('#menuURLTextBox').textbox('setText', '');
					$('#menuExtCodeTextBox').textbox('setText', '-');
					$('#menuURLTextBox').textbox('setValue', '');
					$('#menuExtCodeTextBox').textbox('setValue', '-');
				} else if (newValue == 4) {
					$('#menuURLTextBox').textbox('disable');
					$('#menuExtCodeTextBox').textbox('enable');
					$('#menuURLTextBox').textbox('setText', '-');
					$('#menuExtCodeTextBox').textbox('setText', '');
					$('#menuURLTextBox').textbox('setValue', '-');
					$('#menuExtCodeTextBox').textbox('setValue', '');
				}
			}
		});
	}

	//初始化列表元素
	function initDataGrid() {
		$('#datagrid').datagrid({
			url : 'queryMenuPage.do',
			idField : 'MENU_ID',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'op',
				title : '操作',
				formatter : editColumnFormatter
			}, {
				field : 'MENU_NAME',
				title : '菜单名称',
				width : 100,
			}, {
				field : 'MENU_LEVEL_NAME',
				title : '菜单级别',
				width : 100,
			}, {
				field : 'MENU_URL',
				title : '菜单链接',
				width : 250,
			}, {
				field : 'MENU_EXT_CODE',
				title : '扩展权限代码',
				width : 100,
			}, {
				field : 'MENU_SORT',
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
						iconCls="icon-remove" plain="true" onclick="delMenus()">删除</a><a
						href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="updateInnerData()">更新级联数据</a>
				</div>
				<div>
					<input id="keyWordTextInput" class="easyui-textbox"
						data-options="prompt:'菜单名称',validType:'length[0,10]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryForPage('')">查询</a>
				</div>

			</div>
		</div>
	</div>

	<!--  详细界面 -->
	<div id="editUI" class="easyui-window" title="添加菜单" closed="true"
		data-options="iconCls:'icon-save'"
		style="width: 400px; height: 300px; padding: 5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" fit="true" border="false">
				<form id="form" method="post" style="width: 100%;">
					<div style="display: none">
						<input id="menuIdTextBox" name="MENU_ID" class="easyui-textbox" />
					</div>
					<table width="100%">
						<tr>
							<td width="30%">上级菜单:</td>
							<td><input id="comboTree" class="easyui-combotree"
								name="UP_MENU_ID" data-options="required:true"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="30%">菜单名称:</td>
							<td><input id="menuNameTextBox" name="MENU_NAME"
								class="easyui-textbox"
								data-options="prompt:'菜单名称',required:true,validType:'length[0,25]'"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="30%">菜单级别:</td>
							<td><input id="menuLevelComboBox" name="MENU_LEVEL"
								class="easyui-combobox" style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="30%">菜单链接:</td>
							<td><input id="menuURLTextBox" name="MENU_URL"
								class="easyui-textbox"
								data-options="prompt:'菜单链接',required:true,validType:'length[0,50]'"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="30%">排序号:</td>
							<td><input id="menuSortTextBox" name="MENU_SORT"
								class="easyui-numberbox" style="width: 100%; height: 32px"
								data-options="min:0,max:99,precision:0,prompt:'排序号',required:true,validType:'length[0,2]'" /></td>
						</tr>
						<tr>
							<td width="30%">扩展权限代码:</td>
							<td><input id="menuExtCodeTextBox" name="MENU_EXT_CODE"
								type="text" class="easyui-textbox"
								style="width: 100%; height: 32px"
								data-options="prompt:'扩展权限代码',required:true,validType:'length[0,25]'" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px">
				<a class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="saveMenu()">保存</a> <a
					class="easyui-linkbutton" iconCls="icon-cancel"
					href="javascript:void(0)" onclick="closeEditUI()">关闭</a>
			</div>
		</div>
	</div>
</body>
</html>