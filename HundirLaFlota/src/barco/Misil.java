package barco;

import java.awt.Color;

public class Misil implements Armamento {

	public boolean[] usar (CasillaDeJuego[][] pMatrizA, Color[][] pMatrizB, Posicion pPos) {
		

		
		boolean[] res = pMatrizA[pPos.getFila()][pPos.getCol()].disparar(this, pMatrizB);
		return res;
		
	
	}

}
