package nirmalya.aathithya.webmodule.asset.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.training.model.ManageTrainingDocumentModel;

public class AssetViewMasterModel {
	private String edate;
	private String pdate;
	private String sdate;
	private String assetId;
	private String assetcat;
	private String assetname;
	private String assetsubcat;
	private String assettype;
	private String lifespan;
	private String purchaseno;
	private String qcId;
	private String remark;
	private String warrantyid;
	private String serviceprovider;
	private String insurancename;
	private String insuranceno;
	private String isdate;
	private String iedate;
	private String wstatus;
	private String category;
	private String fileName;
	private String documentURL;
	private String documentFileBase;
	private String createdBy;
	private String organization;
	private String orgDivision;

	private List<ManageTrainingDocumentModel> documentList;

	public AssetViewMasterModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocumentFileBase() {
		return documentFileBase;
	}

	public void setDocumentFileBase(String documentFileBase) {
		this.documentFileBase = documentFileBase;
	}

	public String getWstatus() {
		return wstatus;
	}

	public void setWstatus(String wstatus) {
		this.wstatus = wstatus;
	}

	public String getInsuranceno() {
		return insuranceno;
	}

	public void setInsuranceno(String insuranceno) {
		this.insuranceno = insuranceno;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInsurancename() {
		return insurancename;
	}

	public void setInsurancename(String insurancename) {
		this.insurancename = insurancename;
	}

	public String getIsdate() {
		return isdate;
	}

	public void setIsdate(String isdate) {
		this.isdate = isdate;
	}

	public String getIedate() {
		return iedate;
	}

	public void setIedate(String iedate) {
		this.iedate = iedate;
	}

	public String getQcId() {
		return qcId;
	}

	public void setQcId(String qcId) {
		this.qcId = qcId;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetcat() {
		return assetcat;
	}

	public void setAssetcat(String assetcat) {
		this.assetcat = assetcat;
	}

	public String getAssetname() {
		return assetname;
	}

	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}

	public String getAssetsubcat() {
		return assetsubcat;
	}

	public void setAssetsubcat(String assetsubcat) {
		this.assetsubcat = assetsubcat;
	}

	public String getAssettype() {
		return assettype;
	}

	public void setAssettype(String assettype) {
		this.assettype = assettype;
	}

	public String getLifespan() {
		return lifespan;
	}

	public void setLifespan(String lifespan) {
		this.lifespan = lifespan;
	}

	public String getPurchaseno() {
		return purchaseno;
	}

	public void setPurchaseno(String purchaseno) {
		this.purchaseno = purchaseno;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWarrantyid() {
		return warrantyid;
	}

	public void setWarrantyid(String warrantyid) {
		this.warrantyid = warrantyid;
	}

	public String getServiceprovider() {
		return serviceprovider;
	}

	public void setServiceprovider(String serviceprovider) {
		this.serviceprovider = serviceprovider;
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

	public List<ManageTrainingDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<ManageTrainingDocumentModel> documentList) {
		this.documentList = documentList;
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
