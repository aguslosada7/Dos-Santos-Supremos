package torneo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import acontecimientos.AccionRealizada;
import acontecimientos.CanalAcontecimientos;
import acontecimientos.Eliminacion;
import acontecimientos.InicioCombate;
import acontecimientos.ResultadoCombate;
import acontecimientos.TipoAccion;
import combatientes.Combatiente;
import tecnicas.CategoriaTecnica;
import tecnicas.Tecnica;

public class Combate {
	private final int ronda;
	private final Pareja parejaA;
	private final Pareja parejaB;
	private final int limiteAsaltos;
	private final int energiaPorAsalto;
	private int numeroAsalto;
	private final CanalAcontecimientos canalAcontecimientos;
	private Pareja ganadora;

	public Combate(int ronda, Pareja a, Pareja b, int limite, int energia, CanalAcontecimientos canal) {
		Objects.requireNonNull(a, "La primera pareja es obligatoria.");

		Objects.requireNonNull(b, "La segunda pareja es obligatoria.");

		Objects.requireNonNull(canal, "El canal es obligatorio.");

		if (ronda <= 0 || limite <= 0 || energia < 0 || a.getId() == b.getId()) {
			throw new IllegalArgumentException(
					"Ronda y limite deben ser positivos, energia no negativa y parejas distintas.");
		}

		for (Combatiente primero : a.getIntegrantes()) {
			for (Combatiente segundo : b.getIntegrantes()) {
				if (primero.getId() == segundo.getId()) {
					throw new IllegalArgumentException("Un combatiente no puede enfrentar a su propia pareja.");
				}
			}
		}

		this.ronda = ronda;
		this.parejaA = a;
		this.parejaB = b;
		this.limiteAsaltos = limite;
		this.energiaPorAsalto = energia;
		this.canalAcontecimientos = canal;
	}

	public Pareja resolver() {
		if (ganadora != null) {
			return ganadora;
		}

		try {
			iniciarCombate();

			while (ganadora == null && numeroAsalto < limiteAsaltos) {
				ejecutarAsalto();
			}

			if (ganadora == null) {
				ganadora = ganadoraAlLimite();
			}
		} finally {
			finalizarCombate();
		}

		return ganadora;
	}

	private void iniciarCombate() {
		numeroAsalto = 0;

		for (Pareja pareja : List.of(parejaA, parejaB)) {
			pareja.reiniciarRegistroTecnicas();

			for (Combatiente integrante : pareja.getIntegrantes()) {
				integrante.iniciarCombate();
			}
		}

		canalAcontecimientos.publicar(new InicioCombate(ronda, parejaA.getId(), parejaB.getId()));
	}

	private void ejecutarAsalto() {
		numeroAsalto++;

		recuperarEnergiaPorAsalto();

		List<Pareja> ordenParejas;

		if (parejaA.getId() < parejaB.getId()) {
			ordenParejas = List.of(parejaA, parejaB);
		} else {
			ordenParejas = List.of(parejaB, parejaA);
		}

		for (Pareja pareja : ordenParejas) {
			List<Combatiente> integrantes = new ArrayList<>(pareja.getIntegrantes());

			integrantes.sort(Comparator.comparingInt(Combatiente::getId));

			for (Combatiente integrante : integrantes) {
				resolverAccion(pareja, integrante);

				if (parejaA.estaEliminada()) {
					ganadora = parejaB;

					return;
				}

				if (parejaB.estaEliminada()) {
					ganadora = parejaA;

					return;
				}
			}
		}
	}

	private void recuperarEnergiaPorAsalto() {
		for (Pareja pareja : List.of(parejaA, parejaB)) {
			for (Combatiente integrante : pareja.integrantesEnPie()) {
				integrante.recuperarEnergia(energiaPorAsalto);
			}
		}
	}

	private void resolverAccion(Pareja pareja, Combatiente combatiente) {
		if (!combatiente.estaEnPie()) {
			return;
		}

		Optional<Tecnica> elegida = combatiente.elegirTecnica(pareja);

		if (elegida.isPresent()) {
			ejecutarTecnica(pareja, combatiente, elegida.get());
		} else {
			ejecutarAtaqueBasico(combatiente);
		}
	}

