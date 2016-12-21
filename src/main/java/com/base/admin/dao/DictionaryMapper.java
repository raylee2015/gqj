package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Dictionary;

public interface DictionaryMapper {
	int deleteByPrimaryKeys(Dictionary dictionary);

	int insertSelective(Dictionary dictionary);

	int updateByPrimaryKeySelective(Dictionary record);

	List<Dictionary> queryDictionarysForList(Dictionary dictionary);

	List<Map<String, Object>> queryDictionarysForCache();

	List<Map<String, Object>> queryDictionarysForPage(
			Dictionary dictionary);

	int queryCountOfDictionarysForPage(Dictionary dictionary);

}