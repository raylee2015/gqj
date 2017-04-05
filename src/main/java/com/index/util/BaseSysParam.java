package com.index.util;

public class BaseSysParam {
	private static String sysRootPath = "";

	public static String getSysRootPath() {
		return sysRootPath;
	}

	public static void setSysRootPath(String sysRootPath) {
		BaseSysParam.sysRootPath = sysRootPath;
	}

}
