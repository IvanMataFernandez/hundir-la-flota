package barco;



public class JugadorHumano extends Jugador {




	
	public JugadorHumano () {
		super(true);
		
	}
	
	
	
	public void procesarAcciones() throws ExcepcionFinDePartida {
		boolean valido = false;
		boolean usoRadar = false;
		boolean atkYNoDef = true;
		boolean res;
		boolean bombaYNoMisil = true;
		
		Posicion pos;
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		GestorInputs inputs = GestorInputs.getGestor();
		
		super.cambiarABomba();

		
		if (super.tieneRadar()) {
			pos = super.radarEn();
			textoYAudio.setTexto("Radar en ("+(pos.getFila()+1)+" , "+(char)(65+pos.getCol())+"). Elige donde disparar");
			textoYAudio.setBoton(1, "usar radar (usos:"+super.usosRadarActual()+")");
			textoYAudio.setBoton(2, "cambiar posición de radar" );
			


		} else {
			textoYAudio.setTexto("No tienes radar. Elige donde disparar");
			textoYAudio.setBoton(1,"ocultar" );
			textoYAudio.setBoton(2,"ocultar" );

		}	
			
		if (super.tieneEscudos()) {
				
			textoYAudio.setBoton(0,"cambiar a modo poner escudos (Restantes: "+super.numEscudos()+")" );
				
				
			
		} else {
			
			textoYAudio.setBoton(0,"ocultar" );

				
		}
		
		if (super.tieneMisiles()) {
			textoYAudio.setBoton(3, "Cambiar a misiles (Restantes: "+super.obtMisiles()+")" );
			
		} else {
			textoYAudio.setBoton(3, "ocultar" );

			
		}
			

			
			
		
		
		while (!valido) {
			
			
			
			
			textoYAudio.actualizarCambios();
			inputs.esperarInput();
			
			if (inputs.sePulsoBoton()) {
				
				if (super.tieneEscudos() && inputs.getBotonPulsado() == 0) {
					atkYNoDef = !atkYNoDef;
					
					if (atkYNoDef) {
						textoYAudio.setTexto("Cambiado a modo disparar");
						textoYAudio.setBoton(0,"cambiar a modo poner escudos (Restantes: "+super.numEscudos()+")" );


					} else {
						textoYAudio.setTexto("Cambiado a modo poner escudos");
						textoYAudio.setBoton(0,"cambiar a modo disparar" );

					}
					
				} else if (super.tieneMisiles() && inputs.getBotonPulsado() == 3){
					
					if (bombaYNoMisil) {
						textoYAudio.setTexto("Equipando con misil");
						textoYAudio.setBoton(3,"Cambiar a bombas" );	
						
					} else {
						textoYAudio.setTexto("Equipando con bomba");
						textoYAudio.setBoton(3, "Cambiar a misiles (Restantes: "+super.obtMisiles()+")" );
					}
					super.cambiarArmamento();
					bombaYNoMisil = !bombaYNoMisil;
					
					
				} else {
					
				
					
					if (super.tieneRadar() && !usoRadar) {usoRadar = this.usoRadar();}
					
					if (usoRadar) {
						textoYAudio.setBoton(1, "ocultar" );
						textoYAudio.setBoton(2, "ocultar" );
					}					
				}
				

				
			} else {
				
				if (atkYNoDef) {
					valido = this.tiroDirecto();	
					
					if (!bombaYNoMisil && !super.tieneMisiles() && !valido) { // Si se tiro misil, se encadena y no quedan misiles, cambiar a bombas
						textoYAudio.setTexto("No te quedan misiles, cambiando a bombas");
						textoYAudio.setBoton(3, "ocultar" );
						super.cambiarABomba();

					}
					
				} else {
					res = this.ponerEscudos();
					
					if (res && !super.tieneEscudos()) {
						textoYAudio.actualizarCambios();
						textoYAudio.setTexto("Te has quedado sin escudos, cambiando a modo ataque");
						atkYNoDef = true;
					}
					
				}
				
			}
			

			
		}
		
		textoYAudio.actualizarCambios();

		
		super.esperar(1000);
		
		
	}
	
