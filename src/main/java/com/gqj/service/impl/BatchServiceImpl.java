package com.gqj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.BaseUtil;
import com.base.util.DateUtil;
import com.gqj.dao.BatchMapper;
import com.gqj.entity.Batch;
import com.gqj.entity.Tool;
import com.gqj.entity.ToolTrack;
import com.gqj.service.IBatchService;
import com.gqj.service.IToolService;
import com.gqj.service.IToolTrackService;
import com.gqj.util.BatchType;
import com.gqj.util.ToolStatus;

@Service
public class BatchServiceImpl implements IBatchService {

	@Autowired
	private BatchMapper batchMapper;

	@Autowired
	private IToolService toolService;

	@Autowired
	private IToolTrackService toolTrackService;

	@Override
	public Map<String, Object> deleteBatchs(Batch batch) {
		Map<String, Object> map = new HashMap<>();
		String batchIds = batch.getIds();
		String[] batchId_arr = batchIds.split(",");
		int bool = 1;
		for (String batchId : batchId_arr) {
			Tool tool = new Tool();
			tool.setBatchId(BaseUtil.strToLong(batchId));
			// 查询tool
			List<Tool> tools = toolService.selectToolsForList(tool);
			for (Tool item : tools) {
				// 根据tool和相应的track来reset
				tool = item;
				ToolTrack toolTrack = new ToolTrack();
				toolTrack.setBatchId(BaseUtil.strToLong(batchId));
				toolTrack.setToolId(item.getToolId());
				// 根据toolID找出trackId
				toolTrack.setTrackId(toolTrackService
						.selectToolTracksForObject(toolTrack)
						.getTrackId());
				toolTrack.setBatchId(null);
				toolService.resetTool(tool, toolTrack);
			}
			Batch paramBatch = new Batch();
			paramBatch.setBatchId(BaseUtil.strToLong(batchId));
			bool = batchMapper.deleteByPrimaryKeys(paramBatch);
		}
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
	public Map<String, Object> confirmBatchs(Batch batch) {
		Map<String, Object> map = new HashMap<>();
		String batchIds = batch.getIds();
		String[] batchId_arr = batchIds.split(",");
		int bool = 1;
		for (String batchId : batchId_arr) {
			ToolTrack toolTrack = new ToolTrack();
			toolTrack.setBatchId(BaseUtil.strToLong(batchId));
			if (batch.getBatchType() == BatchType.CHECK_IN) {
				toolTrack.setToolStatus(ToolStatus.CHECK_IN);
			} else if (batch.getBatchType() == BatchType.CHECK_OUT) {
				toolTrack.setToolStatus(ToolStatus.CHECK_OUT);
			} else if (batch.getBatchType() == BatchType.EXCHANGE) {
				toolTrack.setToolStatus(ToolStatus.CHECK_OUT);
			} else if (batch.getBatchType() == BatchType.BACK) {
				toolTrack.setToolStatus(ToolStatus.CHECK_IN);
			} else if (batch.getBatchType() == BatchType.REJECT) {
				toolTrack.setToolStatus(ToolStatus.REJECT);
			} else if (batch.getBatchType() == BatchType.BORROW) {
				toolTrack.setToolStatus(ToolStatus.BORROW);
			} else if (batch.getBatchType() == BatchType.RETURN) {
				toolTrack.setToolStatus(ToolStatus.CHECK_IN);
			}
			toolTrack.setBatchConfirmTime(new Date());
			toolTrack.setBatchConfirmUserId(
					batch.getBatchConfirmUserId());
			toolTrackService.updateToolTrack(toolTrack);
			Tool tool = new Tool();
			tool.setBatchId(BaseUtil.strToLong(batchId));
			if (batch.getBatchType() == BatchType.CHECK_IN) {
				tool.setToolStatus(ToolStatus.CHECK_IN);
			} else if (batch.getBatchType() == BatchType.CHECK_OUT) {
				tool.setToolStatus(ToolStatus.CHECK_OUT);
			} else if (batch.getBatchType() == BatchType.EXCHANGE) {
				tool.setToolStatus(ToolStatus.CHECK_OUT);
			} else if (batch.getBatchType() == BatchType.BACK) {
				tool.setToolStatus(ToolStatus.CHECK_IN);
			} else if (batch.getBatchType() == BatchType.REJECT) {
				tool.setToolStatus(ToolStatus.REJECT);
			} else if (batch.getBatchType() == BatchType.BORROW) {
				tool.setToolStatus(ToolStatus.BORROW);
			} else if (batch.getBatchType() == BatchType.RETURN) {
				tool.setToolStatus(ToolStatus.CHECK_IN);
			}
			toolService.updateToolByBatch(tool);
			Batch paramBatch = new Batch();
			paramBatch.setBatchId(BaseUtil.strToLong(batchId));
			paramBatch.setBatchConfirmTime(new Date());
			paramBatch.setBatchConfirmUserId(
					batch.getBatchConfirmUserId());
			bool = batchMapper.updateByPrimaryKeySelective(paramBatch);
		}
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public synchronized Map<String, Object> addNewBatchsAndDetails(
			Batch batch, Tool tool, ToolTrack toolTrack) {
		int bool = 0;
		Batch temp = batchMapper.selectBatchsForObject(batch);
		if (temp == null) {
			batch.setBatchCount(0L);
			bool = batchMapper.insertSelective(batch);
		} else {
			batch = temp;
		}
		Map<String, Object> resultMap = null;
		boolean success = false;
		String msg = "程序发生错误，请联系系统管理员";
		long batchType = batch.getBatchType();
		tool.setBatchId(batch.getBatchId());
		if (batchType == BatchType.CHECK_IN) {
			tool.setToolId(-1L);
			tool.setToolStatus(ToolStatus.CHECK_IN_COMING);
			resultMap = toolService.checkInTool(batch, tool, toolTrack);

		} else if (batchType == BatchType.CHECK_OUT) {
			tool.setToolStatus(ToolStatus.CHECK_OUT_COMING);
			resultMap = toolService.checkOutTool(batch, tool,
					toolTrack);
		} else if (batchType == BatchType.EXCHANGE) {
			tool.setToolStatus(ToolStatus.CHECK_OUT_COMING);
			resultMap = toolService.exchangeTool(batch, tool,
					toolTrack);
		} else if (batchType == BatchType.BACK) {
			tool.setToolStatus(ToolStatus.CHECK_IN_COMING);
			resultMap = toolService.backTool(batch, tool, toolTrack);
		} else if (batchType == BatchType.REJECT) {
			tool.setToolStatus(ToolStatus.REJECT);
			resultMap = toolService.rejectTool(batch, tool, toolTrack);
		} else if (batchType == BatchType.BORROW) {
			tool.setToolStatus(ToolStatus.BORROW_COMING);
			resultMap = toolService.borrowTool(batch, tool, toolTrack);
		} else if (batchType == BatchType.RETURN) {
			tool.setToolStatus(ToolStatus.CHECK_IN_COMING);
			resultMap = toolService.checkInTool(batch, tool, toolTrack);
		}
		if (resultMap != null) {
			success = (boolean) resultMap.get("success");
			msg = resultMap.get("msg").toString();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (success) {
			// 批次更新数量
			batch.setBatchCount(batch.getBatchCount() + 1);
			bool = batchMapper.updateByPrimaryKeySelective(batch);

			if (bool == 0) {
				map.put("success", false);
				map.put("msg", "保存出错，请联系管理员");
			} else {
				map.put("success", true);
				map.put("msg", "保存成功");
				map.put("count", batch.getBatchCount());
			}
		} else {
			map.put("success", false);
			map.put("msg", msg);
		}

		return map;
	}

	@Override
	public Map<String, Object> selectBatchsForPage(Batch batch) {
		List<Map<String, Object>> batchs = batchMapper
				.selectBatchsForPage(batch);
		for (Map<String, Object> item : batchs) {
			if (item.get("BATCH_CREATE_TIME") != null) {
				item.put("BATCH_CREATE_TIME", DateUtil.getDate(
						item.get("BATCH_CREATE_TIME").toString()));
			}
			if (item.get("BATCH_CONFIRM_TIME") != null) {
				item.put("BATCH_CONFIRM_TIME", DateUtil.getDate(
						item.get("BATCH_CONFIRM_TIME").toString()));
			}
			if (item.get("BATCH_TAKE_TIME") != null) {
				item.put("BATCH_TAKE_TIME", DateUtil.getDate(
						item.get("BATCH_TAKE_TIME").toString()));
			}
		}
		int count = batchMapper.selectCountOfBatchsForPage(batch);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", batchs);
		map.put("total", count);
		return map;
	}

	@Override
	public Batch selectBatchsForObject(Batch batch) {
		return batchMapper.selectBatchsForObject(batch);
	}

	@Override
	public Map<String, Object> updateBatch(Batch batch) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = batchMapper.updateByPrimaryKeySelective(batch);
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
	public Map<String, Object> updateBatchs(Batch batch) {
		String batchIds = batch.getIds();
		String[] batchId_arr = batchIds.split(",");
		int bool = 1;
		for (String batchId : batchId_arr) {
			Batch param = new Batch();
			param.setBatchId(BaseUtil.strToLong(batchId));
			param.setBatchTakeTime(new Date());
			param.setBatchTakeUserId(batch.getBatchTakeUserId());
			bool = batchMapper.updateByPrimaryKeySelective(param);
		}
		Map<String, Object> map = new HashMap<String, Object>();

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
	public Map<String, Object> delToolAndTrack(Tool tool,
			ToolTrack toolTrack, Batch batch) {
		Map<String, Object> map = toolService.resetTool(tool,
				toolTrack);
		batch = batchMapper.selectBatchsForObject(batch);
		batch.setBatchCount(batch.getBatchCount() - 1);
		int bool = batchMapper.updateByPrimaryKeySelective(batch);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
			map.put("batchId", batch.getBatchId());
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
