package acontecimientos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class RegistroPosicionTest {
	@Test
	void guardaLosDatosDeLaPosicionInclusoConVidaCero() {
		RegistroPosicion primero = new RegistroPosicion(3, 2, 85);
		RegistroPosicion eliminado = new RegistroPosicion(4, 1, 0);

		assertEquals(3, primero.idPareja());
		assertEquals(2, primero.rondaUltimoCombate());
		assertEquals(85, primero.vidaTotalUltimoCombate());
		assertEquals(0, eliminado.vidaTotalUltimoCombate());
	}

	@Test
	void rechazaDatosInvalidos() {
		assertThrows(IllegalArgumentException.class, () -> new RegistroPosicion(0, 1, 50));
		assertThrows(IllegalArgumentException.class, () -> new RegistroPosicion(-1, 1, 50));
		assertThrows(IllegalArgumentException.class, () -> new RegistroPosicion(1, 0, 50));
		assertThrows(IllegalArgumentException.class, () -> new RegistroPosicion(1, -2, 50));
		assertThrows(IllegalArgumentException.class, () -> new RegistroPosicion(1, 1, -1));
	}

	@Test
	void ordenaPorRondaDescendenteVidaDescendenteEIdAscendente() {
		RegistroPosicion campeon = new RegistroPosicion(5, 3, 20);
		RegistroPosicion finalista = new RegistroPosicion(2, 3, 0);
		RegistroPosicion semiMayorVida = new RegistroPosicion(4, 2, 70);
		RegistroPosicion semiEmpateIdMenor = new RegistroPosicion(1, 2, 40);
		RegistroPosicion semiEmpateIdMayor = new RegistroPosicion(3, 2, 40);
		RegistroPosicion primeraRondaMuchaVida = new RegistroPosicion(6, 1, 200);

		List<RegistroPosicion> registros = new ArrayList<>(List.of(primeraRondaMuchaVida, semiEmpateIdMayor, finalista,
				semiMayorVida, semiEmpateIdMenor, campeon));
		Collections.sort(registros);

		assertEquals(List.of(campeon, finalista, semiMayorVida, semiEmpateIdMenor, semiEmpateIdMayor,
				primeraRondaMuchaVida), registros);
	}

	@Test
	void compareToEsConsistenteConIgualdadYRechazaNulo() {
		RegistroPosicion a = new RegistroPosicion(2, 1, 50);
		RegistroPosicion igual = new RegistroPosicion(2, 1, 50);
		RegistroPosicion menor = new RegistroPosicion(3, 1, 50);

		assertEquals(0, a.compareTo(igual));
		assertEquals(a, igual);
		assertEquals(a.hashCode(), igual.hashCode());
		assertTrue(a.compareTo(menor) < 0);
		assertTrue(menor.compareTo(a) > 0);
		assertThrows(NullPointerException.class, () -> a.compareTo(null));
	}
}
