package combatientes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import tecnicas.CorteDeLosMilAmaneceres;

class CultivadorDeLaEspadaTest {
	@Test
	void potenciaUnVeintePorCientoElDanioQueProvoca() {
		Combatiente cultivador = new CultivadorDeLaEspada(1, "Aurelio");

		assertEquals(36, cultivador.calcularDanio(30));
	}

	@Test
	void redondeaHaciaAbajo() {
		Combatiente cultivador = new CultivadorDeLaEspada(1, "Aurelio");

		assertEquals(28, cultivador.calcularDanio(24)); // 24 * 120 / 100 = 28,8
	}

	@Test
	void unaMismaTecnicaDaniaDistintoSegunElTipoDelEjecutor() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		Combatiente cultivador = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente hechicero = new HechiceroDelVacio(2, "Nébula");
		Combatiente objetivoDelCultivador = new HechiceroDelVacio(3, "Lyra");
		Combatiente objetivoDelHechicero = new HechiceroDelVacio(4, "Orión");
		int vidaInicial = objetivoDelCultivador.getVidaActual();

		corte.aplicar(cultivador, objetivoDelCultivador);
		corte.aplicar(hechicero, objetivoDelHechicero);

		assertEquals(vidaInicial - 28, objetivoDelCultivador.getVidaActual());
		assertEquals(vidaInicial - 24, objetivoDelHechicero.getVidaActual());
	}

	@Test
	void noModificaElCostoDeEnergia() {
		Combatiente cultivador = new CultivadorDeLaEspada(1, "Aurelio");

		assertEquals(8, cultivador.calcularCostoEnergia(8));
	}

	@Test
	void dosCombatientesConElMismoIdSonIgualesAunqueCambieElNombre() {
		Combatiente primero = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente segundo = new CultivadorDeLaEspada(1, "Otro");

		assertEquals(primero, segundo);
		assertEquals(primero.hashCode(), segundo.hashCode());
	}
}
