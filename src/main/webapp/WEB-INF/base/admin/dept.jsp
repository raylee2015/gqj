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
	function setComboTreeDataProvider() {
		var deptTreeNodes = $('#deptTree').tree('getRoots');
		$('#upDeptComboTree').combotree('loadData', deptTreeNodes);
	}

	//打开编辑窗口
	function openEditUI(opType, rowIndex) {
		if (opType == 'add') {
			$('#editUI').window('open');
			$('#form').form('clear');
			setComboTreeDataProvider();
			$('#upDeptComboTree').combotree('setValue', 0);
			url = 'addNewDept.do';
		} else if (opType == 'edit') {
			var rowData = $('#datagrid').datagrid('getData').rows[rowIndex];
			$('#editUI').window('open');
			$('#form').form('load', rowData);
			$('#upDeptComboTree').combotree('setValue', rowData.UP_DEPT_ID);
			$('#upDeptComboTree').combotree('setText', rowData.UP_DEPT_NAME);
			url = 'updateDept.do?DEPT_ID=' + rowData.DEPT_ID;
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
					$('#deptTree').tree('reload'); // reload the tree
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
			alert('请选择记录');
			return;
		}
		var deptIds = '';
		for (var i = 0; i < rowDatas.length; i++) {
			var dept = rowDatas[i];
			deptIds += dept.DEPT_INNER_CODE + ',';
		}
		deptIds = deptIds.substring(0, deptIds.length - 1);
		if (deptIds.length > 0) {
			$.messager.confirm('确认', '是否删除所选记录?', function(r) {
				if (r) {
					$.post('delDepts.do', {
						DEPT_IDS : deptIds
					}, function(result) {
						if (result.success) {
							$('#datagrid').datagrid('reload'); // reload the datagrid
							$('#deptTree').tree('reload'); // reload the tree
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
				}
			});
		}
	}

	//更新级联数据
	function updataInnerData() {
		$.messager.confirm('确认', '是否更新级联数据?', function(r) {
			if (r) {
				$.post('updataInnerData.do', {}, function(result) {
					if (result.success) {
						$('#datagrid').datagrid('reload'); // reload the datagrid
						$('#deptTree').tree('reload'); // reload the tree
						$.messager.show({
							title : '更新级联数据成功',
							msg : result.msg
						});
					} else {
						$.messager.show({ // show error message
							title : '更新级联数据失败',
							msg : result.errorMsg
						});
					}
				}, 'json');
			}
		});
	}

	//查询
	function queryForPage(deptInnerCode) {
		$.post('queryDeptPage.do',
				{
					deptInnerCode : deptInnerCode,
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
				queryForPage(node.dept_inner_code); // 在用户点击的时候提示
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
		<div region="west" collapsible="false" style="width: 200px;">
			<ul id="deptTree" class="easyui-tree" url="queryDeptTree.do"
				onClick="queryDeptPageWithTree()" method="get" animate="true"
				lines="true"></ul>
		</div>
		<div region="center">
			<table id="datagrid" class="easyui-datagrid" rownumbers="true"
				toolbar="#toolbar" idField="DEPT_ID" pagination="true" pageSize="30"
				pageNumber="1" checkOnSelect="false" url="queryDeptPage.do"
				method="get" fit="true">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th field="op" formatter="editColumnFormatter">操作</th>
						<th field="DEPT_NAME" width="100">部门名称</th>
						<th field="UP_DEPT_NAME" width="100">上级部门</th>
						<th field="DEPT_INNER_NAME" width="200">部门级联</th>
						<th field="DEPT_SORT" width="80">排序号</th>
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
						onclick="del()">删除</a> <a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true" onclick="updataInnerData()">更新级联数据</a>
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
					<table width="100%">
						<tr>
							<td width="22%">上级部门:</td>
							<td><input id="upDeptComboTree" name="UP_DEPT_ID"
								class="easyui-combotree" data-options="required:true"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="22%">部门名称:</td>
							<td><input name="DEPT_NAME" class="easyui-textbox"
								data-options="prompt:'部门名称',required:true,validType:'length[3,10]'"
								style="width: 100%; height: 32px"></td>
						</tr>
						<tr>
							<td width="22%">排序号:</td>
							<td><input name="DEPT_SORT" type="text"
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