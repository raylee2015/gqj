package com.base.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.ParamMapper;
import com.base.admin.entity.Param;
import com.base.admin.service.IParamService;

@Service
public class ParamServiceImpl implements IParamService {

	@Autowired
	private ParamMapper paramMapper;

	@Override
	public Map<String, Object> deleteParams(Param param) {
		Map<String, Object> map = new HashMap<>();
		int bool = paramMapper.deleteByPrimaryKeys(param);
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
	public Map<String, Object> addNewParam(Param param) {
		Map<String, Object> result = new HashMap<>();
		if (paramMapper.queryParamsForList(param).size() > 0) {
			result.put("success", false);
			result.put("msg", "系统已经存在同样的参数键与参数值");
		} else {
			int resultOfInsert = paramMapper.insertSelective(param);
			if (resultOfInsert == 0) {
				result.put("success", false);
				result.put("msg", "保存失败，请联系系统管理员");
			} else {
				result.put("success", true);
				result.put("msg", "保存成功");
			}

		}
		return result;
	}

	@Override
	public Map<String, Object> queryParamsForPage(Param param) {
		List<Map<String, Object>> params = paramMapper
				.queryParamsForPage(param);
		int count = paramMapper.queryCountOfParamsForPage(param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", params);
		map.put("total", count);
		return map;
	}

	@Override
	public String queryParamsForMap(String paramKey) {
		List<Param> params = paramMapper.queryParamsForList(null);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Param item : params) {
			map.put(item.getParamKey(), item.getParamValue());
		}
		if (map.containsKey(paramKey)) {
			return map.get(paramKey).toString();
		} else {
			return "";
		}
	}

	@Override
	public Map<String, Object> updateParam(Param param) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = paramMapper.updateByPrimaryKeySelective(param);
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
