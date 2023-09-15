package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CrmCampaignModel {
	
	private String campaignId;
	private String leadId;
	private String campaignOwner;
	private String campaignType;
	private String campaignName;
	private String campaignStatus;
	private String startDate;
	private String endDate;
	private String expectedRevenue;
	private String budgetedCost;
	private String actualCost;
	private String expectedResponse;	
	private String numberSent;
	private String description;
	private String createdBy;
	private String createdOn;
	private String action;
	private String campaignContactId;
	private String campaignAccountId;
	private String ownerName;
	private String pageType;
	
	
	
	public String getPageType() {
		return pageType;
	}



	public void setPageType(String pageType) {
		this.pageType = pageType;
	}



	public String getOwnerName() {
		return ownerName;
	}



	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}



	public String getCampaignAccountId() {
		return campaignAccountId;
	}



	public void setCampaignAccountId(String campaignAccountId) {
		this.campaignAccountId = campaignAccountId;
	}



	public String getCampaignContactId() {
		return campaignContactId;
	}



	public void setCampaignContactId(String campaignContactId) {
		this.campaignContactId = campaignContactId;
	}



	public String getLeadId() {
		return leadId;
	}



	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}



	public String getCampaignId() {
		return campaignId;
	}



	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}



	public String getCampaignOwner() {
		return campaignOwner;
	}



	public void setCampaignOwner(String campaignOwner) {
		this.campaignOwner = campaignOwner;
	}



	public String getCampaignType() {
		return campaignType;
	}



	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}



	public String getCampaignName() {
		return campaignName;
	}



	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}



	public String getCampaignStatus() {
		return campaignStatus;
	}



	public void setCampaignStatus(String campaignStatus) {
		this.campaignStatus = campaignStatus;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public String getExpectedRevenue() {
		return expectedRevenue;
	}



	public void setExpectedRevenue(String expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}



	public String getBudgetedCost() {
		return budgetedCost;
	}



	public void setBudgetedCost(String budgetedCost) {
		this.budgetedCost = budgetedCost;
	}



	public String getActualCost() {
		return actualCost;
	}



	public void setActualCost(String actualCost) {
		this.actualCost = actualCost;
	}



	public String getExpectedResponse() {
		return expectedResponse;
	}



	public void setExpectedResponse(String expectedResponse) {
		this.expectedResponse = expectedResponse;
	}



	public String getNumberSent() {
		return numberSent;
	}



	public void setNumberSent(String numberSent) {
		this.numberSent = numberSent;
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



	public String getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
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
