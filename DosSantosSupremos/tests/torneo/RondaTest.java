package torneo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import acontecimientos.CanalAcontecimientos;
import combatientes.HechiceroDelVacio;

class RondaTest {
	@Test
	void rechazaUnaRondaSinCruces() {
		assertThrows(IllegalArgumentException.class, () -> new Ronda(1, List.of()));
	}

	@Test
	void devuelveLasGanadorasEnElOrdenDeLosCruces() {
		Pareja pareja1 = crearPareja(1);
		Pareja pareja2 = crearPareja(2);
		Pareja pareja3 = crearPareja(3);
		Pareja pareja4 = crearPareja(4);
		CanalAcontecimientos canal = new CanalAcontecimientos();
		Combate primerCombate = new Combate(1, pareja3, pareja4, 1, 0, canal);
		Combate segundoCombate = new Combate(1, pareja1, pareja2, 1, 0, canal);
		Cruce primerCruce = new Cruce(pareja3, pareja4, primerCombate);
		Cruce segundoCruce = new Cruce(pareja1, pareja2, segundoCombate);
		Ronda ronda = new Ronda(1, List.of(primerCruce, segundoCruce));

		assertEquals(List.of(pareja3, pareja1), ronda.jugar());
	}

	private Pareja crearPareja(int id) {
		return new Pareja(id, new HechiceroDelVacio(id * 2 - 1, "Ana"), new HechiceroDelVacio(id * 2, "Luis"));
	}
}
