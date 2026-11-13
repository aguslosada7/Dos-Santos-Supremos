package combatientes;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import equipamiento.CategoriaEquipamiento;
import equipamiento.Equipamiento;
import tecnicas.CategoriaTecnica;
import tecnicas.Tecnica;
import torneo.Pareja;

public class Combatiente {
	private int id;
	private String nombre;
	private int vidaActual;
	private int energiaActual;
	private int danioBase;
	private boolean enCombate;
	private int reduccionDefensivaCombate;
	private ComportamientoReactivo comportamientoReactivo;
	private Map<Tecnica, Integer> usosRestantesPorTecnica = new LinkedHashMap<>();
	private Map<CategoriaEquipamiento, Equipamiento> equipamientoPorCategoria = new EnumMap<>(CategoriaEquipamiento.class);
	private final static int VIDA_MAXIMA = 100;
	private final static int ENERGIA_MAXIMA = 40;
	private final static int DANIO_ATAQUE_BASICO = 12;
	
	public Combatiente(int id, String nombre) {
		if (id <= 0) {
			throw new IllegalArgumentException("El id debe ser positivo.");
		}
		Objects.requireNonNull(nombre, "El nombre es obligatorio.");
		if (nombre.isBlank()) {
			throw new IllegalArgumentException("El nombre no puede estar en blanco.");
		}
		
		this.id = id;
		this.nombre = nombre;
		vidaActual = VIDA_MAXIMA;
		energiaActual = ENERGIA_MAXIMA;
		danioBase = DANIO_ATAQUE_BASICO;
	}
	
	// MÉTODOS PROTEGIDOS
	protected int modificarDanioPorTipo(int danio) {
		return danio;
	}
	
	protected int modificarCostoEnergiaPorTipo(int costo) {
		return costo;
	}
	
	protected int modificarDanioRecibidoPorTipo(int cantidad) {
		return cantidad;
	}

	// MÉTODOS DE TÉCNICAS
	public void aprender(Tecnica tecnica) {
		Objects.requireNonNull(tecnica, "La técnica es obligatoria.");
		
		if(!usosRestantesPorTecnica.containsKey(tecnica))
			usosRestantesPorTecnica.put(tecnica, Tecnica.getUsosMaximosPorTecnica());
	}
	
	public int usosRestantes(Tecnica tecnica) {
		Objects.requireNonNull(tecnica, "La técnica es obligatoria.");
		
		return usosRestantesPorTecnica.getOrDefault(tecnica, 0);
	}
	
	public boolean puedeUsar(Tecnica tecnica) {
		if (tecnica == null || !estaEnPie())
            return false;
        
        if (!usosRestantesPorTecnica.containsKey(tecnica) || usosRestantes(tecnica) <= 0)
            return false;
        
        int costo = calcularCostoEnergia(tecnica.getCostoEnergia());
        if(energiaActual < costo)
        	return false;
        
        return true;
	}
	
	public void consumirUso(Tecnica tecnica) {
		Objects.requireNonNull(tecnica, "La técnica es obligatoria.");
		
		int usos = usosRestantes(tecnica);
		if(usos <= 0)
			throw new IllegalStateException("La técnica no tiene más usos");
		
		usosRestantesPorTecnica.put(tecnica, usos - 1);
	}
	
	public Tecnica elegirTecnica(Pareja pareja) {
		Objects.requireNonNull(pareja, "La pareja es obligatoria.");
		
		if(!estaEnPie())
			return null;
		
		if(vidaActual < getVidaTotal()) {
			for(Tecnica tecnica: usosRestantesPorTecnica.keySet()) {
				if(tecnica.getCategoria() == CategoriaTecnica.RECUPERACION && puedeEjecutarTecnica(pareja, tecnica))
					return tecnica;
			}
		}
		
		if(reduccionDefensivaCombate == 0) {
			for(Tecnica tecnica: usosRestantesPorTecnica.keySet()) {
				if(tecnica.getCategoria() == CategoriaTecnica.DEFENSIVA && puedeEjecutarTecnica(pareja, tecnica))
					return tecnica;
			}
		}
		
		Tecnica mejorOfensiva = null;
		int maxUsos = -1;
		for(Tecnica tecnica: usosRestantesPorTecnica.keySet()) {
			if(tecnica.getCategoria() == CategoriaTecnica.OFENSIVA && puedeEjecutarTecnica(pareja, tecnica)) {
				int usos = usosRestantes(tecnica);
				if(usos > maxUsos) {
					maxUsos = usos;
					mejorOfensiva = tecnica;
				}
			}
		}
		
		if(mejorOfensiva != null)
			return mejorOfensiva;
		
		return null;
	}
	
