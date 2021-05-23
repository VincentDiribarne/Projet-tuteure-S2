package Version_Etudiant.All_Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.*;
import javafx.scene.text.Text;

public class Controller_Page_Exercice implements Initializable{

	//Variables qui vont contenir les différentes informations sur l'exercice
	//Informations textuelles
	public static String contenuTranscription;
	public static String contenuConsigne;
	public static Media contenuMedia;
	public static String caractereOccul;
	
	//Options
	public static boolean sensiCasse;
	public static boolean entrainement;
	public static boolean evaluation;
	public static String nbMin;
	public static boolean solution;
	public static boolean motDecouverts;
	public static boolean motIncomplet;
	public static boolean lettres_2;
	public static boolean lettres_3;
	public static Image contenuImage;
	
	//TextFields et autre composants qui contiennent les informations de l'exercice
	@FXML private TextArea transcription;
	@FXML private TextArea consigne;
	@FXML private ImageView imageView;
	@FXML private MediaView mediaView;
	@FXML private Text time;
	
	@FXML private Button playPause;
	MediaPlayer mediaPlayer = new MediaPlayer(contenuMedia);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		int i;
		
		transcription.setWrapText(true);
		
		//On fait apparaître un fenêtre pour que l'étudiant rentre son nom et prénom en vue du futur enregistrement
		
		//On load les informations de l'exercice dans les composants
		if(contenuTranscription != null) {
			
			//Pour la transcription, on utilise le caractère d'occultation
			for(i = 0; i < contenuTranscription.length(); i++) {
				if(okRegex(contenuTranscription.charAt(i)) == true) {
					transcription.setText(transcription.getText() + caractereOccul);
				} else {
					transcription.setText(transcription.getText() + contenuTranscription.charAt(i));
				}
			}	
		}
		
		//On load la consigne
		if(contenuConsigne != null) {
			consigne.setText(contenuConsigne);
		}
		
		//On load le media
		if(contenuMedia != null) {
			mediaView.setMediaPlayer(mediaPlayer);
		}
		
		//On load l'image quand il s'agit d'un mp3
		if(contenuImage != null) {
			imageView.setImage(contenuImage);
		}
		
	}
	
	//Fonction qui play/pause le media
	@FXML
	public void playMedia(ActionEvent event) {
		//Si on veut mettre en pause la vidéo
		if(playPause.getText().compareTo("Play") == 0) {
			playPause.setText("Pause");
			mediaPlayer.play();
		} else {
			playPause.setText("Play");
			mediaPlayer.pause();
		}
		
	}
	
	//Fonction qui regarde si le caractère est compatible avec la regex (toutes les lettres et chiffres)
	private boolean okRegex(char caractere) {
		String s = "" + caractere;
		if(s.matches("[a-zA-Z0-9]")) {
			return true;
		} 
		return false;
	}

}
