package barco;

import vista.*;


public class JugadorHumano extends Jugador {

	private boolean aceptaInput;

	private int filaSelec;
	private int colSelec;
	private boolean matrizJ1Selec;


	
	public JugadorHumano () {

		
	}
	


	public void colocarBarcos() {
	// CREAR UNA TUPLA DE LOS 3 ELEMENTOS O MANTERNELOS COMO VARIABLES DE LA CLASE???

		
		int barcosRestantes = 10;
		int[] bConRes = new int[4]; // barcos concretos restantes (de 4,3,2 y 1)
		int tamSelec; // tamaño del barco que se quiere poner
		int seleccionesHechas;
		boolean valido;
		boolean selecHorizontal=false;
		Posicion p1 = null;
		Posicion p2 = null;
		Posicion p3 = null;
		Posicion p4 = null;

		Posicion aux;
		
		for (int i = 0; i != 4; i++) {
			bConRes[i] = 4-i;
		}


		
		while (barcosRestantes != 0) {
			seleccionesHechas = 0;
			
			
			while (seleccionesHechas != 2) {
				valido = false;
				while (!valido) {
					// MSG MARCA POS.

					this.esperarInput();
					valido = this.matrizJ1Selec;
					
					if (!valido) {
						// MSG DE ERROR DICIENDO QUE NO ES ESA LA TUYA
					} else {
						if (seleccionesHechas == 0) {

							p1 = new Posicion(this.filaSelec, this.colSelec);

							System.out.println("Debug: Primera casilla seleccionada");

						} else {
							
							p2 = new Posicion(this.filaSelec, this.colSelec);
				

							System.out.println("Debug: Segunda casilla seleccionada");

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
						
						if (selecHorizontal) { // BARCO EN HORINZOTAL
		
							p3 = new Posicion(Math.max(0, p1.getFila()-1),Math.max(0, p1.getCol()-1));
									
		
								
							p4 = new Posicion( Math.min(9, p1.getFila()+1),Math.min(9, p2.getCol()+1));
				
							
						} else { // BARCO EN VERTICAL

							p3 = new Posicion(Math.max(0, p1.getFila()-1), Math.max(0, p1.getCol()-1) );
							
							p4 = new Posicion( Math.min(9, p2.getFila()+1), Math.min(9, p2.getCol()+1) );
		
				
							
						}
						
						
						// CALCULAR
						valido = super.posibleColocar(p1, p2, selecHorizontal);
						


						
						if (valido) {

							super.generarBarco(p1, p2, p3, p4, tamSelec, selecHorizontal);

							bConRes[tamSelec]--;
							barcosRestantes--;
							
							super.actualizarCambios();


						} else {
							
							// MSG DE ERROR, EL BARCO SOLAPA CON OTRO (O ES ADYECENTE A OTRO)
						}
						
						
					} else {
						// MSG DE ERROR, NO QUEDAN BARCOS DE ESE TIPO
					}
				} else {
					
					if (tamSelec == -1) {
						// MSG DE ERROR, BARCO INTENTANDO PONER EN DIAGONAL

					} else {
						// MSG DE ERROR, LONGITUD MAS DE 4
						
					}
					
				}
				
			
				
				

			

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
