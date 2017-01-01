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
	public Map<String, Object> deletePosts(
			Post post) {
		Map<String, Object> map = new HashMap<>();
		int bool = postMapper.deleteByPrimaryKeys(post);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addNewPost(Post post) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = postMapper.insertSelective(post);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> selectPostsForPage(
			Post post) {
		List<Map<String, Object>> posts = postMapper
				.selectPostsForPage(post);
		int count = postMapper
				.selectCountOfPostsForPage(post);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", posts);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updatePost(
			Post post) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = postMapper
				.updateByPrimaryKeySelective(post);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
