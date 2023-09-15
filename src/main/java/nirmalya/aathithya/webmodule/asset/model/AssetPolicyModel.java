package nirmalya.aathithya.webmodule.asset.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AssetPolicyModel {

	private String policyid;
	private String catid;
	private String assetsubcat;
	private String frequency;
	private String policyName;
	private String priority;
	private String description;
	

	private String createdBy;
	private String organization;
	private String orgDivision;

	public AssetPolicyModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public String getPolicyid() {
		return policyid;
	}


	public void setPolicyid(String policyid) {
		this.policyid = policyid;
	}


	public String getCatid() {
		return catid;
	}


	public void setCatid(String catid) {
		this.catid = catid;
	}


	public String getAssetsubcat() {
		return assetsubcat;
	}


	public void setAssetsubcat(String assetsubcat) {
		this.assetsubcat = assetsubcat;
	}


	public String getFrequency() {
		return frequency;
	}


	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}


	public String getPolicyName() {
		return policyName;
	}


	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}

	@Override
	public String toString() {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr;
		try {
			jsonStr = mapperObj.writeValueAsString(this);
		} catch (IOException ex) {

			jsonStr = ex.toString();
		}
		return jsonStr;
	}
}
