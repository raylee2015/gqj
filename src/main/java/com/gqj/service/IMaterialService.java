package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Material;

public interface IMaterialService {
	int deleteByPrimaryKeys(Material material);

	int insertSelective(Material material);

	int updateByPrimaryKeySelective(Material material);

	Map<String, Object> selectMaterialsForPage(
			Material material);
}
