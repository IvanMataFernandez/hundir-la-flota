package barco;

import java.util.ArrayList;
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
		
	   	 int[] bConRes = new int[4]; // barcos concretos restantes (de 4,3,2 y 1)
	   	 int tamSelec = 3; // tamaño del barco que se quiere poner
	   	 int f1;
	   	 int c1;
	   	 boolean valido;
	   	 boolean selecHorizontal=false;
	   	 boolean porColocar;
	   	 boolean generado = false;
	   	 Posicion p1 = null;
	   	 Posicion p2 = null;
	   	 Posicion aux = null;
	   	 ArrayList<Integer> arrayDirecciones = null;
	   	 int posicion;
	   	 int direccion;
	   	 
	   	 for (int i = 0; i != 4; i++) {
	   		 bConRes[i] = 4-i;
	   	 }

	   	 while (tamSelec >= 0) {
	   		 if (bConRes[tamSelec] != 0) {
	   			 generado = false;
	   			 valido = false;
	   			
	   			 while (!generado) {
	   				 f1 = generador.nextInt(10);
	   				 c1 = generador.nextInt(10);			 
	   				 p1 = new Posicion(f1, c1);	 
	   				 arrayDirecciones = direccionesPosibles(p1, tamSelec);
	   				 porColocar = true;
	   				
	   				 while (porColocar) {
	   					 posicion = generador.nextInt(arrayDirecciones.size());  //Decide si el barco se podnrá hacia N,S,E,O
	   					 direccion = arrayDirecciones.get(posicion);
	   						 
	   					 switch (direccion) {
	   						case 0: //NORTE
  								 p2 = new Posicion(f1-tamSelec, c1);
  								 selecHorizontal = false;
	   							break;
	   						case 1: //SUR
  								 p2 = new Posicion(f1+tamSelec, c1);
   								 selecHorizontal = false;
	   							break;
	   						case 2://ESTE
  								 p2 = new Posicion(f1, c1-tamSelec);
  								 selecHorizontal = true;
	   							break;
	   						case 3: //OESTE
  								 p2 = new Posicion(f1, c1+tamSelec);
  								 selecHorizontal = true;
	   						default:
	   							
	   					}
	   					
	   					 if (p1.getFila() > p2.getFila() || p1.getCol() > p2.getCol()) {
	   						 aux = p1;
	   						 p1 = p2;
	   						 p2 = aux;
	   					 }
		   				

		   				
		   				valido = super.posibleColocar(p1, p2, selecHorizontal); //Mira que no choque con otros barcos
							 
							 
		   				if (valido) { // Si se puede...

		   					super.generarBarco(p1, p2, tamSelec, selecHorizontal); //Pone el barco

		   					bConRes[tamSelec]--;
	   								 
		   					super.actualizarCambios();
		   					generado = true;
		   					porColocar = false;
	   								 
	   					 } else {
	   						 arrayDirecciones.remove(posicion);
	   						 
	   						 if (arrayDirecciones.isEmpty()) {porColocar = false; }
	   						 
	   					 }

	   				 }
	   						 
		 
	   			}
	   					 
	   		} else {tamSelec = tamSelec-1;}

	   	}
	   	 
	   	 super.completarTablero();	 
	   	 super.actualizarCambios();
	    
	}
	
	private ArrayList<Integer> direccionesPosibles(Posicion p1, int tamSelec) {
	    ArrayList<Integer> arrayDireccionesAux = new ArrayList<Integer>(); //Creamos el array con 4 posiciones
	    int f1 = p1.getFila();
	    int c1 = p1.getCol();
	    
	   	 //Puede Norte?    Si puede norte 100-->0
	   	 if (f1-tamSelec > -1) {
	   		 arrayDireccionesAux.add(0);
	   	 }
	   	 
	   	 //Puede Sur?    Si puede sur 100-->1
	   	 if (f1+tamSelec < 10) {
	   		 arrayDireccionesAux.add(1);
	   	 }
	   	 
	   	 //Puede Este?    Si puede este 100-->2
	   	 if (c1-tamSelec > -1) {
	   		 arrayDireccionesAux.add(2);
	   	 }
	   	 
	   	 //Puede Oeste?    Si puede oeste 100-->3
	   	 if (c1+tamSelec < 10) {
	   		 arrayDireccionesAux.add(3);
	   	 }
	    
	    return (arrayDireccionesAux);
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
					textoYAudio.setTexto("IA dispara en: "+(p.getFila()+1)+" "+(char)(65+p.getCol())+ ". Barco hundido");
					textoYAudio.setAudio("hundido");
					jHu.acabaLaPartida(); // Lanza excepcion si se quedo sin barcos								


				} else {
						
					if (this.tocadoInicial == null) { // Si toco un barco lanzando al azar y no hundio, centrar los siguientes tiros en el
						this.tocadoInicial = new Posicion(p.getFila(),p.getCol());
						this.sentidoEnComprobacion = this.generador.nextInt(4);
						this.distancia = 1;
				}
						
				textoYAudio.setTexto("IA dispara en: "+(p.getFila()+1)+" "+(char)(65+p.getCol())+ ". Barco tocado");
				textoYAudio.setAudio("tocado");

				}
					
			} else {
				
				if (this.tocadoInicial != null) {
					this.sentidoEnComprobacion++; if (this.sentidoEnComprobacion == 4) {this.sentidoEnComprobacion = 0;}
					this.distancia = 1;
				}
				
				
				encadenar = false; //Fallo, fin del bucle
				textoYAudio.setTexto("IA dispara en: "+(p.getFila()+1)+" "+(char)(65+p.getCol())+ ". Agua");
				textoYAudio.setAudio("agua");
				
				
				
			}
				

				
			jHu.actualizarCambios();
			textoYAudio.actualizarCambios();


			this.esperar(1600);

			


				
			
			
		}
		

		
	}
		
	
	
}
