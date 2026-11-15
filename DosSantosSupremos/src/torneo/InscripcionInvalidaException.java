package torneo;

public class InscripcionInvalidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InscripcionInvalidaException() {
		super();
	}

	public InscripcionInvalidaException(String mensaje) {
		super(mensaje);
	}

	public InscripcionInvalidaException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
