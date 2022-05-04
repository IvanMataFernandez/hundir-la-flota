package barco;

import java.util.ArrayList;
import java.util.Random;

public class JugadorIA extends Jugador {

	
	private Random generador;
	private Posicion tocadoInicialDeBarco;
	private Posicion escudoTocado;
	private boolean detectoConRadar;
	private boolean conoceSentidoDeBarco;
	private int sentidoEnComprobacion;
	private int sentidoTocadoInicial;
	private int distancia;
	
	public JugadorIA() {
		super(false);
		this.generador = new Random();
	}

	


	public void colocarBarcos() {

		
	   	 int[] bConRes = new int[4]; // barcos concretos restantes (de 4,3,2 y 1)
	   	 int tamSelec = 3; // tamaño del barco que se quiere poner
	   	 int f1;
	   	 int c1;
	   	 boolean valido;
	   	 boolean selecHorizontal=false;
	   	 boolean porColocar;
	   	 boolean generado = false;
	   	 Posicion p1 = null;
	   	 Posicion p2 = null;
	   	 Posicion aux;
	   	 ArrayList<Integer> arrayDirecciones = null;
	   	 int posicion;
	   	 int direccion;
	   	 
	   	 for (int i = 0; i != 4; i++) {
	   		 bConRes[i] = 4-i;
	   	 }

	   	 while (tamSelec >= 0) {
	   		 if (bConRes[tamSelec] != 0) {
	   			 generado = false;
	   			 valido = false;
	   			
	   			 while (!generado) {
	   				 f1 = generador.nextInt(10);
	   				 c1 = generador.nextInt(10);			 
	   				 p1 = new Posicion(f1, c1);	 
	   				 arrayDirecciones = direccionesPosibles(p1, tamSelec);
	   				 porColocar = true;
	   				 aux = null;
	   				
	   				 while (porColocar) {
	   					 posicion = generador.nextInt(arrayDirecciones.size());  //Decide si el barco se podnrá hacia N,S,E,O
	   					 direccion = arrayDirecciones.get(posicion);
	   						 
	   					 switch (direccion) {
	   						case 0: //NORTE
  								 p2 = new Posicion(f1-tamSelec, c1);
  								 selecHorizontal = false;
	   							break;
	   						case 1: //SUR
  								 p2 = new Posicion(f1+tamSelec, c1);
   								 selecHorizontal = false;
	   							break;
	   						case 2://ESTE
  								 p2 = new Posicion(f1, c1-tamSelec);
  								 selecHorizontal = true;
	   							break;
	   						case 3: //OESTE
  								 p2 = new Posicion(f1, c1+tamSelec);
  								 selecHorizontal = true;
	   						default:
	   							
	   					}
	   					
	   					 if (p1.getFila() > p2.getFila() || p1.getCol() > p2.getCol()) {
	   						 // CAMBIAR LOS PUNTEROS PARA QUE P1 TENGA EL VALOR DE MÁS ESQUINA SUPERIOR IZQUIERDA
	   						 aux = p1;
	   						 p1 = p2;
	   						 p2 = aux;
	
	   					 }
		   				

		   				
		   				valido = super.posibleColocar(p1, p2, selecHorizontal); //Mira que no choque con otros barcos
							 
							 
		   				if (valido) { // Si se puede...

		   					super.generarBarco(p1, p2, tamSelec, selecHorizontal); //Pone el barco

		   					bConRes[tamSelec]--;
	   								 
		   					super.actualizarCambios();
		   					generado = true;
		   					porColocar = false;
	   								 
	   					 } else {
	   						 arrayDirecciones.remove(posicion);
	   						 
	   						 if (arrayDirecciones.isEmpty()) {porColocar = false; }
	   						 else if (aux != null ){ 
	   							 
	   							 // TRAS CAMBIAR LOS PUNTEROS, VOLVER A PONER P1
	   							 // COMO EL PUNTERO BASE DESDE DONDE SE QUIERE COLOCAR EL BARCO
	   							 
	   							 p2 = p1;
	   							 p1 = aux;
	   							 aux = null;
	   							 
	   						 }
	   						 
	   					 }

	   				 }
	   						 
		 
	   			}
	   					 
	   		} else {tamSelec = tamSelec-1;}

	   	}
	   	 
	   	 super.completarTablero();	 
	   	 super.actualizarCambios();
	    
	}
	
