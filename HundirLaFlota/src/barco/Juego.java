package barco;

import java.awt.Color;
import java.util.Observable;

public class Juego extends Observable {

	
	
	private static Juego juego;
	private int filaSelec;
	private int colSelec;
	private boolean matrizJugadorSeleccionado;
	private boolean aceptaInput;
	
	
	
	private CasillaDeJuego[][] matrizJugador;
	private CasillaDeJuego[][] matrizMaquina;
	
	private boolean[][] cambiosEnMatrizJugador;
	private boolean[][] cambiosENMatrizIA;
	
	
	private Juego() {
		

		this.aceptaInput = false;
		this.matrizJugador = new CasillaDeJuego[10][10];
		this.matrizMaquina = new CasillaDeJuego[10][10];
		this.cambiosEnMatrizJugador = new boolean[10][10];
		this.cambiosENMatrizIA = new boolean[10][10];

		
	}
	
	
	
	
	public static Juego getJuego() {
		if (Juego.juego == null) {Juego.juego = new Juego();}
		return Juego.juego;
	}
	
	public void cogerInput(CasillaDePantalla pCas) {
		
		if (this.aceptaInput) {
			this.aceptaInput = false;
			this.filaSelec = pCas.getFila();
			this.colSelec = pCas.getCol();
			this.matrizJugadorSeleccionado = pCas.esDeJ1();
			
			
		}
		
	//	System.out.println("La fila es: " +pF+" \nLa columna es: "+pC+"\n La matriz es del jugador: " +pJ);
	}
	
	
	private void actualizarCambios() {
		super.setChanged();
		super.notifyObservers();
		
	}
	
	public Color calcularColor(int pF, int pC, boolean pJug) {
		
		boolean valor;
		
		if (pJug) {

			valor = this.cambiosEnMatrizJugador[pF][pC];
			this.cambiosEnMatrizJugador[pF][pC] = false;
			
			if (valor) {
				return this.matrizJugador[pF][pC].obtenerColorActualizado();
			} else {
				return null;
			}
			
		} else {

			valor = this.cambiosENMatrizIA[pF][pC];
			this.cambiosENMatrizIA[pF][pC] = false;
			
			if (valor) {
				return this.matrizMaquina[pF][pC].obtenerColorActualizado();
			} else {
				return null;
			}
		}
		

		
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
	
	
	private boolean posibleColocar (Posicion p1, Posicion p2, boolean horizontal) {
		
		// ¿ SE PUEDE CON JAVA8?
		
		
		boolean puede = true;
		int cte;
		int min;
		int max;
		
		if (horizontal) {
			cte = p1.fila;
			min = p1.columna;
			max = p2.columna;
			
			while (min <= max && puede) {
				puede = this.matrizJugador[cte][min] == null;
				min++;
			}
			
			
		} else {
			cte = p1.columna;
			min = p1.fila;
			max = p2.fila;
				
	
			
			while (min <= max && puede) {
				puede = this.matrizJugador[min][cte] == null;
				min++;
			}

		}
		
		return puede;
		
	}
	
	private void generarBarco(Posicion p1, Posicion p2, Posicion p3, Posicion p4, int tam, boolean horizontal) {
		
		// PARA HACER EL UPDATE EN LAS CASILLAS LABEL DESPUES IGUAL CREAR UN ARRAY DE BOOLEAN
		// INDICANDO QUE CASILLA SE TOCO DESDE LA ULTIMA VEZ Y CUAL NO. EN ESE CASO HABRIA Q
		// CAMBIAR ESE BOOLEAN AQUI.
		
		
		Barco b = FabricaBarcos.getFab().generarBarco(tam);
		ParteBarco p;
		int min;
		int max;
		int cte;
	
		int cont = 0;
		
		if (horizontal) {
			min = p1.columna;
			max = p2.columna;
			cte = p1.fila;
			
			while (min <= max) {
				p = new ParteBarco(cont, b);
				this.matrizJugador[cte][min] = new CasillaDeJuego(p);
				this.cambiosEnMatrizJugador[cte][min] = true; // SE DEBE ACTUALIZAR EL COLOR DESPUES
				cont++;
				min++;
			}
			
			
			
			
		} else {
			min = p1.fila;
			max = p2.fila;
			cte = p1.columna;
			
			while (min <= max) {
				p = new ParteBarco(cont, b);
				this.matrizJugador[min][cte] = new CasillaDeJuego(p);
				this.cambiosEnMatrizJugador[min][cte] = true; // SE DEBE ACTUALIZAR EL COLOR DESPUES
				cont++;
				min++;
			}
			

		}
		
		
		for (int f = p3.fila; f <= p4.fila; f++) {
			for (int c = p3.columna; c <= p4.columna; c++) {
				
				if (this.matrizJugador[f][c] == null) {
					this.matrizJugador[f][c] = new CasillaDeJuego(null); // No barco asignado a casilla
					this.cambiosEnMatrizJugador[f][c] = true; // SE DEBE ACTUALIZAR EL COLOR DESPUES

				}
			}
		}
		
		
	}
	
	public void colocarBarcos() {
		
		// CREAR UNA TUPLA DE LOS 3 ELEMENTOS O MANTERNELOS COMO VARIABLES DE LA CLASE???

		
		int barcosRestantes = 10;
		int[] bConRes = new int[4]; // barcos concretos restantes (de 4,3,2 y 1)
		int tamSelec; // tamaño del barco que se quiere poner
		int seleccionesHechas;
		boolean valido;
		boolean selecHorizontal=false;
		Posicion p1 = new Posicion();
		Posicion p2 = new Posicion();
		Posicion p3 = new Posicion();
		Posicion p4 = new Posicion();

		int aux;
		
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
					valido = this.matrizJugadorSeleccionado;
					
					if (!valido) {
						// MSG DE ERROR DICIENDO QUE NO ES ESA LA TUYA
					} else {
						if (seleccionesHechas == 0) {
							p1.fila = this.filaSelec;
							p1.columna = this.colSelec;

							System.out.println("Debug: Primera casilla seleccionada");

						} else {
							p2.fila = this.filaSelec;
							p2.columna = this.colSelec;

							System.out.println("Debug: Segunda casilla seleccionada");

						}
						

						seleccionesHechas++;
					}
				}
			}
			
