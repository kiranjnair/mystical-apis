
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
    "rxcui",
    "name",
    "tty"
})
public class MinConcept {

    @JsonProperty("rxcui")
    private String rxcui;
    @JsonProperty("name")
    private String name;
    @JsonProperty("tty")
    private String tty;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("rxcui")
    public String getRxcui() {
        return rxcui;
    }

    @JsonProperty("rxcui")
    public void setRxcui(String rxcui) {
        this.rxcui = rxcui;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("tty")
    public String getTty() {
        return tty;
    }

    @JsonProperty("tty")
    public void setTty(String tty) {
        this.tty = tty;
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
