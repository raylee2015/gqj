package com.base.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.PostMapper;
import com.base.admin.entity.Post;
import com.base.admin.service.IPostService;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	private PostMapper postMapper;

	@Override
	public int deleteByPrimaryKeys(Post post) {
		return postMapper.deleteByPrimaryKeys(post);
	}

	@Override
	public int insertSelective(Post post) {
		return postMapper.insertSelective(post);
	}

	@Override
	public Map<String, Object> selectPostsForPage(Post post) {
		List<Map<String, Object>> posts = postMapper
				.selectPostsForPage(post);
		int count = postMapper.selectCountOfPostsForPage(post);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", posts);
		map.put("total", count);
		return map;
	}

	@Override
	public int updateByPrimaryKeySelective(Post post) {
		return postMapper.updateByPrimaryKeySelective(post);
	}

}
