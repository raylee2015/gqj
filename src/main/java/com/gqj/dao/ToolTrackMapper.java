package com.gqj.dao;

import com.gqj.entity.ToolTrack;

public interface ToolTrackMapper {
    int deleteByPrimaryKey(Long trackId);

    int insert(ToolTrack record);

    int insertSelective(ToolTrack record);

    ToolTrack selectByPrimaryKey(Long trackId);

    int updateByPrimaryKeySelective(ToolTrack record);

    int updateByPrimaryKey(ToolTrack record);
}