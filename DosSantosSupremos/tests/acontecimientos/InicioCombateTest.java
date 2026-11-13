package acontecimientos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InicioCombateTest {

	@Test
	void guardaRondaEIdsDeParejas() {
		InicioCombate evento = new InicioCombate(1, 10, 20);

		assertEquals(1, evento.ronda());
		assertEquals(10, evento.idParejaA());
		assertEquals(20, evento.idParejaB());
	}

	@Test
	void rechazaRondaMenorOIgualACero() {
		assertThrows(IllegalArgumentException.class, () -> new InicioCombate(0, 1, 2));
		assertThrows(IllegalArgumentException.class, () -> new InicioCombate(-1, 1, 2));
	}

	@Test
	void rechazaIdsDeParejaNoPositivos() {
		assertThrows(IllegalArgumentException.class, () -> new InicioCombate(1, 0, 2));
		assertThrows(IllegalArgumentException.class, () -> new InicioCombate(1, -1, 2));
		assertThrows(IllegalArgumentException.class, () -> new InicioCombate(1, 1, 0));
		assertThrows(IllegalArgumentException.class, () -> new InicioCombate(1, 1, -2));
	}

	@Test
	void rechazaParejasConElMismoId() {
		assertThrows(IllegalArgumentException.class, () -> new InicioCombate(1, 5, 5));
	}

	@Test
	void notificaAlObservador() {
		InicioCombate evento = new InicioCombate(2, 3, 4);

		class ObservadorPrueba implements ObservadorAcontecimientos {
			boolean fueNotificado = false;
			InicioCombate recibido = null;

			@Override
			public void recibirInicioCombate(InicioCombate e) {
				this.fueNotificado = true;
				this.recibido = e;
			}
			
			@Override
			public void recibirAccionRealizada(AccionRealizada e) {}
			
			@Override
			public void recibirEliminacion(Eliminacion e) {}
			
			@Override
			public void recibirResultadoCombate(ResultadoCombate e) {}
			
			@Override
			public void recibirProclamacion(Proclamacion e) {}
		}

		ObservadorPrueba observador = new ObservadorPrueba();
		evento.notificarA(observador);

		assertTrue(observador.fueNotificado);
		assertEquals(evento, observador.recibido);
	}

	@Test
	void dosInstanciasConMismosDatos() {
		InicioCombate primero = new InicioCombate(1, 10, 20);
		InicioCombate segundo = new InicioCombate(1, 10, 20);

		assertEquals(primero, segundo);
		assertEquals(primero.hashCode(), segundo.hashCode());
	}

	@Test
	void dosInstanciasConDistintosDatos() {
		InicioCombate primero = new InicioCombate(1, 10, 20);
		InicioCombate segundo = new InicioCombate(2, 10, 20);

		assertNotEquals(primero, segundo);
	}
}
