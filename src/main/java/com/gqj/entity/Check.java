package com.gqj.entity;

import java.util.Date;

public class Check {
    private Long checkId;

    private Long checkDeptId;

    private String checkCode;

    private Long checkStatus;

    private Long checkCreateUserId;

    private Date checkCreateDate;

    private String planRemark;

    private String checkCreateUserName;

    private String checkDeptName;

    private String checkStatusName;

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Long getCheckDeptId() {
        return checkDeptId;
    }

    public void setCheckDeptId(Long checkDeptId) {
        this.checkDeptId = checkDeptId;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode == null ? null : checkCode.trim();
    }

    public Long getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Long checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Long getCheckCreateUserId() {
        return checkCreateUserId;
    }

    public void setCheckCreateUserId(Long checkCreateUserId) {
        this.checkCreateUserId = checkCreateUserId;
    }

    public Date getCheckCreateDate() {
        return checkCreateDate;
    }

    public void setCheckCreateDate(Date checkCreateDate) {
        this.checkCreateDate = checkCreateDate;
    }

    public String getPlanRemark() {
        return planRemark;
    }

    public void setPlanRemark(String planRemark) {
        this.planRemark = planRemark == null ? null : planRemark.trim();
    }

    public String getCheckCreateUserName() {
        return checkCreateUserName;
    }

    public void setCheckCreateUserName(String checkCreateUserName) {
        this.checkCreateUserName = checkCreateUserName == null ? null : checkCreateUserName.trim();
    }

    public String getCheckDeptName() {
        return checkDeptName;
    }

    public void setCheckDeptName(String checkDeptName) {
        this.checkDeptName = checkDeptName == null ? null : checkDeptName.trim();
    }

    public String getCheckStatusName() {
        return checkStatusName;
    }

    public void setCheckStatusName(String checkStatusName) {
        this.checkStatusName = checkStatusName == null ? null : checkStatusName.trim();
    }
}