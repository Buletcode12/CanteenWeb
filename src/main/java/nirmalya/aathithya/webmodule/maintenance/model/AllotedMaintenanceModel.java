package nirmalya.aathithya.webmodule.maintenance.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AllotedMaintenanceModel {

	private String allocid;
	private String policyName;
	private String policyid;
	private String status;
	private String priority;
	private String description;
	

	private String createdBy;
	private String organization;
	private String orgDivision;

	public AllotedMaintenanceModel() {
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



	public String getAllocid() {
		return allocid;
	}


	public void setAllocid(String allocid) {
		this.allocid = allocid;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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
