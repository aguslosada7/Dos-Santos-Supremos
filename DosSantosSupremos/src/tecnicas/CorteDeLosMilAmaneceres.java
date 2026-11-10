package tecnicas;

import java.util.Objects;

import combatientes.Combatiente;

public class CorteDeLosMilAmaneceres extends Tecnica {
	private final static int COSTO = 8;
	private final static int DANIO = 24;
	
	public CorteDeLosMilAmaneceres() {
		super("Corte de los Mil Amaneceres", COSTO, DANIO, CategoriaTecnica.OFENSIVA);
	}

	@Override
	public void aplicar(Combatiente ejecutor, Combatiente objetivo) {
		Objects.requireNonNull(ejecutor, "El ejecutor es obligatorio.");
		Objects.requireNonNull(objetivo, "El objetivo es obligatorio.");
		objetivo.recibirDanio(ejecutor.calcularDanio(DANIO));
	}
}
