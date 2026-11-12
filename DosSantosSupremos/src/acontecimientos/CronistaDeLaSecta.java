package acontecimientos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CronistaDeLaSecta implements ObservadorAcontecimientos {
	private final List<Acontecimiento> historial = new ArrayList<>();
	private final Map<Integer, RegistroPosicion> ultimaPosicionPorPareja = new HashMap<>();

	public CronistaDeLaSecta() {
		super();
	}

	@Override
	public void recibirInicioCombate(InicioCombate evento) {
		registrar(evento);
	}

	@Override
	public void recibirAccionRealizada(AccionRealizada evento) {
		registrar(evento);
	}

	@Override
	public void recibirEliminacion(Eliminacion evento) {
		registrar(evento);
	}

	@Override
	public void recibirResultadoCombate(ResultadoCombate evento) {
		registrar(evento);

		ultimaPosicionPorPareja.put(evento.idParejaA(),
				new RegistroPosicion(evento.idParejaA(), evento.ronda(), evento.vidaTotalFinalParejaA()));
		ultimaPosicionPorPareja.put(evento.idParejaB(),
				new RegistroPosicion(evento.idParejaB(), evento.ronda(), evento.vidaTotalFinalParejaB()));
	}

	@Override
	public void recibirProclamacion(Proclamacion evento) {
		registrar(evento);
	}

	public void registrar(Acontecimiento evento) {
		historial.add(Objects.requireNonNull(evento, "El acontecimiento es obligatorio."));
	}

	public List<Acontecimiento> getHistorial() {
		return List.copyOf(historial);
	}

	public List<RegistroPosicion> generarTablaPosiciones() {
		List<RegistroPosicion> tabla = new ArrayList<>(ultimaPosicionPorPareja.values());

		Collections.sort(tabla);

		return List.copyOf(tabla);
	}
}
