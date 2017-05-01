package com.bpbj.dao;

import java.util.List;
import java.util.Map;

public interface BPBJHomePageMapper {

	List<Map<String, Object>> selectNeedReturnToolsForPage(
			Map<String, Object> param);

	int selectCountOfNeedReturnToolsForPage(
			Map<String, Object> param);
}