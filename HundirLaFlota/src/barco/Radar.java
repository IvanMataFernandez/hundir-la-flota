package barco;

import java.awt.Color;
import java.util.Random;

public class Radar implements Armamento {
	
	
	private Random aleatorio;
	private Posicion pos;
	private int usos;
	
	public Radar () {
		
		this.aleatorio = new Random();
		this.pos = new Posicion(this.aleatorio.nextInt(10), this.aleatorio.nextInt(10));
		this.usos = 3; // 3 usos al inicio
	}
	
	public boolean[] usar (CasillaDeJuego[][] pMatrizA, Color[][] pMatrizB, Posicion pPos) {
		
		
		
		// Post:  Boolean 0 -> ENC    Boolean 1 --> Se rompe tras usarlo
		
		int f = Math.max(this.pos.getFila() - 1, 0);
		int colInic = Math.max(this.pos.getCol() - 1, 0);
		int colFin = Math.min(this.pos.getCol() + 1, 9);
		int filFin = Math.min(this.pos.getFila() + 1, 9);
		int c;
		boolean[] enc = new boolean[2];
		enc[0] = false;
	
		
		while (!enc[0] && f <= filFin) {
			c = colInic;
			
			while (!enc[0] && c <= colFin) {
				
				if (!pMatrizA[f][c].visible() && pMatrizA[f][c].hayBarco()) {
					pMatrizA[f][c].marcarVisible();
					enc[0] = true;
					pMatrizB[f][c] = pMatrizA[f][c].calcularColorSinTocar();
				}
				
				c++;
				
			}
			
			f++;
		}
		
		this.usos--;
		
		enc[1] = this.usos == 0;
		
		
	
		
		return enc;
		
		
	}
	
	public void recolocarRadar(CasillaDeJuego[][] pMatriz) {
		this.pos = new Posicion(this.aleatorio.nextInt(10), this.aleatorio.nextInt(10));		
	}
	
	public Posicion getPosicion() {return this.pos;}

}