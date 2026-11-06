package combatientes;

import torneo.Combate;
import torneo.Pareja;

public class Cruce {
	private Pareja parejaA;
	private Pareja parejaB;
	private Combate combate;
	private Pareja ganadora;

	public Cruce(Pareja parejaA, Pareja parejaB, Combate combate) {
		super();
		this.parejaA = parejaA;
		this.parejaB = parejaB;
		this.combate = combate;
	}
}
