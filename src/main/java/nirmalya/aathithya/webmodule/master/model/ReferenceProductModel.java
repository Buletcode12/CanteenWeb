package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReferenceProductModel {

	private String brandId;
	private String brandName;
	private String brandOrder;
	private String brandCode;
	private String brandDesc;
	private String brandStatus;
	private String brandCreatedBy;
	private String brandModifiedBy;
	private String brandCreatedOn;
	private String brandUpdatedOn;

	// product Type

	private String productId;
	private String productOrder;
	private String productName;
	private String productCode;
	private String productDesc;
	private String productStatus;
	private String productCreatedBy;
	private String productModifiedBy;
	private String productCreatedOn;
	private String productUpdatedOn;

	// variation Type

	private String variationId;
	private String variationName;
	private String variationDesc;
	private String variationStatus;
	private String variationCreatedBy;
	private String variationModifiedBy;
	private String variationCreatedOn;
	private String variationUpdatedOn;

	public ReferenceProductModel() {
		super(); 
	}

	public ReferenceProductModel(String brandOrder, String brandCode, String brandName, 
			String brandStatus, String brandCreatedBy) {
		super();
		this.brandName = brandName;
		this.brandOrder = brandOrder;
		this.brandCode = brandCode; 
		this.brandStatus = brandStatus;
		this.brandCreatedBy = brandCreatedBy;
	}

	public ReferenceProductModel(String productName, String productDesc, String productStatus,
			String productCreatedBy) {
		super();
		this.productName = productName;
		this.productDesc = productDesc;
		this.productStatus = productStatus;
		this.productCreatedBy = productCreatedBy;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandOrder() {
		return brandOrder;
	}

	public void setBrandOrder(String brandOrder) {
		this.brandOrder = brandOrder;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandDesc() {
		return brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public String getBrandStatus() {
		return brandStatus;
	}

	public void setBrandStatus(String brandStatus) {
		this.brandStatus = brandStatus;
	}

	public String getBrandCreatedBy() {
		return brandCreatedBy;
	}

	public void setBrandCreatedBy(String brandCreatedBy) {
		this.brandCreatedBy = brandCreatedBy;
	}

	public String getBrandModifiedBy() {
		return brandModifiedBy;
	}

	public void setBrandModifiedBy(String brandModifiedBy) {
		this.brandModifiedBy = brandModifiedBy;
	}

	public String getBrandCreatedOn() {
		return brandCreatedOn;
	}

	public void setBrandCreatedOn(String brandCreatedOn) {
		this.brandCreatedOn = brandCreatedOn;
	}

	public String getBrandUpdatedOn() {
		return brandUpdatedOn;
	}

	public void setBrandUpdatedOn(String brandUpdatedOn) {
		this.brandUpdatedOn = brandUpdatedOn;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductOrder() {
		return productOrder;
	}

	public void setProductOrder(String productOrder) {
		this.productOrder = productOrder;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductCreatedBy() {
		return productCreatedBy;
	}

	public void setProductCreatedBy(String productCreatedBy) {
		this.productCreatedBy = productCreatedBy;
	}

	public String getProductModifiedBy() {
		return productModifiedBy;
	}

	public void setProductModifiedBy(String productModifiedBy) {
		this.productModifiedBy = productModifiedBy;
	}

	public String getProductCreatedOn() {
		return productCreatedOn;
	}

	public void setProductCreatedOn(String productCreatedOn) {
		this.productCreatedOn = productCreatedOn;
	}

	public String getProductUpdatedOn() {
		return productUpdatedOn;
	}

	public void setProductUpdatedOn(String productUpdatedOn) {
		this.productUpdatedOn = productUpdatedOn;
	}

	public String getVariationId() {
		return variationId;
	}

	public void setVariationId(String variationId) {
		this.variationId = variationId;
	}

	public String getVariationName() {
		return variationName;
	}

	public void setVariationName(String variationName) {
		this.variationName = variationName;
	}

	public String getVariationDesc() {
		return variationDesc;
	}

	public void setVariationDesc(String variationDesc) {
		this.variationDesc = variationDesc;
	}

	public String getVariationStatus() {
		return variationStatus;
	}

	public void setVariationStatus(String variationStatus) {
		this.variationStatus = variationStatus;
	}

	public String getVariationCreatedBy() {
		return variationCreatedBy;
	}

	public void setVariationCreatedBy(String variationCreatedBy) {
		this.variationCreatedBy = variationCreatedBy;
	}

	public String getVariationModifiedBy() {
		return variationModifiedBy;
	}

	public void setVariationModifiedBy(String variationModifiedBy) {
		this.variationModifiedBy = variationModifiedBy;
	}

	public String getVariationCreatedOn() {
		return variationCreatedOn;
	}

	public void setVariationCreatedOn(String variationCreatedOn) {
		this.variationCreatedOn = variationCreatedOn;
	}

	public String getVariationUpdatedOn() {
		return variationUpdatedOn;
	}

	public void setVariationUpdatedOn(String variationUpdatedOn) {
		this.variationUpdatedOn = variationUpdatedOn;
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
