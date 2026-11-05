package combatientes;

import java.util.Objects;

public class Combatiente {
	private int id;
	private String nombre;
	private int vidaActual;
	private int energiaActual;
	private final static int VIDA_MAXIMA = 100;
	private final static int ENERGIA_MAXIMA = 40;
	
	public Combatiente(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(id));
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Combatiente other = (Combatiente) obj;
		return id == other.id;
	}
}
