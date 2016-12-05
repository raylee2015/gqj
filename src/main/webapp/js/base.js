//发生错误时需要执行的代码
var errorCodeForQuery = 'alert(\'数据加载发生错误，请联系系统管理员\')\;';

// 发生错误时需要执行的代码
var errorCodeForOption = 'alert(\'数据操作发生错误，请联系系统管理员\')\;';

// 重写操作列，使得操作栏显示编辑连接
function editColumnFormatter(fieldValue, rowData, rowIndex) {
	var btn = '<a class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditUI(\'edit\',\''
			+ rowIndex + '\')" href="javascript:void(0)">编辑</a>';
	return btn;
}

// 获取COMBOTREE里面的值
function getComboTreeValue(comboTreeId) {
	var comboTree = eval('$(\'#' + comboTreeId + '\')');
	return comboTree.combotree('getValue');
}

// 获取输入框里面的值
function getTextBoxValue(TextBoxId) {
	var textBox = eval('$(\'#' + TextBoxId + '\')');
	return textBox.textbox('getValue');
}

// 输入框赋值
function setTextBoxValue(TextBoxId, value) {
	var textBox = eval('$(\'#' + TextBoxId + '\')');
	return textBox.textbox('setValue', value);
}

// 查询时错误反馈
function errorFunctionForQuery() {
	eval(errorCodeForQuery);
}
// 操作时错误反馈
function errorFunctionForOption() {
	eval(errorCodeForOption);
}

// 与后台交互方法
function ajaxFunction(params, url, successFunction, errorFunction, haveTree) {
	$.ajax({
		type : 'POST',
		url : url,
		data : params,
		success : function(result, textStatus) {
			successFunction(result, haveTree);
		},
		error : errorFunction,
		dataType : 'json'
	});
}

// 保存成功后执行的方法
function successFunctionForSave(result, haveTree) {
	var title = '';
	if (result.success) {
		$('#editUI').window('close'); // close the window
		$('#datagrid').datagrid('reload'); // reload the datagrid
		if (haveTree) {
			$('#tree').tree('reload'); // reload the tree
		}
		title = '保存成功';
	} else {
		title = '保存失败';
	}
	showMessage(title, result.msg);
}

/* 保存数据 */
function save1(params, urlForSave, haveTree) {
	save2(params, urlForSave, successFunctionForSave, errorFunctionForOption,
			haveTree);
}

// 保存数据
function save2(params, urlForSave, successFunction, errorFunction, haveTree) {
	ajaxFunction(params, urlForSave, successFunction, errorFunction, haveTree);
}

// 重载表格的数据
function refresh() {
	$('#datagrid').datagrid('reload');
}

// 关闭编辑窗口
function closeEditUI() {
	$('#editUI').window('close');
}

// 关闭AJAX相应的缓存
function closeCache() {
	$.ajaxSetup({
		cache : false
	});
}

// 初始化
function initDocument() {
	registerKeyPressForTextInput();
	closeCache();
}

// 注册按下回车的事件
function registerKeyPressForTextInput() {
	var keyWordTextInput = $('#keyWordTextInput');
	keyWordTextInput.textbox('textbox').bind('keypress', function(e) {
		if (e.keyCode == 13) {
			queryForPage();
		}
	});
}

// 设置编辑界面的树的数据源
function setComboTreeDataProvider() {
	var treeNodes = $('#tree').tree('getRoots');
	$('#comboTree').combotree('loadData', treeNodes);
}

// 提示框显示信息
function showMessage(title, messaeg) {
	$.messager.show({
		title : title,
		msg : messaeg
	});
}

// 根据所选记录获取id串
function getIdsOfSelectedItems(columnNameForCheck, columnNameForPost,
		tipsForChecked) {
	var rowDatas = $('#datagrid').datagrid('getSelections');
	var ids = '';
	for (var i = 0; i < rowDatas.length; i++) {
		var item = rowDatas[i];
		if (typeof (eval('item.' + columnNameForCheck)) != 'undefined') {
			ids += eval('item.' + columnNameForCheck) + ',';
		} else {
			alert(tipsForChecked);
			break;
		}
	}
	ids = ids.substring(0, ids.length - 1);
	return ids;
}

// 检查是否选中了记录
function checkSelectedItems(tipsForUnSelected) {
	var rowDatas = $('#datagrid').datagrid('getSelections');
	if (rowDatas.length == 0) {
		alert(tipsForUnSelected);
		return false;
	} else {
		return true;
	}
}

// 删除成功之后的方法
function successFunctionForDel(result, haveTree) {
	// reload the datagrid
	$('#datagrid').datagrid('reload');
	if (haveTree) {
		// reload the tree
		$('#tree').tree('reload');
	}
}

// 删除
function del(params, tipsForUnSelected, tipsForDelete, urlForDelete, haveTree) {
	if (checkSelectedItems(tipsForUnSelected)) {
		$.messager.confirm('确认', tipsForDelete, function(r) {
			if (r) {
				ajaxFunction(params, urlForDelete, successFunctionForDel,
						errorFunctionForOption, haveTree);
			} else {
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('uncheckAll');
			}
		});
	}
}

// 翻页查询
function query(params, url) {
	ajaxFunction(params, url, successFunctionForQuery, errorFunctionForQuery,
			true);
}

// 当查询成功时需要执行的代码
function successFunctionForQuery(result, haveTree) {
	$('#datagrid').datagrid('loadData', result);
}

// 打开编辑窗口
function openAddDataUI1() {
	openAddDataUI2(false);
}

// 打开编辑窗口
function openAddDataUI2(haveTree) {
	$('#form').form('clear');
	if (haveTree) {
		setComboTreeDataProvider();
		$('#comboTree').combotree('setValue', 0);
	}
	$('#editUI').window('open');
}

// 打开编辑窗口
function openEditDataUI1(rowData) {
	openEditDataUI2(rowData, false, '', '');
}

// 打开编辑窗口
function openEditDataUI2(rowData, haveTree, columnNameOfcomboTreeValue,
		columnNameOfcomboTreeText) {
	$('#editUI').window('open');
	$('#form').form('load', rowData);
	if (haveTree) {
		setComboTreeDataProvider();
		$('#comboTree').combotree('setValue',
				eval('rowData.' + columnNameOfcomboTreeValue));
		$('#comboTree').combotree('setText',
				eval('rowData.' + columnNameOfcomboTreeText));
	}
}