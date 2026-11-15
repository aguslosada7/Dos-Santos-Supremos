package tecnicas;

import java.util.Objects;

import combatientes.Combatiente;

public class RugidoDeLaBestiaPrimordial extends Tecnica {
	private final static int COSTO = 20;
	private final static int DANIO = 40;

	public RugidoDeLaBestiaPrimordial() {
		super("Rugido de la Bestia Primordial", COSTO, DANIO, CategoriaTecnica.OFENSIVA);
	}

	@Override
	public void aplicar(Combatiente ejecutor, Combatiente objetivo) {
		Objects.requireNonNull(ejecutor, "El ejecutor es obligatorio.");
		Objects.requireNonNull(objetivo, "El objetivo es obligatorio.");
		objetivo.recibirDanio(ejecutor.calcularDanio(DANIO));
	}
}
