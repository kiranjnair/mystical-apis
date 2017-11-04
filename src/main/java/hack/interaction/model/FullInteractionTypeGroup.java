
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
    "sourceDisclaimer",
    "sourceName",
    "fullInteractionType"
})
public class FullInteractionTypeGroup {

    @JsonProperty("sourceDisclaimer")
    private String sourceDisclaimer;
    @JsonProperty("sourceName")
    private String sourceName;
    @JsonProperty("fullInteractionType")
    private List<FullInteractionType> fullInteractionType = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sourceDisclaimer")
    public String getSourceDisclaimer() {
        return sourceDisclaimer;
    }

    @JsonProperty("sourceDisclaimer")
    public void setSourceDisclaimer(String sourceDisclaimer) {
        this.sourceDisclaimer = sourceDisclaimer;
    }

    @JsonProperty("sourceName")
    public String getSourceName() {
        return sourceName;
    }

    @JsonProperty("sourceName")
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @JsonProperty("fullInteractionType")
    public List<FullInteractionType> getFullInteractionType() {
        return fullInteractionType;
    }

    @JsonProperty("fullInteractionType")
    public void setFullInteractionType(List<FullInteractionType> fullInteractionType) {
        this.fullInteractionType = fullInteractionType;
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
