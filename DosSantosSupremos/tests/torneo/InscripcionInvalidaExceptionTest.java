package torneo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class InscripcionInvalidaExceptionTest {
	@Test
	void esUnaRuntimeException() {
		InscripcionInvalidaException excepcion = new InscripcionInvalidaException();

		assertInstanceOf(RuntimeException.class, excepcion);
	}

	@Test
	void permiteCrearseSinMensajeNiCausa() {
		InscripcionInvalidaException excepcion = new InscripcionInvalidaException();

		assertNull(excepcion.getMessage());
		assertNull(excepcion.getCause());
	}

	@Test
	void conservaElMensajeDeError() {
		InscripcionInvalidaException excepcion = new InscripcionInvalidaException("La pareja ya fue inscripta.");

		assertEquals("La pareja ya fue inscripta.", excepcion.getMessage());
		assertNull(excepcion.getCause());
	}

	@Test
	void conservaElMensajeYLaCausa() {
		Throwable causa = new IllegalArgumentException("Datos inválidos.");
		InscripcionInvalidaException excepcion = new InscripcionInvalidaException("Inscripción rechazada.", causa);

		assertEquals("Inscripción rechazada.", excepcion.getMessage());
		assertSame(causa, excepcion.getCause());
	}
}
