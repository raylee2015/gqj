package com.gqj.util;

public class PlanStatus {

	/**
	 * 未提交
	 */
	public static Long UNSUBMIT = 0L;

	/**
	 * 提交
	 */
	public static Long SUBMIT = 1L;

	/**
	 * 班组通过
	 */
	public static Long PASS_BY_WORK_GROUP = 2L;

	/**
	 * 班组不通过
	 */
	public static Long UNPASS_BY_WORK_GROUP = 3L;

	/**
	 * 部门通过
	 */
	public static Long PASS_BY_DEPT = 4L;

	/**
	 * 部门不通过
	 */
	public static Long UNPASS_BY_DEPT = 5L;

	/**
	 * 完成
	 */
	public static Long FINISH = 6L;

	/**
	 * 汇总
	 */
	public static Long TOTAL = 7L;
}
