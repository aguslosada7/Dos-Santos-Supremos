package torneo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import acontecimientos.CanalAcontecimientos;
import combatientes.Combatiente;
import combatientes.HechiceroDelVacio;
import tecnicas.CorteDeLosMilAmaneceres;

class CombateTest {
	@Test
	void enEmpateGanaLaParejaDeMenorId() {
		Pareja pareja1 = crearPareja(1);
		Pareja pareja2 = crearPareja(2);
		Combate combate = new Combate(1, pareja2, pareja1, 1, 0, new CanalAcontecimientos());

		assertSame(pareja1, combate.resolver());
		assertEquals(pareja1.vidaTotal(), pareja2.vidaTotal());
	}

	@Test
	void alLimiteGanaLaParejaConMasVida() {
		Pareja pareja1 = crearPareja(1);
		Pareja pareja2 = crearPareja(2);
		Combatiente ejecutor = pareja2.getIntegrantes().get(0);
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		ejecutor.aprender(corte);
		Combate combate = new Combate(1, pareja1, pareja2, 1, 0, new CanalAcontecimientos());

		assertSame(pareja2, combate.resolver());
		assertTrue(pareja2.vidaTotal() > pareja1.vidaTotal());
		assertEquals(4, ejecutor.usosRestantes(corte));
	}

	@Test
	void terminaCuandoCaeLaParejaRival() {
		Pareja pareja1 = crearPareja(1);
		Pareja pareja2 = crearPareja(2);
		Combate combate = new Combate(1, pareja1, pareja2, 50, 0, new CanalAcontecimientos());

		assertSame(pareja1, combate.resolver());
		assertTrue(pareja2.estaEliminada());
	}

	@Test
	void rechazaUnCombatienteEnLasDosParejas() {
		Combatiente compartido = new HechiceroDelVacio(1, "Ana");
		Pareja pareja1 = new Pareja(1, compartido, new HechiceroDelVacio(2, "Luis"));
		Pareja pareja2 = new Pareja(2, compartido, new HechiceroDelVacio(3, "Pablo"));
		CanalAcontecimientos canal = new CanalAcontecimientos();

		assertThrows(IllegalArgumentException.class, () -> new Combate(1, pareja1, pareja2, 1, 0, canal));
	}

	private Pareja crearPareja(int id) {
		return new Pareja(id, new HechiceroDelVacio(id * 2 - 1, "Ana"), new HechiceroDelVacio(id * 2, "Luis"));
	}
}
