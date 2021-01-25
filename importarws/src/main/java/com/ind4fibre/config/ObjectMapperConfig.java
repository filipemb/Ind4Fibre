package com.ind4fibre.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ObjectMapperConfig {

	@Lazy
	@Autowired
	ObjectMapper mapper;

	@PostConstruct
	public ObjectMapper configureMapper() {
	   
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
		mapper.configOverride(Collection.class).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_EMPTY, null));
	    mapper.configOverride(List.class).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_EMPTY, null));
	    mapper.configOverride(Map.class).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_EMPTY, null));
		
	    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

	    mapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, true);
	    mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);

	    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

	    //default
	    mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
	    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    
	    //Jackson2DatatypeHelper
	    //jsr310
	    mapper.registerModule(new JavaTimeModule());
	    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
	    //hibernate
	    mapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
	    
	    //default is lazy load all before serialize
	    Hibernate5Module module = new Hibernate5Module();
		module.disable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
		mapper.registerModule(module);
	    
	    return mapper;
	}
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private abstract class IgnoreHibernatePropertiesInJackson{ }
}

