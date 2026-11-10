package equipamiento;

public class SelloDelFenix extends Equipamiento {
	private final static int VIDA_EXTRA = 20;
	
	public SelloDelFenix() {
		super("Sello del Fénix", CategoriaEquipamiento.SELLO, 0, 0, VIDA_EXTRA, 0);
	}
}
