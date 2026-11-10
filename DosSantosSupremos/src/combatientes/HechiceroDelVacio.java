package combatientes;

public class HechiceroDelVacio extends Combatiente {
	public HechiceroDelVacio(int id, String nombre) {
		super(id, nombre);
	}
	
	@Override
    protected int modificarCostoEnergiaPorTipo(int costo) {
        if (costo <= 0) {
            return 0;
        }
        
        // Se aplica -25% al costo de técnicas
        return Math.max(1, (costo * 75) / 100);
    }
}