				tamSelec = -1;
				
				if (p1.fila == p2.fila) {
					
					if (p1.columna > p2.columna) {
						tamSelec = p1.columna-p2.columna;
						aux = p2.columna;
						p2.columna = p1.columna;
						p1.columna = aux;
					} else {
						tamSelec = p2.columna-p1.columna;
						
					}
					
					selecHorizontal = true;
					
				} else if (p1.columna == p2.columna) {
					
					if (p1.fila > p2.fila) {
						tamSelec = p1.fila-p2.fila;
						aux = p2.fila;
						p2.fila = p1.fila;
						p1.fila = aux;
					} else {
						tamSelec = p2.fila-p1.fila;
						
					}
					
					selecHorizontal = false;

				}
				
				if (tamSelec >= 0 && tamSelec <=3) {
					if (bConRes[tamSelec] != 0) {
						
						if (selecHorizontal) { // BARCO EN HORINZOTAL
							p3.fila = Math.max(0, p1.fila-1);
							p4.fila = Math.min(9, p1.fila+1);
							
							p3.columna = Math.max(0, p1.columna-1);
							p4.columna = Math.min(9, p2.columna+1);
								
				
							
						} else { // BARCO EN VERTICAL
							p3.columna = Math.max(0, p1.columna-1);
							p4.columna = Math.min(9, p2.columna+1);
							
							p3.fila = Math.max(0, p1.fila-1);
							p4.fila = Math.min(9, p2.fila+1);
				
							
						}
						
						
						// CALCULAR
						
						
						valido = this.posibleColocar(p1, p2, selecHorizontal);


						
						if (valido) {

							this.generarBarco(p1,p2,p3,p4, tamSelec, selecHorizontal);

							bConRes[tamSelec]--;
							barcosRestantes--;
							
							this.actualizarCambios();


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
		
		
		
 		
	
		
		
	}
	
	private class Posicion {
		int fila;
		int columna;
	}


	
	
}
