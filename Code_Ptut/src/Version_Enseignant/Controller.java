package Version_Enseignant;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.*;
import javafx.stage.*;

public class Controller {

	//Id des variables présentes dans les fichiers FXML
	@FXML private TextField repertoire;
	@FXML private MediaView mediaView;

	//Pour l'édition de l'exercice
	//Consigne
	@FXML private TextField textFieldConsigne;
	@FXML private TextArea textAreaConsigne;
	@FXML private TextField textFieldConsigneFinale;


	private void changerScene (Parent root) {
		Stage stage = (Stage) MainEnseignant.root.getScene().getWindow();
		MainEnseignant.root = root;
		stage.setMaximized(true);
		stage.setScene(new Scene(root));
		stage.show();
	}

	//Bouton Quitter qui permet à l'enseignant de quitter l'application
	@FXML
	public void quitter(ActionEvent event) {
		Platform.exit();
	}

	//Bouton Ouvrir qui permet à l'enseignant d'ouvrir un exercice qu'il à déjà créé auparavant
	@FXML
	public void ouvrir(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.showOpenDialog(null);
		//TODO Chargez l'exercice dans la page
	}

	//Bouton Nouveau qui permet à l'enseignant de démarrer la création d'un nouvel exercice
	@FXML
	public void PageNouvelExo(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("NouvelExo.fxml"));
		changerScene(root);
	}


	@FXML
	public void annuler(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PageAccueil.fxml"));
		changerScene(root);
	}


	//Méthode pour choisir le répertoire dans lequel l'enseignant enregistrera son fichier
	@FXML
	public void choisirRepertoire(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory;
		directoryChooser.setTitle("Choisissez un répertoire pour l'enregistrement");
		selectedDirectory = directoryChooser.showDialog(null);
		repertoire.setText(selectedDirectory.getAbsolutePath());
	}

	@FXML
	public void pageImporterRessource(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ImporterRessource.fxml"));
		changerScene(root);
	}

	@FXML
	public void importerRessource(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = new File("");
		selectedFile = fileChooser.showOpenDialog(null);
		Media media = new Media(selectedFile.toURI().toURL().toExternalForm());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		mediaPlayer.play();
	}

	@FXML
	public void pageApercu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PageApercu.fxml"));
		changerScene(root);
	}

	//Méthode pour ouvrir une nouvelle fenêtre pour éditer la consigne
	@FXML
	public void modifierConsigne(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ModifierConsigne.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));

		//La fenêtre créée doit être validée et aucune opératio n'est eprmis avec la fenêtre parente
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(MainEnseignant.root.getScene().getWindow());
		stage.setTitle("Édition de la consigne");
		
		stage.show();
	}

	//Méthode pour ouvrir une nouvelle fenêtre pour éditer la transcription
	@FXML
	public void modifierTranscription(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ModifierTranscription.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));

		//La fenêtre créée doit être validée et aucune opératio n'est eprmis avec la fenêtre parente
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(MainEnseignant.root.getScene().getWindow());
		stage.setTitle("Édition de la transcription");

		stage.show();
	}

	//Méthode pour ouvrir une nouvelle fenêtre pour éditer les aides
	@FXML
	public void modifierAide(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ModifierAide.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));

		//La fenêtre créée doit être validée et aucune opératio n'est eprmis avec la fenêtre parente
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(MainEnseignant.root.getScene().getWindow());
		stage.setTitle("Édition des aides");

		stage.show();
	}
	
	//Méthode qui remplit le textArea en fonction de ce que contient le TextField
	@FXML 
	public void remplissageTexte(KeyEvent event) {
		//On dit que le texte doit revenir à la ligne
		textAreaConsigne.setWrapText(true);
		//On note ce qu'écrit le professeur dans le textArea
		textAreaConsigne.setText(textFieldConsigne.getText());
	}
	
	@FXML
	public void validation(ActionEvent event) {
		textFieldConsigneFinale = new TextField();
		textFieldConsigneFinale.setText(textFieldConsigne.getText());
	}

}
