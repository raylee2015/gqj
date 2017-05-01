package com.bpbj.util;

public class ToolStatus {

	/**
	 * 入库打单
	 */
	public static Long CHECK_IN_COMING = 0L;

	/**
	 * 入库
	 */
	public static Long CHECK_IN = 1L;

	/**
	 * 
	 * 出库打单
	 */
	public static Long CHECK_OUT_COMING = 2L;

	/**
	 * 出库
	 */
	public static Long CHECK_OUT = 3L;

	/**
	 * 在途
	 */
	public static Long ON_ROAD = 4L;

	/**
	 * 报废打单
	 */
	public static Long REJECT_COMING = 5L;

	/**
	 * 报废
	 */
	public static Long REJECT = 6L;

	/**
	 * 借用打单
	 */
	public static Long BORROW_COMING = 7L;

	/**
	 * 借用
	 */
	public static Long BORROW = 8L;
}
