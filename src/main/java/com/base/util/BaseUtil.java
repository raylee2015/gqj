package com.base.util;

import java.security.MessageDigest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaseUtil {

	/**
	 * @Description 递归形成树所需要的数据源
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月2日
	 * @param list
	 *            传入数据列表
	 * @param parentId
	 *            父id
	 * @param idName
	 *            id名称
	 * @param upIdName
	 *            父id名称
	 * @param childName
	 *            孩子的名称
	 * @return
	 */
	public static JSONArray list2Tree(JSONArray list,
			long parentId, String idName, String upIdName,
			String childName) {
		JSONArray childMenu = new JSONArray();
		for (Object object : list) {
			JSONObject jsonMenu = JSONObject
					.fromObject(object);
			long menuId = jsonMenu.getLong(
					idName.toString().toUpperCase());
			long pid = 0;
			if (jsonMenu.get(upIdName.toString()
					.toUpperCase()) != null) {
				pid = jsonMenu.getLong(
						upIdName.toString().toUpperCase());
			} else {
				pid = -1;
			}
			if (parentId == pid) {
				JSONArray c_node = list2Tree(list, menuId,
						idName, upIdName, childName);
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
	 * @Description: 字符串转整形
	 * @Author: RayLee
	 * @Version: 1.0
	 * @date 2016年12月2日
	 * @param param
	 *            传入参数
	 * @return
	 */
	public static Integer strToInt(String param) {
		if (param != null && param != "") {
			return Integer.parseInt(param);
		} else {
			return null;
		}
	}

	/**
	 * @Description: 字符串转double
	 * @Author: RayLee
	 * @Version: 1.0
	 * @date 2016年12月2日
	 * @param param
	 *            传入参数
	 * @return
	 */
	public static Double strToDouble(String param) {
		if (param != null && param != "") {
			return Double.parseDouble(param);
		} else {
			return null;
		}
	}

	/**
	 * @Description: 字符串转换成Long型
	 * @Author: RayLee
	 * @Version: 1.0
	 * @date 2016年12月2日
	 * @param param
	 *            传入参数
	 * @return
	 */
	public static Long strToLong(String param) {
		if (param != null && param != "") {
			return Long.parseLong(param);
		} else {
			return null;
		}
	}

	/**
	 * @Description: md5加密
	 * @Author: RayLee
	 * @Version: 1.0
	 * @date 2016年12月2日
	 * @param param
	 *            传入参数
	 * @return
	 */
	public final static String MD5(String param) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
				'F' };
		try {
			byte[] btInput = param.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest
					.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Description 初始化用户密码：123456
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月2日
	 * @return
	 */
	public static String initUserPassWord() {
		return MD5("123456").substring(0, 20);
	}

	/**
	 * @Description unicode转中文
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月28日
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar
									- '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10
									+ aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10
									+ aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();

	}
}
