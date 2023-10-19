package nirmalya.aathithya.webmodule.canteen.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AssignComboModel {
     
	 private String weakendId;
	 private String weakendName;
	 
	 private List<AssignComboItem> data;
		    	     
	    
	public String getWeakendId() {
			return weakendId;
		}
	
		public void setWeakendId(String weakendId) {
			this.weakendId = weakendId;
		}
       
		public String getWeakendName() {
			return weakendName;
		}

		public void setWeakendName(String weakendName) {
			this.weakendName = weakendName;
		}

		public List<AssignComboItem> getData() {
			return data;
		}

		public void setData(List<AssignComboItem> data) {
			this.data = data;
		}

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
