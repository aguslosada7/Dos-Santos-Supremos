package torneo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import acontecimientos.CanalAcontecimientos;
import acontecimientos.ObservadorAcontecimientos;
import combatientes.Combatiente;

public class Torneo {
	private List<Pareja> ordenInscripcion = new ArrayList<>();
    private List<Ronda> rondas = new ArrayList<>();
    private Set<Integer> idsParejasInscritas = new HashSet<>();
    private Set<Integer> idsCombatientesInscritos = new HashSet<>();
	private EstadoTorneo estado = EstadoTorneo.PENDIENTE;
	private CanalAcontecimientos canalAcontecimientos;
	private Pareja parejaCampeona;
	private final static int LIMITE_ASALTOS = 50;
	private final static int ENERGIA_POR_ASALTO = 6;
	
	public void inscribir(Pareja pareja) {
		Objects.requireNonNull(pareja, "La pareja es obligatoria.");
		
        if (estado != EstadoTorneo.PENDIENTE)
            throw new InscripcionInvalidaException("No se pueden inscribir parejas si el torneo está en curso o finalizó.");
        
        if (ordenInscripcion.size() >= 8)
            throw new InscripcionInvalidaException("El torneo ya tiene el cupo máximo de 8 parejas.");
        
        if (idsParejasInscritas.contains(pareja.getId()))
            throw new InscripcionInvalidaException("La pareja ya se encuentra inscrita.");
        
        Combatiente combatienteA = pareja.getCombatienteA();
        Combatiente combatienteB = pareja.getCombatienteB();
        if (idsCombatientesInscritos.contains(combatienteA.getId()) || idsCombatientesInscritos.contains(combatienteB.getId())) {
            throw new InscripcionInvalidaException("Un combatiente no puede inscribirse en más de una pareja.");
        }
		
		ordenInscripcion.add(pareja);
		idsParejasInscritas.add(pareja.getId());
		idsCombatientesInscritos.add(combatienteA.getId());
		idsCombatientesInscritos.add(combatienteA.getId());
	}
	
	public void suscribir(ObservadorAcontecimientos observador) {
		Objects.requireNonNull(observador, "El observador es obligatorio.");
		canalAcontecimientos.suscribir(observador);
	}
	
	public void iniciar() {
		if(estado != EstadoTorneo.PENDIENTE)
			throw new IllegalStateException("El torneo solo se puede iniciar desde el estado PENDIENTE.");
		
		if(ordenInscripcion.size() != 4 && ordenInscripcion.size() != 8)
			throw new IllegalStateException("El torneo solo se puede jugar con 4 u 8 parejas.");
		
		estado = EstadoTorneo.EN_CURSO;
		
		int numeroRonda = 1;
		List<Pareja> parejasRondaActual = new ArrayList<>(ordenInscripcion);
		
		while(parejasRondaActual.size() > 1) {
			Ronda ronda = armarRonda(numeroRonda, parejasRondaActual);
			rondas.add(ronda);
			parejasRondaActual = ronda.jugar();
			numeroRonda++;
		}
		
		parejaCampeona = parejasRondaActual.get(0);
		estado = EstadoTorneo.FINALIZADO;
		
		Combatiente combatienteA = parejaCampeona.getCombatienteA();
		Combatiente combatienteB = parejaCampeona.getCombatienteB();
		canalAcontecimientos.publicar(new Proclamacion(parejaCampeona.getId(), combatienteA.getId(), combatienteB.getId(),
				combatienteA.getNombre(), combatienteB.getNombre()));
	}
	
	private Ronda armarRonda(int numero, List<Pareja> parejas) {
		List<Cruce> cruces = new ArrayList<>();
		
		for (int i = 0; i < parejas.size(); i+=2) {
			Pareja parejaA = parejas.get(i);
			Pareja parejaB = parejas.get(i+1);
			Combate combate = new Combate(numero, parejaA, parejaB, LIMITE_ASALTOS, ENERGIA_POR_ASALTO, canalAcontecimientos);
			cruces.add(new Cruce(parejaA, parejaB, combate));
		}
		
		return new Ronda(numero, cruces);
	}
}
