
package com.ardetrick.alexa.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "datasets",
    "headword",
    "id",
    "part_of_speech",
    "senses",
    "url"
})
public class Result {

    @JsonProperty("datasets")
    private List<String> datasets = new ArrayList<String>();
    @JsonProperty("headword")
    private String headword;
    @JsonProperty("id")
    private String id;
    @JsonProperty("part_of_speech")
    private String partOfSpeech;
    @JsonProperty("senses")
    private List<Sense> senses = new ArrayList<Sense>();
    @JsonProperty("url")
    private String url;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The datasets
     */
    @JsonProperty("datasets")
    public List<String> getDatasets() {
        return datasets;
    }

    /**
     * 
     * @param datasets
     *     The datasets
     */
    @JsonProperty("datasets")
    public void setDatasets(List<String> datasets) {
        this.datasets = datasets;
    }

    /**
     * 
     * @return
     *     The headword
     */
    @JsonProperty("headword")
    public String getHeadword() {
        return headword;
    }

    /**
     * 
     * @param headword
     *     The headword
     */
    @JsonProperty("headword")
    public void setHeadword(String headword) {
        this.headword = headword;
    }

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The partOfSpeech
     */
    @JsonProperty("part_of_speech")
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    /**
     * 
     * @param partOfSpeech
     *     The part_of_speech
     */
    @JsonProperty("part_of_speech")
    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    /**
     * 
     * @return
     *     The senses
     */
    @JsonProperty("senses")
    public List<Sense> getSenses() {
        return senses;
    }

    /**
     * 
     * @param senses
     *     The senses
     */
    @JsonProperty("senses")
    public void setSenses(List<Sense> senses) {
        this.senses = senses;
    }

    /**
     * 
     * @return
     *     The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
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
        return "Result{" +
                "datasets=" + datasets +
                ", headword='" + headword + '\'' +
                ", id='" + id + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", senses=" + senses +
                ", url='" + url + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
