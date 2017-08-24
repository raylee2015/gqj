package com.bpbj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.BaseUtil;
import com.base.util.DateUtil;
import com.bpbj.dao.BPBJBatchMapper;
import com.bpbj.entity.Batch;
import com.bpbj.entity.PlugIn;
import com.bpbj.entity.PlugInTrack;
import com.bpbj.service.IBPBJBatchService;
import com.bpbj.service.IBPBJPlugInService;
import com.bpbj.service.IBPBJPlugInTrackService;
import com.bpbj.util.BatchType;
import com.bpbj.util.PlugInStatus;

@Service
public class BPBJBatchServiceImpl implements IBPBJBatchService {

	@Autowired
	private BPBJBatchMapper batchMapper;

	@Autowired
	private IBPBJPlugInService plugInService;

	@Autowired
	private IBPBJPlugInTrackService plugInTrackService;

	@Override
	public Map<String, Object> deleteBatchs(Batch batch) {
		Map<String, Object> map = new HashMap<String, Object>();
		String batchIds = batch.getIds();
		String[] batchId_arr = batchIds.split(",");
		int bool = 1;
		for (String batchId : batchId_arr) {
			PlugIn plugIn = new PlugIn();
			plugIn.setBatchId(BaseUtil.strToLong(batchId));
			// 查询plugIn
			List<PlugIn> plugIns = plugInService.selectPlugInsForList(plugIn);
			for (PlugIn item : plugIns) {
				// 根据plugIn和相应的track来reset
				plugIn = item;
				PlugInTrack plugInTrack = new PlugInTrack();
				plugInTrack.setBatchId(BaseUtil.strToLong(batchId));
				plugInTrack.setPlugInId(item.getPlugInId());
				// 根据plugInID找出trackId
				plugInTrack.setTrackId(plugInTrackService.selectPlugInTracksForObject(plugInTrack).getTrackId());
				plugInTrack.setBatchId(null);
				plugInService.resetPlugIn(plugIn, plugInTrack);
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
		Map<String, Object> map = new HashMap<String, Object>();
		String batchIds = batch.getIds();
		String[] batchId_arr = batchIds.split(",");
		int bool = 1;
		for (String batchId : batchId_arr) {
			PlugInTrack plugInTrack = new PlugInTrack();
			plugInTrack.setBatchId(BaseUtil.strToLong(batchId));
			if (batch.getBatchType() == BatchType.CHECK_IN) {
				plugInTrack.setPlugInStatus(PlugInStatus.CHECK_IN);
			} else if (batch.getBatchType() == BatchType.CHECK_OUT) {
				plugInTrack.setPlugInStatus(PlugInStatus.CHECK_OUT);
			} else if (batch.getBatchType() == BatchType.EXCHANGE) {
				plugInTrack.setPlugInStatus(PlugInStatus.CHECK_OUT);
			} else if (batch.getBatchType() == BatchType.BACK) {
				plugInTrack.setPlugInStatus(PlugInStatus.CHECK_IN);
			} else if (batch.getBatchType() == BatchType.REJECT) {
				plugInTrack.setPlugInStatus(PlugInStatus.REJECT);
			} else if (batch.getBatchType() == BatchType.BORROW) {
				plugInTrack.setPlugInStatus(PlugInStatus.BORROW);
			} else if (batch.getBatchType() == BatchType.RETURN) {
				plugInTrack.setPlugInStatus(PlugInStatus.CHECK_IN);
			}
			plugInTrackService.updatePlugInTrack(plugInTrack);
			PlugIn plugIn = new PlugIn();
			plugIn.setBatchId(BaseUtil.strToLong(batchId));
			if (batch.getBatchType() == BatchType.CHECK_IN) {
				plugIn.setPlugInStatus(PlugInStatus.CHECK_IN);
			} else if (batch.getBatchType() == BatchType.CHECK_OUT) {
				plugIn.setPlugInStatus(PlugInStatus.CHECK_OUT);
			} else if (batch.getBatchType() == BatchType.EXCHANGE) {
				plugIn.setPlugInStatus(PlugInStatus.CHECK_OUT);
			} else if (batch.getBatchType() == BatchType.BACK) {
				plugIn.setPlugInStatus(PlugInStatus.CHECK_IN);
			} else if (batch.getBatchType() == BatchType.REJECT) {
				plugIn.setPlugInStatus(PlugInStatus.REJECT);
			} else if (batch.getBatchType() == BatchType.BORROW) {
				plugIn.setPlugInStatus(PlugInStatus.BORROW);
			} else if (batch.getBatchType() == BatchType.RETURN) {
				plugIn.setPlugInStatus(PlugInStatus.CHECK_IN);
			}
			plugInService.updatePlugInByBatch(plugIn);
			Batch paramBatch = new Batch();
			paramBatch.setBatchId(BaseUtil.strToLong(batchId));
			paramBatch.setBatchConfirmTime(new Date());
			paramBatch.setBatchConfirmUserId(batch.getBatchConfirmUserId());
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
	public synchronized Map<String, Object> addNewBatchsAndDetails(Batch batch, PlugIn plugIn,
			PlugInTrack plugInTrack) {
		Batch temp = batchMapper.selectBatchsForObject(batch);
		if (temp == null) {
			batchMapper.insertSelective(batch);
		} else {
			batch = temp;
		}
		Map<String, Object> resultMap = null;
		boolean success = false;
		String msg = "程序发生错误，请联系系统管理员";
		long batchType = batch.getBatchType();
		plugIn.setBatchId(batch.getBatchId());
		if (batchType == BatchType.CHECK_IN) {
			plugIn.setPlugInId(-1L);
			plugIn.setPlugInStatus(PlugInStatus.CHECK_IN_COMING);
			resultMap = plugInService.checkInPlugIn(batch, plugIn, plugInTrack);
		} else if (batchType == BatchType.CHECK_OUT) {
			plugIn.setPlugInStatus(PlugInStatus.CHECK_OUT_COMING);
			resultMap = plugInService.checkOutPlugIn(batch, plugIn, plugInTrack);
		} else if (batchType == BatchType.BACK) {
			plugIn.setPlugInStatus(PlugInStatus.CHECK_IN_COMING);
			resultMap = plugInService.backPlugIn(batch, plugIn, plugInTrack);
		} else if (batchType == BatchType.REJECT) {
			plugIn.setPlugInStatus(PlugInStatus.REJECT_COMING);
			resultMap = plugInService.rejectPlugIn(batch, plugIn, plugInTrack);
		}
		if (resultMap != null) {
			success = Boolean.parseBoolean(resultMap.get("success").toString());
			msg = resultMap.get("msg").toString();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (success) {
			map.put("success", true);
			map.put("msg", "保存成功，请联系管理员");
		} else {
			map.put("success", false);
			map.put("msg", msg);
		}
		return map;
	}

	@Override
	public Map<String, Object> selectBatchsForPage(Batch batch) {
		List<Map<String, Object>> batchs = batchMapper.selectBatchsForPage(batch);
		for (Map<String, Object> item : batchs) {
			if (item.get("BATCH_CREATE_TIME") != null) {
				item.put("BATCH_CREATE_TIME", DateUtil.getDate(item.get("BATCH_CREATE_TIME").toString()));
			}
			if (item.get("BATCH_CONFIRM_TIME") != null) {
				item.put("BATCH_CONFIRM_TIME", DateUtil.getDate(item.get("BATCH_CONFIRM_TIME").toString()));
			}
			if (item.get("BATCH_TAKE_TIME") != null) {
				item.put("BATCH_TAKE_TIME", DateUtil.getDate(item.get("BATCH_TAKE_TIME").toString()));
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
	public Map<String, Object> delPlugInAndTrack(PlugIn plugIn, PlugInTrack plugInTrack, Batch batch) {
		Map<String, Object> map = plugInService.resetPlugIn(plugIn, plugInTrack);
		batch = batchMapper.selectBatchsForObject(batch);
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
