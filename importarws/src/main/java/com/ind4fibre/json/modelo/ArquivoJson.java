
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
    "Armazenamento",
    "Imp3D",
    "Sala"
})
public class ArquivoJson implements Serializable
{

    @JsonProperty("Armazenamento")
    private List<Armazenamento> armazenamento = new ArrayList<Armazenamento>();
    @JsonProperty("Imp3D")
    private List<Imp3D> imp3D = new ArrayList<Imp3D>();
    @JsonProperty("Sala")
    private List<Sala> sala = new ArrayList<Sala>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 9002627555553497647L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ArquivoJson() {
    }

    /**
     * 
     * @param armazenamento
     * @param sala
     * @param imp3D
     */
    public ArquivoJson(List<Armazenamento> armazenamento, List<Imp3D> imp3D, List<Sala> sala) {
        super();
        this.armazenamento = armazenamento;
        this.imp3D = imp3D;
        this.sala = sala;
    }

    @JsonProperty("Armazenamento")
    public List<Armazenamento> getArmazenamento() {
        return armazenamento;
    }

    @JsonProperty("Armazenamento")
    public void setArmazenamento(List<Armazenamento> armazenamento) {
        this.armazenamento = armazenamento;
    }

    public ArquivoJson withArmazenamento(List<Armazenamento> armazenamento) {
        this.armazenamento = armazenamento;
        return this;
    }

    @JsonProperty("Imp3D")
    public List<Imp3D> getImp3D() {
        return imp3D;
    }

    @JsonProperty("Imp3D")
    public void setImp3D(List<Imp3D> imp3D) {
        this.imp3D = imp3D;
    }

    public ArquivoJson withImp3D(List<Imp3D> imp3D) {
        this.imp3D = imp3D;
        return this;
    }

    @JsonProperty("Sala")
    public List<Sala> getSala() {
        return sala;
    }

    @JsonProperty("Sala")
    public void setSala(List<Sala> sala) {
        this.sala = sala;
    }

    public ArquivoJson withSala(List<Sala> sala) {
        this.sala = sala;
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

    public ArquivoJson withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("armazenamento", armazenamento).append("imp3D", imp3D).append("sala", sala).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(additionalProperties).append(armazenamento).append(sala).append(imp3D).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ArquivoJson) == false) {
            return false;
        }
        ArquivoJson rhs = ((ArquivoJson) other);
        return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties).append(armazenamento, rhs.armazenamento).append(sala, rhs.sala).append(imp3D, rhs.imp3D).isEquals();
    }

}
