package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.Material;

public interface MaterialMapper {
	List<Map<String, Object>> selectMaterialsForPage(
			Material material);

	int selectCountOfMaterialsForPage(Material material);

	int deleteByPrimaryKeys(Material material);

	int insertSelective(Material material);

	int updateByPrimaryKeySelective(Material material);
}