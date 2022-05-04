package barco;

import java.awt.Color;

public class ParteBarco {
	
	
	private int idPos;
	private Barco barco;
	private CasillaDeJuego cas;
	
	public ParteBarco(int pID, Barco pBarco, CasillaDeJuego pCas) {
		this.idPos = pID;
		this.barco = pBarco;
		this.cas = pCas;
		pCas.asignarParteBarco(this);
		
	}
	
	public void repararTrozo(Color[][] matriz, boolean pTieneEscudo) {
		
		if (this.disparado()) {
			this.cas.marcarComoNoDisparado();
			this.cas.ponerColorTrasReparar(matriz, pTieneEscudo);									
		} 
		
	}
	
	
	public void repararBarco(Color[][] matriz) {
		this.barco.repararBarco(matriz);
	}
	
	
	public int costeReparacionDeBarco() {
		
		return this.barco.costeDeReparacionDeBarco();
		
	}
	
	
	public boolean disparado() {return this.cas.disparado();}
	
	public void marcarComoDisparado() {this.cas.marcarComoDisparado();}
	
	

	
	
	public void visibilizarEscudo (Color[][] pMatriz) {
		if (cas.visible()) {
			pMatriz[this.cas.pos().getFila()][this.cas.pos().getCol()] = FabricaColores.getFabricaColores().generarColores("BarcoEscudo");			
		}
		
	}
	
	
	public void ponerColorSinEscudo(Color[][] pMatriz) {
		if (cas.visible()) {
			pMatriz[this.cas.pos().getFila()][this.cas.pos().getCol()] = this.cas.calcularColorSinEscudo();			
		}
	}
	
	public void visibilizarHundido(Color[][] pMatriz) {
		this.cas.visibilizarHundido(pMatriz);
	}
	
	public void marcarComoTocado(Color[][] pMatriz) {
		this.cas.marcarCasilla(pMatriz);
	}
	
	public boolean[] tocarBarco(Armamento pTiro, Color[][] pMatriz) {
		
		// Post: 00 --> Tocado | 01 -> Hundido | 10 -> Escudo Bloquea | 11 -> Escudo Se Destruye

		
		boolean[] res = this.barco.tocar(this.idPos, pTiro, pMatriz);

		if (res[0]) {
			if (!res[1]) {
				pMatriz[this.cas.pos().getFila()][this.cas.pos().getCol()] = FabricaColores.getFabricaColores().generarColores("BarcoEscudoTocado"); // MARCAR COMO ESCUDO DAÑADO
		
			}
			
			// SI EL ESCUDO FUE DESTRUIDO, YA SE ACTUALIZO EN BARCO.TOCAR()
		
		} else if (!res[1]) {
			
			pMatriz[this.cas.pos().getFila()][this.cas.pos().getCol()] = FabricaColores.getFabricaColores().generarColores("BarcoTocado"); // MARCAR COMO DAÑADO
		
			
			
		} // SI EL BARCO FUE HUNDIDO YA SE ACTUALIZO EL COLOR
		
		
		return res;
		
	}
	
	public boolean conEscudo() {return this.barco.conEscudo();}
	
	
	public boolean ponerEscudo (Escudo pEsc, Color[][] pMatriz) {
		
		
		
		return this.barco.ponerEscudo(pEsc, pMatriz);}
	
	

}