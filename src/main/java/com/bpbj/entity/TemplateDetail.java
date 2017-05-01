package com.bpbj.entity;

import com.base.entity.Base;

public class TemplateDetail extends Base {
	private Long detailId;

	private Long templateId;

	private Long toolId;

	private String detailRemark;

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getDetailRemark() {
		return detailRemark;
	}

	public void setDetailRemark(String detailRemark) {
		this.detailRemark = detailRemark == null ? null
				: detailRemark.trim();
	}
}