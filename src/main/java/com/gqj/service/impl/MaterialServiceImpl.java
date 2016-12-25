package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.MaterialMapper;
import com.gqj.entity.Material;
import com.gqj.service.IMaterialService;

@Service
public class MaterialServiceImpl
		implements IMaterialService {

	@Autowired
	private MaterialMapper materialMapper;

	@Override
	public int deleteByPrimaryKeys(Material material) {
		return materialMapper.deleteByPrimaryKeys(material);
	}

	@Override
	public int insertSelective(Material material) {
		return materialMapper.insertSelective(material);
	}

	@Override
	public Map<String, Object> selectMaterialsForPage(
			Material material) {
		List<Map<String, Object>> materials = materialMapper
				.selectMaterialsForPage(material);
		int count = materialMapper
				.selectCountOfMaterialsForPage(material);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", materials);
		map.put("total", count);
		return map;
	}

	@Override
	public int updateByPrimaryKeySelective(
			Material material) {
		return materialMapper
				.updateByPrimaryKeySelective(material);
	}

}
