package com.gqj.util;

public class PlanStatus {

	/**
	 * 未提交
	 */
	public static Long UNSUBMIT = 0l;

	/**
	 * 提交
	 */
	public static Long SUBMIT = 1l;

	/**
	 * 班组通过
	 */
	public static Long PASS_BY_WORK_GROUP = 2l;

	/**
	 * 班组不通过
	 */
	public static Long UNPASS_BY_WORK_GROUP = 3l;

	/**
	 * 部门通过
	 */
	public static Long PASS_BY_DEPT = 4l;

	/**
	 * 部门不通过
	 */
	public static Long UNPASS_BY_DEPT = 5l;

	/**
	 * 完成
	 */
	public static Long FINISH = 6l;
}
