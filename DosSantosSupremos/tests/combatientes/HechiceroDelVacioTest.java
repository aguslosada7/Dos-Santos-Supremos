package combatientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tecnicas.CorteDeLosMilAmaneceres;

class HechiceroDelVacioTest {

	@Test
	void reduceElCostoDeTecnicasEnUnVeinticincoPorCiento() {
		Combatiente hechicero = new HechiceroDelVacio(1, "Nébula");

		assertEquals(6, hechicero.calcularCostoEnergia(8));
		assertEquals(9, hechicero.calcularCostoEnergia(12));
		assertEquals(15, hechicero.calcularCostoEnergia(20));
	}

	@Test
	void redondeaDescuentoHaciaAbajo() {
		Combatiente hechicero = new HechiceroDelVacio(1, "Nébula");

		assertEquals(3, hechicero.calcularCostoEnergia(5));
		assertEquals(7, hechicero.calcularCostoEnergia(10));
	}

	@Test
	void unCostoPositivoPagaComoMinimoUno() {
		Combatiente hechicero = new HechiceroDelVacio(1, "Nébula");

		assertEquals(1, hechicero.calcularCostoEnergia(1));
	}

	@Test
	void costoDeAtaqueBasicoOCeroEsCero() {
		Combatiente hechicero = new HechiceroDelVacio(1, "Nébula");

		assertEquals(0, hechicero.calcularCostoEnergia(0));
	}

	@Test
	void unaTecnicaTieneDistintoCostoSegunTipoDelEjecutor() {
		Combatiente cultivador = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente hechicero = new HechiceroDelVacio(2, "Nébula");
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();

		int costoCultivador = cultivador.calcularCostoEnergia(corte.getCostoEnergia());
		int costoHechicero = hechicero.calcularCostoEnergia(corte.getCostoEnergia());

		assertEquals(8, costoCultivador);
		assertEquals(6, costoHechicero);
		assertTrue(costoHechicero < costoCultivador);
	}

	@Test
	void noModificaElDanioEmitido() {
		Combatiente hechicero = new HechiceroDelVacio(1, "Nébula");

		assertEquals(12, hechicero.calcularDanio(12));
		assertEquals(30, hechicero.calcularDanio(30));
	}

	@Test
	void noModificaElDanioRecibido() {
		Combatiente hechicero = new HechiceroDelVacio(1, "Nébula");

		hechicero.recibirDanio(25);

		assertEquals(75, hechicero.getVidaActual());
	}

	@Test
	void esUnCombatienteConSusAtributosYComportamientosBase() {
		Combatiente hechicero = new HechiceroDelVacio(1, "Nébula");

		assertEquals(1, hechicero.getId());
		assertEquals("Nébula", hechicero.getNombre());
		assertEquals(100, hechicero.getVidaActual());
		assertEquals(40, hechicero.getEnergiaActual());
		assertTrue(hechicero.estaEnPie());
	}
}
