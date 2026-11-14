package combatientes;

public class HechiceroDelVacio extends Combatiente {
	private final static int PORCENTAJE_REDUCCION_COSTO = 75;
	
	public HechiceroDelVacio(int id, String nombre) {
		super(id, nombre);
	}
	
	@Override
    protected int modificarCostoEnergiaPorTipo(int costo) {
        if (costo <= 0) {
            return 0;
        }
        
        // Se aplica -25% al costo de técnicas
        return Math.max(1, (costo * PORCENTAJE_REDUCCION_COSTO) / 100);
    }
}
