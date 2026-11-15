package equipamiento;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArmaduraDoradaTest {
	@Test
	void reduceSoloElDanioRecibido() {
		ArmaduraDorada armadura = new ArmaduraDorada();

		assertEquals(CategoriaEquipamiento.ARMADURA, armadura.getCategoria());
		assertEquals(4, armadura.getReduccionDanio());
		assertEquals(0, armadura.getBonificacionDanio());
		assertEquals(0, armadura.getBonificacionVidaMaxima());
		assertEquals(0, armadura.getBonificacionEnergiaMaxima());
	}
}