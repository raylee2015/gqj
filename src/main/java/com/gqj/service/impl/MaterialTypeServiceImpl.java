package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.MaterialTypeMapper;
import com.gqj.entity.MaterialType;
import com.gqj.service.IMaterialTypeService;

@Service
public class MaterialTypeServiceImpl
		implements IMaterialTypeService {

	@Autowired
	private MaterialTypeMapper materialTypeMapper;

	@Override
	public int deleteByPrimaryKeys(
			MaterialType materialType) {
		return materialTypeMapper
				.deleteByPrimaryKeys(materialType);
	}

	@Override
	public int insertSelective(MaterialType materialType) {
		return materialTypeMapper
				.insertSelective(materialType);
	}

	@Override
	public Map<String, Object> selectMaterialTypesForPage(
			MaterialType materialType) {
		List<Map<String, Object>> materialTypes = materialTypeMapper
				.selectMaterialTypesForPage(materialType);
		int count = materialTypeMapper
				.selectCountOfMaterialTypesForPage(
						materialType);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", materialTypes);
		map.put("total", count);
		return map;
	}

	@Override
	public int updateByPrimaryKeySelective(
			MaterialType materialType) {
		return materialTypeMapper
				.updateByPrimaryKeySelective(materialType);
	}

}
