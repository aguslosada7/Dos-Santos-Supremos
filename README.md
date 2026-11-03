# Dos Santos Supremos

Trabajo Práctico N.º 2 de Paradigmas de Programación: torneo eliminatorio por parejas, diseñado utilizando programación orientada a objetos.

**Estado:** diseño propuesto. El proyecto de Eclipse está configurado para Java 21; la implementación Java y las pruebas JUnit 5 todavía están pendientes.

- [Consigna del trabajo](Consigna.pdf).
- [Diagrama de clases y contratos del diseño](DIAGRAMA_DE_CLASES.md).

## Diseño general

1. **Los acontecimientos conservan datos inmutables del momento:** identificadores, nombres, ronda, asalto y resultados. `ResultadoCombate` registra la vida total final de ambas parejas.
2. **Las técnicas tienen clases concretas**, como `CorteDeLosMilAmaneceres` y `DominioDeLaNada`, compatibles con el `main` de la consigna. Heredan de `Tecnica` e implementan su efecto. Los usos pertenecen al combatiente, nunca a la técnica.
3. **El equipamiento se aplica mediante Decorator sobre las capacidades.** `CapacidadesBase` es el componente inicial y cada `CapacidadesEquipadas` envuelve al componente anterior y suma las bonificaciones de un objeto. `Combatiente` conserva su identidad, vida actual, energía actual y usos.
4. **Las escuelas son configuraciones.** `new Escuela(nombre, tecnicas, equipamiento)` prepara cualquier tipo de combatiente. Las definiciones de técnicas y equipamiento son inmutables y se pueden compartir; cada cadena de capacidades y cada registro de usos son propios.
5. **Los acontecimientos usan Observer y despacho polimórfico.** El Heraldo es la única clase encargada de mostrar información por pantalla. El Cronista conserva el historial y calcula posiciones con los resultados registrados.
6. **El bonus elegido es el combatiente reactivo.** Una estrategia independiente potencia su próximo ataque ofensivo cuando cae su compañero y se reinicia al terminar el combate.

Decorator es una elección del grupo, no un requisito explícito de la consigna. Permite acumular y retirar bonificaciones sin alterar la jerarquía de combatientes.

## Técnicas

`Tecnica` es abstracta y contiene nombre, costo de energía y categoría inmutables. Su método `aplicar(ejecutor, objetivo)` modifica a los combatientes mediante su interfaz pública. Las ofensivas obtienen el daño a través de `ejecutor.calcularDanio(...)`, sin conocer su clase concreta.

| Clase concreta | Categoría | Costo base | Efecto base |
| --- | --- | ---: | --- |
| `CorteDeLosMilAmaneceres` | Ofensiva | 8 | 24 de daño |
| `MuroDelCuerpoDorado` | Defensiva | 12 | Suma 6 de reducción de daño hasta finalizar el combate |
| `DominioDeLaNada` | Ofensiva | 12 | 20 de daño y drena hasta 8 de energía |
| `RugidoDeLaBestiaPrimordial` | Ofensiva | 20 | 40 de daño |
| `MeditacionDelInmortal` | Recuperación | 12 | Recupera hasta 24 de vida |

Los nombres son identificadores únicos e inmutables del catálogo. La igualdad y `hashCode` de técnicas dependen del nombre: dos instancias de `CorteDeLosMilAmaneceres` representan la misma técnica para contar usos y aplicar la regla del compañero. No se permiten definiciones diferentes bajo el mismo nombre.

`Combate` valida que el ejecutor siga en pie, conozca la técnica, tenga energía y usos, y cumpla la regla del compañero. Solo después paga el costo, consume un uso, registra al primer usuario y aplica el efecto. Una acción rechazada no cambia el estado ni publica una acción realizada. Aprender de nuevo una técnica conocida no repone sus usos.

## Equipamiento y Decorator

| Clase concreta | Categoría | Bonificación mientras está equipada |
| --- | --- | --- |
| `EspadaCelestial` | Arma | +6 al daño de cada ataque básico o técnica ofensiva |
| `ArmaduraDorada` | Armadura | Reduce en 4 cada daño recibido |
| `AnilloEspiritual` | Anillo | +16 de energía máxima |
| `SelloDelFenix` | Sello | +20 de vida máxima |

