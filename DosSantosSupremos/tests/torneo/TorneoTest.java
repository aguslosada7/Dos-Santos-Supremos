package torneo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import acontecimientos.Acontecimiento;
import acontecimientos.CronistaDeLaSecta;
import acontecimientos.ObservadorAcontecimientos;
import acontecimientos.Proclamacion;
import combatientes.Combatiente;
import combatientes.CultivadorDeLaEspada;
import combatientes.HechiceroDelVacio;
import tecnicas.CorteDeLosMilAmaneceres;

class TorneoTest {

	@Test
	void inscripcionesDuplicadasYCantidadDeParejasInvalidaSeRechazan() throws InscripcionInvalidaException {
		Torneo torneo = new Torneo();
		Pareja p1 = crearPareja(1);
		torneo.inscribir(p1);

		assertThrows(Exception.class, () -> torneo.inscribir(new Pareja(1, new HechiceroDelVacio(10, "A"), new HechiceroDelVacio(11, "B"))));

		Combatiente combatienteCompartido = p1.getCombatienteA();
		assertThrows(Exception.class, () -> torneo.inscribir(new Pareja(2, combatienteCompartido, new HechiceroDelVacio(12, "C"))));

		assertThrows(IllegalStateException.class, () -> torneo.iniciar());

		torneo.inscribir(crearPareja(2));
		assertThrows(IllegalStateException.class, () -> torneo.iniciar());

		torneo.inscribir(crearPareja(3));
		assertThrows(IllegalStateException.class, () -> torneo.iniciar());
	}

	@Test
	void rechazaInscribirMasDeOchoParejas() throws InscripcionInvalidaException {
		Torneo torneo = new Torneo();
		for (int i = 1; i <= 8; i++) {
			torneo.inscribir(crearPareja(i));
		}

		assertThrows(Exception.class, () -> torneo.inscribir(crearPareja(9)));
	}

	@Test
	void rechazaInscripcionesSiElTorneoYaInicioOFinalizo() throws InscripcionInvalidaException {
		Torneo torneo = new Torneo();
		for (int i = 1; i <= 4; i++) {
			torneo.inscribir(crearPareja(i));
		}
		torneo.iniciar();

		assertThrows(Exception.class, () -> torneo.inscribir(crearPareja(5)));
	}

	@Test
	void torneoConCuatroParejasJuegaDosRondasYProclamaCampeon() throws InscripcionInvalidaException {
		Torneo torneo = new Torneo();
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		torneo.suscribir(cronista);

		for (int i = 1; i <= 4; i++) {
			torneo.inscribir(crearPareja(i));
		}

		torneo.iniciar();

		assertEquals(EstadoTorneo.FINALIZADO, torneo.getEstado());
		assertNotNull(torneo.getParejaCampeona());
		assertEquals(2, torneo.getRondas().size());

		List<Acontecimiento> historial = cronista.getHistorial();
		assertFalse(historial.isEmpty());
		assertInstanceOf(Proclamacion.class, historial.get(historial.size() - 1));
	}

	@Test
	void torneoConOchoParejasJuegaTresRondas() throws InscripcionInvalidaException {
		Torneo torneo = new Torneo();
		for (int i = 1; i <= 8; i++) {
			torneo.inscribir(crearPareja(i));
		}

		torneo.iniciar();

		assertEquals(EstadoTorneo.FINALIZADO, torneo.getEstado());
		assertNotNull(torneo.getParejaCampeona());
		assertEquals(3, torneo.getRondas().size());
	}

	@Test
	void dosTorneosArmadosIgualProducenElMismoCampeonEHistorial() throws InscripcionInvalidaException {
		Torneo torneo1 = new Torneo();
		CronistaDeLaSecta cronista1 = new CronistaDeLaSecta();
		torneo1.suscribir(cronista1);
		poblarTorneoDeterminista(torneo1);
		torneo1.iniciar();

		Torneo torneo2 = new Torneo();
		CronistaDeLaSecta cronista2 = new CronistaDeLaSecta();
		torneo2.suscribir(cronista2);
		poblarTorneoDeterminista(torneo2);
		torneo2.iniciar();

		assertEquals(torneo1.getParejaCampeona().getId(), torneo2.getParejaCampeona().getId());
		assertEquals(cronista1.getHistorial(), cronista2.getHistorial());
	}

	@Test
	void publicaProclamacionDeLosCampeonesAlFinalizar() throws InscripcionInvalidaException {
		Torneo torneo = new Torneo();
		final List<Proclamacion> proclamaciones = new ArrayList<>();

		torneo.suscribir(new ObservadorAcontecimientos() {
			@Override
			public void recibirInicioCombate(acontecimientos.InicioCombate e) {}
			
			@Override
			public void recibirAccionRealizada(acontecimientos.AccionRealizada e) {}
			
			@Override
			public void recibirEliminacion(acontecimientos.Eliminacion e) {}
			
			@Override
			public void recibirResultadoCombate(acontecimientos.ResultadoCombate e) {}
			
			@Override
			public void recibirProclamacion(Proclamacion evento) {
				proclamaciones.add(evento);
			}
		});

		for (int i = 1; i <= 4; i++) {
			torneo.inscribir(crearPareja(i));
		}
		torneo.iniciar();

		assertEquals(1, proclamaciones.size());
		assertEquals(torneo.getParejaCampeona().getId(), proclamaciones.get(0).idParejaCampeona());
	}

	private Pareja crearPareja(int id) {
		Combatiente c1 = new HechiceroDelVacio(id * 2 - 1, "Combatiente " + (id * 2 - 1));
		Combatiente c2 = new HechiceroDelVacio(id * 2, "Combatiente " + (id * 2));
		return new Pareja(id, c1, c2);
	}

	private void poblarTorneoDeterminista(Torneo torneo) throws InscripcionInvalidaException {
		for (int i = 1; i <= 4; i++) {
			Combatiente c1 = new CultivadorDeLaEspada(i * 2 - 1, "Guerrero " + (i * 2 - 1));
			Combatiente c2 = new HechiceroDelVacio(i * 2, "Mago " + (i * 2));
			c1.aprender(new CorteDeLosMilAmaneceres());
			c2.aprender(new CorteDeLosMilAmaneceres());
			torneo.inscribir(new Pareja(i, c1, c2));
		}
	}

	private void assertFalse(boolean condition) {
		org.junit.jupiter.api.Assertions.assertFalse(condition);
	}

	private void assertInstanceOf(Class<?> expectedType, Object obj) {
		org.junit.jupiter.api.Assertions.assertInstanceOf(expectedType, obj);
	}
}
