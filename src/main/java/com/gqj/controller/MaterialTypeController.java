package com.gqj.controller;

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

import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.gqj.entity.MaterialType;
import com.gqj.service.IMaterialTypeService;

@Controller
@RequestMapping("/gqj/material_type")
public class MaterialTypeController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(MaterialTypeController.class);

	@Autowired
	private IMaterialTypeService materialTypeService;

	/**
	 * 添加物资类型信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewMaterialType.do")
	@ResponseBody
	public Map<String, Object> addNewMaterialType(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String typeName = request.getParameter("TYPE_NAME");
		Map<String, Object> map = new HashMap<String, Object>();
		MaterialType materialType = new MaterialType();
		materialType.setTypeId(-1l);
		materialType.setTypeName(typeName);
		int bool = materialTypeService.insertSelective(materialType);
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
	 * 删除物资类型
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delMaterialTypes.do")
	@ResponseBody
	public Map<String, Object> delMaterialTypes(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String materialTypeIds = request.getParameter("TYPE_IDS");
		Map<String, Object> map = new HashMap<>();
		MaterialType materialType = new MaterialType();
		materialType.setIds(materialTypeIds);
		int bool = materialTypeService
				.deleteByPrimaryKeys(materialType);
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
	 * 跳转到物资类型管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/material_type/editUI");
	}

	/**
	 * 分页查询物资类型列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMaterialTypesPage.do")
	@ResponseBody
	public Map<String, Object> queryMaterialTypesPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		MaterialType materialType = new MaterialType();
		materialType.setCurrPage(Integer.parseInt(page));
		materialType.setPageSize(Integer.parseInt(rows));
		materialType.setKeyWord(keyWord);
		return materialTypeService
				.selectMaterialTypesForPage(materialType);
	}

	/**
	 * 跳转到物资类型管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/material_type/index");
	}

	/**
	 * 更新物资类型信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMaterialType.do")
	@ResponseBody
	public Map<String, Object> updateMaterialType(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String typeId = request.getParameter("TYPE_ID");
		String typeName = request.getParameter("TYPE_NAME");
		Map<String, Object> map = new HashMap<String, Object>();
		MaterialType materialType = new MaterialType();
		materialType.setTypeId(BaseUtil.strToLong(typeId));
		materialType.setTypeName(typeName);
		int bool = materialTypeService
				.updateByPrimaryKeySelective(materialType);
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