`Equipamiento` es una definición inmutable con nombre, categoría y cuatro bonificaciones; sus clases concretas permiten usar `new EspadaCelestial()`. `CapacidadesEquipadas` es el decorador que combina esa definición con unas `Capacidades` anteriores.

Al equipar o quitar se reconstruye una cadena propia a partir de las capacidades base y de los objetos que quedan equipados, en orden de categoría. No se modifica la definición compartida ni se reutiliza la cadena anterior como base.

```java
Capacidades capacidades = new CapacidadesEquipadas(
        new CapacidadesEquipadas(
                new CapacidadesBase(100, 40), new EspadaCelestial()),
        new ArmaduraDorada());
```

Cada consulta delega en el componente anterior y suma su bonificación. Aquí se obtienen vida máxima 100, energía máxima 40, bonificación de daño 6 y reducción de daño 4.

- Hasta tres objetos, sin repetir categoría. Agregar un cuarto o una categoría ocupada lanza `EquipamientoInvalidoException` y deja todo igual. Para reemplazar un objeto, primero se quita el anterior.
- Los cambios se permiten fuera del combate del combatiente. `Combate` activa y libera la marca `enCombate`; equipar o quitar mientras está activa se rechaza sin cambios.
- Aumentar un máximo no aumenta el valor actual. Tras cualquier cambio, vida y energía actuales se recortan a sus nuevos máximos si los superan.
- Quitar una categoría vacía se rechaza sin cambios. `getEquipamientos()` devuelve una colección no modificable.

## Valores y orden de cálculo

Estos valores son decisiones propuestas del grupo para implementar y justificar en el informe; la consigna no impone estas cantidades, salvo los cinco usos.

| Parámetro | Valor propuesto | Justificación |
| --- | ---: | --- |
| Vida inicial y máxima base | 100 | Referencia simple para daño y recuperación |
| Energía inicial y máxima base | 40 | Permite varias técnicas antes de depender de la regeneración |
| Daño base del ataque básico | 12 | Alternativa cuando no se puede usar una técnica |
| Recuperación de energía por asalto | 6 | Menor al costo mínimo estándar; mantiene relevante administrar energía |
| Límite de asaltos | 50 | Garantiza la terminación aun con defensas y recuperaciones |
| Usos por técnica y combatiente | 5 por torneo | Exigido por la consigna |
| Cultivador de la Espada | +20 % al daño emitido | Especialización ofensiva |
| Monje del Cuerpo Celestial | -20 % al daño recibido | Especialización defensiva |
| Hechicero del Vacío | -25 % al costo de técnicas | Especialización en energía |
| Impulso por caída del compañero | +10 al próximo ataque ofensivo | Bonus acotado a una acción |

El balance de estos valores debe comprobarse mediante partidas.

Orden de aplicación, con enteros y redondeo hacia abajo de cada multiplicación porcentual:

1. **Daño emitido:** base del ataque o técnica + bonificación del equipo; luego modificador del tipo; finalmente +10 si está activo el impulso. Se consume incluso si el rival absorbe todo el daño.
2. **Daño recibido:** se restan equipo y defensa temporal, con mínimo cero; después se aplica el modificador del tipo receptor. La vida queda entre cero y su máximo.
3. **Costo:** el Hechicero paga `max(1, piso(costoBase * 75 / 100))` si el costo es positivo; cero sigue siendo cero. Los demás pagan el costo base. El descuento no afecta al drenaje recibido.
4. **Drenaje y recuperación:** la energía no baja de cero y ninguna recuperación supera el máximo vigente. Un caído no revive durante el combate.

Ejemplo: Corte con Espada produce 30 antes del tipo y 36 si lo ejecuta un Cultivador. Contra un Monje con Armadura y Muro, recibe `piso((36 - 4 - 6) * 80 / 100) = 20`.

## Desarrollo determinista del torneo

