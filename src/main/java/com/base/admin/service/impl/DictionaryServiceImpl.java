package com.base.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.DictionaryMapper;
import com.base.admin.entity.Dictionary;
import com.base.admin.service.IDictionaryService;

import net.sf.json.JSONArray;

@Service
public class DictionaryServiceImpl implements IDictionaryService {

	@Autowired
	private DictionaryMapper dictionaryMapper;

	@Override
	public Map<String, Object> deleteDictionaries(
			Dictionary dictionary) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = dictionaryMapper.deleteByPrimaryKeys(dictionary);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addNewDictionary(Dictionary dictionary) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (queryDictionarysForList(dictionary).size() > 0) {
			result.put("success", false);
			result.put("msg", "系统已经存在同样的字典代码与字典值");
		} else {
			int resultOfInsert = dictionaryMapper
					.insertSelective(dictionary);
			if (resultOfInsert == 0) {
				result.put("success", false);
				result.put("msg", "保存失败，请联系系统管理员");
			} else {
				result.put("success", true);
				result.put("msg", "保存成功");
			}

		}
		return result;
	}

	@Override
	public List<Dictionary> queryDictionarysForList(
			Dictionary dictionary) {
		return dictionaryMapper.queryDictionarysForList(dictionary);
	}

	@Override
	public Map<String, Object> queryDictionarysForPage(
			Dictionary dictionary) {
		List<Map<String, Object>> dictionarys = dictionaryMapper
				.queryDictionarysForPage(dictionary);
		int count = dictionaryMapper
				.queryCountOfDictionarysForPage(dictionary);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", dictionarys);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateDictionary(Dictionary dictionary) {
		int bool = dictionaryMapper
				.updateByPrimaryKeySelective(dictionary);
		Map<String, Object> map = new HashMap<String, Object>();
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, List<Map<String, Object>>> queryDictionarysForCache() {
		List<Map<String, Object>> dicList = dictionaryMapper
				.queryDictionarysForCache();
		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
		for (int i = 0; i < dicList.size(); i++) {
			Map<String, Object> item = dicList.get(i);
			String dicCode = item.get("DIC_CODE").toString();
			if (result.containsKey(dicCode)) {
				List<Map<String, Object>> listOfResult = result
						.get(dicCode);
				listOfResult.add(item);
				result.put(dicCode, listOfResult);
			} else {
				List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
				newList.add(item);
				result.put(dicCode, newList);
			}
		}
		return result;
	}

	@Override
	public String getDictionarysByDicCode(String dicCode) {
		List<Map<String, Object>> list = queryDictionarysForCache()
				.get(dicCode);
		String result = "";
		if (list != null && list.size() != 0) {
			result = JSONArray.fromObject(list).toString();
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getDictionaryListByDicCode(
			String dicCode) {
		return queryDictionarysForCache().get(dicCode);
	}

}
