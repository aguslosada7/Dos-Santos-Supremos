package combatientes;

public class CultivadorDeLaEspada extends Combatiente {
	private static final int PORCENTAJE_DANIO_EMITIDO = 120;

	public CultivadorDeLaEspada(int id, String nombre) {
		super(id, nombre);
	}

	//Potencia el daño que provoca: +20 %, con redondeo hacia abajo.

	@Override
	protected int modificarDanioPorTipo(int danio) {
		return Math.multiplyExact(danio, PORCENTAJE_DANIO_EMITIDO) / 100;
	}
}
