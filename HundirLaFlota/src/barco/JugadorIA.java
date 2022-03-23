package barco;

import java.util.Random;

public class JugadorIA extends Jugador {

	
	private Random generador;
	
	
	private Posicion tocadoInicial;
	
	private int sentidoEnComprobacion;
	private int distancia;
	
	public JugadorIA() {
		super(false);
		this.generador = new Random();
	}

	
	private void esperar(int pMs) {
		try {
			Thread.sleep(pMs);
		} catch (InterruptedException e) {}
	}

	public void colocarBarcos() {

		// FALTA ESTO
		
		
	}
	
	private Posicion elegirTiroPreciso() {
		
		
		/* Pre: Turno de IA y ha encontrado un barco que quiere hundir (tocado inicial /= null)
		        El valor sentidoEnComprobacion decide en que sentido le toca buscar y distancia
		        a cuantas casillas del origen del tiro está
		 
		 Post: La posicion marcada en cuestion por estas variables si no esta marcada y existe en el tablero
		       Si esto no ocurre devuelve null actualizando la distancia a 1 de nuevo y cambiando la direccion
		       del siguiente tiro. Si se devuelve null se volverá a llamar a este metodo inmediatamente hasta
		       que se de un valor concreto.
		 
		  
		  
		 */
		
		Posicion p = null;
		JugadorHumano jHu = Jugadores.getJugadores().getJugadorHumano();

		switch (this.sentidoEnComprobacion) {
		
			case 0: // Mirar arriba
			
				if (this.tocadoInicial.getFila() - this.distancia >= 0 && !jHu.haDisparadoAhi(this.tocadoInicial.getFila()-distancia, this.tocadoInicial.getCol())) {
					p = new Posicion(this.tocadoInicial.getFila()-this.distancia, this.tocadoInicial.getCol());
					this.distancia++;

				} else {
					this.sentidoEnComprobacion = 1;
					this.distancia = 1;
				}

			
				break;
		
			case 1:   // Mirar derecha
			
				if (this.tocadoInicial.getCol() + this.distancia <= 9 && !jHu.haDisparadoAhi(this.tocadoInicial.getFila(), this.tocadoInicial.getCol()+distancia)) {
					p = new Posicion(this.tocadoInicial.getFila(), this.tocadoInicial.getCol()+distancia);
					this.distancia++;

				} else {
					this.sentidoEnComprobacion = 2;
					this.distancia = 1;
				}
			
			
				break;
		
			case 2:   // Mirar abajo
			
				if (this.tocadoInicial.getFila() + this.distancia <= 9 && !jHu.haDisparadoAhi(this.tocadoInicial.getFila()+distancia, this.tocadoInicial.getCol())) {
					p = new Posicion(this.tocadoInicial.getFila()+this.distancia, this.tocadoInicial.getCol());
					this.distancia++;

				} else {
					this.sentidoEnComprobacion = 3;
					this.distancia = 1;
				}
			
				break;
			case 3:	  // Mirar izquierda
			
				if (this.tocadoInicial.getCol() - this.distancia >= 0 && !jHu.haDisparadoAhi(this.tocadoInicial.getFila(), this.tocadoInicial.getCol()-distancia)) {
					p = new Posicion(this.tocadoInicial.getFila(), this.tocadoInicial.getCol()-distancia);
					this.distancia++;

				} else {
					this.sentidoEnComprobacion = 0;
					this.distancia = 1;
				}
			
			default:
			

		}
		
		return p;
	}
	
	private Posicion elegirTiro() {
		
		/* Pre: Turno de IA y necesita tirar un proyectil
		   Post: Posicion para disparar, calculado por:
		   
		   Si la IA no había encontrado un barco sin hundir para tirar lanza a una casilla sin marcar al azar
		   Si la IA había encontrado un barco sin hundir:
		   		Trata de hundirlo lanzando ataques en los 4 sentidos a la casilla a la que se le pego al barco
		   		por primera vez
		   		
		   		El sentido en el que decide empezar a atacar es aleatorio, pero sigue un patrón circular:
		   		arriba --> derecha --> abajo --> izquierda
		   		
		   		La IA procederá a atacar el barco en el sentido que le toque. 
		   		Si toca el barco en el sentido actual avanza una posición en ese
		   		Si llega a una esquina del tablero o a una casilla ya marcada cambia de dirección
		   		Si el barco es hundido procederá a lanzar ataques aleatorios de nuevo hasta tocar un barco
 
		 */
		
		
		JugadorHumano jHu = Jugadores.getJugadores().getJugadorHumano();
		int fila=-1;
		int col=-1;
		boolean valido = false;
		Posicion p = null;
		
		if (this.tocadoInicial == null) {  // Tiro completamente aleatorio
			
			while (!valido) {               
				fila = this.generador.nextInt(10);
				col = this.generador.nextInt(10);
				valido = !jHu.haDisparadoAhi(fila, col);
			}
			
			p = new Posicion(fila,col);


			
		} else {                            // Tiro con más certeza
			
			while (p == null) {
				p = this.elegirTiroPreciso();
			}
			
		

			
		}
		
		return p;
		
	}
	
	public void disparar() throws ExcepcionFinDePartida {
		
		// Pre: Turno de IA
		// Post: La IA ha disparado hasta fallar una bomba o matado toda la tripulación enemiga (lanza Excepcion)
		
		
		boolean encadenar = true;
		boolean res[];
		Posicion p = null;
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		JugadorHumano jHu = Jugadores.getJugadores().getJugadorHumano();
		
		
		this.esperar(1500);
		
		while (encadenar) {

	
				
			p = this.elegirTiro();
			
			
			

			res = jHu.dispararEn(p.getFila(), p.getCol());
				
			if (res[0]) {
					

					
				if (res[1]) {
						
					if (this.tocadoInicial != null) { // Si se estaban centrando tiros en un barco, se quita pq se acaba de hundir
						this.tocadoInicial = null;
					}
						
					jHu.hundeUnBarco();
					jHu.acabaLaPartida(); // Lanza excepcion si se quedo sin barcos								
					textoYAudio.setTexto("IA dispara en: "+(p.getFila()+1)+" "+(char)(65+p.getCol())+ ". Barco hundido");
						
				} else {
						
					if (this.tocadoInicial == null) { // Si toco un barco lanzando al azar y no hundio, centrar los siguientes tiros en el
						this.tocadoInicial = new Posicion(p.getFila(),p.getCol());
						this.sentidoEnComprobacion = this.generador.nextInt(4);
						this.distancia = 1;
				}
						
				textoYAudio.setTexto("IA dispara en: "+(p.getFila()+1)+" "+(char)(65+p.getCol())+ ". Barco tocado");

				}
					
			} else {
				
				if (this.tocadoInicial != null) {
					this.sentidoEnComprobacion++; if (this.sentidoEnComprobacion == 4) {this.sentidoEnComprobacion = 0;}
					this.distancia = 1;
				}
				
				
				encadenar = false; //Fallo, fin del bucle
				textoYAudio.setTexto("IA dispara en: "+(p.getFila()+1)+" "+(char)(65+p.getCol())+ ". Agua");

				
				
				
			}
				

				
			jHu.actualizarCambios();
			textoYAudio.actualizarCambios();


			this.esperar(1500);

			


				
			
			
		}
		

		
	}
		
	
	
}
