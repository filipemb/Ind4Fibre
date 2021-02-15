
package com.ind4fibre.json.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Robo",
    "Alarmes"
})
public class ArquivoJsonRobot implements Serializable
{

    @JsonProperty("Robo")
    private List<Robo> robo = new ArrayList<Robo>();
    @JsonProperty("Alarmes")
    private List<Alarme> alarmes = new ArrayList<Alarme>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 4530367842633605578L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ArquivoJsonRobot() {
    }

    /**
     * 
     * @param alarmes
     * @param robo
     */
    public ArquivoJsonRobot(List<Robo> robo, List<Alarme> alarmes) {
        super();
        this.robo = robo;
        this.alarmes = alarmes;
    }

    @JsonProperty("Robo")
    public List<Robo> getRobo() {
        return robo;
    }

    @JsonProperty("Robo")
    public void setRobo(List<Robo> robo) {
        this.robo = robo;
    }

    public ArquivoJsonRobot withRobo(List<Robo> robo) {
        this.robo = robo;
        return this;
    }

    @JsonProperty("Alarmes")
    public List<Alarme> getAlarmes() {
        return alarmes;
    }

    @JsonProperty("Alarmes")
    public void setAlarmes(List<Alarme> alarmes) {
        this.alarmes = alarmes;
    }

    public ArquivoJsonRobot withAlarmes(List<Alarme> alarmes) {
        this.alarmes = alarmes;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public ArquivoJsonRobot withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("robo", robo).append("alarmes", alarmes).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(alarmes).append(additionalProperties).append(robo).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ArquivoJsonRobot) == false) {
            return false;
        }
        ArquivoJsonRobot rhs = ((ArquivoJsonRobot) other);
        return new EqualsBuilder().append(alarmes, rhs.alarmes).append(additionalProperties, rhs.additionalProperties).append(robo, rhs.robo).isEquals();
    }

}