	private boolean puedeEjecutarTecnica(Pareja pareja, Tecnica tecnica) {
		return puedeUsar(tecnica) && pareja.puedeUsarTecnica(this, tecnica);
	}
	
	// MÉTODOS DE EQUIPAMIENTO
	public void equipar(Equipamiento objeto) {
		Objects.requireNonNull(objeto, "El objeto es obligatorio.");
		
		if(equipamientoPorCategoria.size() >= 3)
			throw new IllegalStateException("Un combatiente no puede llevar más de 3 equipamientos.");
		
		if(equipamientoPorCategoria.containsKey(objeto.getCategoria()))
			throw new IllegalArgumentException("No se puede equipar más de un objeto de la misma categoría");
			
		if(enCombate)
			throw new IllegalStateException("Un combatiente no puede equiparse durante un combate.");
		
		equipamientoPorCategoria.put(objeto.getCategoria(), objeto);
	}
	
	public void quitar(CategoriaEquipamiento categoria) {
		Objects.requireNonNull(categoria, "La categoria es obligatoria.");
		
		if(enCombate)
			throw new IllegalStateException("Un combatiente no puede desequiparse durante un combate.");
		
		if(!equipamientoPorCategoria.containsKey(categoria))
			throw new IllegalArgumentException("El combatiente no tiene equipamiento para quitar de la categoría " + categoria);
		
		equipamientoPorCategoria.remove(categoria);

		int maxVida = getVidaTotal();
		if (vidaActual > maxVida) {
			vidaActual = maxVida;
		}

		int maxEnergia = getEnergiaTotal();
		if (energiaActual > maxEnergia) {
			energiaActual = maxEnergia;
		}
	}
	
	// MÉTODOS DE COMBATE
	public boolean tieneDefensaActiva() {
		if(reduccionDefensivaCombate > 0)
			return true;
		
		return false;
	}
	
	public void recuperarVida(int cantidad){
		if(cantidad <= 0)
			throw new IllegalArgumentException("La cantidad debe ser positiva.");
		
		if(!estaEnPie())
			return ;
		
		vidaActual += cantidad;
		int maxVida = getVidaTotal();
		if(vidaActual > maxVida)
			vidaActual = maxVida;
	}
	
	public int calcularDanio(int danioBase) {
		if(danioBase < 0)
			throw new IllegalArgumentException("El daño debe ser positivo.");
		
		int bonificacionEquipo = 0;
		for(Equipamiento equipamiento: equipamientoPorCategoria.values())
			bonificacionEquipo += equipamiento.getBonificacionDanio();
		
		int danioTotal = danioBase + bonificacionEquipo;
		danioTotal = modificarDanioPorTipo(danioTotal);
		
		if(comportamientoReactivo != null)
			danioTotal = comportamientoReactivo.modificarProximoDanio(danioTotal);
		
		return danioTotal;
	}
	
	public int calcularCostoEnergia(int costoBase) {
		if(costoBase < 0)
			throw new IllegalArgumentException("El costo debe ser positivo.");
			
		return modificarCostoEnergiaPorTipo(costoBase);
	}
	
	public void recibirDanio(int cantidad) {
		if(cantidad <= 0)
			throw new IllegalArgumentException("La cantidad debe ser positiva.");
		
		if(!estaEnPie() || cantidad == 0)
			return ;
		
		int reduccionEquipo = 0;
        for (Equipamiento equipamiento : equipamientoPorCategoria.values())
            reduccionEquipo += equipamiento.getReduccionDanio();
        
        int danioNeto = cantidad - reduccionEquipo - reduccionDefensivaCombate;
        if(danioNeto < 0)
        	danioNeto = 0;
        
        int danioFinal = modificarDanioRecibidoPorTipo(danioNeto);
        
        vidaActual -= danioFinal;
        if(vidaActual < 0)
        	vidaActual = 0;
	}
	
