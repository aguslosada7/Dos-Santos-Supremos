package acontecimientos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CanalAcontecimientos {
	private final List<ObservadorAcontecimientos> observadores = new ArrayList<>();

	public void suscribir(ObservadorAcontecimientos observador) {
		Objects.requireNonNull(observador, "El observador es obligatorio.");

		for (ObservadorAcontecimientos suscripto : observadores) {
			if (suscripto == observador) {
				throw new IllegalArgumentException("El observador ya está suscripto.");
			}
		}

		observadores.add(observador);
	}

	public void publicar(Acontecimiento evento) {
		Objects.requireNonNull(evento, "El acontecimiento es obligatorio.");

		for (ObservadorAcontecimientos observador : new ArrayList<>(observadores)) {
			evento.notificarA(observador);
		}
	}
}