	private ArrayList<Integer> direccionesPosibles(Posicion p1, int tamSelec) {
	    ArrayList<Integer> arrayDireccionesAux = new ArrayList<Integer>(); //Creamos el array con 4 posiciones
	    int f1 = p1.getFila();
	    int c1 = p1.getCol();
	    
	   	 //Puede Norte?    Si puede norte 100-->0
	   	 if (f1-tamSelec > -1) {
	   		 arrayDireccionesAux.add(0);
	   	 }
	   	 
	   	 //Puede Sur?    Si puede sur 100-->1
	   	 if (f1+tamSelec < 10) {
	   		 arrayDireccionesAux.add(1);
	   	 }
	   	 
	   	 //Puede Este?    Si puede este 100-->2
	   	 if (c1-tamSelec > -1) {
	   		 arrayDireccionesAux.add(2);
	   	 }
	   	 
	   	 //Puede Oeste?    Si puede oeste 100-->3
	   	 if (c1+tamSelec < 10) {
	   		 arrayDireccionesAux.add(3);
	   	 }
	    
	    return (arrayDireccionesAux);
	}
		
		
	
	
	private Posicion elegirTiroPreciso() {
		
		
		/* Pre: Turno de IA y ha encontrado un barco que quiere hundir (tocado inicial /= null)
		        y el disparo anterior no fue bloqueado por un escudo.

		 
		 Post: La posición donde quiere disparar si encaja en el sentido actual o null si quiere cambiar sentido
		       de disparo, para ello calcula la posición con las siguientes variables:
		       
		       - La variable tocadoInicial marca en que posición se dañó la primera parte del barco
		       - La variable sentidoEnComprobación marca el sentido en el que quiere disparar la bomba,
		         solo puede disparar en la linea horizontal o vertical (y no diagonal) a en la que 
		         descubrió el barco por primera vez. Los valores posibles de esta variable son
		         0 (arriba), 1 (derecha), 2 (abajo) o 3 (izquierda).
		       - La variable distancia marca a cuanta distancia del origen del primer disparo al barco
		         se debería atacar ahora. 
		         
		         
		         
		      Con esa información, la IA procede a mirar si la casilla existe en la matriz y no se disparó ahí.
		      Si se cumple eso, devuelve esa posición. La IA procederá a atacar la posición y actualizar las 
		      variables anteriores.
		      
		      Si no se cumple, la IA no puede disparar en dicha posición (devuelve null).
		    
		      * Si la causa de eso fue que ya el barco fue tocado ahí, se suma uno a la distancia total y 
		        se atacará la siguiente casilla
		      
		      * Si la causa de eso fue que no hay espacio en la matriz o se tocó agua, se cambiará la dirección
		        de ataque (usando la misma lógica que en la que cuando la IA ataca y toca agua, explicado más abajo),
		        reseteando la distancia a 1.
		      
		      
		      Si se puede atacar, tras el ataque pueden ocurrir 4 cosas que influirán en las siguientes llamadas:
		      
		      La IA acierta y hunde el barco --> Hasta que la IA no toque a otro barco
		                                         por otra causa este método no será llamado (tocadoInicial = null)
		                                         
		      
		      La IA acierta y daña pero no hunde -> La IA preparará el tiro en este método para (distancia + 1)    
		                                            La IA ahora conoce la orientación del barco (horiz / vert).                               
		      
		      
		      La IA falla y toca agua ->  Si la IA ya dañó al menos una parte extra del barco en este sentido 
		                                  (no incluye tiro inicial),  entonces da un giro de 180º al sentido a 
		                                  disparar (izquierda <--> derecha, arriba <--> abajo)
		      
		                                  Si la IA no dañó, y por lo tanto no conoce la orientación del barco, 
		                                  procede a comprobar la siguiente  dirección que le toca, siendo el orden:
		                                  arriba -> derecha -> abajo -> izquierda -> arriba
		      
		      
		      La IA acierta pero escudo bloquea -> Debido a que el jugador reforzó el barco con un escudo entre turnos
		                                           la IA pasará a prioridad 1 (veáse documentación elegirTiro() )
		                                           e ignorará este método hasta que rompa el escudo y pegue al trozo
		                                           en su interior. Tras hacer eso, depende de si hunda el barco o no
		                                           hará el caso correspondiente explicado anteriormente.
		      
		        
		  	
             Existe un caso crítico extra, si el humano decide reparar el barco mientras que la IA lo está hundiendo, para
             solventar esto la IA comprueba antes de cada disparo si el barco fue reparado, si lo fue pueden ocurrir dos cosas:
             
             La IA no conoce todavía la orientación del barco -> IA procede a disparar la casilla inicial que tocó y el resto se hace igual
             
             La IA conoce la orientación del barco -> IA procede a atacar el barco en el primer sentido acertado que hizo, luego
                                                      da vuelta de 180º y acaba de hundirlo atacando por el sentido contrario.
                                                                 
                                                                 
             Si se combinase REPARACION y ESCUDO, entonces la IA rompería el escudo y dañaría dicho trozo del barco fuera de este método 
             (prioridad 1) y tras darse cuenta de que el barco se arregló, volvería a atacar el barco usando la lógica de "La IA conoce
             la orientación del barco" del punto anterior.                                              
		  
		  
		 */
		
		Posicion p = null;
		JugadorHumano jHu = Jugadores.getJugadores().getJugadorHumano();

		
		if (!jHu.haDisparadoAhi(this.tocadoInicialDeBarco.getFila(), this.tocadoInicialDeBarco.getCol()))  {
			this.distancia = 0;
			if (this.conoceSentidoDeBarco) {
				this.sentidoEnComprobacion = this.sentidoTocadoInicial;
			}
		} // Este IF mira si el barco que está hundiendo en el momento fue reparado por el Humano
		
		
		switch (this.sentidoEnComprobacion) {
		
			case 0: // Mirar arriba
			
				if (this.tocadoInicialDeBarco.getFila() - this.distancia >= 0 && !jHu.haDisparadoAhi(this.tocadoInicialDeBarco.getFila()-distancia, this.tocadoInicialDeBarco.getCol())) {
					p = new Posicion(this.tocadoInicialDeBarco.getFila()-this.distancia, this.tocadoInicialDeBarco.getCol());
					this.distancia++;

				} else if (this.tocadoInicialDeBarco.getFila() - this.distancia >= 0 && jHu.hayBarcoDisparadoAhi(this.tocadoInicialDeBarco.getFila()-distancia, this.tocadoInicialDeBarco.getCol())) {
					this.distancia++;
					
				} else {	
					
					
					if (this.conoceSentidoDeBarco) {
						this.sentidoEnComprobacion = 2;
					} else {
						this.sentidoEnComprobacion = 1;

					}
					
					this.distancia = 1;
				}

			
				break;
		
			case 1:   // Mirar derecha
			
				if (this.tocadoInicialDeBarco.getCol() + this.distancia <= 9 && !jHu.haDisparadoAhi(this.tocadoInicialDeBarco.getFila(), this.tocadoInicialDeBarco.getCol()+distancia)) {
					p = new Posicion(this.tocadoInicialDeBarco.getFila(), this.tocadoInicialDeBarco.getCol()+distancia);
					this.distancia++;

				} else if (this.tocadoInicialDeBarco.getCol() + this.distancia <= 9 && jHu.hayBarcoDisparadoAhi(this.tocadoInicialDeBarco.getFila(), this.tocadoInicialDeBarco.getCol()+distancia)) {	
					this.distancia++;
					
				} else {
					
					if (this.conoceSentidoDeBarco) {
						this.sentidoEnComprobacion = 3;
					} else {
						this.sentidoEnComprobacion = 2;

					}					
					
					this.distancia = 1;
				}
			
			
				break;
		
			case 2:   // Mirar abajo
			
				if (this.tocadoInicialDeBarco.getFila() + this.distancia <= 9 && !jHu.haDisparadoAhi(this.tocadoInicialDeBarco.getFila()+distancia, this.tocadoInicialDeBarco.getCol())) {
					p = new Posicion(this.tocadoInicialDeBarco.getFila()+this.distancia, this.tocadoInicialDeBarco.getCol());
					this.distancia++;

				} else if (this.tocadoInicialDeBarco.getFila() + this.distancia <= 9 && jHu.hayBarcoDisparadoAhi(this.tocadoInicialDeBarco.getFila()+distancia, this.tocadoInicialDeBarco.getCol())) {
					this.distancia++;
					
				} else {
					
					if (this.conoceSentidoDeBarco) {
						this.sentidoEnComprobacion = 0;
					} else {
						this.sentidoEnComprobacion = 3;

					}					
					
					this.distancia = 1;
				}
			
				break;
			case 3:	  // Mirar izquierda
			
				if (this.tocadoInicialDeBarco.getCol() - this.distancia >= 0 && !jHu.haDisparadoAhi(this.tocadoInicialDeBarco.getFila(), this.tocadoInicialDeBarco.getCol()-distancia)) {
					p = new Posicion(this.tocadoInicialDeBarco.getFila(), this.tocadoInicialDeBarco.getCol()-distancia);
					this.distancia++;

				} else if (this.tocadoInicialDeBarco.getCol() - this.distancia >= 0 && jHu.hayBarcoDisparadoAhi(this.tocadoInicialDeBarco.getFila(), this.tocadoInicialDeBarco.getCol()-distancia)) {
					this.distancia++;
					
				} else {

					if (this.conoceSentidoDeBarco) {
						this.sentidoEnComprobacion = 1;
					} else {
						this.sentidoEnComprobacion = 0;

					}
					
					this.distancia = 1;
				}
			
			default:
			

		}
		
		return p;
	}
	
	
	
