package barco;

import java.awt.Color;
import java.util.Random;

public class Radar implements Armamento {
	
	
	private Random aleatorio;
	private Posicion pos;
	private int usos;
	private Posicion deteccion;
	
	public Radar () {
		
		this.aleatorio = new Random();
		this.pos = new Posicion(this.aleatorio.nextInt(10), this.aleatorio.nextInt(10));
		this.usos = 3; // 3 usos al inicio

	}
	
	
	public Posicion getDeteccion() {return this.deteccion;}
	
	public int usosRestantes() {return this.usos;}
	
	public void recuperarUsos() {this.usos = 3;}
	
	public boolean[] usar (CasillaDeJuego[][] pMatrizA, Color[][] pMatrizB, Posicion pPos) {
		
		
		// Pre: pPos es irrelevante, incluido por estar definido como método abstracto en clase madre
		// Post:  True <--> ENC
		
		

		int filInic = Math.max(this.pos.getFila() - 1, 0);
		int f = filInic;
		int colInic = Math.max(this.pos.getCol() - 1, 0);
		int colFin = Math.min(this.pos.getCol() + 1, 9);
		int filFin = Math.min(this.pos.getFila() + 1, 9);
		int c = 0;
		boolean[] enc = new boolean[1];
		TextoYAudio.getInstancia().setAudio("radar");
		
		while (!enc[0] && f <= filFin) {
			c = colInic;
			
			while (!enc[0] && c <= colFin) {
				
				if (!pMatrizA[f][c].seVio() && pMatrizA[f][c].hayBarco()) {
					pMatrizA[f][c].marcarVisible();
					enc[0] = true;
					pMatrizB[f][c] = pMatrizA[f][c].calcularColorSinTocar();
				}
				
				c++;
				
			}
			
			f++;
		}
		
		this.usos--;
		
		
		if (enc[0]) {
			this.deteccion = new Posicion(f-1, c-1);
		} else {
			this.revelearAguas(pMatrizA, pMatrizB, filInic, filFin, colInic, colFin);
			this.deteccion = null;
		}
		
		
	
		
		return enc;
		
		
	}
	
	public void recolocarRadar(CasillaDeJuego[][] pMatriz) {
		this.pos = new Posicion(this.aleatorio.nextInt(10), this.aleatorio.nextInt(10));		
	}
	
	public Posicion getPosicion() {return this.pos;}
	
	
	private void revelearAguas(CasillaDeJuego[][] pMatrizA, Color[][] pMatrizB, int pFilaInic, int pFilaFin, int pColInic, int pColFin) {
		
		
		int f = pFilaInic;
		int c;
		
		while (f <= pFilaFin) {
			c = pColInic;
			
			while (c <= pColFin) {
				CasillaDeJuego cas = pMatrizA[f][c];
				if (!cas.seVio()) {
					cas.marcarComoDisparado();
					cas.marcarVisible();
					pMatrizB[f][c] = FabricaColores.getFabricaColores().generarColores("AguaTocada");	
				}

				
						
						
				
				c++;
				
			}
			
			f++;
		}
		
		
	}
	
	

}