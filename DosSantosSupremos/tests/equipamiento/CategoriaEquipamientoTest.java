package equipamiento;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class CategoriaEquipamientoTest {
	@Test
	void tieneLasCuatroCategoriasDeEquipamiento() {
		CategoriaEquipamiento[] categorias = { CategoriaEquipamiento.ARMA, CategoriaEquipamiento.ARMADURA,
				CategoriaEquipamiento.ANILLO, CategoriaEquipamiento.SELLO };

		assertArrayEquals(categorias, CategoriaEquipamiento.values());
	}
}
