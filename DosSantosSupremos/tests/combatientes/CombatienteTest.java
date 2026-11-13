package combatientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import equipamiento.ArmaduraDorada;
import equipamiento.CategoriaEquipamiento;
import equipamiento.EspadaCelestial;
import equipamiento.SelloDelFenix;
import tecnicas.CorteDeLosMilAmaneceres;
import tecnicas.DominioDeLaNada;
import tecnicas.MeditacionDelInmortal;
import tecnicas.MuroDelCuerpoDorado;
import tecnicas.Tecnica;
import torneo.Pareja;

class CombatienteTest {

	private static class CombatienteDePrueba extends Combatiente {
		CombatienteDePrueba(int id, String nombre) {
			super(id, nombre);
		}
	}

	@Test
	void guardaIdYNombres() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");

		assertEquals(1, combatiente.getId());
		assertEquals("Aurelio", combatiente.getNombre());
	}

	@Test
	void valoresInicialesDeVidaEnergiaYDanio() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");

		assertEquals(100, combatiente.getVidaActual());
		assertEquals(100, Combatiente.getVidaMaxima());
		assertEquals(40, combatiente.getEnergiaActual());
		assertEquals(40, Combatiente.getEnergiaMaxima());
		assertEquals(12, combatiente.getDanioBase());
		assertTrue(combatiente.estaEnPie());
	}

	@Test
	void dosCombatientesConElMismoIdSonIguales() {
		Combatiente primero = new CombatienteDePrueba(1, "Aurelio");
		Combatiente segundo = new CombatienteDePrueba(1, "Darian");

		assertEquals(primero, segundo);
		assertEquals(primero.hashCode(), segundo.hashCode());
	}

	@Test
	void rechazaIdNoPositivoONombreInvalido() {
		assertThrows(IllegalArgumentException.class, () -> new CombatienteDePrueba(0, "Aurelio"));
		assertThrows(IllegalArgumentException.class, () -> new CombatienteDePrueba(-1, "Aurelio"));
		assertThrows(NullPointerException.class, () -> new CombatienteDePrueba(1, null));
		assertThrows(IllegalArgumentException.class, () -> new CombatienteDePrueba(1, "   "));
	}

	@Test
	void aprenderTecnicaInicializaCincoUsos() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();

		combatiente.aprender(corte);

		assertEquals(5, combatiente.usosRestantes(corte));
	}

	@Test
	void reaprenderNoReiniciaLosUsos() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();

		combatiente.aprender(corte);
		combatiente.consumirUso(corte);
		combatiente.consumirUso(corte);
		assertEquals(3, combatiente.usosRestantes(corte));

		combatiente.aprender(corte);
		assertEquals(3, combatiente.usosRestantes(corte));
	}

	@Test
	void consumirUsoReduceEnUno() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		combatiente.aprender(corte);

		combatiente.consumirUso(corte);

		assertEquals(4, combatiente.usosRestantes(corte));
	}

	@Test
	void consumirUsoSinUsosRestantesLanzaExcepcion() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		combatiente.aprender(corte);

		for (int i = 0; i < 5; i++) {
			combatiente.consumirUso(corte);
		}

		assertThrows(IllegalStateException.class, () -> combatiente.consumirUso(corte));
	}

	@Test
	void noSeEjecutaUnaTecnicaSinEnergiaOSinUsosRestantes() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		combatiente.aprender(corte);

		assertTrue(combatiente.puedeUsar(corte));

		combatiente.perderEnergia(35);
		assertFalse(combatiente.puedeUsar(corte));

		combatiente.recuperarEnergia(40);
		for (int i = 0; i < 5; i++) {
			combatiente.consumirUso(corte);
		}
		assertEquals(0, combatiente.usosRestantes(corte));
		assertFalse(combatiente.puedeUsar(corte));

		Combatiente caido = new CombatienteDePrueba(2, "Caído");
		caido.aprender(corte);
		caido.recibirDanio(caido.getVidaActual());
		assertFalse(caido.puedeUsar(corte));
	}

	@Test
	void permiteEquiparHastaTresObjetosDeDistintaCategoria() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		EspadaCelestial espada = new EspadaCelestial();
		ArmaduraDorada armadura = new ArmaduraDorada();
		SelloDelFenix sello = new SelloDelFenix();

		combatiente.equipar(espada);
		combatiente.equipar(armadura);
		combatiente.equipar(sello);

		assertEquals(3, combatiente.getEquipamientos().size());
		assertTrue(combatiente.getEquipamientos().containsAll(List.of(espada, armadura, sello)));
	}

	@Test
	void equiparUnCuartoObjetoSeRechazaSinCambios() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		combatiente.equipar(new EspadaCelestial());
		combatiente.equipar(new ArmaduraDorada());
		combatiente.equipar(new SelloDelFenix());

		assertThrows(IllegalStateException.class, () -> combatiente.equipar(new EspadaCelestial()));
		assertEquals(3, combatiente.getEquipamientos().size());
	}

	@Test
	void equiparUnaCategoriaRepetidaSeRechazaSinCambios() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		combatiente.equipar(new EspadaCelestial());

		assertThrows(IllegalArgumentException.class, () -> combatiente.equipar(new EspadaCelestial()));
		assertEquals(1, combatiente.getEquipamientos().size());
	}

	@Test
	void rechazaEquiparODesequiparDuranteElCombate() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		EspadaCelestial espada = new EspadaCelestial();
		combatiente.iniciarCombate();

		assertThrows(IllegalStateException.class, () -> combatiente.equipar(espada));

		combatiente.finalizarCombate();
		combatiente.equipar(espada);
		combatiente.iniciarCombate();

		assertThrows(IllegalStateException.class, () -> combatiente.quitar(CategoriaEquipamiento.ARMA));
	}

	@Test
	void rechazaQuitarCategoriaNoEquipada() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");

		assertThrows(IllegalArgumentException.class, () -> combatiente.quitar(CategoriaEquipamiento.ARMA));
	}

	@Test
	void alQuitarUnEquipamientoQueSubeLaVidaMaximaLaVidaActualSeRecorta() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		SelloDelFenix sello = new SelloDelFenix();
		combatiente.equipar(sello);

		assertEquals(120, combatiente.getVidaTotal());
		combatiente.recuperarVida(20);
		assertEquals(120, combatiente.getVidaActual());

		combatiente.quitar(CategoriaEquipamiento.SELLO);

		assertEquals(100, combatiente.getVidaTotal());
		assertEquals(100, combatiente.getVidaActual());
	}

	@Test
	void equiparNoAumentaLaVidaActual() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		assertEquals(100, combatiente.getVidaActual());

		combatiente.equipar(new SelloDelFenix());

		assertEquals(120, combatiente.getVidaTotal());
		assertEquals(100, combatiente.getVidaActual());
	}

	@Test
	void recibirDanioReduceLaVidaActual() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");

		combatiente.recibirDanio(30);

		assertEquals(70, combatiente.getVidaActual());
		assertTrue(combatiente.estaEnPie());
	}

	@Test
	void alLlegarACeroDeVidaQuedaFueraDeCombate() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");

		combatiente.recibirDanio(100);

		assertEquals(0, combatiente.getVidaActual());
		assertFalse(combatiente.estaEnPie());
	}

	@Test
	void unCaidoNoReviveDuranteElCombate() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		combatiente.recibirDanio(100);
		assertFalse(combatiente.estaEnPie());

		combatiente.recuperarVida(50);

		assertEquals(0, combatiente.getVidaActual());
		assertFalse(combatiente.estaEnPie());
	}

	@Test
	void recuperarVidaYEnergiaNoSuperanLosMaximos() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		combatiente.recibirDanio(20);
		combatiente.perderEnergia(15);

		combatiente.recuperarVida(50);
		combatiente.recuperarEnergia(50);

		assertEquals(100, combatiente.getVidaActual());
		assertEquals(40, combatiente.getEnergiaActual());
	}

	@Test
	void perderEnergiaNoBajaDeCero() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");

		combatiente.perderEnergia(60);

		assertEquals(0, combatiente.getEnergiaActual());
	}

	@Test
	void losUsosSeConservanEntreCombatesVidaYEnergiaSeRecuperan() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		combatiente.aprender(corte);

		combatiente.iniciarCombate();
		combatiente.consumirUso(corte);
		combatiente.consumirUso(corte);
		combatiente.recibirDanio(50);
		combatiente.perderEnergia(20);
		combatiente.finalizarCombate();

		assertEquals(50, combatiente.getVidaActual());
		assertEquals(20, combatiente.getEnergiaActual());
		assertEquals(3, combatiente.usosRestantes(corte));

		combatiente.iniciarCombate();

		assertEquals(100, combatiente.getVidaActual());
		assertEquals(40, combatiente.getEnergiaActual());
		assertEquals(3, combatiente.usosRestantes(corte));
	}

	@Test
	void defensaCombateSeReiniciaAlTerminarCombate() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		combatiente.iniciarCombate();

		combatiente.aplicarDefensaCombate(6);
		assertTrue(combatiente.tieneDefensaActiva());

		combatiente.finalizarCombate();
		assertFalse(combatiente.tieneDefensaActiva());
	}

	@Test
	void unCaidoNoActuaNiEsElegidoComoObjetivo() {
		Combatiente ejecutor = new CombatienteDePrueba(1, "Aurelio");
		Combatiente rivalEnPie = new CombatienteDePrueba(2, "Kael");
		Combatiente rivalCaido = new CombatienteDePrueba(3, "Lyra");
		rivalCaido.recibirDanio(100);

		Combatiente objetivo = ejecutor.elegirObjetivo(List.of(rivalCaido, rivalEnPie));

		assertEquals(rivalEnPie, objetivo);
	}

	@Test
	void elegirObjetivoDesempataPorMenorId() {
		Combatiente ejecutor = new CombatienteDePrueba(1, "Aurelio");
		Combatiente rivalA = new CombatienteDePrueba(5, "Rival 5");
		Combatiente rivalB = new CombatienteDePrueba(3, "Rival 3");

		Combatiente objetivo = ejecutor.elegirObjetivo(List.of(rivalA, rivalB));

		assertEquals(rivalB, objetivo);
	}

	@Test
	void elegirTecnicaPriorizaRecuperacionSiFaltaVida() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		Combatiente companiero = new CombatienteDePrueba(2, "Nebula");
		Pareja pareja = new Pareja(1, combatiente, companiero);

		MeditacionDelInmortal curacion = new MeditacionDelInmortal();
		CorteDeLosMilAmaneceres ataque = new CorteDeLosMilAmaneceres();
		combatiente.aprender(ataque);
		combatiente.aprender(curacion);

		combatiente.recibirDanio(30);

		Tecnica elegida = combatiente.elegirTecnica(pareja);

		assertNotNull(elegida);
		assertEquals("Meditación del Inmortal", elegida.getNombre());
	}

	@Test
	void elegirTecnicaPriorizaDefensaSiNoTieneActiva() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		Combatiente companiero = new CombatienteDePrueba(2, "Nebula");
		Pareja pareja = new Pareja(1, combatiente, companiero);

		MuroDelCuerpoDorado defensa = new MuroDelCuerpoDorado();
		CorteDeLosMilAmaneceres ataque = new CorteDeLosMilAmaneceres();
		combatiente.aprender(ataque);
		combatiente.aprender(defensa);

		Tecnica elegida = combatiente.elegirTecnica(pareja);

		assertNotNull(elegida);
		assertEquals("Muro del Cuerpo Dorado", elegida.getNombre());
	}

	@Test
	void elegirTecnicaEligeOfensivaConMasUsosRestantes() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		Combatiente companiero = new CombatienteDePrueba(2, "Nebula");
		Pareja pareja = new Pareja(1, combatiente, companiero);

		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		DominioDeLaNada dominio = new DominioDeLaNada();
		combatiente.aprender(corte);
		combatiente.aprender(dominio);

		combatiente.aplicarDefensaCombate(6);
		combatiente.consumirUso(corte);

		Tecnica elegida = combatiente.elegirTecnica(pareja);

		assertEquals(dominio, elegida);
	}

	@Test
	void siNingunaTecnicaEsPosibleRetornaNullParaAtaqueBasico() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		Combatiente companiero = new CombatienteDePrueba(2, "Nebula");
		Pareja pareja = new Pareja(1, combatiente, companiero);

		// Sin técnicas aprendidas
		assertNull(combatiente.elegirTecnica(pareja));
	}

	@Test
	void alCaerCompanieroActivaReaccionSiEstaConfigurada() {
		Combatiente combatiente = new CombatienteDePrueba(1, "Aurelio");
		ImpulsoPorCaida impulso = new ImpulsoPorCaida(10);
		combatiente.configurarReaccion(impulso);

		assertEquals(12, combatiente.calcularDanio(12));

		combatiente.alCaerCompaniero();

		// El próximo ataque debe tener +10 de daño
		assertEquals(22, combatiente.calcularDanio(12));
		// El siguiente vuelve a ser normal
		assertEquals(12, combatiente.calcularDanio(12));
	}
}
