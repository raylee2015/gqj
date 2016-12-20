package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Param;

public interface ParamMapper {
	List<Map<String, Object>> queryParamsForPage(Param param);

	List<Map<String, Object>> queryParamsForList(Param param);

	int queryCountOfParamsForPage(Param param);

	int deleteByPrimaryKeys(Param param);

	int insertSelective(Param param);

	int updateByPrimaryKeySelective(Param param);
}