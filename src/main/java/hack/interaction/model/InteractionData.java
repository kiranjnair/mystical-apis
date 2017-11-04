
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
    "nlmDisclaimer",
    "userInput",
    "fullInteractionTypeGroup"
})
public class InteractionData {

    @JsonProperty("nlmDisclaimer")
    private String nlmDisclaimer;
    @JsonProperty("userInput")
    private UserInput userInput;
    @JsonProperty("fullInteractionTypeGroup")
    private List<FullInteractionTypeGroup> fullInteractionTypeGroup = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("nlmDisclaimer")
    public String getNlmDisclaimer() {
        return nlmDisclaimer;
    }

    @JsonProperty("nlmDisclaimer")
    public void setNlmDisclaimer(String nlmDisclaimer) {
        this.nlmDisclaimer = nlmDisclaimer;
    }

    @JsonProperty("userInput")
    public UserInput getUserInput() {
        return userInput;
    }

    @JsonProperty("userInput")
    public void setUserInput(UserInput userInput) {
        this.userInput = userInput;
    }

    @JsonProperty("fullInteractionTypeGroup")
    public List<FullInteractionTypeGroup> getFullInteractionTypeGroup() {
        return fullInteractionTypeGroup;
    }

    @JsonProperty("fullInteractionTypeGroup")
    public void setFullInteractionTypeGroup(List<FullInteractionTypeGroup> fullInteractionTypeGroup) {
        this.fullInteractionTypeGroup = fullInteractionTypeGroup;
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
