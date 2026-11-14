package equipamiento;

public class ArmaduraDorada extends Equipamiento{
	public final static int REDUCCION_DANIO = 4;
	
	public ArmaduraDorada() {
		super("Armadura Dorada", CategoriaEquipamiento.ARMADURA, 0, REDUCCION_DANIO, 0, 0);
	}
}
