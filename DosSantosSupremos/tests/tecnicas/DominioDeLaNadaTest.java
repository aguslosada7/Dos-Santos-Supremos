package tecnicas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import combatientes.Combatiente;
import combatientes.CultivadorDeLaEspada;
import combatientes.HechiceroDelVacio;
import equipamiento.ArmaduraDorada;

class DominioDeLaNadaTest {
	@Test
	void esUnaTecnicaOfensivaQueCuestaDoceYHaceVeinte() {
		DominioDeLaNada dominio = new DominioDeLaNada();

		assertEquals("Dominio de la Nada", dominio.getNombre());
		assertEquals(CategoriaTecnica.OFENSIVA, dominio.getCategoria());
		assertEquals(12, dominio.getCostoEnergia());
		assertEquals(20, dominio.getDanio());
	}

	@Test
	void quitaVeinteDeVidaYOchoDeEnergiaAlObjetivo() {
		Combatiente ejecutor = new HechiceroDelVacio(1, "Nébula");
		Combatiente objetivo = new HechiceroDelVacio(2, "Luis");
		DominioDeLaNada dominio = new DominioDeLaNada();
		int vidaInicialObjetivo = objetivo.getVidaActual();
		int energiaInicialObjetivo = objetivo.getEnergiaActual();

		dominio.aplicar(ejecutor, objetivo);

		assertEquals(vidaInicialObjetivo - 20, objetivo.getVidaActual());
		assertEquals(energiaInicialObjetivo - 8, objetivo.getEnergiaActual());
	}

	@Test
	void potenciaElDanioConCultivadorSinAlterarElDrenajeDeEnergia() {
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente objetivo = new HechiceroDelVacio(2, "Luis");
		DominioDeLaNada dominio = new DominioDeLaNada();
		int vidaInicialObjetivo = objetivo.getVidaActual();
		int energiaInicialObjetivo = objetivo.getEnergiaActual();

		dominio.aplicar(ejecutor, objetivo);

		assertEquals(vidaInicialObjetivo - 24, objetivo.getVidaActual());
		assertEquals(energiaInicialObjetivo - 8, objetivo.getEnergiaActual());
	}

	@Test
	void drenaEnergiaSinBajarDeCero() {
		Combatiente ejecutor = new HechiceroDelVacio(1, "Nébula");
		Combatiente objetivo = new HechiceroDelVacio(2, "Luis");
		DominioDeLaNada dominio = new DominioDeLaNada();

		for (int i = 0; i < 6; i++) {
			dominio.aplicar(ejecutor, objetivo);
		}

		assertEquals(0, objetivo.getEnergiaActual());
	}

	@Test
	void drenaEnergiaSinBajarDeCeroCuandoElObjetivoSigueEnPie() {
		Combatiente ejecutor = new HechiceroDelVacio(1, "Nébula");
		Combatiente objetivo = new HechiceroDelVacio(2, "Luis");
		objetivo.equipar(new ArmaduraDorada());
		DominioDeLaNada dominio = new DominioDeLaNada();

		for (int i = 0; i < 6; i++) {
			dominio.aplicar(ejecutor, objetivo);
		}

		assertEquals(4, objetivo.getVidaActual());
		assertEquals(0, objetivo.getEnergiaActual());
	}

	@Test
	void rechazaEjecutorUObjetivoNulo() {
		DominioDeLaNada dominio = new DominioDeLaNada();
		Combatiente combatiente = new HechiceroDelVacio(1, "Nébula");

		assertThrows(NullPointerException.class, () -> dominio.aplicar(null, combatiente));
		assertThrows(NullPointerException.class, () -> dominio.aplicar(combatiente, null));
	}
}
