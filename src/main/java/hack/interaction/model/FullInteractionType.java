
package hack.interaction.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "comment",
    "minConcept",
    "interactionPair"
})
public class FullInteractionType {

    @JsonProperty("comment")
    private String comment;
    @JsonProperty("minConcept")
    private List<MinConcept> minConcept = null;
    @JsonProperty("interactionPair")
    private List<InteractionPair> interactionPair = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty("minConcept")
    public List<MinConcept> getMinConcept() {
        return minConcept;
    }

    @JsonProperty("minConcept")
    public void setMinConcept(List<MinConcept> minConcept) {
        this.minConcept = minConcept;
    }

    @JsonProperty("interactionPair")
    public List<InteractionPair> getInteractionPair() {
        return interactionPair;
    }

    @JsonProperty("interactionPair")
    public void setInteractionPair(List<InteractionPair> interactionPair) {
        this.interactionPair = interactionPair;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
