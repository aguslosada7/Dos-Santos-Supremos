package combatientes;

public class ImpulsoPorCaida implements ComportamientoReactivo {
	private final int bonificacionDanio;
	private boolean activo;

	public ImpulsoPorCaida(int bonificacionDanio) {
		if (bonificacionDanio < 0) {
			throw new IllegalArgumentException("La bonificacion no puede ser negativa.");
		}

		this.bonificacionDanio = bonificacionDanio;
	}

	@Override
	public void activar() {
		activo = true;
	}

	@Override
	public int modificarProximoDanio(int danio) {
		if (danio < 0) {
			throw new IllegalArgumentException("El danio no puede ser negativo.");
		}

		if (!activo) {
			return danio;
		}

		int danioBonificado = Math.addExact(danio, bonificacionDanio);
		activo = false;
		return danioBonificado;
	}

	@Override
	public void reiniciar() {
		activo = false;
	}
}
