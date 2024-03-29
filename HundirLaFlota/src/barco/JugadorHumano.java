package barco;

import vista.*;


public class JugadorHumano extends Jugador {




	
	public JugadorHumano () {
		super(true);
		
	}
	
	
	
	public void disparar() throws ExcepcionFinDePartida {
		boolean valido = false;
		boolean res[];
		int filaSelec;
		int colSelec;
		JugadorIA jIA = Jugadores.getJugadores().getJugadorIA();
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		GestorInputs inputs = GestorInputs.getGestor();
		textoYAudio.setTexto("Elige donde disparar");
		while (!valido) {
			textoYAudio.actualizarCambios();
			inputs.esperarInputDeCasilla();
			valido = !inputs.getMatrizJ1Selec();
			filaSelec = inputs.getFila();
			colSelec = inputs.getCol();
			
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

							textoYAudio.setTexto("Disparas en:  "+(filaSelec+1)+" "+(char)(65+colSelec)+ ". Barco hundido");
							textoYAudio.setAudio("hundido");
							
							jIA.acabaLaPartida(); // Lanza excepcion si se quedo sin barcos
							
						} else { // ESCUDO
						
							textoYAudio.setTexto("Disparas en:  "+(filaSelec+1)+" "+(char)(65+colSelec)+ ". Bloqueado por escudo");
						//	textoYAudio.setAudio("bloqueado por escudo");
							
						}
						
					} else {
						
						if (res[1]) { // TOCADO
					
							textoYAudio.setTexto("Disparas en:  "+(filaSelec+1)+" "+(char)(65+colSelec)+ ". Barco tocado");
							textoYAudio.setAudio("tocado");
							
						} else { // AGUA
							
							textoYAudio.setTexto("Disparas en:  "+(filaSelec+1)+" "+(char)(65+colSelec)+ ". Agua");
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
		GestorInputs inputs = GestorInputs.getGestor();
		int barcosRestantes = 10;
		int[] bConRes = new int[4]; // barcos concretos restantes (de 4,3,2 y 1)
		int tamSelec; // tama�o del barco que se quiere poner
		int seleccionesHechas;
		int filaSelec;
		int colSelec;
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

					inputs.esperarInputDeCasilla();
					valido = inputs.getMatrizJ1Selec();
					filaSelec = inputs.getFila();
					colSelec = inputs.getCol();
					
					if (!valido) {
						textoYAudio.setTexto("Has pinchado en el tablero del rival");
						textoYAudio.setAudio("error");

					} else {
						if (seleccionesHechas == 0) {

							p1 = new Posicion(filaSelec, colSelec);
							textoYAudio.setTexto("Pinchado en: "+(filaSelec+1)+" "+(char)(65+colSelec));


						} else {
							
							p2 = new Posicion(filaSelec, colSelec);
				


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
							
							textoYAudio.setTexto("Quedan: Portaaviones: "+bConRes[3]+" | Submarinos: "+bConRes[2]+" | Destructores: "+bConRes[1]+" | Fragatas:  "+bConRes[0]);							
							textoYAudio.setAudio("construccion");
							
							super.actualizarCambios();


						} else {
							textoYAudio.setTexto("Barco demasiado pr�ximo a otro. No se ha colocado.");
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
						textoYAudio.setTexto("Barcos de hasta 4 casillas de longitud, no m�s.");
						textoYAudio.setAudio("error");

						
					}
					
				}
				
			
				
				textoYAudio.actualizarCambios();
			

		}
		
		
		
		super.completarTablero();
		super.actualizarCambios();
	
		
		
	}
	
	

	

}