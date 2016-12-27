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

import com.base.admin.service.IDictionaryService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.gqj.entity.Material;
import com.gqj.entity.MaterialType;
import com.gqj.service.IMaterialService;
import com.gqj.service.IMaterialTypeService;

@Controller
@RequestMapping("/gqj/material")
public class MaterialController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(MaterialController.class);

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private IMaterialService materialService;

	@Autowired
	private IMaterialTypeService materialTypeService;

	/**
	 * 添加工器具信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewMaterial.do")
	@ResponseBody
	public Map<String, Object> addNewMaterial(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String typeId = request.getParameter("TYPE_ID");
		String matName = request.getParameter("MAT_NAME");
		String matSpec = request.getParameter("MAT_SPEC");
		String matModel = request.getParameter("MAT_MODEL");
		String matUnit = request.getParameter("MAT_UNIT");
		String matRemark = request.getParameter("MAT_REMARK");
		Map<String, Object> map = new HashMap<String, Object>();
		Material material = new Material();
		material.setMatId(-1l);
		material.setTypeId(BaseUtil.strToLong(typeId));
		material.setMatName(matName);
		material.setMatSpec(matSpec);
		material.setMatModel(matModel);
		material.setMatUnit(BaseUtil.strToLong(matUnit));
		material.setMatRemark(matRemark);
		int bool = materialService.insertSelective(material);
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
	 * 删除工器具
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delMaterials.do")
	@ResponseBody
	public Map<String, Object> delMaterials(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String materialIds = request.getParameter("MAT_IDS");
		Map<String, Object> map = new HashMap<>();
		Material material = new Material();
		material.setIds(materialIds);
		int bool = materialService.deleteByPrimaryKeys(material);
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
	 * 跳转到工器具管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/material/editUI");
	}

	/**
	 * 分页查询工器具列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMaterialsPage.do")
	@ResponseBody
	public Map<String, Object> queryMaterialsPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		Material material = new Material();
		material.setCurrPage(Integer.parseInt(page));
		material.setPageSize(Integer.parseInt(rows));
		material.setKeyWord(keyWord);
		return materialService.selectMaterialsForPage(material);
	}

	/**
	 * 查询下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryMaterialTypeDropList.do")
	@ResponseBody
	public void queryMaterialTypeDropList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(materialTypeService
				.selectMaterialTypesForList(new MaterialType()));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 查询工器具单位下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryMaterialUnitDropList.do")
	@ResponseBody
	public void queryMaterialUnitDropList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(
				dictionaryService.getDictionarysByDicCode("UNIT"));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 跳转到工器具管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/material/index");
	}

	/**
	 * 更新工器具信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMaterial.do")
	@ResponseBody
	public Map<String, Object> updateMaterial(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String matId = request.getParameter("MAT_ID");
		String typeId = request.getParameter("TYPE_ID");
		String matName = request.getParameter("MAT_NAME");
		String matSpec = request.getParameter("MAT_SPEC");
		String matModel = request.getParameter("MAT_MODEL");
		String matUnit = request.getParameter("MAT_UNIT");
		String matRemark = request.getParameter("MAT_REMARK");
		Map<String, Object> map = new HashMap<String, Object>();
		Material material = new Material();
		material.setMatId(BaseUtil.strToLong(matId));
		material.setTypeId(BaseUtil.strToLong(typeId));
		material.setMatName(matName);
		material.setMatSpec(matSpec);
		material.setMatModel(matModel);
		material.setMatUnit(BaseUtil.strToLong(matUnit));
		material.setMatRemark(matRemark);
		int bool = materialService
				.updateByPrimaryKeySelective(material);
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
