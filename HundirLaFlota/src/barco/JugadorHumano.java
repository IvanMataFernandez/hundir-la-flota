package barco;

import vista.*;


public class JugadorHumano extends Jugador {

	private boolean aceptaInput;
	private int filaSelec;
	private int colSelec;
	private boolean matrizJ1Selec;


	
	public JugadorHumano () {
		super(true);
		
	}
	
	
	
	public void disparar() throws ExcepcionFinDePartida {
		boolean valido = false;
		boolean res[];
		JugadorIA jIA = Jugadores.getJugadores().getJugadorIA();
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		textoYAudio.setTexto("Elige donde disparar");
		while (!valido) {
			textoYAudio.actualizarCambios();
			this.esperarInput();
			valido = !this.matrizJ1Selec;
			
			if (!valido) {
				textoYAudio.setTexto("Pincha en el tablero del rival");

			} else {
				valido = !jIA.haDisparadoAhi(filaSelec, colSelec);
				
				if (!valido) {
					textoYAudio.setTexto("Ese espacio ya fue disparado");

				} else {
					res = jIA.dispararEn(new Posicion(filaSelec, colSelec));
					

					if (res[0]) {
											
						if (res[1]) { // HUNDIDO
							
							jIA.hundeUnBarco();

							textoYAudio.setTexto("Disparas en:  "+(this.filaSelec+1)+" "+(char)(65+this.colSelec)+ ". Barco hundido");
							textoYAudio.setAudio("hundido");
							
							jIA.acabaLaPartida(); // Lanza excepcion si se quedo sin barcos
							
						} else { // ESCUDO
						
							textoYAudio.setTexto("Disparas en:  "+(this.filaSelec+1)+" "+(char)(65+this.colSelec)+ ". Bloqueado por escudo");
						//	textoYAudio.setAudio("bloqueado por escudo");
							
						}
						
					} else {
						
						if (res[1]) { // TOCADO
					
							textoYAudio.setTexto("Disparas en:  "+(this.filaSelec+1)+" "+(char)(65+this.colSelec)+ ". Barco tocado");
							textoYAudio.setAudio("tocado");
							
						} else { // AGUA
							
							textoYAudio.setTexto("Disparas en:  "+(this.filaSelec+1)+" "+(char)(65+this.colSelec)+ ". Agua");
							textoYAudio.setAudio("agua");
							
						}
						
						
					}
	
					
					valido = !res[1]; // Si ha tocado o hundido, repetir proceso.
					
					jIA.actualizarCambios();

				}
				
			}
			
		}
		
		textoYAudio.actualizarCambios();

		
		
		
	}
	

	
	

	public void colocarBarcos() {

		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		int barcosRestantes = 10;
		int[] bConRes = new int[4]; // barcos concretos restantes (de 4,3,2 y 1)
		int tamSelec; // tamaño del barco que se quiere poner
		int seleccionesHechas;
		boolean valido;
		boolean selecHorizontal=false;
		Posicion p1 = null;
		Posicion p2 = null;
		Posicion aux;
		
		textoYAudio.setTexto("Coloca los barcos, pincha en la casilla para poner");
		
		for (int i = 0; i != 4; i++) {
			bConRes[i] = 4-i;
		}


		
		while (barcosRestantes != 0) {
			seleccionesHechas = 0;
			
			
			while (seleccionesHechas != 2) {

				valido = false;
				while (!valido) {
					textoYAudio.actualizarCambios();

					this.esperarInput();
					valido = this.matrizJ1Selec;
					
					if (!valido) {
						textoYAudio.setTexto("Has pinchado en el tablero del rival");
						textoYAudio.setAudio("error");

					} else {
						if (seleccionesHechas == 0) {

							p1 = new Posicion(this.filaSelec, this.colSelec);
							textoYAudio.setTexto("Pinchado en: "+(this.filaSelec+1)+" "+(char)(65+this.colSelec));


						} else {
							
							p2 = new Posicion(this.filaSelec, this.colSelec);
				


						}
						

						seleccionesHechas++;
					}
				}
			}
			
				tamSelec = -1;
				
				if (p1.getFila() == p2.getFila()) {
					
					if (p1.getCol() > p2.getCol()) {
						tamSelec = p1.getCol()-p2.getCol();
						aux = p2;
						p2 = p1;
						p1 = aux;

					} else {
						tamSelec = p2.getCol()-p1.getCol();
						
					}
					
					selecHorizontal = true;
					
				} else if (p1.getCol() == p2.getCol()) {
					
					if (p1.getFila() > p2.getFila()) {
						tamSelec = p1.getFila()-p2.getFila();
						aux = p2;
						p2 = p1;
						p1 = aux;
					} else {
						tamSelec = p2.getFila()-p1.getFila();
						
					}
					
					selecHorizontal = false;

				}
				
				if (tamSelec >= 0 && tamSelec <=3) {
					if (bConRes[tamSelec] != 0) {
						
						
						

						
						
						// CALCULAR
						valido = super.posibleColocar(p1, p2, selecHorizontal);
						


						
						if (valido) {

							super.generarBarco(p1, p2, tamSelec, selecHorizontal);

							bConRes[tamSelec]--;
							barcosRestantes--;
							
							textoYAudio.setTexto("Barco colocado, te quedan "+bConRes[tamSelec]+ " de ese tipo");
							textoYAudio.setAudio("construccion");
							
							super.actualizarCambios();


						} else {
							textoYAudio.setTexto("Barco demasiado próximo a otro. No se ha colocado.");
							textoYAudio.setAudio("error");

						}
						
						
					} else {
						textoYAudio.setTexto("Has puesto ya todos los barcos de "+(tamSelec+1)+" espacios.");
						textoYAudio.setAudio("error");

					}
				} else {
					
					if (tamSelec == -1) {
						textoYAudio.setTexto("No se permite poner en diagonal");
						textoYAudio.setAudio("error");


					} else {
						textoYAudio.setTexto("Barcos de hasta 4 casillas de longitud, no más.");
						textoYAudio.setAudio("error");

						
					}
					
				}
				
			
				
				
				textoYAudio.actualizarCambios();
			

		}
		
		
		
		super.completarTablero();
		super.actualizarCambios();
	
		
		
	}
	
	
	public void cogerInput(CasillaDePantalla pCas) {
		
		if (this.aceptaInput) {
			this.aceptaInput = false;
			this.filaSelec = pCas.getFila();
			this.colSelec = pCas.getCol();
			this.matrizJ1Selec = pCas.esDeJ1();
			
			
		}
		
	//	System.out.println("La fila es: " +pF+" \nLa columna es: "+pC+"\n La matriz es del jugador: " +pJ);
	}
	
	private void esperarInput() {
		
		
		this.aceptaInput = true;
		this.filaSelec = -1;
		
		

		while (this.filaSelec == -1) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}
	

}