	private void ejecutarTecnica(Pareja pareja, Combatiente ejecutor, Tecnica tecnica) {
		if (!ejecutor.estaEnPie() || !ejecutor.puedeUsar(tecnica) || !pareja.puedeUsarTecnica(ejecutor, tecnica)) {
			return;
		}

		int costo = ejecutor.calcularCostoEnergia(tecnica.getCostoEnergia());

		if (costo < 0 || ejecutor.getEnergiaActual() < costo || ejecutor.usosRestantes(tecnica) <= 0) {
			return;
		}

		Combatiente objetivo = ejecutor;

		if (tecnica.getCategoria() == CategoriaTecnica.OFENSIVA) {
			Pareja rivales = pareja == parejaA ? parejaB : parejaA;

			objetivo = ejecutor.elegirObjetivo(rivales.integrantesEnPie());
		}

		int vidaAnterior = objetivo.getVidaActual();

		ejecutor.perderEnergia(costo);

		ejecutor.consumirUso(tecnica);

		pareja.registrarPrimerUsoTecnica(ejecutor, tecnica);

		tecnica.aplicar(ejecutor, objetivo);

		int danioEfectivo = 0;

		if (tecnica.getCategoria() == CategoriaTecnica.OFENSIVA) {
			danioEfectivo = Math.max(0, vidaAnterior - objetivo.getVidaActual());
		}

		publicarAccion(ejecutor, objetivo, TipoAccion.TECNICA, tecnica.getNombre(), costo, danioEfectivo);

		if (vidaAnterior > 0 && !objetivo.estaEnPie()) {
			notificarCaidaDeCompaniero(objetivo);
		}
	}

	private void ejecutarAtaqueBasico(Combatiente ejecutor) {
		Pareja propia = buscarPareja(ejecutor);

		Pareja rivales = propia == parejaA ? parejaB : parejaA;

		Combatiente objetivo = ejecutor.elegirObjetivo(rivales.integrantesEnPie());

		int vidaAnterior = objetivo.getVidaActual();

		objetivo.recibirDanio(ejecutor.calcularDanio(ejecutor.getDanioBase()));

		int danioEfectivo = Math.max(0, vidaAnterior - objetivo.getVidaActual());

		publicarAccion(ejecutor, objetivo, TipoAccion.ATAQUE_BASICO, "", 0, danioEfectivo);

		if (vidaAnterior > 0 && !objetivo.estaEnPie()) {
			notificarCaidaDeCompaniero(objetivo);
		}
	}

	private void publicarAccion(Combatiente ejecutor, Combatiente objetivo, TipoAccion tipo, String nombreTecnica,
			int costo, int danioEfectivo) {
		canalAcontecimientos.publicar(
				new AccionRealizada(ronda, numeroAsalto, ejecutor.getId(), ejecutor.getNombre(), objetivo.getId(),
						objetivo.getNombre(), tipo, nombreTecnica, costo, danioEfectivo, ejecutor.getVidaActual(),
						ejecutor.getEnergiaActual(), objetivo.getVidaActual(), objetivo.getEnergiaActual()));
	}

	private void notificarCaidaDeCompaniero(Combatiente caido) {
		Pareja pareja = buscarPareja(caido);

		canalAcontecimientos
				.publicar(new Eliminacion(ronda, numeroAsalto, pareja.getId(), caido.getId(), caido.getNombre()));

		for (Combatiente integrante : pareja.getIntegrantes()) {
			if (integrante.getId() != caido.getId() && integrante.estaEnPie()) {
				integrante.alCaerCompaniero();
			}
		}
	}

	private Pareja buscarPareja(Combatiente combatiente) {
		for (Pareja pareja : List.of(parejaA, parejaB)) {
			for (Combatiente integrante : pareja.getIntegrantes()) {
				if (integrante.getId() == combatiente.getId()) {
					return pareja;
				}
			}
		}

		throw new IllegalArgumentException("El combatiente no participa de este combate.");
	}

	private Pareja ganadoraAlLimite() {
		int vidaA = parejaA.vidaTotal(), vidaB = parejaB.vidaTotal();

		if (vidaA > vidaB) {
			return parejaA;
		}

		if (vidaB > vidaA) {
			return parejaB;
		}

		return parejaA.getId() < parejaB.getId() ? parejaA : parejaB;
	}

	private void finalizarCombate() {
		ResultadoCombate resultado = null;

		if (ganadora != null) {
			resultado = new ResultadoCombate(ronda, numeroAsalto, parejaA.getId(), parejaB.getId(), ganadora.getId(),
					parejaA.vidaTotal(), parejaB.vidaTotal());
		}

		for (Pareja pareja : List.of(parejaA, parejaB)) {
			for (Combatiente integrante : pareja.getIntegrantes()) {
				integrante.finalizarCombate();
			}
		}

		if (resultado != null) {
			canalAcontecimientos.publicar(resultado);
		}
	}
}
