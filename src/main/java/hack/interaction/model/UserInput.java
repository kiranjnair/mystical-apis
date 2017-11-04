
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
    "sources",
    "rxcuis"
})
public class UserInput {

    @JsonProperty("sources")
    private List<String> sources = null;
    @JsonProperty("rxcuis")
    private List<String> rxcuis = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sources")
    public List<String> getSources() {
        return sources;
    }

    @JsonProperty("sources")
    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    @JsonProperty("rxcuis")
    public List<String> getRxcuis() {
        return rxcuis;
    }

    @JsonProperty("rxcuis")
    public void setRxcuis(List<String> rxcuis) {
        this.rxcuis = rxcuis;
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
