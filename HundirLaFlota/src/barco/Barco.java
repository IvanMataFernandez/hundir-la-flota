package barco;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Barco {


	private String nombre;
	private Escudo escudo;
	private ArrayList<ParteBarco> trozosDeBarcos;
	private int longitud;
	private int precioPorCasilla;
	
	
	
	public Barco(int pLong, String pNombre, int pPrecioPorCasilla) {
		this.nombre = pNombre;
		this.trozosDeBarcos = new ArrayList<ParteBarco>();
		this.longitud = pLong; // NO EN USO POR AHORA
		this.escudo = null;
		this.precioPorCasilla = pPrecioPorCasilla;
	}
	
	
	public void repararBarco(Color[][] matriz) {
		boolean tieneEscudo = this.escudo != null;
		
		this.trozosDeBarcos.stream().forEach(p -> p.repararTrozo(matriz, tieneEscudo));
	}
	
	
	public int costeDeReparacionDeBarco() {
		
		if (this.estaHundido()) {
			return -2;
			
		} else {
			return (int) this.trozosDeBarcos.stream().filter(p -> p.disparado()).count() * this.precioPorCasilla;
			
		}
		
	}
	
	
	
	public boolean ponerEscudo(Escudo pEsc, Color[][] pMatriz) {
		
		/* Pre: Objetos Escudo y matriz de colores no NULL
		
		   Post: True --> Escudo puesto y se han marcado que cambios hay que hacer en la interfaz gr�fica
		         False -> Escudo no posible colocar.
		
		
		*/
		
		if (this.escudo == null && !this.estaHundido()) { // El escudo solo se pone si no hay uno ya y el barco no se hundi�
			this.escudo = pEsc;
			this.trozosDeBarcos.stream().forEach((p) -> p.visibilizarEscudo(pMatriz));

			return true;
		} else {
			return false;
		}
		
	}
	
	private boolean estaHundido() {
		
		
		/* Pre: --
		   
		   Post: True --> Todas las casillas a las que apunta este barco est�n tocadas
		         False -> No todas las casillas est�n tocadas.
		  
		  
		  
		 */
		
		
		return this.trozosDeBarcos.stream().allMatch((trozo) -> trozo.disparado());
	
		
	}
	
	

	public void visibilizarHundido(Color[][] pMatriz) {this.trozosDeBarcos.stream().forEach(p -> p.visibilizarHundido(pMatriz));}
	
	private void hundirBarco(Color[][] pMatriz) {this.trozosDeBarcos.stream().forEach(p -> p.marcarComoTocado(pMatriz));}
	
	public void addParteBarco(ParteBarco p) {this.trozosDeBarcos.add(p);} // Asignar casillas al barco tras inicializarlo.
	
	

	
	
	public boolean conEscudo() {return this.escudo != null;}  // Si el escudo no es null tiene activo, si es null no tiene escudo.
	
	
	public boolean[] tocar(int pIDPosBarco, Armamento pTiro, Color[][] pMatriz) {
		
		/* Pre: Primer int dice que posici�n del barco se quiere tocar, el tiro si es un misil o bomba y la matriz donde se deber�an
		        los cambios en la interfaz gr�fica.
		        
		   Post: Dos booleanos (0 = F, 1 = T) que dicen que paso tras hacer el tiro (el tiro se sabe que est� impactando en un barco) 
		         
		         00 --> Tocado | 01 -> Hundido | 10 -> Escudo Bloquea | 11 -> Escudo Se Destruye 
		   
		   
		   */
		
		boolean[] res = new boolean[2];
		
		if (!this.conEscudo()) {
			
			if (pTiro instanceof Bomba)  {
				res[0] = false;
				this.trozosDeBarcos.get(pIDPosBarco).marcarComoDisparado();
			
				
				res[1] = this.estaHundido();
				
				if (res[1]) {
					this.visibilizarHundido(pMatriz);
				}
				
			} else {
				this.hundirBarco(pMatriz);
				
				res[1] = true;
			}
			

			
		} else {
			res[0] = true;
			
			if (this.escudo.procesarDa�o(pTiro)) {
				this.escudo = null;
				this.trozosDeBarcos.stream().forEach((t) -> t.ponerColorSinEscudo(pMatriz));
				res[1] = true;
				
			} else {
				res[1] = false;
			}

		}
		

		
		
		return res;		

		
		
		
	}
	


}