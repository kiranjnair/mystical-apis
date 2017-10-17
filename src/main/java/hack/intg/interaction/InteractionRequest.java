package hack.intg.interaction;

import java.util.HashMap;
import java.util.Map;

public class InteractionRequest implements IntgRequest {
	private String 	rxcuis;
	private String url = "https://rxnav.nlm.nih.gov/REST/interaction/list.json";
	Map<String,String> params;

	public String getRxcuis() {
		return rxcuis;
	}

	public void setRxcuis(String rxcuis) {
		this.rxcuis = rxcuis;
	}

	public String getUrl() {
		return url+"?rxcuis="+rxcuis;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Map<String,String> getParams() {
		params = new HashMap<String,String>(); 
		if(rxcuis!=null) {
			params.put("rxcuis", rxcuis);
		}
		return params;
	}
	
	
	

}
