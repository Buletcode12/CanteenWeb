package nirmalya.aathithya.webmodule.purchase.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VendorNewModel {
	
	private String vendorId;
	private String salutation;
	private String vendorName;
	private String companyName;
	private String vendorDisplayName;
	private String vendorEmail;
	private String vendorPhone;
	private String vendorMobile;
	private String vendorSkype;
	private String vendorDesignation;
	private String department;
	private String webSite;	
	private String status;
	
	private String pan;
	private String currency;
	private String openingBalance;	
	private String paymentTerms;
	private String tds;
	private String gstNumber;
	private String vendorCategory;
	private String whatsAppNo;
	private String tanNo;
	private String faceBook;
	private String twitter;	

	private String country;
	private String states;
	private String city;	
	private String street1;
	private String street2;
	private String zipCode;
	private String phone;
	private String fax;
	
	private String country1;
	private String states1;
	private String city1;	
	private String street11;
	private String street21;
	private String zipCode1;
	private String phone1;
	private String fax1;
	
	private String salutation1;
	private String firstName;
	private String lastName;	
	private String emailAdd;
	private String mobile;
	
	private String beneficiaryName;
	private String bankName;	
	private String accountNo;
	private String ifsc;
	
	
	private String remarks;	
	
	private String createdDate;	
	private String createdTime;
	private String createdBy;
	private String orgName;
	private String orgDivision;
	private Double reqSent;
	private Double candidates;
	private Double closed;
	private Integer slNo;	
	
	private String gstRate;
	private String unitPrice;
	private String packages;
	private String poItmCgst;
	private String discount;
	private String poItmSgst;
	private String poItmIgst;
	private String qty;
	private String makespecific;
	private String taxblamnt;
	private String itemLineTtl;
	private String poItmHsncode;
	private String skuId;
	private String itemName;
	private String uom;
	
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getVendorDisplayName() {
		return vendorDisplayName;
	}
	public void setVendorDisplayName(String vendorDisplayName) {
		this.vendorDisplayName = vendorDisplayName;
	}
	public String getVendorEmail() {
		return vendorEmail;
	}
	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}
	public String getVendorPhone() {
		return vendorPhone;
	}
	public void setVendorPhone(String vendorPhone) {
		this.vendorPhone = vendorPhone;
	}
	public String getVendorMobile() {
		return vendorMobile;
	}
	public void setVendorMobile(String vendorMobile) {
		this.vendorMobile = vendorMobile;
	}
	public String getVendorSkype() {
		return vendorSkype;
	}
	public void setVendorSkype(String vendorSkype) {
		this.vendorSkype = vendorSkype;
	}
	public String getVendorDesignation() {
		return vendorDesignation;
	}
	public void setVendorDesignation(String vendorDesignation) {
		this.vendorDesignation = vendorDesignation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getTds() {
		return tds;
	}
	public void setTds(String tds) {
		this.tds = tds;
	}
	public String getFaceBook() {
		return faceBook;
	}
	public void setFaceBook(String faceBook) {
		this.faceBook = faceBook;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCountry1() {
		return country1;
	}
	public void setCountry1(String country1) {
		this.country1 = country1;
	}
	public String getStates1() {
		return states1;
	}
	public void setStates1(String states1) {
		this.states1 = states1;
	}
	public String getCity1() {
		return city1;
	}
	public void setCity1(String city1) {
		this.city1 = city1;
	}
	public String getStreet11() {
		return street11;
	}
	public void setStreet11(String street11) {
		this.street11 = street11;
	}
	public String getStreet21() {
		return street21;
	}
	public void setStreet21(String street21) {
		this.street21 = street21;
	}
	public String getZipCode1() {
		return zipCode1;
	}
	public void setZipCode1(String zipCode1) {
		this.zipCode1 = zipCode1;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getFax1() {
		return fax1;
	}
	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}
	public String getSalutation1() {
		return salutation1;
	}
	public void setSalutation1(String salutation1) {
		this.salutation1 = salutation1;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAdd() {
		return emailAdd;
	}
	public void setEmailAdd(String emailAdd) {
		this.emailAdd = emailAdd;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	

	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	
	public String getVendorCategory() {
		return vendorCategory;
	}
	public void setVendorCategory(String vendorCategory) {
		this.vendorCategory = vendorCategory;
	}
	public String getWhatsAppNo() {
		return whatsAppNo;
	}
	public void setWhatsAppNo(String whatsAppNo) {
		this.whatsAppNo = whatsAppNo;
	}
	public String getTanNo() {
		return tanNo;
	}
	public void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}
	
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgDivision() {
		return orgDivision;
	}
	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}
	
	public Double getReqSent() {
		return reqSent;
	}
	public void setReqSent(Double reqSent) {
		this.reqSent = reqSent;
	}
	public Double getCandidates() {
		return candidates;
	}
	public void setCandidates(Double candidates) {
		this.candidates = candidates;
	}
	public Double getClosed() {
		return closed;
	}
	public void setClosed(Double closed) {
		this.closed = closed;
	}
	
	public Integer getSlNo() {
		return slNo;
	}
	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}
	
	
	public String getGstRate() {
		return gstRate;
	}
	public void setGstRate(String gstRate) {
		this.gstRate = gstRate;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	public String getPoItmCgst() {
		return poItmCgst;
	}
	public void setPoItmCgst(String poItmCgst) {
		this.poItmCgst = poItmCgst;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getPoItmSgst() {
		return poItmSgst;
	}
	public void setPoItmSgst(String poItmSgst) {
		this.poItmSgst = poItmSgst;
	}
	public String getPoItmIgst() {
		return poItmIgst;
	}
	public void setPoItmIgst(String poItmIgst) {
		this.poItmIgst = poItmIgst;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getMakespecific() {
		return makespecific;
	}
	public void setMakespecific(String makespecific) {
		this.makespecific = makespecific;
	}
	public String getTaxblamnt() {
		return taxblamnt;
	}
	public void setTaxblamnt(String taxblamnt) {
		this.taxblamnt = taxblamnt;
	}
	public String getItemLineTtl() {
		return itemLineTtl;
	}
	public void setItemLineTtl(String itemLineTtl) {
		this.itemLineTtl = itemLineTtl;
	}
	public String getPoItmHsncode() {
		return poItmHsncode;
	}
	public void setPoItmHsncode(String poItmHsncode) {
		this.poItmHsncode = poItmHsncode;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
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
