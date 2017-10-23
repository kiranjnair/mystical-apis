package hack.intg.conversation;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hack.intg.IntgRequest;
import hack.model.converse.RequestModel;

public class ConversationRequest implements IntgRequest {
	private RequestModel 	rModel;
	private String url = "https://api.api.ai/v1/";
	Map<String,String> params;

	

	public RequestModel getrModel() {
		return rModel;
	}

	public void setrModel(RequestModel rModel) {
		this.rModel = rModel;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getBody() {
		Map <String, Object> body = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		body.put("responseModel", getrModel());
		String json=null;
		try {
			json = mapper.writeValueAsString(body);
		} catch (JsonProcessingException e) {
			e.getMessage();
		}
		return json;
		
		
	}
	
	

}
