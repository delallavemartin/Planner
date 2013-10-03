package exception;

@SuppressWarnings("serial")
public class UserException extends RuntimeException {

	public UserException(String msj) {
		super(msj);
	}

}