	private void equiparMisil(boolean pSeguro) {
		
		
		if (super.tieneMisiles() && (pSeguro || this.generador.nextInt(20) == 0)) { // Equipar misil si posible
			super.cambiarArmamento();
			TextoYAudio.getInstancia().setTexto("¡IA equipa misil!");
			TextoYAudio.getInstancia().actualizarCambios();
			this.esperar(1000);
		}
		
		
		
	}
	
	private Posicion elegirTiro() {
		
		/* Pre: Turno de IA y necesita tirar un proyectil
		   Post: Posicion para disparar.
		   
		   El cálculo de posición de disparo de la IA es algo complejo y se diferencia en 4 prioridades, de más
		   alta a más baja:
	       
	       * Prioridad 1 -> La IA disparó a un barco con escudo en el disparo anterior (no influye si lo rompió o no)

	        Lla IA dispara una bomba a la misma casilla que en el disparo anterior, rompiendo el escudo
	        o dañando al barco que está en esa posición
	        
	       * Prioridad 2 -> La IA actualmente tiene un barco tocado y no hundido.
	                       
	        La IA usa el método elegirTiroPreciso() para lanzar bombas a casillas adyecentes al barco. 
	        Es demasiado complejo para entrar en detalle (véase la documentación de dicho método)
	        
	       * Prioridad 3 -> La IA conoce la posición de un barco por radar encontrado en este turno o uno previo
	       
	        La IA disparará un misil (si le quedan) a la casilla indicada para tirar hundir todo el barco de golpe. 
	        Si el barco está reforzado con escudo o no tiene misiles, tirará una bomba en su lugar
	           
		   * Prioridad 4 -> No prioridad 1, 2 o 3. (La IA está ciega sin info por parte de radar o tiros anteriores)
		   
		    La IA generá dos ints [0,9] para determinar a donde disparar. Si la casilla fue ya disparada, vuelve
		    a generar otro par hasta que encuentre un espacio donde tirar. Si la casilla no fue disparada pero
		    hay un barco visible adyacente a ella, volverá a generar otro par de ints porque sabe que hay agua ahí.
		    Si la IA dispone de misiles, tiene un 5% de probabilidades de usarlo en vez de una bomba.
		   
	
		 */
		
		
		
		JugadorHumano jHu = Jugadores.getJugadores().getJugadorHumano();
		int fila=-1;
		int col=-1;
		boolean valido = false;
		Posicion p = null;
		
		
		super.cambiarABomba(); // POR DEFECTO USAR BOMBA
 
		
		if (this.escudoTocado != null) {  // Pegar al escudo otra vez
			
			p = this.escudoTocado;



			
		} else if (this.tocadoInicialDeBarco != null) {  // Tiro para encadenar el barco
			
			while (p == null) {
				p = this.elegirTiroPreciso();
			}
			
			
		

			
		} else if (this.detectoConRadar) {    // Tiro usando la información del radar obtenida
			this.detectoConRadar = false;
		    
			if (!super.getValorEscudoRadar()) {
				this.equiparMisil(true);
			}
			
			
			p = super.getDeteccionRadar();
			

			
		} else {  // Tiro al azar sin info
			
			
			this.equiparMisil(false);

			
			while (!valido) {
				while (!valido) {               
					fila = this.generador.nextInt(10);
					col = this.generador.nextInt(10);
					valido = !jHu.haDisparadoAhi(fila, col);
				}
				
				p = new Posicion(fila,col);
				valido = Jugadores.getJugadores().getJugadorHumano().puedeHaberBarcoAhi(p);

			}
			

			
		}
		
		return p;
		
	}
	
