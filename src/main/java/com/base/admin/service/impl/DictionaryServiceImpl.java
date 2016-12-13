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
	public int deleteByPrimaryKeys(Dictionary dictionary) {
		return dictionaryMapper.deleteByPrimaryKeys(dictionary);
	}

	@Override
	public Map<String, Object> insertSelective(Dictionary dictionary) {
		Map<String, Object> result = new HashMap<>();
		if (selectDictionarysForList(dictionary).size() > 0) {
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

	@Override
	public Map<String, List<Map<String, Object>>> selectDictionarysForCache() {
		List<Map<String, Object>> dicList = dictionaryMapper
				.selectDictionarysForCache();
		Map<String, List<Map<String, Object>>> result = new HashMap<>();
		for (int i = 0; i < dicList.size(); i++) {
			Map<String, Object> item = dicList.get(i);
			String dicCode = item.get("DIC_CODE").toString();
			if (result.containsKey(dicCode)) {
				List<Map<String, Object>> listOfResult = result
						.get(dicCode);
				listOfResult.add(item);
				result.put(dicCode, listOfResult);
			} else {
				List<Map<String, Object>> newList = new ArrayList<>();
				newList.add(item);
				result.put(dicCode, newList);
			}
		}
		return result;
	}

	@Override
	public String getDictionarysByDicCode(String dicCode) {
		List<Map<String, Object>> list = selectDictionarysForCache()
				.get(dicCode);
		String result = "";
		if (list != null && list.size() != 0) {
			result = JSONArray.fromObject(list).toString();
		}
		return result;
	}

}
