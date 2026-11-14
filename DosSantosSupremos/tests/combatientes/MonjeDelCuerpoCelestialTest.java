package combatientes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import equipamiento.ArmaduraDorada;
import tecnicas.CorteDeLosMilAmaneceres;

class MonjeDelCuerpoCelestialTest {
	@Test
	void reduceUnVeintePorCientoElDanioQueRecibe() {
		Combatiente monje = new MonjeDelCuerpoCelestial(1, "Kael");
		int vidaInicial = monje.getVidaActual();

		monje.recibirDanio(25);

		assertEquals(vidaInicial - 20, monje.getVidaActual());
	}

	@Test
	void redondeaHaciaAbajoElDanioRecibido() {
		Combatiente monje = new MonjeDelCuerpoCelestial(1, "Kael");
		int vidaInicial = monje.getVidaActual();

		monje.recibirDanio(24); // 24 * 80 / 100 = 19,2

		assertEquals(vidaInicial - 19, monje.getVidaActual());
	}

	@Test
	void unaMismaTecnicaDaniaDistintoSegunElTipoDelObjetivo() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		Combatiente ejecutor = new HechiceroDelVacio(1, "Nébula");
		Combatiente monje = new MonjeDelCuerpoCelestial(2, "Kael");
		Combatiente hechicero = new HechiceroDelVacio(3, "Lyra");
		int vidaInicial = monje.getVidaActual();

		corte.aplicar(ejecutor, monje);
		corte.aplicar(ejecutor, hechicero);

		assertEquals(vidaInicial - 19, monje.getVidaActual());
		assertEquals(vidaInicial - 24, hechicero.getVidaActual());
	}

	@Test
	void primeroRestaEquipoYDefensaYDespuesAplicaElPorcentaje() {
		Combatiente monje = new MonjeDelCuerpoCelestial(1, "Kael");
		monje.equipar(new ArmaduraDorada());
		monje.aplicarDefensaCombate(6);
		int vidaInicial = monje.getVidaActual();

		monje.recibirDanio(36); // (36 - 4 - 6) * 80 / 100 = 20,8

		assertEquals(vidaInicial - 20, monje.getVidaActual());
	}

	@Test
	void noModificaElDanioQueProvocaNiElCostoDeEnergia() {
		Combatiente monje = new MonjeDelCuerpoCelestial(1, "Kael");

		assertEquals(30, monje.calcularDanio(30));
		assertEquals(8, monje.calcularCostoEnergia(8));
	}
}