	private void procesarEscudo () {
		
		boolean escudoPuesto;

		if (super.tieneEscudos()) { 
			
			
			escudoPuesto = super.ponerEscudoEn(new Posicion(this.generador.nextInt(10),this.generador.nextInt(10)));

			
			
			
			if (escudoPuesto) {	
				super.actualizarCambios();
				TextoYAudio.getInstancia().setTexto("IA refuerza barco con escudo");
				TextoYAudio.getInstancia().actualizarCambios();
				this.esperar(2000);

			}
			
		}	
		
	}
	
	private void procesarRadar() {
		
		
		
		if (this.escudoTocado == null && this.tocadoInicialDeBarco == null && !this.detectoConRadar && super.tieneRadar() && this.generador.nextInt(10) == 1) {
			super.moverRadar();
			
			
			this.detectoConRadar = super.usarRadarEnRival();
			
			
			
			if (this.detectoConRadar) {
				TextoYAudio.getInstancia().setTexto("¡IA usa radar y encuentra un barco!");
			} else  {
				TextoYAudio.getInstancia().setTexto("IA usa radar pero no encuentra nada...");
			}
			
			Jugadores.getJugadores().getJugadorHumano().actualizarCambios();
			
			TextoYAudio.getInstancia().actualizarCambios();
			this.esperar(1000);

		}
		
	}

