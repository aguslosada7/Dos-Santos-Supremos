package combatientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CreadorCombatientesTest {

	private static class EscuelaDePrueba extends Escuela {
		boolean fuePreparado = false;
		Combatiente combatientePreparado = null;

		EscuelaDePrueba() {
			super();
		}

		public void preparar(Combatiente combatiente) {
			this.fuePreparado = true;
			this.combatientePreparado = combatiente;
		}
	}

	@Test
	void creaUnHechiceroDelVacio() {
		CreadorCombatientes creador = new CreadorCombatientes();
		EscuelaDePrueba escuela = new EscuelaDePrueba();

		Combatiente combatiente = creador.crear("Hechicero del Vacío", "Nébula", escuela);

		assertInstanceOf(HechiceroDelVacio.class, combatiente);
		assertEquals("Nébula", combatiente.getNombre());
		assertEquals(1, combatiente.getId());
	}

	@Test
	void creaUnCultivadorDeLaEspada() {
		CreadorCombatientes creador = new CreadorCombatientes();
		EscuelaDePrueba escuela = new EscuelaDePrueba();

		Combatiente combatiente = creador.crear("Cultivador de la Espada", "Aurelio", escuela);

		assertInstanceOf(CultivadorDeLaEspada.class, combatiente);
		assertEquals("Aurelio", combatiente.getNombre());
		assertEquals(1, combatiente.getId());
	}

	@Test
	void creaUnMonjeDelCuerpoCelestial() {
		CreadorCombatientes creador = new CreadorCombatientes();
		EscuelaDePrueba escuela = new EscuelaDePrueba();

		Combatiente combatiente = creador.crear("Monje del Cuerpo Celestial", "Kael", escuela);

		assertInstanceOf(MonjeDelCuerpoCelestial.class, combatiente);
		assertEquals("Kael", combatiente.getNombre());
		assertEquals(1, combatiente.getId());
	}

	@Test
	void asignaIdentificadoresConsecutivos() {
		CreadorCombatientes creador = new CreadorCombatientes();
		EscuelaDePrueba escuela = new EscuelaDePrueba();

		Combatiente primero = creador.crear("Cultivador de la Espada", "Aurelio", escuela);
		Combatiente segundo = creador.crear("Hechicero del Vacío", "Nébula", escuela);
		Combatiente tercero = creador.crear("Monje del Cuerpo Celestial", "Kael", escuela);

		assertEquals(1, primero.getId());
		assertEquals(2, segundo.getId());
		assertEquals(3, tercero.getId());
	}

	@Test
	void delegaLaPreparacionEnLaEscuela() {
		CreadorCombatientes creador = new CreadorCombatientes();
		EscuelaDePrueba escuela = new EscuelaDePrueba();

		Combatiente combatiente = creador.crear("Cultivador de la Espada", "Aurelio", escuela);

		assertTrue(escuela.fuePreparado);
		assertEquals(combatiente, escuela.combatientePreparado);
	}

	@Test
	void rechazaTipoDesconocido() {
		CreadorCombatientes creador = new CreadorCombatientes();
		EscuelaDePrueba escuela = new EscuelaDePrueba();

		assertThrows(IllegalArgumentException.class, () -> creador.crear("Ninja de las Sombras", "Sombra", escuela));
	}

	@Test
	void rechazaNombreNuloOEnBlanco() {
		CreadorCombatientes creador = new CreadorCombatientes();
		EscuelaDePrueba escuela = new EscuelaDePrueba();

		assertThrows(NullPointerException.class, () -> creador.crear("Cultivador de la Espada", null, escuela));
		assertThrows(IllegalArgumentException.class, () -> creador.crear("Cultivador de la Espada", "   ", escuela));
	}

	@Test
	void rechazaEscuelaNula() {
		CreadorCombatientes creador = new CreadorCombatientes();

		assertThrows(NullPointerException.class, () -> creador.crear("Cultivador de la Espada", "Aurelio", null));
	}
}
