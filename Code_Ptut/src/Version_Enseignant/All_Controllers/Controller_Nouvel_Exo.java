package Version_Enseignant.All_Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	
	public static String contenuRepertoire;
	public static String contenuNomExo;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////			INITIALISATION		////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//On rempli les champs s'il ne sont pas null (si l'enseignant revient en arrière)
		if(contenuRepertoire != null) {
			repertoire.setText(contenuRepertoire);
		}
		
		if(contenuNomExo != null) {
			nomExo.setText(contenuNomExo);
		}
		
		//Si les deux champs sont remplis, on met le bouton cliquable
		if(contenuRepertoire != null && nomExo != null) {
			okNouvelExo.setDisable(false);
		}
		
		//On regarde si le textField du repertoire est vide ou non
		repertoire.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newvalue) {
				if(!repertoire.getText().isEmpty() && !nomExo.getText().isEmpty()) {
					okNouvelExo.setDisable(false);
				} else {
					okNouvelExo.setDisable(true);
				}
			}
			
		});
		
		//On regarde si le textField du Nomd e l'exercice est vide ou non
		nomExo.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newvalue) {
				if(!nomExo.getText().isEmpty() && !repertoire.getText().isEmpty()) {
					okNouvelExo.setDisable(false);
				} else {
					okNouvelExo.setDisable(true);
				}
			}
			
		});
		
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

	//Bouton Préférences qui emmène sur la page des paramètres
	@FXML
	public void preferences(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) repertoire.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/PageDesParametres.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}

	//Bouton DarkMode qui met en darkMode l'application
	@FXML 
	public void darkMode() {
		//TODO faire le DarkMode
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////		METHODES SPECIFIQUES A LA PAGE		////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Bouton Nouveau qui permet de créer un nouvel exercice
	@FXML
	public void pageNouvelExo() throws IOException {
		Stage primaryStage = (Stage) repertoire.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/NouvelExo.fxml"));

		primaryStage.setMaximized(true);
		primaryStage.setScene(new Scene(root));

		primaryStage.show();
	}
	
	@FXML
	public void retourAccueil() throws IOException {
		Stage primaryStage = (Stage) repertoire.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/Menu.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}
	
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
	}
	
	//Méthode pour aller sur la page d'importation de la ressource
	@FXML
	public void pageImportationRessource(ActionEvent event) throws IOException {
		
		//Au moment d'aller sur la page d'après, on récupère le contenu des TextFields
		contenuRepertoire = repertoire.getText();
		contenuNomExo = nomExo.getText();
		
		Stage primaryStage = (Stage) repertoire.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/ImporterRessource.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}
}
