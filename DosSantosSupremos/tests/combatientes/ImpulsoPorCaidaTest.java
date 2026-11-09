package combatientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ImpulsoPorCaidaTest {
	@Test
	void mantieneElDanioSiNoSeActivo() {
		ImpulsoPorCaida impulso = new ImpulsoPorCaida(10);

		assertEquals(20, impulso.modificarProximoDanio(20));
	}

	@Test
	void bonificaSoloElProximoAtaque() {
		ImpulsoPorCaida impulso = new ImpulsoPorCaida(10);
		impulso.activar();

		assertEquals(30, impulso.modificarProximoDanio(20));
		assertEquals(20, impulso.modificarProximoDanio(20));
	}

	@Test
	void reiniciarQuitaLaBonificacion() {
		ImpulsoPorCaida impulso = new ImpulsoPorCaida(10);
		impulso.activar();
		impulso.reiniciar();

		assertEquals(20, impulso.modificarProximoDanio(20));
	}

	@Test
	void rechazaValoresNegativos() {
		ImpulsoPorCaida impulso = new ImpulsoPorCaida(10);

		assertThrows(IllegalArgumentException.class, () -> new ImpulsoPorCaida(-1));
		assertThrows(IllegalArgumentException.class, () -> impulso.modificarProximoDanio(-1));
	}
}
