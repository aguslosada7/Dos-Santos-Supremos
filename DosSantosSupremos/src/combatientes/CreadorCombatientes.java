package combatientes;

public class CreadorCombatientes {
	private static int siguienteId = 1;
	
	public Combatiente crear(String tipo, String nombre, Escuela escuela) {
		Combatiente combatiente;
		
		if(tipo == "Hechicero del Vacío")
			combatiente = new HechiceroDelVacio(siguienteId, nombre);
		
		if(tipo == "Monje del Cuerpo Celestial")
			combatiente = new MonjeDelCuerpoCelestial(siguienteId, nombre);
		
		if(tipo == "Cultivador de la Espada")
			combatiente = new CultivadorDeLaEspada(siguienteId, nombre);
		
		escuela.preparar();
		
		siguienteId += 1;
		return combatiente;
	}
}
