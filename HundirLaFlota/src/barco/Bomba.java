package barco;

import java.awt.Color;

public class Bomba implements Armamento {
	
	public Bomba() {}
	
	public boolean[] usar (CasillaDeJuego[][] pMatrizA, Color[][] pMatrizB, Posicion pPos) {
		
		/* Pre: La matriz donde se quiere tirar la bomba, la matriz donde se actualizarán los cambios
		        en la interfaz gráfica y la posición donde se tira la bomba.
		        
		   Post: El resultado tras tirar el proyectil:
		   
		         00 -> Agua | 01 -> Tocado | 10 -> Escudo | 11 -> Hundido
		  
		  
		  
		 */
		
		
		boolean[] res = pMatrizA[pPos.getFila()][pPos.getCol()].disparar(this, pMatrizB);
		return res;
		
	
	}

}
