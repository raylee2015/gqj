package com.base.admin.service;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Dictionary;

public interface IDictionaryService {
	int deleteByPrimaryKeys(Dictionary dictionary);

	Map<String, Object> insertSelective(Dictionary dictionary);

	int updateByPrimaryKeySelective(Dictionary dictionary);

	List<Dictionary> selectDictionarysForList(Dictionary dictionary);

	Map<String, List<Map<String, Object>>> selectDictionarysForCache();

	List<Map<String, Object>> selectDictionarysForPage(
			Dictionary dictionary);

	int selectCountOfDictionarysForPage(Dictionary dictionary);

}
