package barco;

import java.awt.Color;
import java.util.Observable;

public abstract class Jugador extends Observable {

	private boolean esJ1;
	private CasillaDeJuego[][] matriz;
	private Color[][] cambiosEnMatriz;
	private int barcosConVida;
	private Armamento equipadoCon;
	private Radar radar;
	private int numEscudos;
	private int numMisiles;
	private int dinero;
	
	public Jugador (boolean pJ1) {
		this.esJ1 = pJ1;
		this.matriz = new CasillaDeJuego[10][10];
		this.cambiosEnMatriz = new Color[10][10];
		this.barcosConVida = 10;
		this.numEscudos = 5;
		this.numMisiles = 3;
		this.equipadoCon = FabricaArmamento.getFabricaArmamento().generarArmamento(1);
		this.radar = (Radar) FabricaArmamento.getFabricaArmamento().generarArmamento(0);
		this.dinero = 5000; // LUEGO SE PUEDE CAMBIAR
	}
	
/*	protected Armamento obtArmamentoAct() {
		return this.equipadoCon;
	} */
	
	
	protected boolean getValorEscudoRadar() {
		return this.radar.getValorEscudo();
	}
	
	
	protected void cambiarABomba() {
		if (this.equipadoCon instanceof Misil) {
			this.equipadoCon = FabricaArmamento.getFabricaArmamento().generarArmamento(1);		
		}
		
	}
	
	protected void cambiarArmamento() {
		
		if (this.equipadoCon instanceof Bomba) {
			this.equipadoCon = FabricaArmamento.getFabricaArmamento().generarArmamento(2);
		} else {
			this.equipadoCon = FabricaArmamento.getFabricaArmamento().generarArmamento(1);		
		}
		
	}
	
	protected int obtMisiles() {return this.numMisiles;}
	
	protected boolean tieneMisiles() {return this.numMisiles != 0;}
	
	protected boolean ponerEscudoEn(Posicion pos) {
		boolean res;
		FabricaArmamento fab = FabricaArmamento.getFabricaArmamento();
		Escudo esc = (Escudo) fab.generarArmamento(3);
		res = esc.usar(matriz, cambiosEnMatriz, pos)[0];
		
		if (res) {
			this.numEscudos--;
		}
		
		return res;
		
	}
	
	protected int numEscudos() {return this.numEscudos;}
	
	protected boolean tieneEscudos() {return this.numEscudos != 0;}
	
	protected boolean tieneRadar() {return this.radar.usosRestantes() != 0;}
	
	protected Posicion getDeteccionRadar() {return this.radar.getDeteccion();}
	
	
	protected int usosRadarActual() {return this.radar.usosRestantes();}
	
	protected Posicion moverRadar() {this.radar.recolocarRadar(this.matriz); return this.radarEn();}
	
	protected Posicion radarEn() {return this.radar.getPosicion();}
	
	protected boolean usarRadarEnRival() {
		
		boolean res;
		
		if (this.esJ1) {
			res = Jugadores.getJugadores().getJugadorIA().leUsanRadar(this.radar);
			
		} else {
			res = Jugadores.getJugadores().getJugadorHumano().leUsanRadar(this.radar);

		}
		

		
		return res;
		
	}
	
	public boolean leUsanRadar(Radar pRad) {
		return pRad.usar(matriz, cambiosEnMatriz, null)[0];
		
	}
	
	public abstract void procesarAcciones() throws ExcepcionFinDePartida;

	public abstract void colocarBarcos();

	public abstract void procesarCompras();
	
	
	protected int getDinero() {return this.dinero;}
	
	public void actualizarCambios() {
		
		this.setChanged();
		this.notifyObservers(this.cambiosEnMatriz);
		
		this.cambiosEnMatriz = new Color[10][10];
	}
	

	
	
	

	
	
