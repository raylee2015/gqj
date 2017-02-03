package com.gqj.dao;

import java.util.Map;

public interface SequenceMapper {
	Map<String, Object> selectSequence(
			Map<String, Object> param);
}