package acontecimientos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccionRealizadaTest {
	private AccionRealizada tecnica(int idActor, int idObjetivo, String nombreTecnica) {
		return new AccionRealizada(1, 1, idActor, "Dorian", idObjetivo, "Sora", TipoAccion.TECNICA, nombreTecnica, 8,
				24, 100, 32, 76, 40);
	}

	private AccionRealizada ataqueBasico(int idActor, int idObjetivo) {
		return new AccionRealizada(1, 1, idActor, "Dorian", idObjetivo, "Sora", TipoAccion.ATAQUE_BASICO, "", 0, 12,
				100, 40, 88, 40);
	}

	@Test
	void guardaLosDatosDelMomento() {
		AccionRealizada accion = tecnica(1, 2, "Corte de los Mil Amaneceres");

		assertEquals(1, accion.ronda());
		assertEquals(1, accion.numeroAsalto());
		assertEquals("Dorian", accion.nombreActor());
		assertEquals("Sora", accion.nombreObjetivo());
		assertEquals(TipoAccion.TECNICA, accion.tipoAccion());
		assertEquals("Corte de los Mil Amaneceres", accion.nombreTecnica());
		assertEquals(24, accion.danioEfectivo());
		assertEquals(76, accion.vidaObjetivoFinal());
	}

	@Test
	void dosAccionesConLosMismosDatosSonIguales() {
		assertEquals(tecnica(1, 2, "Corte"), tecnica(1, 2, "Corte"));
		assertEquals(tecnica(1, 2, "Corte").hashCode(), tecnica(1, 2, "Corte").hashCode());
	}

	@Test
	void rechazaNumerosNoPositivos() {
		assertThrows(IllegalArgumentException.class, () -> new AccionRealizada(0, 1, 1, "Dorian", 2, "Sora",
				TipoAccion.ATAQUE_BASICO, "", 0, 12, 100, 40, 88, 40));
		assertThrows(IllegalArgumentException.class, () -> ataqueBasico(0, 2));
		assertThrows(IllegalArgumentException.class, () -> ataqueBasico(1, 0));
	}

	@Test
	void rechazaValoresNegativos() {
		assertThrows(IllegalArgumentException.class, () -> new AccionRealizada(1, 1, 1, "Dorian", 2, "Sora",
				TipoAccion.ATAQUE_BASICO, "", -1, 12, 100, 40, 88, 40));
		assertThrows(IllegalArgumentException.class, () -> new AccionRealizada(1, 1, 1, "Dorian", 2, "Sora",
				TipoAccion.ATAQUE_BASICO, "", 0, 12, 100, 40, -5, 40));
	}

	@Test
	void unAtaqueBasicoNoPuedeDirigirseAUnoMismo() {
		assertThrows(IllegalArgumentException.class, () -> ataqueBasico(1, 1));
	}

	@Test
	void unaTecnicaPuedeAplicarseAlPropioEjecutor() {
		AccionRealizada defensa = tecnica(1, 1, "Muro del Cuerpo Dorado");

		assertEquals(defensa.idActor(), defensa.idObjetivo());
	}

	@Test
	void rechazaNombresEnBlanco() {
		assertThrows(IllegalArgumentException.class, () -> new AccionRealizada(1, 1, 1, " ", 2, "Sora",
				TipoAccion.ATAQUE_BASICO, "", 0, 12, 100, 40, 88, 40));
		assertThrows(IllegalArgumentException.class, () -> new AccionRealizada(1, 1, 1, "Dorian", 2, "",
				TipoAccion.ATAQUE_BASICO, "", 0, 12, 100, 40, 88, 40));
	}

	@Test
	void unaTecnicaNecesitaNombreDeTecnica() {
		assertThrows(IllegalArgumentException.class, () -> tecnica(1, 2, ""));
	}

	@Test
	void rechazaTipoDeAccionNulo() {
		assertThrows(IllegalArgumentException.class, () -> new AccionRealizada(1, 1, 1, "Dorian", 2, "Sora", null, "",
				0, 12, 100, 40, 88, 40));
	}

	@Test
	void rechazaUnObservadorNulo() {
		assertThrows(NullPointerException.class, () -> ataqueBasico(1, 2).notificarA(null));
	}
}
