package barco;
import vista.*;
public class Main {

	
	
	public static void main (String args[]) throws InterruptedException {
		

	
	
		
		Jugadores listaJugador = Jugadores.getJugadores();	
		Pantalla.getPantalla(); // INICIALIZAR PANTALLA
		listaJugador.jugadoresColocanBarcos();
		

		try {
			while (true) {
				listaJugador.jugadorActualCompra();
				listaJugador.jugadorActualDispara();
				listaJugador.cambiarJugador();
				
				
	
				
			}

			
			
		} catch (ExcepcionFinDePartida excepcion) {


			
			if (excepcion.ganoJ1()) {
				TextoYAudio.getInstancia().setTexto("Jugador Humano gana");
			} else {
				TextoYAudio.getInstancia().setTexto("Jugador IA gana");
			}
			TextoYAudio.getInstancia().actualizarCambios();
		}
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {}
		
		Pantalla.getPantalla().dispose();
		
		
		
		
	}
}
