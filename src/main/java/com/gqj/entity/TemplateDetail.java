package com.gqj.entity;

import com.base.entity.Base;

public class TemplateDetail extends Base {
	private Long detailId;

	private Long templateId;

	private Long matId;

	private String detailRemark;

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

	public Long getMatId() {
		return matId;
	}

	public void setMatId(Long matId) {
		this.matId = matId;
	}

	public String getDetailRemark() {
		return detailRemark;
	}

	public void setDetailRemark(String detailRemark) {
		this.detailRemark = detailRemark == null ? null
				: detailRemark.trim();
	}
}