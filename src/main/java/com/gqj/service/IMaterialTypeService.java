package com.gqj.service;

import java.util.Map;

import com.gqj.entity.MaterialType;

public interface IMaterialTypeService {
	int deleteByPrimaryKeys(MaterialType materialType);

	int insertSelective(MaterialType materialType);

	int updateByPrimaryKeySelective(MaterialType materialType);

	Map<String, Object> selectMaterialTypesForPage(
			MaterialType materialType);

	String selectMaterialTypesForList(MaterialType materialType);
}
