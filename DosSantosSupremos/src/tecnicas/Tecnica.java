package tecnicas;

import java.util.Objects;

import combatientes.Combatiente;
import tecnicas.CategoriaTecnica;

public abstract class Tecnica {
	private final String nombre;
	private int costoEnergia;
	private int danio;
	private CategoriaTecnica categoria;
	private final static int USOS_MAXIMOS_POR_TECNICA = 5;

	protected Tecnica(String nombre, int costoEnergia, int danio, CategoriaTecnica categoria) {
		this.nombre = nombre;
		this.costoEnergia = costoEnergia;
		this.danio = danio;
		this.categoria = categoria;
	}

	// No hay un comportamiento común para todas las técnicas, varían según su tipo.
	public abstract void aplicar(Combatiente ejecutor, Combatiente objetivo);

	public String getNombre() {
		return nombre;
	}

	public int getCostoEnergia() {
		return costoEnergia;
	}

	public CategoriaTecnica getCategoria() {
		return categoria;
	}

	public static int getUsosMaximosPorTecnica() {
		return USOS_MAXIMOS_POR_TECNICA;
	}

	@Override
	public final int hashCode() {
		return Objects.hashCode(nombre);
	}

	@Override
	public final boolean equals(Object obj) {
		if (!Tecnica.class.isInstance(obj)) {
			return false;
		}

		Tecnica otra = (Tecnica) obj;
		return Objects.equals(nombre, otra.nombre);
	}
}
