package hack.model.converse;

public class RequestModel {
	private String query;
	private String lang;
	private String sessionId;
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
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
	@Override
	public String toString() {
		return "RequestModel [query=" + query + ", lang=" + lang + ", sessionId=" + sessionId + "]";
	}
	
	
}
