package acontecimientos;

import java.util.List;
import java.util.Objects;

public class HeraldoImperial implements ObservadorAcontecimientos {

	public HeraldoImperial() {
	}

	@Override
	public void recibirInicioCombate(InicioCombate evento) {
		System.out.println("=== Ronda " + evento.ronda() + ": combate entre la pareja " + evento.idParejaA()
				+ " y la pareja " + evento.idParejaB() + " ===");
	}

	@Override
	public void recibirAccionRealizada(AccionRealizada evento) {
		String accion;
		if (evento.tipoAccion() == TipoAccion.ATAQUE_BASICO) {
			accion = "realiza un ataque básico";
		} else {
			accion = "usa " + evento.nombreTecnica() + " (costo " + evento.costoPagado() + ")";
		}

		System.out.println("  Asalto " + evento.numeroAsalto() + ": " + evento.nombreActor() + " " + accion
				+ " sobre " + evento.nombreObjetivo() + " | daño " + evento.danioEfectivo() + " | vida del objetivo "
				+ evento.vidaObjetivoFinal());
	}

	@Override
	public void recibirEliminacion(Eliminacion evento) {
		System.out.println("  " + evento.nombreCombatiente() + " (pareja " + evento.idPareja()
				+ ") queda fuera de combate en el asalto " + evento.numeroAsalto() + ".");
	}

	@Override
	public void recibirResultadoCombate(ResultadoCombate evento) {
		System.out.println("Resultado de la ronda " + evento.ronda() + " (asalto " + evento.numeroAsalto()
				+ "): pareja " + evento.idParejaA() + " con " + evento.vidaTotalFinalParejaA() + " de vida, pareja "
				+ evento.idParejaB() + " con " + evento.vidaTotalFinalParejaB() + ". Gana la pareja "
				+ evento.idParejaGanadora() + ".");
		System.out.println();
	}

	@Override
	public void recibirProclamacion(Proclamacion evento) {
		System.out.println("*** Los Dos Santos Supremos son " + evento.nombrePrimerCampeon() + " y "
				+ evento.nombreSegundoCampeon() + " (pareja " + evento.idParejaCampeona() + ") ***");
		System.out.println();
	}

	public void presentarTabla(List<RegistroPosicion> posiciones) {
		Objects.requireNonNull(posiciones, "Las posiciones son obligatorias.");

		System.out.println("--- Tabla de posiciones ---");
		int puesto = 1;
		for (RegistroPosicion posicion : posiciones) {
			System.out.println(puesto + ". Pareja " + posicion.idPareja() + " | última ronda "
					+ posicion.rondaUltimoCombate() + " | vida total " + posicion.vidaTotalUltimoCombate());
			puesto++;
		}
	}
}