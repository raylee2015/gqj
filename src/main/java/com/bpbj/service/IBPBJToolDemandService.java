package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.ToolDemand;

public interface IBPBJToolDemandService {
	Map<String, Object> deleteToolDemands(
			ToolDemand toolDemand);

	Map<String, Object> addNewToolDemand(
			ToolDemand toolDemand);

	Map<String, Object> updateToolDemand(
			ToolDemand toolDemand);

	Map<String, Object> selectToolDemandsForPage(
			ToolDemand toolDemand);
}
