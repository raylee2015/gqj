package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.BatchMapper;
import com.gqj.entity.Batch;
import com.gqj.entity.Tool;
import com.gqj.entity.ToolTrack;
import com.gqj.service.IBatchService;
import com.gqj.service.IToolService;
import com.gqj.util.BatchType;
import com.gqj.util.ToolStatus;

@Service
public class BatchServiceImpl implements IBatchService {

	@Autowired
	private BatchMapper batchMapper;

	@Autowired
	private IToolService toolService;

	@Override
	public Map<String, Object> deleteBatchs(Batch batch) {
		Map<String, Object> map = new HashMap<>();
		int bool = batchMapper.deleteByPrimaryKeys(batch);
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
		long batchType = batch.getBatchType();
		tool.setBatchId(batch.getBatchId());
		if (batchType == BatchType.CHECK_IN) {
			tool.setToolId(-1L);
			tool.setToolStatus(ToolStatus.CHECK_IN_COMING);
			resultMap = toolService.checkInNewTool(batch, tool,
					toolTrack);
		}
		boolean success = (boolean) resultMap.get("success");
		String msg = resultMap.get("msg").toString();
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
		int count = batchMapper.selectCountOfBatchsForPage(batch);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", batchs);
		map.put("total", count);
		return map;
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

}