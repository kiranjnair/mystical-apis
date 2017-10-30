package hack.dialog.model;

public class RequestModel {

  private String query;
  private String lang;
  private String sessionId;
  private String timezone;
  private String resetContexts;
  private String converseType;
  private String userid = "bob";



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

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getConverseType() {
    return converseType;
  }

  public void setConverseType(String converseType) {
    this.converseType = converseType;
  }

  @Override
  public String toString() {
    return "RequestModel [query=" + query + ", lang=" + lang + ", sessionId=" + sessionId
        + ", timezone=" + timezone + ", resetContexts=" + resetContexts + ", converseType="
        + converseType + ", userid=" + userid + "]";
  }
  
  

}
