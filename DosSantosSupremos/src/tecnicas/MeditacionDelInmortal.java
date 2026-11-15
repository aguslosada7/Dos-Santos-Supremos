package tecnicas;

import java.util.Objects;

import combatientes.Combatiente;

public class MeditacionDelInmortal extends Tecnica {
	private final static int COSTO = 12;
	private final static int DANIO = 0;
	private final static int RECUPERACION_VIDA = 24;

	public MeditacionDelInmortal() {
		super("Meditación del Inmortal", COSTO, DANIO, CategoriaTecnica.RECUPERACION);
	}

	@Override
	public void aplicar(Combatiente ejecutor, Combatiente objetivo) {
		Objects.requireNonNull(ejecutor, "El ejecutor es obligatorio.");
		Objects.requireNonNull(objetivo, "El objetivo es obligatorio.");
		ejecutor.recuperarVida(RECUPERACION_VIDA);
	}
}
