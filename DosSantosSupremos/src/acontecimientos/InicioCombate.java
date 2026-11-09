package acontecimientos;

import java.util.Objects;

public record InicioCombate(int ronda, int idParejaA, int idParejaB) implements Acontecimiento {
	
	public InicioCombate {
		if (ronda <= 0 || idParejaA <= 0 || idParejaB <= 0 || idParejaA == idParejaB) {
			throw new IllegalArgumentException("Los ids y el número de ronda deben ser positivos y las parejas distintas.");
		}
	}

	@Override
	public void notificarA(ObservadorAcontecimientos observador) {
		Objects.requireNonNull(observador, "El observador es obligatorio.").recibirInicioCombate(this);
	}
}
