package com.bpbj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bpbj.entity.Batch;
import com.bpbj.entity.PlugIn;
import com.bpbj.entity.PlugInTrack;

public interface IBPBJPlugInService {
	Map<String, Object> deletePlugIns(PlugIn plugIn);

	Map<String, Object> addNewPlugIn(PlugIn plugIn);

	Map<String, Object> updatePlugIn(PlugIn plugIn);

	Map<String, Object> selectPlugInsForPage(
			HashMap<String, Object> param);

	Map<String, Object> checkInPlugIn(Batch batch,
			PlugIn plugIn, PlugInTrack plugInTrack);

	Map<String, Object> resetPlugIn(PlugIn plugIn,
			PlugInTrack plugInTrack);

	Map<String, Object> checkOutPlugIn(Batch batch, PlugIn plugIn,
			PlugInTrack plugInTrack);

	Map<String, Object> updatePlugInByBatch(PlugIn plugIn);

	List<PlugIn> selectPlugInsForList(PlugIn plugIn);

	Map<String, Object> exchangePlugIn(Batch batch,
			PlugIn plugInParam, PlugInTrack plugInTrack);

	Map<String, Object> backPlugIn(Batch batch, PlugIn plugInParam,
			PlugInTrack plugInTrack);

	Map<String, Object> rejectPlugIn(Batch batch, PlugIn plugInParam,
			PlugInTrack plugInTrack);

	Map<String, Object> borrowPlugIn(Batch batch, PlugIn plugInParam,
			PlugInTrack plugInTrack);

	Map<String, Object> usePlugIn(Batch batch, PlugIn plugInParam,
			PlugInTrack plugInTrack);

	Map<String, Object> selfRetrunPlugIn(Batch batch,
			PlugIn plugInParam, PlugInTrack plugInTrack);

	PlugIn selectPlugInForObject(PlugIn plugIn);

}
