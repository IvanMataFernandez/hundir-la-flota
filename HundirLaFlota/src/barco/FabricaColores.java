package barco;

import java.awt.Color;

public class FabricaColores {
	private static FabricaColores fabrica;
	
	private FabricaColores () {}
	
	public static FabricaColores getFabricaColores() {
		if (FabricaColores.fabrica == null) {
			FabricaColores.fabrica = new FabricaColores();
		}
		return FabricaColores.fabrica;
	}
	
	public Color generarColores(String pModo) {
		Color col = null;
		
		if (pModo.contentEquals("Fondo")) { col = new Color(62, 57, 97); }
		else if (pModo.contentEquals("AguaTocada")) { col = new Color(62, 57, 97);}
		else if (pModo.contentEquals("AguaBase")) { col = new Color(27, 153, 139);}
		

		else if (pModo.contentEquals("BarcoColocado")) { col = new Color(47, 105, 65);}
		else if (pModo.contentEquals("BarcoTocado")) { col = new Color(244, 96, 54);}
		else if (pModo.contentEquals("BarcoHundido") ) { col = new Color(215, 38, 61);}

		else if (pModo.contentEquals("BarcoEscudo")) { col = new Color(255, 238, 112);}
		else if (pModo.contentEquals("BarcoEscudoTocado")) { col = new Color(255, 189, 112);}
		
		
		return col;
	}
}