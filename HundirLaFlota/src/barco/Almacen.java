package barco;


public class Almacen {
	
	
	private static Almacen alc;
	

	private Integer[] cantidad; // ORDEN: RADAR, MISIL, ESCUDO
	private Integer[] precios;
	
	
	private Almacen () {
		this.cantidad = new Integer[3];
		this.precios = new Integer[3];
		this.cantidad[0] = 5;
		this.cantidad[1] = 5;
		this.cantidad[2] = 5;
		this.precios[0] = 500;
		this.precios[1] = 750;
		this.precios[2] = 1000;
		
	}
	
	public static Almacen getAlmacen() {
		if (Almacen.alc == null) {Almacen.alc = new Almacen();}
		return Almacen.alc;
	}
	
	
	
	public int radaresRestantes() {return this.cantidad[0];}
	
	public int misilesRestantes() {return this.cantidad[1];}
	
	public int escudosRestantes() {return this.cantidad[2];}
	
	public boolean hayRadares() {return this.cantidad[0] != 0;}
	
	public boolean hayMisiles() {return this.cantidad[1] != 0;}
	
	public boolean hayEscudos() {return this.cantidad[2] != 0;}
	
	public void consumirRadar() {this.cantidad[0]--;}
	
	public void consumirMisil() {this.cantidad[1]--;}
	
	public void consumirEscudo() {this.cantidad[2]--;}
	
	public int precioRadar() {return this.precios[0];}
	
	public int precioMisil() {return this.precios[1];}
	
	public int precioEscudo() {return this.precios[2];}
	

}
