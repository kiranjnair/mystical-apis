package hack.model.converse;

import java.util.List;

public class Result {
	private String source;
	private String resolvedQuery;
	private String action;
	private boolean actionIncomplete;	
	private Parameters parameters;
	private List<Context> contexts;
	private Metadata metadata;	
	private Fullfillment fulfillment;
	private String score;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getResolvedQuery() {
		return resolvedQuery;
	}
	public void setResolvedQuery(String resolvedQuery) {
		this.resolvedQuery = resolvedQuery;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Parameters getParameters() {
		return parameters;
	}
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public boolean isActionIncomplete() {
		return actionIncomplete;
	}
	public void setActionIncomplete(boolean actionIncomplete) {
		this.actionIncomplete = actionIncomplete;
	}
	public Fullfillment getFulfillment() {
		return fulfillment;
	}
	public void setFulfillment(Fullfillment fulfillment) {
		this.fulfillment = fulfillment;
	}

	public List<Context> getContexts() {
		return contexts;
	}
	public void setContexts(List<Context> contexts) {
		this.contexts = contexts;
	}
	@Override
	public String toString() {
		return "Result [source=" + source + ", resolvedQuery=" + resolvedQuery + ", action=" + action
				+ ", actionIncomplete=" + actionIncomplete + ", parameters=" + parameters + ", contexts=" + contexts
				+ ", metadata=" + metadata + ", fulfillment=" + fulfillment + ", score=" + score + "]";
	}

}
