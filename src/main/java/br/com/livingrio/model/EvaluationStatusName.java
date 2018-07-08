package br.com.livingrio.model;

import lombok.Getter;

public enum EvaluationStatusName {

	WAITING_FOR_VALIDATION("Aguardando Validação"), VALIDATED("Aprovado"), DENIED("Negado");
	
	EvaluationStatusName(String name){
		this.name = name;
	}
	
	@Getter private String name;
	
}
