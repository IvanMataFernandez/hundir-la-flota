package vista;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Reproductor implements Observer {

	
	
	/*   ARCHIVOS .WAV USADOS AHORA (SE PUEDEN CAMBIAR POR OTROS CON EL MISMO NOMBRE PARA CAMBIAR EL EFECTO):
	     - agua.wav ----------> Sonido cuando se da a una casilla sin barco
	     - construccion.wav --> Sonido que suena cuando se pone un barco
	     - error.wav ---------> Sonido que suena cuando se intenta hacer algo no permitido
	     - hundido.wav -------> Sonido que suena cuando se hunde un barco
	     - tocado.wav --------> Sonido que suena cuando se toca un barco (y no se hunde)
	  	 - escudo.wav --------> Sonido que suena cuando se golpea un barco con escudo
	  	 - escudoPuesto.wav --------> Sonido que suena cuando se pone un escudo a un barco

	  
	  
	  
	 */
	
	
	private static Reproductor r;
	private Clip clip;
	
	private Reproductor () {}
		


	public static Reproductor getRep() {
		if (Reproductor.r == null) {Reproductor.r = new Reproductor();}
		return Reproductor.r;
	}
	
	
	private void sonar(String pS) {
		
		/* Pre: String no null. Dicha string indica el nombre de que
		        archivo .wav (archivo de audio) guardado en la carpeta
		        "materiales" se quiere hacer sonar en el programa.
		        
		   Post: Void. El programa hará sonar el fichero de audio si 
		         el reproductor no estaba ocupando haciendo sonar otro.
		         Si lo estaba, esta orden se ignorará.     
		
		
		
		*/
		
		try {
			
			if (!pS.contentEquals("") ) {
				
				if (this.clip != null && this.clip.isRunning()) {
					this.clip.stop();
				}
				
				AudioInputStream audio = AudioSystem.getAudioInputStream(new File(".\\materiales\\"+pS+".wav"));
				this.clip = AudioSystem.getClip();
				this.clip.open(audio);
				this.clip.start();
			}

		} catch (Exception e) {
			System.out.println("DEBUG: SI ESTÁS LEYENDO ESTO, SE ESTÁ INTENTANDO USAR AUDIO NO DISPONIBLE. AÑADE EL NOMBRE DEL ARCHIVO COMO AUDIO CON EXTENSION .WAV A LA CARPETA MATERIALES");
		}

		

		

		
		
	}





	public void update(Observable o, Object arg) {
		String[] array = (String[]) arg;
		this.sonar(array[0]);
	}
	
	
	
	
	
}
