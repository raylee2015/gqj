package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.MaterialType;

public interface MaterialTypeMapper {
	List<Map<String, Object>> selectMaterialTypesForPage(
			MaterialType materialType);

	List<Map<String, Object>> selectMaterialTypesForList(
			MaterialType materialType);

	int selectCountOfMaterialTypesForPage(MaterialType materialType);

	int deleteByPrimaryKeys(MaterialType materialType);

	int insertSelective(MaterialType materialType);

	int updateByPrimaryKeySelective(MaterialType materialType);

}