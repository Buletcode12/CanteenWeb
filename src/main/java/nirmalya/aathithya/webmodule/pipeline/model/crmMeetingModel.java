package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class crmMeetingModel {
	
	private String meetingId;
	private String meetingLead;
	private String meetingTitle;
	private String meetingLocation;
	private String isThisOnlineMeeting;
	private String isAllDay;
	private String meetingType;
	private String meetingFromDate;
	private String meetingFromTime;
	private String meetingToDate;
	private String meetingToTime;
	private String meetingHost;	
	private String meetingParticipants;
	private String relatedMeetingTo;
	private String isRepeat;
	private String meetingStatus;
	private String isAllDayRepeat;
	private String meetingRepeatFromDate;
	private String meetingRepeatFromTime;
	private String meetingRepeatToDate;
	private String meetingCalendarRepeatToTime;
	private String repeatType;
	private String description;
	private String createdBy;
	private String createdOn;
	private String action;
	private String participantId;
	
	private String leadName;
	private String leadId;
	private String contactName;
	private String leadOrContactName;
	private String contactId;
	private String meetingLeadId;
	private String meetingDealId;
	private String meetingContactId;
	private String meetingAccountId;
	private String ownerName;
	

	private String quoteId;
	private String soId;
	private String poId;
	private String invoiceId;
	
	
	
	
	public String getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getPoId() {
		return poId;
	}
	public void setPoId(String poId) {
		this.poId = poId;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getMeetingType() {
		return meetingType;
	}
	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public void setMeetingLeadId(String meetingLeadId) {
		this.meetingLeadId = meetingLeadId;
	}
	public String getMeetingAccountId() {
		return meetingAccountId;
	}
	public void setMeetingAccountId(String meetingAccountId) {
		this.meetingAccountId = meetingAccountId;
	}
	public String getMeetingContactId() {
		return meetingContactId;
	}
	public void setMeetingContactId(String meetingContactId) {
		this.meetingContactId = meetingContactId;
	}
	public String getMeetingDealId() {
		return meetingDealId;
	}
	public void setMeetingDealId(String meetingDealId) {
		this.meetingDealId = meetingDealId;
	}
	public String getMeetingLeadId() {
		return meetingLeadId;
	}
	public void setMeetingleadId(String meetingLeadId) {
		this.meetingLeadId = meetingLeadId;
	}
	public String getLeadName() {
		return leadName;
	}
	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getLeadOrContactName() {
		return leadOrContactName;
	}
	public void setLeadOrContactName(String leadOrContactName) {
		this.leadOrContactName = leadOrContactName;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getMeetingStatus() {
		return meetingStatus;
	}
	public void setMeetingStatus(String meetingStatus) {
		this.meetingStatus = meetingStatus;
	}
	public String getMeetingLead() {
		return meetingLead;
	}
	public void setMeetingLead(String meetingLead) {
		this.meetingLead = meetingLead;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getMeetingTitle() {
		return meetingTitle;
	}
	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
	}
	public String getMeetingLocation() {
		return meetingLocation;
	}
	public void setMeetingLocation(String meetingLocation) {
		this.meetingLocation = meetingLocation;
	}
	public String getIsThisOnlineMeeting() {
		return isThisOnlineMeeting;
	}
	public void setIsThisOnlineMeeting(String isThisOnlineMeeting) {
		this.isThisOnlineMeeting = isThisOnlineMeeting;
	}
	public String getIsAllDay() {
		return isAllDay;
	}
	public void setIsAllDay(String isAllDay) {
		this.isAllDay = isAllDay;
	}
	public String getMeetingFromDate() {
		return meetingFromDate;
	}
	public void setMeetingFromDate(String meetingFromDate) {
		this.meetingFromDate = meetingFromDate;
	}
	public String getMeetingFromTime() {
		return meetingFromTime;
	}
	public void setMeetingFromTime(String meetingFromTime) {
		this.meetingFromTime = meetingFromTime;
	}
	public String getMeetingToDate() {
		return meetingToDate;
	}
	public void setMeetingToDate(String meetingToDate) {
		this.meetingToDate = meetingToDate;
	}
	public String getMeetingToTime() {
		return meetingToTime;
	}
	public void setMeetingToTime(String meetingToTime) {
		this.meetingToTime = meetingToTime;
	}
	public String getMeetingHost() {
		return meetingHost;
	}
	public void setMeetingHost(String meetingHost) {
		this.meetingHost = meetingHost;
	}
	public String getMeetingParticipants() {
		return meetingParticipants;
	}
	public void setMeetingParticipants(String meetingParticipants) {
		this.meetingParticipants = meetingParticipants;
	}
	public String getRelatedMeetingTo() {
		return relatedMeetingTo;
	}
	public void setRelatedMeetingTo(String relatedMeetingTo) {
		this.relatedMeetingTo = relatedMeetingTo;
	}
	public String getIsRepeat() {
		return isRepeat;
	}
	public void setIsRepeat(String isRepeat) {
		this.isRepeat = isRepeat;
	}
	public String getIsAllDayRepeat() {
		return isAllDayRepeat;
	}
	public void setIsAllDayRepeat(String isAllDayRepeat) {
		this.isAllDayRepeat = isAllDayRepeat;
	}
	public String getMeetingRepeatFromDate() {
		return meetingRepeatFromDate;
	}
	public void setMeetingRepeatFromDate(String meetingRepeatFromDate) {
		this.meetingRepeatFromDate = meetingRepeatFromDate;
	}
	public String getMeetingRepeatFromTime() {
		return meetingRepeatFromTime;
	}
	public void setMeetingRepeatFromTime(String meetingRepeatFromTime) {
		this.meetingRepeatFromTime = meetingRepeatFromTime;
	}
	public String getMeetingRepeatToDate() {
		return meetingRepeatToDate;
	}
	public void setMeetingRepeatToDate(String meetingRepeatToDate) {
		this.meetingRepeatToDate = meetingRepeatToDate;
	}
	public String getMeetingCalendarRepeatToTime() {
		return meetingCalendarRepeatToTime;
	}
	public void setMeetingCalendarRepeatToTime(String meetingCalendarRepeatToTime) {
		this.meetingCalendarRepeatToTime = meetingCalendarRepeatToTime;
	}
	public String getRepeatType() {
		return repeatType;
	}
	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
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

	public String getParticipantId() {
		return participantId;
	}
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
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
