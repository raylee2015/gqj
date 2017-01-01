package com.gqj.service;

import java.util.Map;

import com.gqj.entity.ToolDemand;

public interface IToolDemandService {
	Map<String, Object> deleteToolDemands(
			ToolDemand toolDemand);

	Map<String, Object> addNewToolDemand(
			ToolDemand toolDemand);

	Map<String, Object> updateToolDemand(
			ToolDemand toolDemand);

	Map<String, Object> selectToolDemandsForPage(
			ToolDemand toolDemand);
}
