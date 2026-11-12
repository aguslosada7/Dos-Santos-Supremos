package acontecimientos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class EliminacionTest {
	@Test
	void guardaLosDatosDelMomento() {
		Eliminacion eliminacion = new Eliminacion(2, 7, 3, 5, "Kael");

		assertEquals(2, eliminacion.ronda());
		assertEquals(7, eliminacion.numeroAsalto());
		assertEquals(3, eliminacion.idPareja());
		assertEquals(5, eliminacion.idCombatiente());
		assertEquals("Kael", eliminacion.nombreCombatiente());
	}

	@Test
	void rechazaValoresNoPositivos() {
		assertThrows(IllegalArgumentException.class, () -> new Eliminacion(0, 1, 1, 1, "Kael"));
		assertThrows(IllegalArgumentException.class, () -> new Eliminacion(1, 0, 1, 1, "Kael"));
		assertThrows(IllegalArgumentException.class, () -> new Eliminacion(1, 1, 0, 1, "Kael"));
		assertThrows(IllegalArgumentException.class, () -> new Eliminacion(1, 1, 1, 0, "Kael"));
	}

	@Test
	void rechazaNombreNulo() {
		assertThrows(NullPointerException.class, () -> new Eliminacion(1, 1, 1, 1, null));
	}

	@Test
	void dosEliminacionesConLosMismosDatosSonIguales() {
		assertEquals(new Eliminacion(1, 2, 3, 4, "Kael"), new Eliminacion(1, 2, 3, 4, "Kael"));
		assertEquals(new Eliminacion(1, 2, 3, 4, "Kael").hashCode(), new Eliminacion(1, 2, 3, 4, "Kael").hashCode());
	}

	@Test
	void rechazaUnObservadorNulo() {
		Eliminacion eliminacion = new Eliminacion(1, 2, 3, 4, "Kael");

		assertThrows(NullPointerException.class, () -> eliminacion.notificarA(null));
	}
}