	public void procesarAcciones() throws ExcepcionFinDePartida {
		
		// Pre: Turno de IA
		// Post: La IA ha disparado hasta fallar una bomba o matado toda la tripulación enemiga (lanza Excepcion)
		

		boolean encadenar = true;
		Posicion p = null;

		
		
		
		
		// PONER ESCUDO ANTES DE TIRAR
		
		this.procesarEscudo();

		
		
		// CONSULTAR RADAR
		
		this.procesarRadar();

		
		// TIRAR
		
		while (encadenar) {


			
			p = this.elegirTiro();	
			
			encadenar = this.disparar(p);

				

		}

			
			
		}
		

		
	
	
	private boolean disparar(Posicion pPos) throws ExcepcionFinDePartida {
		
		boolean res[];
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		JugadorHumano jHu = Jugadores.getJugadores().getJugadorHumano();
		
		res = super.dispararAlOtro(pPos);
		
		if (res[0]) {
			
				
			if (res[1]) { // HUNDIDO
					
				if (this.tocadoInicialDeBarco != null) { // Si se estaban centrando tiros en un barco, se quita pq se acaba de hundir
					this.tocadoInicialDeBarco = null;
				}
				
				if (this.escudoTocado != null) { // Si estaba tirando a la misma casilla para romper el escudo, ya lo hizo
					this.escudoTocado = null;
				}
					
				textoYAudio.setTexto("IA dispara en: "+(pPos.getFila()+1)+" "+(char)(65+pPos.getCol())+ ". Barco hundido");
				textoYAudio.setAudio("hundido");
				jHu.hundeUnBarco();


			} else { // ESCUDO
					
				
				if (this.escudoTocado == null) {
					this.escudoTocado = pPos;
				}
				
				
		
				textoYAudio.setTexto("IA dispara en: "+(pPos.getFila()+1)+" "+(char)(65+pPos.getCol())+ ". Bloqueado por escudo");
				textoYAudio.setAudio("escudo");

			}
				
		} else {
			
			if (res[1]) { // TOCADO
				
				if (this.tocadoInicialDeBarco == null) { // Si toco un barco lanzando al azar y no hundio, centrar los siguientes tiros en el
					this.conoceSentidoDeBarco = false;
					this.tocadoInicialDeBarco = pPos;
					this.sentidoTocadoInicial = -1;
					this.sentidoEnComprobacion = this.generador.nextInt(4);
					this.distancia = 1;
					
				} else if (!this.conoceSentidoDeBarco) {
					this.conoceSentidoDeBarco = true;
					this.sentidoTocadoInicial = this.sentidoEnComprobacion;
				}
				
				if (this.escudoTocado != null) { // Si estaba tirando a la misma casilla para romper el escudo, ya lo hizo
					this.escudoTocado = null;
				}
					
			textoYAudio.setTexto("IA dispara en: "+(pPos.getFila()+1)+" "+(char)(65+pPos.getCol())+ ". Barco tocado");
			textoYAudio.setAudio("tocado");
				
			} else { // AGUA

				if (this.tocadoInicialDeBarco != null) {
					
					if (this.conoceSentidoDeBarco) {
						
						switch (this.sentidoEnComprobacion) {
							case 0: this.sentidoEnComprobacion = 2; break;
							case 1: this.sentidoEnComprobacion = 3; break;
							case 2: this.sentidoEnComprobacion = 0; break;
							case 3: this.sentidoEnComprobacion = 1; 
						}
	
					} else {
						this.sentidoEnComprobacion++; if (this.sentidoEnComprobacion == 4) {this.sentidoEnComprobacion = 0;}
					}
					
					this.distancia = 1;
				}
				
				
				textoYAudio.setTexto("IA dispara en: "+(pPos.getFila()+1)+" "+(char)(65+pPos.getCol())+ ". Agua");
				textoYAudio.setAudio("agua");
				
			}
			

			
			
			
		}
			
		


			
		jHu.actualizarCambios();
		textoYAudio.actualizarCambios();


		this.esperar(500);

		
		return res[1];
	}


