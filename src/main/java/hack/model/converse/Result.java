package hack.model.converse;

public class Result {
	public String source;
	public String resolvedQuery;
	public String speech;
	public String action;
	
	public Parameters parameters;
	public Metadata metadata;
	public String score;
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
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
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
	@Override
	public String toString() {
		return "Result [source=" + source + ", resolvedQuery=" + resolvedQuery + ", speech=" + speech + ", action="
				+ action + ", parameters=" + parameters + ", metadata=" + metadata + ", score=" + score + "]";
	}
	

}
