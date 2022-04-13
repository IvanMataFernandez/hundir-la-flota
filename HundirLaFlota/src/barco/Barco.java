package barco;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Barco {


	private String nombre;
	private Escudo escudo;
	private ArrayList<ParteBarco> trozosDeBarcos;
	private int longitud;
	
	
	
	
	public Barco(int pLong, String pNombre) {
		this.nombre = pNombre;
		this.trozosDeBarcos = new ArrayList<ParteBarco>();
		this.longitud = pLong; // NO EN USO POR AHORA
		this.escudo = null;
	}
	
	public boolean ponerEscudo(Escudo pEsc, Color[][] pMatriz) {
		
		/* Pre: Objetos Escudo y matriz de colores no NULL
		
		   Post: True --> Escudo puesto y se han marcado que cambios hay que hacer en la interfaz gráfica
		         False -> Escudo no posible colocar.
		
		
		*/
		
		if (this.escudo == null && !this.estaHundido()) { // El escudo solo se pone si no hay uno ya y el barco no se hundió
			this.escudo = pEsc;
			this.trozosDeBarcos.stream().forEach((p) -> p.visibilizarEscudo(pMatriz));

			return true;
		} else {
			return false;
		}
		
	}
	
	private boolean estaHundido() {
		
		
		/* Pre: --
		   
		   Post: True --> Todas las casillas a las que apunta este barco están tocadas
		         False -> No todas las casillas están tocadas.
		  
		  
		  
		 */
		
		
		return this.trozosDeBarcos.stream().allMatch((trozo) -> trozo.disparado());
	
		
	}
	
	

	
	

	
	public void addParteBarco(ParteBarco p) {this.trozosDeBarcos.add(p);} // Asignar casillas al barco tras inicializarlo.
	
	

	
	
	public boolean conEscudo() {return this.escudo != null;}  // Si el escudo no es null tiene activo, si es null no tiene escudo.
	
	
	public boolean[] tocar(int pIDPosBarco, Armamento pTiro, Color[][] pMatriz) {
		
		/* Pre: Primer int dice que posición del barco se quiere tocar, el tiro si es un misil o bomba y la matriz donde se deberían
		        los cambios en la interfaz gráfica.
		        
		   Post: Dos booleanos (0 = F, 1 = T) que dicen que paso tras hacer el tiro (el tiro se sabe que está impactando en un barco) 
		         
		         00 --> Tocado | 01 -> Hundido | 10 -> Escudo Bloquea | 11 -> Escudo Se Destruye 
		   
		   
		   */
		
		boolean[] res = new boolean[2];
		
		if (!this.conEscudo()) {
			res[0] = false;
			this.trozosDeBarcos.get(pIDPosBarco).marcarComoDisparado();
		
			
			res[1] = this.estaHundido();
			
		} else {
			res[0] = true;
			
			if (this.escudo.procesarDaño(pTiro)) {
				this.escudo = null;
				this.trozosDeBarcos.stream().forEach((t) -> t.ponerColorSinEscudo(pMatriz));
				res[1] = true;
				
			} else {
				res[1] = false;
			}

		}
		

		
		
		return res;		

		
		
		
	}
	
	
	/*	public void marcarColoresEnTodoElBarco (Color[][] pMatriz) {
	this.trozosDeBarcos.stream().forEach((p) -> p.(Color.black, pMatriz));
} */
	
	

}