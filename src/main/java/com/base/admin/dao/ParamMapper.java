package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Param;

public interface ParamMapper {
	List<Map<String, Object>> selectParamsForPage(Param param);

	List<Map<String, Object>> selectParamsForList(Param param);

	int selectCountOfParamsForPage(Param param);

	int deleteByPrimaryKeys(Param param);

	int insertSelective(Param param);

	int updateByPrimaryKeySelective(Param param);
}