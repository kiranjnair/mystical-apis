package hack.intg.conversation;

import hack.intg.IntgReply;
import hack.model.converse.ResponseModel;

public class ConversationReply implements IntgReply {
	private ResponseModel responseModel;

	public ResponseModel getResponseModel() {
		return responseModel;
	}

	public void setResponseModel(ResponseModel responseModel) {
		this.responseModel = responseModel;
	}

	@Override
	public String toString() {
		return "ConversationReply [responseModel=" + responseModel + "]";
	}



}
