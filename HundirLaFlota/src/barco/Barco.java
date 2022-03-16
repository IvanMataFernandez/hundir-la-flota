package barco;

public abstract class Barco {

	
	private boolean[] partes;
	private String nombre;
	
	public Barco(int pLong, String pNombre) {
		this.partes = new boolean[pLong];
		this.nombre = pNombre;
		// DEBEREIA ESTAR COMO FALSE, SI FALLA INBICIALIZAR A FALSE
	}
	
	
	
	
	public boolean tocar(int pIDPosBarco) {
		
		// Pre: La posicion tocada:
		// Post: boolean = el barco fue hundido tras tocar con esto.
		
		
		// ¿SE PUEDE CON JAVA8?
		
		this.partes[pIDPosBarco] = true;

		boolean hundido = false;
		int i = 0;

		while (!hundido && i != partes.length) {
			hundido = this.partes[i];
			i++;
		}
		return hundido;		

		
		
		
	}
	
	

}