	private boolean ponerEscudos() {
		GestorInputs inputs = GestorInputs.getGestor();
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		int filaSelec = inputs.getFila();
		int colSelec = inputs.getCol();
		boolean valido = inputs.getMatrizJ1Selec();
		
		if (valido) {
			valido = super.ponerEscudoEn(new Posicion(filaSelec, colSelec));
			
			if (valido) {
				
				if (super.tieneEscudos()) {
					textoYAudio.setTexto("Escudo colocado, te quedan: "+super.numEscudos());					
				} else {
					textoYAudio.setBoton(0, "ocultar");
				}
				
				super.actualizarCambios();

				
			} else {
				textoYAudio.setTexto("Escudo no se pudo colocar, ponlo en un barco que este en pie");
				textoYAudio.setAudio("error");

				
			}
			
			
			
		} else {
			textoYAudio.setTexto("Pincha en tu tablero para poner escudos");
			textoYAudio.setAudio("error");

		}
		
		return valido;
		
	}
	
	private boolean usoRadar() {
		// Pre: Jugador tiene radar y lo puede usar.
		// Post: Se usó el radar.
		
		GestorInputs inputs = GestorInputs.getGestor();
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		boolean res;
		Posicion pos;
		boolean val;
		
		if (inputs.getBotonPulsado() == 2) {
			pos = super.moverRadar();
			textoYAudio.setTexto("Radar movido a ("+(pos.getFila()+1)+" , "+(char)(65+pos.getCol())+")");
			val = false;
			
		} else {
			res = super.usarRadarEnRival();
			
			
			if (res) {
				textoYAudio.setTexto("Barco encontrado");
			} else {
				textoYAudio.setTexto("No hay barcos del rival");
			}
			
			Jugadores.getJugadores().getJugadorIA().actualizarCambios();

			
			val = true;
		}
		
		return  val;

		
		
	}
	

	private boolean tiroDirecto() throws ExcepcionFinDePartida {
		boolean res[];
		int filaSelec;
		int colSelec;
		JugadorIA jIA = Jugadores.getJugadores().getJugadorIA();
		boolean valido;
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		GestorInputs inputs = GestorInputs.getGestor();
		
		valido = !inputs.getMatrizJ1Selec();
		filaSelec = inputs.getFila();
		colSelec = inputs.getCol();
		
		if (!valido) {
			textoYAudio.setTexto("Pincha en el tablero del rival");
			textoYAudio.setAudio("error");

		} else {
			valido = !jIA.haDisparadoAhi(filaSelec, colSelec);
			
			if (!valido) {
				textoYAudio.setTexto("Ese espacio ya fue disparado");
				textoYAudio.setAudio("error");

			} else {
				res = super.dispararAlOtro(new Posicion(filaSelec, colSelec));
				

				if (res[0]) {
										
					if (res[1]) { // HUNDIDO
						

						textoYAudio.setTexto("Disparas en:  "+(filaSelec+1)+" "+(char)(65+colSelec)+ ". Barco hundido");
						textoYAudio.setAudio("hundido");
						jIA.hundeUnBarco();

						
					} else { // ESCUDO
					
						textoYAudio.setTexto("Disparas en:  "+(filaSelec+1)+" "+(char)(65+colSelec)+ ". Bloqueado por escudo");
						textoYAudio.setAudio("escudo");
						
					}
					
				} else {
					
					if (res[1]) { // TOCADO
				
						textoYAudio.setTexto("Disparas en:  "+(filaSelec+1)+" "+(char)(65+colSelec)+ ". Barco tocado");
						textoYAudio.setAudio("tocado");
						
					} else { // AGUA
						
						textoYAudio.setTexto("Disparas en:  "+(filaSelec+1)+" "+(char)(65+colSelec)+ ". Agua");
						textoYAudio.setAudio("agua");
						
					}
					
					
				}

				
				valido = !res[1]; // Si ha tocado o hundido, repetir proceso.
				
				jIA.actualizarCambios();

			}
			
		}
		
		return valido;
	}
	