	public void procesarCompras() {
		
		// REPARAR BARCO
		
		this.procesarReparacion();
		
		this.procesarAdquisicionesDeMateriales();
		

	}
	
	private void procesarReparacion () {
		
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		
		int f = this.generador.nextInt(10);
		int c = this.generador.nextInt(10);
		
		int precio = super.precioDeReparacionDeBarco(f,c);
 
		
		if (precio > 0 && precio <= super.getDinero()) {
			super.repararBarco(f, c);
			super.bajarDineroEn(precio);
			textoYAudio.setTexto("La IA ha reparado un barco por valor de "+precio);
			textoYAudio.setAudio("construccion");
			textoYAudio.actualizarCambios();
			super.esperar(500);
		}
		
	}
	
	private void procesarAdquisicionesDeMateriales() {
		
		Almacen alm = Almacen.getAlmacen();
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		String msg = "";
		boolean dosPrimerasCompras = false;
		
		// Comprar RADAR (20% de probabilidad)
		
		if (!super.tieneRadar() && this.generador.nextInt(5) == 0 && super.getDinero() >= alm.precioRadar() && alm.hayRadares()) {
			alm.consumirRadar();
			super.darRadar();
			super.bajarDineroEn(alm.precioRadar());
			msg = "La IA compra un radar";
			textoYAudio.setAudio("compra");
		}
		
		// Comprar MISIL (20% de probabilidad)
		
		if (this.generador.nextInt(5) == 0 && super.getDinero() >= alm.precioMisil() && alm.hayMisiles()) {
			alm.consumirMisil();
			super.darMisil();
			super.bajarDineroEn(alm.precioMisil());
			
			if (msg.contentEquals("")) {
				msg = "La IA compra un misil";
				textoYAudio.setAudio("compra");

			} else {
				dosPrimerasCompras = true;
			}
			
		}
		
		// Comprar ESCUDO (20% de probabilidad)

		if (this.generador.nextInt(5) == 0 && super.getDinero() >= alm.precioEscudo() && alm.hayEscudos()) {
			alm.consumirEscudo();
			super.darEscudo();
			super.bajarDineroEn(alm.precioEscudo());
			
			if (msg.contentEquals("")) {
				msg = "La IA compra un escudo ";
				textoYAudio.setAudio("compra");

			
			} else if (dosPrimerasCompras) {
				msg = msg +  ", un misil y un escudo";
			
			} else {
				msg = msg + " y un escudo";
			}
			
		} else if (dosPrimerasCompras) {msg = " y un misil";}
		
		if (!msg.contentEquals("")) {
			textoYAudio.setTexto(msg);
			textoYAudio.actualizarCambios();
			super.esperar(500);
		}
		

	}
		
	
	
}