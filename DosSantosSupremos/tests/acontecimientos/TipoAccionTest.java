package acontecimientos;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class TipoAccionTest {
	@Test
	void tieneAtaqueBasicoYTecnica() {
		TipoAccion[] tipos = { TipoAccion.ATAQUE_BASICO, TipoAccion.TECNICA };

		assertArrayEquals(tipos, TipoAccion.values());
	}
}
