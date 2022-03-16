package barco;

public class CasillaDeJuego {

	
	private ParteBarco trozoDeBarco;
	private boolean disparado;
	
	public CasillaDeJuego(ParteBarco pB) {
		this.trozoDeBarco = pB;
		this.disparado = false;
	}
	
	
	public boolean disparado() {return this.disparado; }
	
	public boolean[] disparar() {
		
		boolean res[] = new boolean[2]; // Por defecto en F
		
		this.disparado = true;
		
		if (this.trozoDeBarco != null) {
			res[0] = true;
			res[1] = this.trozoDeBarco.tocarBarco();
		
		} 
		
		return res;
		
		
	}
	
}
