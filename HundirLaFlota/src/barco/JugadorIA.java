package barco;

import java.util.Random;

public class JugadorIA extends Jugador {

	
	private Random generador;
	
	public JugadorIA() {
		super(false);
		this.generador = new Random();
	}

	


	public void colocarBarcos() {
		// NO HECHO TODAVIA
		

		
		
		
	}
	
	public void disparar() throws ExcepcionFinDePartida {
		boolean valido = false;
		boolean res[];
		int fila;
		int col;
		JugadorHumano jHu = Jugadores.getJugadores().getJugadorHumano();
		while (!valido) {

			fila = this.generador.nextInt(10);
			col = this.generador.nextInt(10);
	
		
			valido = !jHu.haDisparadoAhi(fila, col);
			if (valido) {

				res = jHu.dispararEn(fila, col);
				
				
				if (res[1]) {
					jHu.hundeUnBarco();
					jHu.acabaLaPartida(); // Lanza excepcion si se quedo sin barcos								
					valido = !res[0]; // Si ha tocado, valido false para repetir bucle.	
					jHu.actualizarCambios();
				}
				
			} 

				
			
			
		}
		
	}
		
	
	
}
