package barco;

import vista.*;

public class GestorInputs {

	private static GestorInputs gestor;
	private boolean aceptaInput;
	private int filaSelec;
	private int colSelec;
	private boolean matrizJ1Selec;
	private int botonPulsado;
	
	
	private GestorInputs() {
		this.botonPulsado = -1;
	}
	
	public static GestorInputs getGestor() {
		if (GestorInputs.gestor == null) {GestorInputs.gestor = new GestorInputs();}
		return GestorInputs.gestor;
	}
	
	
	
	public void cogerInput(CasillaDePantalla pCas) {
		
		if (this.aceptaInput) {
			this.aceptaInput = false;
			this.filaSelec = pCas.getFila();
			this.colSelec = pCas.getCol();
			this.matrizJ1Selec = pCas.esDeJ1();
			
			
		}
		
	//	System.out.println("La fila es: " +pF+" \nLa columna es: "+pC+"\n La matriz es del jugador: " +pJ);
	}
	
	public void cogerInput (Boton pBot) {
		
		if (this.aceptaInput) {
			this.aceptaInput = false;
			this.botonPulsado = pBot.getID();
		}	

		
	}
	
	public void esperarInputDeCasilla() {
		
		
		this.aceptaInput = true;
		this.filaSelec = -1;
		this.botonPulsado = -1;
		

		while (this.filaSelec == -1) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}
	
	public void esperarInput() {
		this.aceptaInput = true;
		this.filaSelec = -1;
		this.botonPulsado = -1;
		

		while (this.filaSelec == -1 && this.botonPulsado == -1) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
		
	}
	
	public int getFila() {return this.filaSelec;}
	
	public int getCol() {return this.colSelec;}
	
	public boolean getMatrizJ1Selec() {return this.matrizJ1Selec;}
	
	public int getBotonPulsado() {
		
		return this.botonPulsado;
		
		
	}
	
}
