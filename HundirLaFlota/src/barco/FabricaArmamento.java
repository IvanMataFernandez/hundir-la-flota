package barco;

public class FabricaArmamento {

	private static FabricaArmamento fabrica;
	
	private FabricaArmamento () {}
	
	public static FabricaArmamento getFabricaArmamento() {
		
		if (FabricaArmamento.fabrica == null) {FabricaArmamento.fabrica = new FabricaArmamento();}
		
		return FabricaArmamento.fabrica;
		
		
	}
	
	public Armamento generarArmamento(int pArm) {
		
		Armamento arm = null;
		
		switch (pArm) {
			case 0: 
				arm = new Radar();
				break;
			case 1:
				arm = new Bomba();
				break;
			case 2:
				break; // MISIL NO IMPLEMENTADO
			case 3:
				arm = new Escudo();
			default:	
		
		}
		
		return arm;
		
	}
	
}