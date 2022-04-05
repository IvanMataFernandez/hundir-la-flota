

package barco;
import vista.*;
public class Main {

	
	
	public static void main (String args[]) throws InterruptedException {
		
		
		// EL MAIN NO VA A IR DEL TODO HASTA QUE SE HAYA ACABADO EL PONER BARCOS DE IA
		
		
		
		/* STRATEGY --> BOMBAS / MISIL
		 * STATE -----> ESCUDO / NO ESCUDO
		 * 
		 * MIARAR EL DE COLOCAR IA
		  
		 
		  
		 */
	
		Jugadores listaJugador = Jugadores.getJugadores();	
		Pantalla.getPantalla(); // INICIALIZAR PANTALLA
		listaJugador.jugadoresColocanBarcos();
		

		try {
			while (true) {
				listaJugador.jugadorActualDispara();
				listaJugador.cambiarJugador();
				
				
			}

			
			
		} catch (ExcepcionFinDePartida excepcion) {


			
			if (excepcion.ganoJ1()) {
				// GANA J1!
			} else {
				// GANA IA!
			}
			
		}
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {}
		
		Pantalla.getPantalla().dispose();
		
		
		
		
	}
}
