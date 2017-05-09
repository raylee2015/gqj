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
import com.bpbj.dao.BPBJPlugInMapper;
import com.bpbj.dao.BPBJPlugInTrackMapper;
import com.bpbj.entity.Batch;
import com.bpbj.entity.PlugIn;
import com.bpbj.entity.PlugInTrack;
import com.bpbj.service.IBPBJBaseToolService;
import com.bpbj.service.IBPBJPlugInService;
import com.bpbj.util.PlugInStatus;

@Service
public class BPBJPlugInServiceImpl implements IBPBJPlugInService {

	@Autowired
	private IBPBJBaseToolService baseToolService;

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
			bool = plugInTrackMapper.insertSelective(plugInTrack);
			if (bool == 0) {
				map.put("success", false);
				map.put("msg", "保存出错，请联系管理员");
			} else {
				map.put("success", true);
				map.put("msg", "保存成功");
			}
		} else {
			long plugInStatus = temp.getToolStatus();
			if (plugInStatus == PlugInStatus.REJECT) {
				msg = "该工器具已经报废";
				map.put("success", false);
				map.put("msg", msg);
			} else if (plugInStatus == PlugInStatus.CHECK_OUT || plugInStatus == PlugInStatus.BORROW) {
				PlugIn plugInFromSearch = plugInMapper.selectPlugInForObject(plugIn);
				plugInFromSearch.setPosId(plugIn.getPosId());
				plugInFromSearch.setStoreId(plugIn.getStoreId());
				plugInFromSearch.setToolStatus(plugIn.getToolStatus());
				plugInFromSearch.setPlugInBox(plugIn.getPlugInBox());
				plugInFromSearch.setPlugInRemark(plugIn.getPlugInRemark());
				plugInFromSearch.setBatchId(batch.getBatchId());
				plugInFromSearch.setPlugInDeptId(plugIn.getPlugInDeptId());
				// 更新plugIn状态，新增track记录
				bool = plugInMapper.updateByPrimaryKeySelective(plugInFromSearch);
				BasePlugIn basePlugInParam = new BasePlugIn();
				basePlugInParam.setBasePlugInId(plugInFromSearch.getBasePlugInId());
				Map<String, Object> basePlugIn = baseToolService.selectBasePlugInForObject(basePlugInParam);
				plugInTrack.setPlugInId(plugInFromSearch.getPlugInId());
				plugInTrack.setToolStatus(plugIn.getToolStatus());
				plugInTrack.setBatchId(batch.getBatchId());
				plugInTrack.setBatchCode(batch.getBatchCode());
				plugInTrack.setTrackId(-1L);
				plugInTrack.setBasePlugInId(BaseUtil.strToLong(basePlugIn.get("BASE_TOOL_ID").toString()));
				plugInTrack.setPlugInTestDate(plugInFromSearch.getPlugInTestDate());
				plugInTrack.setPlugInRejectDate(plugInFromSearch.getPlugInRejectDate());
				plugInTrack.setPlugInTestDateCircle(plugInFromSearch.getPlugInTestDateCircle());
				plugInTrack.setPlugInNextTestDate(plugInFromSearch.getPlugInNextTestDate());
				plugInTrack.setBasePlugInName(basePlugIn.get("BASE_TOOL_NAME").toString());
				plugInTrack.setBasePlugInTypeId(BaseUtil.strToLong(basePlugIn.get("BASE_TOOL_TYPE_ID").toString()));
				plugInTrack.setBasePlugInTypeName(basePlugIn.get("BASE_TOOL_TYPE_NAME").toString());
				plugInTrack.setBasePlugInModel(basePlugIn.get("BASE_TOOL_MODEL").toString());
				plugInTrack.setBasePlugInSpec(basePlugIn.get("BASE_TOOL_SPEC").toString());
				plugInTrack.setBasePlugInManufacturerName(basePlugIn.get("BASE_TOOL_MANUFACTURER_NAME").toString());
				plugInTrack.setPlugInManufactureDate(plugInFromSearch.getPlugInManufactureDate());
				plugInTrack.setPlugInPurchaseDate(plugInFromSearch.getPlugInPurchaseDate());
				bool = plugInTrackMapper.insertSelective(plugInTrack);
				if (bool == 0) {
					map.put("success", false);
					map.put("msg", "保存出错，请联系管理员");
				} else {
					map.put("success", true);
					map.put("msg", "保存成功");
				}
			} else if (plugInStatus == PlugInStatus.CHECK_IN_COMING || plugInStatus == PlugInStatus.CHECK_IN) {
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
			map.put("msg", "查询出错，没有该工器具");
		} else {
			long plugInStatus = plugInFromSearch.getToolStatus();
			if (plugInStatus == PlugInStatus.REJECT) {
				msg = "该工器具已经报废";
				map.put("success", false);
				map.put("msg", msg);
			} else if (plugInStatus == PlugInStatus.CHECK_IN) {
				plugInFromSearch.setPosId(plugInParam.getPosId());
				plugInFromSearch.setStoreId(plugInParam.getStoreId());
				plugInFromSearch.setToolStatus(plugInParam.getToolStatus());
				plugInFromSearch.setPlugInBox(plugInParam.getPlugInBox());
				plugInFromSearch.setPlugInRemark(plugInParam.getPlugInRemark());
				plugInFromSearch.setBatchId(batch.getBatchId());
				// 更新plugIn状态，新增track记录
				bool = plugInMapper.updateByPrimaryKeySelective(plugInFromSearch);
				BasePlugIn basePlugInParam = new BasePlugIn();
				basePlugInParam.setBasePlugInId(plugInFromSearch.getBasePlugInId());
				Map<String, Object> basePlugIn = baseToolService.selectBasePlugInForObject(basePlugInParam);
				plugInTrack.setPlugInId(plugInFromSearch.getPlugInId());
				plugInTrack.setToolStatus(plugInParam.getToolStatus());
				plugInTrack.setBatchId(batch.getBatchId());
				plugInTrack.setBatchCode(batch.getBatchCode());
				plugInTrack.setTrackId(-1L);
				plugInTrack.setBasePlugInId(BaseUtil.strToLong(basePlugIn.get("BASE_TOOL_ID").toString()));
				plugInTrack.setPlugInTestDate(plugInFromSearch.getPlugInTestDate());
				plugInTrack.setPlugInRejectDate(plugInFromSearch.getPlugInRejectDate());
				plugInTrack.setPlugInTestDateCircle(plugInFromSearch.getPlugInTestDateCircle());
				plugInTrack.setPlugInNextTestDate(plugInFromSearch.getPlugInNextTestDate());
				plugInTrack.setBasePlugInName(basePlugIn.get("BASE_TOOL_NAME").toString());
				plugInTrack.setBasePlugInTypeId(BaseUtil.strToLong(basePlugIn.get("BASE_TOOL_TYPE_ID").toString()));
				plugInTrack.setBasePlugInTypeName(basePlugIn.get("BASE_TOOL_TYPE_NAME").toString());
				plugInTrack.setBasePlugInModel(basePlugIn.get("BASE_TOOL_MODEL").toString());
				plugInTrack.setBasePlugInSpec(basePlugIn.get("BASE_TOOL_SPEC").toString());
				plugInTrack.setBasePlugInManufacturerName(basePlugIn.get("BASE_TOOL_MANUFACTURER_NAME").toString());
				plugInTrack.setPlugInManufactureDate(plugInFromSearch.getPlugInManufactureDate());
				plugInTrack.setPlugInPurchaseDate(plugInFromSearch.getPlugInPurchaseDate());
				bool = plugInTrackMapper.insertSelective(plugInTrack);
				if (bool == 0) {
					map.put("success", false);
					map.put("msg", "保存出错，请联系管理员");
				} else {
					map.put("success", true);
					map.put("msg", "保存成功");
				}
			} else if (plugInStatus == PlugInStatus.CHECK_OUT_COMING || plugInStatus == PlugInStatus.CHECK_OUT) {
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
			plugIn.setPlugInDeptId(temp.getPlugInDeptId());
			plugIn.setToolStatus(temp.getToolStatus());
			plugIn.setPlugInBox(temp.getPlugInBox());
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

	@Autowired
	private IParamService paramService;

	@Override
	public Map<String, Object> selectPlugInsForPage(HashMap<String, Object> param) {
		List<Map<String, Object>> plugIns = plugInMapper.selectPlugInsForPage(param);
		for (Map<String, Object> item : plugIns) {
			String plugInStatus = item.get("TOOL_STATUS").toString();
			List<Map<String, Object>> dicList = distionaryService.getDictionaryListByDicCode("TOOL_STATUS");
			for (Map<String, Object> dic : dicList) {
				if (dic.get("ID").equals(plugInStatus)) {
					item.put("TOOL_STATUS_NAME", dic.get("TEXT").toString());
					break;
				}
			}
			if (item.get("TOOL_REJECT_DATE") != null) {
				item.put("TOOL_REJECT_DATE", DateUtil.getDate(item.get("TOOL_REJECT_DATE").toString()));
				Date plugInRejectDate = DateUtil
						.StringToDate(DateUtil.getDate(item.get("TOOL_REJECT_DATE").toString()));
				Date now = new Date();
				if (now.after(plugInRejectDate)) {
					item.put("NEED_REJECT", 1);
				}
			}
			if (item.get("TOOL_NEXT_TEST_DATE") != null) {
				item.put("TOOL_NEXT_TEST_DATE", DateUtil.getDate(item.get("TOOL_NEXT_TEST_DATE").toString()));
				// 计算超期的日期
				int days = BaseUtil.strToInt(paramService.queryParamsForMap("BEFORE_TEST_DAYS"));
				Date now = new Date();
				Date sysDate = DateUtil.addDay(new Date(), days);
				Date plugInNextTestDate = DateUtil
						.StringToDate(DateUtil.getDate(item.get("TOOL_NEXT_TEST_DATE").toString()));
				if (sysDate.after(plugInNextTestDate)) {
					item.put("NEED_TEST", 1);
				}
				if (now.after(plugInNextTestDate)) {
					item.put("NEED_TEST", 2);
				}
			}
			if (item.get("TOOL_MANUFACTURE_DATE") != null) {
				item.put("TOOL_MANUFACTURE_DATE",
						DateUtil.DateToString((Date) item.get("TOOL_MANUFACTURE_DATE"), DateStyle.YYYY_MM_DD));
			}
			if (item.get("TOOL_PURCHASE_DATE") != null) {
				item.put("TOOL_PURCHASE_DATE",
						DateUtil.DateToString((Date) item.get("TOOL_PURCHASE_DATE"), DateStyle.YYYY_MM_DD));
			}
			if (item.get("TOOL_TEST_DATE") != null) {
				item.put("TOOL_TEST_DATE",
						DateUtil.DateToString((Date) item.get("TOOL_TEST_DATE"), DateStyle.YYYY_MM_DD));
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

}
