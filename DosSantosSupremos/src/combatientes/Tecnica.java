package combatientes;

import java.util.Objects;

import tecnicas.CategoriaTecnica;

public class Tecnica {
	private final String nombre;
	private int costoEnergia;
	private CategoriaTecnica categoria;

	protected Tecnica(String nombre, int costoEnergia, CategoriaTecnica categoria) {
		super();
		this.nombre = nombre;
		this.costoEnergia = costoEnergia;
		this.categoria = categoria;
	}
	
	public void aplicar(Combatiente ejecutor, Combatiente objetivo) {
		//
	}

	public String getNombre() {
		return nombre;
	}

	public int getCostoEnergia() {
		return costoEnergia;
	}

	public CategoriaTecnica getCategoria() {
		return categoria;
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
