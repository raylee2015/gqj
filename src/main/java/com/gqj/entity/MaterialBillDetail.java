package com.gqj.entity;

import java.math.BigDecimal;

public class MaterialBillDetail {
    private Long detailId;

    private Long baseToolId;

    private Long billId;

    private BigDecimal detailBillAmount;

    private String detailRemark;

    private Long posId;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getBaseToolId() {
        return baseToolId;
    }

    public void setBaseToolId(Long baseToolId) {
        this.baseToolId = baseToolId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public BigDecimal getDetailBillAmount() {
        return detailBillAmount;
    }

    public void setDetailBillAmount(BigDecimal detailBillAmount) {
        this.detailBillAmount = detailBillAmount;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark == null ? null : detailRemark.trim();
    }

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }
}