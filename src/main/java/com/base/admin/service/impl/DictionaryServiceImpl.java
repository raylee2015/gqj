package com.base.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.DictionaryMapper;
import com.base.admin.entity.Dictionary;
import com.base.admin.service.IDictionaryService;

@Service
public class DictionaryServiceImpl implements IDictionaryService {

	@Autowired
	private DictionaryMapper dictionaryMapper;

	/*
	 * (非 Javadoc) <p>Title: deleteByPrimaryKeys</p> <p>Description: </p>
	 * 
	 * @param dictionaryIds
	 * 
	 * @return
	 * 
	 * @see
	 * com.base.admin.service.IDictionaryService#deleteByPrimaryKeys(java.lang.
	 * String)
	 */
	@Override
	public int deleteByPrimaryKeys(Dictionary dictionary) {
		return dictionaryMapper.deleteByPrimaryKeys(dictionary);
	}

	@Override
	public int insertSelective(Dictionary dictionary) {
		return dictionaryMapper.insertSelective(dictionary);
	}

	@Override
	public int selectCountOfDictionarysForPage(Dictionary dictionary) {
		return dictionaryMapper
				.selectCountOfDictionarysForPage(dictionary);
	}

	@Override
	public List<Dictionary> selectDictionarysForList(
			Dictionary dictionary) {
		return dictionaryMapper.selectDictionarysForList(dictionary);
	}

	@Override
	public List<Map<String, Object>> selectDictionarysForPage(
			Dictionary dictionary) {
		return dictionaryMapper.selectDictionarysForPage(dictionary);
	}

	@Override
	public int updateByPrimaryKeySelective(Dictionary dictionary) {
		return dictionaryMapper.updateByPrimaryKeySelective(dictionary);
	}

}
