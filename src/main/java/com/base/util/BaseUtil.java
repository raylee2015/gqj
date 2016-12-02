package com.base.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaseUtil {

	/**
	 * @Title: list2Tree @Description: TODO(递归形成树所需要的数据源) @param list @param
	 *         parentId @param idName @param upIdName @param childName @return
	 *         设定文件 @return JSONArray 返回类型 @throws
	 */
	public static JSONArray list2Tree(JSONArray list, long parentId,
			String idName, String upIdName, String childName) {
		JSONArray childMenu = new JSONArray();
		for (Object object : list) {
			JSONObject jsonMenu = JSONObject.fromObject(object);
			long menuId = jsonMenu
					.getLong(idName.toString().toUpperCase());
			long pid = 0;
			if (jsonMenu
					.get(upIdName.toString().toUpperCase()) != null) {
				pid = jsonMenu
						.getLong(upIdName.toString().toUpperCase());
			} else {
				pid = -1;
			}
			if (parentId == pid) {
				JSONArray c_node = list2Tree(list, menuId, idName,
						upIdName, childName);
				if (!c_node.isEmpty()) {
					jsonMenu.put(childName, c_node);
				}
				childMenu.add(jsonMenu);
			}
			if (pid == -1) {
				jsonMenu.put(childName, childMenu);
			}
		}
		return childMenu;
	}

	/**
	 * 
	 * @Title: strToInt @Description: TODO(字符串转整形) @param param @return
	 *         设定文件 @return Integer 返回类型 @throws
	 */
	public static Integer strToInt(String param) {
		if (param != null) {
			return Integer.parseInt(param);
		} else {
			return null;
		}
	}

	public static Long strToLong(String param) {
		if (param != null) {
			return Long.parseLong(param);
		} else {
			return null;
		}
	}
}
