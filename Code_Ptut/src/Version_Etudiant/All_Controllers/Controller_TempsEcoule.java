package Version_Etudiant.All_Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller_TempsEcoule {

	@FXML private Label recupScene;


	@FXML
	public void retourAccueil() throws IOException {

		recupScene.getScene().getWindow().hide();
		
		//TODO Revenir à la page d'accueil
	}
}
