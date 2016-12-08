package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Post;

public interface PostMapper {

	int deleteByPrimaryKeys(Post post);

	List<Map<String, Object>> selectPostsForPage(Post post);

	int selectCountOfPostsForPage(Post post);

	int insertSelective(Post post);

	int updateByPrimaryKeySelective(Post post);
}