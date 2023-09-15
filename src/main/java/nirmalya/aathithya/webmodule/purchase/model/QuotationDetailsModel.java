package nirmalya.aathithya.webmodule.purchase.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

public class QuotationDetailsModel {

	
	private String vendorId;
	private String vendorName;
	private String quotationId;
	private String poId;
	private String qutValidDate; 
	private String reference;
	private String paymentTermId;
	private String carrierId;
	private String qutDescription;
	private String itemId;
	private Integer slNo;
	private String itemName;
	private String hsnCode;
	private Double quantity;
	private Double unitPrice;
	private Double discount;
	private Double gstRate;
	private Double lineTotal;
	private Double subTotal;
	private Double qIGST;
	private Double qCGST;
	private Double qSGST;
	private Double grandTotal;
	private Boolean taxType;
	private String sku;
	private Double itemIgst;
	private Double itemCgst;
	private Double itemSgst;
	private String poActive;
	private Double adjustment;
	private String tcsId;
	private Double tcsAmount;
	private String tcs;
    private String terms;
	private String qutActive; 
	private String createdBy;
	private String updatedOn;
    private String organization; 
	private String orgDivision;
	private String shippingDetails;
	private String orgDetails;
	private String custDetails;
	private String unit;
	private String unitName;
	private Double taxableAmt;
	private Double totalTaxableAmt;
	private Double pendingQuantity;
	private Double receivingQuantity;
	private Double receivedQuantity;
	private String description;
	private String grandTotalInWords;
	private String custGSTNo;
	private String invoiceStatus;
	private String qutNo;
	private String vcountry;
	private String vstate;
	private String qutStatus;
	private String vcity;
	private String paymentModeId; 
	private String paymentModeName; 
	private String creditLimit;
	private String approveStatus;
	private String brandName;
	private String project;
	private String projectName;
	List<InventoryVendorDocumentModel> documentList;
	
	
	public QuotationDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getVendorId() {
		return vendorId;
	}


	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}


	public String getVendorName() {
		return vendorName;
	}


	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	public String getQuotationId() {
		return quotationId;
	}


	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}


	public String getPoId() {
		return poId;
	}


	public void setPoId(String poId) {
		this.poId = poId;
	}


	public String getQutValidDate() {
		return qutValidDate;
	}


	public void setQutValidDate(String qutValidDate) {
		this.qutValidDate = qutValidDate;
	}


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}


	public String getPaymentTermId() {
		return paymentTermId;
	}


	public void setPaymentTermId(String paymentTermId) {
		this.paymentTermId = paymentTermId;
	}


	public String getCarrierId() {
		return carrierId;
	}


	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}


	public String getQutDescription() {
		return qutDescription;
	}


	public void setQutDescription(String qutDescription) {
		this.qutDescription = qutDescription;
	}


	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public Integer getSlNo() {
		return slNo;
	}


	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getHsnCode() {
		return hsnCode;
	}


	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}


	public Double getQuantity() {
		return quantity;
	}


	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}


	public Double getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}


	public Double getDiscount() {
		return discount;
	}


	public void setDiscount(Double discount) {
		this.discount = discount;
	}


	public Double getGstRate() {
		return gstRate;
	}


	public void setGstRate(Double gstRate) {
		this.gstRate = gstRate;
	}


	public Double getLineTotal() {
		return lineTotal;
	}


	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}


	public Double getSubTotal() {
		return subTotal;
	}


	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}


	public Double getqIGST() {
		return qIGST;
	}


	public void setqIGST(Double qIGST) {
		this.qIGST = qIGST;
	}


	public Double getqCGST() {
		return qCGST;
	}


	public void setqCGST(Double qCGST) {
		this.qCGST = qCGST;
	}


	public Double getqSGST() {
		return qSGST;
	}


	public void setqSGST(Double qSGST) {
		this.qSGST = qSGST;
	}


	public Double getGrandTotal() {
		return grandTotal;
	}


	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}


	public Boolean getTaxType() {
		return taxType;
	}


	public void setTaxType(Boolean taxType) {
		this.taxType = taxType;
	}


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}


	public Double getItemIgst() {
		return itemIgst;
	}


	public void setItemIgst(Double itemIgst) {
		this.itemIgst = itemIgst;
	}


	public Double getItemCgst() {
		return itemCgst;
	}


	public void setItemCgst(Double itemCgst) {
		this.itemCgst = itemCgst;
	}


	public Double getItemSgst() {
		return itemSgst;
	}


	public void setItemSgst(Double itemSgst) {
		this.itemSgst = itemSgst;
	}


	public String getPoActive() {
		return poActive;
	}


	public void setPoActive(String poActive) {
		this.poActive = poActive;
	}


	public Double getAdjustment() {
		return adjustment;
	}


	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}


	public String getTcsId() {
		return tcsId;
	}


	public void setTcsId(String tcsId) {
		this.tcsId = tcsId;
	}


	public Double getTcsAmount() {
		return tcsAmount;
	}


	public void setTcsAmount(Double tcsAmount) {
		this.tcsAmount = tcsAmount;
	}


	public String getTcs() {
		return tcs;
	}


	public void setTcs(String tcs) {
		this.tcs = tcs;
	}


	public String getTerms() {
		return terms;
	}


	public void setTerms(String terms) {
		this.terms = terms;
	}


	public String getQutActive() {
		return qutActive;
	}


	public void setQutActive(String qutActive) {
		this.qutActive = qutActive;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
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


	public String getShippingDetails() {
		return shippingDetails;
	}


	public void setShippingDetails(String shippingDetails) {
		this.shippingDetails = shippingDetails;
	}


	public String getOrgDetails() {
		return orgDetails;
	}


	public void setOrgDetails(String orgDetails) {
		this.orgDetails = orgDetails;
	}


	public String getCustDetails() {
		return custDetails;
	}


	public void setCustDetails(String custDetails) {
		this.custDetails = custDetails;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getUnitName() {
		return unitName;
	}


	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public Double getTaxableAmt() {
		return taxableAmt;
	}


	public void setTaxableAmt(Double taxableAmt) {
		this.taxableAmt = taxableAmt;
	}


	public Double getTotalTaxableAmt() {
		return totalTaxableAmt;
	}


	public void setTotalTaxableAmt(Double totalTaxableAmt) {
		this.totalTaxableAmt = totalTaxableAmt;
	}


	public Double getPendingQuantity() {
		return pendingQuantity;
	}


	public void setPendingQuantity(Double pendingQuantity) {
		this.pendingQuantity = pendingQuantity;
	}


	public Double getReceivingQuantity() {
		return receivingQuantity;
	}


	public void setReceivingQuantity(Double receivingQuantity) {
		this.receivingQuantity = receivingQuantity;
	}


	public Double getReceivedQuantity() {
		return receivedQuantity;
	}


	public void setReceivedQuantity(Double receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getGrandTotalInWords() {
		return grandTotalInWords;
	}


	public void setGrandTotalInWords(String grandTotalInWords) {
		this.grandTotalInWords = grandTotalInWords;
	}


	public String getCustGSTNo() {
		return custGSTNo;
	}


	public void setCustGSTNo(String custGSTNo) {
		this.custGSTNo = custGSTNo;
	}


	public String getInvoiceStatus() {
		return invoiceStatus;
	}


	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}


	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}


	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
	}


	public String getQutNo() {
		return qutNo;
	}


	public void setQutNo(String qutNo) {
		this.qutNo = qutNo;
	}
	
	public String getVcountry() {
		return vcountry;
	}


	public void setVcountry(String vcountry) {
		this.vcountry = vcountry;
	}


	public String getVstate() {
		return vstate;
	}


	public void setVstate(String vstate) {
		this.vstate = vstate;
	}


	public String getQutStatus() {
		return qutStatus;
	}


	public void setQutStatus(String qutStatus) {
		this.qutStatus = qutStatus;
	}


	public String getVcity() {
		return vcity;
	}


	public void setVcity(String vcity) {
		this.vcity = vcity;
	}


	public String getPaymentModeId() {
		return paymentModeId;
	}


	public void setPaymentModeId(String paymentModeId) {
		this.paymentModeId = paymentModeId;
	}


	public String getPaymentModeName() {
		return paymentModeName;
	}


	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}


	public String getCreditLimit() {
		return creditLimit;
	}


	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}


	public String getApproveStatus() {
		return approveStatus;
	}


	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}


	public String getBrandName() {
		return brandName;
	}


	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public String getProject() {
		return project;
	}


	public void setProject(String project) {
		this.project = project;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
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