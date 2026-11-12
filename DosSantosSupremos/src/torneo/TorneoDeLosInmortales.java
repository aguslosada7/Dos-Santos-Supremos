package torneo;

import java.util.List;

import acontecimientos.CronistaDeLaSecta;
import combatientes.Combatiente;
import combatientes.CreadorCombatientes;
import combatientes.ImpulsoPorCaida;
import equipamiento.AnilloEspiritual;
import equipamiento.SelloDelFenix;
import tecnicas.CorteDeLosMilAmaneceres;

public class TorneoDeLosInmortales {
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

		aurelio.configurarReaccion(new ImpulsoPorCaida(10));

		HeraldoImperial heraldo = new HeraldoImperial();
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		Torneo torneo = new Torneo();
		torneo.suscribir(heraldo);
		torneo.suscribir(cronista);

		torneo.inscribir(new Pareja(1, aurelio, nebula));
		torneo.inscribir(new Pareja(2, kael, darian));
		torneo.inscribir(new Pareja(3, lyra, borin));
		torneo.inscribir(new Pareja(4, selene, orion));
		torneo.iniciar();

		// El Heraldo anuncia a los Dos Santos Supremos al recibir la proclamación;
		// después, el main le pide la tabla al Cronista y el Heraldo la presenta.
		heraldo.presentarTabla(cronista.generarTablaPosiciones());
	}
}
