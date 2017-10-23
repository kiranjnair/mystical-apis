package hack.model.converse;

public class RequestModel {
	//private String v;
	private String q;
	private String lang;
	private String sessionId;
	private String timezone;
	private String resetContexts;
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getResetContexts() {
		return resetContexts;
	}
	public void setResetContexts(String resetContexts) {
		this.resetContexts = resetContexts;
	}
	@Override
	public String toString() {
		return "RequestModel [q=" + q + ", lang=" + lang + ", sessionId=" + sessionId + ", timezone=" + timezone
				+ ", resetContexts=" + resetContexts + "]";
	}

}
