package nirmalya.aathithya.webmodule.recruitment.model;

import java.util.List;

public class CandidateApplyRequisitionModel {

	private List<String> candidateId;
	private List<String> requisitionId;
	private String createdBy;
	private String requId;
	private String candId;
	
	public List<String> getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(List<String> candidateId) {
		this.candidateId = candidateId;
	}
	public List<String> getRequisitionId() {
		return requisitionId;
	}
	public void setRequisitionId(List<String> requisitionId) {
		this.requisitionId = requisitionId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getRequId() {
		return requId;
	}
	public void setRequId(String requId) {
		this.requId = requId;
	}
	public String getCandId() {
		return candId;
	}
	public void setCandId(String candId) {
		this.candId = candId;
	}
	
}
