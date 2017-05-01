package com.bpbj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.service.IDictionaryService;
import com.base.admin.service.IParamService;
import com.base.util.BaseUtil;
import com.base.util.DateStyle;
import com.base.util.DateUtil;
import com.bpbj.dao.BPBJToolMapper;
import com.bpbj.dao.BPBJToolTrackMapper;
import com.bpbj.entity.BaseTool;
import com.bpbj.entity.Batch;
import com.bpbj.entity.Tool;
import com.bpbj.entity.ToolTrack;
import com.bpbj.service.IBPBJBaseToolService;
import com.bpbj.service.IBPBJToolService;
import com.bpbj.util.ToolStatus;

@Service
public class BPBJToolServiceImpl implements IBPBJToolService {

	@Autowired
	private IBPBJBaseToolService baseToolService;

	@Autowired
	private IDictionaryService distionaryService;

	@Autowired
	private BPBJToolMapper toolMapper;

	@Autowired
	private BPBJToolTrackMapper toolTrackMapper;

	@Override
	public Map<String, Object> addNewTool(Tool tool) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolMapper.insertSelective(tool);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> checkInTool(Batch batch,
			Tool tool, ToolTrack toolTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		Tool temp = toolMapper.selectToolForObject(tool);
		int bool = 0;
		String msg = "";
		if (temp == null) {
			bool = toolMapper.insertSelective(tool);
			toolTrack.setTrackId(-1L);
			toolTrack.setToolId(tool.getToolId());
			toolTrack.setBatchId(tool.getBatchId());
			toolTrack.setToolStatus(tool.getToolStatus());
			bool = toolTrackMapper
					.insertSelective(toolTrack);
			if (bool == 0) {
				map.put("success", false);
				map.put("msg", "保存出错，请联系管理员");
			} else {
				map.put("success", true);
				map.put("msg", "保存成功");
			}
		} else {
			long toolStatus = temp.getToolStatus();
			if (toolStatus == ToolStatus.REJECT) {
				msg = "该工器具已经报废";
				map.put("success", false);
				map.put("msg", msg);
			} else if (toolStatus == ToolStatus.CHECK_OUT
					|| toolStatus == ToolStatus.BORROW) {
				Tool toolFromSearch = toolMapper
						.selectToolForObject(tool);
				toolFromSearch.setPosId(tool.getPosId());
				toolFromSearch
						.setStoreId(tool.getStoreId());
				toolFromSearch.setToolStatus(
						tool.getToolStatus());
				toolFromSearch
						.setToolBox(tool.getToolBox());
				toolFromSearch.setToolRemark(
						tool.getToolRemark());
				toolFromSearch
						.setBatchId(batch.getBatchId());
				toolFromSearch.setToolDeptId(
						tool.getToolDeptId());
				// 更新tool状态，新增track记录
				bool = toolMapper
						.updateByPrimaryKeySelective(
								toolFromSearch);
				BaseTool baseToolParam = new BaseTool();
				baseToolParam.setBaseToolId(
						toolFromSearch.getBaseToolId());
				Map<String, Object> baseTool = baseToolService
						.selectBaseToolForObject(
								baseToolParam);
				toolTrack.setToolId(
						toolFromSearch.getToolId());
				toolTrack.setToolStatus(
						tool.getToolStatus());
				toolTrack.setBatchId(batch.getBatchId());
				toolTrack
						.setBatchCode(batch.getBatchCode());
				toolTrack.setTrackId(-1L);
				toolTrack.setBaseToolId(BaseUtil.strToLong(
						baseTool.get("BASE_TOOL_ID")
								.toString()));
				toolTrack.setToolTestDate(
						toolFromSearch.getToolTestDate());
				toolTrack.setToolRejectDate(
						toolFromSearch.getToolRejectDate());
				toolTrack.setToolTestDateCircle(
						toolFromSearch
								.getToolTestDateCircle());
				toolTrack.setToolNextTestDate(toolFromSearch
						.getToolNextTestDate());
				toolTrack.setBaseToolName(baseTool
						.get("BASE_TOOL_NAME").toString());
				toolTrack.setBaseToolTypeId(
						BaseUtil.strToLong(baseTool
								.get("BASE_TOOL_TYPE_ID")
								.toString()));
				toolTrack.setBaseToolTypeName(
						baseTool.get("BASE_TOOL_TYPE_NAME")
								.toString());
				toolTrack.setBaseToolModel(baseTool
						.get("BASE_TOOL_MODEL").toString());
				toolTrack.setBaseToolSpec(baseTool
						.get("BASE_TOOL_SPEC").toString());
				toolTrack.setBaseToolManufacturerName(
						baseTool.get(
								"BASE_TOOL_MANUFACTURER_NAME")
								.toString());
				toolTrack.setToolManufactureDate(
						toolFromSearch
								.getToolManufactureDate());
				toolTrack.setToolPurchaseDate(toolFromSearch
						.getToolPurchaseDate());
				bool = toolTrackMapper
						.insertSelective(toolTrack);
				if (bool == 0) {
					map.put("success", false);
					map.put("msg", "保存出错，请联系管理员");
				} else {
					map.put("success", true);
					map.put("msg", "保存成功");
				}
			} else if (toolStatus == ToolStatus.CHECK_IN_COMING
					|| toolStatus == ToolStatus.CHECK_IN) {
				msg = "该工器具已经入库";
				map.put("success", false);
				map.put("msg", msg);
			} else {
				msg = "该工器具处于非正常状态，请查证";
				map.put("success", false);
				map.put("msg", msg);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> exchangeTool(Batch batch,
			Tool toolParam, ToolTrack toolTrack) {
		return checkOutTool(batch, toolParam, toolTrack);
	}
	
	@Override
	public Map<String, Object> useTool(Batch batch,
			Tool toolParam, ToolTrack toolTrack) {
		return checkOutTool(batch, toolParam, toolTrack);
	}

	@Override
	public Map<String, Object> backTool(Batch batch,
			Tool toolParam, ToolTrack toolTrack) {
		return checkInTool(batch, toolParam, toolTrack);
	}
	
	@Override
	public Map<String, Object> selfRetrunTool(Batch batch,
			Tool toolParam, ToolTrack toolTrack) {
		return checkInTool(batch, toolParam, toolTrack);
	}

	@Override
	public Map<String, Object> rejectTool(Batch batch,
			Tool toolParam, ToolTrack toolTrack) {
		return checkOutTool(batch, toolParam, toolTrack);
	}

	@Override
	public Map<String, Object> borrowTool(Batch batch,
			Tool toolParam, ToolTrack toolTrack) {
		return checkOutTool(batch, toolParam, toolTrack);
	}

	@Override
	public Map<String, Object> checkOutTool(Batch batch,
			Tool toolParam, ToolTrack toolTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		Tool toolFromSearch = toolMapper
				.selectToolForObject(toolParam);
		int bool = 0;
		String msg = "";
		if (toolFromSearch == null) {
			map.put("success", false);
			map.put("msg", "查询出错，没有该工器具");
		} else {
			long toolStatus = toolFromSearch
					.getToolStatus();
			if (toolStatus == ToolStatus.REJECT) {
				msg = "该工器具已经报废";
				map.put("success", false);
				map.put("msg", msg);
			} else if (toolStatus == ToolStatus.CHECK_IN) {
				toolFromSearch
						.setPosId(toolParam.getPosId());
				toolFromSearch
						.setStoreId(toolParam.getStoreId());
				toolFromSearch.setToolStatus(
						toolParam.getToolStatus());
				toolFromSearch
						.setToolBox(toolParam.getToolBox());
				toolFromSearch.setToolRemark(
						toolParam.getToolRemark());
				toolFromSearch
						.setBatchId(batch.getBatchId());
				// 更新tool状态，新增track记录
				bool = toolMapper
						.updateByPrimaryKeySelective(
								toolFromSearch);
				BaseTool baseToolParam = new BaseTool();
				baseToolParam.setBaseToolId(
						toolFromSearch.getBaseToolId());
				Map<String, Object> baseTool = baseToolService
						.selectBaseToolForObject(
								baseToolParam);
				toolTrack.setToolId(
						toolFromSearch.getToolId());
				toolTrack.setToolStatus(
						toolParam.getToolStatus());
				toolTrack.setBatchId(batch.getBatchId());
				toolTrack
						.setBatchCode(batch.getBatchCode());
				toolTrack.setTrackId(-1L);
				toolTrack.setBaseToolId(BaseUtil.strToLong(
						baseTool.get("BASE_TOOL_ID")
								.toString()));
				toolTrack.setToolTestDate(
						toolFromSearch.getToolTestDate());
				toolTrack.setToolRejectDate(
						toolFromSearch.getToolRejectDate());
				toolTrack.setToolTestDateCircle(
						toolFromSearch
								.getToolTestDateCircle());
				toolTrack.setToolNextTestDate(toolFromSearch
						.getToolNextTestDate());
				toolTrack.setBaseToolName(baseTool
						.get("BASE_TOOL_NAME").toString());
				toolTrack.setBaseToolTypeId(
						BaseUtil.strToLong(baseTool
								.get("BASE_TOOL_TYPE_ID")
								.toString()));
				toolTrack.setBaseToolTypeName(
						baseTool.get("BASE_TOOL_TYPE_NAME")
								.toString());
				toolTrack.setBaseToolModel(baseTool
						.get("BASE_TOOL_MODEL").toString());
				toolTrack.setBaseToolSpec(baseTool
						.get("BASE_TOOL_SPEC").toString());
				toolTrack.setBaseToolManufacturerName(
						baseTool.get(
								"BASE_TOOL_MANUFACTURER_NAME")
								.toString());
				toolTrack.setToolManufactureDate(
						toolFromSearch
								.getToolManufactureDate());
				toolTrack.setToolPurchaseDate(toolFromSearch
						.getToolPurchaseDate());
				bool = toolTrackMapper
						.insertSelective(toolTrack);
				if (bool == 0) {
					map.put("success", false);
					map.put("msg", "保存出错，请联系管理员");
				} else {
					map.put("success", true);
					map.put("msg", "保存成功");
				}
			} else if (toolStatus == ToolStatus.CHECK_OUT_COMING
					|| toolStatus == ToolStatus.CHECK_OUT) {
				msg = "该工器具已经出库";
				map.put("success", false);
				map.put("msg", msg);
			} else {
				msg = "该工器具处于非正常状态，请查证";
				map.put("success", false);
				map.put("msg", msg);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteTools(Tool tool) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolMapper.deleteByPrimaryKeys(tool);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> resetTool(Tool tool,
			ToolTrack toolTrack) {
		int bool = 1;
		// 查询track的条数
		List<ToolTrack> toolTracks = toolTrackMapper
				.selectToolTracksForList(toolTrack);
		if (toolTracks.size() == 1) {// 1.=1，删掉tool与track
			bool = toolTrackMapper
					.deleteByPrimaryKeys(toolTrack);
			bool = toolMapper.deleteByPrimaryKeys(tool);
		} else {// 2.>1，删掉track，然后用tooltrack的状态替换当前tool的状态
			ToolTrack temp = toolTracks.get(1);
			tool.setPosId(temp.getPosId());
			tool.setStoreId(temp.getStoreId());
			tool.setToolDeptId(temp.getToolDeptId());
			tool.setToolStatus(temp.getToolStatus());
			tool.setToolBox(temp.getToolBox());
			tool.setBatchId(temp.getBatchId());
			bool = toolMapper
					.updateByPrimaryKeySelective(tool);
			bool = toolTrackMapper
					.deleteByPrimaryKeys(toolTrack);
		}

		Map<String, Object> map = new HashMap<String, Object>();

		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public List<Tool> selectToolsForList(Tool tool) {
		return toolMapper.selectToolsForList(tool);
	}

	@Autowired
	private IParamService paramService;

	@Override
	public Map<String, Object> selectToolsForPage(
			HashMap<String, Object> param) {
		List<Map<String, Object>> tools = toolMapper
				.selectToolsForPage(param);
		for (Map<String, Object> item : tools) {
			String toolStatus = item.get("TOOL_STATUS")
					.toString();
			List<Map<String, Object>> dicList = distionaryService
					.getDictionaryListByDicCode(
							"TOOL_STATUS");
			for (Map<String, Object> dic : dicList) {
				if (dic.get("ID").equals(toolStatus)) {
					item.put("TOOL_STATUS_NAME",
							dic.get("TEXT").toString());
					break;
				}
			}
			if (item.get("TOOL_REJECT_DATE") != null) {
				item.put("TOOL_REJECT_DATE",
						DateUtil.getDate(
								item.get("TOOL_REJECT_DATE")
										.toString()));
				Date toolRejectDate = DateUtil
						.StringToDate(DateUtil.getDate(
								item.get("TOOL_REJECT_DATE")
										.toString()));
				Date now = new Date();
				if (now.after(toolRejectDate)) {
					item.put("NEED_REJECT", 1);
				}
			}
			if (item.get("TOOL_NEXT_TEST_DATE") != null) {
				item.put("TOOL_NEXT_TEST_DATE",
						DateUtil.getDate(item
								.get("TOOL_NEXT_TEST_DATE")
								.toString()));
				// 计算超期的日期
				int days = BaseUtil.strToInt(
						paramService.queryParamsForMap(
								"BEFORE_TEST_DAYS"));
				Date now = new Date();
				Date sysDate = DateUtil.addDay(new Date(),
						days);
				Date toolNextTestDate = DateUtil
						.StringToDate(DateUtil.getDate(item
								.get("TOOL_NEXT_TEST_DATE")
								.toString()));
				if (sysDate.after(toolNextTestDate)) {
					item.put("NEED_TEST", 1);
				}
				if (now.after(toolNextTestDate)) {
					item.put("NEED_TEST", 2);
				}
			}
			if (item.get("TOOL_MANUFACTURE_DATE") != null) {
				item.put("TOOL_MANUFACTURE_DATE",
						DateUtil.DateToString(
								(Date) item
										.get("TOOL_MANUFACTURE_DATE"),
								DateStyle.YYYY_MM_DD));
			}
			if (item.get("TOOL_PURCHASE_DATE") != null) {
				item.put("TOOL_PURCHASE_DATE",
						DateUtil.DateToString(
								(Date) item
								.get("TOOL_PURCHASE_DATE"),
								DateStyle.YYYY_MM_DD));
			}
			if (item.get("TOOL_TEST_DATE") != null) {
				item.put("TOOL_TEST_DATE",
						DateUtil.DateToString(
								(Date) item
								.get("TOOL_TEST_DATE"),
								DateStyle.YYYY_MM_DD));
			}
		}
		int count = toolMapper
				.selectCountOfToolsForPage(param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", tools);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateTool(Tool tool) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolMapper
				.updateByPrimaryKeySelective(tool);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateToolByBatch(
			Tool tool) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolMapper.updateToolByBatch(tool);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
