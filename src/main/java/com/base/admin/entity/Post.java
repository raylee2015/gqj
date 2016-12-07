package com.base.admin.entity;

public class Post {
    private Long postId;

    private String postName;

    private String postDesp;

    private Long postSort;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName == null ? null : postName.trim();
    }

    public String getPostDesp() {
        return postDesp;
    }

    public void setPostDesp(String postDesp) {
        this.postDesp = postDesp == null ? null : postDesp.trim();
    }

    public Long getPostSort() {
        return postSort;
    }

    public void setPostSort(Long postSort) {
        this.postSort = postSort;
    }
}