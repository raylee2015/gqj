package com.base.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.entity.Dictionary;
import com.base.admin.service.IDictionaryService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/dictionary")
public class DictionaryController {

	public static final Logger LOGGER = Logger
			.getLogger(DictionaryController.class);

	/**
	 * 跳转到部门管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/dictionary");
	}

	@Autowired
	private IDictionaryService dictionaryService;

	/**
	 * 分页查询部门列表
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            显示行数
	 * @param keyWord
	 *            关键字
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDictionaryPage.do")
	@ResponseBody
	public Map<String, Object> queryDictionaryPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		Dictionary dictionary = new Dictionary();
		dictionary.setCurrPage(Integer.parseInt(page));
		dictionary.setPageSize(Integer.parseInt(rows));
		dictionary.setKeyWord(keyWord);
		List<Map<String, Object>> dictionarys = dictionaryService
				.selectDictionarysForPage(dictionary);
		int count = dictionaryService
				.selectCountOfDictionarysForPage(dictionary);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", dictionarys);
		map.put("total", count);
		return map;
	}

	/**
	 * 添加部门信息
	 * 
	 * @param dictionaryName
	 *            部门名称
	 * @param dictionarySort
	 *            部门排序号
	 * @param upDictionaryId
	 *            上级部门id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewDictionary.do")
	@ResponseBody
	public Map<String, Object> addNewDictionary(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String dictionaryName = request.getParameter("DIC_NAME");
		String dictionarySort = request.getParameter("DIC_SORT");
		Map<String, Object> map = new HashMap<String, Object>();
		Dictionary dictionary = new Dictionary();
		dictionary.setDicId(-1l);
		dictionary.setDicName(dictionaryName);
		dictionary.setDicSort(BaseUtil.strToLong(dictionarySort));
		int bool = dictionaryService.insertSelective(dictionary);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	/**
	 * 更新部门信息
	 * 
	 * @param dictionaryId
	 *            部门id
	 * @param dictionaryName
	 *            部门名称
	 * @param dictionarySort
	 *            部门排序号
	 * @param upDictionaryId
	 *            上级部门id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateDictionary.do")
	@ResponseBody
	public Map<String, Object> updateDictionary(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String dictionaryId = request.getParameter("DIC_ID");
		String dictionaryName = request.getParameter("DIC_NAME");
		String dictionarySort = request.getParameter("DIC_SORT");
		Map<String, Object> map = new HashMap<String, Object>();
		Dictionary dictionary = new Dictionary();
		dictionary.setDicId(BaseUtil.strToLong(dictionaryId));
		dictionary.setDicName(dictionaryName);
		dictionary.setDicSort(BaseUtil.strToLong(dictionarySort));
		int bool = dictionaryService
				.updateByPrimaryKeySelective(dictionary);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	/**
	 * 删除部门
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delDictionarys.do")
	@ResponseBody
	public Map<String, Object> delDictionarys(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String dictionaryIds = request.getParameter("DIC_IDS");
		Map<String, Object> map = new HashMap<>();
		Dictionary dictionary = new Dictionary();
		dictionary.setIds(dictionaryIds);
		int bool = dictionaryService.deleteByPrimaryKeys(dictionary);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

}
