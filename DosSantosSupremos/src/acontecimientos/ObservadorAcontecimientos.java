package acontecimientos;

public interface ObservadorAcontecimientos {
	public void recibirInicioCombate(InicioCombate evento);
	public void recibirAccionRealizada(AccionRealizada evento);
	public void recibirEliminacion(Eliminacion evento);
	public void recibirResultadoCombate(ResultadoCombate evento);
	public void recibirProclamacion(Proclamacion evento);
}
