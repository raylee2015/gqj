package com.base.admin.entity;

/**
 * 主要用来分页用的，因为在oracle里面分页的优化而设置
 * 
 * @author JMSCADMIN
 *
 * @param <T>
 */
public class Base {

	private int currPage;

	private int pageSize;

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
