package tecnicas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import combatientes.Combatiente;
import combatientes.CultivadorDeLaEspada;
import combatientes.HechiceroDelVacio;

class RugidoDeLaBestiaPrimordialTest {
	@Test
	void esUnaTecnicaOfensivaQueCuestaVeinteYHaceCuarenta() {
		RugidoDeLaBestiaPrimordial rugido = new RugidoDeLaBestiaPrimordial();

		assertEquals("Rugido de la Bestia Primordial", rugido.getNombre());
		assertEquals(CategoriaTecnica.OFENSIVA, rugido.getCategoria());
		assertEquals(20, rugido.getCostoEnergia());
		assertEquals(40, rugido.getDanio());
	}

	@Test
	void quitaCuarentaDeVidaAlObjetivo() {
		Combatiente ejecutor = new HechiceroDelVacio(1, "Ana");
		Combatiente objetivo = new HechiceroDelVacio(2, "Luis");
		RugidoDeLaBestiaPrimordial rugido = new RugidoDeLaBestiaPrimordial();
		int vidaInicialEjecutor = ejecutor.getVidaActual();
		int vidaInicialObjetivo = objetivo.getVidaActual();

		rugido.aplicar(ejecutor, objetivo);

		assertEquals(vidaInicialObjetivo - 40, objetivo.getVidaActual());
		assertEquals(vidaInicialEjecutor, ejecutor.getVidaActual());
	}

	@Test
	void potenciaElDanioConCultivadorDeLaEspada() {
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente objetivo = new HechiceroDelVacio(2, "Luis");
		RugidoDeLaBestiaPrimordial rugido = new RugidoDeLaBestiaPrimordial();
		int vidaInicialObjetivo = objetivo.getVidaActual();

		rugido.aplicar(ejecutor, objetivo);

		assertEquals(vidaInicialObjetivo - 48, objetivo.getVidaActual());
	}

	@Test
	void rechazaEjecutorUObjetivoNulo() {
		RugidoDeLaBestiaPrimordial rugido = new RugidoDeLaBestiaPrimordial();
		Combatiente combatiente = new HechiceroDelVacio(1, "Ana");

		assertThrows(NullPointerException.class, () -> rugido.aplicar(null, combatiente));
		assertThrows(NullPointerException.class, () -> rugido.aplicar(combatiente, null));
	}
}
