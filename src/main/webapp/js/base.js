//发生错误时需要执行的代码
var errorCodeForQuery = 'alert(\'数据加载发生错误，请联系系统管理员\')\;';

// 发生错误时需要执行的代码
var errorCodeForOption = 'alert(\'数据操作发生错误，请联系系统管理员\')\;';

// 重写操作列，使得操作栏显示编辑连接
function editColumnFormatter(fieldValue, rowData, rowIndex) {
	var btn = '<a class="easyui-linkbutton"  onclick="openEditUI(\'' + rowIndex
			+ '\')" href="javascript:void(0)">编辑</a>';
	return btn;
}

// 获取ComboBox里面的值
function getComboBoxValue(comboBoxId) {
	var comboBox = eval('$(\'#' + comboBoxId + '\')');
	return comboBox.combobox('getValue');
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

// 注销
function logout() {
	$('#logoutForm').submit();
}

// 保存成功后执行的方法
function successFunctionForSave(result, haveTree) {
	var title = '';
	if (result.success) {
		closeEditUI();
		reloadData(haveTree);
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
	reloadDataGrid();
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
	registerKeyPressForTextInput('keyWordTextInput', queryForPage);
	closeCache();
}

// 注册按下回车的事件
function registerKeyPressForTextInput(textInputId, searchFunction) {
	var keyWordTextInput = eval('$(\'#' + textInputId + '\')');
	keyWordTextInput.textbox('textbox').bind('keypress', function(e) {
		if (e.keyCode == 13) {
			searchFunction();
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
function getIdsOfSelectedItems(columnNameForCheck, tipsForChecked) {
	var rowDatas = $('#datagrid').datagrid('getSelections');
	var ids = '';
	for (var i = 0; i < rowDatas.length; i++) {
		var item = rowDatas[i];
		if (typeof (eval('item.' + columnNameForCheck)) != 'undefined') {
			ids += eval('item.' + columnNameForCheck) + ',';
		} else {
			// 当需要获取的数据为空时，将提示失败
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
	reloadData(haveTree);
}

// 确认成功之后的方法
function successFunctionForConfirm(result, haveTree) {
	showMessage(result.msg, result.msg);
	reloadData(haveTree);
}

function reloadData(haveTree) {
	// reload the datagrid
	reloadDataGrid();
	if (haveTree) {
		// reload the tree
		reloadTree();
	}
}

function reloadDataGrid() {
	$('#datagrid').datagrid('reload');
	$('#datagrid').datagrid('unselectAll');
	$('#datagrid').datagrid('uncheckAll');
}

function reloadTree() {
	$('#tree').tree('reload');
}

// 删除
function del(params, tipsForUnSelected, tipsForDelete, urlForDelete, haveTree) {
	getDataAndDo(params, tipsForUnSelected, tipsForDelete, urlForDelete,
			haveTree);
}

// 获取记录，然后进行操作
function getDataAndDo(params, tipsForUnSelected, tipsForDo, urlForDo, haveTree) {
	if (checkSelectedItems(tipsForUnSelected)) {
		shwoConfirm(params, tipsForDo, urlForDo, haveTree);
	}
}

// 确认
function shwoConfirm(params, tipsForConfirm, urlForConfirm, haveTree) {
	$.messager.confirm('确认', tipsForConfirm, function(r) {
		if (r) {
			ajaxFunction(params, urlForConfirm, successFunctionForConfirm,
					errorFunctionForOption, haveTree);
		}
	});
}

// 翻页查询
function query(params, url) {
	ajaxFunction(params, url, successFunctionForQuery, errorFunctionForQuery,
			true);
}

// 当查询成功时需要执行的代码
function successFunctionForQuery(result, haveTree) {
	// $('#datagrid').datagrid('loadData', result);
	dataGridLoadData('datagrid', result)
}

// 为表格设置数据
function dataGridLoadData(dataGridId, result) {
	var dataGrid = eval('$(\'#' + dataGridId + '\')');
	dataGrid.datagrid('loadData', result);
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
	if (haveTree) {
		setComboTreeDataProvider();
		$('#comboTree').combotree('setValue',
				eval('rowData.' + columnNameOfcomboTreeValue));
		$('#comboTree').combotree('setText',
				eval('rowData.' + columnNameOfcomboTreeText));
	}
	$('#form').form('load', rowData);
	$('#editUI').window('open');
}

/**
 * 创建一个模态 Dialog
 * 
 * @param id
 *            divId
 * @param _url
 *            Div链接
 * @param _title
 *            标题
 * @param _width
 *            宽度
 * @param _height
 *            高度
 * @param _icon
 *            ICON图标
 */
function createModalDialog(id, _url, _title, _width, _height, _icon) {
	$("body").append("<div id='" + id + "' class='easyui-window'></div>");
	if (_width == null)
		_width = 800;
	if (_height == null)
		_height = 500;
	var content = '<iframe scrolling="auto" frameborder="0"  src="' + _url
			+ '" style="width:100%;height:100%;"></iframe>';
	$("#" + id).dialog({
		title : _title,
		width : _width,
		height : _height,
		cache : false,
		iconCls : _icon,
		content : content,
		shadow : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : false,
		modal : true,
		closed : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}