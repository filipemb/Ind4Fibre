package com.ind4fibre.json.modelo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LocalDaSRAM{ 
	FLORIANOPOLIS("FLORIANOPOLIS"){@Override public String toString() {return "FLORIANOPOLIS";}},
	BAHIA("BAHIA"){@Override public String toString() {return "BAHIA";}};
	
	private String value;
	
	LocalDaSRAM(String value) {
      this.value = value;
    }
	
    @JsonValue
    public String getValue() {
      return value;
    }
    
    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static LocalDaSRAM fromValue(String text) {
      for (LocalDaSRAM b : LocalDaSRAM.values()) {
        if (String.valueOf(b.value).contentEquals(text)) {
          return b;
        }
      }
      return null;
    }
}