- El creador asigna ids consecutivos a los combatientes. Las parejas reciben un id explícito y exactamente dos integrantes diferentes. La igualdad de combatientes depende solo del id, incluso entre tipos distintos.
- Las inscripciones repetidas por id de pareja o combatiente se rechazan antes de modificar colecciones. Solo se inicia con cuatro u ocho parejas.
- Los primeros cruces siguen el orden de inscripción; los siguientes, el orden de los cruces ganados. No se ordena por id para armar cruces.
- Cada combate restaura vida y energía al máximo equipado y limpia defensa, reacción y restricciones de compañero. Los usos y el equipamiento se conservan. Al terminar se limpian efectos y se habilita el cambio de equipo.
- Cada asalto, incluido el primero, recupera energía para los integrantes en pie. Actúan primero la pareja de menor id y, dentro de ella, el integrante de menor id.
- Se elige entre las técnicas ejecutables: recuperación si falta vida; defensa si no hay una activa; ofensiva con más usos. Empates por orden escolar. Si ninguna es posible, ataque básico.
- Se ataca al rival en pie con menos vida; empate por menor id. Defensa y recuperación se aplican al ejecutor.
- Una caída se registra una sola vez y activa la reacción del compañero si sigue en pie. El combate termina inmediatamente cuando una pareja no tiene integrantes en pie.
- La regla del compañero bloquea mientras el primer usuario siga en pie y conserve usos, aunque no tenga energía. Se libera al caer o agotar usos. No cambia el primer usuario registrado.
- Tras 50 asaltos completos gana la pareja con más vida total; empate por menor id.
- Se publica la acción, luego las eliminaciones y finalmente el resultado si terminó. Después de la final se publica la proclamación. No se usan azar ni marcas horarias.
- El Cronista ordena por última ronda descendente, vida total final descendente e id ascendente. Usa los valores de `ResultadoCombate`, sin consultar combatientes ya recuperados.

## Ejemplo de `main` completo

Este ejemplo muestra la API prevista; todavía no es un archivo Java ejecutable. Las clases del dominio se consideran en un mismo paquete para omitir imports propios.

```java
import java.util.List;

public class TorneoDeLosInmortales {
    public static void main(String[] args) {
        Escuela escuelaDeLaEspada = new Escuela(
                "Escuela de la Espada Celestial",
                List.of(new CorteDeLosMilAmaneceres(),
                        new RugidoDeLaBestiaPrimordial()),
                List.of(new EspadaCelestial()));

        Escuela palacioDelVacio = new Escuela(
                "Palacio del Vacío",
                List.of(new DominioDeLaNada(), new MeditacionDelInmortal()),
                List.of(new AnilloEspiritual()));

        Escuela monasterioDelCuerpoDorado = new Escuela(
                "Monasterio del Cuerpo Dorado",
                List.of(new MuroDelCuerpoDorado(), new MeditacionDelInmortal()),
                List.of(new ArmaduraDorada(), new SelloDelFenix()));

        CreadorCombatientes creador = new CreadorCombatientes();
        Combatiente aurelio = creador.crear(
                "Cultivador de la Espada", "Aurelio", escuelaDeLaEspada);
        Combatiente nebula = creador.crear(
                "Hechicero del Vacío", "Nébula", palacioDelVacio);
        Combatiente kael = creador.crear(
                "Monje del Cuerpo Celestial", "Kael", palacioDelVacio);
        Combatiente darian = creador.crear(
                "Cultivador de la Espada", "Darian", escuelaDeLaEspada);
        Combatiente lyra = creador.crear(
                "Hechicero del Vacío", "Lyra", escuelaDeLaEspada);
        Combatiente borin = creador.crear(
                "Monje del Cuerpo Celestial", "Borin", monasterioDelCuerpoDorado);
        Combatiente selene = creador.crear(
                "Cultivador de la Espada", "Selene", monasterioDelCuerpoDorado);
        Combatiente orion = creador.crear(
                "Hechicero del Vacío", "Orión", palacioDelVacio);

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

        heraldo.presentarTabla(cronista.generarTablaPosiciones());
    }
}
```

`CreadorCombatientes()` registra las tres fábricas iniciales. `crear(tipo, nombre, escuela)` resuelve la fábrica, asigna el id y delega la preparación. Un tipo desconocido se rechaza con `IllegalArgumentException`.

`Torneo()` usa los valores anteriores y crea su canal. `suscribir(...)` delega en él y los combates comparten ese canal. Se prevé `Torneo(limiteAsaltos, energiaPorAsalto)` para pruebas. El torneo publica la proclamación y el Heraldo la presenta al recibirla.

## Validación prevista para la implementación

Se deben implementar pruebas JUnit 5 y vincular cada regla de las secciones 4, 5 y 6 de la consigna con al menos un caso en el informe. La [matriz de contratos y pruebas](DIAGRAMA_DE_CLASES.md#contratos-y-pruebas-necesarias) especifica esa cobertura. Las pruebas de combate y torneo consultan estado e historial, sin leer la salida por pantalla.
