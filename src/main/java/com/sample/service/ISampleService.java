package com.sample.service;

import java.util.List;

import com.sample.entity.Sample;

public interface ISampleService {
	List<Sample> querySamples() throws Exception;
}
