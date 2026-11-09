package acontecimientos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ProclamacionTest {
	@Test
	void guardaLosDatosDeLaParejaCampeona() {
		Proclamacion proclamacion = new Proclamacion(1, 2, "Ana", 3, "Luis");

		assertEquals(1, proclamacion.idParejaCampeona());
		assertEquals(2, proclamacion.idPrimerCampeon());
		assertEquals("Ana", proclamacion.nombrePrimerCampeon());
		assertEquals(3, proclamacion.idSegundoCampeon());
		assertEquals("Luis", proclamacion.nombreSegundoCampeon());
	}

	@Test
	void rechazaIdsInvalidos() {
		assertThrows(IllegalArgumentException.class, () -> new Proclamacion(0, 2, "Ana", 3, "Luis"));
		assertThrows(IllegalArgumentException.class, () -> new Proclamacion(1, 2, "Ana", 2, "Luis"));
	}

	@Test
	void rechazaNombresNulos() {
		assertThrows(NullPointerException.class, () -> new Proclamacion(1, 2, null, 3, "Luis"));
		assertThrows(NullPointerException.class, () -> new Proclamacion(1, 2, "Ana", 3, null));
	}
}
