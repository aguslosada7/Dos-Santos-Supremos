package equipamiento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class AnilloEspiritualTest {
	@Test
	void aumentaSoloLaEnergiaMaxima() {
		AnilloEspiritual anillo = new AnilloEspiritual();

		assertEquals("Anillo Espiritual", anillo.getNombre());
		assertEquals(CategoriaEquipamiento.ANILLO, anillo.getCategoria());
		assertEquals(16, anillo.getBonificacionEnergiaMaxima());
		assertEquals(0, anillo.getBonificacionVidaMaxima());
		assertEquals(0, anillo.getBonificacionDanio());
		assertEquals(0, anillo.getReduccionDanio());
	}

	@Test
	void dosInstanciasTienenLosMismosValoresSinCompartirEstado() {
		AnilloEspiritual primero = new AnilloEspiritual();
		AnilloEspiritual segundo = new AnilloEspiritual();

		assertNotSame(primero, segundo);
		assertEquals(primero.getBonificacionEnergiaMaxima(), segundo.getBonificacionEnergiaMaxima());
		assertEquals(primero.getCategoria(), segundo.getCategoria());
	}
}
