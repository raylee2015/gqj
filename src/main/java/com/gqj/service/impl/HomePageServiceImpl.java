package com.gqj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.service.IParamService;
import com.base.util.BaseUtil;
import com.base.util.DateUtil;
import com.gqj.dao.HomePageMapper;
import com.gqj.service.IHomePageService;

@Service
public class HomePageServiceImpl
		implements IHomePageService {

	@Autowired
	private HomePageMapper homePageMapper;

	@Autowired
	private IParamService paramService;
	
	@Override
	public Map<String, Object> selectNeedReturnToolsForPage(
			Map<String, Object> param) {
		List<Map<String, Object>> list = homePageMapper
				.selectNeedReturnToolsForPage(param);
		for (Map<String, Object> item : list) {
			if (item.get("TOOL_NEXT_TEST_DATE") != null) {
				item.put("TOOL_NEXT_TEST_DATE",
						DateUtil.getDate(item
								.get("TOOL_NEXT_TEST_DATE")
								.toString()));
			}
			// 计算超期的日期
			int days = BaseUtil.strToInt(
					paramService.queryParamsForMap(
							"BEFORE_TEST_DAYS"));
			Date now = new Date();
			Date sysDate = DateUtil.addDay(new Date(),
					days);
			Date toolNextTestDate = DateUtil
					.StringToDate(DateUtil.getDate(item
							.get("TOOL_NEXT_TEST_DATE")
							.toString()));
			if (sysDate.after(toolNextTestDate)) {
				item.put("NEED_TEST", 1);
			}
			if (now.after(toolNextTestDate)) {
				item.put("NEED_TEST", 2);
			}
		}
		int count = homePageMapper
				.selectCountOfNeedReturnToolsForPage(param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", count);
		return map;
	}

}
