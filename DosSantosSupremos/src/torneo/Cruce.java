package torneo;

import java.util.Objects;

public class Cruce {
	private Pareja parejaA;
	private Pareja parejaB;
	private Combate combate;
	private Pareja ganadora;

	public Cruce(Pareja parejaA, Pareja parejaB, Combate combate) {
		Objects.requireNonNull(parejaA, "Las parejas son obligatorias.");
		Objects.requireNonNull(parejaB, "Las parejas son obligatorias.");
		Objects.requireNonNull(combate, "El combate es obligatorio.");
		
		this.parejaA = parejaA;
		this.parejaB = parejaB;
		this.combate = combate;
	}
	
	public Pareja resolver() {
		if (ganadora == null)
            ganadora = combate.resolver();
		
        return ganadora;
	}

	public Pareja getParejaA() {
		return parejaA;
	}

	public Pareja getParejaB() {
		return parejaB;
	}
}
