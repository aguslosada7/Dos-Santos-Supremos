package tecnicas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import combatientes.Combatiente;
import combatientes.HechiceroDelVacio;

class MeditacionDelInmortalTest {
	@Test
	void esUnaTecnicaDeRecuperacionQueCuestaDoceYSinDanio() {
		MeditacionDelInmortal meditacion = new MeditacionDelInmortal();

		assertEquals("Meditación del Inmortal", meditacion.getNombre());
		assertEquals(CategoriaTecnica.RECUPERACION, meditacion.getCategoria());
		assertEquals(12, meditacion.getCostoEnergia());
		assertEquals(0, meditacion.getDanio());
	}

	@Test
	void recuperaVeinticuatroDeVidaAlEjecutorAunqueElObjetivoSeaOtro() {
		Combatiente ejecutor = new HechiceroDelVacio(1, "Lyra");
		Combatiente objetivo = new HechiceroDelVacio(2, "Orión");
		MeditacionDelInmortal meditacion = new MeditacionDelInmortal();
		ejecutor.recibirDanio(40);
		int vidaInicialObjetivo = objetivo.getVidaActual();

		meditacion.aplicar(ejecutor, objetivo);

		assertEquals(84, ejecutor.getVidaActual());
		assertEquals(vidaInicialObjetivo, objetivo.getVidaActual());
	}

	@Test
	void permiteAplicarseSobreElMismoEjecutorSinSuperarLaVidaMaxima() {
		Combatiente ejecutor = new HechiceroDelVacio(1, "Lyra");
		MeditacionDelInmortal meditacion = new MeditacionDelInmortal();
		ejecutor.recibirDanio(10);

		meditacion.aplicar(ejecutor, ejecutor);

		assertEquals(100, ejecutor.getVidaActual());
	}

	@Test
	void rechazaEjecutorUObjetivoNulo() {
		MeditacionDelInmortal meditacion = new MeditacionDelInmortal();
		Combatiente combatiente = new HechiceroDelVacio(1, "Lyra");

		assertThrows(NullPointerException.class, () -> meditacion.aplicar(null, combatiente));
		assertThrows(NullPointerException.class, () -> meditacion.aplicar(combatiente, null));
	}
}
