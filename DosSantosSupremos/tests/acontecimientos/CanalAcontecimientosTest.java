package acontecimientos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CanalAcontecimientosTest {
	@Test
	void notificaALosObservadoresEnElOrdenDeSuscripcion() {
		List<String> registro = new ArrayList<>();
		CanalAcontecimientos canal = new CanalAcontecimientos();
		canal.suscribir(new ObservadorGrabador("primero", registro));
		canal.suscribir(new ObservadorGrabador("segundo", registro));

		canal.publicar(new InicioCombate(1, 1, 2));

		assertEquals(List.of("primero:inicio", "segundo:inicio"), registro);
	}

	@Test
	void cadaAcontecimientoLlegaAlCallbackQueLeCorresponde() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		ObservadorGrabador observador = new ObservadorGrabador();
		canal.suscribir(observador);

		canal.publicar(new InicioCombate(1, 1, 2));
		canal.publicar(new Eliminacion(1, 3, 2, 4, "Kael"));
		canal.publicar(new ResultadoCombate(1, 3, 1, 2, 1, 80, 0));
		canal.publicar(new Proclamacion(1, 1, "Aurelio", 2, "Nébula"));

		assertEquals(List.of("inicio", "eliminacion", "resultado", "proclamacion"), observador.llamadas);
	}

	@Test
	void conservaElOrdenDeLosAcontecimientosPublicados() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		ObservadorGrabador observador = new ObservadorGrabador();
		canal.suscribir(observador);
		InicioCombate inicio = new InicioCombate(1, 1, 2);
		Eliminacion eliminacion = new Eliminacion(1, 3, 2, 4, "Kael");

		canal.publicar(inicio);
		canal.publicar(eliminacion);

		assertEquals(List.of(inicio, eliminacion), observador.eventos);
	}

	@Test
	void unNuevoInteresadoSeSumaSinCambiarElCanal() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		ObservadorGrabador original = new ObservadorGrabador();
		ObservadorGrabador nuevo = new ObservadorGrabador();
		canal.suscribir(original);
		canal.publicar(new InicioCombate(1, 1, 2));

		canal.suscribir(nuevo);
		canal.publicar(new Eliminacion(1, 3, 2, 4, "Kael"));

		assertEquals(List.of("inicio", "eliminacion"), original.llamadas);
		assertEquals(List.of("eliminacion"), nuevo.llamadas);
	}

	@Test
	void rechazaUnObservadorNuloOUnaInstanciaRepetida() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		ObservadorGrabador observador = new ObservadorGrabador();
		canal.suscribir(observador);

		assertThrows(NullPointerException.class, () -> canal.suscribir(null));
		assertThrows(IllegalArgumentException.class, () -> canal.suscribir(observador));
	}

	@Test
	void unaInstanciaRepetidaNoSeNotificaDosVeces() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		ObservadorGrabador observador = new ObservadorGrabador();
		canal.suscribir(observador);

		try {
			canal.suscribir(observador);
		} catch (IllegalArgumentException esperada) {
			// Se rechaza la repetición.
		}

		canal.publicar(new InicioCombate(1, 1, 2));

		assertEquals(1, observador.llamadas.size());
	}

	@Test
	void rechazaPublicarUnAcontecimientoNulo() {
		CanalAcontecimientos canal = new CanalAcontecimientos();

		assertThrows(NullPointerException.class, () -> canal.publicar(null));
	}

	@Test
	void publicarSinObservadoresNoFalla() {
		CanalAcontecimientos canal = new CanalAcontecimientos();

		canal.publicar(new InicioCombate(1, 1, 2));
	}
}
