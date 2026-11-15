package acontecimientos;

import java.util.Objects;

public record ResultadoCombate(int ronda, int numeroAsalto, int idParejaA, int idParejaB, int idParejaGanadora,
		int vidaTotalFinalParejaA, int vidaTotalFinalParejaB) implements Acontecimiento {

	public ResultadoCombate {
		if (ronda <= 0 || numeroAsalto <= 0 || idParejaA <= 0 || idParejaB <= 0 || idParejaGanadora <= 0) {
			throw new IllegalArgumentException("La ronda, el asalto y los ids deben ser positivos.");
		}

		if (idParejaA == idParejaB) {
			throw new IllegalArgumentException("Las parejas que combaten deben ser distintas.");
		}

		if (idParejaGanadora != idParejaA && idParejaGanadora != idParejaB) {
			throw new IllegalArgumentException("La pareja ganadora debe ser una de las parejas participantes.");
		}

		if (vidaTotalFinalParejaA < 0 || vidaTotalFinalParejaB < 0) {
			throw new IllegalArgumentException("La vida total final de las parejas no puede ser negativa.");
		}
	}

	@Override
	public void notificarA(ObservadorAcontecimientos observador) {
		Objects.requireNonNull(observador, "El observador es obligatorio.").recibirResultadoCombate(this);
	}
}
