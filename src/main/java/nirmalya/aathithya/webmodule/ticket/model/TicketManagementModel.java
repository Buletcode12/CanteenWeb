package nirmalya.aathithya.webmodule.ticket.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;



public class TicketManagementModel {
	
	private String ticketId;
	private String empId;
	private String date;
	private String empName;
	private String dept;
	private String ticketType;
	private String ticketCategory;
	private String ticketSubCategory;
	private String ticketPriority;
	private String description;
	private String locType;
	private String latitude;
	private String longitude;
	private String address;
	private String empAddress;
	
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	
	private List<TicketDocumentManagementModel> documentList;

	public TicketManagementModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public String getTicketId() {
		return ticketId;
	}



	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}



	public String getEmpId() {
		return empId;
	}



	public void setEmpId(String empId) {
		this.empId = empId;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getEmpName() {
		return empName;
	}



	public void setEmpName(String empName) {
		this.empName = empName;
	}



	public String getDept() {
		return dept;
	}



	public void setDept(String dept) {
		this.dept = dept;
	}



	public String getTicketType() {
		return ticketType;
	}



	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}



	public String getTicketCategory() {
		return ticketCategory;
	}



	public void setTicketCategory(String ticketCategory) {
		this.ticketCategory = ticketCategory;
	}



	public String getTicketSubCategory() {
		return ticketSubCategory;
	}



	public void setTicketSubCategory(String ticketSubCategory) {
		this.ticketSubCategory = ticketSubCategory;
	}



	public String getTicketPriority() {
		return ticketPriority;
	}



	public void setTicketPriority(String ticketPriority) {
		this.ticketPriority = ticketPriority;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getLocType() {
		return locType;
	}



	public void setLocType(String locType) {
		this.locType = locType;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getEmpAddress() {
		return empAddress;
	}



	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}



	public List<TicketDocumentManagementModel> getDocumentList() {
		return documentList;
	}



	public void setDocumentList(List<TicketDocumentManagementModel> documentList) {
		this.documentList = documentList;
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
