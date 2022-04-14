package barco;

import java.awt.Color;

public class Escudo implements Armamento {

	
	private boolean sinTocar;
	
	public Escudo () {this.sinTocar = true;}
	
	
	public boolean[] usar(CasillaDeJuego[][] pMatrizA, Color[][] pMatrizB, Posicion pPos) {
		
		// TRUE --> ESCUDO SE PONE.   FALSE --> NO SE PONE
		
		boolean[] res = new boolean[1];
		res [0] = pMatrizA[pPos.getFila()][pPos.getCol()].ponerEscudo(this, pMatrizB);
		
		if (res[0]) {
			TextoYAudio.getInstancia().setAudio("escudoPuesto");
		}
		
		return res;
	}
	
	
	
	public boolean procesarDaño(Armamento pTiro) {
		
		
		// Post: True <--> EL ESCUDO SE DESTRUYE
		
		if (this.sinTocar && pTiro instanceof Bomba) {
			this.sinTocar = false;
			return false;
		} else {
			return true;
		}
		
		
	}
}