package barco;

public class ParteBarco {
	
	
	private int idPos;
	private Barco barco;
	
	public ParteBarco(int pID, Barco pBarco) {
		this.idPos = pID;
		this.barco = pBarco;
		
	}
	
	
	public boolean tocarBarco() {
		
		// Pre: --
		// Post: boolean = El barco fue hundido tras tocarlo aquí.
		
		return this.barco.tocar(this.idPos);
		
	}
	

}
