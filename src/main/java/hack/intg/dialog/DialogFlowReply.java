package hack.intg.dialog;

import hack.intg.IntgReply;
import hack.dialog.model.ResponseModel;

public class DialogFlowReply implements IntgReply {
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
