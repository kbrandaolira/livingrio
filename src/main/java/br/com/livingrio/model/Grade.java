package br.com.livingrio.model;

import lombok.Getter;

public enum Grade {
	
	UM("1.0"), UM_MEIO("1.5"), DOIS("2.0"), DOIS_MEIO("2.5"), TRES("3.0"), TRES_MEIO("3.5"), QUATRO("4.0"), QUATRO_MEIO("4.5"), CINCO("5.0");
	
	@Getter private String value;
	
	Grade(String value){
		this.value = value;
	}

}