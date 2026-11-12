package acontecimientos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class CronistaDeLaSectaTest {
	@Test
	void sinAcontecimientosElHistorialYLaTablaEstanVacios() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();

		assertTrue(cronista.getHistorial().isEmpty());
		assertTrue(cronista.generarTablaPosiciones().isEmpty());
	}

	@Test
	void registraLosAcontecimientosEnElOrdenEnQueLlegan() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		CanalAcontecimientos canal = new CanalAcontecimientos();
		canal.suscribir(cronista);
		InicioCombate inicio = new InicioCombate(1, 1, 2);
		AccionRealizada accion = new AccionRealizada(1, 1, 1, "Aurelio", 3, "Lyra", TipoAccion.ATAQUE_BASICO, "", 0,
				12, 100, 40, 88, 40);
		Eliminacion eliminacion = new Eliminacion(1, 5, 2, 3, "Lyra");
		ResultadoCombate resultado = new ResultadoCombate(1, 5, 1, 2, 1, 100, 0);
		Proclamacion proclamacion = new Proclamacion(1, 1, "Aurelio", 2, "Nébula");

		canal.publicar(inicio);
		canal.publicar(accion);
		canal.publicar(eliminacion);
		canal.publicar(resultado);
		canal.publicar(proclamacion);

		assertEquals(List.of(inicio, accion, eliminacion, resultado, proclamacion), cronista.getHistorial());
	}

	@Test
	void elHistorialDevueltoNoSePuedeModificar() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		cronista.recibirInicioCombate(new InicioCombate(1, 1, 2));
		List<Acontecimiento> historial = cronista.getHistorial();

		assertThrows(UnsupportedOperationException.class, () -> historial.add(new InicioCombate(1, 3, 4)));
		assertThrows(UnsupportedOperationException.class, () -> historial.remove(0));
	}

	@Test
	void unaConsultaAnteriorNoCambiaConLosNuevosAcontecimientos() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		cronista.recibirInicioCombate(new InicioCombate(1, 1, 2));
		List<Acontecimiento> anterior = cronista.getHistorial();

		cronista.recibirInicioCombate(new InicioCombate(1, 3, 4));

		assertEquals(1, anterior.size());
		assertEquals(2, cronista.getHistorial().size());
	}

	@Test
	void rechazaAcontecimientosNulos() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();

		assertThrows(NullPointerException.class, () -> cronista.recibirInicioCombate(null));
		assertThrows(NullPointerException.class, () -> cronista.recibirAccionRealizada(null));
		assertThrows(NullPointerException.class, () -> cronista.recibirEliminacion(null));
		assertThrows(NullPointerException.class, () -> cronista.recibirResultadoCombate(null));
		assertThrows(NullPointerException.class, () -> cronista.recibirProclamacion(null));
		assertTrue(cronista.getHistorial().isEmpty());
	}

	@Test
	void unResultadoDejaLaPosicionDeLasDosParejasConSusValoresFinales() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();

		cronista.recibirResultadoCombate(new ResultadoCombate(1, 12, 1, 2, 1, 70, 0));

		assertEquals(List.of(new RegistroPosicion(1, 1, 70), new RegistroPosicion(2, 1, 0)),
				cronista.generarTablaPosiciones());
	}

	@Test
	void laTablaQuedaOrdenadaPorUltimaRondaYVidaTotalFinal() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();

		// Ronda 1: ganan las parejas 1 y 3.
		cronista.recibirResultadoCombate(new ResultadoCombate(1, 9, 1, 2, 1, 80, 0));
		cronista.recibirResultadoCombate(new ResultadoCombate(1, 9, 3, 4, 3, 60, 0));
		// Ronda 2 (final): gana la pareja 1.
		cronista.recibirResultadoCombate(new ResultadoCombate(2, 14, 1, 3, 1, 50, 0));

		List<RegistroPosicion> tabla = cronista.generarTablaPosiciones();

		assertEquals(List.of(1, 3, 2, 4), tabla.stream().map(RegistroPosicion::idPareja).toList());
		assertEquals(List.of(2, 2, 1, 1), tabla.stream().map(RegistroPosicion::rondaUltimoCombate).toList());
		assertEquals(List.of(50, 0, 0, 0), tabla.stream().map(RegistroPosicion::vidaTotalUltimoCombate).toList());
	}

	@Test
	void enLaMismaRondaDesempataLaVidaTotalYLuegoElId() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();

		cronista.recibirResultadoCombate(new ResultadoCombate(1, 50, 1, 2, 2, 30, 45));
		cronista.recibirResultadoCombate(new ResultadoCombate(1, 50, 3, 4, 3, 45, 20));

		List<RegistroPosicion> tabla = cronista.generarTablaPosiciones();

		assertEquals(List.of(2, 3, 1, 4), tabla.stream().map(RegistroPosicion::idPareja).toList());
	}

	@Test
	void unaParejaApareceUnaSolaVezConSuUltimoResultado() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();

		cronista.recibirResultadoCombate(new ResultadoCombate(1, 9, 1, 2, 1, 80, 0));
		cronista.recibirResultadoCombate(new ResultadoCombate(2, 9, 1, 3, 1, 35, 0));

		List<RegistroPosicion> tabla = cronista.generarTablaPosiciones();
		RegistroPosicion primera = tabla.get(0);

		assertEquals(3, tabla.size());
		assertEquals(new RegistroPosicion(1, 2, 35), primera);
	}

	@Test
	void laTablaDevueltaNoSePuedeModificar() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		cronista.recibirResultadoCombate(new ResultadoCombate(1, 9, 1, 2, 1, 80, 0));
		List<RegistroPosicion> tabla = cronista.generarTablaPosiciones();

		assertThrows(UnsupportedOperationException.class, () -> tabla.add(new RegistroPosicion(9, 1, 0)));
		assertThrows(UnsupportedOperationException.class, () -> tabla.clear());
	}

	@Test
	void soloLosResultadosActualizanLaTabla() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();

		cronista.recibirInicioCombate(new InicioCombate(1, 1, 2));
		cronista.recibirEliminacion(new Eliminacion(1, 3, 2, 4, "Kael"));
		cronista.recibirProclamacion(new Proclamacion(1, 1, "Aurelio", 2, "Nébula"));

		assertTrue(cronista.generarTablaPosiciones().isEmpty());
		assertEquals(3, cronista.getHistorial().size());
	}

	@Test
	void dosCronistasQueRecibenLoMismoTienenElMismoHistorial() {
		CronistaDeLaSecta primero = new CronistaDeLaSecta();
		CronistaDeLaSecta segundo = new CronistaDeLaSecta();

		for (CronistaDeLaSecta cronista : List.of(primero, segundo)) {
			cronista.recibirInicioCombate(new InicioCombate(1, 1, 2));
			cronista.recibirResultadoCombate(new ResultadoCombate(1, 9, 1, 2, 1, 80, 0));
		}

		assertEquals(primero.getHistorial(), segundo.getHistorial());
		assertEquals(primero.generarTablaPosiciones(), segundo.generarTablaPosiciones());
	}
}
