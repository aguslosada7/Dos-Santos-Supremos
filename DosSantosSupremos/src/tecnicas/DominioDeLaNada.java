package tecnicas;

import java.util.Objects;

import combatientes.Combatiente;

public class DominioDeLaNada extends Tecnica {
	private final static int COSTO = 12;
	private final static int DANIO = 20;
	private final static int DRENAJE_ENERGIA = 8;

	public DominioDeLaNada() {
		super("Dominio de la Nada", COSTO, DANIO, CategoriaTecnica.OFENSIVA);
	}

	@Override
	public void aplicar(Combatiente ejecutor, Combatiente objetivo) {
		Objects.requireNonNull(ejecutor, "El ejecutor es obligatorio.");
		Objects.requireNonNull(objetivo, "El objetivo es obligatorio.");
		objetivo.perderEnergia(DRENAJE_ENERGIA);
		objetivo.recibirDanio(ejecutor.calcularDanio(DANIO));
	}
}
