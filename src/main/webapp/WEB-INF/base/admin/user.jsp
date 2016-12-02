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
<script type="text/javascript">
	//记录新增或者修改的方法
	var url;

	//重写操作列，使得操作栏显示编辑连接
	function editColumnFormatter(fieldValue, rowData, rowIndex) {
		var btn = '<a class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditUI(\'edit\',\''
				+ rowIndex + '\')" href="javascript:void(0)">编辑</a>';
		return btn;
	}

	//设置上级部门树的数据源
	function setDeptTreeDataProvider() {
		var deptTreeNodes = $('#deptTree').tree('getRoots');
		$('#userDeptComboTree').combotree('loadData', deptTreeNodes);
	}

	//打开编辑窗口
	function openEditUI(opType, rowIndex) {
		setDeptTreeDataProvider();
		if (opType == 'add') {
			$('#editUI').window('open');
			$('#form').form('clear');
			setDeptTreeDataProvider();
			$('#userDeptComboTree').combotree('setValue', 0);
			url = 'addNewUser.do';
		} else if (opType == 'edit') {
			var rowData = $('#datagrid').datagrid('getData').rows[rowIndex];
			$('#editUI').window('open');
			$('#form').form('load', rowData);
			$('#userDeptComboTree').combotree('setValue', rowData.USER_DEPT_ID);
			$('#userDeptComboTree')
					.combotree('setText', rowData.USER_DEPT_NAME);
			url = 'updateUser.do?USER_ID=' + rowData.USER_ID;
		}
	}

	//保存数据
	function save() {
		$('#form').form('submit', {
			url : url,
			onSubmit : function(param) {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.success) {
					$('#editUI').window('close'); // close the window
					$('#datagrid').datagrid('reload'); // reload the datagrid
					$.messager.show({
						title : '保存成功',
						msg : result.msg
					});
				} else {
					$.messager.show({
						title : '保存成功',
						msg : result.msg
					});
				}
			}
		});
	}

	function refresh() {
		$('#datagrid').datagrid('reload');
	}

	//关闭编辑窗口
	function closeEditUI() {
		$('#editUI').window('close');
	}

	//删除
	function del() {
		var rowDatas = $('#datagrid').datagrid('getSelections');
		if (rowDatas.length == 0) {
			alert('请选择用户');
			return;
		}
		var ids = '';
		for (var i = 0; i < rowDatas.length; i++) {
			var item = rowDatas[i];
			ids += item.USER_ID + ',';
		}
		ids = ids.substring(0, ids.length - 1);
		if (ids.length > 0) {
			$.messager.confirm('确认', '是否删除所选用户?', function(r) {
				if (r) {
					$.post('delUsers.do', {
						USER_IDS : ids
					}, function(result) {
						if (result.success) {
							$('#datagrid').datagrid('reload'); // reload the datagrid
							$.messager.show({
								title : '删除成功',
								msg : result.msg
							});
						} else {
							$.messager.show({ // show error message
								title : '删除失败',
								msg : result.errorMsg
							});
						}
					}, 'json');
				}else{
					$('#datagrid').datagrid('unselectAll');
					$('#datagrid').datagrid('uncheckAll');
				}
			});
		}
	}

	//初始化用户密码
	function initUserPassWord() {
		var rowDatas = $('#datagrid').datagrid('getSelections');
		if (rowDatas.length == 0) {
			alert('请选择用户');
			return;
		}
		var ids = '';
		for (var i = 0; i < rowDatas.length; i++) {
			var item = rowDatas[i];
			ids += item.USER_ID + ',';
		}
		ids = ids.substring(0, ids.length - 1);
		if (ids.length > 0) {
			$.messager.confirm('确认', '是否初始化所选用户密码?', function(r) {
				if (r) {
					$.post('initUserPassWord.do', {
						USER_IDS : ids
					}, function(result) {
						if (result.success) {
							$('#datagrid').datagrid('unselectAll');
							$('#datagrid').datagrid('uncheckAll');
							$('#datagrid').datagrid('reload'); // reload the datagrid
							$.messager.show({
								title : '初始化成功',
								msg : result.msg
							});
						} else {
							$.messager.show({ // show error message
								title : '初始化失败',
								msg : result.errorMsg
							});
						}
					}, 'json');
				}else{
					$('#datagrid').datagrid('unselectAll');
					$('#datagrid').datagrid('uncheckAll');
				}
			});
		}
	}

	//查询
	function queryForPage(userDeptId) {
		$.post('queryUserPage.do',
				{
					userDeptId : userDeptId,
					keyWord : $('#keyWordTextInput').textbox('getValue'),
					page : 1,
					rows : $('#datagrid').datagrid('getPager').data(
							"pagination").options.pageSize
				}, function(result) {
					$('#datagrid').datagrid('loadData', result.rows);
				}, 'json');
	}

	//关闭AJAX相应的缓存
	$.ajaxSetup({
		cache : false
	});

	//点击树查询
	function queryDeptPageWithTree() {
		$('#deptTree').tree({
			onClick : function(node) {
				queryForPage(node.id); // 在用户点击的时候提示
			}
		});
	}

	//初始化
	function init() {
		registerKeyPressForTextInput();
	}

	//注册按下回车的事件
	function registerKeyPressForTextInput() {
		var keyWordTextInput = $('#keyWordTextInput');
		keyWordTextInput.textbox('textbox').bind('keypress', function(e) {
			if (e.keyCode == 13) {
				queryForPage();
			}
		});
	}

	//页面加载完
	$(document).ready(function() {
		init();
	});
