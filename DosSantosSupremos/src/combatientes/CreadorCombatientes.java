package combatientes;

import java.util.Objects;

public class CreadorCombatientes {
	private static int SIGUIENTE_ID = 1;
	
	public Combatiente crear(String tipo, String nombre, Escuela escuela) {
		Combatiente combatiente;
		
		Objects.requireNonNull(escuela, "La escuela es obligatoria.");
		
		if(tipo == "Hechicero del Vacío")
			combatiente = new HechiceroDelVacio(SIGUIENTE_ID, nombre);
		
		if(tipo == "Monje del Cuerpo Celestial")
			combatiente = new MonjeDelCuerpoCelestial(SIGUIENTE_ID, nombre);
		
		if(tipo == "Cultivador de la Espada")
			combatiente = new CultivadorDeLaEspada(SIGUIENTE_ID, nombre);
		
		escuela.preparar(combatiente);
		
		SIGUIENTE_ID++;
		return combatiente;
	}
}
