package com.base.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.UserMapper;
import com.base.admin.entity.User;
import com.base.admin.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	/*
	 * (非 Javadoc) <p>Title: deleteByPrimaryKeys</p> <p>Description: </p>
	 * 
	 * @param userIds
	 * 
	 * @return
	 * 
	 * @see
	 * com.base.admin.service.IUserService#deleteByPrimaryKeys(java.lang.String[
	 * ])
	 */
	@Override
	public int deleteByPrimaryKeys(String[] userIds) {
		return userMapper.deleteByPrimaryKeys(userIds);
	}

	/*
	 * (非 Javadoc) <p>Title: selectUsersForPage</p> <p>Description: </p>
	 * 
	 * @param user
	 * 
	 * @return
	 * 
	 * @see
	 * com.base.admin.service.IUserService#selectUsersForPage(com.base.admin.
	 * entity.User)
	 */
	@Override
	public List<Map<String, Object>> selectUsersForPage(User user) {
		return userMapper.selectUsersForPage(user);
	}

	@Override
	public List<User> selectUsersForList(User user) {
		return userMapper.selectUsersForList(user);
	}

	/*
	 * (非 Javadoc) <p>Title: selectCountOfUsersForPage</p> <p>Description: </p>
	 * 
	 * @param user
	 * 
	 * @return
	 * 
	 * @see
	 * com.base.admin.service.IUserService#selectCountOfUsersForPage(com.base.
	 * admin.entity.User)
	 */
	@Override
	public int selectCountOfUsersForPage(User user) {
		return userMapper.selectCountOfUsersForPage(user);
	}

	/*
	 * (非 Javadoc) <p>Title: insertSelective</p> <p>Description: </p>
	 * 
	 * @param user
	 * 
	 * @return
	 * 
	 * @see
	 * com.base.admin.service.IUserService#insertSelective(com.base.admin.entity
	 * .User)
	 */
	@Override
	public int insertSelective(User user) {
		return userMapper.insertSelective(user);
	}

	/*
	 * (非 Javadoc) <p>Title: updateByPrimaryKeySelective</p> <p>Description:
	 * </p>
	 * 
	 * @param user
	 * 
	 * @return
	 * 
	 * @see
	 * com.base.admin.service.IUserService#updateByPrimaryKeySelective(com.base.
	 * admin.entity.User)
	 */
	@Override
	public int updateByPrimaryKeysSelective(User user) {
		return userMapper.updateByPrimaryKeysSelective(user);
	}

}
