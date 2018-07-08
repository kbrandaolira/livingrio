package br.com.livingrio.exception;

/**
 * Exception criada para ser lan�ada em caso de formul�rio com dados inv�lidos
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
