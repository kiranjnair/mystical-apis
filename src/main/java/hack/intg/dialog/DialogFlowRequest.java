package hack.intg.dialog;

import java.util.HashMap;
import java.util.Map;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hack.dialog.common.DialogFlowConstants;
import hack.dialog.model.RequestModel;
import hack.intg.IntgRequest;

public class DialogFlowRequest implements IntgRequest {

  private RequestModel requestModel;
  private String URL = DialogFlowConstants.DIALOG_FLOW_URL;
  Map<String, String> params;
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public RequestModel getRequestModel() {
    return requestModel;
  }

  public void setRequestModel(RequestModel requestModel) {
    this.requestModel = requestModel;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public void setParams(Map<String, String> params) {
    this.params = params;
  }

  public String getURL() {
    return URL;
  }

  public void setURL(String uRL) {
    URL = uRL;
  }

  public String getBody() {
    Map<String, Object> body = new HashMap<String, Object>();
    ObjectMapper mapper = new ObjectMapper();

    body.put("q", getRequestModel().getQuery());
    body.put("lang", getRequestModel().getLang());
    body.put("sessionId", getRequestModel().getSessionId());
    body.put("timezone", getRequestModel().getTimezone());
    body.put("resetContexts", getRequestModel().getResetContexts());
    body.put("userid", getRequestModel().getUserid());
    body.put("converseType", (StringUtils.isEmpty(requestModel.getConverseType())) ? "converse"
        : getRequestModel().getConverseType());
    String json = null;
    try {
      json = mapper.writeValueAsString(body);
    } catch (JsonProcessingException e) {
      e.getMessage();
    }
    return json;


  }



}
