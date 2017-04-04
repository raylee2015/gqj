package com.gqj.dao;

import java.util.List;
import java.util.Map;

public interface HomePageMapper {

	List<Map<String, Object>> selectNeedReturnToolsForPage(
			Map<String, Object> param);

	int selectCountOfNeedReturnToolsForPage(
			Map<String, Object> param);
}