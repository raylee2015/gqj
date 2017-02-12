//发生错误时需要执行的代码
var errorCodeForQuery = 'alert(\'数据加载发生错误，请联系系统管理员\')\;';

// 发生错误时需要执行的代码
var errorCodeForOption = 'alert(\'数据操作发生错误，请联系系统管理员\')\;';

// 刷新列表
function refreshDataGrid(dataGridId) {
	reloadDataGrid(dataGridId);
}

// 获取ComboBox里面的值
function getComboBoxValue(comboBoxId) {
	var comboBox = eval('$(\'#' + comboBoxId + '\')');
	return comboBox.combobox('getValue');
}

// 获取ComboBox里面的值
function setComboBoxValue(comboBoxId, value) {
	var comboBox = eval('$(\'#' + comboBoxId + '\')');
	return comboBox.combobox('setValue', value);
}

// 获取datebox里面的值
function getDateBoxValue(dateBoxId) {
	var dateBox = eval('$(\'#' + dateBoxId + '\')');
	return dateBox.datebox('getValue');
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

// 获取列表的显示条数
function getPageSizeOfDataGrid(dataGridId) {
	var dataGrid = eval('$(\'#' + dataGridId + '\')');
	return dataGrid.datagrid('getPager').data("pagination").options.pageSize;
}

// 输入框赋值
function setTextBoxValue(TextBoxId, value) {
	var textBox = eval('$(\'#' + TextBoxId + '\')');
	return textBox.textbox('setValue', value);
}

// 输入框赋值
function setTextBoxText(TextBoxId, text) {
	var textBox = eval('$(\'#' + TextBoxId + '\')');
	return textBox.textbox('setText', text);
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
function ajaxFunction(params, url, successFunction, errorFunction) {
	$.ajax({
		type : 'POST',
		url : url,
		data : params,
		success : successFunction,
		error : errorFunction,
		dataType : 'json'
	});
}

// 注销
function logout() {
	$('#logoutForm').submit();
}

// 保存数据
function save(params, urlForSave, successFunction) {
	ajaxFunction(params, urlForSave, successFunction, errorFunctionForOption);
}

// 更新数据
function update(params, urlForUpdate, successFunction) {
	ajaxFunction(params, urlForUpdate, successFunction, errorFunctionForOption);
}

// 关闭AJAX相应的缓存
function closeCache() {
	$.ajaxSetup({
		cache : false
	});
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

// 提示框显示信息
function showMessage(title, message) {
	$.messager.show({
		title : title,
		msg : message
	});
}

// 根据所选记录获取id串
function getIdsOfSelectedItems(dataGridId, columnNameForSelect) {
	var dataGrid = eval('$(\'#' + dataGridId + '\')');
	var rowDatas = dataGrid.datagrid('getChecked');
	var ids = '';
	for (var i = 0; i < rowDatas.length; i++) {
		var item = rowDatas[i];
		if (typeof (eval('item.' + columnNameForSelect)) != 'undefined') {
			ids += eval('item.' + columnNameForSelect) + ',';
		} else {
			// 获取数据失败，提示失败
			alert('没有更新级联关系或者数据错误，请联系系统管理员');
			return;
		}
	}
	ids = ids.substring(0, ids.length - 1);
	return ids;
}

// 检查是否选中了记录
function checkSelectedItems(dataGridId, tipsForUnSelected) {
	var dataGrid = eval('$(\'#' + dataGridId + '\')');
	var rowDatas = dataGrid.datagrid('getSelections');
	if (rowDatas.length == 0) {
		alert(tipsForUnSelected);
		return false;
	} else {
		return true;
	}
}

// 重新加载列表
function reloadDataGrid(dataGridId) {
	var dataGrid = eval('$(\'#' + dataGridId + '\')');
	dataGrid.datagrid('reload');
	dataGrid.datagrid('unselectAll');
	dataGrid.datagrid('uncheckAll');
}

// 重新加载树形列表
function reloadTreeGrid(treeGridId) {
	var treeGrid = eval('$(\'#' + treeGridId + '\')');
	treeGrid.treegrid('reload');
	treeGrid.treegrid('unselectAll');
	treeGrid.treegrid('uncheckAll');
}

function reloadTree(treeId) {
	var tree = eval('$(\'#' + treeId + '\')');
	tree.tree('reload');
}

// 确认
function showMessageBox(params, url, tipsForConfirm, successFunction) {
	$.messager.confirm('确认', tipsForConfirm, function(confirmOrNot) {
		if (confirmOrNot) {
			ajaxFunction(params, url, successFunction, errorFunctionForOption);
		}
	});
}

// 翻页查询
function query(params, url, successFunction) {
	ajaxFunction(params, url, successFunction, errorFunctionForQuery);
}

// 为表格设置数据
function dataGridLoadData(dataGridId, result) {
	var dataGrid = eval('$(\'#' + dataGridId + '\')');
	dataGrid.datagrid('loadData', result);
	dataGrid.datagrid('uncheckAll');
	dataGrid.datagrid('unselectAll');
}

// 为表格设置数据
function treeGridLoadData(treeGridId, result) {
	var treeGrid = eval('$(\'#' + treeGridId + '\')');
	treeGrid.treegrid('loadData', result);
	treeGrid.treegrid('uncheckAll');
	treeGrid.treegrid('unselectAll');
}

// 关闭编辑窗口
function closeEditUI(editUIId) {
	var editUI = eval('$(\'#' + editUIId + '\')');
	editUI.dialog('destroy');
	editUI.remove();
}

// 关闭窗口
function closeUI(UIId) {
	var UI = eval('$(\'#' + UIId + '\')');
	UI.dialog('destroy');
	UI.remove();
}

// 打开编辑窗口
function openEditUI(editUIId) {
	var editUI = eval('$(\'#' + editUIId + '\')');
	editUI.dialog('open');
}

// 打开窗口
function openUI(UIId) {
	var UI = eval('$(\'#' + UIId + '\')');
	UI.dialog('open');
}

// 获取本页面列表的行的数据对象
function getRowDataOfSelfDataGrid(dataGridId, rowIndex) {
	var dataGrid = eval('$(\'#' + dataGridId + '\')');
	return dataGrid.datagrid('getData').rows[rowIndex];
}

// 获取本页面列表的行的数据对象
function getRowDataOfSelfTreeGrid(treeGridId, rowIndex) {
	var treeGrid = eval('$(\'#' + treeGridId + '\')');
	return treeGrid.treegrid('getData')[rowIndex];
}

// 获取父页面列表的行的数据对象
function getRowDataOfParentDataGrid(dataGridId, rowIndex) {
	var dataGrid = eval('parent.$(\'#' + dataGridId + '\')');
	return dataGrid.datagrid('getData').rows[rowIndex];
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