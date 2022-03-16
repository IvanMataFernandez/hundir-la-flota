package barco;

public class Barco {

	
	private boolean[] partes;
	
	
	public Barco(int pLong) {
		this.partes = new boolean[pLong];
		
		// DEBEREIA ESTAR COMO FALSE, SI FALLA INBICIALIZAR A FALSE
	}
	
	
	
	
	public boolean tocar(int pIDPosBarco) {
		
		// Pre: La posicion tocada:
		// Post: boolean = el barco fue hundido tras tocar con esto.
		
		
		// ¿SE PUEDE CON JAVA8?
		
		int i = 0;
		boolean hundido = false;
		this.partes[pIDPosBarco] = true;

		
		while (!hundido && i != partes.length) {
			hundido = this.partes[i];
			i++;
		}
		
		return hundido;
		
		
	}
}
