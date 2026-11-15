package torneo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import combatientes.Combatiente;
import combatientes.CultivadorDeLaEspada;
import combatientes.HechiceroDelVacio;
import equipamiento.AnilloEspiritual;
import equipamiento.ArmaduraDorada;
import equipamiento.EspadaCelestial;
import equipamiento.Equipamiento;
import equipamiento.SelloDelFenix;
import tecnicas.CorteDeLosMilAmaneceres;
import tecnicas.MeditacionDelInmortal;
import tecnicas.RugidoDeLaBestiaPrimordial;
import tecnicas.Tecnica;


class EscuelaTest {
	@Test
	void conservaElNombreYElOrdenDeLasTecnicas() {
		Escuela escuela = new Escuela("Escuela de prueba",
				List.of(new RugidoDeLaBestiaPrimordial(), new CorteDeLosMilAmaneceres()), List.of());

		assertEquals("Escuela de prueba", escuela.getNombre());
		assertEquals(new RugidoDeLaBestiaPrimordial(), escuela.getTecnicas().get(0));
		assertEquals(new CorteDeLosMilAmaneceres(), escuela.getTecnicas().get(1));
	}

	@Test
	void noSeAlteraSiCambiaLaListaOriginal() {
		List<Tecnica> tecnicas = new ArrayList<>(List.of(new CorteDeLosMilAmaneceres()));
		Escuela escuela = new Escuela("Escuela de prueba", tecnicas, List.of());

		tecnicas.add(new MeditacionDelInmortal());

		assertEquals(1, escuela.getTecnicas().size());
	}

	@Test
	void entregaListasQueNoSePuedenModificar() {
		Escuela escuela = new Escuela("Escuela de prueba", List.of(new CorteDeLosMilAmaneceres()),
				List.of(new EspadaCelestial()));

		assertThrows(UnsupportedOperationException.class, () -> escuela.getTecnicas().add(new MeditacionDelInmortal()));
		assertThrows(UnsupportedOperationException.class, () -> escuela.getEquipamiento().add(new ArmaduraDorada()));
	}

	@Test
	void rechazaTecnicasRepetidas() {
		assertThrows(IllegalArgumentException.class, () -> new Escuela("Escuela de prueba",
				List.of(new CorteDeLosMilAmaneceres(), new CorteDeLosMilAmaneceres()), List.of()));
	}

	@Test
	void rechazaMasDeTresEquipamientos() {
		List<Equipamiento> cuatro = List.of(new EspadaCelestial(), new ArmaduraDorada(), new AnilloEspiritual(),
				new SelloDelFenix());

		assertThrows(IllegalArgumentException.class, () -> new Escuela("Escuela de prueba", List.of(), cuatro));
	}

	@Test
	void rechazaDosEquipamientosDeLaMismaCategoria() {
		assertThrows(IllegalArgumentException.class,
				() -> new Escuela("Escuela de prueba", List.of(), List.of(new EspadaCelestial(), new EspadaCelestial())));
	}

	@Test
	void rechazaArgumentosNulos() {
		assertThrows(NullPointerException.class, () -> new Escuela(null, List.of(), List.of()));
		assertThrows(NullPointerException.class, () -> new Escuela("Escuela de prueba", null, List.of()));
		assertThrows(NullPointerException.class, () -> new Escuela("Escuela de prueba", List.of(), null));
	}

	@Test
	void preparaCombatientesDeCualquierTipo() {
		Escuela escuela = new Escuela("Escuela de prueba", List.of(new CorteDeLosMilAmaneceres()),
				List.of(new EspadaCelestial()));
		Combatiente cultivador = new CultivadorDeLaEspada(1, "Ilan");
		Combatiente hechicero = new HechiceroDelVacio(2, "Vael");

		escuela.preparar(cultivador);
		escuela.preparar(hechicero);

		assertEquals(1, cultivador.getEquipamientos().size());
		assertEquals(1, hechicero.getEquipamientos().size());
	}

	@Test
	void cadaCombatientePreparadoTieneSuPropioEquipamiento() {
		Escuela escuela = new Escuela("Escuela de prueba", List.of(), List.of(new EspadaCelestial()));
		Combatiente primero = new CultivadorDeLaEspada(1, "Ilan");
		Combatiente segundo = new CultivadorDeLaEspada(2, "Kaito");

		escuela.preparar(primero);
		escuela.preparar(segundo);
		primero.quitar(equipamiento.CategoriaEquipamiento.ARMA);

		assertEquals(0, primero.getEquipamientos().size());
		assertEquals(1, segundo.getEquipamientos().size());
	}
}