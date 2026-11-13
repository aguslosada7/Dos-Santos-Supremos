package tecnicas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import combatientes.Combatiente;
import combatientes.HechiceroDelVacio;

class TecnicaTest {

	private static class TecnicaDePrueba extends Tecnica {
		TecnicaDePrueba(String nombre, int costoEnergia, int danio, CategoriaTecnica categoria) {
			super(nombre, costoEnergia, danio, categoria);
		}

		public void aplicar(Combatiente ejecutor, Combatiente objetivo) {
			objetivo.recibirDanio(ejecutor.calcularDanio(getDanio()));
		}
	}

	@Test
	void guardaNombreCostoDanioYCategoria() {
		Tecnica tecnica = new TecnicaDePrueba("Golpe Relámpago", 10, 25, CategoriaTecnica.OFENSIVA);

		assertEquals("Golpe Relámpago", tecnica.getNombre());
		assertEquals(10, tecnica.getCostoEnergia());
		assertEquals(25, tecnica.getDanio());
		assertEquals(CategoriaTecnica.OFENSIVA, tecnica.getCategoria());
		assertEquals(5, Tecnica.getUsosMaximosPorTecnica());
	}

	@Test
	void rechazaNombreNuloOEnBlanco() {
		assertThrows(NullPointerException.class, () -> new TecnicaDePrueba(null, 10, 20, CategoriaTecnica.OFENSIVA));
		assertThrows(IllegalArgumentException.class, () -> new TecnicaDePrueba("", 10, 20, CategoriaTecnica.OFENSIVA));
		assertThrows(IllegalArgumentException.class, () -> new TecnicaDePrueba("   ", 10, 20, CategoriaTecnica.OFENSIVA));
	}

	@Test
	void rechazaCostoNegativo() {
		assertThrows(IllegalArgumentException.class, () -> new TecnicaDePrueba("Golpe", -1, 20, CategoriaTecnica.OFENSIVA));
	}

	@Test
	void rechazaDanioNegativo() {
		assertThrows(IllegalArgumentException.class, () -> new TecnicaDePrueba("Golpe", 10, -5, CategoriaTecnica.OFENSIVA));
	}

	@Test
	void rechazaCategoriaNula() {
		assertThrows(NullPointerException.class, () -> new TecnicaDePrueba("Golpe", 10, 20, null));
	}

	@Test
	void dosInstanciasConElMismoNombreRepresentanLaMismaTecnica() {
		Tecnica primera = new TecnicaDePrueba("Corte Astral", 8, 20, CategoriaTecnica.OFENSIVA);
		Tecnica segunda = new TecnicaDePrueba("Corte Astral", 8, 20, CategoriaTecnica.OFENSIVA);

		assertEquals(primera, segunda);
		assertEquals(primera.hashCode(), segunda.hashCode());
	}

	@Test
	void tecnicasConDistintoNombreSonDistintas() {
		Tecnica primera = new TecnicaDePrueba("Corte Astral", 8, 20, CategoriaTecnica.OFENSIVA);
		Tecnica segunda = new TecnicaDePrueba("Otro Corte", 8, 20, CategoriaTecnica.OFENSIVA);

		assertNotEquals(primera, segunda);
	}

	@Test
	void aplicarUsaPolimorfismo() {
		Combatiente ejecutor = new HechiceroDelVacio(1, "Nébula");
		Combatiente objetivo = new HechiceroDelVacio(2, "Luis");
		Tecnica tecnica = new TecnicaDePrueba("Golpe", 10, 30, CategoriaTecnica.OFENSIVA);

		tecnica.aplicar(ejecutor, objetivo);

		assertEquals(70, objetivo.getVidaActual());
	}
}