	protected boolean posibleColocar(Posicion p1, Posicion p2, boolean pHor) {

		
		/* Pre: Las dos posiciones que marcan las esquinas del barco, así como si está en horizontal o no
		 
		   Post: Si el barco es posible colocarlo ahí en acorde con las normas establecidas en el enunciado del proyecto.
	       	  
	       	     Nota: Un barco se puede colocar si no hay casillas generadas en el espacio que ocupa el barco.
		  
		 */
		
		
		
		
		boolean puede = true;
		int cte;
		int min;
		int max;
		
		
		
		if (pHor) {
			cte = p1.getFila();
			min = p1.getCol();
			max = p2.getCol();
			
			while (min <= max && puede) {
				puede = matriz[cte][min] == null;
				min++;
			}
			
			
		} else {
			cte = p1.getCol();
			min = p1.getFila();
			max = p2.getFila();
				
	
			
			while (min <= max && puede) {
				puede = matriz[min][cte] == null;
				min++;
			}

		}
		
		
		return puede;
		
	}
	

	
	protected void generarBarco(Posicion p1, Posicion p2, int tam, boolean pHor) {
		
		
		/* Pre: Las dos posiciones que marcan las esquinas del barco, así como si está en horizontal o no y su tamaño
		        El barco PUEDE ser colocado entre las posiciones p1 y p2.
		 
		   Post: El barco se pone en la matriz (generando casillas con barco en el camino directo y agua a los lados de el
		         si no había). Se marcan los colores que se deben actualizar en la interfaz gráfica después.
		  
		 */
	
		
		
		Barco b = FabricaBarcos.getFab().generarBarco(tam);
		ParteBarco p;
		int min;
		int max;
		int cte;
		Posicion p3 = null;
		Posicion p4 = null;

		Color colorBarco;
		int cont = 0;
		
		if (this.esJ1) {
			colorBarco = FabricaColores.getFabricaColores().generarColores("BarcoColocado");
		} else {
			colorBarco = FabricaColores.getFabricaColores().generarColores("AguaBase");
		}

		
	
		// CALCULAR EXTREMOS PARA AGUA
		
		if (pHor) { // BARCO EN HORINZOTAL
			
			p3 = new Posicion(Math.max(0, p1.getFila()-1),Math.max(0, p1.getCol()-1));
					

				
			p4 = new Posicion( Math.min(9, p1.getFila()+1),Math.min(9, p2.getCol()+1));

			
		} else { // BARCO EN VERTICAL

			p3 = new Posicion(Math.max(0, p1.getFila()-1), Math.max(0, p1.getCol()-1) );
			
			p4 = new Posicion( Math.min(9, p2.getFila()+1), Math.min(9, p2.getCol()+1) );

	
		}
		
	

		// GENERAR EL BARCO

		
		if (pHor) {
			min = p1.getCol();
			max = p2.getCol();
			cte = p1.getFila();
			
			while (min <= max) {
				matriz[cte][min] = new CasillaDeJuego(this.esJ1, new Posicion(cte,min));
				p = new ParteBarco(cont, b, matriz[cte][min]);
				b.addParteBarco(p);
				this.cambiosEnMatriz[cte][min] = colorBarco;
				cont++;
				min++;
			}
			
			
			
			
		} else {
			min = p1.getFila();
			max = p2.getFila();
			cte = p1.getCol();
			
			while (min <= max) {
				matriz[min][cte] = new CasillaDeJuego(this.esJ1, new Posicion(min,cte));
				p = new ParteBarco(cont, b, matriz[min][cte]);
				b.addParteBarco(p);
				this.cambiosEnMatriz[min][cte] = colorBarco;
				cont++;
				min++;
			}
			

		}
		
		// GENERAR EL AGUA QUE RODEA AL BARCO
		
		
		for (int f = p3.getFila(); f <= p4.getFila(); f++) {
			for (int c = p3.getCol(); c <= p4.getCol(); c++) {
				
				if (this.matriz[f][c] == null) {
					this.matriz[f][c] = new CasillaDeJuego(this.esJ1, new Posicion(f,c)); // No barco asignado a casilla
					this.cambiosEnMatriz[f][c] = FabricaColores.getFabricaColores().generarColores("AguaBase");


				}
			}
		}
		

		
	}
	
	
	protected void completarTablero() {
		
		// LLenar el resto del tablero que no tienen casillas colocadas como agua.
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (this.matriz[i][j] == null) {
					this.matriz[i][j] = new CasillaDeJuego(this.esJ1, new Posicion(i,j));
					this.cambiosEnMatriz[i][j] = FabricaColores.getFabricaColores().generarColores("AguaBase");

				}
			}
		}
		
	}
	
	public boolean haDisparadoAhi(int pF, int pC) {return this.matriz[pF][pC].disparado();}
	
	
	
	public boolean[] dispararAlOtro(Posicion pPos) {
		
		boolean[] res;
		
		if (this.esJ1) {
			res = Jugadores.getJugadores().getJugadorIA().dispararEn(pPos, this.equipadoCon);
		} else {
			res = Jugadores.getJugadores().getJugadorHumano().dispararEn(pPos, this.equipadoCon);
			
		}
		
		if (this.equipadoCon instanceof Misil) {
			this.numMisiles--;
		}
		
		return res;
		
	}
	
	public boolean[] dispararEn(Posicion pPos, Armamento pTiro) {

		

		
		return pTiro.usar(this.matriz, this.cambiosEnMatriz, pPos);
		
	
		
		
	}
	
	public void hundeUnBarco() throws ExcepcionFinDePartida {
		this.barcosConVida--;
		
	
		if (this.barcosConVida == 0) {
			this.actualizarCambios();
			TextoYAudio.getInstancia().actualizarCambios();
			throw new ExcepcionFinDePartida(!this.esJ1);
		}
	
	}
	

	

	
	/*	public Color calcularColor(int pF, int pC) {
	
	if (this.cambiosEnMatriz[pF][pC]) {
		this.cambiosEnMatriz[pF][pC] = false;
		return this.matriz[pF][pC].obtenerColorActualizado();
	} else {
		return null;
	}
	
	public CasillaDeJuego[][] getMatriz() {return this.matriz;}
	
	public boolean[][] getMatrizCambios() {return this.cambiosEnMatriz;}
	
	
}	*/
	
}