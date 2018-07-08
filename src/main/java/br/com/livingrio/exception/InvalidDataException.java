package br.com.livingrio.exception;

/**
 * Exception criada para ser lançada em caso de formulário com dados inválidos
*/
public class InvalidDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidDataException( String msg ){
		super( msg );
	}

}
