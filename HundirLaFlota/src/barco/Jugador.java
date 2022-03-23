package barco;

import java.awt.Color;
import java.util.Observable;

public abstract class Jugador extends Observable {

	
	private CasillaDeJuego[][] matriz;
	private boolean[][] cambiosEnMatriz;
	
	public Jugador () {
		
		this.matriz = new CasillaDeJuego[10][10];
		this.cambiosEnMatriz = new boolean[10][10];
	}
	
	public void actualizarCambios() {
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public Color calcularColor(int pF, int pC) {
		boolean valor = this.cambiosEnMatriz[pF][pC];
		
		if (valor) {
			this.cambiosEnMatriz[pF][pC] = false;
			return this.matriz[pF][pC].obtenerColorActualizado();
		} else {
			return null;
		}
	}	
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
				cambiosEnMatriz[cte][min] = true; // SE DEBE ACTUALIZAR EL COLOR DESPUES
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
				cambiosEnMatriz[min][cte] = true; // SE DEBE ACTUALIZAR EL COLOR DESPUES
				cont++;
				min++;
			}
			

		}
		
		
		for (int f = p3.getFila(); f <= p4.getFila(); f++) {
			for (int c = p3.getCol(); c <= p4.getCol(); c++) {
				
				if (matriz[f][c] == null) {
					matriz[f][c] = new CasillaDeJuego(null); // No barco asignado a casilla
					cambiosEnMatriz[f][c] = true; // SE DEBE ACTUALIZAR EL COLOR DESPUES

				}
			}
		}
	}
	
	
	protected void completarTablero() {
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (this.matriz[i][j] == null) {
					this.cambiosEnMatriz[i][j] = true;
					this.matriz[i][j] = new CasillaDeJuego(null);
				}
			}
		}
		
	}
	
	public CasillaDeJuego[][] getMatriz() {return this.matriz;}
	
	public boolean[][] getMatrizCambios() {return this.cambiosEnMatriz;}
	
	
	
}
