package Version_Enseignant;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Ressource {

	//Attributs d'objets
	private String transcription;
	private MediaView mediaView = choisirFichier();
	
	//Constructeurs
	public Ressource(String transcription, MediaView mediaView) {
		super();
		this.transcription = transcription;
	}
	
	private MediaView choisirFichier(){
		FileChooser fileChooser = new FileChooser();
		File selectedFile = new File("");
		Stage primaryStage = new Stage();
		selectedFile = fileChooser.showOpenDialog(primaryStage);
		Media media = null;
		try {
			media = new Media(selectedFile.toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer);
		return mediaView;
	}
	
	//Méthodes qui gèrent le son de la vidéo
	public void baisserSon() {
		MediaPlayer m = mediaView.getMediaPlayer();
		m.setVolume(m.getVolume() - 0.1);
	}
	
	public void augmenterSon() {
		MediaPlayer m = mediaView.getMediaPlayer();
		m.setVolume(m.getVolume() + 0.1);
	}
	
	public void avancer() {
		MediaPlayer m = mediaView.getMediaPlayer();
		m.seek(m.getCurrentTime().add(Duration.seconds(5)));
	}
	
	public void reculer() {
		MediaPlayer m = mediaView.getMediaPlayer();
		m.seek(m.getCurrentTime().add(Duration.seconds(-5)));
	}
	
}
