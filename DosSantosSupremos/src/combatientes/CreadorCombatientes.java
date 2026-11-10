package combatientes;

import java.util.Objects;

public class CreadorCombatientes {
	private static int SIGUIENTE_ID = 1;
	
	public Combatiente crear(String tipo, String nombre, Escuela escuela) {
		Combatiente combatiente;
		
		if (nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar en blanco.");
        }
		
		Objects.requireNonNull(escuela, "La escuela es obligatoria.");
		
		if(tipo.equalsIgnoreCase("Hechicero del Vacío"))
			combatiente = new HechiceroDelVacio(SIGUIENTE_ID, nombre);
		
		else if(tipo.equalsIgnoreCase("Monje del Cuerpo Celestial"))
			combatiente = new MonjeDelCuerpoCelestial(SIGUIENTE_ID, nombre);
		
		else if(tipo.equalsIgnoreCase("Cultivador de la Espada"))
			combatiente = new CultivadorDeLaEspada(SIGUIENTE_ID, nombre);
		
		else
			throw new IllegalArgumentException("Tipo de combatiente desconocido: " + tipo);
		
		escuela.preparar(combatiente);
		
		SIGUIENTE_ID++;
		return combatiente;
	}
}
