package Version_Enseignant.All_Controllers;

import java.io.File;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Nouvel_Exo implements Initializable{


	@FXML private TextField repertoire;
	@FXML private TextField nomExo;
	@FXML private Button okNouvelExo;


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			INITIALISATION		////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			METHDOES GENERALES		////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

	//Bouton Nouveau qui permet de créer un nouvel exercice
	@FXML
	public void pageNouvelExo() throws IOException {
		Stage primaryStage = (Stage) repertoire.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/NouvelExo.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	//Bouton Préférences qui emmène sur la page des paramètres
	@FXML
	public void preferences(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) repertoire.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PagesDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	//Bouton DarkMode qui met en darkMode l'application
	@FXML 
	public void darkMode() {
		//TODO faire le DarkMode
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////		METHDOES SPECIFIQUES A LA PAGE		////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Méthode pour choisir le répertoire dans lequel l'enseignant enregistrera son fichier
	@FXML
	public void choisirRepertoire(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory;
		directoryChooser.setTitle("Choisissez un répertoire pour l'enregistrement");
		selectedDirectory = directoryChooser.showDialog(null);
		if(selectedDirectory != null) {
			repertoire.setText(selectedDirectory.getAbsolutePath());
		}
		verifRempli();
	}


	//Méthode qui vérifie si les textFields sont rempli afin d'activer le bouton Ok
	//TODO à revoir
	@FXML
	private void verifRempli() {

		//Pour la page de nouvelExo
		if(!nomExo.getText().trim().isEmpty() && !repertoire.getText().trim().isEmpty()) {
			okNouvelExo.setDisable(false);
		}

		if(nomExo.getText().trim().isEmpty() || repertoire.getText().trim().isEmpty()) {
			okNouvelExo.setDisable(true);
		}
	}

}
