package equipamiento;

public class AnilloEspiritual extends Equipamiento {
	private final static int ENERGIA_EXTRA = 16;

	public AnilloEspiritual() {
		super("Anillo Espiritual", CategoriaEquipamiento.ANILLO, 0, 0, 0, ENERGIA_EXTRA);
	}
}
