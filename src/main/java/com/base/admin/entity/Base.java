package com.base.admin.entity;

/**
 * 1.主要用来分页用的，因为在oracle里面分页的优化而设置<br>
 * 2.用来使用in查询<br>
 * 3.关键字搜索
 * 
 * @author JMSCADMIN
 *
 * @param <T>
 */
public class Base {

	private int currPage;

	private int pageSize;

	private String keyWord;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	private String[] innerCodes;

	public String[] getInnerCodes() {
		return innerCodes;
	}

	public void setInnerCodes(String[] innerCodes) {
		this.innerCodes = innerCodes;
	}

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
