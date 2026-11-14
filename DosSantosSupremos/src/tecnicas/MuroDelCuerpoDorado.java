package tecnicas;

import java.util.Objects;

import combatientes.Combatiente;

public class MuroDelCuerpoDorado extends Tecnica{
	private final static int COSTO = 12;
	private final static int REDUCCION = 6;
	
	public MuroDelCuerpoDorado() {
		super("Muro del Cuerpo Dorado", COSTO, 0, CategoriaTecnica.DEFENSIVA);
	}

	@Override
	public void aplicar(Combatiente ejecutor, Combatiente objetivo) {
		Objects.requireNonNull(ejecutor, "El ejecutor es obligatorio.");
		Objects.requireNonNull(objetivo, "El objetivo es obligatorio.");
		ejecutor.aplicarDefensaCombate(REDUCCION);	
	}
}
