package com.bpbj.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpbj.dao.BPBJSequenceMapper;
import com.bpbj.service.IBPBJSequenceService;

@Service
public class BPBJSequenceServiceImpl
		implements IBPBJSequenceService {

	@Autowired
	private BPBJSequenceMapper sequenceMapper;

	@Override
	public String selectSequence(
			Map<String, Object> param) {
		sequenceMapper.selectSequence(param);
		return param.get("result").toString();
	}

}
