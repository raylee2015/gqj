package com.base.admin.entity;

/**
 * 1.主要用来分页用的，因为在oracle里面分页的优化而设置<br>
 * 2.用来使用in查询<br>
 * 3.关键字搜索<br>
 * 4.进行id串的搜索
 * 
 * @author JMSCADMIN
 *
 * @param <T>
 */
public class Base {

	/**
	 * 分页查询参数
	 */
	private int currPage;

	/**
	 * 参数是需要id串的时候
	 */
	private String ids;

	/**
	 */
	private String[] innerCodes;

	/**
	 * 关键字查询参数
	 */
	private String keyWord;

	/**
	 * 分页查询参数
	 */
	private int pageSize;

	public int getCurrPage() {
		return currPage;
	}

	public String getIds() {
		return ids;
	}

	public String[] getInnerCodes() {
		return innerCodes;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setInnerCodes(String[] innerCodes) {
		this.innerCodes = innerCodes;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
