package hack.model.converse;

public class Context {
	private String name;
	private String lifespan;
	private ContextParameters parameters;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLifespan() {
		return lifespan;
	}
	public void setLifespan(String lifespan) {
		this.lifespan = lifespan;
	}
	public ContextParameters getParameters() {
		return parameters;
	}
	public void setParameters(ContextParameters parameters) {
		this.parameters = parameters;
	}
	@Override
	public String toString() {
		return "Context [name=" + name + ", lifespan=" + lifespan + ", parameters=" + parameters + "]";
	}
	
	
}
