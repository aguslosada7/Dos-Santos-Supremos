package torneo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import combatientes.Combatiente;
import combatientes.HechiceroDelVacio;
import tecnicas.CorteDeLosMilAmaneceres;

class ParejaTest {
	@Test
	void guardaElIdYLosIntegrantes() {
		Combatiente primero = new HechiceroDelVacio(1, "Ana");
		Combatiente segundo = new HechiceroDelVacio(2, "Luis");
		Pareja pareja = new Pareja(1, primero, segundo);

		assertEquals(1, pareja.getId());
		assertEquals(List.of(primero, segundo), pareja.getIntegrantes());
	}

	@Test
	void rechazaIntegrantesConElMismoId() {
		Combatiente primero = new HechiceroDelVacio(1, "Ana");
		Combatiente segundo = new HechiceroDelVacio(1, "Luis");

		assertThrows(IllegalArgumentException.class, () -> new Pareja(1, primero, segundo));
	}

	@Test
	void quedaEliminadaCuandoCaenLosDosIntegrantes() {
		Combatiente primero = new HechiceroDelVacio(1, "Ana");
		Combatiente segundo = new HechiceroDelVacio(2, "Luis");
		Pareja pareja = new Pareja(1, primero, segundo);
		primero.recibirDanio(primero.getVidaActual());

		assertEquals(List.of(segundo), pareja.integrantesEnPie());
		assertEquals(segundo.getVidaActual(), pareja.vidaTotal());
		assertFalse(pareja.estaEliminada());

		segundo.recibirDanio(segundo.getVidaActual());

		assertTrue(pareja.estaEliminada());
		assertEquals(0, pareja.vidaTotal());
	}

	@Test
	void liberaLaTecnicaCuandoCaeElPrimerUsuario() {
		Combatiente primero = new HechiceroDelVacio(1, "Ana");
		Combatiente segundo = new HechiceroDelVacio(2, "Luis");
		Pareja pareja = new Pareja(1, primero, segundo);
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		primero.aprender(corte);
		segundo.aprender(corte);
		pareja.registrarPrimerUsoTecnica(primero, corte);

		assertFalse(pareja.puedeUsarTecnica(segundo, corte));

		primero.recibirDanio(primero.getVidaActual());

		assertTrue(pareja.puedeUsarTecnica(segundo, corte));
	}
}
