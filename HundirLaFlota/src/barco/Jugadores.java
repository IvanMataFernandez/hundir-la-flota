package barco;

public class Jugadores {

	private static Jugadores lista;
	private JugadorHumano j1;
	private JugadorIA j2;
	private boolean apuntaAJ1;
	
	private Jugadores() {
		this.j1 = new JugadorHumano();
		this.j2 = new JugadorIA();
		this.apuntaAJ1 = true;
	}
	
	public static Jugadores getJugadores() {
		if (Jugadores.lista == null) {Jugadores.lista = new Jugadores();}
		return Jugadores.lista;
	}
	
	private Jugador getJugadorActual() {
		if (this.apuntaAJ1) {
			return this.j1;
		} else {
			return this.j2;
		}
	}
	
	public void jugadorActualDispara() throws ExcepcionFinDePartida {
		this.getJugadorActual().disparar();
	}
	

	
	public void jugadoresColocanBarcos() {
		this.j2.colocarBarcos();
		this.j1.colocarBarcos();
	}
	
	public void cambiarJugador() {this.apuntaAJ1 = !this.apuntaAJ1;}
	
	
	public JugadorHumano getJugadorHumano() {return this.j1;}
	
	public JugadorIA getJugadorIA() {return this.j2;}
	
	
	
	
	
}