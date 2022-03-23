package vista;

import barco.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Reproductor implements Observer {

	
	private Clip clip;
	
	public Reproductor () {}
		


	
	
	private void sonar(String pS) {
		
		/* Pre: String no null. Dicha string indica el nombre de que
		        archivo .wav (archivo de audio) guardado en la carpeta
		        "materiales" se quiere hacer sonar en el programa.
		        
		   Post: Void. El programa hará sonar el fichero de audio si 
		         el reproductor no estaba ocupando haciendo sonar otro.
		         Si lo estaba, esta orden se ignorará.     
		
		
		
		*/
		
		try {
			
			if (!pS.contentEquals("") && (this.clip == null || !this.clip.isRunning())) {
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
		this.sonar(TextoYAudio.getInstancia().getAudio());
	}
	
	
	
	
	
}
