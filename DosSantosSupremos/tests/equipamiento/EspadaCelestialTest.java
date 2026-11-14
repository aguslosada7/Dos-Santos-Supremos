package equipamiento;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EspadaCelestialTest {
	@Test
	void aumentaSoloElDanio() {
		EspadaCelestial espada = new EspadaCelestial();

		assertEquals(CategoriaEquipamiento.ARMA, espada.getCategoria());
		assertEquals(6, espada.getBonificacionDanio());
		assertEquals(0, espada.getReduccionDanio());
		assertEquals(0, espada.getBonificacionVidaMaxima());
		assertEquals(0, espada.getBonificacionEnergiaMaxima());
	}
}