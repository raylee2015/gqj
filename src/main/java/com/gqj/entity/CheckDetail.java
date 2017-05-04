package com.gqj.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CheckDetail {
    private Long detailId;

    private String checkStatus;

    private Long toolId;

    private String toolCode;

    private Long batchId;

    private String toolBox;

    private Long toolStatus;

    private Long toolDeptId;

    private String toolRemark;

    private Date toolTestDate;

    private Date toolRejectDate;

    private BigDecimal toolTestDateCircle;

    private Date toolNextTestDate;

    private Date toolManufactureDate;

    private Date toolPurchaseDate;

    private Long storeId;

    private String storeName;

    private Long posId;

    private String posName;

    private Long baseToolId;

    private String baseToolName;

    private String baseToolModel;

    private String baseToolSpec;

    private Long baseToolEarthWire;

    private Long baseToolTypeId;

    private String baseToolTypeName;

    private Long baseToolManufacturerId;

    private String baseToolManufacturerName;

    private String toolDeptName;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode == null ? null : toolCode.trim();
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getToolBox() {
        return toolBox;
    }

    public void setToolBox(String toolBox) {
        this.toolBox = toolBox == null ? null : toolBox.trim();
    }

    public Long getToolStatus() {
        return toolStatus;
    }

    public void setToolStatus(Long toolStatus) {
        this.toolStatus = toolStatus;
    }

    public Long getToolDeptId() {
        return toolDeptId;
    }

    public void setToolDeptId(Long toolDeptId) {
        this.toolDeptId = toolDeptId;
    }

    public String getToolRemark() {
        return toolRemark;
    }

    public void setToolRemark(String toolRemark) {
        this.toolRemark = toolRemark == null ? null : toolRemark.trim();
    }

    public Date getToolTestDate() {
        return toolTestDate;
    }

    public void setToolTestDate(Date toolTestDate) {
        this.toolTestDate = toolTestDate;
    }

    public Date getToolRejectDate() {
        return toolRejectDate;
    }

    public void setToolRejectDate(Date toolRejectDate) {
        this.toolRejectDate = toolRejectDate;
    }

    public BigDecimal getToolTestDateCircle() {
        return toolTestDateCircle;
    }

    public void setToolTestDateCircle(BigDecimal toolTestDateCircle) {
        this.toolTestDateCircle = toolTestDateCircle;
    }

    public Date getToolNextTestDate() {
        return toolNextTestDate;
    }

    public void setToolNextTestDate(Date toolNextTestDate) {
        this.toolNextTestDate = toolNextTestDate;
    }

    public Date getToolManufactureDate() {
        return toolManufactureDate;
    }

    public void setToolManufactureDate(Date toolManufactureDate) {
        this.toolManufactureDate = toolManufactureDate;
    }

    public Date getToolPurchaseDate() {
        return toolPurchaseDate;
    }

    public void setToolPurchaseDate(Date toolPurchaseDate) {
        this.toolPurchaseDate = toolPurchaseDate;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName == null ? null : posName.trim();
    }

    public Long getBaseToolId() {
        return baseToolId;
    }

    public void setBaseToolId(Long baseToolId) {
        this.baseToolId = baseToolId;
    }

    public String getBaseToolName() {
        return baseToolName;
    }

    public void setBaseToolName(String baseToolName) {
        this.baseToolName = baseToolName == null ? null : baseToolName.trim();
    }

    public String getBaseToolModel() {
        return baseToolModel;
    }

    public void setBaseToolModel(String baseToolModel) {
        this.baseToolModel = baseToolModel == null ? null : baseToolModel.trim();
    }

    public String getBaseToolSpec() {
        return baseToolSpec;
    }

    public void setBaseToolSpec(String baseToolSpec) {
        this.baseToolSpec = baseToolSpec == null ? null : baseToolSpec.trim();
    }

    public Long getBaseToolEarthWire() {
        return baseToolEarthWire;
    }

    public void setBaseToolEarthWire(Long baseToolEarthWire) {
        this.baseToolEarthWire = baseToolEarthWire;
    }

    public Long getBaseToolTypeId() {
        return baseToolTypeId;
    }

    public void setBaseToolTypeId(Long baseToolTypeId) {
        this.baseToolTypeId = baseToolTypeId;
    }

    public String getBaseToolTypeName() {
        return baseToolTypeName;
    }

    public void setBaseToolTypeName(String baseToolTypeName) {
        this.baseToolTypeName = baseToolTypeName == null ? null : baseToolTypeName.trim();
    }

    public Long getBaseToolManufacturerId() {
        return baseToolManufacturerId;
    }

    public void setBaseToolManufacturerId(Long baseToolManufacturerId) {
        this.baseToolManufacturerId = baseToolManufacturerId;
    }

    public String getBaseToolManufacturerName() {
        return baseToolManufacturerName;
    }

    public void setBaseToolManufacturerName(String baseToolManufacturerName) {
        this.baseToolManufacturerName = baseToolManufacturerName == null ? null : baseToolManufacturerName.trim();
    }

    public String getToolDeptName() {
        return toolDeptName;
    }

    public void setToolDeptName(String toolDeptName) {
        this.toolDeptName = toolDeptName == null ? null : toolDeptName.trim();
    }
}