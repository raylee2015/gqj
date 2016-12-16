package com.gqj.entity;

import com.base.entity.Base;

public class Position extends Base {
    private Long posId;

    private Long storeId;

    private String posName;

    private Long posDeptId;

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName == null ? null : posName.trim();
    }

    public Long getPosDeptId() {
        return posDeptId;
    }

    public void setPosDeptId(Long posDeptId) {
        this.posDeptId = posDeptId;
    }
}