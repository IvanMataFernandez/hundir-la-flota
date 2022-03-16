package barco;

public class FabricaBarcos {

	private static FabricaBarcos fabrica;
	
	
	private FabricaBarcos() {}
	
	public static FabricaBarcos getFab() {
		if (FabricaBarcos.fabrica == null) {FabricaBarcos.fabrica = new FabricaBarcos();}
		return FabricaBarcos.fabrica;
	}
	
	public Barco generarBarco(int pTipo) {
		
		Barco b = null;
		
		switch (pTipo) {
			case 0:
				b = new Fragata();
				break;
			case 1:
				b = new Destructor();
				break;
			case 2:
				b = new Submarino();
				break;
			case 3:
				b = new Portaavion();
		
		}
		
		return b;
				
		}
		
	}

