package acontecimientos;

import java.util.Objects;

import combatientes.ObservadorAcontecimientos;

public record Proclamacion(int idParejaCampeona, int idPrimerCampeon, String nombrePrimerCampeon, int idSegundoCampeon,
		String nombreSegundoCampeon) implements Acontecimiento {
	public Proclamacion {
		if (idParejaCampeona <= 0 || idPrimerCampeon <= 0 || idSegundoCampeon <= 0
				|| idPrimerCampeon == idSegundoCampeon) {
			throw new IllegalArgumentException("Los ids deben ser positivos y los campeones distintos.");
		}

		Objects.requireNonNull(nombrePrimerCampeon, "El primer nombre es obligatorio.");

		Objects.requireNonNull(nombreSegundoCampeon, "El segundo nombre es obligatorio.");
	}

	@Override
	public void notificarA(ObservadorAcontecimientos observador) {
		Objects.requireNonNull(observador, "El observador es obligatorio.").recibirProclamacion(this);
	}
}
