package acontecimientos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ResultadoCombateTest {
	@Test
	void guardaLosDatosDelResultado() {
		ResultadoCombate resultado = new ResultadoCombate(2, 14, 1, 3, 1, 50, 0);

		assertEquals(2, resultado.ronda());
		assertEquals(14, resultado.numeroAsalto());
		assertEquals(1, resultado.idParejaA());
		assertEquals(3, resultado.idParejaB());
		assertEquals(1, resultado.idParejaGanadora());
		assertEquals(50, resultado.vidaTotalFinalParejaA());
		assertEquals(0, resultado.vidaTotalFinalParejaB());
	}

	@Test
	void permiteEmpateDeVidasCuandoGanaLaParejaB() {
		ResultadoCombate resultado = new ResultadoCombate(1, 50, 2, 5, 5, 40, 40);

		assertEquals(5, resultado.idParejaGanadora());
		assertEquals(40, resultado.vidaTotalFinalParejaA());
		assertEquals(40, resultado.vidaTotalFinalParejaB());
	}

	@Test
	void rechazaValoresNoPositivos() {
		assertThrows(IllegalArgumentException.class, () -> new ResultadoCombate(0, 1, 1, 2, 1, 10, 0));
		assertThrows(IllegalArgumentException.class, () -> new ResultadoCombate(1, 0, 1, 2, 1, 10, 0));
		assertThrows(IllegalArgumentException.class, () -> new ResultadoCombate(1, 1, 0, 2, 2, 0, 10));
		assertThrows(IllegalArgumentException.class, () -> new ResultadoCombate(1, 1, 1, 0, 1, 10, 0));
		assertThrows(IllegalArgumentException.class, () -> new ResultadoCombate(1, 1, 1, 2, 0, 10, 0));
	}

	@Test
	void rechazaParejasIgualesOGanadoraAjenaAlCombate() {
		assertThrows(IllegalArgumentException.class, () -> new ResultadoCombate(1, 1, 2, 2, 2, 50, 0));
		assertThrows(IllegalArgumentException.class, () -> new ResultadoCombate(1, 1, 1, 2, 3, 50, 0));
	}

	@Test
	void rechazaVidasFinalesNegativas() {
		assertThrows(IllegalArgumentException.class, () -> new ResultadoCombate(1, 1, 1, 2, 1, -1, 0));
		assertThrows(IllegalArgumentException.class, () -> new ResultadoCombate(1, 1, 1, 2, 1, 0, -1));
	}

	@Test
	void dosResultadosConLosMismosDatosSonIguales() {
		ResultadoCombate primero = new ResultadoCombate(1, 9, 1, 2, 1, 80, 0);
		ResultadoCombate segundo = new ResultadoCombate(1, 9, 1, 2, 1, 80, 0);

		assertEquals(primero, segundo);
		assertEquals(primero.hashCode(), segundo.hashCode());
	}

	@Test
	void notificaAlObservadorYRechazaNulo() {
		ResultadoCombate resultado = new ResultadoCombate(1, 9, 1, 2, 1, 80, 0);
		ObservadorRegistrador observador = new ObservadorRegistrador();

		resultado.notificarA(observador);

		assertSame(resultado, observador.ultimoResultado);
		assertThrows(NullPointerException.class, () -> resultado.notificarA(null));
	}

	private static class ObservadorRegistrador implements ObservadorAcontecimientos {
		private ResultadoCombate ultimoResultado;

		@Override
		public void recibirInicioCombate(InicioCombate evento) {
		}

		@Override
		public void recibirAccionRealizada(AccionRealizada evento) {
		}

		@Override
		public void recibirEliminacion(Eliminacion evento) {
		}

		@Override
		public void recibirResultadoCombate(ResultadoCombate evento) {
			this.ultimoResultado = evento;
		}

		@Override
		public void recibirProclamacion(Proclamacion evento) {
		}
	}
}
