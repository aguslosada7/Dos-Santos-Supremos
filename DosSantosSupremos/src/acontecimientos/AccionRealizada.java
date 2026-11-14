package acontecimientos;

import java.util.Objects;

public record AccionRealizada(int ronda, int numeroAsalto, int idActor, String nombreActor, int idObjetivo,
		String nombreObjetivo, TipoAccion tipoAccion, String nombreTecnica, int costoPagado, int danioEfectivo,
		int vidaActorFinal, int energiaActorFinal, int vidaObjetivoFinal, int energiaObjetivoFinal)
		implements Acontecimiento {

	public AccionRealizada {
		if (ronda <= 0 || numeroAsalto <= 0 || idActor <= 0 || idObjetivo <= 0) {
			throw new IllegalArgumentException("La ronda, el asalto y los ids deben ser positivos.");
		}
		
		if(tipoAccion == TipoAccion.ATAQUE_BASICO && idActor == idObjetivo)
			throw new IllegalArgumentException("Los combatientes deben ser distintos.");

		if (costoPagado < 0 || danioEfectivo < 0 || vidaActorFinal < 0 || energiaActorFinal < 0 || vidaObjetivoFinal < 0
				|| energiaObjetivoFinal < 0) {
			throw new IllegalArgumentException("El costo, el daño, la vida y la energía no pueden ser negativos.");
		}
		
		if(nombreActor.isBlank() || nombreObjetivo.isBlank())
			throw new IllegalArgumentException("Los nombres del actor y del objetivo son obligatorios.");

		if(tipoAccion == TipoAccion.TECNICA && nombreTecnica.isBlank())
			throw new IllegalArgumentException("El nombre de la técnica es obligatorio.");
		
		if(tipoAccion == null)
			throw new IllegalArgumentException("El tipo de acción es obligatorio.");
	}

	@Override
	public void notificarA(ObservadorAcontecimientos observador) {
		Objects.requireNonNull(observador, "El observador es obligatorio.").recibirAccionRealizada(this);
	}
}
