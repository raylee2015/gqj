package com.gqj.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.SequenceMapper;
import com.gqj.service.ISequenceService;

@Service
public class SequenceServiceImpl
		implements ISequenceService {

	@Autowired
	private SequenceMapper sequenceMapper;

	@Override
	public String selectSequence(
			Map<String, Object> param) {
		sequenceMapper.selectSequence(param);
		return param.get("result").toString();
	}

}
