package barco;

public class ExcepcionFinDePartida extends Exception {
	
	private boolean ganaJ1;
	
	
	public ExcepcionFinDePartida(boolean pJ1) {this.ganaJ1 = pJ1;}
	
	public boolean ganoJ1() {return this.ganaJ1;}

}
