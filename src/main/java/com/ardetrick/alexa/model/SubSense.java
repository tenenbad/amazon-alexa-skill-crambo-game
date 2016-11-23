
package com.ardetrick.alexa.model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "definition",
    "examples"
})
public class SubSense {

    @JsonProperty("definition")
    private String definition;
    @JsonProperty("examples")
    private List<Example_> examples = new ArrayList<Example_>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The definition
     */
    @JsonProperty("definition")
    public String getDefinition() {
        return definition;
    }

    /**
     * 
     * @param definition
     *     The definition
     */
    @JsonProperty("definition")
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     * 
     * @return
     *     The examples
     */
    @JsonProperty("examples")
    public List<Example_> getExamples() {
        return examples;
    }

    /**
     * 
     * @param examples
     *     The examples
     */
    @JsonProperty("examples")
    public void setExamples(List<Example_> examples) {
        this.examples = examples;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Sense{" +
                "definition='" + definition + '\'' +
                ", examples=" + examples +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
