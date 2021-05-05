package Version_Enseignant;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class Controller {

	//Id des variables présentes dans les fichiers FXML
	@FXML private TextField repertoire;
	@FXML private MediaView mediaView;
	//Page Apercu
	@FXML private TextField texteConsigne;
	@FXML private TextField texteTranscription;
	@FXML private TextField texteAide;
	//Page Options
	@FXML private RadioButton radioButtonEntrainement;
	@FXML private RadioButton radioButtonEvaluation;
	@FXML private RadioButton radioButton2Lettres;
	@FXML private RadioButton radioButton3Lettres;
	@FXML private HBox modeEntrainement;
	@FXML private HBox modeEntrainement1;
	@FXML private HBox modeEntrainement2;
	@FXML private HBox modeEntrainement3;
	@FXML private HBox modeEntrainement4;
	@FXML private HBox modeEvaluation;
	

	//////////////////////////Méthodes////////////////////////////////////////
	//Méthode pour passer d'une scène à l'autre
	private void changerScene (Parent root) {
		Stage stage = (Stage) MainEnseignant.root.getScene().getWindow();
		MainEnseignant.root = root;
		stage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
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
	}

	@FXML
	public void pageApercu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PageApercu.fxml"));
		changerScene(root);
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			EDITION DE L'EXERCICE		////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			GESTION DES OPTIONS 		////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Chargement de la page des Options
	@FXML
	public void pageOptions(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PageOptions.fxml"));
		changerScene(root);
	}
	
	//Gestion de si je sélectionne un mode, l'autre se décoche
	@FXML
	public void selectionModeEvaluation(ActionEvent event) {
		//On fait apparaître ce qui concerne le mode Evaluation
		modeEvaluation.setVisible(true);
		
		//On cache ce qui concerne le mode Entraînement
		modeEntrainement.setVisible(false);
		modeEntrainement1.setVisible(false);
		modeEntrainement2.setVisible(false);
		modeEntrainement3.setVisible(false);
		modeEntrainement4.setVisible(false);
		
		//On regarde si l'autre bouton est sélectionné, si c'est le cas on le déselectionne
		if(radioButtonEntrainement.isSelected()) {
			radioButtonEntrainement.setSelected(false);
		}
		
		//Dans le cas d'une déselection du bouton, on retire ce qui concerne le mode Evaluation
		if(!radioButtonEvaluation.isSelected()) {
			modeEvaluation.setVisible(false);
		}
	}
	
	@FXML
	public void selectionModeEntrainement(ActionEvent event) {	
		//On fait apparaître ce qui concerne le mode Entrainement
		modeEntrainement.setVisible(true);
		modeEntrainement1.setVisible(true);
		modeEntrainement2.setVisible(true);
		modeEntrainement3.setVisible(true);
		modeEntrainement4.setVisible(true);
		
		//On cache ce qui concerne le mode Evaluation
		modeEvaluation.setVisible(false);
		
		//On regarde si l'autre bouton est sélectionné, si c'est le cas on le déselectionne
		if(radioButtonEvaluation.isSelected()) {
			radioButtonEvaluation.setSelected(false);
		}
		
		//Dans le cas d'une déselection du bouton, on retire ce qui concerne le mode Entrainement
		if(!radioButtonEntrainement.isSelected()) {
			modeEntrainement.setVisible(false);
			modeEntrainement1.setVisible(false);
			modeEntrainement2.setVisible(false);
			modeEntrainement3.setVisible(false);
			modeEntrainement4.setVisible(false);
		}
	}
	
	
	//Gestion de si je sélectionne une nombre de lettres minimum autorisé, l'autre se décoche 
	@FXML
	public void selection2Lettres(ActionEvent event) {
		if(radioButton3Lettres.isSelected()) {
			radioButton3Lettres.setSelected(false);
		}
	}
	
	@FXML
	public void selection3Lettres(ActionEvent event) {
		if(radioButton2Lettres.isSelected()) {
			radioButton2Lettres.setSelected(false);
		}
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			PAGE D'ENREGISTREMENT 		////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@FXML
	public void pageEnregistrementFinal(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("EnregistrementFinal.fxml"));
		changerScene(root);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////		GESTION DES PARAMETRES 		////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@FXML
	public void pageParametres(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PageParametres.fxml"));
		changerScene(root);
	}
	
	/*@FXML
	public void darkMode(ActionEvent event) {
		
	}*/

}
