package acontecimientos;

import java.util.List;
import java.util.Objects;

import tecnicas.CategoriaTecnica;

public class HeraldoImperial implements ObservadorAcontecimientos {
	public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[1;31m";
    public static final String ANSI_RED_LIGHT = "\u001B[0;91m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_GREEN_BOLD = "\u001B[1;32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_YELLOW_BOLD = "\u001B[1;33m";
    public static final String ANSI_YELLOW_UL = "\u001B[4;33m";
    public static final String ANSI_CYAN_BG = "\u001B[0;106m";
    public static final String ANSI_CYAN = "\u001B[0;36m";
    public static final String ANSI_BLUE = "\u001B[1;34m";
    public static final String ANSI_PURPLE = "\u001B[0;95m";
    private static final String SEPARADOR = ANSI_GREEN + "=================================";
    private static final String SEPARADOR_TABLA = ANSI_GREEN + "================================================";

	public HeraldoImperial() {
	}

	@Override
	public void recibirInicioCombate(InicioCombate evento) {
		System.out.println(SEPARADOR + " " + ANSI_YELLOW + "Ronda " + evento.ronda() +
				": combate entre la pareja " + evento.idParejaA()
				+ " y la pareja " + evento.idParejaB() + " " + SEPARADOR + ANSI_RESET);
	}

	@Override
	public void recibirAccionRealizada(AccionRealizada evento) {
		String accion;
		if (evento.tipoAccion() == TipoAccion.ATAQUE_BASICO) {
			accion = "realiza un ataque básico sobre " + evento.nombreObjetivo() + ", causando " + ANSI_CYAN + evento.danioEfectivo() + ANSI_RESET + " de daño";
		} else {
			if(evento.categoriaTecnica() == CategoriaTecnica.OFENSIVA)
				accion = "usa " + ANSI_RED_LIGHT + evento.nombreTecnica() + ANSI_RESET + " (costo " + evento.costoPagado() + ") sobre " + evento.nombreObjetivo() + ", causando " + ANSI_CYAN + evento.danioEfectivo() + ANSI_RESET + " de daño";
			else if(evento.categoriaTecnica() == CategoriaTecnica.DEFENSIVA)
				accion = "usa " + ANSI_RED_LIGHT + evento.nombreTecnica() + ANSI_RESET + " (costo " + evento.costoPagado() + ") para incrementar su defensa";
			else
				accion = "usa " + ANSI_RED_LIGHT + evento.nombreTecnica() + ANSI_RESET + " (costo " + evento.costoPagado() + ") para recuperar " + ANSI_CYAN + evento.vidaRecuperada() + ANSI_RESET + " de vida";
		}
		
		System.out.println("  " + ANSI_YELLOW_UL + "Asalto " + evento.numeroAsalto() + ANSI_RESET + ANSI_YELLOW + ":" + ANSI_RESET);
		System.out.println(ANSI_GREEN_BOLD + "   - " + ANSI_RESET + evento.nombreActor() + " " + accion + ".");
		
		if(evento.categoriaTecnica() != CategoriaTecnica.DEFENSIVA) {
			System.out.println(ANSI_GREEN + "     - " + ANSI_RESET + "Vida de " + evento.nombreActor() + ": " + ANSI_CYAN + evento.vidaActorFinal() + ANSI_RESET);
			System.out.println(ANSI_GREEN + "     - " + ANSI_RESET + "Energia de " + evento.nombreActor() + ": " + ANSI_CYAN + evento.energiaActorFinal() + ANSI_RESET);
		}
		
		if(evento.categoriaTecnica() == CategoriaTecnica.OFENSIVA || evento.tipoAccion() == TipoAccion.ATAQUE_BASICO) {
			System.out.println(ANSI_GREEN + "     - " + ANSI_RESET + "Vida de " + evento.nombreObjetivo() + ": " + ANSI_CYAN +
					evento.vidaObjetivoFinal() + ANSI_RESET);
			System.out.println(ANSI_GREEN + "     - " + ANSI_RESET + "Energia de " + evento.nombreObjetivo() + ": " + ANSI_CYAN +
					evento.energiaObjetivoFinal() + ANSI_RESET);
		}
	}

	@Override
	public void recibirEliminacion(Eliminacion evento) {
		System.out.println(ANSI_RED + "  " + evento.nombreCombatiente() + " (pareja " + evento.idPareja()
				+ ") queda fuera de combate en el asalto " + evento.numeroAsalto() + " de la ronda " + evento.ronda() + "." + ANSI_RESET);
	}

	@Override
	public void recibirResultadoCombate(ResultadoCombate evento) {
		System.out.println(ANSI_GREEN + "--------------------------------------------------------------------------------------------------------------------" + ANSI_RESET);
		System.out.println(ANSI_YELLOW_BOLD + "  Resultado de la ronda " + evento.ronda() + " (asalto " + evento.numeroAsalto() + "): "
				+ ANSI_RESET + "pareja " + ANSI_CYAN + evento.idParejaA() + ANSI_RESET + " con " + ANSI_CYAN + evento.vidaTotalFinalParejaA()
				+ ANSI_RESET + " de vida, pareja " + ANSI_CYAN + evento.idParejaB() + ANSI_RESET + " con " + ANSI_CYAN
				+ evento.vidaTotalFinalParejaB() + ANSI_RESET + ". Gana la pareja " + ANSI_CYAN + evento.idParejaGanadora() + ANSI_RESET + ".");
		System.out.println("\n");
	}

	@Override
	public void recibirProclamacion(Proclamacion evento) {
		System.out.println(ANSI_CYAN_BG + ANSI_BLUE + " Los Dos Santos Supremos son " + evento.nombrePrimerCampeon() + " y "
				+ evento.nombreSegundoCampeon() + " (pareja " + evento.idParejaCampeona() + ") " + ANSI_RESET);
		System.out.println("\n");
	}

	public void presentarTabla(List<RegistroPosicion> posiciones) {
		Objects.requireNonNull(posiciones, "Las posiciones son obligatorias.");

		System.out.println(SEPARADOR_TABLA + ANSI_YELLOW + " Tabla de posiciones " + SEPARADOR_TABLA + ANSI_RESET);
		System.out.println(" _____________________________________________");
		System.out.println("| " + ANSI_PURPLE + "Puesto" + ANSI_RESET + " | " + ANSI_PURPLE + "Pareja" + ANSI_RESET + " | "
				+ ANSI_PURPLE + "Última ronda" + ANSI_RESET + " | " + ANSI_PURPLE + "Vida total" + ANSI_RESET + " |");
		System.out.println("|=============================================|");
		int puesto = 1;
		for (RegistroPosicion posicion : posiciones) {
			System.out.printf("| " + ANSI_CYAN + "%-6d" + ANSI_RESET + " | " + ANSI_CYAN + "%-6d" + ANSI_RESET + " | "
					+ ANSI_CYAN + "%-12d" + ANSI_RESET + " | " + ANSI_CYAN + "%-10d" + ANSI_RESET + " |\n", puesto,
					posicion.idPareja(), posicion.rondaUltimoCombate(),
					posicion.vidaTotalUltimoCombate());
			puesto++;
		}
		System.out.println(" ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
	}
}