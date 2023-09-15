package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageLeadgerModel {
	
	private String leadgerId;
	private String ledgername;
	private String undergroup;
	private String lname;
	private String address;
	private String leadgercountry;
	private String leadgerstate;
	private String pincode;
	private String panitn;
	private String openinbalanceDate;
	private String groupId;
	private String groupName;
	
	public ManageLeadgerModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public String getLeadgerId() {
		return leadgerId;
	}


	public void setLeadgerId(String leadgerId) {
		this.leadgerId = leadgerId;
	}


	public String getLedgername() {
		return ledgername;
	}


	public void setLedgername(String ledgername) {
		this.ledgername = ledgername;
	}


	public String getUndergroup() {
		return undergroup;
	}


	public void setUndergroup(String undergroup) {
		this.undergroup = undergroup;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getLeadgercountry() {
		return leadgercountry;
	}


	public void setLeadgercountry(String leadgercountry) {
		this.leadgercountry = leadgercountry;
	}


	public String getLeadgerstate() {
		return leadgerstate;
	}


	public void setLeadgerstate(String leadgerstate) {
		this.leadgerstate = leadgerstate;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


	public String getPanitn() {
		return panitn;
	}


	public void setPanitn(String panitn) {
		this.panitn = panitn;
	}


	public String getOpeninbalanceDate() {
		return openinbalanceDate;
	}


	public void setOpeninbalanceDate(String openinbalanceDate) {
		this.openinbalanceDate = openinbalanceDate;
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
