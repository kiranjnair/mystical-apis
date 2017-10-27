package hack.model.converse;

public class ContextParameters {

	private String recurrence;
	private String date;
	private String time_original;
	private String date_original;
	private String medicine_original;
	private String medicine;
	private String time;
	private String recurrence_original;	
	private String myaction;

	public String getRecurrence() {
		return recurrence;
	}
	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime_original() {
		return time_original;
	}
	public void setTime_original(String time_original) {
		this.time_original = time_original;
	}
	public String getDate_original() {
		return date_original;
	}
	public void setDate_original(String date_original) {
		this.date_original = date_original;
	}
	public String getMedicine_original() {
		return medicine_original;
	}
	public void setMedicine_original(String medicine_original) {
		this.medicine_original = medicine_original;
	}
	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRecurrence_original() {
		return recurrence_original;
	}
	public void setRecurrence_original(String recurrence_original) {
		this.recurrence_original = recurrence_original;
	}
	public String getMyaction() {
		return myaction;
	}
	public void setMyaction(String myaction) {
		this.myaction = myaction;
	}
	@Override
	public String toString() {
		return "ContextParameters [recurrence=" + recurrence + ", date=" + date + ", time_original=" + time_original
				+ ", date_original=" + date_original + ", medicine_original=" + medicine_original + ", medicine="
				+ medicine + ", time=" + time + ", recurrence_original=" + recurrence_original + ", myaction="
				+ myaction + "]";
	}
	
	
	
}
