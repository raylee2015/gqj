package com.bpbj.entity;

import java.util.Date;

import com.base.entity.Base;

public class Template extends Base{
    private Long templateId;

    private String templateName;

    private Date templateCreateDate;

    private Long templateCreateUserId;

    private Long templateDeptId;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public Date getTemplateCreateDate() {
        return templateCreateDate;
    }

    public void setTemplateCreateDate(Date templateCreateDate) {
        this.templateCreateDate = templateCreateDate;
    }

    public Long getTemplateCreateUserId() {
        return templateCreateUserId;
    }

    public void setTemplateCreateUserId(Long templateCreateUserId) {
        this.templateCreateUserId = templateCreateUserId;
    }

    public Long getTemplateDeptId() {
        return templateDeptId;
    }

    public void setTemplateDeptId(Long templateDeptId) {
        this.templateDeptId = templateDeptId;
    }
}