package main;

import java.util.List;

import acontecimientos.CronistaDeLaSecta;
import acontecimientos.HeraldoImperial;
import combatientes.Combatiente;
import combatientes.CreadorCombatientes;
import combatientes.ImpulsoPorCaida;
import equipamiento.AnilloEspiritual;
import equipamiento.ArmaduraDorada;
import equipamiento.CategoriaEquipamiento;
import equipamiento.EspadaCelestial;
import equipamiento.SelloDelFenix;
import tecnicas.CorteDeLosMilAmaneceres;
import tecnicas.DominioDeLaNada;
import tecnicas.MeditacionDelInmortal;
import tecnicas.MuroDelCuerpoDorado;
import tecnicas.RugidoDeLaBestiaPrimordial;
import torneo.Escuela;
import torneo.InscripcionInvalidaException;
import torneo.Pareja;
import torneo.Torneo;

public class PresentacionTorneo {
	public static void main(String[] args) {
		// 1. Escuelas: configuraciones reutilizables
		Escuela escuelaDeLaEspada = new Escuela("Escuela de la Espada Celestial",
				List.of(new CorteDeLosMilAmaneceres(), new RugidoDeLaBestiaPrimordial()),
				List.of(new EspadaCelestial()));

		Escuela palacioDelVacio = new Escuela("Palacio del Vacío",
				List.of(new DominioDeLaNada(), new MeditacionDelInmortal()),
				List.of(new AnilloEspiritual()));

		Escuela monasterioDelCuerpoDorado = new Escuela("Monasterio del Cuerpo Dorado",
				List.of(new MuroDelCuerpoDorado(), new MeditacionDelInmortal()),
				List.of(new ArmaduraDorada(), new SelloDelFenix()));

		// 2. Creacion de los 3 tipos (polimorfismo)
		CreadorCombatientes creador = new CreadorCombatientes();
		Combatiente nebula = creador.crear("Hechicero del Vacío", "Nébula", palacioDelVacio);
		Combatiente darian = creador.crear("Cultivador de la Espada", "Darian", palacioDelVacio);
		Combatiente aurelio = creador.crear("Cultivador de la Espada", "Aurelio", escuelaDeLaEspada);
		Combatiente kael = creador.crear("Monje del Cuerpo Celestial", "Kael", monasterioDelCuerpoDorado);
		Combatiente lyra = creador.crear("Hechicero del Vacío", "Lyra", escuelaDeLaEspada);
		Combatiente borin = creador.crear("Monje del Cuerpo Celestial", "Borin", monasterioDelCuerpoDorado);
		Combatiente selene = creador.crear("Cultivador de la Espada", "Selene", escuelaDeLaEspada);
		Combatiente orion = creador.crear("Hechicero del Vacío", "Orión", palacioDelVacio);

		// 3. Gestión de equipamiento antes del torneo y bonus reactivo:
		// Darian reemplaza el anillo de su escuela y equipa hasta 3 categorias; si Nebula (su compañero) cae primero
		// se libera Dominio de la Nada y se activa el ImpulsoPorCaida de Darian.
		darian.quitar(CategoriaEquipamiento.ANILLO);
		darian.equipar(new EspadaCelestial());
		darian.equipar(new ArmaduraDorada());
		darian.equipar(new SelloDelFenix());
		darian.configurarReaccion(new ImpulsoPorCaida(10));

		// 4. Torneo de 4 parejas (2 rondas) con Heraldo y Cronista como observadores
		HeraldoImperial heraldo = new HeraldoImperial();
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		Torneo torneo = new Torneo();
		torneo.suscribir(heraldo);
		torneo.suscribir(cronista);

		Pareja pareja1 = new Pareja(1, nebula, darian);
		torneo.inscribir(pareja1);
		torneo.inscribir(new Pareja(2, aurelio, kael));
		torneo.inscribir(new Pareja(3, lyra, borin));
		torneo.inscribir(new Pareja(4, selene, orion));

		// Validacion de reglas de inscripcion sin alterar el torneo
		try {
			torneo.inscribir(pareja1);
		} catch (InscripcionInvalidaException esperada) {
			// Se rechaza x pareja duplicada
		}

		// 5. Ejecucion de semifinales y final (Heraldo anuncia eventos y tabla final)
		torneo.iniciar();
		heraldo.presentarTabla(cronista.generarTablaPosiciones());
	}
}
