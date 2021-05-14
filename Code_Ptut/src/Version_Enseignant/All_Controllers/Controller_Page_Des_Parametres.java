package Version_Enseignant.All_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller_Page_Des_Parametres implements Initializable{

	//Page de parametres
	@FXML ListView<String> listePolices = new ListView<>();
	@FXML ListView<String> listeTailles = new ListView<>();
	@FXML TextField taille;
	@FXML TextField police;
	
	//Méthode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> polices = FXCollections.observableArrayList("Arial", "Avant Garde", "Avenir","BebasBell Gothic", "Benguiat Gothic", "Bitstream Vera Sans", "Calibri");
		listePolices.setItems(polices);
		ObservableList<String> tailles = FXCollections.observableArrayList("12","13","14","15","16");
		listeTailles.setItems(tailles);
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
	
	//Bouton DarkMode qui met en darkMode l'application
	@FXML 
	public void darkMode() {
		//TODO faire le DarkMode
	}
	
	//Bouton Nouveau qui permet de créer un nouvel exercice
	@FXML
	public void pageNouvelExo() throws IOException {
		Stage primaryStage = (Stage) taille.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/NouvelExo.fxml"));
		primaryStage.setScene(new Scene(root, MainEnseignant.width, MainEnseignant.height));
		primaryStage.show();
	}
	
	@FXML
	public void setParametreTaille(MouseEvent event) {
		taille.setText((String) listeTailles.getFocusModel().getFocusedItem());
	}
	
	@FXML
	public void setParametrePolice(MouseEvent event) {
		police.setText((String) listePolices.getFocusModel().getFocusedItem());
	}

}
