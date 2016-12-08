package com.base.admin.entity;

public class Post extends Base {
	private Long deptId;

	private String postDesp;

	private Long postId;

	private String postName;

	private Long postSort;

	public Long getDeptId() {
		return deptId;
	}

	public String getPostDesp() {
		return postDesp;
	}

	public Long getPostId() {
		return postId;
	}

	public String getPostName() {
		return postName;
	}

	public Long getPostSort() {
		return postSort;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setPostDesp(String postDesp) {
		this.postDesp = postDesp == null ? null : postDesp.trim();
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public void setPostName(String postName) {
		this.postName = postName == null ? null : postName.trim();
	}

	public void setPostSort(Long postSort) {
		this.postSort = postSort;
	}
}