</script>
</head>
<body>
	<!-- 列表页面 -->
	<div class="easyui-layout" data-options="fit:true">
		<div region="west" ,collapsible="false" style="width: 200px;">
			<ul id="deptTree" class="easyui-tree" url="queryDeptTree.do"
				onClick="queryDeptPageWithTree()" method="get" animate="true"
				lines="true"></ul>
		</div>
		<div region="center">
			<table id="datagrid" class="easyui-datagrid" rownumbers="true"
				toolbar="#toolbar" idField="USER_ID" pagination="true" pageSize="30"
				pageNumber="1" checkOnSelect="false" url="queryUserPage.do"
				method="get" fit="true">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th field="op" formatter="editColumnFormatter">操作</th>
						<th field="USER_NAME" width="100">用户名称</th>
						<th field="USER_CODE" width="100">用户编号</th>
						<th field="USER_DEPT_NAME" width="100">所属部门</th>
						<th field="USER_PHONE" width="100">手机号码</th>
						<th field="USER_SORT" width="80">排序号</th>
					</tr>
				</thead>
			</table>
			<div id="toolbar">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refresh()">刷新</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="openEditUI('add')">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="del()">删除</a><a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true" onclick="initUserPassWord()">初始化密码</a>
				</div>
				<div>
					<input id="keyWordTextInput" class="easyui-textbox"
						data-options="prompt:'用户名称',validType:'length[0,10]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryForPage('')">查询</a>
				</div>

			</div>
		</div>
	</div>

	<!--  详细界面 -->
	<div id="editUI" class="easyui-window" title="添加用户" closed="true"
		data-options="iconCls:'icon-save'"
		style="width: 400px; height: 260px; padding: 5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div region="north" fit="true" border="false">
				<form id="form" method="post" style="width: 100%;">
					<table width="100%">
						<tr>
							<td width="22%">所属部门:</td>
							<td><input id="userDeptComboTree" class="easyui-combotree"
								name="USER_DEPT_ID" data-options="required:true"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="22%">用户名称:</td>
							<td><input name="USER_NAME" class="easyui-textbox"
								data-options="prompt:'用户名称',required:true,validType:'length[3,10]'"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="22%">用户编号:</td>
							<td><input name="USER_CODE" class="easyui-textbox"
								data-options="prompt:'用户编号',required:true,validType:'length[3,10]'"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="22%">手机号码:</td>
							<td><input name="USER_PHONE" type="text"
								class="easyui-numberbox" style="width: 100%; height: 32px"
								data-options="precision:0,prompt:'手机号码',required:true,validType:'length[11,11]'" /></td>
						</tr>
						<tr>
							<td width="22%">排序号:</td>
							<td><input name="USER_SORT" type="text"
								class="easyui-numberbox" style="width: 100%; height: 32px"
								data-options="min:0,max:99,precision:0,prompt:'排序号',required:true,validType:'length[0,2]'" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px">
				<a class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="save()">保存</a> <a
					class="easyui-linkbutton" iconCls="icon-cancel"
					href="javascript:void(0)" onclick="closeEditUI()">关闭</a>
			</div>
		</div>
	</div>
</body>
</html>