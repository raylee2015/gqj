package com.bpbj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.service.IDictionaryService;
import com.base.util.DateStyle;
import com.base.util.DateUtil;
import com.bpbj.dao.BPBJPlugInMapper;
import com.bpbj.dao.BPBJPlugInTrackMapper;
import com.bpbj.entity.Batch;
import com.bpbj.entity.PlugIn;
import com.bpbj.entity.PlugInTrack;
import com.bpbj.service.IBPBJPlugInService;
import com.bpbj.util.PlugInStatus;

@Service
public class BPBJPlugInServiceImpl implements IBPBJPlugInService {

	@Autowired
	private IDictionaryService distionaryService;

	@Autowired
	private BPBJPlugInMapper plugInMapper;

	@Autowired
	private BPBJPlugInTrackMapper plugInTrackMapper;

	@Override
	public Map<String, Object> addNewPlugIn(PlugIn plugIn) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = plugInMapper.insertSelective(plugIn);
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
	public Map<String, Object> checkInPlugIn(Batch batch, PlugIn plugIn, PlugInTrack plugInTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		PlugIn temp = plugInMapper.selectPlugInForObject(plugIn);
		int bool = 0;
		String msg = "";
		if (temp == null) {
			bool = plugInMapper.insertSelective(plugIn);
			plugInTrack.setTrackId(-1L);
			plugInTrack.setPlugInId(plugIn.getPlugInId());
			plugInTrack.setBatchId(plugIn.getBatchId());
			plugInTrack.setPlugInStatus(plugIn.getPlugInStatus());
			bool = plugInTrackMapper.insertSelective(plugInTrack);
			if (bool == 0) {
				map.put("success", false);
				map.put("msg", "保存出错，请联系管理员");
			} else {
				map.put("success", true);
				map.put("msg", "保存成功");
			}
		} else {
			long plugInStatus = temp.getPlugInStatus();
			if (plugInStatus == PlugInStatus.REJECT) {
				msg = "该插件已经报废";
				map.put("success", false);
				map.put("msg", msg);
			} else if (plugInStatus == PlugInStatus.CHECK_OUT) {
				temp.setPosId(plugIn.getPosId());
				temp.setStoreId(plugIn.getStoreId());
				temp.setPlugInStatus(plugIn.getPlugInStatus());
				temp.setPlugInRemark(plugIn.getPlugInRemark());
				temp.setBatchId(batch.getBatchId());
				temp.setPlugInDeptId(plugIn.getPlugInDeptId());
				// 更新plugIn状态，新增track记录
				bool = plugInMapper.updateByPrimaryKeySelective(temp);
				plugInTrack.setPlugInId(temp.getPlugInId());
				plugInTrack.setBatchId(batch.getBatchId());
				plugInTrack.setTrackId(-1L);
				plugInTrack.setPosId(temp.getPosId());
				plugInTrack.setStoreId(temp.getStoreId());
				plugInTrack.setPlugInStatus(temp.getPlugInStatus());
				bool = plugInTrackMapper.insertSelective(plugInTrack);
				if (bool == 0) {
					map.put("success", false);
					map.put("msg", "保存出错，请联系管理员");
				} else {
					map.put("success", true);
					map.put("msg", "保存成功");
				}
			} else if (plugInStatus == PlugInStatus.CHECK_IN_COMING || plugInStatus == PlugInStatus.CHECK_IN) {
				msg = "该插件已经入库";
				map.put("success", false);
				map.put("msg", msg);
			} else {
				msg = "该插件处于非正常状态，请查证";
				map.put("success", false);
				map.put("msg", msg);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> exchangePlugIn(Batch batch, PlugIn plugInParam, PlugInTrack plugInTrack) {
		return checkOutPlugIn(batch, plugInParam, plugInTrack);
	}

	@Override
	public Map<String, Object> usePlugIn(Batch batch, PlugIn plugInParam, PlugInTrack plugInTrack) {
		return checkOutPlugIn(batch, plugInParam, plugInTrack);
	}

	@Override
	public Map<String, Object> backPlugIn(Batch batch, PlugIn plugInParam, PlugInTrack plugInTrack) {
		return checkInPlugIn(batch, plugInParam, plugInTrack);
	}

	@Override
	public Map<String, Object> selfRetrunPlugIn(Batch batch, PlugIn plugInParam, PlugInTrack plugInTrack) {
		return checkInPlugIn(batch, plugInParam, plugInTrack);
	}

	@Override
	public Map<String, Object> rejectPlugIn(Batch batch, PlugIn plugInParam, PlugInTrack plugInTrack) {
		return checkOutPlugIn(batch, plugInParam, plugInTrack);
	}

	@Override
	public Map<String, Object> borrowPlugIn(Batch batch, PlugIn plugInParam, PlugInTrack plugInTrack) {
		return checkOutPlugIn(batch, plugInParam, plugInTrack);
	}

	@Override
	public Map<String, Object> checkOutPlugIn(Batch batch, PlugIn plugInParam, PlugInTrack plugInTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		PlugIn plugInFromSearch = plugInMapper.selectPlugInForObject(plugInParam);
		int bool = 0;
		String msg = "";
		if (plugInFromSearch == null) {
			map.put("success", false);
			map.put("msg", "查询出错，没有该插件");
		} else {
			long plugInStatus = plugInFromSearch.getPlugInStatus();
			if (plugInStatus == PlugInStatus.REJECT) {
				msg = "该插件已经报废";
				map.put("success", false);
				map.put("msg", msg);
			} else if (plugInStatus == PlugInStatus.CHECK_IN) {
				plugInFromSearch.setPosId(plugInParam.getPosId());
				plugInFromSearch.setStoreId(plugInParam.getStoreId());
				plugInFromSearch.setPlugInRemark(plugInParam.getPlugInRemark());
				plugInFromSearch.setBatchId(batch.getBatchId());
				// 更新plugIn状态，新增track记录
				bool = plugInMapper.updateByPrimaryKeySelective(plugInFromSearch);
				plugInTrack.setPlugInId(plugInFromSearch.getPlugInId());
				plugInTrack.setBatchId(batch.getBatchId());
				plugInTrack.setTrackId(-1L);
				bool = plugInTrackMapper.insertSelective(plugInTrack);
				if (bool == 0) {
					map.put("success", false);
					map.put("msg", "保存出错，请联系管理员");
				} else {
					map.put("success", true);
					map.put("msg", "保存成功");
				}
			} else if (plugInStatus == PlugInStatus.CHECK_OUT_COMING || plugInStatus == PlugInStatus.CHECK_OUT) {
				msg = "该插件已经出库";
				map.put("success", false);
				map.put("msg", msg);
			} else {
				msg = "该插件处于非正常状态，请查证";
				map.put("success", false);
				map.put("msg", msg);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> deletePlugIns(PlugIn plugIn) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = plugInMapper.deleteByPrimaryKeys(plugIn);
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
	public PlugIn selectPlugInForObject(PlugIn plugIn) {
		return plugInMapper.selectPlugInForObject(plugIn);
	}

	@Override
	public Map<String, Object> resetPlugIn(PlugIn plugIn, PlugInTrack plugInTrack) {
		int bool = 1;
		// 查询track的条数
		List<PlugInTrack> plugInTracks = plugInTrackMapper.selectPlugInTracksForList(plugInTrack);
		if (plugInTracks.size() == 1) {// 1.=1，删掉plugIn与track
			bool = plugInTrackMapper.deleteByPrimaryKeys(plugInTrack);
			bool = plugInMapper.deleteByPrimaryKeys(plugIn);
		} else {// 2.>1，删掉track，然后用plugIntrack的状态替换当前plugIn的状态
			PlugInTrack temp = plugInTracks.get(1);
			plugIn.setPosId(temp.getPosId());
			plugIn.setStoreId(temp.getStoreId());
			plugIn.setBatchId(temp.getBatchId());
			bool = plugInMapper.updateByPrimaryKeySelective(plugIn);
			bool = plugInTrackMapper.deleteByPrimaryKeys(plugInTrack);
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
	public List<PlugIn> selectPlugInsForList(PlugIn plugIn) {
		return plugInMapper.selectPlugInsForList(plugIn);
	}

	@Override
	public Map<String, Object> selectPlugInsForPage(HashMap<String, Object> param) {
		List<Map<String, Object>> plugIns = plugInMapper.selectPlugInsForPage(param);
		for (Map<String, Object> item : plugIns) {
			String plugInStatus = item.get("PLUGIN_STATUS").toString();
			List<Map<String, Object>> dicList = distionaryService.getDictionaryListByDicCode("TOOL_STATUS");
			for (Map<String, Object> dic : dicList) {
				if (dic.get("ID").equals(plugInStatus)) {
					item.put("PLUGIN_STATUS_NAME", dic.get("TEXT").toString());
					break;
				}
			}
			if (item.get("PLUGIN_MAN_DATE") != null) {
				item.put("PLUGIN_MAN_DATE",
						DateUtil.DateToString((Date) item.get("PLUGIN_MAN_DATE"), DateStyle.YYYY_MM_DD));
			}
		}
		int count = plugInMapper.selectCountOfPlugInsForPage(param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", plugIns);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updatePlugIn(PlugIn plugIn) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = plugInMapper.updateByPrimaryKeySelective(plugIn);
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
	public Map<String, Object> updatePlugInByBatch(PlugIn plugIn) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = plugInMapper.updatePlugInByBatch(plugIn);
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
	public Map<String, Object> selectPlugInsForPageByBaseTool(HashMap<String, Object> param) {
		List<Map<String, Object>> plugIns = plugInMapper.selectPlugInsForPageByBaseTool(param);
		int count = plugInMapper.selectCountOfPlugInsForPageByBaseTool(param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", plugIns);
		map.put("total", count);
		return map;
	}

}