	public void recuperarEnergia(int cantidad) {
		if(cantidad < 0)
			throw new IllegalArgumentException("La cantidad debe ser positiva.");
		
		if(!estaEnPie())
			return ;
		
		energiaActual += cantidad;
		int maxEnergia = getEnergiaTotal();
		if(energiaActual > maxEnergia)
			energiaActual = maxEnergia;
	}
	
	public void perderEnergia(int cantidad) {
		if(cantidad <= 0)
			throw new IllegalArgumentException("La cantidad debe ser positiva.");
		
		if(!estaEnPie())
			return ;
		
		energiaActual -= cantidad;
		if(energiaActual < 0)
			energiaActual = 0;
	}
	
	public void aplicarDefensaCombate(int reduccion) {
		if(reduccion <= 0)
			throw new IllegalArgumentException("La reducción debe ser positiva.");
		
		reduccionDefensivaCombate += reduccion;
	}
	
	public void iniciarCombate() {
		vidaActual = getVidaTotal();
		energiaActual = getEnergiaTotal();
		reduccionDefensivaCombate = 0;
		enCombate = true;
		
		if(comportamientoReactivo != null)
			comportamientoReactivo.reiniciar();
	}
	
	public void finalizarCombate() {
		reduccionDefensivaCombate = 0;
		enCombate = false;
		
		if(comportamientoReactivo != null)
			comportamientoReactivo.reiniciar();
	}
	
	public Combatiente elegirObjetivo(List<Combatiente> rivales) {
		if(rivales == null || rivales.isEmpty())
			throw new IllegalArgumentException("Los rivales son obligatorios.");
		
		Combatiente objetivo = null;
		for(Combatiente rival: rivales) {
			if(rival.estaEnPie()) {
				if(objetivo == null)
					objetivo = rival;
				else if(rival.getVidaActual() < objetivo.getVidaActual())
					objetivo = rival;
				else if(rival.getVidaActual() == objetivo.getVidaActual() && rival.getId() < objetivo.getId())
					objetivo = rival;
			}
		}
		
		if(objetivo == null)
			throw new IllegalStateException("No hay rivales en pie.");
		
		return objetivo;
	}
	
	// MÉTODOS DEL BONUS
	public void alCaerCompaniero() {
		if(comportamientoReactivo != null)
			comportamientoReactivo.activar();
	}
	
	public void configurarReaccion(ComportamientoReactivo reaccion) {
		comportamientoReactivo = reaccion;
	}
	
	// OTROS MÉTODOS
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(id));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Combatiente other = (Combatiente) obj;
		return id == other.id;
	}
	
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getVidaActual() {
		return vidaActual;
	}

	public int getEnergiaActual() {
		return energiaActual;
	}
	
	public int getDanioBase() {
		return danioBase;
	}

	public static int getVidaMaxima() {
		return VIDA_MAXIMA;
	}

	public static int getEnergiaMaxima() {
		return ENERGIA_MAXIMA;
	}
	
	public Collection<Equipamiento> getEquipamientos() {
        return Collections.unmodifiableCollection(equipamientoPorCategoria.values());
    }
	
	public int getVidaTotal() {
		int extra = 0;
		for (Equipamiento equipamiento : equipamientoPorCategoria.values())
			extra += equipamiento.getBonificacionVidaMaxima();
		
		return VIDA_MAXIMA + extra;
	}

	public int getEnergiaTotal() {
		int extra = 0;
		for (Equipamiento equipamiento : equipamientoPorCategoria.values())
			extra += equipamiento.getBonificacionEnergiaMaxima();
		
		return ENERGIA_MAXIMA + extra;
	}
	
	public boolean estaEnPie() {
		if(vidaActual <= 0)
			return false;
		
		return true;
	}
}
