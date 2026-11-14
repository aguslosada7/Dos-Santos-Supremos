package tecnicas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import combatientes.HechiceroDelVacio;
import combatientes.Combatiente;

class MuroDelCuerpoDoradoTest {
	@Test
	void esUnaTecnicaDefensivaQueCuestaDoce() {
		MuroDelCuerpoDorado muro = new MuroDelCuerpoDorado();

		assertEquals("Muro del Cuerpo Dorado", muro.getNombre());
		assertEquals(CategoriaTecnica.DEFENSIVA, muro.getCategoria());
		assertEquals(12, muro.getCostoEnergia());
	}

	@Test
	void reduceElDanioRecibidoSoloPorElEjecutor() {
		Combatiente ejecutor = new HechiceroDelVacio(1, "Ana");
		Combatiente otro = new HechiceroDelVacio(2, "Luis");
		MuroDelCuerpoDorado muro = new MuroDelCuerpoDorado();
		int vidaEjecutor = ejecutor.getVidaActual();
		int vidaOtro = otro.getVidaActual();

		muro.aplicar(ejecutor, otro);
		ejecutor.recibirDanio(10);
		otro.recibirDanio(10);

		assertEquals(vidaEjecutor - 4, ejecutor.getVidaActual());
		assertEquals(vidaOtro - 10, otro.getVidaActual());
	}
}
