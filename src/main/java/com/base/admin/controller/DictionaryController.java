package com.base.admin.controller;

import java.util.HashMap;
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
import com.base.admin.entity.Menu;
import com.base.admin.service.IDictionaryService;
import com.base.admin.service.IMenuService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/dictionary")
public class DictionaryController {

	public static final Logger LOGGER = Logger
			.getLogger(DictionaryController.class);

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private IMenuService menuService;

	/**
	 * 添加数据字典信息
	 * 
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
		String dictionaryCode = request.getParameter("DIC_CODE");
		String dictionaryValue = request.getParameter("DIC_VALUE");
		String dictionaryLabel = request.getParameter("DIC_LABEL");
		String dictionarySort = request.getParameter("DIC_SORT");
		String menuId = request.getParameter("MENU_ID");
		Dictionary dictionary = new Dictionary();
		dictionary.setDicId(-1l);
		dictionary.setDicName(dictionaryName);
		dictionary.setDicCode(dictionaryCode);
		dictionary.setDicValue(dictionaryValue);
		dictionary.setDicLabel(dictionaryLabel);
		dictionary.setDicSort(BaseUtil.strToLong(dictionarySort));
		dictionary.setMenuId(BaseUtil.strToLong(menuId));
		return dictionaryService.insertSelective(dictionary);
	}

	/**
	 * 删除数据字典
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

	/**
	 * 跳转到数据字典管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/base/admin/dictionary/editUI");
	}

	/**
	 * 分页查询数据字典列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDictionariesPage.do")
	@ResponseBody
	public Map<String, Object> queryDictionariesPage(
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String menuId = request.getParameter("MENU_ID");
		String keyWord = request.getParameter("keyWord");
		Dictionary dictionary = new Dictionary();
		dictionary.setMenuId(BaseUtil.strToLong(menuId));
		dictionary.setCurrPage(Integer.parseInt(page));
		dictionary.setPageSize(Integer.parseInt(rows));
		dictionary.setKeyWord(keyWord);
		return dictionaryService.queryDictionarysForPage(dictionary);
	}

	/**
	 * 查询系统树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryMenuTree.do")
	@ResponseBody
	public void queryMenuTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Menu menu = new Menu();
		// 查询子系统
		menu.setMenuLevel("1");
		response.getWriter().print(menuService.queryMenusForTree(menu));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 跳转到数据字典管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/dictionary/index");
	}

	/**
	 * 更新数据字典信息
	 * 
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
		String dictionaryCode = request.getParameter("DIC_CODE");
		String dictionaryValue = request.getParameter("DIC_VALUE");
		String dictionaryLabel = request.getParameter("DIC_LABEL");
		String dictionarySort = request.getParameter("DIC_SORT");
		String menuId = request.getParameter("MENU_ID");
		Dictionary dictionary = new Dictionary();
		dictionary.setDicId(BaseUtil.strToLong(dictionaryId));
		dictionary.setDicName(dictionaryName);
		dictionary.setDicCode(dictionaryCode);
		dictionary.setDicValue(dictionaryValue);
		dictionary.setDicLabel(dictionaryLabel);
		dictionary.setDicSort(BaseUtil.strToLong(dictionarySort));
		dictionary.setMenuId(BaseUtil.strToLong(menuId));
		int bool = dictionaryService
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

}
