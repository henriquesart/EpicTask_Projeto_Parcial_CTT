package br.com.fiap.epictask.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationFieldErro {
	
	private String field;
	private String error;

}
