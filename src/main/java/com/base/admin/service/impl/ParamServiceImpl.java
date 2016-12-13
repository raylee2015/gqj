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
	public int deleteByPrimaryKeys(Param param) {
		return paramMapper.deleteByPrimaryKeys(param);
	}

	@Override
	public Map<String, Object> insertSelective(Param param) {
		Map<String, Object> result = new HashMap<>();
		if (paramMapper.selectParamsForList(param).size() > 0) {
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
	public Map<String, Object> selectParamsForPage(Param param) {
		List<Map<String, Object>> params = paramMapper
				.selectParamsForPage(param);
		int count = paramMapper.selectCountOfParamsForPage(param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", params);
		map.put("total", count);
		return map;
	}

	@Override
	public int updateByPrimaryKeySelective(Param param) {
		return paramMapper.updateByPrimaryKeySelective(param);
	}

}
