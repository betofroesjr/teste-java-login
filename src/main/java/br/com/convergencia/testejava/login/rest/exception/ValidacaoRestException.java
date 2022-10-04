package br.com.convergencia.testejava.login.rest.exception;

public class ValidacaoRestException extends Exception {

	private static final long serialVersionUID = -3153167019425797487L;
	
	public ValidacaoRestException(String error) {
		super(error);
	}
}
