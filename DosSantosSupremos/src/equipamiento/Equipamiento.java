package equipamiento;

import java.util.Objects;

public abstract class Equipamiento {
	private final String nombre;
	private final CategoriaEquipamiento categoria;
	private final int bonificacionDanio;
	private final int reduccionDanio;
	private final int bonificacionVidaMaxima;
	private final int bonificacionEnergiaMaxima;

	protected Equipamiento(String nombre, CategoriaEquipamiento categoria, int bonificacionDanio, int reduccionDanio,
			int bonificacionVidaMaxima, int bonificacionEnergiaMaxima) {
		Objects.requireNonNull(nombre, "El nombre es obligatorio.");
		Objects.requireNonNull(categoria, "La categoría es obligatoria.");

		if (bonificacionDanio < 0 || reduccionDanio < 0 || bonificacionVidaMaxima < 0
				|| bonificacionEnergiaMaxima < 0) {
			throw new IllegalArgumentException("Las bonificaciones no pueden ser negativas.");
		}

		this.nombre = nombre;
		this.categoria = categoria;
		this.bonificacionDanio = bonificacionDanio;
		this.reduccionDanio = reduccionDanio;
		this.bonificacionVidaMaxima = bonificacionVidaMaxima;
		this.bonificacionEnergiaMaxima = bonificacionEnergiaMaxima;
	}

	public String getNombre() {
		return nombre;
	}

	public CategoriaEquipamiento getCategoria() {
		return categoria;
	}

	public int getBonificacionDanio() {
		return bonificacionDanio;
	}

	public int getReduccionDanio() {
		return reduccionDanio;
	}

	public int getBonificacionVidaMaxima() {
		return bonificacionVidaMaxima;
	}

	public int getBonificacionEnergiaMaxima() {
		return bonificacionEnergiaMaxima;
	}
}
