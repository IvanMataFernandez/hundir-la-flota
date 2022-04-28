package barco;

import java.util.ArrayList;

public class Jugadores {

	private static Jugadores lista;

	private ArrayList<Jugador> jugadores;
	private boolean apuntaAJ1;
	
	private Jugadores() {
		this.jugadores = new ArrayList<Jugador>();
		this.jugadores.add(new JugadorHumano());
		this.jugadores.add(new JugadorIA());
		this.apuntaAJ1 = true;
	}
	
	public static Jugadores getJugadores() {
		if (Jugadores.lista == null) {Jugadores.lista = new Jugadores();}
		return Jugadores.lista;
	}
	
	private Jugador getJugadorActual() {
		if (this.apuntaAJ1) {
			return this.jugadores.get(0);
		} else {
			return this.jugadores.get(1);
		}
	}
	
	public void jugadorActualDispara() throws ExcepcionFinDePartida {
		this.getJugadorActual().procesarAcciones();
	}
	
	public void jugadorActualCompra() {
		this.getJugadorActual().procesarCompras();
	}
	

	
	public void jugadoresColocanBarcos() {
		this.jugadores.stream().forEach((j) -> j.colocarBarcos());
	}
	
	public void cambiarJugador() {this.apuntaAJ1 = !this.apuntaAJ1;}
	
	
	public JugadorHumano getJugadorHumano() {return (JugadorHumano) this.jugadores.get(0);}
	
	public JugadorIA getJugadorIA() {return (JugadorIA) this.jugadores.get(1);}
	
	
	
	
	
}