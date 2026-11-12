package torneo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class EstadoTorneoTest {
	@Test
	void tienePendienteEnCursoYFinalizado() {
		EstadoTorneo[] estados = { EstadoTorneo.PENDIENTE, EstadoTorneo.EN_CURSO, EstadoTorneo.FINALIZADO };

		assertArrayEquals(estados, EstadoTorneo.values());
	}
}
