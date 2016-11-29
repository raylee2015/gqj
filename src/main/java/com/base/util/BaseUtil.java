package com.base.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaseUtil {

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
}
