package barco;

public class Juego {

	
	
	private static Juego juego;
	private int filaSelec;
	private int colSelec;
	private boolean matrizJugadorSeleccionado;
	
	private boolean aceptaInput;
	
	
	private CasillaDeJuego[][] matrizJugador;
	private CasillaDeJuego[][] matrizMaquina;
	
	
	private Juego() {
		
		
		this.aceptaInput = false;
		this.matrizJugador = new CasillaDeJuego[10][10];
		this.matrizMaquina = new CasillaDeJuego[10][10];
	}
	
	
	public static Juego getJuego() {
		if (Juego.juego == null) {Juego.juego = new Juego();}
		return Juego.juego;
	}
	
	public void cogerInput(int pF, int pC, boolean pJ) {
		
		if (this.aceptaInput) {
			this.aceptaInput = false;
			this.filaSelec = pF;
			this.colSelec = pC;
			this.matrizJugadorSeleccionado = pJ;
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
	
	
	private boolean posibleColocar (int f1, int c1, int f2, int c2, boolean horizontal) {
		
		// ¿ SE PUEDE CON JAVA8?
		
		
		boolean puede = true;
		int cte;
		int min;
		int max;
		
		if (horizontal) {
			cte = f1;
			min = c1;
			max = c2;
			
			while (min <= max && puede) {
				puede = this.matrizJugador[cte][min] == null;
				min++;
			}
			
			
		} else {
			cte = c1;
			min = f1;
			max = f2;
				
	
			
			while (min <= max && puede) {
				puede = this.matrizJugador[min][cte] == null;
				min++;
			}

		}
		
		return puede;
		
	}
	
	private void generarBarco(int f1, int c1, int f2, int c2, int f3, int c3, int f4, int c4, int tam, boolean horizontal) {
		
		// PARA HACER EL UPDATE EN LAS CASILLAS LABEL DESPUES IGUAL CREAR UN ARRAY DE BOOLEAN
		// INDICANDO QUE CASILLA SE TOCO DESDE LA ULTIMA VEZ Y CUAL NO. EN ESE CASO HABRIA Q
		// CAMBIAR ESE BOOLEAN AQUI.
		
		
		Barco b = new Barco(tam+1);
		ParteBarco p;
		int min;
		int max;
		int cte;
	
		int cont = 0;
		
		if (horizontal) {
			min = c1;
			max = c2;
			cte = f1;
			
			while (min <= max) {
				p = new ParteBarco(cont, b);
				this.matrizJugador[cte][min] = new CasillaDeJuego(p);
				cont++;
			}
			
			
			
			
		} else {
			min = f1;
			max = f2;
			cte = c1;
			
			while (min <= max) {
				p = new ParteBarco(cont, b);
				this.matrizJugador[min][cte] = new CasillaDeJuego(p);
				cont++;
			}
			

		}
		
		
		for (int f = f3; f <= f4; f++) {
			for (int c = c3; c <= c4; c++) {
				
				if (this.matrizJugador[f][c] == null) {
					this.matrizJugador[f][c] = new CasillaDeJuego(null); // No barco asignado a casilla
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
		int f1=-1;
		int f2=-1;
		int c1=-1;
		int c2=-1;
		int f3=-1; // PARA LAS ESQUINAS MAS ALEJADAS DE LOS BARCOS
		int f4=-1;
		int c3=-1;
		int c4=-1;
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
							f1 = this.filaSelec;
							c1 = this.colSelec;								
						} else {
							f2 = this.filaSelec;
							c2 = this.colSelec;	
						}
						seleccionesHechas++;
					}
				}
			
				tamSelec = -1;
				
				if (f1 == f2) {
					
					if (c1 > c2) {
						tamSelec = c1-c2;
						aux = c2;
						c2 = c1;
						c1 = aux;
					} else {
						tamSelec = c2-c1;
						
					}
					
					selecHorizontal = true;
					
				} else if (c1 == c2) {
					
					if (f1 > f2) {
						tamSelec = f1-f2;
						aux = f2;
						f2 = f1;
						f1 = aux;
					} else {
						tamSelec = f2-f1;
						
					}
					
					selecHorizontal = false;

				}
				
				if (tamSelec >= 0 && tamSelec <=3) {
					if (bConRes[tamSelec] != 0) {
						
						if (selecHorizontal) { // BARCO EN HORINZOTAL
							f3 = Math.max(0, f1-1);
							f4 = Math.min(9, f1+1);
							
							c3 = Math.max(0, c1-1);
							c4 = Math.min(9, c2+1);
								
				
							
						} else { // BARCO EN VERTICAL
							c3 = Math.max(0, c1-1);
							c4 = Math.min(9, c2+1);
							
							f3 = Math.max(0, f1-1);
							f4 = Math.min(9, f2+1);
				
							
						}
						
						
						// CALCULAR
						
						valido = this.posibleColocar(f1, c1, f2, c2, selecHorizontal);
						
						if (valido) {
							this.generarBarco(f1, c1, f2, c2, f3, c3, f4, c4, tamSelec, selecHorizontal);
							bConRes[tamSelec]--;
							barcosRestantes--;
							
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
		
		
		
 		
		
		
		
	}
	
	
}
