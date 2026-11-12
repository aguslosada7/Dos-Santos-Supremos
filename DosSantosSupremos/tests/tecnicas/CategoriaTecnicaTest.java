package tecnicas;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class CategoriaTecnicaTest {
	@Test
	void tieneOfensivaDefensivaYRecuperacion() {
		CategoriaTecnica[] categorias = { CategoriaTecnica.OFENSIVA, CategoriaTecnica.DEFENSIVA,
				CategoriaTecnica.RECUPERACION };

		assertArrayEquals(categorias, CategoriaTecnica.values());
	}
}
