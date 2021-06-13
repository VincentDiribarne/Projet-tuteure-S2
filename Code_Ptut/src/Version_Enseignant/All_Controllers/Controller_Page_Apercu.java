package Version_Enseignant.All_Controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import Version_Etudiant.DeplacementFenetre;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller_Page_Apercu implements Initializable {

	// Page Apercu
	@FXML
	private TextField texteConsigne;
	@FXML
	private TextArea texteTranscription;
	@FXML
	private TextField texteAide;
	@FXML
	private MediaView MediaViewApercu;
	@FXML
	private Button okApercu;
	@FXML
	private ImageView imageViewApercu;

	public static String contenuConsigne;
	public static String contenuTranscription;
	public static String contenuAide;

	@FXML private CheckMenuItem dark;

	// Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// On met le media dans la preview
		MediaPlayer mediaPlayer = new MediaPlayer(Controller_Importer_Ressource.contenuMedia);
		MediaViewApercu.setMediaPlayer(mediaPlayer);

		// On met l'image dans la preview
		imageViewApercu.setImage(Controller_Importer_Ressource.contenuImage);

		// Si les contenus ne sont pas null (lorsque l'enseignant fait retour), les
		// informations sont conservées
		// Consigne
		if (contenuConsigne != null) {
			texteConsigne.setText(contenuConsigne);
		}

		// Transcription
		if (contenuTranscription != null) {
			texteTranscription.setText(contenuTranscription);
		}

		// Aide
		if (contenuAide != null) {
			texteAide.setText(contenuAide);
		}
	}

	//Bouton Quitter qui permet à l'enseignant de quitter l'application (disponible sur toutes les pages)
		@FXML
		public void quitter(ActionEvent event) throws IOException {
			
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/ConfirmationQuitter.fxml"));
			Scene scene = new Scene(root, 400, 200);
			//On bloque sur cette fenêtre
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			scene.setFill(Color.TRANSPARENT);
			
			//Bordure
			Rectangle rect = new Rectangle(400,200); 
			rect.setArcHeight(20.0); 
			rect.setArcWidth(20.0);  
			root.setClip(rect);
			
			DeplacementFenetre.deplacementFenetre((Pane) root, primaryStage);
			primaryStage.setScene(scene);
			darkModeActivation(scene);
			primaryStage.show();
		}
		
	@FXML
	public void pageNouvelExo() throws IOException {

		//Réinitialisation des variables
		Controller_Page_Accueil c = new Controller_Page_Accueil();
		c.delete();
		Stage primaryStage = (Stage) okApercu.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/NouvelExo.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}
	
	//Méthode qui permet de se rendre au manuel utilisateur == tuto
	@FXML
	public void tuto() throws MalformedURLException, IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URL("https://docs.google.com/document/d/1r6RBg1hgmUD9whe2_Opq_Uy1BgxdBL1Th0HkQHWxcFo/edit?usp=sharing").toURI());
	}

	// Méthode pour charger la page d'importation de ressource (bouton retour)
	@FXML
	public void pageImporterRessource(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) okApercu.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/ImporterRessource.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		darkModeActivation(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Méthode pour charger la page des options de l'exercice
	@FXML
	public void pageOptions(ActionEvent event) throws IOException {
		// Quand on passe à la page suivante, on réucpère les informations des
		// TextFields
		contenuConsigne = texteConsigne.getText();
		contenuTranscription = texteTranscription.getText();
		contenuAide = texteAide.getText();

		Stage primaryStage = (Stage) okApercu.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageOptions.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}

	//Méthode pour passer ou non le darkMode
	@FXML
	public void darkMode() {

		if(dark.isSelected()) {
			okApercu.getScene().getStylesheets().removeAll(getClass().getResource("../FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			okApercu.getScene().getStylesheets().addAll(getClass().getResource("../FXML_Files/darkModeTest.css").toExternalForm());
			Controller_Page_Accueil.isDark = true;
		} else {
			okApercu.getScene().getStylesheets().removeAll(getClass().getResource("../FXML_Files/darkModeTest.css").toExternalForm());
			okApercu.getScene().getStylesheets().addAll(getClass().getResource("../FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			Controller_Page_Accueil.isDark = false;
		}

	}

	//Méthode qui regarde si le darkMode est actif et l'applique en conséquence à la scene
	public void darkModeActivation(Scene scene) {
		if(Controller_Page_Accueil.isDark) {
			scene.getStylesheets().removeAll(getClass().getResource("../FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			scene.getStylesheets().addAll(getClass().getResource("../FXML_Files/darkModeTest.css").toExternalForm());
			dark.setSelected(true);
		} else {
			scene.getStylesheets().removeAll(getClass().getResource("../FXML_Files/darkModeTest.css").toExternalForm());
			scene.getStylesheets().addAll(getClass().getResource("../FXML_Files/MenuAndButtonStyles.css").toExternalForm());
			dark.setSelected(false);
		}
	}

}
