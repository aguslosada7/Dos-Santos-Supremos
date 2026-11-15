package acontecimientos;

import java.util.Objects;

public record RegistroPosicion(int idPareja, int rondaUltimoCombate, int vidaTotalUltimoCombate)
		implements Comparable<RegistroPosicion> {

	public RegistroPosicion {
		if (idPareja <= 0 || rondaUltimoCombate <= 0) {
			throw new IllegalArgumentException("El id de pareja y la ronda deben ser positivos.");
		}

		if (vidaTotalUltimoCombate < 0) {
			throw new IllegalArgumentException("La vida total no puede ser negativa.");
		}
	}

	@Override
	public int compareTo(RegistroPosicion otro) {
		Objects.requireNonNull(otro, "El registro a comparar es obligatorio.");

		int comparacionRonda = Integer.compare(otro.rondaUltimoCombate, this.rondaUltimoCombate);
		if (comparacionRonda != 0) {
			return comparacionRonda;
		}

		int comparacionVida = Integer.compare(otro.vidaTotalUltimoCombate, this.vidaTotalUltimoCombate);
		if (comparacionVida != 0) {
			return comparacionVida;
		}

		return Integer.compare(this.idPareja, otro.idPareja);
	}
}
