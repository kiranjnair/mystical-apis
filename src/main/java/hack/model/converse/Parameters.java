package hack.model.converse;

public class Parameters {
	public String date;
	public String medicine;
	public String recurrence;
	public String time;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public String getRecurrence() {
		return recurrence;
	}
	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Parameters [date=" + date + ", medicine=" + medicine + ", recurrence=" + recurrence + ", time=" + time
				+ "]";
	}
	
	

}
