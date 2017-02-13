package com.base.admin.service;

import java.util.Map;

import com.base.admin.entity.Param;

public interface IParamService {
	Map<String, Object> deleteParams(Param param);

	Map<String, Object> addNewParam(Param param);

	Map<String, Object> updateParam(Param param);

	Map<String, Object> queryParamsForPage(Param param);

	String queryParamsForMap(String paramKey);

}
