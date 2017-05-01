package com.bpbj.util;

public class BatchType {

	/**
	 * 入库
	 */
	public static Long CHECK_IN = 0L;

	/**
	 * 出库
	 */
	public static Long CHECK_OUT = 1L;

	/**
	 * 转仓
	 */
	public static Long EXCHANGE = 2L;

	/**
	 * 
	 * 退仓
	 */
	public static Long BACK = 3L;

	/**
	 * 报废
	 */
	public static Long REJECT = 4L;

	/**
	 * 借用
	 */
	public static Long BORROW = 5L;

	/**
	 * 归还
	 */
	public static Long RETURN = 6L;
	/**
	 * 本站使用
	 */
	public static Long USE = 8L;
	/**
	 * 本站归还
	 */
	public static Long SELF_RETURN = 9L;

}
