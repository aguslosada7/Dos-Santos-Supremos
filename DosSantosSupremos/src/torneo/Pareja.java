package torneo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import combatientes.Combatiente;
import tecnicas.Tecnica;

public class Pareja {
	private final int id;
	private Combatiente combatienteA;
	private Combatiente combatienteB;
	private final Map<Tecnica, Combatiente> primerUsuarioPorTecnica = new HashMap<>();

	public Pareja(int id, Combatiente primero, Combatiente segundo) {
		Objects.requireNonNull(primero, "El primer integrante es obligatorio.");

		Objects.requireNonNull(segundo, "El segundo integrante es obligatorio.");

		if (id <= 0 || primero.getId() <= 0 || segundo.getId() <= 0 || primero.getId() == segundo.getId()) {
			throw new IllegalArgumentException("Los ids deben ser positivos y los integrantes distintos.");
		}

		this.id = id;
		combatienteA = primero;
		combatienteB = segundo;
	}

	public int getId() {
		return id;
	}
	
	public Combatiente getCombatienteA() {
		return combatienteA;
	}

	public Combatiente getCombatienteB() {
		return combatienteB;
	}

	public List<Combatiente> getIntegrantes(){
		return List.of(combatienteA, combatienteB);
	}

	public List<Combatiente> integrantesEnPie() {
		List<Combatiente> enPie = new ArrayList<>();

		if (combatienteA.estaEnPie()) {
			enPie.add(combatienteA);
		}
		
		if (combatienteB.estaEnPie()) {
			enPie.add(combatienteB);
		}

		return List.copyOf(enPie);
	}

	public boolean estaEliminada() {
		return integrantesEnPie().isEmpty();
	}

	public int vidaTotal() {
		int total = 0;

		if (combatienteA.estaEnPie()) {
			total += combatienteA.getVidaActual();
		}
		
		if (combatienteB.estaEnPie()) {
			total += combatienteB.getVidaActual();
		}

		return total;
	}

	public boolean puedeUsarTecnica(Combatiente combatiente, Tecnica tecnica) {
		Combatiente integrante = buscarIntegrante(combatiente);

		Objects.requireNonNull(tecnica, "La tecnica es obligatoria.");

		Combatiente primero = primerUsuarioPorTecnica.get(tecnica);

		return primero == null || primero.getId() == integrante.getId() || !primero.estaEnPie()
				|| primero.usosRestantes(tecnica) == 0;
	}

	public void registrarPrimerUsoTecnica(Combatiente combatiente, Tecnica tecnica) {
		Combatiente integrante = buscarIntegrante(combatiente);

		if (!puedeUsarTecnica(integrante, tecnica)) {
			throw new IllegalStateException("La tecnica esta reservada por el companiero.");
		}

		primerUsuarioPorTecnica.putIfAbsent(tecnica, integrante);
	}

	public void reiniciarRegistroTecnicas() {
		primerUsuarioPorTecnica.clear();
	}

	private Combatiente buscarIntegrante(Combatiente combatiente) {
		Objects.requireNonNull(combatiente, "El combatiente es obligatorio.");

		if (combatienteA.getId() == combatiente.getId()) {
			return combatienteA;
		}
		
		if (combatienteB.getId() == combatiente.getId()) {
			return combatienteB;
		}

		throw new IllegalArgumentException("El combatiente no pertenece a la pareja.");
	}
}
