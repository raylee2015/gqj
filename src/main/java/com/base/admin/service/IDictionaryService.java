package com.base.admin.service;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Dictionary;

public interface IDictionaryService {
	Map<String, Object> deleteDictionaries(Dictionary dictionary);

	Map<String, Object> addNewDictionary(Dictionary dictionary);

	Map<String, Object> updateDictionary(Dictionary dictionary);

	List<Dictionary> queryDictionarysForList(Dictionary dictionary);

	Map<String, List<Map<String, Object>>> queryDictionarysForCache();

	Map<String, Object> queryDictionarysForPage(Dictionary dictionary);

	String getDictionarysByDicCode(String dicCode);

}
