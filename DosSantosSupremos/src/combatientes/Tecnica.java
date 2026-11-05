package combatientes;

import java.util.Objects;

public class Tecnica {
	private String nombre;
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
	public int hashCode() {
		return Objects.hash(Integer.valueOf(costoEnergia), nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tecnica other = (Tecnica) obj;
		return costoEnergia == other.costoEnergia && Objects.equals(nombre, other.nombre);
	}
}
