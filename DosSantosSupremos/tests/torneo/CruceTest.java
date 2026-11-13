package torneo;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import acontecimientos.CanalAcontecimientos;
import combatientes.Combatiente;
import combatientes.HechiceroDelVacio;

class CruceTest {

	@Test
	void guardaLasParejasYElCombate() {
		Pareja pareja1 = crearPareja(1);
		Pareja pareja2 = crearPareja(2);
		Combate combate = new Combate(1, pareja1, pareja2, 1, 0, new CanalAcontecimientos());
		Cruce cruce = new Cruce(pareja1, pareja2, combate);

		assertSame(pareja1, cruce.getParejaA());
		assertSame(pareja2, cruce.getParejaB());
	}

	@Test
	void rechazaParejasNulasOCombateNulo() {
		Pareja pareja = crearPareja(1);
		Combate combate = new Combate(1, pareja, crearPareja(2), 1, 0, new CanalAcontecimientos());

		assertThrows(NullPointerException.class, () -> new Cruce(null, pareja, combate));
		assertThrows(NullPointerException.class, () -> new Cruce(pareja, null, combate));
		assertThrows(NullPointerException.class, () -> new Cruce(pareja, pareja, null));
	}

	@Test
	void rechazaLasMismasParejasEnAmbosLados() {
		Pareja pareja = crearPareja(1);
		Combate combate = new Combate(1, pareja, crearPareja(2), 1, 0, new CanalAcontecimientos());

		assertThrows(IllegalArgumentException.class, () -> new Cruce(pareja, pareja, combate));
	}

	@Test
	void resolverDevuelveLaParejaGanadoraDelCombate() {
		Pareja pareja1 = crearPareja(1);
		Pareja pareja2 = crearPareja(2);
		Combate combate = new Combate(1, pareja2, pareja1, 1, 0, new CanalAcontecimientos());
		Cruce cruce = new Cruce(pareja2, pareja1, combate);

		Pareja ganadora = cruce.resolver();

		assertSame(pareja1, ganadora);
	}

	@Test
	void resolverEsIdempotenteYNoRepiteElCombate() {
		Pareja pareja1 = crearPareja(1);
		Pareja pareja2 = crearPareja(2);
		Combate combate = new Combate(1, pareja1, pareja2, 1, 0, new CanalAcontecimientos());
		Cruce cruce = new Cruce(pareja1, pareja2, combate);

		Pareja primerResultado = cruce.resolver();
		Pareja segundoResultado = cruce.resolver();

		assertSame(primerResultado, segundoResultado);
	}

	private Pareja crearPareja(int id) {
		Combatiente c1 = new HechiceroDelVacio(id * 2 - 1, "Combatiente " + (id * 2 - 1));
		Combatiente c2 = new HechiceroDelVacio(id * 2, "Combatiente " + (id * 2));
		return new Pareja(id, c1, c2);
	}
}
