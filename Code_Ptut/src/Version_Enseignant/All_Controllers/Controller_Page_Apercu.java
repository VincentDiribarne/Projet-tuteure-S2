package Version_Enseignant.All_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Page_Apercu implements Initializable{

	//Page Apercu
	@FXML private TextField texteConsigne;
	@FXML private TextField texteTranscription;
	@FXML private TextField texteAide;
	@FXML private MediaView MediaViewApercu;
	@FXML private Button okApercu;
	@FXML private ImageView imageViewApercu;
	
	public static String contenuConsigne;
	public static String contenuTranscription;
	public static String contenuAide;
	
	//Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//On met le media dans la preview
		MediaPlayer mediaPlayer = new MediaPlayer(Controller_Importer_Ressource.contenuMedia);
		MediaViewApercu.setMediaPlayer(mediaPlayer);
		
		//On met l'image dans la preview
		imageViewApercu.setImage(Controller_Importer_Ressource.contenuImage);
		
		//Si les contenus ne sont pas null (lorsque l'enseignant fait retour), les informations sont conservées
		//Consigne
		if(contenuConsigne != null) {
			texteConsigne.setText(contenuConsigne);
		}
		
		//Transcription
		if(contenuTranscription != null) {
			texteTranscription.setText(contenuTranscription);
		}
		
		//Aide
		if(contenuAide != null) {
			texteAide.setText(contenuAide);
		}
	}

	//Bouton Quitter qui permet à l'enseignant de quitter l'application (disponible sur toutes les pages)
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}

	//Bouton Ouvrir qui permet à l'enseignant d'ouvrir un exercice qu'il à déjà créé auparavant
	@FXML
	public void ouvrir(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Ouvrez votre exercice");
		fileChooser.showOpenDialog(null);
		//TODO Chargez l'exercice dans la page
	}
	
	//Bouton Préférences qui emmène sur la page des paramètres
	@FXML
	public void preferences(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) okApercu.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}
	
	//Bouton DarkMode qui met en darkMode l'application
	@FXML 
	public void darkMode() {
		//TODO faire le DarkMode
	}
	
	//Pour éditer la consigne
		//Lorsqu'on appuie sur Valider, un effet s'applique au TextField
		@FXML
		public void validationConsigne(ActionEvent event) {
			texteConsigne.setEditable(false);
			texteConsigne.setOpacity(0.5);
		}

		//Lorsque le professeur veut rééditer la consigne, l'effet disparaît sur le TextField
		@FXML
		public void editionConsigne(MouseEvent event) {
			texteConsigne.setEditable(true);
			texteConsigne.setOpacity(1);
		}
		
		//Pour éditer la transcription
		//Lorsqu'on appuie sur Valider, un effet s'applique au TextField
		@FXML
		public void validationTranscription(ActionEvent event) {
			texteTranscription.setEditable(false);
			texteTranscription.setOpacity(0.5);
		}

		//Lorsque le professeur veut rééditer la transcription, l'effet disparaît sur le TextField
		@FXML
		public void editionTranscription(MouseEvent event) {
			texteTranscription.setEditable(true);
			texteTranscription.setOpacity(1);
		}
		
		//Pour éditer les aides
		//Lorsqu'on appuie sur Valider, un effet s'applique au TextField
		@FXML
		public void validationAide(ActionEvent event) {
			texteAide.setEditable(false);
			texteAide.setOpacity(0.5);
		}

		//Lorsque le professeur veut rééditer les aides, l'effet disparaît sur le TextField
		@FXML
		public void editionAide(MouseEvent event) {
			texteAide.setEditable(true);
			texteAide.setOpacity(1);
		}
		
		
		//Méthode pour charger la page d'importation de ressource (bouton retour)
		@FXML
		public void pageImporterRessource(ActionEvent event) throws IOException {
			Stage primaryStage = (Stage) okApercu.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/ImporterRessource.fxml"));
			primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
			primaryStage.show();
		}
		
		//Méthode pour charger la page des options de l'exercice
		@FXML
		public void pageOptions(ActionEvent event) throws IOException {
			//Quand on passe à la page suivante, on réucpère les informations des TextFields
			contenuConsigne = texteConsigne.getText();
			contenuTranscription = texteTranscription.getText();
			contenuAide = texteAide.getText();
			
			Stage primaryStage = (Stage) okApercu.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageOptions.fxml"));
			primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
			primaryStage.show();
		}
		

}
