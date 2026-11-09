package tecnicas;

import java.util.Objects;

import combatientes.Combatiente;

public class CorteDeLosMilAmaneceres extends Tecnica {
	public CorteDeLosMilAmaneceres() {
		super("Corte de los Mil Amaneceres", 8, CategoriaTecnica.OFENSIVA);
	}

	@Override
	public void aplicar(Combatiente ejecutor, Combatiente objetivo) {
		Objects.requireNonNull(ejecutor, "El ejecutor es obligatorio.");
		Objects.requireNonNull(objetivo, "El objetivo es obligatorio.");
		objetivo.recibirDanio(ejecutor.calcularDanio(24));
	}
}
