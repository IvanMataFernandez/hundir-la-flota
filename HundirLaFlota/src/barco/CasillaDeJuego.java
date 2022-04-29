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
	
	
	
	public boolean barcoReforzadoConEscudo() {
		// Pre: La casilla tiene un barco sin hundir
		// Post: Dicho barco tiene escudo o no
		
		return this.trozoDeBarco.conEscudo();
		
	}
	
	public void marcarComoDisparado() {this.disparado = true;}

	
	public boolean ponerEscudo(Escudo pEsc, Color[][] pMatriz) {
		
		if (this.trozoDeBarco != null) {
			return this.trozoDeBarco.ponerEscudo(pEsc, pMatriz);	
		} else {
			return false;
		}
		
	}
	
	public boolean visible() {return this.esDeHumano || this.visible;}
	
	public void marcarCasilla(Color[][] pMatriz) {this.visible = true; this.disparado = true; pMatriz[this.posicion.getFila()][this.posicion.getCol()] = FabricaColores.getFabricaColores().generarColores("BarcoHundido");}
	
	public void visibilizarHundido(Color[][] pMatriz) {pMatriz[this.posicion.getFila()][this.posicion.getCol()] = FabricaColores.getFabricaColores().generarColores("BarcoHundido");}
	
	
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
			
			if (res[0]) {res[1] = false;} // Transformar la l�gica del post de tocarBarco() a la postcondicion de arriba
			else if (res[1]) {res[0] = true;}
			else {res[1] = true;}
					
		
		} else {
			res[0] = false;
			res[1] = false;
			pMatriz[this.posicion.getFila()][this.posicion.getCol()] = FabricaColores.getFabricaColores().generarColores("AguaTocada");
		}
		
		return res;
		
		
	}
	
	public Posicion pos() {return this.posicion;}
	
	public boolean hayBarco() {return this.trozoDeBarco != null;}

	
	
	public Color calcularColorSinEscudo() {
		if (this.disparado) {
			return FabricaColores.getFabricaColores().generarColores("BarcoTocado");
		} else {
			return FabricaColores.getFabricaColores().generarColores("BarcoColocado");
		}
	}
	
	public Color calcularColorSinTocar() {
		
		if (this.esDeHumano) {
			if (this.trozoDeBarco.conEscudo()) { 
				return FabricaColores.getFabricaColores().generarColores("BarcoEscudoDetectado");
			} else {
				return FabricaColores.getFabricaColores().generarColores("BarcoDetectado");
			}
			
		} else {
			if (this.trozoDeBarco.conEscudo()) { 
				return FabricaColores.getFabricaColores().generarColores("BarcoEscudo");
			} else {
				return FabricaColores.getFabricaColores().generarColores("BarcoColocado");
			}	
			
		}
		
		

	} 

	
	
}