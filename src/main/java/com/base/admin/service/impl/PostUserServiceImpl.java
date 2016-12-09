package com.base.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.PostUserMapper;
import com.base.admin.entity.Post;
import com.base.admin.entity.PostUser;
import com.base.admin.service.IDeptService;
import com.base.admin.service.IPostService;
import com.base.admin.service.IPostUserService;

@Service
public class PostUserServiceImpl implements IPostUserService {

	@Autowired
	private IPostService postService;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private PostUserMapper postUserMapper;

	@Override
	public Map<String, Object> selectPostsForPage(Post post) {
		return postService.selectPostsForPage(post);
	}

	@Override
	public int deleteByPrimaryKeys(PostUser postUser) {
		return postUserMapper.deleteByPrimaryKeys(postUser);
	}

	@Override
	public int insert(PostUser postUser) {
		return postUserMapper.insert(postUser);
	}

	@Override
	public String selectDeptsForTree() {
		return deptService.selectDeptsForTree();
	}

	@Override
	public Map<String, Object> querySelectedUsersForPage(
			PostUser postUser) {
		List<Map<String, Object>> postUsers = postUserMapper
				.querySelectedUsers(postUser);
		int count = postUserMapper.queryCountOfSelectedUsers(postUser);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", postUsers);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> queryUnSelectedUsersForPage(
			PostUser postUser) {
		List<Map<String, Object>> postUsers = postUserMapper
				.queryUnSelectedUsers(postUser);
		int count = postUserMapper
				.queryCountOfUnSelectedUsers(postUser);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", postUsers);
		map.put("total", count);
		return map;
	}

}
