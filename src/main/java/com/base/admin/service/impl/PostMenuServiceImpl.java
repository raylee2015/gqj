package com.base.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.PostMenuMapper;
import com.base.admin.entity.Menu;
import com.base.admin.entity.Post;
import com.base.admin.entity.PostMenu;
import com.base.admin.service.IDeptService;
import com.base.admin.service.IMenuService;
import com.base.admin.service.IPostMenuService;
import com.base.admin.service.IPostService;
import com.base.util.BaseUtil;

import net.sf.json.JSONArray;

@Service
public class PostMenuServiceImpl implements IPostMenuService {

	@Autowired
	private IPostService postService;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private PostMenuMapper postMenuMapper;

	@Autowired
	private IMenuService menuService;

	@Override
	public Map<String, Object> selectPostsForPage(Post post) {
		return postService.selectPostsForPage(post);
	}

	@Override
	public int deleteByPrimaryKeys(PostMenu postMenu) {
		return postMenuMapper.deleteByPrimaryKeys(postMenu);
	}

	@Override
	public int insert(PostMenu postMenu) {
		String menuIds = postMenu.getIds();
		String[] menuId_arr = menuIds.split(",");
		int result = 0;
		for (int i = 0; i < menuId_arr.length; i++) {
			String menuId = menuId_arr[i];
			if ("0".equals(menuId)) {
				continue;
			}
			PostMenu temp = new PostMenu();
			temp.setPostId(postMenu.getPostId());
			temp.setMenuId(BaseUtil.strToLong(menuId));
			if (postMenuMapper.selectCountByPrimaryKey(temp) == 0) {
				result = postMenuMapper.insert(temp);
			}
		}
		return result;
	}

	@Override
	public String selectDeptsForTree() {
		return deptService.selectDeptsForTree();
	}

	@Override
	public String querySelectedMenusForTree(PostMenu postMenu) {
		List<Map<String, Object>> menus = postMenuMapper
				.selectSelectedMenusForTree(postMenu);
		JSONArray menuArr = JSONArray.fromObject(menus);
		String tree = BaseUtil
				.list2Tree(menuArr, -1, "id", "up_menu_id", "children")
				.toString().toLowerCase();
		return tree;
	}

	@Override
	public String queryUnSelectedMenusForTree() {
		return menuService.queryMenusForTree(new Menu());
	}

}
