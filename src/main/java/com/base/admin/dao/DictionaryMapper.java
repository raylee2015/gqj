package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Dictionary;

public interface DictionaryMapper {
	int deleteByPrimaryKeys(Dictionary dictionary);

	int insertSelective(Dictionary dictionary);

	int updateByPrimaryKeySelective(Dictionary record);

	List<Dictionary> selectDictionarysForList(Dictionary dictionary);

	List<Map<String, Object>> selectDictionarysForCache();

	List<Map<String, Object>> selectDictionarysForPage(
			Dictionary dictionary);

	int selectCountOfDictionarysForPage(Dictionary dictionary);

}