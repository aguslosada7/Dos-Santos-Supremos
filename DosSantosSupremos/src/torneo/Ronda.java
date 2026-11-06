package torneo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import combatientes.Cruce;

public class Ronda {
	private final int numero;
	private final List<Cruce> cruces;

	public Ronda(int numero, List<Cruce> cruces) {
		Objects.requireNonNull(cruces, "Los cruces son obligatorios.");

		if (numero <= 0 || cruces.isEmpty() || cruces.size() > 4) {
			throw new IllegalArgumentException("La ronda debe ser positiva y tener entre uno y cuatro cruces.");
		}

		this.numero = numero;
		this.cruces = List.copyOf(cruces);
	}

	public List<Pareja> jugar() {
		List<Pareja> ganadoras = new ArrayList<>();

		for (Cruce cruce : cruces) {
			ganadoras.add(cruce.resolver());
		}

		return List.copyOf(ganadoras);
	}
}
