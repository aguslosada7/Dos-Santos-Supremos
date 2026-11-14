package torneo;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import combatientes.Combatiente;
import equipamiento.CategoriaEquipamiento;
import equipamiento.Equipamiento;
import tecnicas.Tecnica;

public class Escuela {
	private final String nombre;
	private final List<Tecnica> tecnicas;
	private final List<Equipamiento> equipamiento;
	private static final int MAXIMO_EQUIPAMIENTOS = 3;

	public Escuela(String nombre, List<Tecnica> tecnicas, List<Equipamiento> equipamiento) {
		Objects.requireNonNull(nombre, "El nombre es obligatorio.");
		Objects.requireNonNull(tecnicas, "Las técnicas son obligatorias.");
		Objects.requireNonNull(equipamiento, "El equipamiento es obligatorio.");

		Set<Tecnica> tecnicasVistas = new HashSet<>();
		for (Tecnica tecnica : tecnicas) {
			if (!tecnicasVistas.add(Objects.requireNonNull(tecnica, "No puede haber técnicas nulas."))) {
				throw new IllegalArgumentException("La escuela repite una técnica.");
			}
		}

		if (equipamiento.size() > MAXIMO_EQUIPAMIENTOS) {
			throw new IllegalArgumentException("Una escuela no puede asignar más de 3 equipamientos.");
		}

		Set<CategoriaEquipamiento> categoriasVistas = new HashSet<>();
		for (Equipamiento objeto : equipamiento) {
			Objects.requireNonNull(objeto, "No puede haber equipamientos nulos.");
			if (!categoriasVistas.add(objeto.getCategoria())) {
				throw new IllegalArgumentException("La escuela repite una categoría de equipamiento.");
			}
		}

		this.nombre = nombre;
		this.tecnicas = List.copyOf(tecnicas);
		this.equipamiento = List.copyOf(equipamiento);
	}

	public void preparar(Combatiente combatiente) {
		Objects.requireNonNull(combatiente, "El combatiente es obligatorio.");

		for (Tecnica tecnica : tecnicas) {
			combatiente.aprender(tecnica);
		}

		for (Equipamiento objeto : equipamiento) {
			combatiente.equipar(objeto);
		}
	}

	public String getNombre() {
		return nombre;
	}

	public List<Tecnica> getTecnicas() {
		return tecnicas;
	}

	public List<Equipamiento> getEquipamiento() {
		return equipamiento;
	}
}