	public void colocarBarcos() {

		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		GestorInputs inputs = GestorInputs.getGestor();
		int barcosRestantes = 10;
		int[] bConRes = new int[4]; // barcos concretos restantes (de 4,3,2 y 1)
		int tamSelec; // tamaño del barco que se quiere poner
		int seleccionesHechas;
		int filaSelec;
		int colSelec;
		boolean valido;
		boolean selecHorizontal=false;
		Posicion p1 = null;
		Posicion p2 = null;
		Posicion aux;
		
		textoYAudio.setTexto("Coloca los barcos, pincha en la casilla para poner");
		
		for (int i = 0; i != 4; i++) {
			textoYAudio.setBoton(i, "ocultar");			
		}
		
		
		for (int i = 0; i != 4; i++) {
			bConRes[i] = 4-i;
		}


		
		while (barcosRestantes != 0) {
			seleccionesHechas = 0;
			
			
			while (seleccionesHechas != 2) {

				valido = false;
				while (!valido) {
					textoYAudio.actualizarCambios();

					inputs.esperarInputDeCasilla();
					valido = inputs.getMatrizJ1Selec();
					filaSelec = inputs.getFila();
					colSelec = inputs.getCol();
					
					if (!valido) {
						textoYAudio.setTexto("Has pinchado en el tablero del rival");
						textoYAudio.setAudio("error");

					} else {
						if (seleccionesHechas == 0) {

							p1 = new Posicion(filaSelec, colSelec);
							textoYAudio.setTexto("Pinchado en: "+(filaSelec+1)+" "+(char)(65+colSelec));


						} else {
							
							p2 = new Posicion(filaSelec, colSelec);
				


						}
						

						seleccionesHechas++;
					}
				}
			}
			
				tamSelec = -1;
				
				if (p1.getFila() == p2.getFila()) {
					
					if (p1.getCol() > p2.getCol()) {
						tamSelec = p1.getCol()-p2.getCol();
						aux = p2;
						p2 = p1;
						p1 = aux;

					} else {
						tamSelec = p2.getCol()-p1.getCol();
						
					}
					
					selecHorizontal = true;
					
				} else if (p1.getCol() == p2.getCol()) {
					
					if (p1.getFila() > p2.getFila()) {
						tamSelec = p1.getFila()-p2.getFila();
						aux = p2;
						p2 = p1;
						p1 = aux;
					} else {
						tamSelec = p2.getFila()-p1.getFila();
						
					}
					
					selecHorizontal = false;

				}
				
				if (tamSelec >= 0 && tamSelec <=3) {
					if (bConRes[tamSelec] != 0) {
						
						
						

						
						
						// CALCULAR
						valido = super.posibleColocar(p1, p2, selecHorizontal);
						


						
						if (valido) {

							super.generarBarco(p1, p2, tamSelec, selecHorizontal);

							bConRes[tamSelec]--;
							barcosRestantes--;
							
							textoYAudio.setTexto("Quedan: Portaaviones: "+bConRes[3]+" | Submarinos: "+bConRes[2]+" | Destructores: "+bConRes[1]+" | Fragatas:  "+bConRes[0]);							
							textoYAudio.setAudio("construccion");
							
							super.actualizarCambios();


						} else {
							textoYAudio.setTexto("Barco demasiado próximo a otro. No se ha colocado.");
							textoYAudio.setAudio("error");

						}
						
						
					} else {
						textoYAudio.setTexto("Has puesto ya todos los barcos de "+(tamSelec+1)+" espacios.");
						textoYAudio.setAudio("error");

					}
				} else {
					
					if (tamSelec == -1) {
						textoYAudio.setTexto("No se permite poner en diagonal");
						textoYAudio.setAudio("error");


					} else {
						textoYAudio.setTexto("Barcos de hasta 4 casillas de longitud, no más.");
						textoYAudio.setAudio("error");

						
					}
					
				}
				
			
				
				textoYAudio.actualizarCambios();
			

		}
		
		
		
		super.completarTablero();
		super.actualizarCambios();
	
		
		
	}



	public void procesarCompras() {
		
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		GestorInputs inputs = GestorInputs.getGestor();
		int boton = -1;
		
		while (boton != 2) {
			textoYAudio.setTexto("¿Qué desea hacer? Dinero: "+super.getDinero());
			textoYAudio.setBoton(0, "Reparar barco");
			textoYAudio.setBoton(1, "Acceder tienda");
			textoYAudio.setBoton(2, "Pasar a disparar");
			textoYAudio.setBoton(3, "ocultar");
			
			textoYAudio.actualizarCambios();
			
			inputs.esperarInputDeBoton();
			
			boton = inputs.getBotonPulsado();
			
			if (boton == 0) {
				this.menuReparar();
				
				
			} else if (boton == 1) {
				this.menuTienda();
			}

			
		}
		
		
		
		
		
		/*
		 listaJugador.procesarCompras();
		 
		  
		 Menu inicial:
		 0 -> Reparar Barco
		 1 -> Acceder tienda
		 2 -> Pasar a Disparar
		 
		 Reparar BArco:
		 0 -> volver
		 
		 Tienda:
		 0 -> Misil 
		 1 -> Radar
		 2 -> Escudo
		 3 -> volver 
		  
		 */
		
		
	}
	
