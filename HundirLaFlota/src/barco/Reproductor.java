package barco;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Reproductor {

	
	private static Reproductor rep;
	private Clip clip;
	
	private Reproductor () {}
		

	public static Reproductor getReproductor() {
		if (Reproductor.rep == null) {Reproductor.rep = new Reproductor();}
		return Reproductor.rep;
	}
	
	
	public void sonar(String pS) {
		
		/* Pre: String no null. Dicha string indica el nombre de que
		        archivo .wav (archivo de audio) guardado en la carpeta
		        "materiales" se quiere hacer sonar en el programa.
		        
		   Post: Void. El programa hará sonar el fichero de audio si 
		         el reproductor no estaba ocupando haciendo sonar otro.
		         Si lo estaba, esta orden se ignorará.     
		
		
		
		*/
		
		try {
		
			
			if (this.clip == null || !this.clip.isRunning()) {
				AudioInputStream audio = AudioSystem.getAudioInputStream(new File(".\\materiales\\"+pS+".wav"));
				this.clip = AudioSystem.getClip();
				this.clip.open(audio);
				this.clip.start();
			}
			

			
		} catch (Exception e) {
			System.out.println("ERROR NO SE ENCUENTRA AUDIO");
		}
		

		
		
	}
	
	
	
	
	
}
