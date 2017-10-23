package hack.intg.conversation;

import hack.intg.IntgReply;
import hack.model.converse.ResponseModel;

public class ConversationReply implements IntgReply {
	private ResponseModel rModel;

	public ResponseModel getrModel() {
		return rModel;
	}

	public void setrModel(ResponseModel rModel) {
		this.rModel = rModel;
	}

	@Override
	public String toString() {
		return "ConversationReply [rModel=" + rModel + "]";
	}
	


	

}
