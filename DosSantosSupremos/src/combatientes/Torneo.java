package combatientes;

import java.util.List;
import java.util.Set;

public class Torneo {
	private List<Pareja> ordenInscripcion;
	private List<Ronda> rondas;
	private Set<Integer> idsParejasInscritas;
	private Set<Combatiente> combatientesInscritos;
	private EstadoTorneo estado;
	private int limiteAsaltos;
	private int energiaPorAsalto;
	private CanalAcontecimientos canalAcontecimientos;
}
