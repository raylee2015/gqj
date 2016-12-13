package com.base.admin.service;

import java.util.Map;

import com.base.admin.entity.Param;

public interface IParamService {
	int deleteByPrimaryKeys(Param param);

	Map<String, Object> insertSelective(Param param);

	int updateByPrimaryKeySelective(Param param);

	Map<String, Object>  selectParamsForPage(Param param);

}
