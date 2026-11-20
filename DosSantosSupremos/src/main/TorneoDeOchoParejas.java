package main;

import java.util.List;

import acontecimientos.CronistaDeLaSecta;
import acontecimientos.HeraldoImperial;
import combatientes.Combatiente;
import combatientes.CreadorCombatientes;
import combatientes.ImpulsoPorCaida;
import equipamiento.AnilloEspiritual;
import equipamiento.ArmaduraDorada;
import equipamiento.EspadaCelestial;
import equipamiento.SelloDelFenix;
import tecnicas.CorteDeLosMilAmaneceres;
import tecnicas.DominioDeLaNada;
import tecnicas.MeditacionDelInmortal;
import tecnicas.MuroDelCuerpoDorado;
import tecnicas.RugidoDeLaBestiaPrimordial;
import torneo.Escuela;
import torneo.Pareja;
import torneo.Torneo;

public class TorneoDeOchoParejas {

	public static void main(String[] args) {
		// Escuela
		Escuela escuelaDeLaEspada = new Escuela("Escuela de la Espada Celestial",
				List.of(new CorteDeLosMilAmaneceres(), new RugidoDeLaBestiaPrimordial()),
				List.of(new EspadaCelestial()));

		Escuela palacioDelVacio = new Escuela("Palacio del Vacío",
				List.of(new DominioDeLaNada(), new MeditacionDelInmortal()), List.of(new AnilloEspiritual()));

		Escuela monasterioDelCuerpoDorado = new Escuela("Monasterio del Cuerpo Dorado",
				List.of(new MuroDelCuerpoDorado(), new MeditacionDelInmortal()),
				List.of(new ArmaduraDorada(), new SelloDelFenix()));
		
		Escuela escuelaDelAmanecer = new Escuela("Escuela del Amanecer",
				List.of(new CorteDeLosMilAmaneceres(), new MeditacionDelInmortal(), new MuroDelCuerpoDorado()),
				List.of(new AnilloEspiritual()));

		// Creación de combatientes
		CreadorCombatientes creador = new CreadorCombatientes();
		Combatiente aurelio = creador.crear("Cultivador de la Espada", "Aurelio", escuelaDeLaEspada);
		Combatiente nebula = creador.crear("Hechicero del Vacío", "Nébula", palacioDelVacio);
		Combatiente kael = creador.crear("Monje del Cuerpo Celestial", "Kael", palacioDelVacio);
		Combatiente darian = creador.crear("Cultivador de la Espada", "Darian", escuelaDeLaEspada);
		Combatiente lyra = creador.crear("Hechicero del Vacío", "Lyra", escuelaDeLaEspada);
		Combatiente borin = creador.crear("Monje del Cuerpo Celestial", "Borin", monasterioDelCuerpoDorado);
		Combatiente selene = creador.crear("Cultivador de la Espada", "Selene", monasterioDelCuerpoDorado);
		Combatiente orion = creador.crear("Hechicero del Vacío", "Orión", palacioDelVacio);
		Combatiente voldemort = creador.crear("Hechicero del Vacío", "Voldemort", escuelaDelAmanecer);
		Combatiente qu = creador.crear("Cultivador de la Espada", "Qu", escuelaDeLaEspada);
		Combatiente vecna = creador.crear("Monje del Cuerpo Celestial", "Vecna", escuelaDeLaEspada);
		Combatiente sarlaac = creador.crear("Monje del Cuerpo Celestial", "Sarlaac", monasterioDelCuerpoDorado);
		Combatiente aragog = creador.crear("Hechicero del Vacío", "Aragog", palacioDelVacio);
		Combatiente hornet = creador.crear("Cultivador de la Espada", "Hornet", escuelaDelAmanecer);
		Combatiente doom = creador.crear("Monje del Cuerpo Celestial", "Doom", palacioDelVacio);
		Combatiente aquiles = creador.crear("Cultivador de la Espada", "Aquiles", monasterioDelCuerpoDorado);

		aurelio.configurarReaccion(new ImpulsoPorCaida(10));

		HeraldoImperial heraldo = new HeraldoImperial();
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		Torneo torneo = new Torneo();
		torneo.suscribir(heraldo);
		torneo.suscribir(cronista);

		torneo.inscribir(new Pareja(1, voldemort, doom));
		torneo.inscribir(new Pareja(2, selene, vecna));
		torneo.inscribir(new Pareja(3, qu, hornet));
		torneo.inscribir(new Pareja(4, darian, aurelio));
		torneo.inscribir(new Pareja(5, sarlaac, borin));
		torneo.inscribir(new Pareja(6, lyra, kael));
		torneo.inscribir(new Pareja(7, orion, aquiles));
		torneo.inscribir(new Pareja(8, aragog, nebula));
		torneo.iniciar();

		// El Heraldo anuncia a los Dos Santos Supremos al recibir la proclamación;
		// después, el main le pide la tabla al Cronista y el Heraldo la presenta.
		heraldo.presentarTabla(cronista.generarTablaPosiciones());
	}

}
