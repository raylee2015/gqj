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
				
				queryNewBatchCode();

				registerKeyPressForTextInput('plugInCodeTextInput', saveBatch);

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

	// 保存数据
	function saveBatch() {
		var params = null;
		var batchCode = getTextBoxValue('batchCodeTextInput');
		var batchRemark = getTextBoxValue('batchRemarkTextInput');
		var batchType = getTextBoxValue('batchTypeTextInput');
		var plugInCode = getTextBoxValue('plugInCodeTextInput');

		params = {
			BATCH_CODE : batchCode,
			BATCH_TYPE : batchType,
			BATCH_REMARK : batchRemark,
			PLUGIN_CODE : plugInCode
		};
		url = "addNewBatchsAndDetails.do";
		save(params, url, successFunctionForSave);
	}

	//回调函数，保存成功后执行
	function successFunctionForSave(result) {
		if (result.success) {
			//更新提示
			var count = result.count;
			var plugInCode = getTextBoxValue('plugInCodeTextInput');
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
			var tip1 = "<font color='#0000ff' >" + plugInCode + " 已经" + opText
					+ "</font>";
			var tip2 = "<font >已经" + opText + count + "个插件</font>";
			$("#tip1").delay(200).html(tip1);
			$("#tip2").delay(200).html(tip2);
			setTextBoxText("plugInCodeTextInput", "");
			setTextBoxValue("plugInCodeTextInput", "");
		} else {
			alert(result.msg);
		}
	}

	//关闭编辑窗口
	function closeAddPlugInsUIForBatch() {
		parent.queryBatchs();
		parent.closeAddPlugInsUIForBatch();
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
			<tr>
				<td width="18%">备注:</td>
				<td><input id="batchRemarkTextInput" class="easyui-textbox"
					data-options="validType:'length[0,200]'"
					style="width: 100%; height: 32px" /></td>
			</tr>
			<tr>
				<td width="18%">插件编号:</td>
				<td><input id="plugInCodeTextInput" class="easyui-textbox"
					data-options="required:true,validType:'length[0,50]'"
					style="width: 100%; height: 32px" /></td>
			</tr>
			<tr>
				<td width="100%" align="right" colspan="2"><a href="#"
					id="saveBtn" class="easyui-linkbutton" onclick="saveBatch()"
					style="height: 32px">入库</a><a href="#" id="closeBtn"
					class="easyui-linkbutton" onclick="closeAddPlugInsUIForBatch()"
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
</body>
</html>