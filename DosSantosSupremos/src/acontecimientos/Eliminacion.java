package acontecimientos;

import java.util.Objects;

public record Eliminacion(int ronda, int numeroAsalto, int idPareja, int idCombatiente, String nombreCombatiente)
		implements Acontecimiento {

	public Eliminacion {
		if (ronda <= 0 || numeroAsalto <= 0 || idPareja <= 0 || idCombatiente <= 0) {
			throw new IllegalArgumentException("La ronda, el asalto y los ids deben ser positivos.");
		}

		Objects.requireNonNull(nombreCombatiente, "El nombre del combatiente es obligatorio.");
	}

	@Override
	public void notificarA(ObservadorAcontecimientos observador) {
		Objects.requireNonNull(observador, "El observador es obligatorio.").recibirEliminacion(this);
	}
}