	// FALTAN ESTOS MENUS
	

	
	
	
	private void menuReparar() {
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		GestorInputs inputs = GestorInputs.getGestor();
		boolean salir = false;
		int f;
		int c;
		int precio;

		textoYAudio.setTexto("¿Qué barco desea reparar? Dinero: "+super.getDinero());
		textoYAudio.setBoton(0, "Volver");
		textoYAudio.setBoton(1, "ocultar");
		textoYAudio.setBoton(2, "ocultar");
	
		
		while (!salir) {
			textoYAudio.actualizarCambios();
			

			
			inputs.esperarInput();
			
		
			if (inputs.sePulsoBoton()) {
				salir = true;
				

	
			} else if (inputs.getMatrizJ1Selec()) {
				f = inputs.getFila();
				c = inputs.getCol();
				precio = super.precioDeReparacionDeBarco(f, c);
				
				if (precio > 0 && precio <= super.getDinero()) {
	
					if (this.confirmarReparacion(precio)) {
						super.repararBarco(f, c);
						super.bajarDineroEn(precio);
						super.actualizarCambios();
					}
					
					textoYAudio.setTexto("¿Qué barco desea reparar? Dinero: "+super.getDinero());
					textoYAudio.setBoton(0, "Volver");
					textoYAudio.setBoton(1, "ocultar");
					
				} else if (precio == -1) {
					textoYAudio.setTexto("No hay barco para reparar ahí ");
					
				} else if (precio == 0) { 
					textoYAudio.setTexto("El barco ya está reparado");
				
				} else { // precio == -2
					textoYAudio.setTexto("No se puede reparar un barco hundido.");
					
				}

				

				
				
				
			}			
		}
		
		

		

	}
	
	
	private boolean confirmarReparacion (int pPrecio) {
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		GestorInputs inputs = GestorInputs.getGestor();
		boolean reparacion;
		
		textoYAudio.setTexto("Coste de reparación: "+pPrecio+ " | Tu dinero: "+super.getDinero());
		
		textoYAudio.setBoton(0, "Reparar");
		textoYAudio.setBoton(1, "No reparar");
		
		textoYAudio.actualizarCambios();
		
		inputs.esperarInputDeBoton();
		
	    reparacion = inputs.getBotonPulsado() == 0;

	    if (reparacion) {
			textoYAudio.setAudio("construccion");
	    }
	    
	    return reparacion;
			
	}
	
	
	private boolean confirmarCompra (int pPrecio, int pPosee, int pStock) {
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		GestorInputs inputs = GestorInputs.getGestor();
		boolean compra;
		
		textoYAudio.setTexto("Coste de compra: "+pPrecio+ " | Tu dinero: "+super.getDinero()+" | Tienes: "+pPosee+" | En stock: "+pStock);
		
		textoYAudio.setBoton(0, "Comprar");
		textoYAudio.setBoton(1, "No comprar");
		textoYAudio.setBoton(2, "ocultar" );
		textoYAudio.setBoton(3, "ocultar" );

		textoYAudio.actualizarCambios();
		
		inputs.esperarInputDeBoton();
		
		
		compra = inputs.getBotonPulsado() == 0;
		
		if (compra) {
			textoYAudio.setAudio("compra");
		}
		
		return compra;
	
		
	}
	
	
	private void menuTienda() {

		Almacen alm = Almacen.getAlmacen();
		TextoYAudio textoYAudio = TextoYAudio.getInstancia();
		GestorInputs inputs = GestorInputs.getGestor();
		int boton = -1;
		textoYAudio.setBoton(0,"Comprar Misil (" + alm.precioMisil() + " monedas) ");
		textoYAudio.setBoton(1,"Comprar Radar (" + alm.precioRadar() + " monedas) ");
		textoYAudio.setBoton(2,"Comprar Escudo (" + alm.precioEscudo() + " monedas) ");
		textoYAudio.setBoton(3,"Volver" );
		textoYAudio.setTexto("¿Qué deseas comprar? Dinero: "+super.getDinero());
		textoYAudio.actualizarCambios();

		
		
		
		
		
		while (boton != 3) {
			inputs.esperarInputDeBoton();

			boton = inputs.getBotonPulsado();
			
			
			switch (boton) {
			case 0:
				
				if (alm.hayMisiles()) {
					
					if (super.getDinero() >= alm.precioMisil()) {
						
						if (this.confirmarCompra(alm.precioMisil(), super.obtMisiles(), alm.misilesRestantes())) {
							super.bajarDineroEn(alm.precioMisil());
							super.darMisil();
							alm.consumirMisil();
						}
						
						textoYAudio.setBoton(0,"Comprar Misil (" + alm.precioMisil() + " monedas) ");
						textoYAudio.setBoton(1,"Comprar Radar (" + alm.precioRadar() + " monedas) ");
						textoYAudio.setBoton(2,"Comprar Escudo (" + alm.precioEscudo() + " monedas) ");
						textoYAudio.setBoton(3,"Volver" );
						textoYAudio.setTexto("¿Qué deseas comprar? Dinero: "+super.getDinero());
						
						

						
					} else {
						textoYAudio.setTexto("No tienes dinero suficiente para la compra");
					}
					
					

					
			    } else {textoYAudio.setTexto("No quedan existencias");}
				
				textoYAudio.actualizarCambios();
				break;
			case 1:
				
				
				if (alm.hayRadares()) {
					
					if (super.getDinero() >= alm.precioRadar()) {
						
			
						if (!super.tieneRadar()) {
							
							if (this.confirmarCompra(alm.precioRadar(), 0, alm.radaresRestantes())) {
								super.bajarDineroEn(alm.precioRadar());
								super.darRadar();
								super.moverRadar(); // MOVER RADAR PARA QUE PAREZCA QUE SE COMPRÓ OTRO Y SE PONGA A OTRA CASILLA POR DEFECTO
								alm.consumirRadar();
								
							}
							
							
							textoYAudio.setBoton(0,"Comprar Misil (" + alm.precioMisil() + " monedas) ");
							textoYAudio.setBoton(1,"Comprar Radar (" + alm.precioRadar() + " monedas) ");
							textoYAudio.setBoton(2,"Comprar Escudo (" + alm.precioEscudo() + " monedas) ");
							textoYAudio.setBoton(3,"Volver" );
							textoYAudio.setTexto("¿Qué deseas comprar? Dinero: "+super.getDinero());

							
						} else {

							textoYAudio.setTexto("Tienes radar con usos, no hay espacio en el inventario para otro");
						}
						

						
					} else {
						textoYAudio.setTexto("No tienes dinero suficiente para la compra");
					}
					
					

					
			    } else {textoYAudio.setTexto("No quedan existencias");}
				
				
				textoYAudio.actualizarCambios();
				break;
			case 2:
				
				
				if (alm.hayEscudos()) {
					
					if (super.getDinero() >= alm.precioEscudo()) {
						
						
						if (this.confirmarCompra(alm.precioEscudo(), super.numEscudos(), alm.escudosRestantes())) {
							super.bajarDineroEn(alm.precioEscudo());
							super.darEscudo();
							alm.consumirEscudo();
						}
						
						textoYAudio.setBoton(0,"Comprar Misil (" + alm.precioMisil() + " monedas) ");
						textoYAudio.setBoton(1,"Comprar Radar (" + alm.precioRadar() + " monedas) ");
						textoYAudio.setBoton(2,"Comprar Escudo (" + alm.precioEscudo() + " monedas) ");
						textoYAudio.setBoton(3,"Volver" );
						textoYAudio.setTexto("¿Qué deseas comprar? Dinero: "+super.getDinero());
						
						

						
					} else {
						textoYAudio.setTexto("No tienes dinero suficiente para la compra");
					}
					
					

					
			    } else {textoYAudio.setTexto("No quedan existencias");}
				
				

				
				textoYAudio.actualizarCambios();
			}
			

			
			
			
		}
		
		

			
			

			


			
		
	}
	
	
	
	

	

}