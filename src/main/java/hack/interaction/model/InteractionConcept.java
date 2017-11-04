
package hack.interaction.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "minConceptItem",
    "sourceConceptItem"
})
public class InteractionConcept {

    @JsonProperty("minConceptItem")
    private MinConceptItem minConceptItem;
    @JsonProperty("sourceConceptItem")
    private SourceConceptItem sourceConceptItem;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("minConceptItem")
    public MinConceptItem getMinConceptItem() {
        return minConceptItem;
    }

    @JsonProperty("minConceptItem")
    public void setMinConceptItem(MinConceptItem minConceptItem) {
        this.minConceptItem = minConceptItem;
    }

    @JsonProperty("sourceConceptItem")
    public SourceConceptItem getSourceConceptItem() {
        return sourceConceptItem;
    }

    @JsonProperty("sourceConceptItem")
    public void setSourceConceptItem(SourceConceptItem sourceConceptItem) {
        this.sourceConceptItem = sourceConceptItem;
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
