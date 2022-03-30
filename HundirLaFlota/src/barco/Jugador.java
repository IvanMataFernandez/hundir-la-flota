package barco;

import java.awt.Color;
import java.util.Observable;

public abstract class Jugador extends Observable {

	private boolean esJ1;
	private CasillaDeJuego[][] matriz;
	private Color[][] cambiosEnMatriz;
	private int barcosConVida;
	
	public Jugador (boolean pJ1) {
		this.esJ1 = pJ1;
		this.matriz = new CasillaDeJuego[10][10];
		this.cambiosEnMatriz = new Color[10][10];
		this.barcosConVida = 10;
	}
	
	
	public abstract void disparar() throws ExcepcionFinDePartida;
	
	public void actualizarCambios() {
		
		this.setChanged();
		this.notifyObservers(this.cambiosEnMatriz);
		
		this.cambiosEnMatriz = new Color[10][10];
	}
	
/*	public Color calcularColor(int pF, int pC) {
		
		if (this.cambiosEnMatriz[pF][pC]) {
			this.cambiosEnMatriz[pF][pC] = false;
			return this.matriz[pF][pC].obtenerColorActualizado();
		} else {
			return null;
		}
	}	*/
	public abstract void colocarBarcos();
	
	protected boolean posibleColocar(Posicion p1, Posicion p2, boolean pHor) {

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
	
	
	protected void generarBarco(Posicion p1, Posicion p2, Posicion p3, Posicion p4, int tam, boolean pHor) {
		Barco b = FabricaBarcos.getFab().generarBarco(tam);
		ParteBarco p;
		int min;
		int max;
		int cte;
	
		int cont = 0;
		

		
		if (pHor) {
			min = p1.getCol();
			max = p2.getCol();
			cte = p1.getFila();
			
			while (min <= max) {
				p = new ParteBarco(cont, b);
				matriz[cte][min] = new CasillaDeJuego(p);
				cambiosEnMatriz[cte][min] = matriz[cte][min].obtenerColorActualizado(); // SE DEBE ACTUALIZAR EL COLOR DESPUES
				cont++;
				min++;
			}
			
			
			
			
		} else {
			min = p1.getFila();
			max = p2.getFila();
			cte = p1.getCol();
			
			while (min <= max) {
				p = new ParteBarco(cont, b);
				matriz[min][cte] = new CasillaDeJuego(p);
				cambiosEnMatriz[min][cte] = matriz[min][cte].obtenerColorActualizado();; // SE DEBE ACTUALIZAR EL COLOR DESPUES
				cont++;
				min++;
			}
			

		}
		
		
		for (int f = p3.getFila(); f <= p4.getFila(); f++) {
			for (int c = p3.getCol(); c <= p4.getCol(); c++) {
				
				if (matriz[f][c] == null) {
					matriz[f][c] = new CasillaDeJuego(null); // No barco asignado a casilla
					cambiosEnMatriz[f][c] = matriz[f][c].obtenerColorActualizado(); // SE DEBE ACTUALIZAR EL COLOR DESPUES

				}
			}
		}
	}
	
	
	protected void completarTablero() {
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (this.matriz[i][j] == null) {
					this.matriz[i][j] = new CasillaDeJuego(null);
					this.cambiosEnMatriz[i][j] = matriz[i][j].obtenerColorActualizado();;

				}
			}
		}
		
	}
	
	public boolean haDisparadoAhi(int pF, int pC) {return this.matriz[pF][pC].disparado();}
	
	
	public boolean[] dispararEn(int pF, int pC) {
		// AÑADIR BOOLEAN DESPUES PARA MISIL O BOMBA
		boolean[] res = this.matriz[pF][pC].disparar();
		this.cambiosEnMatriz[pF][pC] = this.matriz[pF][pC].obtenerColorActualizado();
		return res;
		
		
	}
	
	public void hundeUnBarco() {this.barcosConVida--;}
	
	public void acabaLaPartida() throws ExcepcionFinDePartida {
		
		if (this.barcosConVida == 0) {
			throw new ExcepcionFinDePartida(!this.esJ1);
		}
		
	}
	
//	public CasillaDeJuego[][] getMatriz() {return this.matriz;}
	
//	public boolean[][] getMatrizCambios() {return this.cambiosEnMatriz;}
	
	
	
}
