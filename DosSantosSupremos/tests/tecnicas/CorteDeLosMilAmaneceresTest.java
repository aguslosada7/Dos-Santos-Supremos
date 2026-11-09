package tecnicas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import combatientes.Combatiente;
import combatientes.HechiceroDelVacio;

class CorteDeLosMilAmaneceresTest {
	@Test
	void esUnaTecnicaOfensivaQueCuestaOcho() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();

		assertEquals("Corte de los Mil Amaneceres", corte.getNombre());
		assertEquals(CategoriaTecnica.OFENSIVA, corte.getCategoria());
		assertEquals(8, corte.getCostoEnergia());
	}

	@Test
	void quitaVeinticuatroDeVidaAlObjetivo() {
		Combatiente ejecutor = new HechiceroDelVacio(1, "Ana");
		Combatiente objetivo = new HechiceroDelVacio(2, "Luis");
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		int vidaInicial = objetivo.getVidaActual();

		corte.aplicar(ejecutor, objetivo);

		assertEquals(vidaInicial - 24, objetivo.getVidaActual());
	}
}
