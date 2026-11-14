package acontecimientos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CanalAcontecimientosTest {

	@Test
	void notificaALosObservadoresEnElOrdenDeSuscripcion() {
		List<String> ordenNotificacion = new ArrayList<>();
		CanalAcontecimientos canal = new CanalAcontecimientos();

		CronistaDeLaSecta primero = new CronistaDeLaSecta() {
			@Override
			public void recibirInicioCombate(InicioCombate evento) {
				ordenNotificacion.add("primero");
				super.recibirInicioCombate(evento);
			}
		};

		CronistaDeLaSecta segundo = new CronistaDeLaSecta() {
			@Override
			public void recibirInicioCombate(InicioCombate evento) {
				ordenNotificacion.add("segundo");
				super.recibirInicioCombate(evento);
			}
		};

		canal.suscribir(primero);
		canal.suscribir(segundo);

		InicioCombate inicio = new InicioCombate(1, 1, 2);
		canal.publicar(inicio);

		assertEquals(List.of("primero", "segundo"), ordenNotificacion);
		assertEquals(List.of(inicio), primero.getHistorial());
		assertEquals(List.of(inicio), segundo.getHistorial());
	}

	@Test
	void cadaAcontecimientoLlegaAlCallbackQueLeCorresponde() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		canal.suscribir(cronista);

		InicioCombate inicio = new InicioCombate(1, 1, 2);
		AccionRealizada accionAtaque = new AccionRealizada(1, 1, 1, "Aurelio", 3, "Lyra", TipoAccion.ATAQUE_BASICO, "", 0,
				12, 100, 40, 88, 40);
		AccionRealizada accionTecnica = new AccionRealizada(1, 1, 1, "Aurelio", 3, "Lyra", TipoAccion.TECNICA,
				"Corte de los Mil Amaneceres", 10, 25, 100, 30, 63, 40);
		Eliminacion eliminacion = new Eliminacion(1, 3, 2, 4, "Kael");
		ResultadoCombate resultado = new ResultadoCombate(1, 3, 1, 2, 1, 80, 0);
		Proclamacion proclamacion = new Proclamacion(1, 1, "Aurelio", 2, "Nébula");

		canal.publicar(inicio);
		canal.publicar(accionAtaque);
		canal.publicar(accionTecnica);
		canal.publicar(eliminacion);
		canal.publicar(resultado);
		canal.publicar(proclamacion);

		assertEquals(List.of(inicio, accionAtaque, accionTecnica, eliminacion, resultado, proclamacion),
				cronista.getHistorial());
	}

	@Test
	void conservaElOrdenDeLosAcontecimientosPublicados() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		canal.suscribir(cronista);

		InicioCombate inicio = new InicioCombate(1, 1, 2);
		AccionRealizada accion = new AccionRealizada(1, 1, 1, "Aurelio", 3, "Lyra", TipoAccion.ATAQUE_BASICO, "", 0,
				12, 100, 40, 88, 40);
		Eliminacion eliminacion = new Eliminacion(1, 3, 2, 4, "Kael");
		ResultadoCombate resultado = new ResultadoCombate(1, 3, 1, 2, 1, 80, 0);

		canal.publicar(inicio);
		canal.publicar(accion);
		canal.publicar(eliminacion);
		canal.publicar(resultado);

		assertEquals(List.of(inicio, accion, eliminacion, resultado), cronista.getHistorial());
	}

	@Test
	void unNuevoInteresadoSeSumaSinCambiarElCanal() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		CronistaDeLaSecta original = new CronistaDeLaSecta();
		CronistaDeLaSecta nuevo = new CronistaDeLaSecta();

		canal.suscribir(original);
		InicioCombate inicio = new InicioCombate(1, 1, 2);
		canal.publicar(inicio);

		canal.suscribir(nuevo);
		Eliminacion eliminacion = new Eliminacion(1, 3, 2, 4, "Kael");
		canal.publicar(eliminacion);

		assertEquals(List.of(inicio, eliminacion), original.getHistorial());
		assertEquals(List.of(eliminacion), nuevo.getHistorial());
	}

	@Test
	void rechazaUnObservadorNuloOUnaInstanciaRepetida() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		canal.suscribir(cronista);

		assertThrows(NullPointerException.class, () -> canal.suscribir(null));
		assertThrows(IllegalArgumentException.class, () -> canal.suscribir(cronista));
	}

	@Test
	void unaInstanciaRepetidaNoSeNotificaDosVeces() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		canal.suscribir(cronista);

		try {
			canal.suscribir(cronista);
		} catch (IllegalArgumentException esperada) {
			// Se rechaza la repetición.
		}

		canal.publicar(new InicioCombate(1, 1, 2));

		assertEquals(1, cronista.getHistorial().size());
	}

	@Test
	void rechazaPublicarUnAcontecimientoNulo() {
		CanalAcontecimientos canal = new CanalAcontecimientos();

		assertThrows(NullPointerException.class, () -> canal.publicar(null));
	}

	@Test
	void publicarSinObservadoresNoFalla() {
		CanalAcontecimientos canal = new CanalAcontecimientos();

		assertDoesNotThrow(() -> canal.publicar(new InicioCombate(1, 1, 2)));
	}

	@Test
	void publicarTodosLosTiposDeAcontecimientosSinObservadores() {
		CanalAcontecimientos canal = new CanalAcontecimientos();

		assertDoesNotThrow(() -> {
			canal.publicar(new InicioCombate(1, 1, 2));
			canal.publicar(new AccionRealizada(1, 1, 1, "Aurelio", 3, "Lyra", TipoAccion.ATAQUE_BASICO, "", 0, 12, 100, 40,
					88, 40));
			canal.publicar(new Eliminacion(1, 3, 2, 4, "Kael"));
			canal.publicar(new ResultadoCombate(1, 3, 1, 2, 1, 80, 0));
			canal.publicar(new Proclamacion(1, 1, "Aurelio", 2, "Nébula"));
		});
	}

	@Test
	void permiteSuscribirMultiplesObservadoresDistintos() {
		CanalAcontecimientos canal = new CanalAcontecimientos();
		CronistaDeLaSecta cronista1 = new CronistaDeLaSecta();
		CronistaDeLaSecta cronista2 = new CronistaDeLaSecta();

		assertDoesNotThrow(() -> {
			canal.suscribir(cronista1);
			canal.suscribir(cronista2);
		});

		canal.publicar(new InicioCombate(1, 1, 2));

		assertEquals(1, cronista1.getHistorial().size());
		assertEquals(1, cronista2.getHistorial().size());
	}
}
