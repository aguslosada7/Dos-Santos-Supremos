package equipamiento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class EquipamientoTest {
	private static class ObjetoDePrueba extends Equipamiento {
		ObjetoDePrueba(String nombre, CategoriaEquipamiento categoria, int danio, int reduccion, int vida,
				int energia) {
			super(nombre, categoria, danio, reduccion, vida, energia);
		}
	}

	@Test
	void guardaNombreCategoriaYLasCuatroBonificaciones() {
		Equipamiento objeto = new ObjetoDePrueba("Prueba", CategoriaEquipamiento.ARMA, 1, 2, 3, 4);

		assertEquals("Prueba", objeto.getNombre());
		assertEquals(CategoriaEquipamiento.ARMA, objeto.getCategoria());
		assertEquals(1, objeto.getBonificacionDanio());
		assertEquals(2, objeto.getReduccionDanio());
		assertEquals(3, objeto.getBonificacionVidaMaxima());
		assertEquals(4, objeto.getBonificacionEnergiaMaxima());
	}

	@Test
	void aceptaBonificacionesEnCero() {
		Equipamiento objeto = new ObjetoDePrueba("Vacío", CategoriaEquipamiento.SELLO, 0, 0, 0, 0);

		assertEquals(0, objeto.getBonificacionDanio());
		assertEquals(0, objeto.getReduccionDanio());
		assertEquals(0, objeto.getBonificacionVidaMaxima());
		assertEquals(0, objeto.getBonificacionEnergiaMaxima());
	}

	@Test
	void rechazaBonificacionesNegativas() {
		CategoriaEquipamiento arma = CategoriaEquipamiento.ARMA;

		assertThrows(IllegalArgumentException.class, () -> new ObjetoDePrueba("X", arma, -1, 0, 0, 0));
		assertThrows(IllegalArgumentException.class, () -> new ObjetoDePrueba("X", arma, 0, -1, 0, 0));
		assertThrows(IllegalArgumentException.class, () -> new ObjetoDePrueba("X", arma, 0, 0, -1, 0));
		assertThrows(IllegalArgumentException.class, () -> new ObjetoDePrueba("X", arma, 0, 0, 0, -1));
	}

	@Test
	void rechazaNombreOCategoriaNulos() {
		assertThrows(NullPointerException.class,
				() -> new ObjetoDePrueba(null, CategoriaEquipamiento.ARMA, 0, 0, 0, 0));
		assertThrows(NullPointerException.class, () -> new ObjetoDePrueba("X", null, 0, 0, 0, 0));
	}
}
