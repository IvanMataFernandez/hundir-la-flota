package barco;

import java.awt.Color;

public class CasillaDeJuego {

	
	private ParteBarco trozoDeBarco;
	private boolean disparado;
	private boolean esDeHumano;
	private boolean visible; 
	private Posicion posicion;
	
	
	
	
	
	
	public CasillaDeJuego(boolean pJ1, Posicion pPos) {
		this.disparado = false;
		this.esDeHumano = pJ1;
		this.visible = false;
		this.posicion = pPos;
		this.trozoDeBarco = null;
	}
	
	
	public void asignarParteBarco (ParteBarco pB) {this.trozoDeBarco = pB;}
	
	
	public void marcarComoDisparado() {this.disparado = true;}

	
	public boolean ponerEscudo(Escudo pEsc, Color[][] pMatriz) {
		
		if (this.trozoDeBarco != null) {
			return this.trozoDeBarco.ponerEscudo(pEsc, pMatriz);	
		} else {
			return false;
		}
		
	}
	
	public boolean visible() {return this.esDeHumano || this.visible;}
	
	public boolean seVio() {return this.visible;}
	
	public void marcarVisible() {this.visible = true;}
	
	public boolean disparado() {return this.disparado; }
	
	
	public boolean[] disparar(Armamento pTiro, Color[][] pMatriz) {
		
		// Post: 00 -> Agua | 01 -> Tocado | 10 -> Escudo | 11 -> Hundido
	
		boolean res[] = new boolean[2]; 

		this.visible = true;
		
		this.disparado = this.trozoDeBarco == null || !this.trozoDeBarco.conEscudo();
		
		if (this.trozoDeBarco != null) {
			res = this.trozoDeBarco.tocarBarco(pTiro, pMatriz);
			
			if (res[0]) {res[1] = false;} // Transformar la lógica del post de tocarBarco() a la postcondicion de arriba
			else if (res[1]) {res[0] = true;}
			else {res[1] = true;}
		
		} else {
			res[0] = false;
			res[1] = false;
			pMatriz[this.posicion.getFila()][this.posicion.getCol()] = Color.blue;
		}
		
		return res;
		
		
	}
	
	public Posicion pos() {return this.posicion;}
	
	public boolean hayBarco() {return this.trozoDeBarco != null;}

	
	
	public Color calcularColorSinEscudo() {
		if (this.disparado) {
			return Color.red;
		} else {
			return Color.green;
		}
	}
	
	public Color calcularColorSinTocar() {
		if (this.trozoDeBarco.conEscudo()) { 
			return Color.black;
		} else {
			return Color.green;
		}
	}

	
	
}