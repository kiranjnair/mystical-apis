package hack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "schedule")
public class Schedule {

	@Id
	private String id;
	private String useruid;
	private String rxnormId;
	private String drugname;
	private String time;
	private String ampm;
	private String freqency;
	private String date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getUseruid() {
		return useruid;
	}
	public void setUseruid(String useruid) {
		this.useruid = useruid;
	}
	
	public String getRxnormId() {
		return rxnormId;
	}
	public void setRxnormId(String rxnormId) {
		this.rxnormId = rxnormId;
	}
	public String getDrugname() {
		return drugname;
	}
	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAmpm() {
		return ampm;
	}
	public void setAmpm(String ampm) {
		this.ampm = ampm;
	}
	public String getFreqency() {
		return freqency;
	}
	public void setFreqency(String freqency) {
		this.freqency = freqency;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", useruid=" + useruid + ", rxnormId=" + rxnormId + ", drugname=" + drugname
				+ ", time=" + time + ", ampm=" + ampm + ", freqency=" + freqency + "]";
	}

}
	

	

    