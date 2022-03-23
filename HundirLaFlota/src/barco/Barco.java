package barco;


public abstract class Barco {


	private boolean[] partes;
	private String nombre;
	
	public Barco(int pLong, String pNombre) {
		this.partes = new boolean[pLong];
		this.nombre = pNombre;
		// DEBEREIA ESTAR COMO FALSE, SI FALLA INBICIALIZAR A FALSE
	}
	
	
	// PARA IDEA COMO JAVA 8 AÑADIR ARRAYLIST DE CASILLADEJUEGO (NO ARRAY A SECAS PQ NO IMPLEMENTA COLLECTION)
	// Y AL MARCAR EL BARCO COMO HUNDIDO FORZAR A TODAS LAS CASILLAS A MARCAR SU VALOR
	
	// PARA LOS MISILES TMB SIRVE.
	
	public boolean tocar(int pIDPosBarco) {
		
		// Pre: La posicion tocada:
		// Post: boolean = el barco fue hundido tras tocar con esto.
		
		
		
		
		this.partes[pIDPosBarco] = true;

		boolean hundido = true;
		int i = 0;

		while (hundido && i != partes.length) {
			hundido = this.partes[i];
			i++;
		}
		
		
		return hundido;		

		
		
		
	}
	
	

}
