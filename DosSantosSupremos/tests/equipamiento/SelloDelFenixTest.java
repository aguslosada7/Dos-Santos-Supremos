package equipamiento;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SelloDelFenixTest {
	@Test
	void aumentaSoloLaVidaMaxima() {
		SelloDelFenix sello = new SelloDelFenix();

		assertEquals(CategoriaEquipamiento.SELLO, sello.getCategoria());
		assertEquals(20, sello.getBonificacionVidaMaxima());
		assertEquals(0, sello.getBonificacionEnergiaMaxima());
		assertEquals(0, sello.getBonificacionDanio());
		assertEquals(0, sello.getReduccionDanio());
	}
}
