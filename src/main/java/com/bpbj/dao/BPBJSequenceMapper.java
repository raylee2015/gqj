package com.bpbj.dao;

import java.util.Map;

public interface BPBJSequenceMapper {
	Map<String, Object> selectSequence(
			Map<String, Object> param);
}