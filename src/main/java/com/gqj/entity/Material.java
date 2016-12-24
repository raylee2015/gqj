package com.gqj.entity;

import com.base.entity.Base;

public class Material extends Base {
	private Long matId;

	private Long typeId;

	private String matName;

	private String matSpec;

	private String matModel;

	private Long matUnit;

	private String matRemark;

	public Long getMatId() {
		return matId;
	}

	public void setMatId(Long matId) {
		this.matId = matId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName == null ? null
				: matName.trim();
	}

	public String getMatSpec() {
		return matSpec;
	}

	public void setMatSpec(String matSpec) {
		this.matSpec = matSpec == null ? null
				: matSpec.trim();
	}

	public String getMatModel() {
		return matModel;
	}

	public void setMatModel(String matModel) {
		this.matModel = matModel == null ? null
				: matModel.trim();
	}

	public Long getMatUnit() {
		return matUnit;
	}

	public void setMatUnit(Long matUnit) {
		this.matUnit = matUnit;
	}

	public String getMatRemark() {
		return matRemark;
	}

	public void setMatRemark(String matRemark) {
		this.matRemark = matRemark == null ? null
				: matRemark.trim();
	}
}