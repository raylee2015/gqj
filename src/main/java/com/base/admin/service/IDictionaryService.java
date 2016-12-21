package com.base.admin.service;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Dictionary;

public interface IDictionaryService {
	int deleteByPrimaryKeys(Dictionary dictionary);

	Map<String, Object> insertSelective(Dictionary dictionary);

	int updateByPrimaryKeySelective(Dictionary dictionary);

	List<Dictionary> queryDictionarysForList(Dictionary dictionary);

	Map<String, List<Map<String, Object>>> queryDictionarysForCache();

	Map<String, Object> queryDictionarysForPage(Dictionary dictionary);

	String getDictionarysByDicCode(String dicCode);

}
