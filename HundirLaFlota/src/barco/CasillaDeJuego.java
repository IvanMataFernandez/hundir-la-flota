package barco;

import java.awt.Color;

public class CasillaDeJuego {

	
	private ParteBarco trozoDeBarco;
	private boolean disparado;
	private boolean esDeHumano;
	
	
	// PARA JA
	
	public CasillaDeJuego(ParteBarco pB, boolean pJ1) {
		this.trozoDeBarco = pB;
		this.disparado = false;
		this.esDeHumano = pJ1;
	}
	
	
	public boolean disparado() {return this.disparado; }
	
	public boolean[] disparar() {
		
		// Post: Pos 0 <--> Si ha tocado barco
		//       Pos 1 <--> Ha hundido
		boolean res[] = new boolean[2]; // Por defecto en F
		
		
		this.disparado = true;
		
		if (this.trozoDeBarco != null) {
			res[0] = true;
			res[1] = this.trozoDeBarco.tocarBarco();
		
		} 
		
		return res;
		
		
	}
	
	public Color obtenerColorActualizado() {
		
		Color color;
		
		

		
		if (this.disparado) {
			
			
			if (this.trozoDeBarco != null) {
				color = Color.red;
				
			} else {
				color = Color.blue;
				
				
			}
			
		} else if (this.esDeHumano) {
			
			
			if (this.trozoDeBarco != null) {
				color = Color.green;
				
			} else {
				color = Color.cyan;
				
			}
			
		} else  {
			
			
			
			
				color = Color.cyan;
			
		}
 		
		
		
		
		return color;
	}

	
	
	
}
