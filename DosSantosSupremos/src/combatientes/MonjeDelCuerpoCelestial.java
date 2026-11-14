package combatientes;

public class MonjeDelCuerpoCelestial extends Combatiente{
		public MonjeDelCuerpoCelestial(int id, String nombre) {
			super(id, nombre);
		}
		
		@Override
	    protected int modificarDanioRecibidoPorTipo(int cantidad) {
			return cantidad * 80 / 100;
		}
}
