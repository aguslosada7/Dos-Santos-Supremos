package combatientes;

public class MonjeDelCuerpoCelestial extends Combatiente{
	private final static int PORCENTAJE_REDUCCION_DANIO = 80;
	
	public MonjeDelCuerpoCelestial(int id, String nombre) {
		super(id, nombre);
	}
		
	@Override
    protected int modificarDanioRecibidoPorTipo(int cantidad) {
		return cantidad * PORCENTAJE_REDUCCION_DANIO / 100;
	}
}
