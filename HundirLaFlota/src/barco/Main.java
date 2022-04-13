package barco;
import vista.*;
public class Main {

	
	
	public static void main (String args[]) throws InterruptedException {
		
		
		/*
		 ESCUDOS YA CREADOS, PARA USARLOS GENERARLO DESDE LA FACTORY Y USAR SU METODO
		 USAR SOBRE UNA CASILLA DEL BARCO EN LA MATRIZ. 
		  
		  
		  
		  
		 
		 
		  
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
